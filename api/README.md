# Backend

## Requirements
For building and running the application you need:

- [JDK 17](https://www.oracle.com/java/technologies/downloads/#java17)
- [Maven 3](https://maven.apache.org/download.cgi)
- Container in Docker must be active

## Running the application locally
To run the project, execute the `main` method in the `eshop.src.main.java.com.pvp.eshop.EshopApplication`
class from your IDE.

## Checkstyle static analysis tool
### Intellij:
To add Checkstyle, open `File>Settings>Plugins` and download `Checkstyle` plugin,
apply it. Then to add local configuration file open `File>Settings>Tools>Checkstyle`
and press `+` under the `Configuration file` section.
Press browse and select `checkstyle.xml` file and write a short description,
click `Next`, `Finish` and enable it in the `Active` column. Restart IDE.
Over the `Configuration file` section you can enable `Treat Checkstyle errors as warnings`.

### Visual Studio Code:
To add Checkstyle, go to `Extensions` and install `Checkstyle for Java` plugin.
To add local configuration right click on `checkstyle.xml` file and click
`Set the Checkstyle Configuration File`. In `.vscode->settings.json` add `"java.checkstyle.version": "8.43"`.

### Mock data
For testing purposes, you can login with one of these accounts:

1) Email: pirmas@gmail.com, Password: pirmas
2) Email: antras@gmail.com, Password: antras
3) Email: trecia@gmail.com, Password: trecia

Nr. 1 and Nr 2. are matched