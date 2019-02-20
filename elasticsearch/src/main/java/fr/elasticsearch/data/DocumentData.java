package fr.elasticsearch.data;

import fr.elasticsearch.service.ElasticService;
import fr.elasticsearch.util.LoggerUtil;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DocumentData {

    private static Logger logger = LoggerUtil.getInstance();

    public DocumentData() {
        ElasticService es = new ElasticService();
        Map<String, Object> parameters = new HashMap<String, Object>();

        String index = "document";
        String type = "_doc";
        String id = "1";
        boolean success;


        // PUT
        // IST DATA
        parameters.put(EMetaData.TITRE.name(), "GY-COMSUP-EMIA-DIVOPS-J3AIR 002 MSG");
        parameters.put(EMetaData.CONTENU.name(), new Date());
        parameters.put(EMetaData.AUTEUR.name(), "2019-02-13_DR_DGA_TITLE");
        parameters.put(EMetaData.MOT_CLE.name(), "GY COMSUP MOYENS AERIENS");

        success = es.putData(index, type, id, parameters);
        logger.info("putData : success " + success);

        // 2ND DATA
        parameters.clear();
        parameters.put(EMetaData.TITRE.name(), "GY-COMSUP-EMIA-DIVOPS-J3AIR 002 MSG");
        parameters.put(EMetaData.CONTENU.name(), new Date());
        parameters.put(EMetaData.AUTEUR.name(), "2019-02-13_DR_DGA_TITLE");
        parameters.put(EMetaData.MOT_CLE.name(), "GY COMSUP MOYENS AERIENS");

        success = es.putData(index, type, "2", parameters);
        logger.info("putData : success " + success);

        // 3RD DATA
        parameters.clear();
        parameters.put(EMetaData.TITRE.name(), "GY-COMSUP-EMIA-DIVOPS-J3AIR 002 MSG");
        parameters.put(EMetaData.CONTENU.name(), new Date());
        parameters.put(EMetaData.AUTEUR.name(), "2019-02-13_DR_DGA_TITLE");
        parameters.put(EMetaData.MOT_CLE.name(), "GY COMSUP MOYENS AERIENS");

        success = es.putData(index, type, "3", parameters);
        logger.info("putData : success " + success);

        // GET
        Map<String, Object> data = es.getData(index, type, id);
        logger.info("getData : data.entrySet().size() " + data.entrySet().size());


        // UPDATE
        parameters.clear();
        parameters.put(EMetaData.CONTENU.name(), "Cesson-Sévigné");

        success = es.updateData(index, type, id, parameters);
        logger.info("updateData : success " + success);


        // SEARCH
        parameters.clear();
        parameters.put(EMetaData.CONTENU.name(), "NP");

        SearchHits hits = es.searchData(index, parameters);
        logger.info("searchData : hits.totalHits " + hits.getTotalHits());

        for (SearchHit hit : hits.getHits()) {
            logger.info("searchData : hit.getScore() " + hit.getScore());
            logger.info("searchData : hit.getSourceAsMap().get(\"TITRE\") " + hit.getSourceAsMap().get(EMetaData.TITRE.name()));
            logger.info("searchData : hit.getSourcesAsMap().get(\"CONTENU\") " + hit.getSourceAsMap().get(EMetaData.CONTENU.name()));
            logger.info("searchData : hit.getSourceAsMap().get(\"AUTEUR\") " + hit.getSourceAsMap().get(EMetaData.AUTEUR.name()));
            logger.info("searchData : hit.getSourceAsMap().get(\"MOT_CLE\") " + hit.getSourceAsMap().get(EMetaData.MOT_CLE.name()));
        }

        parameters.clear();
        parameters.put(EMetaData.CONTENU.name(), "test");
        parameters.put(EMetaData.MOT_CLE.name(), "MOYENS");

        hits = es.searchData(index, parameters);
        logger.info("searchData : hits.totalHits " + hits.getTotalHits());

        for (SearchHit hit : hits.getHits()) {
            logger.info("searchData : hit.getScore() " + hit.getScore());
            logger.info("searchData : hit.getSourceAsMap().get(\"TITRE\") " + hit.getSourceAsMap().get(EMetaData.TITRE.name()));
            logger.info("searchData : hit.getSourcesAsMap().get(\"CONTENU\") " + hit.getSourceAsMap().get(EMetaData.CONTENU.name()));
            logger.info("searchData : hit.getSourceAsMap().get(\"AUTEUR\") " + hit.getSourceAsMap().get(EMetaData.AUTEUR.name()));
            logger.info("searchData : hit.getSourceAsMap().get(\"MOT_CLE\") " + hit.getSourceAsMap().get(EMetaData.MOT_CLE.name()));
        }


        // DELETE BULK
        parameters.clear();
        parameters.put(EMetaData.AUTEUR.name(), "Paris");

        success = es.deleteData(index, parameters);
        logger.info("deleteData : success " + success);


        es.disconnect();
    }
}
