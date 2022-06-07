package com.github.nilsbaumgartner1994.refactordataclump.codesmells.dataclumps;


import com.github.nilsbaumgartner1994.refactordataclump.utils.CacheManager;
import com.github.nilsbaumgartner1994.refactordataclump.utils.MyLogger;
import com.github.nilsbaumgartner1994.refactordataclump.utils.PsiUtils;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.lang.javascript.psi.JSFunction;
import com.intellij.lang.javascript.psi.JSParameterListElement;
import com.intellij.lang.javascript.psi.ecmal4.JSClass;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;

import java.util.Collection;
import java.util.List;

/**
 * Main inspection class for data clumps. Responsible for starting checks and reporting found
 * instances.
 *
 * @author Nils Baumgartner
 */
public class DataclumpAnalyzer {

    public static void analyzeMethod(JSFunction node, ProblemsHolder holder){
        boolean isConstructor = node.isConstructor();
        if(!isConstructor){
            CacheManager.addFunction(node);
            DataclumpMethodAnalyzer.compareFunctionToAllCachedFunctions(node, holder);
        }
    }

    public static void analyzeClass(JSClass aClass){
        String name = aClass.getQualifiedName();

        JSFunction constructor = aClass.getConstructor();
        JSClass[] classes = aClass.getSuperClasses();
    }

    public static void registerMethodProblem(ProblemsHolder holder, PsiElement psiElement, TextRange rangeInElement, String descriptionTemplate){
        if (holder != null) {
            DataclumpMethodFix fix = new DataclumpMethodFix();

            // report either fields or parameters instance
            holder.registerProblem(psiElement,
                    rangeInElement,
                    descriptionTemplate,
                    fix);
        }
    }

}
