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
        parameters.put(EMetaData.TITRE.name(), "DOC_001_Titre du document");
        parameters.put(EMetaData.CONTENU.name(), "Description du document 001");
        parameters.put(EMetaData.AUTEUR.name(), "Dupont");
        parameters.put(EMetaData.MOT_CLE.name(), "Document Test Kibana");

        success = es.putData(index, type, id, parameters);
        logger.info("putData : success " + success);

        // 2ND DATA
        parameters.clear();
        parameters.put(EMetaData.TITRE.name(), "DOC_002_Titre du document Word");
        parameters.put(EMetaData.CONTENU.name(), "Description du document 002 (au format autre que PDF)");
        parameters.put(EMetaData.AUTEUR.name(), "Durant");
        parameters.put(EMetaData.MOT_CLE.name(), "Document Test Word");

        id = "2";
        success = es.putData(index, type, id, parameters);
        logger.info("putData : success " + success);

        // 3RD DATA
        parameters.clear();
        parameters.put(EMetaData.TITRE.name(), "DOC_003_Titre du document PDF");
        parameters.put(EMetaData.CONTENU.name(), "Description du document 003 (au format PDF)");
        parameters.put(EMetaData.AUTEUR.name(), "Dupont");
        parameters.put(EMetaData.MOT_CLE.name(), "Document PDF");

        id = "3";
        success = es.putData(index, type, id, parameters);
        logger.info("putData : success " + success);

        // GET
        id = "1";
        Map<String, Object> data = es.getData(index, type, id);
        logger.info("getData : data.entrySet().size() " + data.entrySet().size());


        // UPDATE
        id = "1";
        parameters.clear();
        parameters.put(EMetaData.CONTENU.name(), "Description du document 001 après une mise à jour");

        success = es.updateData(index, type, id, parameters);
        logger.info("updateData : success " + success);


        // SEARCH
        parameters.clear();
        parameters.put(EMetaData.AUTEUR.name(), "Dupont");

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
        parameters.put(EMetaData.MOT_CLE.name(), "PDF");
        parameters.put(EMetaData.CONTENU.name(), "PDF");

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
        parameters.put(EMetaData.AUTEUR.name(), "Dupont");

        success = es.deleteData(index, parameters);
        logger.info("deleteData : success " + success);


        es.disconnect();
    }
}
