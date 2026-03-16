# Notes App

A clean and modern notes application for Android built with Kotlin, Room database, and MVVM architecture.

## Features

- Create, edit, and delete notes
- Search notes by title or content
- Pin important notes to the top
- Color labels for visual organization
- Sort notes by date (newest/oldest first)
- Swipe to delete with undo support
- Material Design 3 UI

## Architecture

The app follows the **MVVM (Model-View-ViewModel)** architecture pattern:

```
├── data
│   ├── local          # Room database, DAO
│   ├── model          # Data entities
│   └── repository     # Repository layer
└── ui
    ├── notes          # Notes list screen
    └── editor         # Note editor screen
```

## Tech Stack

- **Language:** Kotlin
- **Database:** Room Persistence Library
- **Architecture:** MVVM with LiveData
- **UI:** Material Design 3, RecyclerView
- **Navigation:** Jetpack Navigation Component
- **Async:** Kotlin Coroutines
- **Min SDK:** 24 (Android 7.0)
- **Target SDK:** 34 (Android 14)

## Building

1. Clone the repository
2. Open in Android Studio (Hedgehog or later)
3. Sync Gradle and run on device/emulator

## License

MIT License
