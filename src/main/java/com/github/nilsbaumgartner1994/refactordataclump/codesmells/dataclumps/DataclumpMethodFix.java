package com.github.nilsbaumgartner1994.refactordataclump.codesmells.dataclumps;

import com.github.nilsbaumgartner1994.refactordataclump.utils.MyLogger;
import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.codeInspection.util.IntentionFamilyName;
import com.intellij.openapi.project.Project;

/**
 * Main inspection class for data clumps. Responsible for starting checks and reporting found
 * instances.
 *
 * @author Nils Baumgartner
 */
public class DataclumpMethodFix implements LocalQuickFix {

    /** Text to be displayed in problem description */
    public static final String QUICK_FIX_NAME = "Extract dataclumps variables to a new class.";

    @Override
    public @IntentionFamilyName String getFamilyName() {
        return QUICK_FIX_NAME;
    }

    @Override
    public void applyFix(Project project, ProblemDescriptor descriptor) {
        MyLogger.log("applyFix");
    }
}
