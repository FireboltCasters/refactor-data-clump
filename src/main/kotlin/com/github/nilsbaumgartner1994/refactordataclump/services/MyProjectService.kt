package com.github.nilsbaumgartner1994.refactordataclump.services

import com.intellij.openapi.project.Project
import com.github.nilsbaumgartner1994.refactordataclump.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
