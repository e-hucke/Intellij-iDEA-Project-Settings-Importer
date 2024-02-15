package com.eliashucke.projectsettingsimporter.helper

class Utilities
{
    companion object
    {
        const val IMPORT_POPUP_STATE = "com.eliashucke.projectsettingsimporter.importpopupshown"

        fun <T : Any> nullSafeCall(vararg args: T?, func: (List<T>) -> Unit)
        {
            if (args.all { it != null })
            {
                func(args.filterNotNull())
            }
        }
    }
}