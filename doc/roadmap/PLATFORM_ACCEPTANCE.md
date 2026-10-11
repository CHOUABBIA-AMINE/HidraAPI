# HidraAPI — F1–F4 Synthetic PLATFORM Acceptance

## Status

CURRENT — canonical PLATFORM synthetic acceptance planning criteria.

> Criteria only; none of these rows are asserted to have passed. Actual acceptance requires tests and exact-head evidence. Compare to historical HPR-P25-030 A–I before registering exact implementation cases.

| Case | PLATFORM software criterion | Example explicitly synthetic evidence | Dependencies | Status |
|---|---|---|---|---|
| A | Company/owner isolation | Two unrelated companies and denied cross-scope API access | F1-03; F4-02 | PLANNED |
| B | Configurable topology | Different networks, stations, ports and invalid graph cases | F1-04 | PLANNED |
| C | Product/property/method configuration | Multiple configured products, unsupported method rejection | F2-01 | PLANNED |
| D | Immutable engineering baseline | Frozen version manifest, compare and absent reference | F2-03..04 | PLANNED |
| E | Telemetry bindings | Multiple source adapters and invalid tag/asset mappings | F3-01 | PLANNED |
| F | Freshness/UNKNOWN monitoring | Stale, duplicate, missing readings and observed evidence | F3-02..03 | PLANNED |
| G | Operator action/outcome distinction | Authenticated declared action distinct from actual observation | F3-04 | PLANNED |
| H | History/replay/concurrency | Exact replay, conflicting revisions, rollback and immutable history | F1-02; F2-03; F4-02 | PLANNED |
| I | Full database/security/API qualification | Java 21/PostgreSQL/Flyway, authorization, OpenAPI and exact-head CI | F4-02..03 | PLANNED |

These labels organize proposed tests and **are not a claim of identical wording or accepted coverage** of original HPR-P25-030 A–I. Preserve any differences and original evidence in the history file. SITE provisioning/commissioning is separate; no numerical accuracy or production certification is inferred.

For each accepted test retain case version, input fixture, source path, exact commands, actual run/SHA, expected and observed result, coverage limits and reviewer decision.
