package fr.elasticsearch.util;

import org.apache.logging.log4j.LogManager;

public class LoggerUtil {

    private static org.apache.logging.log4j.Logger logger = null;

    public static org.apache.logging.log4j.Logger getInstance() {
        if (logger == null) {
            logger = LogManager.getLogger("fr.elasticsearch");
        }
        return logger;
    }
}
