package com.github.nilsbaumgartner1994.refactordataclump.listeners;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.ModalityState;
import com.intellij.openapi.project.DumbService;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManagerListener;
import org.jetbrains.annotations.NotNull;

/**
 * Listener to invoke recreating cache each time a project opened
 *
 * @author Firas Adleh
 */
public class MyProjectManagerListener implements ProjectManagerListener {

    /**
     * Called when project opens
     *
     * @param project currently opened project
     */
    @Override
    public void projectOpened(@NotNull Project project) {
        // Ensure this isn't part of testing
        if (ApplicationManager.getApplication().isUnitTestMode()) {
            return;
        }
        System.out.println("Okay");

    }
}