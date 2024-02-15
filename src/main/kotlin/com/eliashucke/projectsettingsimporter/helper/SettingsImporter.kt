package com.eliashucke.projectsettingsimporter.helper

import com.eliashucke.projectsettingsimporter.dialog.PathNotSetDialog
import com.eliashucke.projectsettingsimporter.settings.ApplicationSettingsState
import com.intellij.ide.util.PropertiesComponent
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.JDOMUtil
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.util.castSafelyTo
import org.jdom.Attribute
import org.jdom.Element
import kotlin.io.path.Path

class SettingsImporter
{
    companion object
    {
        private const val REARRANGE_CODE_KEY = "rearrange.code.on.save"
        private const val CODE_CLEANUP_KEY = "code.cleanup.on.save"
        private const val REFORMAT_CODE_KEY = "FormatOnSaveOptions"
        private const val OPTIMIZE_IMPORTS_KEY = "OptimizeOnSaveOptions"
        private val settings = ApplicationSettingsState.instance

        fun performSettingsImport(projectOpt: Project?)
        {
            Thread.sleep(1000)
            if (settings.importIdeaFiles && settings.pathToFiles == "")
            {
                PathNotSetDialog().show()
            }
            else projectOpt?.let {
                if (settings.importOnSaveActions)
                {
                    if (settings.rearrangeCode)
                    {
                        PropertiesComponent.getInstance(it).setValue(REARRANGE_CODE_KEY, settings.rearrangeCode)
                    }
                    if (settings.runCodeCleanUp)
                    {
                        PropertiesComponent.getInstance(it).setValue(CODE_CLEANUP_KEY, settings.runCodeCleanUp)
                    }
                    this.editWorkspaceXML(it)
                }
                if (settings.importIdeaFiles)
                {
                    copyFiles(it)
                }
            }
        }

        private fun getRunOnSaveComponent(name: String): Element
        {
            val component = Element("component")
            component.setAttributes(listOf(Attribute("name", name)))
            val option = Element("option")
            option.setAttributes(listOf(Attribute("name", "myRunOnSave"), Attribute("value", "true")))
            component.setContent(option)
            return component
        }

        private fun editWorkspaceXML(project: Project)
        {
            val xml: Element = JDOMUtil.load(Path(project.basePath + "/.idea/workspace.xml"))
            var shouldReformat = settings.reformatCode
            var shouldOptimizeImports = settings.optimizeImports
            for (elementGroup in xml.content())
            {
                if (!shouldReformat && !shouldOptimizeImports)
                {
                    return
                }

                elementGroup.castSafelyTo<Element>()?.getAttribute("name")?.value?.let {
                    if (shouldReformat && it == this.REFORMAT_CODE_KEY)
                    {
                        shouldReformat = false
                    }
                    else if (shouldOptimizeImports && it == this.OPTIMIZE_IMPORTS_KEY)
                    {
                        shouldOptimizeImports = false
                    }
                }
            }
            if (shouldReformat)
            {
                xml.addContent(this.getRunOnSaveComponent(this.REFORMAT_CODE_KEY))
            }
            if (shouldOptimizeImports)
            {
                xml.addContent(this.getRunOnSaveComponent(this.OPTIMIZE_IMPORTS_KEY))
            }
            JDOMUtil.write(xml, Path(project.basePath + "/.idea/workspace.xml"))
        }

        private fun copyFiles(project: Project)
        {
            val settingsFilesOpt = VfsUtil.findFile(Path(settings.pathToFiles), true)
            val ideaLocationOpt = VfsUtil.findFile(Path(project.basePath + "/.idea"), true)
            val application = ApplicationManager.getApplication()
            Utilities.nullSafeCall(settingsFilesOpt, ideaLocationOpt) { (settings, location) ->
                application.runWriteAction {
                    VfsUtil.copyDirectory(null, settings, location, null)
                }
            }
        }
    }
}