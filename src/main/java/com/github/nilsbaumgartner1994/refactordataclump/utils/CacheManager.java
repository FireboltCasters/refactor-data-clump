package com.github.nilsbaumgartner1994.refactordataclump.utils;

import com.intellij.execution.PsiLocation;
import com.intellij.lang.ecmascript6.JSXHarmonyFileType;
import com.intellij.lang.javascript.JavaScriptFileType;
import com.intellij.lang.javascript.TypeScriptFileType;
import com.intellij.lang.javascript.TypeScriptJSXFileType;
import com.intellij.lang.javascript.psi.JSFunction;
import com.intellij.lang.javascript.psi.ecmal4.JSClass;
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
 * @author Nils Baumgartner
 */
public class CacheManager {

    private CacheManager() {}

    public static HashMap<String, JSFunction> allFunctions = new HashMap<>();
    public static HashMap<String, JSClass> allClasses = new HashMap<>();

    public static HashMap<String, List<String>> allSuperClasses = new HashMap<>();
    public static List<String> allClassesQualifiedNames = new ArrayList<>();
    public static boolean isCreatingCache = false;

    public static void resetCache() {
        allFunctions = new HashMap<>();
        allClasses = new HashMap<>();

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
        String[] ignoreFunctionNames = {
            "adopt", "fulfilled", "rejected", "step", "sent", "verb", "Symbols"
        };
        for (String ignoreFunctionName : ignoreFunctionNames) {
            map.put(ignoreFunctionName, ignoreFunctionName);
        }
        return map;
    }

    public static List<PsiLocation<JSFunction>> getAllLocationsFromFunction(JSFunction function) {
        return ImmutableList.of(new PsiLocation<>(function));
    }

    private static Collection<VirtualFile> getAllJavaScriptFiles(Project currentProject) {
        return com.intellij.psi.search.FileTypeIndex.getFiles(
                JavaScriptFileType.INSTANCE, GlobalSearchScope.projectScope(currentProject));
    }

    private static Collection<VirtualFile> getAllJSXFiles(Project currentProject) {
        return com.intellij.psi.search.FileTypeIndex.getFiles(
                JSXHarmonyFileType.INSTANCE, GlobalSearchScope.projectScope(currentProject));
    }

    private static Collection<VirtualFile> getAllTypescriptFiles(Project currentProject) {
        return com.intellij.psi.search.FileTypeIndex.getFiles(
                TypeScriptFileType.INSTANCE, GlobalSearchScope.projectScope(currentProject));
    }

    private static Collection<VirtualFile> getAllTSXFiles(Project currentProject) {
        return com.intellij.psi.search.FileTypeIndex.getFiles(
                TypeScriptJSXFileType.INSTANCE, GlobalSearchScope.projectScope(currentProject));
    }

    private static Collection<VirtualFile> getAllVirtualFiles(Project currentProject) {
        Collection<VirtualFile> files = new ArrayList<>();
        files.addAll(CacheManager.getAllJavaScriptFiles(currentProject));
        files.addAll(CacheManager.getAllJSXFiles(currentProject));
        files.addAll(CacheManager.getAllTypescriptFiles(currentProject));
        files.addAll(CacheManager.getAllTSXFiles(currentProject));
        return files;
    }

    private static List<JSFunction> getAllFunctionsOfFile(PsiFile psiFile) {
        List<JSFunction> functions = new ArrayList<>();
        if (psiFile != null) {
            for (JSFunction function : PsiTreeUtil.findChildrenOfType(psiFile, JSFunction.class)) {
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

    public static String getUniqueFunctionName(JSFunction function) {
        return function.getQualifiedName();
    }

    public static void addFunction(JSFunction function) {
        CacheManager.allFunctions.put(CacheManager.getUniqueFunctionName(function), function);
    }

    public static void addFunctions(List<JSFunction> functions) {
        for (JSFunction function : functions) {
            CacheManager.addFunction(function);
        }
    }

    public static void init(Project currentProject) {
        MyLogger.log("init start");

        try {
            Collection<VirtualFile> virtualJavaScriptFiles =
                    CacheManager.getAllVirtualFiles(currentProject);
            MyLogger.log("init found " + virtualJavaScriptFiles.size() + " files");

            for (VirtualFile virtualFile : virtualJavaScriptFiles) {
                PsiFile psiFile = PsiManager.getInstance(currentProject).findFile(virtualFile);
                List<JSFunction> functions = CacheManager.getAllFunctionsOfFile(psiFile);
                CacheManager.addFunctions(functions);
            }
        } catch (Error err) {
            MyLogger.log(err);
        }

        MyLogger.log("init end");
    }
}
