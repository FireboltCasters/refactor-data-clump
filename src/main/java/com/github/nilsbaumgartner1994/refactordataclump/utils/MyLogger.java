package com.github.nilsbaumgartner1994.refactordataclump.utils;

import com.intellij.openapi.diagnostic.Logger;

public class MyLogger {

  public static final Logger mainLog = Logger.getInstance("#DataClumps");

  private static long lastTimeStamp;

  public static void log(Object o) {
    MyLogger.lastTimeStamp = System.currentTimeMillis();
    System.out.println(o);
    // mainLog.warn(o);
  }
}
