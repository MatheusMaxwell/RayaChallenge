This is a Kotlin Multiplatform project targeting Android, iOS.

RayaChallenge is a project created as a challenge for the company Raya.
An app was created that has 2 screens, one with the user's balance showing values in 4 different
currencies and another screen where it is possible to swap between these currencies.

* `/composeApp` is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - `commonMain` is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    `iosMain` would be the right folder for such calls.

* `/iosApp` contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform, 
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.

* `/common` Contains base and useful classes that can be used by any module in the application.
* `designsystem` Contains modules responsible for the visual part of the app, such as themes and components.
  - `component` Contains components that can be reused across the app.
  - `theme` Contains themes that can be used across the app.
* `feature` Contains modules responsible for the business logic of the app.
  - `balance` Contains the business logic for the balance screen.
  - `login` Contains the business logic for the login screen. 
  - `splash` Contains the business logic for the splash screen.


## Architecture used MVVM with clean architecture

- feature 
  - featureA
    - di (Dependency injection file)
    - impl (Implementation of the feature)
      - data
        - datasource
          - remote
          - local
        - mapper
        - repository (implementation of repository)
      - domain
        - model
        - repository (interfaces of repository)
        - usecase
      - presentation
        - ui
        - viewmodel
    - navigation (Navigation file)

## Android
To run the app on Android devices, open Android Studio, let it synchronize all apps and at the top 
where there is the play button, select the Android device and the ComposeApp variant and run it.

## iOS
To run the iOS app, you need to have an Apple developer account to sign the app before running it. 
recommend opening the iosApp.xcodeproj file inside the iosApp folder in Xcode and selecting the 
developer account in the Signing & Capabilities section.
Execute: 
  - ./gradlew clean
  - ./gradlew :composeApp:syncFramework
  - ./gradlew buildXcFramework
These commands are to generate files needed to run iOS. After that, in the upper right corner, 
to the left of the play button, select the iosApp variant and run the app. If a Run/Debug 
configuration window opens, you probably don't have any iOS device selected. So select one, 
apply, and run it again.

Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…