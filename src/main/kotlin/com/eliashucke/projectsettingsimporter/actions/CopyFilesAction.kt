package com.eliashucke.projectsettingsimporter.actions

import com.eliashucke.projectsettingsimporter.helper.SettingsImporter
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class CopyFilesAction : AnAction()
{
    override fun actionPerformed(e: AnActionEvent)
    {
        SettingsImporter.performSettingsImport(e.project)
    }
}