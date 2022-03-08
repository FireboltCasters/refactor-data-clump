package com.github.nilsbaumgartner1994.refactordataclump.listeners

import com.github.nilsbaumgartner1994.refactordataclump.services.MyProjectService
import com.github.nilsbaumgartner1994.refactordataclump.util.MyCacheManager
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.service
//import com.intellij.openapi.application.ModalityState
//import com.intellij.openapi.project.DumbService
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManagerListener

internal class MyProjectManagerListener : ProjectManagerListener {

    override fun projectOpened(project: Project) {
        // Ensure this isn't part of testing
        if (ApplicationManager.getApplication().isUnitTestMode) {
            return
        }
        println("MyProjectManagerListener - reset the cache to recreate it for the new project");
        // reset the cache to recreate it for the new project
        //CacheManager.resetIsCacheReady()

        MyCacheManager.openProject(project);

        println("MyProjectManagerListener - start creating cache after the project is completely loaded and indexed");
        project.service<MyProjectService>()

        // start creating cache after the project is completely loaded and indexed
        /*
        DumbService.getInstance(project).smartInvokeLater(
            { CacheManager.createClassesListCache(project) }, ModalityState.any()
        )
        */
    }
}
