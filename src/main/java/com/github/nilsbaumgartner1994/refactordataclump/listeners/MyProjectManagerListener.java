package com.github.nilsbaumgartner1994.refactordataclump.listeners;

import com.github.nilsbaumgartner1994.refactordataclump.utils.CacheManager;
import com.github.nilsbaumgartner1994.refactordataclump.utils.MyLogger;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.ModalityState;
import com.intellij.openapi.project.DumbService;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManagerListener;

/**
 * Listener to invoke recreating cache each time a project opened
 *
 * @author Nils Baumgartner & Firas Adleh
 */
public class MyProjectManagerListener implements ProjectManagerListener {

    /**
     * Called when project opens
     *
     * @param project currently opened project
     */
    @Override
    public void projectOpened( Project project) {
        // Ensure this isn't part of testing
        if (ApplicationManager.getApplication().isUnitTestMode()) {
            return;
        }
        MyLogger.log("Okay");

        // reset the cache to recreate it for the new project
        CacheManager.resetCache();

        // start creating cache after the project is completely loaded and indexed
        DumbService.getInstance(project)
                .smartInvokeLater(
                        () -> CacheManager.init(project), ModalityState.any());
    }
}
