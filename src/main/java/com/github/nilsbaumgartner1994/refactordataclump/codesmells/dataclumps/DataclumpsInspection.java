package com.github.nilsbaumgartner1994.refactordataclump.codesmells.dataclumps;

import com.github.nilsbaumgartner1994.refactordataclump.utils.MyLogger;
import com.intellij.codeInspection.LocalInspectionTool;
import com.intellij.codeInspection.LocalInspectionToolSession;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.lang.javascript.psi.JSElementVisitor;
import com.intellij.lang.javascript.psi.JSFunction;
import com.intellij.lang.javascript.psi.ecmal4.JSClass;
import com.intellij.psi.PsiElementVisitor;

import javax.swing.*;

/**
 * Main inspection class for data clumps. Responsible for starting checks and reporting found
 * instances.
 *
 * @author Firas Adleh
 */
public class DataclumpsInspection extends LocalInspectionTool {

    /** Group name in inspections configuration window */
    private static final String GROUP_DISPLAY_NAME = "Code Smells";

    /** Inspection name in inspections configuration window */
    private static final String CODE_SMELL_DISPLAY_NAME = "Data Clumps";

    /**
     * This is called in inspection configuration in settings to allow the user to change the
     * required thresholds to detect data clumps instances. All required swing elements are created
     * here and added to a JPanel element.
     *
     * @return panel that have all inspection configuration elements.
     */
    @Override
    public JComponent createOptionsPanel() {
        MyLogger.log("createOptionsPanel");
        return null;
    }

    /**
     * This is called automatically when inspection starts.
     *
     * @param session
     * @param isOnTheFly
     */
    @Override
    public void inspectionStarted(LocalInspectionToolSession session, boolean isOnTheFly) {
        MyLogger.log("inspectionStarted");
    }

    /**
     * This is called automatically when inspection stops.
     *
     * @param session
     * @param problemsHolder
     */
    @Override
    public void inspectionFinished(
            LocalInspectionToolSession session, ProblemsHolder problemsHolder) {
        MyLogger.log("inspectionFinished");
    }

    @Override
    public PsiElementVisitor buildVisitor(final ProblemsHolder holder, boolean isOnTheFly) {
        MyLogger.log("buildVisitor");
        return new JSElementVisitor() {

            @Override
            public void visitJSClass(JSClass aClass) {
                DataclumpAnalyzer.analyzeClass(aClass);
            }

            // TODO: removed Class ?

            @Override
            public void visitJSFunctionDeclaration(JSFunction node) {
                DataclumpAnalyzer.analyzeMethod(node, holder);
            }

            // TODO: removed Function ?

        };
    }

    @Override
    public String getGroupDisplayName() {
        return GROUP_DISPLAY_NAME;
    }

    @Override
    public String getDisplayName() {
        return CODE_SMELL_DISPLAY_NAME;
    }
}
