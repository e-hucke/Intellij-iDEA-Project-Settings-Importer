package com.eliashucke.projectsettingsimporter.settings

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil

@State(
    name = "com.eliashucke.projectsettingsimporter.settings.ApplicationSettingsState",
    storages = [Storage("projectsettingsimporter.xml")]
)
class ApplicationSettingsState private constructor() : PersistentStateComponent<ApplicationSettingsState>
{
    var importIdeaFiles = false
    var pathToFiles: String = ""
    var disablePopup: Boolean = false

    var importOnSaveActions: Boolean = false
    var reformatCode: Boolean = false
    var optimizeImports: Boolean = false
    var rearrangeCode: Boolean = false
    var runCodeCleanUp: Boolean = false

    companion object
    {
        val instance: ApplicationSettingsState
            get() = ApplicationManager.getApplication().getService(ApplicationSettingsState::class.java)
    }

    override fun getState(): ApplicationSettingsState
    {
        return this
    }

    override fun loadState(state: ApplicationSettingsState)
    {
        XmlSerializerUtil.copyBean(state, this)
    }

}