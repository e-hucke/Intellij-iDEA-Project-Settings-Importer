package com.eliashucke.projectsettingsimporter.dialog

import com.intellij.openapi.ui.DialogWrapper
import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.JPanel

class PathNotSetDialog : DialogWrapper(true)
{
    init
    {
        title = "Error Copying Files"
        init()
    }

    override fun createCenterPanel(): JComponent
    {
        val dialogPanel = JPanel(BorderLayout())
        val label = JLabel("Please set a correct path in Settings -> Tools -> Project Settings Importer")
        label.preferredSize = Dimension(100, 80)
        dialogPanel.add(label, BorderLayout.CENTER)
        return dialogPanel
    }
}