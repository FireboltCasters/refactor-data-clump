package com.github.nilsbaumgartner1994.refactordataclump.listeners;

import com.github.nilsbaumgartner1994.refactordataclump.utils.CacheManager;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.ModalityState;
import com.intellij.openapi.project.DumbService;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManagerListener;
import org.jetbrains.annotations.NotNull;

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
    public void projectOpened(@NotNull Project project) {
        // Ensure this isn't part of testing
        if (ApplicationManager.getApplication().isUnitTestMode()) {
            return;
        }
        System.out.println("Okay");

        // reset the cache to recreate it for the new project
        CacheManager.resetIsCacheReady();

        // start creating cache after the project is completely loaded and indexed
        DumbService.getInstance(project).smartInvokeLater(
                () ->
                        CacheManager.createClassesListCache(project)
                , ModalityState.any()
        );
    }
}