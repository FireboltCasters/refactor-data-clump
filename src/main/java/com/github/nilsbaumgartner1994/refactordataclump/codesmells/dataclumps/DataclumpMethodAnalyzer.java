package com.github.nilsbaumgartner1994.refactordataclump.codesmells.dataclumps;

import com.github.nilsbaumgartner1994.refactordataclump.utils.CacheManager;
import com.github.nilsbaumgartner1994.refactordataclump.utils.PsiUtils;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.lang.javascript.psi.JSFunction;
import com.intellij.lang.javascript.psi.JSParameterList;
import com.intellij.lang.javascript.psi.JSParameterListElement;
import com.intellij.openapi.util.TextRange;

import java.util.Collection;
import java.util.List;

/**
 * Main inspection class for data clumps. Responsible for starting checks and reporting found
 * instances.
 *
 * @author Nils Baumgartner
 */
public class DataclumpMethodAnalyzer {

    public static void compareFunctionToAllCachedFunctions(
            JSFunction currentFunction, ProblemsHolder holder) {
        String currentUniqueName = CacheManager.getUniqueFunctionName(currentFunction);
        Collection<JSFunction> otherFunctions = CacheManager.allFunctions.values();
        for (JSFunction otherFunction : otherFunctions) {
            String otherUniqueName = CacheManager.getUniqueFunctionName(otherFunction);
            if (!otherUniqueName.equals(currentUniqueName)) {
                //            if(!otherFunction.equals(currentFunction)){
                DataclumpMethodAnalyzer.compareFunctionToOtherFunction(
                        currentFunction, otherFunction, holder);
            }
        }
    }

    public static void compareFunctionToOtherFunction(
            JSFunction currentFunction, JSFunction otherFunction, ProblemsHolder holder) {
        JSParameterListElement[] currentParameters = currentFunction.getParameters();
        JSParameterListElement[] otherParameters = otherFunction.getParameters();
        List<JSParameterListElement> commonParameters =
                PsiUtils.getCommonParameters(currentParameters, otherParameters);
        if (commonParameters.size() > 0) {
            String description =
                    ""
                            + commonParameters.size()
                            + " Parameters in "
                            + " file: "
                            + currentFunction.getContainingFile().getVirtualFile().getUrl()
                            + " line : "
                            + currentFunction.getTextOffset()
                            + ", method: "
                            + currentFunction.getQualifiedName();
            JSParameterList parameterList = currentFunction.getParameterList();
            TextRange functionWithParametersRange =
                    new TextRange(0, parameterList.getTextRangeInParent().getEndOffset());

            DataclumpAnalyzer.registerMethodProblem(
                    holder, currentFunction, functionWithParametersRange, description);
        }
    }
}
