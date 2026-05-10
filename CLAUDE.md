# Ponto+ — Claude entry point

**Before exploring this codebase, read these two files at the repo root:**

1. [`ARCHITECTURE.md`](./ARCHITECTURE.md) — stack, module layout, navigation graph, state pattern, file-by-file component/screen map, data flow, and known gaps.
2. [`DESIGN_SYSTEM.md`](./DESIGN_SYSTEM.md) — full color palette + M3 role mapping, Caradonna gradients, typography scale (sizes/weights/letter-spacing), shapes/spacing/iconography, and every component's API surface.

Together they cover ~95% of what you'd otherwise grep for. Skim them first, then dive into source only for the specific files you need to change.

## Quick facts (so you don't need to re-derive)

- Single-module Android app (`:app`), package `com.telusdigital.pontomais`. Pure Jetpack Compose — no XML layouts, no fragments.
- Kotlin 2.2.10, AGP 9.2.1, min SDK 26, target/compile SDK 36, Java 11. Compose BOM `2026.02.01`, Navigation Compose 2.9.
- Single activity (`MainActivity`) → `PontoNavHost` (5 routes: `Login`, `Home`, `History`, `Bank`, `Profile`). Start destination is `Login`.
- State: `ViewModel` + `StateFlow`. Only `HomeViewModel` is real today; other screens use mock data inline.
- No data layer, no DI, no persistence, no tests yet — see ARCHITECTURE.md "What's not here yet" for the full list.
- All UI copy is pt-BR in `app/src/main/res/values/strings.xml`. Brand fonts: `HnTelusSa` + `HnTelusSaDisplay` (in `res/font/`).
- Brand colors live in `ui/theme/Color.kt`. The hero gradient is `CaradonnaBrush` (Orchid → TelusPurple) in `ui/components/GradientCard.kt`.

## Specialized agents

- **`ac-validator`** (Haiku, read-only) — validates that code on the current branch satisfies the Acceptance Criteria of a Jira issue. Defined at `.claude/agents/ac-validator.md`.

  **Invoke it whenever the user asks to validate / review / confirm ACs against a Jira ticket** — e.g. "validate AC for ABC-123", "check if PROJ-45 is done", "review the ACs of ticket X", or when wrapping up work that maps to a known Jira key. Don't validate ACs inline yourself — delegate to this agent so the work runs on Haiku and stays scoped to a single read-only review pass.

  Call it via the Agent tool with `subagent_type: "ac-validator"`. Pass the Jira key (and base branch, if not `main`) in the prompt. The agent returns a per-AC PASS/PARTIAL/FAIL/NOT_VERIFIABLE report with `file:line` evidence — relay its summary to the user.

## Jira workflow

When the session's work is tied to a Jira ticket (key like `ABC-123`, given by the user or unambiguously inferable from the current branch / commit subjects), follow this lifecycle. Transitions go through `mcp__claude_ai_Atlassian__transitionJiraIssue` — resolve `cloudId` via `getAccessibleAtlassianResources` and the correct transition id via `getTransitionsForJiraIssue`.

1. **Starting work — auto-move To Do → In Progress.** Before making the first code change, transition the ticket from **To Do** to **In Progress**. This is pre-authorized by these instructions; do not ask first. If the ticket is already In Progress (or further along), skip silently and continue. If it sits in a status that is *not* the standard backlog (e.g. Blocked, In Review), stop and ask the user how to proceed instead of guessing.

2. **Finishing work — validate, then ask before publishing.** After the implementation is complete:
   - Delegate AC validation to the **`ac-validator`** agent (see "Specialized agents"). The agent is read-only — it cannot transition or comment.
   - Once the agent reports back, summarize the verdict for the user, then **stop and ask** in plain words, e.g. *"ACs validated. Want me to create the PR and move `<KEY>` to In Review?"*
   - Only on an explicit user "yes" (this turn — prior approvals don't carry over):
     a. Create the PR following the standard PR-creation flow in this repo.
     b. Transition the ticket to **In Review**.
   - If the AC validator returns FAIL or PARTIAL, surface that and do **not** offer the PR/transition step — fix the gap first, or ask the user how to proceed.

3. **Never auto-advance past In Review.** Code Review, Done, Closed, Released, etc. are owned by humans and CI. Do not move tickets further without an explicit, unambiguous instruction in the same turn.

4. **Failures are stopping events.** If a transition call fails (permission denied, transition id not available from the current status, workflow mismatch), report the exact error and stop. Do not retry, do not pick a different transition, do not invent a fallback path.

## Skills

- **`claude-design-spec`** — invoke whenever a task touches the design system: theming tokens, typography, gradients, spacing, shapes, or any visual/UI change expected to follow the Ponto+ Theming Spec. Use it via `/claude-design-spec` (or the Skill tool with `skill: "claude-design-spec"`) **before** modifying anything under `app/src/main/java/com/telusdigital/pontomais/ui/theme/` or `…/ui/components/`, and **before** adding new screens, so the change stays consistent with `DESIGN_SYSTEM.md`. If the skill flags a deviation, surface it in the response and pause until the user confirms.

## Working conventions

- Component composables take primitives + lambdas (no ViewModels) so they stay previewable. Every component file has at least one `@Preview` — open it to see states before re-implementing.
- Use `MaterialTheme.colorScheme.*` when an M3 role fits; reach for raw tokens (`TelusPurple`, `Iris`, `Marble`, `Juniper`, `Amber`, etc.) when there is no good role match.
- Use `MaterialTheme.typography.*`. Preserve letter-spacing via `.copy(letterSpacing = ...)` — it's part of the brand.
- Use `stringResource(R.string.*)` over hardcoded strings (HomeScreen is the exemplar).
- Cards: 0 elevation + `BorderStroke(1.dp, Marble)`. Buttons: `CircleShape` (pill). Hero containers always use `CaradonnaBrush`.

## When these docs go stale

If you change theming, navigation, the state pattern, or add new top-level packages (data/, di/, etc.), update the relevant doc in the same change. They're a contract with future sessions, not a snapshot.
