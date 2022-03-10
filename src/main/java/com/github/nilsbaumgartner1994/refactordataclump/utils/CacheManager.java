package com.github.nilsbaumgartner1994.refactordataclump.utils;


import com.google.common.collect.ImmutableList;
import com.intellij.execution.PsiLocation;
import com.intellij.lang.javascript.JavaScriptFileType;
import com.intellij.lang.javascript.TypeScriptFileType;
import com.intellij.lang.javascript.psi.JSFunction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiTreeUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * The cache manager contains meta-information about the whole project. The information is created when the project
 * opens and is quickly accessible afterward
 *
 * @author Nils Baumgartner & Firas Adleh
 */
public class CacheManager {

    public static List<Object> allClasses = new ArrayList<>();
    public static HashMap<String, List<String>> allSuperClasses = new HashMap<>();
    public static List<String> allClassesQualifiedNames = new ArrayList<>();
    public static boolean isCreatingCache = false;

    public static void resetIsCacheReady() {
        allClasses = new ArrayList<>();
        allSuperClasses = new HashMap<>();
        allClassesQualifiedNames = new ArrayList<>();
        isCreatingCache = false;
    }

    private static boolean ignoreFunctionName(String functionName){
        if(functionName==null){
            return true;
        }
        HashMap ignoreFunctionNamesMap = CacheManager.getFunctionNamesToIgnore();
        return ignoreFunctionNamesMap.containsKey(functionName);
    }

    private static HashMap<String, String> getFunctionNamesToIgnore(){
        HashMap map = new HashMap<>();
        String[] ignoreFunctionNames = {"adopt", "fulfilled", "rejected", "step", "sent", "verb"};
        for(String ignoreFunctionName : ignoreFunctionNames){
            map.put(ignoreFunctionName, ignoreFunctionName);
        }
        return map;
    }

    public static void createClassesListCache(Project currentProject) {
        MyLogger.log("createClassesListCache start");
        allClasses = new ArrayList<>();

        //FileType jsType = FileTypeManager.getInstance().getFileTypeByExtension("js");
        Collection<VirtualFile> virtualTypeScriptFiles = com.intellij.psi.search.FileTypeIndex.getFiles(TypeScriptFileType.INSTANCE,
                GlobalSearchScope.projectScope(currentProject));

        Collection<VirtualFile> virtualJavaScriptFiles = com.intellij.psi.search.FileTypeIndex.getFiles(JavaScriptFileType.INSTANCE,
                GlobalSearchScope.projectScope(currentProject));

        for (VirtualFile virtualFile : virtualJavaScriptFiles) {
            PsiFile psiFile = PsiManager.getInstance(currentProject).findFile(virtualFile);
            MyLogger.log("- "+psiFile.getName());

            for (JSFunction function : PsiTreeUtil.findChildrenOfType(psiFile, JSFunction.class)) {
                String functionName = function.getName();
                if(!CacheManager.ignoreFunctionName(functionName)){
                    MyLogger.log("-- "+functionName);
                    List<PsiLocation> test = ImmutableList.of(new PsiLocation<>(function));
                }
            }
        }
        MyLogger.log("createClassesListCache end");
    }

}
