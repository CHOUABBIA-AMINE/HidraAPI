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
| Testing | not yet established | HPR-P2-011 |

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

Data-governance/navigation verification: HPR-P2-010 on 2026-10-09, source parent `b36733fc05e789613485606e1e1dd1731b11af53`. Business approvals and executable import/disposal remain explicitly unestablished; HPR-P2-011..013 remain pending and P2 stays open.
