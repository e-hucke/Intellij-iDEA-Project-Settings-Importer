
# Intellij IDEA Settings Importer

Some IntelliJ Plugins/IntelliJ Settings use project specific Settings even though you might want these to be the same for all of your projects.  
This Plugin works by copying .xml settings from a folder you specified into the /.idea folder in your current project and serializing/editing/deserializing workspace.xml for Actions On Save.  



## Installation

Download the latest release or clone this repository and build it yourself.  
Open the plugins page in IntelliJ IDEA. Click on the 3 Dots next to Installed at the top -> Install from Disk -> Then select the plugin .jar file.

    
## Usage

The Plugin can be configured under Settings -> Tools -> Project Settings Importer.  
<img width="998" alt="importersettings" src="https://github.com/e-hucke/Intellij-iDEA-Project-Settings-Importer/assets/121632203/68c7f2df-1657-4be5-b511-407a55ff29ec">
  
Open any project that has project settings that you want to import. From ./idea copy the .xml files/folders you need to have in all of your projects into a different folder. Then enable "Import /.idea settings" and set the path to this folder.  
You can also choose to import Action On Save by enabling "Import actions on save" and selecting the actions you want to use.  
  
When importing/creating/opening a project that hasn't been opened yet with this plugin installed a popup will show up asking you if you want to import your settings. This behaviour can be disabled in settings or directly in the popup.


## Todo
- Add Icon
- Maybe rework importing Action On Save:
    - Rearrange Code and Run Code Clean Up are currently set using PropertiesComponent.getInstance(project).setValue(...)
    - This isn't possible for the other actions because they are saved directly as components in workspace.xml and I couldn't find an extension point to achieve this. The current (kinda hacky) workaround is serializing the xml and editing it manually.
    - Not sure if this would get approved on the plugin marketplace

## License

All of the code is dual-licensed under either:
* MIT License ([LICENSE-MIT](LICENSE-MIT) or [http://opensource.org/licenses/MIT](http://opensource.org/licenses/MIT))
* Apache License, Version 2.0 ([LICENSE-APACHE](LICENSE-APACHE) or [http://www.apache.org/licenses/LICENSE-2.0](http://www.apache.org/licenses/LICENSE-2.0))
Reasons using a dual-license can be found [here](https://github.com/bevyengine/bevy/issues/2373)

## Contributing

Contributions are always welcome!


