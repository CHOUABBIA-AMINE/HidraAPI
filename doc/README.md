# HidraAPI Canonical Documentation

## Authority

The `doc/` tree is the canonical maintained documentation estate for HidraAPI.

The legacy `docs/` tree is preserved for historical, audit, semantic-review, ADR, provenance, data-definition and execution evidence. Legacy documents remain useful evidence, but they are not automatically authoritative for current implementation or platform-finalization sequencing.

## Start Here

1. `doc/roadmap/ULTIMATE_ROADMAP.md` — single source of truth for platform-finalization execution.
2. `doc/security/WORKBENCH_DATA_EXPOSURE.md` — security contract for the first P0 workstream.
3. `doc/governance/DOCUMENTATION_STANDARD.md` — canonical evidence rules.
4. `doc/governance/DOCUMENT_LIFECYCLE.md` — lifecycle and supersession rules.
5. `doc/governance/DOCUMENT_STATUS_MODEL.md` — document status vocabulary.

## Evidence Precedence

For claims about implemented behavior:

1. current production source;
2. runtime configuration;
3. Flyway migrations;
4. architecture/security tests;
5. CI definitions and exact-head execution evidence;
6. canonical `doc/` documents;
7. legacy `docs/` evidence according to its recorded applicability.

Target and historical documents never override contradictory current executable evidence.
