# HidraAPI Canonical Documentation

## Status

CURRENT — canonical documentation entry point.

## Authority

The `doc/` tree is the canonical maintained documentation estate for HidraAPI.

The legacy `docs/` tree is preserved for historical, audit, semantic-review, ADR, provenance, data-definition and execution evidence. Legacy documents remain useful evidence, but they do not override current repository evidence or the canonical roadmap.

## Start Here

1. `doc/roadmap/ULTIMATE_ROADMAP.md` — single source of truth for platform-finalization sequencing, task codes and phase disposition.
2. `doc/governance/README.md` — canonical documentation governance entry point.
3. `doc/governance/DOCUMENT_REGISTER.md` — canonical domain/status register and P2 documentation readiness map.
4. `doc/governance/DOCUMENTATION_STANDARD.md` — evidence and metadata requirements.
5. `doc/governance/DOCUMENT_LIFECYCLE.md` — lifecycle, preservation and supersession rules.
6. `doc/governance/DOCUMENT_STATUS_MODEL.md` — status vocabulary.
7. `doc/architecture/README.md` — canonical current/target-separated architecture set.
8. `doc/domain/README.md` — canonical ubiquitous language and domain-semantic baseline.
9. `doc/modules/README.md` — canonical current-state index for all 24 implemented business modules.
10. `doc/api/README.md` — canonical versioned API contract and API governance set.
11. `doc/database/README.md` — canonical current database architecture, ownership, Flyway policy and generated persistence dictionary.
12. `doc/model-remediation/RECONCILIATION.md` — completed semantic-remediation execution evidence and historical reconciliation.

13. [Permanent semantic decisions](domain/SEMANTIC_DECISIONS.md) — current-source rules for all 123 reviewed subjects, with module and evidence links.

14. [Data governance](data/README.md) — current ownership, retention/archival, provenance and legacy-data admission baseline.

15. [Testing and verification](testing/README.md) — strategy, architecture, database, API and requirements evidence.

16. [Documentation validation](governance/DOCUMENTATION_VALIDATION.md) — enforced structural checks and generated OpenAPI equality.

## Canonical Domains

| Domain | Current canonical material | P2 disposition |
|---|---|---|
| Governance | `doc/governance/**` | CURRENT — HPR-P2-001 |
| Roadmap | `doc/roadmap/ULTIMATE_ROADMAP.md` | CURRENT execution authority |
| Architecture | `doc/architecture/README.md` and linked canonical set | CURRENT — HPR-P2-002; historical P1 runtime baseline retained separately |
| Security | `doc/security/**` | Existing canonical P0 security baseline |
| Operations | `doc/operations/**` | Existing canonical P1 operations/survivability baseline |
| Database | `doc/database/README.md` and linked canonical set | CURRENT — HPR-P2-006; earlier P1 stage documents retained as historical evidence |
| Domain | `doc/domain/README.md` and linked canonical set | CURRENT — HPR-P2-003; semantics refreshed by HPR-P2-009 |
| Modules | `doc/modules/README.md` plus 24 current-state module documents | CURRENT — HPR-P2-004; inventories refreshed by HPR-P2-009 |
| API | `doc/api/README.md`, governance set and `doc/api/openapi.yaml` | CURRENT — HPR-P2-005 |
| Semantic remediation | `doc/model-remediation/RECONCILIATION.md` | CURRENT reconciliation — HPR-P2-007; HPR-P2-008 completed; permanent decisions transferred by HPR-P2-009 |
| Data governance | [Data index](data/README.md) and four linked governance documents | CURRENT — HPR-P2-010 |
| Testing | [Testing index](testing/README.md) and five linked verification documents | CURRENT — HPR-P2-011 |

Absence of a later P2 canonical set does not invalidate existing P0/P1 evidence. It means that the corresponding P2 canonicalization task has not yet executed.

## Evidence Precedence

For claims about implemented behavior:

1. current production source;
2. runtime configuration;
3. Flyway migrations;
4. architecture/security tests;
5. CI definitions and exact-head execution evidence;
6. canonical `doc/` documents;
7. legacy `docs/` evidence according to its recorded applicability.

Target, deferred, historical and execution-history documents never override contradictory current executable evidence.

## Legacy Preservation

`docs/**` is intentionally retained. Do not delete, rewrite, or silently promote legacy material merely because equivalent canonical documentation exists. When legacy evidence is used, reconcile it against current evidence and record its applicability in the canonical document or register.

Last semantic/navigation verification: HPR-P2-009 on 2026-10-09, source parent `44d4fe773d69ed51dd90820140c8d9e7aee6cba2`. Domain/module metadata and current-versus-target boundaries follow their containing indexes; untouched sets retain their own applicability.

Data-governance/navigation verification: HPR-P2-010 on 2026-10-09, source parent `b36733fc05e789613485606e1e1dd1731b11af53`. Business approvals and executable import/disposal remain explicitly unestablished; HPR-P2-013 remains pending and P2 stays open.

Testing/navigation verification: HPR-P2-011 on 2026-10-09, source parent `35d9d949aa773a4d754c22d00181330f579f752b`. Test presence, historical CI, uninspected per-class results/skips and retained physical evidence remain distinct; no runtime tests were rerun by the documentation task.

Validation/navigation verification: HPR-P2-012, source parent `508337351eef03b82e2c6078a7c8523013efd063`, 2026-10-09. Both structural documentation and production runtime-snapshot gates are wired; exact-head CI follows publication, P2 stays open.

## HPR-P2-013 current canonical review — 2026-10-09

Verified source parent: `00c4fda266b2dfd175cca37ad789dc9462a5af0b`. Revised preflight Documentation Validation
#126 (37913628486) PASSED before this implementation. HPR-P2-012 documentation #124
(37909982710) and production #605 (37909982823) PASSED at
`e4dba168c9e612a5fd49d50b155fa3b2d8d64e40`.

All 24 module documents and canonical indexes remain registered; maintained metadata,
local links and source module roots are checked by canonical validation. Permanent
semantics retain 123 subjects and reconciliation retains 57 completed HMR identities.
The executable source/test/resources/POM and production operations are unchanged from
the semantic-transfer and CI #604 baselines. Legacy docs/** remains subordinate
history; no review verdict is used as current execution authority.

Current database inventory is refreshed to 139 unique versioned migrations and 470
module entities. Current architecture exports are 70 Java files in 63 packages,
including neutral extension roles. Original architecture/database/P1 provenance
retains its own source/deployed applicability. The current API object is unchanged:
CI #604 generated the snapshot; #605 freshly verified equality except source-SHA
provenance and independently passed supported compatibility. No endpoint/schema or
security value is manually edited.

HPR-P2-013 documentation implementation is complete, but P2 final VERIFIED/CLOSED
disposition is PENDING both successful CI workflows on the resulting implementation
SHA. Current implementation publication is not CI success. No P3 task is selected.
Prior dated pending/publication statements above retain historical applicability and
are superseded by this current review where they describe the earlier execution state.

Unknown business retention/policy approvals, complete endpoint/performance/OT coverage,
per-class no-skips evidence, deployed-data/import acceptance, runtime inference/actuation
and fresh physical survivability are not established by this documentation closure.
P0/P1 disposition and original physical evidence remain unchanged absent regression.
TimescaleDB, PostGIS and unimplemented industrial/AI extensions remain DEFERRED/TARGET.
