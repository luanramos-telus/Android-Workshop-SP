---
name: claude-design-spec
description: Implements Android features from Jira tickets that link to a full Claude Design spec. Use when the user asks to build an Android feature from Jira and the ticket contains a Claude Design link, design URL, or design reference. Guides the agent to understand the current Android project first, fetch functionality and constraints from Jira, fetch design references only from the linked Claude Design, understand the specific feature flow requested by the user, present implementation approach options, ask which approach the user wants, plan small tasks, implement with Android and Jetpack Compose best practices, and ask for clarification whenever Jira or the design is incomplete or conflicting.
---

# Jira Claude Design Android Feature

Use this skill to implement an Android feature when the Jira ticket contains a link to the full Claude Design. Jira owns functionality, business rules, acceptance criteria, scope, and non-visual constraints. The linked Claude Design owns feature-specific design references, visual details, UI tokens, screen structure, states, and flow.

## Source Rules

- Never rely on prior chat history, memory, old agent transcripts, previous local notes, or assumptions as design references.
- Get design references only from the Claude Design linked from the current Jira ticket.
- Get functionality, business rules, acceptance criteria, data requirements, permissions, rollout constraints, and priorities from the current Jira ticket.
- If something is not present in Jira or the linked Claude Design, ask the user.
- If Jira and Claude Design conflict, ask the user when the decision affects behavior, scope, data, design fidelity, or implementation cost.

## Operating Principles

- Understand the existing Android project before designing a solution.
- Understand the specific feature the user wants inside the full Claude Design; do not implement unrelated screens or flows just because they exist in the design.
- Always present implementation approach options and ask the user which approach they want before coding.
- Implement native Android UI unless the existing project already uses a different Android UI technology.
- Prefer existing project architecture, navigation, theme, components, and state patterns over new abstractions.
- Plan in small reviewable tasks before editing.
- Ask the user whenever Jira, Claude Design, current app behavior, data contracts, navigation, or acceptance criteria are ambiguous or contradictory.

## Workflow

### 1. Gather Inputs

Identify all available inputs:

- Jira issue key or URL.
- Jira title, description, acceptance criteria, comments, attachments, linked issues, labels, screenshots, and any Claude Design link.
- The specific feature or flow the user wants implemented from the full Claude Design.
- Target Android module, package, screen, branch, and user-provided implementation constraints.

If the Jira ticket does not contain a Claude Design link, ask the user for the link. If the user does not identify the specific feature/flow inside a larger design, ask them to choose the target feature before implementation.

### 2. Understand The Current Project

Before deciding how to implement, inspect the app architecture:

- Build system: Gradle modules, AGP/Kotlin versions, Compose setup, dependency management.
- App structure: navigation graph, screen packages, feature modules, shared UI components, theme, typography, icons, strings, resources.
- State and data patterns: ViewModels, repositories, use cases, Room/DataStore/network, dependency injection, coroutine and Flow conventions.
- Testing patterns: unit tests, Compose UI tests, screenshot tests, existing test fixtures.
- Product conventions: naming, localization, accessibility, error states, loading states, analytics, permissions.

Look for nearby screens or current-code patterns that solve a similar problem. Do not use previous chat history to infer design intent.

### 3. Read Jira For Functionality

Extract the functional brief from Jira:

- User problem and expected outcome.
- Functional requirements and acceptance criteria.
- Entry points, roles, permissions, feature flags, rollout constraints, analytics, and dependencies.
- Data inputs/outputs, API contracts, persistence expectations, validations, and error cases.
- Comments or linked issues that clarify scope or priority.
- The Claude Design link and any note about which part of the design applies.

Do not infer product functionality from Claude Design if Jira already defines it. Use Claude Design to clarify presentation and flow. If Jira lacks behavior but Claude Design implies it, ask the user whether to implement the implied behavior.

### 4. Read The Linked Claude Design

Fetch and inspect the Claude Design linked from Jira. If the link provides a `.tar.gz` handoff or downloaded binary, extract it and read the exported files. If it provides a browsable design page, inspect the relevant design content directly.

For `.tar.gz` handoffs:

```bash
mkdir -p /tmp/claude-design
tar -xzf <design-bundle>.tar.gz -C /tmp/claude-design
```

If a fetched link saves binary gzip content:

```bash
mkdir -p /tmp/claude-design
gunzip -c <downloaded-binary> | tar -xf - -C /tmp/claude-design
```

When files are available, read them in this order:

1. `README.md`: identifies primary files and handoff instructions.
2. Design files for the specific requested feature or flow.
3. Imported CSS/JSX/SVG/assets that define tokens, components, or visual states.
4. Any generated design spec or canvas state that maps screens and transitions.

Only inspect `chats/*.md` if they are included inside the current Claude Design export and needed to understand that design's final intent. Do not use this conversation's chat history or old transcripts as design input.

Extract only what applies to the requested feature:

- Flow: entry point, screen sequence, transitions, back behavior, empty/error/loading states.
- UI structure: hierarchy, layout, spacing, system bars, insets, keyboard behavior, responsive behavior.
- Design tokens: colors, typography, spacing, radii, elevation, icon sizes, component dimensions, motion, opacity.
- Component states: default, pressed, focused, disabled, selected, loading, empty, error, offline, permission denied.
- Content: labels, helper text, error messages, accessibility copy, localization notes.

Map Claude Design tokens to the existing Android design system. Prefer existing theme tokens when equivalent. Only add new tokens when the design requires values that do not already exist.

### 5. Reconcile Jira, Claude Design, And Project

Create a short implementation brief:

- Functionality from Jira.
- Acceptance criteria from Jira.
- Specific feature flow and design requirements from Claude Design.
- Current project patterns that should be reused.
- Missing information, contradictions, or risky assumptions.

When sources disagree, do not silently choose:

- Jira wins for functionality, business rules, acceptance criteria, scope, permissions, data contracts, analytics, and rollout.
- Claude Design wins for visual layout, flow shape, copy shown in UI, component states, hierarchy, and tokens.
- Existing app patterns win for architecture, navigation, theming, localization, accessibility, persistence, and testing unless Jira or Claude Design explicitly requires a change.

If the disagreement changes user-visible behavior, data flow, design fidelity, or implementation cost, ask the user to decide.

### 6. Ask Which Approach The User Wants

Always present approach options before implementation. Tailor the options to the feature, Jira scope, Claude Design complexity, and project architecture. Include tradeoffs in plain language.

Use this shape:

```markdown
I found these viable approaches:
1. Minimal integration: reuse existing screens/components and implement only the Jira-required behavior for this feature.
   Tradeoff: fastest and lowest risk, but may not match every Claude Design visual detail.
2. Design-faithful implementation: implement the requested feature flow closely following Claude Design tokens, layout, and states.
   Tradeoff: best design fidelity, more changes and test surface.
3. Foundation-first: add/update shared tokens/components first, then implement the feature.
   Tradeoff: cleaner long-term design system, larger initial scope.

Which approach do you want?
```

Do not start coding until the user selects an approach, unless the user already explicitly chose one in the request.

### 7. Plan Small Tasks

After the user chooses an approach, present a concise task plan. Keep tasks small enough to verify independently.

Use this shape:

```markdown
Implementation plan:
1. Map existing app entry point and navigation for <feature>.
2. Map Jira requirements to state, data contracts, and acceptance criteria.
3. Map Claude Design tokens and flow to existing or new Android UI pieces.
4. Build Compose UI using existing theme/components.
5. Wire actions, validation, persistence, side effects, and analytics.
6. Add strings, accessibility labels, previews, and edge states.
7. Add or update focused tests.
8. Run build/tests and summarize remaining risks.

Clarifications needed:
- <question if any>
```

If unresolved clarifications materially affect architecture, data, scope, design fidelity, or user-visible behavior, ask before coding. If the ambiguity is minor and reversible, state the assumption and proceed.

### 8. Implement Native Android UI

Follow the current project's Android stack. For Jetpack Compose:

- Use existing Material theme, tokens, typography, colors, shapes, spacing, icons, and reusable components.
- Keep composables stateless where practical; hoist state to the ViewModel or caller according to local patterns.
- Prefer `StateFlow`/`Flow` collection patterns already used in the app.
- Use `stringResource`, plural resources, content descriptions, semantic roles, touch target sizes, and previews.
- Respect system insets, IME behavior, edge-to-edge setup, dark mode, font scaling, and accessibility.
- Translate Claude Design intent to Android units and components; do not create one-off styling when a shared token/component exists.
- Keep business logic out of composables except UI-only state and permission launchers.
- Use `rememberSaveable` only for transient UI state that should survive configuration changes.

For forms or editable screens, avoid save/navigation races. If the screen can navigate away while saving, either:

- Persist each meaningful change as it happens when that matches product behavior, or
- Await the save result before popping navigation and expose loading/error state.

For runtime permissions, keep the launcher and rationale/settings decision in the composable layer because it needs Activity context. The ViewModel should model intent and state, not own permission dialogs.

### 9. Validate Continuously

After each small task, run the narrowest useful validation:

- Compile affected module.
- Run relevant unit tests.
- Run Compose UI/screenshot tests if the project has them.
- Manually check navigation and state transitions when automation is not available.

At the end, run the broadest practical validation for the change and report any commands that could not be run.

### 10. Communicate Clearly

When asking the user for more information, ask specific questions with options when possible:

- Which specific feature or flow from Claude Design should be implemented?
- Which implementation approach do you want: minimal integration, design-faithful implementation, or foundation-first?
- Should this use real backend data, local mock data, or an existing repository?
- Which source wins if Jira and Claude Design conflict on behavior?
- Is this behind a feature flag?
- What should happen for loading, empty, error, offline, permission-denied, and unauthenticated states?

When done, summarize:

- What changed.
- How it maps to Jira functionality and Claude Design references.
- Validation performed.
- Known assumptions, gaps, or follow-up work.

## Anti-Patterns

- Relying on prior chat history, old transcripts, memory, or previous notes for design references.
- Implementing from Claude Design before understanding the Android project.
- Implementing unrelated screens from the full Claude Design instead of the specific requested feature.
- Treating Claude Design as the functional source of truth when Jira defines behavior.
- Treating Jira as the visual source of truth when Claude Design defines design and tokens.
- Starting implementation before asking which approach the user wants.
- Inventing new navigation, theme, DI, or persistence patterns when the app already has one.
- Guessing behavior from visual specs when Jira or product rules are missing.
- Making one large implementation pass without a small-task plan.
- Proceeding through unresolved contradictions that affect UX, data, permissions, scope, or acceptance criteria.
