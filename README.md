# NIT3213 Final Assignment - Android App

## Overview

This project is an Android application developed as part of the NIT3213 Final Assignment. The app includes features for user authentication, displaying a list of entities, and viewing detailed information about each entity. It integrates with the `vu-nit3213-api` for authentication and data retrieval.

## Features

- **Login Screen**: Allows users to authenticate with a username and password.
- **Dashboard Screen**: Displays a list of sports entities retrieved from the API.
- **Details Screen**: Shows detailed information about a selected entity, including sport name, player count, field type, and whether it is an Olympic sport.

## Technologies Used

- **Android Studio**: Integrated development environment (IDE) for Android development.
- **Kotlin**: Programming language used for app development.
- **Retrofit**: HTTP client used for network operations.
- **Kotlin Coroutines**: For handling asynchronous tasks.
- **Dagger Hilt**: Dependency injection framework for managing dependencies.
- **RecyclerView**: For displaying lists of items efficiently.

## Installation

1. **Clone the repository**:
   ```bash
   git clone https://github.com/Jeremietuyau/Final_Assessment
2.Open the project in Android Studio:

Launch Android Studio.
Select Open an existing project.
Navigate to the cloned repository directory and select it.

3.Sync the project:

Click File > Sync Project with Gradle Files.

4.Build and run:

Click the Run button (green triangle) or press Shift + F10 to build and run the app on an emulator or a physical device.

Configuration


API Base URL: Ensure the base URL for the API is configured correctly in your Retrofit client.
API Key/Token: Add any necessary API keys or tokens in the configuration files if required by the API.

Usage

Login: Open the app and enter your credentials to log in. You will be redirected to the Dashboard screen upon successful login.
Dashboard: View the list of sports entities. Each item in the list represents a different sport.
Details: Tap on an entity to view its detailed information, including sport name, player count, field type, and whether it is an Olympic sport.
