package fr.elasticsearch;

import fr.elasticsearch.data.DocumentData;
import fr.elasticsearch.util.LoggerUtil;
import org.apache.logging.log4j.Logger;

public class Main {

    private static Logger logger = LoggerUtil.getInstance();

    public static void main (String[] args) {
        new DocumentData();
    }
}