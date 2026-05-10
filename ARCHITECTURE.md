# Ponto+ Architecture

Single-module Android app (`:app`) — TELUS Digital Brasil time-clock. Pure Jetpack Compose, no fragments, no XML layouts. Read this file instead of re-scanning sources to get oriented.

## Stack & build

| Item | Version / value |
|---|---|
| Language | Kotlin 2.2.10 |
| AGP | 9.2.1 |
| Min SDK | 26 (Android 8.0) |
| Target / compile SDK | 36 (minor 1) |
| Java compat | 11 |
| UI | Jetpack Compose + Material 3 (`compose-bom 2026.02.01`) |
| Navigation | `androidx.navigation:navigation-compose 2.9.0` |
| State | `ViewModel` + `kotlinx.coroutines` `StateFlow` |
| Lifecycle | `androidx-lifecycle 2.10.0` (`lifecycle-runtime-ktx`, `lifecycle-viewmodel-compose`) |
| Icons | `material-icons-extended` |
| Tests | JUnit 4, Espresso, Compose UI test |
| Package | `com.telusdigital.pontomais` |
| App label | `Ponto+` |

Build files: root `build.gradle.kts`, `settings.gradle.kts`, `gradle/libs.versions.toml`. Catalog uses two plugins: `android-application` and `kotlin-compose`. No KSP, Hilt, Room, Retrofit, or DI framework yet.

`AndroidManifest.xml` — single activity (`MainActivity`, exported, LAUNCHER), `Theme.PontoMais`, supports RTL, `allowBackup=true`, `data_extraction_rules` + `backup_rules` defined in `res/xml/`.

## Layered structure

```
app/src/main/java/com/telusdigital/pontomais/
├── MainActivity.kt                     edge-to-edge entry, hosts PontoNavHost in PontoMaisTheme
├── navigation/PontoNavHost.kt          NavHost + Routes object (string consts) + tab→route mapper
└── ui/
    ├── theme/                          design tokens (see DESIGN_SYSTEM.md)
    │   ├── Color.kt
    │   ├── Theme.kt                    Light + Dark M3 ColorScheme + PontoMaisTheme composable
    │   └── Type.kt                     HnTelusSa[Display] FontFamilies + full Typography scale
    ├── components/                     reusable design-system primitives (15 files)
    └── screens/                        feature screens + ViewModels
```

There is **no** `data/`, `domain/`, `repository/`, or `di/` package today. All feature state lives in screen-scoped ViewModels (only `HomeViewModel` exists; other screens use mock data inline). When real APIs land, the natural seams are a `data/` layer + repository injection into ViewModels.

## Entry point

`MainActivity.onCreate` calls `enableEdgeToEdge()` then `setContent { PontoMaisTheme { PontoNavHost() } }`. No splash, no DI graph, no auth gating outside the nav graph itself (start destination is `Login`).

## Navigation

`navigation/PontoNavHost.kt` — single `NavHost` with five routes declared as constants in `object Routes`:

| Route const | String | Screen | Notes |
|---|---|---|---|
| `Routes.Login` | `"login"` | `LoginScreen` | start destination; `onLogin → navigate(Home)` (does not pop login off the stack) |
| `Routes.Home` | `"home"` | `HomeScreen` | hosts `HomeViewModel` via `viewModel()` |
| `Routes.History` | `"history"` | `HistoryScreen` | mock data inline |
| `Routes.Bank` | `"bank"` | `BankScreen` | mock data inline |
| `Routes.Profile` | `"profile"` | `ProfileScreen` | logout pops back to Login with `popUpTo(Login){inclusive=true}` |

Tab switching uses `PontoTab` enum (in `components/PontoBottomNavBar.kt`) with a private `PontoTab.route()` extension in the nav file. Each non-Login screen receives `(onNavigate: (PontoTab) -> Unit, currentTab: PontoTab, onBack: () -> Unit)` — back goes through `navController.popBackStack()`. There is no Scaffold-level shared bottom bar; each screen renders its own `PontoBottomNavBar`. No deep links, no nav arguments, no nested graphs.

Known quirk: tapping bottom-nav tabs always calls `navigate(...)` without `launchSingleTop` or `popUpTo`, so the back stack grows. Worth fixing when the nav graph gets touched again.

## State management

Pattern is canonical Compose + ViewModel + StateFlow. Only `HomeViewModel` is implemented today; it's the template for future screen VMs.

### `HomeViewModel` (the reference example)

`ui/screens/HomeViewModel.kt`

- Holds an in-memory `MutableList<PunchRecord>` (private internal model with real `LocalTime`).
- Exposes `MutableStateFlow<HomeUiState>` → public `StateFlow<HomeUiState>` via `asStateFlow()`.
- `HomeUiState(punches: List<PunchEntry>, workedToday: String, hoursBalance: String)` — UI-shaped, time strings already formatted `HH:mm`.
- `punch()` toggles last-was-`In` ↔ `Out`, appends a new record with `synced=false`, then launches a 1.2 s coroutine that flips it to `synced=true` (simulates network sync).
- `init {}` starts a `viewModelScope` ticker: every 30 s, while `isWorking()`, calls `refreshState()` so the worked-time string keeps advancing.
- `workedMinutes()` walks pairs of In/Out punches, summing minutes. An open `In` segment is summed against `LocalTime.now()` for live counting.
- Hours-balance is currently a hard-coded `"+12:38"` string in the initial state — placeholder.
- `PunchRecord` carries `location: String = "Escritório · POA"` and `synced: Boolean = true`; `toEntry()` maps it to the UI `PunchEntry`.

### How screens consume state

```kotlin
val state by vm.uiState.collectAsState()
```

`HomeScreen` also keeps a separate `LaunchedEffect`-driven local clock string (`currentTime`) ticking every 60 s for the hero card display, independent of VM state.

Other screens (`History`, `Bank`, `Profile`, `Login`) declare their mock data as top-level `val`s or inside the composable. They are stateless w.r.t. domain logic; replace these inlined lists with VMs when wiring real data.

## Resources

`app/src/main/res/`

- `values/strings.xml` — all UI copy in Brazilian Portuguese, organized by screen (Login, Home, History, Bank, Profile, Reminder, Notification, Permission, Common). Greeting uses `%1$s` placeholder.
- `values/colors.xml`, `values/themes.xml` — minimal; the real palette/theme lives in `ui/theme/*.kt`.
- `font/` — `hn_telus_sa*` and `hn_telus_sa_display*` (4 + 3 weights). Referenced from `Type.kt` via `R.font.*`.
- `xml/backup_rules.xml`, `xml/data_extraction_rules.xml` — default scaffolding.
- `mipmap-anydpi/`, `drawable/` — launcher icons only.

Screens read copy via `stringResource(R.string.*)` (HomeScreen does this consistently). Component files often inline literal strings — refactor opportunity if i18n matters.

## File-by-file map

### `ui/components/` (15 files, all stateless except local UI state)

| File | What it is | Public API surface |
|---|---|---|
| `GradientCard.kt` | Caradonna gradient container | `CaradonnaBrush` (linear, Orchid→TelusPurple, ≈135°), `CaradonnaVerticalBrush`, `GradientCard(brush, cornerRadius, content)` |
| `HeroStatusCard.kt` | Big purple card (clock + status pill + "Bater ponto" button) | `HeroStatusCard(time, date, isWorking, onPunch)` — pulses Juniper dot when working |
| `PontoBottomNavBar.kt` | Material3 NavigationBar | `enum PontoTab { Home, History, Bank, Profile }` (label + icon + route) ; `PontoBottomNavBar(currentTab, onTabSelected)` |
| `PontoTopAppBar.kt` | M3 TopAppBar wrapper | `PontoTopAppBar(title, navigationIcon?, leadingContent?, onNavigationClick, actions)`; `NotificationIconButton(badgeCount)` |
| `PontoButton.kt` | Filled & outlined CTAs | `PontoButton(label, onClick, icon?, containerColor=TelusPurple, contentColor=White)` ; `PontoOutlinedButton(...)` |
| `PontoTextField.kt` | M3 TextField with `dark` variant for Login | `PontoTextField(value, onValueChange, label, leadingIcon?, isPassword, dark, keyboardOptions)` — handles password show/hide |
| `PontoFilterChip.kt` | M3 FilterChip with brand colors | `PontoFilterChip(label, selected, onClick)` |
| `PunchRow.kt` | One punch line (icon tile, type, time, sync state) | `enum PunchType { In, Pause, Back, Out }` (label/icon/iconColor/iconBg) ; `data class PunchEntry(type, time, location, synced)` ; `PunchRow(punch, showDivider)` |
| `DayRow.kt` | Expandable history row with 4-punch timeline | `enum DayStatus { Complete, Over, Short, Holiday }` ; `data class DayEntry(date, total, overtime, status, punches)` ; `DayRow(day)` (manages own `expanded` state with `remember`) |
| `MovementRow.kt` | Bank credit/debit list item | (bank screen) |
| `ProfileMenuRow.kt` | Profile settings row (icon + label + sub + chevron / switch) | (profile screen) |
| `QuickInfoCell.kt` | Small info chip with label/value | (profile header) |
| `ScheduleCard.kt` | Schedule summary card | (profile / home future use) |
| `StatCard.kt` | White card w/ overline + big number + sub + optional trend icon | `StatCard(overline, value, sub, trendIcon?, accentColor=TelusPurple, onClick?)` |
| `WeekSummaryCard.kt` | Gradient card for history weekly totals | (history screen) |

### `ui/screens/`

| File | Notes |
|---|---|
| `LoginScreen.kt` | Full-screen `CaradonnaVerticalBrush` background, dark `PontoTextField`s, biometric icon button, `onLogin: () -> Unit` callback. No actual auth call. |
| `HomeScreen.kt` | Scaffold with `PontoTopAppBar` (avatar leading, notif action), `PontoBottomNavBar`. Renders `HeroStatusCard` + 2x `StatCard` row + "Batidas de hoje" card showing only first-`In` and last-`Out` (full list lives behind "Ver histórico"). Local 60s `LaunchedEffect` ticker for the wall-clock; punch list & worked-time come from `HomeViewModel`. Avatar `"LR"` and greeting `"Luan"` are hardcoded — replace when user profile is wired. |
| `HomeViewModel.kt` | Documented above. |
| `HistoryScreen.kt` | Filter chips (week/month/custom) + `WeekSummaryCard` + list of `DayRow`s. Mock days inline. |
| `BankScreen.kt` | Hero gradient balance card, cycle progress, `MovementRow` list. Mock data inline. |
| `ProfileScreen.kt` | Gradient header with avatar + `QuickInfoCell` chips, then sectioned `ProfileMenuRow` lists. Hardcoded user "Luan Ramos / Engenheiro" in São Paulo (per recent commit). Logout via `onLogout` callback. |

## Data flow (today)

```
UI event (tap "Bater ponto")
   ↓
HomeScreen → vm.punch()                              (ViewModel call)
   ↓
HomeViewModel mutates `records: MutableList<PunchRecord>`
   ↓
_uiState.update { ... }                              (StateFlow emits)
   ↓
HomeScreen recomposes via collectAsState
```

There is no persistence. Killing the process clears all punches. Locations and "synced" booleans are simulated. Hours-balance is a constant.

## What's not here yet (intentional gaps to know about)

- No data/network layer — no Retrofit/Ktor/Apollo, no auth, no DTOs. Login button just navigates.
- No DI — wire Hilt or Koin when a repository appears. Today's `HomeViewModel` is constructed via plain `viewModel()` (no factory).
- No persistence — no Room/DataStore. Reminder settings, dark-mode toggle, etc., have UI only.
- No work scheduling — `strings.xml` has reminder/notification copy, but no `WorkManager` / `AlarmManager` / NotificationChannel registration is implemented.
- No error/loading UI primitives — add when networking lands.
- No tests yet — `:app` declares JUnit, Espresso, Compose UI test deps but `androidTest/` and `test/` source sets are empty.
- No analytics, crash reporting, or feature flags.
- Dark scheme is wired (`Theme.kt` has full `DarkColorScheme`) but no UI affordance toggles it yet — relies on system setting via `isSystemInDarkTheme()`.

## Conventions worth keeping

- Component composables take primitives + lambdas, never ViewModels — keeps them previewable. Each component has at least one `@Preview`.
- Colors are imported by name from `ui/theme/Color.kt`; do not hand-roll `Color(0x...)` in components.
- Text styles come from `MaterialTheme.typography.*`; `.copy(letterSpacing = ...)` is fine for fine-tuning.
- Strings: prefer `stringResource(R.string.*)` over hardcoded literals (HomeScreen is the exemplar).
- Locale: dates rendered with `Locale.forLanguageTag("pt-BR")`.
- No emoji in source. Comments are sparse and mostly cite "Theming Spec §X" — that spec is the ground truth for tokens.
