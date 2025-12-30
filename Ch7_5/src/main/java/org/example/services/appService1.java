package org.example.services;

import java.util.function.BiConsumer;

import java.util.logging.Logger;

public class appService1 {
    public appService1(){}
    public static void makeLog(BiConsumer<Logger, String> l, Logger logger, String msgAppend){
        logger.entering(appService1.class.getName(), "makeLog");
        l.accept(logger, msgAppend);
        logger.exiting(appService1.class.getName(), "makeLog");
    }
}
