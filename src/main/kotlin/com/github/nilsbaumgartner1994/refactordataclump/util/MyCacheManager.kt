package com.github.nilsbaumgartner1994.refactordataclump.util

import com.github.nilsbaumgartner1994.refactordataclump.services.MyProjectService
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project

class MyCacheManager {
    companion object {
        fun openProject(project: Project) {
            println("MyCacheManager opened");
        }
    }
}
