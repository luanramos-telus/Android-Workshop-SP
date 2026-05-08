# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build & Run

```bash
./gradlew assembleDebug          # Build debug APK
./gradlew installDebug           # Build and install on connected device/emulator
./gradlew test                   # Run unit tests
./gradlew connectedDebugAndroidTest  # Run instrumented tests (requires device)
./gradlew lint                   # Run lint
./gradlew lintFix                # Auto-fix safe lint issues
```

Single test class: `./gradlew testDebugUnitTest --tests "com.telusdigital.pontomais.SomeTest"`

## Architecture

**Single-module MVVM app — no DI framework, no networking layer, no database yet.** All data is mocked in ViewModels.

- `MainActivity` enables edge-to-edge and hosts a single `PontoNavHost` (Navigation Compose)
- Five screens: `login`, `home` (default), `history`, `bank`, `profile`
- `HomeViewModel` is the only ViewModel; it holds punch records in a `mutableListOf()` (no persistence) and exposes `HomeUiState` via `StateFlow`
- A 30-second coroutine timer in `HomeViewModel` auto-refreshes worked hours when clocked in

## Code layout

```
app/src/main/java/com/telusdigital/pontomais/
├── MainActivity.kt
├── navigation/PontoNavHost.kt
└── ui/
    ├── screens/          # One file per screen + HomeViewModel
    ├── components/       # 15 reusable composables (each has @Preview)
    └── theme/            # Color.kt, Theme.kt, Type.kt
```

## Design system

- **Palette**: TELUS Digital brand — `TelusPurple`, `Orchid`, `Forest`, etc. defined in `Color.kt`
- **Caradonna gradient**: diagonal `Orchid → TelusPurple` brush used for hero cards (`GradientCard`)
- **Material 3**: full `lightColorScheme` + `darkColorScheme` in `Theme.kt`; use semantic color tokens, not raw palette values in components

## Key data models

Defined inside `HomeViewModel.kt` and component files — there is no separate domain/data layer yet:

| Model | Where | Notes |
|---|---|---|
| `PunchRecord` | HomeViewModel | type, time (LocalTime), location, synced |
| `HomeUiState` | HomeViewModel | punches, workedToday, hoursBalance |
| `PunchType` | HomeViewModel | enum: In, Pause, Back, Out |
| `DayEntry` / `Movement` / `WeekSummary` | Screen files | Display-only models |

## SDK targets

- **minSdk**: 26 · **targetSdk / compileSdk**: 36
- Kotlin 2.2.10, Compose BOM 2026.02.01, Navigation Compose 2.9.0, AGP 9.2.1
