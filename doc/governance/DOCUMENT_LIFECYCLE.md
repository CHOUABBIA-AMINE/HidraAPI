# HidraAPI Documentation Lifecycle

## Status

CURRENT — canonical governance rule.

## Lifecycle Rules

1. A document becomes CURRENT only when current-state claims are verified against repository evidence.
2. TARGET material must be visibly separated from CURRENT material.
3. DEFERRED material cannot be treated as a blocking current defect unless an approved decision changes its state.
4. SUPERSEDED documents are retained when they contain audit, provenance, ADR, semantic-review, or execution evidence.
5. HISTORICAL evidence is not rewritten to appear current.
6. EXECUTION_HISTORY documents may provide detailed task provenance but do not control new execution after supersession.
7. Roadmap completion must transfer permanent architecture/domain decisions into canonical architecture/domain/module documents.
8. Stale summary text must not select execution work.
9. Supersession changes authority, not evidentiary preservation.

## Canonical Transition

`docs/` remains preserved as the legacy/reference estate.

`doc/` becomes the maintained canonical estate.

The platform-finalization execution authority is `doc/roadmap/ULTIMATE_ROADMAP.md`.
