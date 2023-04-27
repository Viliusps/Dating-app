# Running your React Native application

Install the Expo Go app on your iOS or Android phone
and connect to the same wireless network as your computer.
On Android, use the Expo Go app to scan the QR code from your terminal to open your project.
On iOS, use the built-in QR code scanner of the default iOS Camera app.

Go to `ui` directory and use `npm i` to get the latest modules.
Then from the same directory use `npx expo start`.

# Formatting

### Visual Studio Code:
Go to Extensions and install Prettier. After installing, it is recommended to restart the IDE. To automatically format document open settings menu by tapping “Command + ,(comma)” if you use a Mac. Click on “Control + ,(comma)”, if you are a Windows user. Go to the search bar and input “Editor: Format on Save.” and ensure it has a checkmark.

For those trying to quickly change Prettier settings for VS Code. Here are the steps:

Go to FILE -> PREFERENCES -> SETTINGS. (VS Code Menus) Settings window should open. Above (Top) there is a search. Type "Prettier" You should see the available Prettier settings.

### Intellij:
In the Settings dialog (Ctrl+Alt+S), go to Languages & Frameworks | JavaScript | Prettier, and from the Prettier package list, select the prettier installation to use.
Turn on the 'On save' checkbox to automatically format the code after save.
