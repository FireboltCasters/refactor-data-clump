package com.github.nilsbaumgartner1994.refactordataclump.utils;

import com.intellij.execution.PsiLocation;
import com.intellij.lang.javascript.JavaScriptFileType;
import com.intellij.lang.javascript.psi.JSFunction;
import com.intellij.lang.javascript.psi.JSParameterListElement;
import com.intellij.openapi.project.Project;
import com.google.common.collect.ImmutableList;
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
 * The cache manager contains meta-information about the whole project. The information is created
 * when the project opens and is quickly accessible afterward
 *
 * @author Nils Baumgartner & Firas Adleh
 */
public class CacheManager {

    private CacheManager() {}

    protected static List<JSFunction> allFunctions = new ArrayList<JSFunction>();
    protected static HashMap<String, List<String>> allSuperClasses = new HashMap<>();
    protected static List<String> allClassesQualifiedNames = new ArrayList<>();
    protected static boolean isCreatingCache = false;

    public static void resetIsCacheReady() {
        allFunctions = new ArrayList<JSFunction>();
        allSuperClasses = new HashMap<>();
        allClassesQualifiedNames = new ArrayList<>();
        isCreatingCache = false;
    }

    private static boolean ignoreFunctionName(String functionName) {
        if (functionName == null) {
            return true;
        }
        HashMap<String, String> ignoreFunctionNamesMap = CacheManager.getFunctionNamesToIgnore();
        return ignoreFunctionNamesMap.containsKey(functionName);
    }

    private static HashMap<String, String> getFunctionNamesToIgnore() {
        HashMap<String, String> map = new HashMap<>();
        String[] ignoreFunctionNames = {"adopt", "fulfilled", "rejected", "step", "sent", "verb", "Symbols"};
        for (String ignoreFunctionName : ignoreFunctionNames) {
            map.put(ignoreFunctionName, ignoreFunctionName);
        }
        return map;
    }

    public static List<PsiLocation<JSFunction>> getAllLocationsFromFunction(JSFunction function){
        return ImmutableList.of(new PsiLocation<>(function));
    }

    private static List<JSFunction> getAllFunctionsOfFile(PsiFile psiFile){
        List<JSFunction> functions = new ArrayList<>();
        if (psiFile != null) {
            for (JSFunction function :
                    PsiTreeUtil.findChildrenOfType(psiFile, JSFunction.class)) {
                String functionName = function.getName();
                if (!CacheManager.ignoreFunctionName(functionName)) {
                    functions.add(function);
                    function.getParameters();
                    function.getQualifiedName();
                    CacheManager.getAllLocationsFromFunction(function);
                }
            }
            System.out.println(" ");
        }
        return functions;
    }

    public static void createClassesListCache(Project currentProject) {
        MyLogger.log("createClassesListCache start");
        allFunctions = new ArrayList<>();
        try {
            Collection<VirtualFile> virtualJavaScriptFiles =
                    com.intellij.psi.search.FileTypeIndex.getFiles(
                            JavaScriptFileType.INSTANCE,
                            GlobalSearchScope.projectScope(currentProject));

            for (VirtualFile virtualFile : virtualJavaScriptFiles) {
                PsiFile psiFile = PsiManager.getInstance(currentProject).findFile(virtualFile);
                List<JSFunction> functions = CacheManager.getAllFunctionsOfFile(psiFile);
                CacheManager.allFunctions.addAll(functions);
            }
        } catch (Error err) {
            MyLogger.log(err);
        }

        MyLogger.log("createClassesListCache end");
    }
}
