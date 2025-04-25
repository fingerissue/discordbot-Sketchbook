package com.fingerissue.atelier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Sketchbook {
    private static final Logger logger = LoggerFactory.getLogger(Sketchbook.class);

    public static void main(String[] args) {
        logger.trace("This is TRACE level message");
        logger.debug("This is DEBUG level message");
        logger.info("This is INFO level message");
        logger.warn("This is WARN level message");
        logger.error("This is ERROR level message");
    }
}
