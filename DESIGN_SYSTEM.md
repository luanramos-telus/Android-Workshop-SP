# Ponto+ Design System

Reference for the TELUS Digital "Ponto+" theming. Source of truth in code: `app/src/main/java/com/telusdigital/pontomais/ui/theme/{Color,Theme,Type}.kt`. Source of truth in spec: **Theming Spec v1.0** (referenced in code comments).

## 1. Brand foundation

- Wordmark: `Ponto+` (the `+` is the Juniper-green wordmark accent).
- Sub-line: `BY TELUS DIGITAL` (`R.string.app_brand_sub`).
- Voice: pt-BR. All copy lives in `res/values/strings.xml`.
- Vibe: "TELUS purple + electric orchid" hero gradient on top of a pearl-white scaffold with high-contrast Obsidian text. Friendly, slightly premium, not flat.

## 2. Color tokens

All values are in `ui/theme/Color.kt` as top-level `val`s (light scheme is the "v1" focus; dark is wired in `Theme.kt` for v2).

### 2.1 Primary / brand

| Token | Hex | Role |
|---|---|---|
| `TelusPurple` | `#4B286D` | Primary action, app bar, active state, notification accent |
| `Orchid` | `#A020FD` | Electric accent — hero gradient start, onboarding |
| `Verbena` | `#DFB2FF` | Mid-tone purple — card accents, badges |
| `Iris` | `#F7ECFF` | Icon container bg, chip active bg, banners |

### 2.2 Success / feedback

| Token | Hex | Role |
|---|---|---|
| `TelusGreen` | `#66CC00` | Confirmation of punch, on-time indicator, success snackbar |
| `Juniper` | `#BAF29E` | Pale green highlight — wordmark accent, extras badge, working-status dot |
| `Forest` | `#007F4A` | Primary TELUS green — confirm-button alt, links, complete icons |
| `Hawthorn` | `#EAFFE0` | Pale green wash — success banner bg, juniper icon container |
| `Moonstone` | `#F2F3ED` | Subtle fills, alt section bg, menu row hover |

### 2.3 Neutrals

| Token | Hex | Role |
|---|---|---|
| `Obsidian` | `#222220` | Primary text, strong typography (clock numbers) |
| `Slate` | `#595956` | Secondary text, outline icons, captions, metadata |
| `Pearl` | `#FCFDFB` | Page / scaffold bg — near-white |
| `Marble` | `#DEE0D9` | Borders, dividers, switch-off track |

### 2.4 Semantic state

| Token | Hex | Role |
|---|---|---|
| `ErrorRed` | `#BA1A1A` | error |
| `ErrorRedContainer` | `#FFDAD6` | error container bg |
| `OnErrorRedContainer` | `#410002` | text on error container |
| `Amber` | `#A05A00` | warning fg — pause, delay, syncing |
| `AmberContainer` | `#FFF1D6` | warning bg — pause/delay icon container |

### 2.5 Dark scheme (v2 roadmap)

| Token | Hex | Role |
|---|---|---|
| `PurpleDark` | `#3A1D57` | dark primary container |
| `OrchidDark` | `#5C1E7A` | dark secondary container |
| `SurfaceDark` | `#26222C` | dark surface |
| `DarkPage` | `#1A1820` | dark scaffold |
| `Galena` | `#B6B6B1` | dark secondary text |

## 3. Material 3 ColorScheme mapping

`ui/theme/Theme.kt` exposes `PontoMaisTheme(darkTheme = isSystemInDarkTheme())`. Use `MaterialTheme.colorScheme.*` in components when the M3 role fits; reach for raw tokens (`TelusPurple`, `Iris`, `Marble`, etc.) when there is no good role match (notably: borders, status dots, brand gradients).

### Light scheme

| M3 role | Token |
|---|---|
| `primary` | `TelusPurple` |
| `onPrimary` | `White` |
| `primaryContainer` | `Iris` |
| `onPrimaryContainer` | `TelusPurple` |
| `secondary` | `Orchid` |
| `onSecondary` | `White` |
| `secondaryContainer` | `Verbena` |
| `onSecondaryContainer` | `Obsidian` |
| `tertiary` | `Forest` |
| `onTertiary` | `White` |
| `tertiaryContainer` | `Juniper` |
| `onTertiaryContainer` | `Obsidian` |
| `error` | `ErrorRed` |
| `errorContainer` | `ErrorRedContainer` |
| `onErrorContainer` | `OnErrorRedContainer` |
| `background` | `Pearl` |
| `onBackground` | `Obsidian` |
| `surface` | `White` |
| `onSurface` | `Obsidian` |
| `surfaceVariant` | `Moonstone` |
| `onSurfaceVariant` | `Slate` |
| `outline` / `outlineVariant` | `Marble` |
| `scrim` / `inverseSurface` | `Obsidian` |
| `inverseOnSurface` | `Pearl` |
| `inversePrimary` | `Iris` |

### Dark scheme

| M3 role | Token |
|---|---|
| `primary` | `Verbena` |
| `onPrimary` | `TelusPurple` |
| `primaryContainer` | `PurpleDark` |
| `onPrimaryContainer` | `Iris` |
| `secondary` | `Verbena` |
| `secondaryContainer` | `OrchidDark` |
| `tertiary` | `TelusGreen` |
| `tertiaryContainer` | `Forest` |
| `background` | `DarkPage` |
| `surface` / `surfaceVariant` | `SurfaceDark` |
| `onBackground` / `onSurface` | `Pearl` |
| `onSurfaceVariant` | `Galena` |
| `outline` | `Marble` |
| `outlineVariant` | `SurfaceDark` |
| `inversePrimary` | `TelusPurple` |

## 4. Gradients (Caradonna)

Defined in `ui/components/GradientCard.kt`.

- **`CaradonnaBrush`** — `Brush.linearGradient(Orchid → TelusPurple)`, start `(0,0)` to end `(∞, ∞)` ⇒ ~135° diagonal. Used on hero cards (`HeroStatusCard`, history week summary, bank balance, profile header) and on the avatar circle in the top app bar.
- **`CaradonnaVerticalBrush`** — `Brush.verticalGradient(Orchid → TelusPurple)`. Used as a full-screen Login background.
- **`GradientCard(brush, cornerRadius=24.dp, content)`** — base container; clips to rounded shape and paints the brush.

White text, `Color.White.copy(alpha = 0.85f)` for secondary text on gradient backgrounds, `Juniper` for "active" accents (status dot, success label).

## 5. Typography

`ui/theme/Type.kt`. Two custom font families load from `res/font/`:

```kotlin
HnTelusSa         (Normal/Italic, Medium, Bold)        — body / UI
HnTelusSaDisplay  (Normal, Medium, Bold)               — display / numerics (clock, balance)
```

Type scale (Theming Spec §03), mapped onto M3 typography slots — ALL slots filled, including custom letter-spacing in em-space:

| M3 slot | Family | Size / weight / lineHeight / letterSpacing | Intent |
|---|---|---|---|
| `displayLarge` | Display | 64 / 400 / 64 / −2.24sp (≈ −3.5%) | Hero clock, big balance |
| `displayMedium` | Display | 36 / 500 / 37.8 / −0.72sp (−2%) | H1 greeting, big numbers |
| `displaySmall` | Display | 22 / 500 / 25.3 / −0.33sp (−1.5%) | H2 screen titles |
| `headlineLarge` | Display | 20 / 500 / 24 / 0 | Display/Num — punch times, time chips |
| `headlineMedium` | Display | 18 / 500 / 22 / 0 | (reserved) |
| `headlineSmall` | Display | 16 / 500 / 20 / 0 | StatCard value |
| `titleLarge` | Sans | 22 / 500 / 28 / 0 | Top-app-bar / dialog titles |
| `titleMedium` | Sans | 15 / 500 / 22.5 (1.5×) / 0 | Body/Default — menu rows, card copy, punch time |
| `titleSmall` | Sans | 14 / 500 / 20 / +0.14sp (+1%) | Section headings, button alt |
| `bodyLarge` | Sans | 15 / 500 / 22.5 / 0 | Body copy |
| `bodyMedium` | Sans | 13 / 400 / 18 / 0 | Body/Sub — subtitles, hints |
| `bodySmall` | Sans | 12 / 400 / 16 / 0 | Caption — dates, units, metadata |
| `labelLarge` | Sans | 14 / 600 (SemiBold) / 20 / +0.14sp (+1%) | Buttons |
| `labelMedium` | Sans | 11 / 700 (Bold) / 16 / +0.88sp (+8%) | Eyebrow / overline (uppercase at call site) |
| `labelSmall` | Sans | 11 / 500 / 16 / +0.55sp | Sync state, nav labels, status pill |

Note: weights map as Medium=500, SemiBold=600, Bold=700 — call sites occasionally override with `.copy(fontWeight = FontWeight.Bold)` (e.g., HomeScreen "Batidas de hoje" header).

### Where each slot is used (concrete examples)

- **`displayMedium`** — hero clock (`HeroStatusCard`, white)
- **`headlineSmall`** — `StatCard` value (`Obsidian`, −0.02em letterSpacing)
- **`titleLarge`** — `PontoTopAppBar` title
- **`titleMedium`** — `PunchRow` time, `DayRow` total
- **`titleSmall`** — section headers
- **`bodyMedium`** — `PunchRow` type label, hero date
- **`bodySmall`** — captions, list-empty text, "Ver histórico"
- **`labelLarge`** — every button label, filter-chip label
- **`labelMedium`** — uppercase eyebrows, avatar initials (with `+8%` letter-spacing baked in)
- **`labelSmall`** — sync state, nav-bar labels, status pill ("Trabalhando" / "Fora do expediente"), `DayRow` status badge

## 6. Shapes & elevation

- Cards / hero gradient containers: `RoundedCornerShape(24.dp)` (hero), `16.dp` (StatCard, punch list), `12.dp` (DayRow, PunchRow icon tile).
- Buttons: `CircleShape` (full pill — `PontoButton`, `PontoOutlinedButton`, hero "Bater ponto"). Height 48–56dp, content padding `24h × 14v`.
- Status pills / status dot: `CircleShape`, dot is `8.dp`.
- Cards use **zero elevation** (`CardDefaults.cardElevation(0.dp)`) and a `BorderStroke(1.dp, Marble)` instead — flat with hairline border is the dominant card style.
- Bottom nav: M3 `NavigationBar` with `Pearl` container and unset tonal elevation (flat).
- Top app bar: M3 `TopAppBar` with `MaterialTheme.colorScheme.background` (Pearl) container — flush with content.

## 7. Spacing

No formal token file; values used consistently across screens:

- Screen horizontal padding: `16.dp`.
- Vertical rhythm: `4 / 8 / 12 / 14 / 16 / 20 / 24 dp` (used in that order of frequency).
- Card inner padding: `14.dp` (StatCard), `16.dp` (rows), `24.dp` (hero card content).
- Spacers between vertical sections: `Spacer(Modifier.height(8.dp))` or `12.dp`.
- Icon size standards: `14.dp` (trend / chevron), `18.dp` (button leading), `20.dp` (row), `22.dp` (text-field leading), `28.dp` (hero button icon disc).

## 8. Iconography

`material-icons-extended`, **outlined** variant unless explicitly stated. Brand mapping:

| Use | Icon |
|---|---|
| Punch In / Saída | `Outlined.ArrowDownward` / `Outlined.ArrowUpward` |
| Pause / Back from pause | `Outlined.Coffee` / `Outlined.ArrowUpward` |
| Authenticate / Punch CTA | `Outlined.Fingerprint` |
| Notifications | `Outlined.Notifications` (with `Badge` for count) |
| Synced | `Outlined.Check` (TelusPurple) |
| Syncing | `Outlined.Wifi` (Amber) |
| Location | `Outlined.LocationOn` (Slate, 11.dp) |
| Trend / bank | `AutoMirrored.Outlined.TrendingUp`, `Outlined.AccountBalance` |
| Bottom nav | `Home`, `History`, `AccountBalance`, `Person` (all Outlined) |
| History expand | `Outlined.ExpandMore` / `Outlined.ExpandLess` |
| Show/hide password | `Outlined.Visibility` / `Outlined.VisibilityOff` |
| Back | `AutoMirrored.Outlined.ArrowBack` |

Icon containers: `Surface(shape = RoundedCornerShape(12.dp), color = Iris)` for primary (purple icon on Iris), `color = AmberContainer` for warning (Amber icon).

## 9. Components catalog (design system primitives)

All composables in `ui/components/`. Every file has a `@Preview` — open the file to see ready-made states.

### `GradientCard` — `Brush`-painted rounded container.
Use `CaradonnaBrush` (default, hero cards) or `CaradonnaVerticalBrush` (full-screen Login).

### `HeroStatusCard(time, date, isWorking, onPunch)`
The signature card. Caradonna gradient, 24dp radius. Content:
1. Status row: pulsing Juniper dot (1.0→1.5 scale, 800 ms `LinearEasing` repeat-reverse) + "Trabalhando"/"Fora do expediente" label.
2. Big white clock (`displayMedium`, −0.03em letter-spacing).
3. White-85% subtitle (date long form).
4. Full-width white pill button: small `TelusPurple` disc + `Outlined.Fingerprint` (white) + "Bater ponto" label in `labelLarge`.

### `StatCard(overline, value, sub, trendIcon?, accentColor=TelusPurple, onClick?)`
White card, 1dp Marble border, 0 elevation, 16dp radius. Layout: overline (`labelSmall` uppercase, accent color, `+8%` letter-spacing) + optional trend icon disc on right (Iris bg) / big `headlineSmall` value (Obsidian) / `bodySmall` sub (Slate).

### `PontoButton(label, onClick, icon?, containerColor=TelusPurple, contentColor=White)`
Pill (`CircleShape`), 48dp height. Optional 18dp leading icon, label in `labelLarge`.

### `PontoOutlinedButton(label, onClick, icon?, borderColor=White40, contentColor=White)`
For dark gradient backgrounds (Login). 1dp white-40% border, transparent fill.

### `PontoTextField(value, onValueChange, label, leadingIcon?, isPassword, dark, keyboardOptions)`
M3 `TextField`. Two visual modes:
- Light: `surfaceVariant` container, `primary` indicator/label-focused.
- Dark (login): `White-10%` container, white text, `White-70%` label, `Juniper` focused indicator/label.
Password mode adds visibility toggle (`Visibility` / `VisibilityOff`).

### `PontoFilterChip(label, selected, onClick)`
M3 `FilterChip`. Selected: `Iris` bg, `TelusPurple` text + 1dp `TelusPurple` border + leading `Check` icon, `SemiBold`. Unselected: White bg, `Obsidian` text, `Marble` border, `Medium`.

### `PontoTopAppBar(title, navigationIcon? | leadingContent?, onNavigationClick, actions)`
M3 `TopAppBar` with `background` color (Pearl) and `onBackground` (Obsidian) content. Title in `titleLarge`. `leadingContent` slot wins over `navigationIcon` (used to inject the avatar disc on Home).
Companion: `NotificationIconButton(badgeCount)` — `Outlined.Notifications` with badge.

### `PontoBottomNavBar(currentTab, onTabSelected)`
M3 `NavigationBar`, `Pearl` container, flat. Items use `Iris` indicator pill, `TelusPurple` selected icon/text, `Slate` unselected. Labels in `labelSmall`. The `PontoTab` enum (`Home`, `History`, `Bank`, `Profile`) holds label / icon / route.

### `PunchRow(punch, showDivider)` + `PunchType` + `PunchEntry`
One row in a punch list. Layout: 40dp rounded `iconBg` square holding the type icon, then [type label / `LocationOn` + location] taking remaining width, then [time / sync state]. Sync state: green check + "sincronizado" (TelusPurple) or wifi + "sincronizando" (Amber).
`PunchType` carries its own visual identity: In/Back/Out share `TelusPurple` on `Iris`; Pause uses `Amber` on `AmberContainer`.

### `DayRow(day)` + `DayStatus` + `DayEntry`
History row card, expands to show 4-punch timeline (TelusPurple/Orchid alternating dots, time + label below each). `DayStatus` colors: Complete / Over / Holiday → TelusPurple on Iris; Short → Amber on AmberContainer.

### `MovementRow`, `WeekSummaryCard`, `ScheduleCard`, `ProfileMenuRow`, `QuickInfoCell`
Domain-specific composites built from the primitives above. Same color/typography/shape conventions.

## 10. Patterns & conventions

- **Hero cards always use `CaradonnaBrush`.** Idle/working differentiation is in the status pill, not the gradient.
- **Pulsing status dot** = "currently working" indicator. The pulse is `infiniteRepeatable` 800 ms reverse on `scale 1→1.5`.
- **White card on Pearl bg, hairline Marble border, no shadow** is the default content surface.
- **Iris (`#F7ECFF`)** is the universal "passive accent" container — chip-active bg, icon container, badge bg, nav indicator, status badge.
- **Amber pair (`Amber` / `AmberContainer`)** is reserved for *attention-needed* states (delay, pause, syncing). Don't use Amber for plain warnings — it specifically signals "needs follow-up".
- **TelusPurple ≠ pressed state.** It's the brand action color. Pressed/selected states use `Iris` containers + TelusPurple foreground.
- **Letter-spacing is part of the brand.** Display headings get negative tracking (−1.5% to −3.5%); eyebrows/buttons get small positive tracking (+1% to +8%). Always preserve via `.copy(letterSpacing = ...)` if you re-style M3 text.
- **Buttons are pills, not square corners.** `CircleShape` is the default button shape across the app (hero, login, generic).
- **Edge-to-edge** is enabled in `MainActivity`; respect insets via `Scaffold` + `innerPadding`.
- **No drop shadows.** Cards rely on Marble borders. Don't add `elevation` unless you're matching M3 chrome (snackbars, dialogs).
- **Brand wordmark accent** is Juniper, not Forest — the green `+` in `Ponto+`.

## 11. Locations of truth

| Looking for | Open |
|---|---|
| Color hex | `app/src/main/java/com/telusdigital/pontomais/ui/theme/Color.kt` |
| M3 role mapping | `…/ui/theme/Theme.kt` |
| Type scale | `…/ui/theme/Type.kt` |
| Gradients | `…/ui/components/GradientCard.kt` |
| Per-component visuals | `…/ui/components/<Name>.kt` (each has a `@Preview`) |
| Copy / strings | `app/src/main/res/values/strings.xml` |
| Fonts | `app/src/main/res/font/hn_telus_sa*` |
