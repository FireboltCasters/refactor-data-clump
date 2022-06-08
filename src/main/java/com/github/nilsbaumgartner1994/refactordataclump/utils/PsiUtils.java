package com.github.nilsbaumgartner1994.refactordataclump.utils;

import com.intellij.lang.javascript.psi.JSParameterListElement;

import java.util.ArrayList;
import java.util.List;

/** Utilities is a collection of reusable functions for detection and refactoring. */
public class PsiUtils {

    public static String getParameterComparableName(JSParameterListElement element) {
        return element.getText(); // TODO maybe check if type+name is the same
    }

    /**
     * Collect the common parameters between two parameter lists is a new list and return it
     *
     * @param list1
     * @param list2
     * @return
     */
    public static List<JSParameterListElement> getCommonParameters(
            JSParameterListElement[] list1, JSParameterListElement[] list2) {
        List<JSParameterListElement> commonParameters = new ArrayList<>();
        for (JSParameterListElement elem1 : list1) {
            String elem1Name = PsiUtils.getParameterComparableName(elem1);
            for (JSParameterListElement elem2 : list2) {
                String elem2Name = PsiUtils.getParameterComparableName(elem2);
                if (elem1Name.equalsIgnoreCase(elem2Name)) {
                    commonParameters.add(elem1);
                    break; // break current for Loop
                }
            }
        }
        return commonParameters;
    }
}
