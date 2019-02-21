package fr.elasticsearch.service;

import fr.elasticsearch.util.LoggerUtil;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.index.reindex.DeleteByQueryRequestBuilder;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Map;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

public class ElasticService {

    private Logger logger = LoggerUtil.getInstance();
    private TransportClient client = null;

    public ElasticService() {
        connect("127.0.0.1", 9300);
    }

    public void connect(String address, int port) {
        try {
            client = new PreBuiltTransportClient(Settings.EMPTY)
                    .addTransportAddress(new TransportAddress(InetAddress.getByName(address), port));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        client.close();
    }

    public boolean putData(String index, String type, String id, Map<String, Object> parameters) {
        boolean success = false;

        XContentBuilder toBuild = createJSON(parameters);
        IndexResponse response = client.prepareIndex(index, type, id)
                .setSource(toBuild)
                .get();

        logger.info("putData - response.getResult().name() " +response.getResult().name());

        if ((response.getResult().name().equals("UPDATED")) || (response.getResult().name().equals("CREATED"))) {
            success = true;
        }

        return success;
    }

    public  Map<String, Object> getData(String index, String type, String id) {
        Map<String, Object> data = null;

        GetResponse response = client.prepareGet(index, type, id).get();

        logger.info("putData - response.isSourceEmpty() " +response.isSourceEmpty());

        if (!response.isSourceEmpty()) {
            data = response.getSourceAsMap();
        }

        return data;
    }

    public boolean updateData(String index, String type, String id, Map<String, Object> parameters) {
        boolean success = false;

        XContentBuilder toBuild = createJSON(parameters);
        UpdateResponse response = client.prepareUpdate(index, type, id)
                .setDoc(toBuild)
                .get();

        logger.info("updateData - response.getResult().name() " +response.getResult().name());

        if (response.getResult().name().equals("UPDATED")) {
            success = true;
        }

        return success;
    }

    public SearchHits searchData(String index, Map<String, Object> parameters) {
        SearchRequestBuilder srBuilder = client.prepareSearch(index);
        BoolQueryBuilder bqBuilder = QueryBuilders.boolQuery();

        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            bqBuilder.should(QueryBuilders.termQuery(entry.getKey(), entry.getValue()));
        }
        srBuilder.setQuery(bqBuilder);
        SearchResponse response = srBuilder.get();

        logger.info("searchData - response.getHits().totalHits " +response.getHits().totalHits);

        return response.getHits();
    }

    public boolean deleteData(String index, Map<String, Object> parameters) {
        boolean success = false;
        DeleteByQueryRequestBuilder dqrBuilder = DeleteByQueryAction.INSTANCE.newRequestBuilder(client);

        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            dqrBuilder.filter(QueryBuilders.matchQuery(entry.getKey(), entry.getValue()));
        }
        BulkByScrollResponse response = dqrBuilder.source(index).get();

        logger.info("deleteData - response.getTotal() " + response.getTotal());

        if (!response.isTimedOut()) {
            success = true;
        }

        return success;
    }

    private XContentBuilder createJSON(Map<String, Object> parameters) {
        XContentBuilder jsonObject = null;

        try {
            jsonObject = jsonBuilder().startObject();
            for (Map.Entry<String, Object> entry : parameters.entrySet()) {
                if (entry.getValue() instanceof String) {
                    jsonObject.field(entry.getKey(), entry.getValue());
                } else {
                    logger.warn("Type non reconnu", entry.getValue());
                }
            }
            jsonObject.endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }
}
