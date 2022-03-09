package com.github.nilsbaumgartner1994.refactordataclump.utils;

public class MyLogger {

    private static long lastTimeStamp;

    public static void log(Object o){
        MyLogger.lastTimeStamp = System.currentTimeMillis();
        System.out.println(o);
    }

}
