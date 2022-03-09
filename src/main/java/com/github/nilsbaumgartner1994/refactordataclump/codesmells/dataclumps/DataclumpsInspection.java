package com.github.nilsbaumgartner1994.refactordataclump.codesmells.dataclumps;

import com.intellij.codeInspection.*;
import com.intellij.psi.*;
import com.intellij.ui.DocumentAdapter;
import com.intellij.util.ui.JBUI;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.io.FileWriter;

/**
 * Main inspection class for data clumps. Responsible for starting checks and reporting found instances.
 *
 * @author Firas Adleh
 */
public class DataclumpsInspection extends LocalInspectionTool {

    /**
     * Group name in inspections configuration window
     */
    private static final String GROUP_DISPLAY_NAME = "Code Smells";

    /**
     * Inspection name in inspections configuration window
     */
    private static final String CODE_SMELL_DISPLAY_NAME = "Data Clumps";

    /**
     * This is called in inspection configuration in settings to allow the user to
     * change the required thresholds to detect data clumps instances.
     * All required swing elements are created here and added to a JPanel element.
     *
     * @return panel that have all inspection configuration elements.
     */
    @Override
    public JComponent createOptionsPanel() {
        System.out.println("createOptionsPanel");
        return null;
    }

    /**
     * This is called automatically when inspection starts.
     *
     * @param session
     * @param isOnTheFly
     */
    @Override
    public void inspectionStarted(@NotNull LocalInspectionToolSession session, boolean isOnTheFly) {
        System.out.println("inspectionStarted");
    }

    /**
     * This is called automatically when inspection stops.
     *
     * @param session
     * @param problemsHolder
     */
    @Override
    public void inspectionFinished(@NotNull LocalInspectionToolSession session, @NotNull ProblemsHolder problemsHolder) {
        System.out.println("inspectionFinished");
    }

    @NotNull
    @Override
    public PsiElementVisitor buildVisitor(@NotNull final ProblemsHolder holder, boolean isOnTheFly) {
        System.out.println("buildVisitor");
        return new PsiElementVisitor() {

        };
    }

    @Override
    @NotNull
    public String getGroupDisplayName() {
        return GROUP_DISPLAY_NAME;
    }

    @Override
    @NotNull
    public String getDisplayName() {
        return CODE_SMELL_DISPLAY_NAME;
    }

}
