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

## Canonical Domains

| Domain | Current canonical material | P2 disposition |
|---|---|---|
| Governance | `doc/governance/**` | CURRENT — HPR-P2-001 |
| Roadmap | `doc/roadmap/ULTIMATE_ROADMAP.md` | CURRENT execution authority |
| Architecture | `doc/architecture/README.md` and linked canonical set | CURRENT — HPR-P2-002; historical P1 runtime baseline retained separately |
| Security | `doc/security/**` | Existing canonical P0 security baseline |
| Operations | `doc/operations/**` | Existing canonical P1 operations/survivability baseline |
| Database | `doc/database/**` | Existing P1 database-operational material; canonical database set is HPR-P2-006 |
| Domain | `doc/domain/README.md` and linked canonical set | CURRENT — HPR-P2-003 |
| Modules | `doc/modules/README.md` plus 24 current-state module documents | CURRENT — HPR-P2-004 |
| API | not yet established under `doc/api/` | HPR-P2-005 |
| Data governance | not yet established | HPR-P2-010 |
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
