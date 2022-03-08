package com.github.nilsbaumgartner1994.refactordataclump.util;

import com.intellij.openapi.project.Project;

import java.util.*;

/**
 * The cache manager contains meta-information about the whole project. The information is created when the project
 * opens and is quickly accessible afterward
 *
 * @author Firas Adleh
 */
public class CacheManager {

    public static List<String> getAllClassesQualifiedNames() {
        return allClassesQualifiedNames;
    }

    /**
     * A list of classes names with their full path
     */
    private static List<String> allClassesQualifiedNames = new ArrayList<>();

    /**
     * A list of basic class types that could be ignored when searching for a common hierarchy
     */
    private static final List<String> basicClassNames = Arrays.asList("Object", "Observable", "Cloneable", "Serializable");
    /**
     * A list of classes names with their hierarchy
     */
    private static HashMap<String, List<String>> allSuperClasses = new HashMap<>();

    /**
     * A flag for creating cache
     */
    private static boolean isCreatingCache = false;

    /**
     * An identifier for cache manger messages in log
     */
    public static final String LOGGER_NAME = CacheManager.class.getSimpleName();

    private CacheManager() {
    }

    /**
     * Start creating a list of all classes names
     *
     * @param currentProject
     */
    public static void createClassesListCache(Project currentProject) {

    }

    /***
     * reset the cache vars to start making the cache from scratch
     */
    public static void resetIsCacheReady() {

    }
}
