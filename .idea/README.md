[README.md](https://github.com/user-attachments/files/34284243/README.md)

# ProjectSync

ProjectSync is a native Android prototype for coordinating a university Final Year Project (FYP) portal. It provides role-based screens for students, supervisors, the PMO, and administrators, along with a student performance prediction feature.

## Features

- Sign in and register with a project role.
- Role-specific dashboards for Student, Supervisor, PMO, and Admin accounts.
- Student tools for submitting a proposal, viewing announcements, and requesting a supervisor.
- Supervisor tools for reviewing student requests, viewing sample group information, and posting announcements.
- PMO screens for sample project and progress overviews, announcements, and student performance prediction.
- Admin user management for adding, removing, activating/deactivating, and resetting accounts.
- A shared announcements screen and profile sign-out action.

### Student performance prediction

The PMO prediction screen accepts a student's study time, previous failures, absences, and G1/G2 grades. It sends these values to a prediction service through Retrofit with Gson conversion, then displays the returned prediction or an error message.

The prediction feature depends on the external service being reachable. Its URL is currently configured in `PerformancePredictionActivity.java`; replace the current ngrok tunnel address with the stable URL for the deployed prediction API before relying on it.

## Technology

- Java Android application with XML layouts.
- Android Gradle Plugin 8.2.0.
- Android SDK compile/target 34; minimum Android API 24.
- AndroidX AppCompat, Material Components, ConstraintLayout, RecyclerView, and CardView.
- Retrofit and Gson for prediction API requests.

## Open and run

1. Install Android Studio with JDK 17 and Android SDK Platform 34.
2. Open the `ProjectSync` project root (the folder containing `settings.gradle`) in Android Studio.
3. Check `gradle/wrapper/gradle-wrapper.properties`: the included wrapper currently points to a machine-specific local Gradle installation. For a portable setup with Android Gradle Plugin 8.2.0, set its `distributionUrl` to:

   ```properties
   distributionUrl=https\://services.gradle.org/distributions/gradle-8.2-bin.zip
   ```

4. Sync the project, select the `app` run configuration, and launch it on an emulator or a connected device running Android 7.0 (API 24) or newer.

You can also build a debug APK from the project root after the wrapper is configured:

```powershell
.\gradlew.bat assembleDebug
```

The APK is written to `app/build/outputs/apk/debug/app-debug.apk`.

## Project layout

```text
ProjectSync/
├── app/
│   └── src/main/
│       ├── java/com/example/projectsync/
│       │   ├── adapter/
│       │   ├── data/
│       │   ├── model/
│       │   │   ├── PredictionRequest.java
│       │   │   └── PredictionResponse.java
│       │   ├── ui/
│       │   │   ├── admin/
│       │   │   ├── auth/
│       │   │   ├── pmo/
│       │   │   │   └── PerformancePredictionActivity.java
│       │   │   ├── student/
│       │   │   └── supervisor/
│       │   └── ApiService.java
│       ├── res/
│       │   └── layout/activity_performance_prediction.xml
│       └── AndroidManifest.xml
├── gradle/wrapper/
├── build.gradle
├── settings.gradle
└── gradle.properties
```

## Current prototype limitations

- Users, announcements, and supervisor requests are stored in memory by `DataManager`; changes are lost when the app process is recreated.
- Some dashboard project and group information is hard-coded for demonstration.
- Registration currently lets the user choose a role, including Admin. Authentication and passwords are local prototype logic, not production security.
- Prediction results depend on the external prediction API and an active network connection.
- Connect a persistent backend, hash passwords, validate roles and permissions on the server, and remove embedded credentials before using this app with real accounts or data.
