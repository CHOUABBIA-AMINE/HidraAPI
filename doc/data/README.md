# HidraAPI Data Governance

## Status

CURRENT — canonical HPR-P2-010 documentation baseline.

## Applicability and ownership

Verified source parent: `b36733fc05e789613485606e1e1dd1731b11af53`, on 2026-10-09.

Business-data accountability follows each owning module in
[domain ownership](../domain/DOMAIN_OWNERSHIP.md) and [the module index](../modules/README.md).
Existing P1 operational policy names its own approval authority; this set does not
invent a named human owner or approve a business dataset. Metadata inherited by
these five documents includes CURRENT source applicability, owner authority, source
links, last verification point above, explicit TARGET/DEFERRED sections and unresolved
facts marked NOT ESTABLISHED. The [documentation standard](../governance/DOCUMENTATION_STANDARD.md)
and [lifecycle](../governance/DOCUMENT_LIFECYCLE.md) govern their maintenance.

## Canonical set

| Document | Purpose |
|---|---|
| [Data Governance](DATA_GOVERNANCE.md) | Ownership for all 24 modules, data responsibilities and decision gaps |
| [Retention and Archival](RETENTION_ARCHIVAL.md) | Approved infrastructure controls versus business metadata/enforcement |
| [Data Provenance](DATA_PROVENANCE.md) | Source-backed evidence and limits of lineage, snapshots and actor attribution |
| [Legacy Data Migration](LEGACY_DATA_MIGRATION.md) | Historical no-approved-import baseline and TARGET admission sequence |

## Evidence precedence and limits

Production source/configuration, migrations and executable tests take precedence over
stale documentation. [Permanent semantics](../domain/SEMANTIC_DECISIONS.md) retain
subject-specific optionality and historical replay rules; this set does not strengthen
them by generalizing a single subject rule. [The roadmap](../roadmap/ULTIMATE_ROADMAP.md)
selects execution; legacy `docs/` retains review/provisioning history.

The exact executable baseline remains `617c2eec812e3a5734957ee9fa0360f6f5613032`,
with prior production CI #604 passed. Documentation verification is separate: no
runtime test, deployment, source-data inspection/import or physical campaign is run
by HPR-P2-010. Prior [P1 exercise evidence](../operations/P1_SURVIVABILITY_EXERCISE_EVIDENCE_2026-10-06.md)
retains its deployed SHA, 82-migration applicability and operator-supplied distinction;
it is not new evidence for the current schema.

## Unresolved decisions

Business retention periods, disposal/hold approvals, source ownership and permitted
reuse, canonical dataset precedence and executable legacy loading are NOT ESTABLISHED
by this task. Documenting those gaps completes the baseline without approving unknown
facts. HPR-P2-011..013 remain pending and P3 deferred; P2 is not closed here.
