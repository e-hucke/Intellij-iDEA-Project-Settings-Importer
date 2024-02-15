package com.eliashucke.projectsettingsimporter.settings

import com.intellij.ide.DataManager
import com.intellij.openapi.actionSystem.PlatformCoreDataKeys
import com.intellij.openapi.fileChooser.FileChooser
import com.intellij.openapi.fileChooser.FileChooserDescriptor
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.util.ui.FormBuilder
import com.intellij.util.ui.JBUI
import java.awt.BorderLayout
import javax.swing.*
import javax.swing.border.CompoundBorder
import javax.swing.border.EtchedBorder

class ApplicationSettingsComponent
{
    private val importIdeaFilesCheckBox = JCheckBox("Import /.idea settings")
    private val pathToSettingsInput = JTextField("", 2)
    private val pathButton = JButton("Choose Path")
    private val disablePopupCheckBox = JCheckBox("Disable import now popup")

    private val importOnSaveActionsCheckBox = JCheckBox("Import actions on save")
    private val reformatCodeCheckBox = JCheckBox("Reformat code")
    private val optimizeImportsCheckBox = JCheckBox("Optimize imports")
    private val rearrangeCodeCheckBox = JCheckBox("Rearrange code")
    private val runCodeCleanUpCheckBox = JCheckBox("Run code cleanup")

    var settingsPanel: JPanel

    init
    {
        pathButton.addActionListener { browseFiles() }
        importIdeaFilesCheckBox.addActionListener { setCheckBoxEnabled() }
        importOnSaveActionsCheckBox.addActionListener { setCheckBoxEnabled() }

        val ideaFilePanel = JPanel(BorderLayout())
        ideaFilePanel.border = CompoundBorder(EtchedBorder(), JBUI.Borders.empty(10))
        importIdeaFilesCheckBox.border = JBUI.Borders.emptyBottom(15)
        ideaFilePanel.add(importIdeaFilesCheckBox, BorderLayout.NORTH)

        val fileInputBox = Box.createHorizontalBox()
        fileInputBox.border = EtchedBorder()
        fileInputBox.add(pathToSettingsInput)
        fileInputBox.add(pathButton)
        ideaFilePanel.add(fileInputBox, BorderLayout.SOUTH)

        val onSaveActionPanel = JPanel(BorderLayout())
        onSaveActionPanel.border = CompoundBorder(EtchedBorder(), JBUI.Borders.empty(10))
        importOnSaveActionsCheckBox.border = JBUI.Borders.emptyBottom(15)
        onSaveActionPanel.add(importOnSaveActionsCheckBox, BorderLayout.NORTH)
        onSaveActionPanel.add(JSeparator(1), BorderLayout.WEST)

        val actionsBox = Box.createVerticalBox()
        actionsBox.border = JBUI.Borders.emptyLeft(5)
        actionsBox.add(reformatCodeCheckBox)
        actionsBox.add(optimizeImportsCheckBox)
        actionsBox.add(rearrangeCodeCheckBox)
        actionsBox.add(runCodeCleanUpCheckBox)
        onSaveActionPanel.add(actionsBox, BorderLayout.CENTER)

        disablePopupCheckBox.border = JBUI.Borders.emptyTop(20)

        settingsPanel = FormBuilder.createFormBuilder()
            .addComponent(ideaFilePanel)
            .addComponent(onSaveActionPanel)
            .addComponent(disablePopupCheckBox)
            .panel
    }

    fun setCheckBoxEnabled()
    {
        this.pathToSettingsInput.isEnabled = importIdeaFiles
        this.pathButton.isEnabled = importIdeaFiles
        this.reformatCodeCheckBox.isEnabled = onSaveActions
        this.rearrangeCodeCheckBox.isEnabled = onSaveActions
        this.runCodeCleanUpCheckBox.isEnabled = onSaveActions
        this.optimizeImportsCheckBox.isEnabled = onSaveActions
    }

    private fun browseFiles()
    {
        val dataContext = DataManager.getInstance().dataContextFromFocusAsync.blockingGet(2000)
        val project = dataContext?.getData(PlatformCoreDataKeys.PROJECT)
        val localFile = LocalFileSystem.getInstance().findFileByPath(pathToSettings)
        val descriptor = FileChooserDescriptor(false, true, false, false, false, false)
        descriptor.isHideIgnored = false
        FileChooser.chooseFile(descriptor, project, localFile)?.let {
            pathToSettings = it.path
        }
    }

    var pathToSettings: String
        get() = pathToSettingsInput.text
        set(text)
        {
            pathToSettingsInput.text = text
        }

    var disablePopup: Boolean
        get() = disablePopupCheckBox.isSelected
        set(isSelected)
        {
            disablePopupCheckBox.isSelected = isSelected
        }
    var reformatCode: Boolean
        get() = reformatCodeCheckBox.isSelected
        set(isSelected)
        {
            reformatCodeCheckBox.isSelected = isSelected
        }
    var optimizeImports: Boolean
        get() = optimizeImportsCheckBox.isSelected
        set(isSelected)
        {
            optimizeImportsCheckBox.isSelected = isSelected
        }
    var rearrangeCode: Boolean
        get() = rearrangeCodeCheckBox.isSelected
        set(isSelected)
        {
            rearrangeCodeCheckBox.isSelected = isSelected
        }

    var importIdeaFiles: Boolean
        get() = importIdeaFilesCheckBox.isSelected
        set(isSelected)
        {
            importIdeaFilesCheckBox.isSelected = isSelected
        }
    var runCodeCleanup: Boolean
        get() = runCodeCleanUpCheckBox.isSelected
        set(isSelected)
        {
            runCodeCleanUpCheckBox.isSelected = isSelected
        }
    var onSaveActions: Boolean
        get() = importOnSaveActionsCheckBox.isSelected
        set(isSelected)
        {
            importOnSaveActionsCheckBox.isSelected = isSelected
        }
}