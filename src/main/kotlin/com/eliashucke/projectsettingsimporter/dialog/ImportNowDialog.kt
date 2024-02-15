package com.eliashucke.projectsettingsimporter.dialog

import com.eliashucke.projectsettingsimporter.helper.SettingsImporter
import com.eliashucke.projectsettingsimporter.helper.Utilities
import com.eliashucke.projectsettingsimporter.settings.ApplicationSettingsState
import com.intellij.ide.util.PropertiesComponent
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.event.ActionEvent
import javax.swing.*

class ImportNowDialog(private var project: Project? = null) : DialogWrapper(true)
{
    private val disableCheckBox = JCheckBox("Disable Popup Permanently")

    init
    {
        title = "Settings Importer: New Project"
        init()
    }

    override fun createCenterPanel(): JComponent
    {
        val text = JLabel("Do You Want to Import Your Settings Now?")

        val dialogPanel = JPanel(BorderLayout())
        dialogPanel.preferredSize = Dimension(400, 120)
        dialogPanel.add(text, BorderLayout.CENTER)
        dialogPanel.add(disableCheckBox, BorderLayout.SOUTH)
        return dialogPanel
    }

    private fun updateSettings()
    {
        ApplicationSettingsState.instance.disablePopup = disableCheckBox.isSelected
        project?.let {
            PropertiesComponent.getInstance(it)
                .setValue(Utilities.IMPORT_POPUP_STATE, true)
        }
    }

    open class ImportActionButton(text: String, private var action: () -> Unit) : AbstractAction(text)
    {
        override fun actionPerformed(e: ActionEvent?)
        {
            action()
        }
    }

    override fun createActions(): Array<Action>
    {
        return arrayOf(
            ImportActionButton("Import!") {
                super.doOKAction()
                updateSettings()
                SettingsImporter.performSettingsImport(project)
            },
            ImportActionButton("Cancel Import") {
                super.doCancelAction()
                updateSettings()
            }
        )
    }
}