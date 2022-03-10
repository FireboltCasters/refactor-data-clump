package com.github.nilsbaumgartner1994.refactordataclump.utils;

import com.intellij.openapi.diagnostic.Logger;

public class MyLogger {

    private MyLogger() {}

    public static final Logger mainLog = Logger.getInstance("#DataClumps");

    private static long lastTimeStamp;

    public static void log(Object o) {
        MyLogger.internLog(o);
        if (MyLogger.lastTimeStamp != 0) {
            long elapsedTime = System.currentTimeMillis() - MyLogger.lastTimeStamp;
            MyLogger.internLog(elapsedTime + "");
        }
        MyLogger.lastTimeStamp = System.currentTimeMillis();
    }

    private static void internLog(Object o) {
        mainLog.warn(o.toString());
    }
}
