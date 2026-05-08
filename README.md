# Ponto+

Android time-clock app for TELUS Digital Brasil, built with Jetpack Compose and Material Design 3.

## Features

- **Login** — SSO corporate flow (primary) or email/password, with biometric option
- **Home** — live clock, one-tap "Bater ponto" button, today's Entrada/Saída timeline, worked hours and bank balance at a glance
- **Histórico** — weekly/monthly punch history, expandable day rows with the full 4-punch timeline, summary card with extras and delays
- **Banco de horas** — current balance, active cycle progress bar, credit/debit movement list
- **Perfil** — user info, quick-info chips, account settings, logout

## Tech stack

| Layer | Choice |
|---|---|
| Language | Kotlin 2.2 |
| UI | Jetpack Compose + Material 3 |
| Navigation | Navigation Compose 2.9 |
| State | `ViewModel` + `StateFlow` |
| Build | AGP 9.2 / Gradle |
| Min SDK | 26 (Android 8.0) |
| Target SDK | 36 |

## Project structure

```
app/src/main/java/com/telusdigital/pontomais/
├── MainActivity.kt              # Edge-to-edge entry point
├── navigation/
│   └── PontoNavHost.kt          # NavHost + route definitions
└── ui/
    ├── components/              # Reusable design-system components
    │   ├── GradientCard.kt      # CaradonnaBrush hero cards
    │   ├── HeroStatusCard.kt
    │   ├── PontoBottomNavBar.kt
    │   ├── PontoButton.kt
    │   ├── PontoFilterChip.kt
    │   ├── PontoTextField.kt
    │   ├── PontoTopAppBar.kt
    │   ├── PunchRow.kt
    │   ├── DayRow.kt
    │   ├── MovementRow.kt
    │   ├── ProfileMenuRow.kt
    │   ├── QuickInfoCell.kt
    │   ├── ScheduleCard.kt
    │   ├── StatCard.kt
    │   └── WeekSummaryCard.kt
    ├── screens/
    │   ├── LoginScreen.kt
    │   ├── HomeScreen.kt
    │   ├── HomeViewModel.kt     # Punch cycle logic, worked-time calculation
    │   ├── HistoryScreen.kt
    │   ├── BankScreen.kt
    │   └── ProfileScreen.kt
    └── theme/
        ├── Color.kt             # TELUS Digital brand palette
        ├── Theme.kt
        └── Type.kt
```

## Getting started

**Requirements:** Android Studio Meerkat (or later), JDK 11+.

```bash
git clone <repo-url>
cd PontoMais
./gradlew assembleDebug
```

Install on a connected device or emulator:

```bash
./gradlew installDebug
```

## Design tokens

Brand colors are defined in `ui/theme/Color.kt` and follow the TELUS Digital palette:

| Token | Value | Usage |
|---|---|---|
| `TelusPurple` | `#4B286D` | Primary brand color, accents |
| `Orchid` | `#A020FD` | Gradient start (Caradonna) |
| `Forest` | `#007F4A` | CTAs, trust indicators |
| `Juniper` | `#BAF29E` | Wordmark accent, working status dot |
| `Pearl` | `#FCFDFB` | Screen backgrounds, card surfaces |
| `Obsidian` | `#222220` | Primary text |
| `Slate` | `#595956` | Secondary text |

The `CaradonnaBrush` (Orchid → TelusPurple diagonal gradient) is used for all hero cards — login, home, history summary, bank balance, and profile header.
