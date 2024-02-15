package com.eliashucke.projectsettingsimporter.settings

import com.intellij.openapi.options.Configurable
import javax.swing.JComponent

class ApplicationSettingsConfigurable : Configurable
{
    private var appSettingsComponent: ApplicationSettingsComponent? = null
    override fun createComponent(): JComponent?
    {
        appSettingsComponent = ApplicationSettingsComponent()
        return appSettingsComponent?.settingsPanel
    }

    override fun isModified(): Boolean
    {
        val settings = ApplicationSettingsState.instance
        appSettingsComponent?.let {
            return it.pathToSettings != settings.pathToFiles || it.disablePopup != settings.disablePopup
                    || it.reformatCode != settings.reformatCode || it.optimizeImports != settings.optimizeImports
                    || it.rearrangeCode != settings.rearrangeCode || it.runCodeCleanup != settings.runCodeCleanUp
                    || it.onSaveActions != settings.importOnSaveActions || it.importIdeaFiles != settings.importIdeaFiles
        }
        return false
    }

    override fun apply()
    {
        val settings = ApplicationSettingsState.instance
        appSettingsComponent?.let {
            settings.pathToFiles = it.pathToSettings
            settings.disablePopup = it.disablePopup
            settings.reformatCode = it.reformatCode
            settings.optimizeImports = it.optimizeImports
            settings.rearrangeCode = it.rearrangeCode
            settings.runCodeCleanUp = it.runCodeCleanup
            settings.importOnSaveActions = it.onSaveActions
            settings.importIdeaFiles = it.importIdeaFiles
        }
    }

    override fun getDisplayName(): String
    {
        return "Import Settings Plugin"
    }

    override fun reset()
    {
        val settings = ApplicationSettingsState.instance
        appSettingsComponent?.let {
            it.pathToSettings = settings.pathToFiles
            it.disablePopup = settings.disablePopup
            it.reformatCode = settings.reformatCode
            it.optimizeImports = settings.optimizeImports
            it.rearrangeCode = settings.rearrangeCode
            it.runCodeCleanup = settings.runCodeCleanUp
            it.onSaveActions = settings.importOnSaveActions
            it.importIdeaFiles = settings.importIdeaFiles
            it.setCheckBoxEnabled()
        }
    }
}