<p align="center">
  <h1 align="center">Notes App</h1>
  <p align="center">A clean and modern notes application for Android built with Kotlin, Room, and MVVM architecture.</p>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Kotlin-1.9-7F52FF?style=flat&logo=kotlin&logoColor=white" alt="Kotlin">
  <img src="https://img.shields.io/badge/Android-7.0+-3DDC84?style=flat&logo=android&logoColor=white" alt="Android">
  <img src="https://img.shields.io/badge/Material_Design-3-757575?style=flat&logo=materialdesign&logoColor=white" alt="Material Design 3">
  <img src="https://img.shields.io/badge/Room-2.6-4285F4?style=flat&logo=android&logoColor=white" alt="Room">
  <img src="https://img.shields.io/badge/Architecture-MVVM-FF6F00?style=flat" alt="MVVM">
  <img src="https://img.shields.io/badge/License-MIT-green?style=flat" alt="License">
</p>

---

## Features

- **Create, Edit, Delete** — full CRUD operations for notes
- **Search** — find notes by title or content
- **Pin Notes** — pin important notes to the top of the list
- **Color Labels** — assign colors for visual organization
- **Sorting** — sort by newest or oldest first
- **Swipe to Delete** — swipe gesture with undo support
- **Material Design 3** — modern, clean Android UI

## Architecture

The app follows the **MVVM (Model-View-ViewModel)** pattern with a repository layer for clean data access:

```
┌──────────────────┐
│       View       │  Fragments + XML layouts
├──────────────────┤
│    ViewModel     │  LiveData, business logic
├──────────────────┤
│   Repository     │  Single source of truth
├──────────────────┤
│    Room DAO      │  Database queries (Coroutines)
└──────────────────┘
```

## Building

1. Clone the repository:
   ```bash
   git clone https://github.com/qorexdev/kotlin-notes-app.git
   ```
2. Open in **Android Studio** (Hedgehog 2023.1 or later)
3. Sync Gradle and run on a device or emulator

### Requirements

- Android Studio Hedgehog+
- JDK 17
- Min SDK 24 (Android 7.0)
- Target SDK 34 (Android 14)

## Project Structure

```
kotlin-notes-app/
├── app/
│   ├── build.gradle.kts
│   └── src/main/
│       ├── AndroidManifest.xml
│       ├── java/com/qorexdev/notesapp/
│       │   ├── data/
│       │   │   ├── local/
│       │   │   │   ├── NoteDao.kt           # Room DAO interface
│       │   │   │   └── NoteDatabase.kt      # Room database
│       │   │   ├── model/
│       │   │   │   └── Note.kt              # Note entity
│       │   │   └── repository/
│       │   │       └── NoteRepository.kt    # Repository layer
│       │   └── ui/
│       │       ├── MainActivity.kt          # Host activity
│       │       ├── notes/
│       │       │   ├── NotesFragment.kt     # Notes list screen
│       │       │   ├── NotesViewModel.kt    # List view model
│       │       │   └── NoteAdapter.kt       # RecyclerView adapter
│       │       └── editor/
│       │           ├── EditorFragment.kt    # Note editor screen
│       │           └── EditorViewModel.kt   # Editor view model
│       └── res/
│           ├── layout/                      # XML layouts
│           ├── navigation/nav_graph.xml     # Navigation graph
│           ├── menu/                        # Toolbar menus
│           └── values/                      # Colors, strings, themes
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
└── README.md
```

## Tech Stack

| Component | Technology |
|---|---|
| Language | Kotlin |
| Database | Room 2.6 |
| Architecture | MVVM + Repository |
| UI | Material Design 3, ViewBinding |
| Navigation | Jetpack Navigation Component |
| Async | Kotlin Coroutines |
| Lifecycle | ViewModel + LiveData |

## License

MIT

---

<p align="center">
  <sub>developed by <a href="https://github.com/qorexdev">qorex</a></sub>
  <br>
  <sub>
    <a href="https://github.com/qorexdev">GitHub</a> · <a href="https://t.me/qorexdev">Telegram</a>
  </sub>
</p>
