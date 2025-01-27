# Trip Drop
Trip Drop is a mobile application designed to facilitate the delivery of goods between users. If a user needs a product from a specific location, they can post a request on the app with a proposed reward. Other users who can fulfill the delivery can accept the task, and upon successful completion, they receive the reward. This app aims to create a community-driven delivery service that benefits both requesters and deliverers.

## Features
1. Enable users to post requests for goods from specific locations.
2. Allow users to accept delivery tasks and receive rewards upon completion.
3. Ensure a secure and efficient transaction process between users.

## Package Structure
* `data` : contains data class.
* `di` : Hilt Modules.
* `ui`
    * `nav`: Contains app navigation and destinations.
    * `presentations`: Contains UI.
    * `theme`: Material3 theme.

## Build With
[Kotlin](https://kotlinlang.org/):
As the programming language.

[Jetpack Compose](https://developer.android.com/jetpack/compose) :
To build UI.

[Jetpack Navigation](https://developer.android.com/jetpack/compose/navigation) :
For navigation between screens and deep linking.

[Firebase](https://firebase.google.com/docs/build) :
To track user's running activity such as speed, distance and path on the map.

[Hilt](https://developer.android.com/training/dependency-injection/hilt-android) :
For injecting dependencies.

[Coil](https://coil-kt.github.io/coil/compose/) :
To load image asynchronously.

## Architecture
This app follows MVVM architecture, Uni Directional Flow (UDF) pattern and Single architecture
pattern.

![image](https://github.com/user-attachments/assets/8e4af5e7-cddf-4f42-9fdd-e5212956f07a)


## Installation
Simple clone this app and open in Android Studio.
 `will be releasing soon`

## Contributing
1. Fork the repository.
2. Create a new branch (git checkout -b feature-branch).
3. Commit your changes (git commit -am 'Add new feature').
4. Push to the branch (git push origin feature-branch).
5. Create a new Pull Request.

