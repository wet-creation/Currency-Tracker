# 📱 Currency Tracker

Currency Tracker is a modern Android application built with Jetpack Compose for tracking fiat and cryptocurrency values. It supports user authentication, favorites, notifications, multilingual interface, and Firebase integration for push messaging.

## ✨ Features

- 🔐 User authentication (Login / Register)
- 💱 View fiat and crypto currency lists
- ⭐ Add favorites for quick access
- 📊 Currency calculator
- 🔔 Notification alerts for selected currencies
- 🌐 Multi-language support
- 🎨 Material 3 design with Jetpack Compose
- ☁️ Firebase Cloud Messaging (push notifications)
- 📲 Adaptive UI for multiple screen sizes

## 🧱 Project Structure

```bash
Currency-Tracker/
├── app/                    # Main application code
│   ├── src/                # Source code
│   ├── build.gradle.kts    # App-level Gradle configuration
│   └── google-services.json# Firebase config
├── build.gradle.kts        # Root Gradle config
├── settings.gradle.kts     # Gradle project settings
├── gradle.properties       # Project-wide Gradle properties
├── gradlew, gradlew.bat    # Gradle wrapper scripts
└── local.properties        # Local machine-specific configs (not committed)
```

## 🛠️ Tech Stack

- **Kotlin**
- **Jetpack Compose**
- **Navigation Compose**
- **Hilt (Dagger) for Dependency Injection**
- **Firebase Cloud Messaging**
- **DataStore for persistent storage**
- **MVVM Architecture**
- **Kotlin Coroutines & Flow**

## 🚀 Getting Started

### Prerequisites

- Android Studio Giraffe or higher
- JDK 17
- Firebase project setup with `google-services.json`

### Installation

1. Clone the repo:
    ```bash
    git clone https://github.com/your-username/currency-tracker.git
    ```

2. Add your local Firebase config file:
    - Place `google-services.json` in `app/` directory.

3. (Optional) Create or update `local.properties`:
    ```properties
    sdk.dir=path_to_android_sdk
    ```

4. Build and run:
    - Open in Android Studio and click **Run**.


## 📦 Build & Release

To build the release APK:
```bash
./gradlew assembleRelease
```

The APK will be located in:
```
app/release/app-release.apk
```
