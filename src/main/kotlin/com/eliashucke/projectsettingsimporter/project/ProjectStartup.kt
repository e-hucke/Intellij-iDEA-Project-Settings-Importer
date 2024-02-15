package com.eliashucke.projectsettingsimporter.project

import com.eliashucke.projectsettingsimporter.dialog.ImportNowDialog
import com.eliashucke.projectsettingsimporter.helper.Utilities
import com.eliashucke.projectsettingsimporter.settings.ApplicationSettingsState
import com.intellij.ide.util.PropertiesComponent
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.StartupActivity

class ProjectStartup : StartupActivity
{
    private val applicationSettings = ApplicationSettingsState.instance

    override fun runActivity(project: Project)
    {
        if (!PropertiesComponent.getInstance(project).getBoolean(
                Utilities.IMPORT_POPUP_STATE) && ((applicationSettings.pathToFiles != "" && applicationSettings.importIdeaFiles) || (applicationSettings.importOnSaveActions))
            && !applicationSettings.disablePopup)
        {
            val importDialog = ImportNowDialog(project)
            importDialog.show()
        }
        else
        {
            PropertiesComponent.getInstance(project).setValue(Utilities.IMPORT_POPUP_STATE, true)
        }
    }
}
