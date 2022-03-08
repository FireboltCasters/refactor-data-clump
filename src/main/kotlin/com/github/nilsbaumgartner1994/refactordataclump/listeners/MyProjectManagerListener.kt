package com.github.nilsbaumgartner1994.refactordataclump.listeners

import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManagerListener
import com.github.nilsbaumgartner1994.refactordataclump.services.MyProjectService

internal class MyProjectManagerListener : ProjectManagerListener {

    override fun projectOpened(project: Project) {
        println("Project Opened");
        project.service<MyProjectService>()
    }
}
