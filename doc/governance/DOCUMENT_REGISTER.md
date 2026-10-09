# HidraAPI Canonical Documentation Register

## Status

CURRENT — canonical documentation register.

## Applicability

Repository: `CHOUABBIA-AMINE/HidraAPI`

Canonical root: `doc/`

Legacy/reference/evidence root: `docs/`

Execution authority: `doc/roadmap/ULTIMATE_ROADMAP.md`

Last semantic/navigation verification: HPR-P2-009 on 2026-10-09, source parent `44d4fe773d69ed51dd90820140c8d9e7aee6cba2`. HPR-P2-001 established the governance baseline; individual untouched sets retain their own applicability. Post-publication validation belongs to the resulting exact-head documentation run.

## Ownership

Documentation authority follows the repository governance and the owner/decision authorities already recorded in individual canonical documents and the Ultimate Roadmap. No additional named document owner is established by HPR-P2-001.

## Domain Register

| Canonical area | Current register status | Authority / applicability | Next canonicalization work |
|---|---|---|---|
| `doc/README.md` | CURRENT | Repository documentation entry point and precedence/navigation control | Maintain as domains are added |
| `doc/governance/**` | CURRENT | Documentation governance, lifecycle, status and register controls | HPR-P2-001 complete |
| `doc/roadmap/ULTIMATE_ROADMAP.md` | CURRENT | Sole platform-finalization execution authority | Maintain per executed HPR |
| `doc/security/**` | CURRENT where individually stated | Canonical P0 security baseline and approved security procedures | No P2 replacement implied by HPR-P2-001 |
| `doc/architecture/**` | CURRENT where individually stated | Canonical HPR-P2-002 architecture set; `RUNTIME_ARCHITECTURE.md` is historical P1 provenance | HPR-P2-002 complete |
| `doc/operations/**` | CURRENT where individually stated | P1 operational, HA, DR, deployment, observability and survivability evidence | Preserve; later tasks may cross-link |
| `doc/database/**` | CURRENT where individually stated | Canonical HPR-P2-006 database architecture, ownership, Flyway policy and generated persistence dictionary; earlier P1 stage docs retained as provenance | HPR-P2-006 complete |
| `doc/domain/**` | CURRENT where individually stated | Canonical ubiquitous language, ownership and focused semantic baseline from current source | HPR-P2-003 complete; HPR-P2-009 refreshed lasting semantics |
| `doc/modules/**` | CURRENT | Canonical current-state documentation for all 24 implemented module roots; excludes non-implemented agents/environment/otsecurity | HPR-P2-004 complete; HPR-P2-009 refreshed inventories/decisions |
| `doc/api/**` | CURRENT | Canonical OpenAPI snapshot plus current source-backed query/correlation/error rules; older untouched guides retain generation applicability | HPR-P2-005 audit correction implemented; exact-head documentation CI PENDING; P2 OPEN |
| `doc/model-remediation/**` | CURRENT | Exact-current-source reconciliation of legacy HMR/HMSR execution obligations; legacy `docs/roadmap/model-semantic-remediation.md` remains history | HPR-P2-007/008 complete; HPR-P2-009 transfers lasting rules into domain/module docs |
| `doc/data/**` | CURRENT | Five-document HPR-P2-010 governance baseline at source parent `b36733fc05e789613485606e1e1dd1731b11af53`; approved infrastructure controls and source-backed provenance separated from TARGET admission and unknown business decisions | HPR-P2-010 complete |
| `doc/testing/**` | CURRENT | Six-document HPR-P2-011 source-backed strategy, architecture/database/API testing and requirements evidence; source parent `35d9d949aa773a4d754c22d00181330f579f752b`, prior CI and execution limits explicit | HPR-P2-011 complete |
| Documentation CI drift controls | CURRENT | [Implemented validation controls](DOCUMENTATION_VALIDATION.md): reviewed inventory/metadata/index/module/P2 consistency, deterministic generated snapshot and strict runtime equality | HPR-P2-012 complete; exact-head CI follows publication |

## Status Interpretation

- `CURRENT where individually stated` means this register does not replace the document's own status or applicability statement.
- `NOT ESTABLISHED` means the later canonical set has not been created; it is not a claim that all underlying implementation is absent.
- `PARTIAL` is register-level descriptive metadata only and is not a new document-status vocabulary value for individual canonical documents.

## Historical / Legacy Estate

Everything under `docs/**` remains preserved as legacy/reference/evidence material. It can be cited for provenance after reconciliation, but it is not current platform-finalization execution authority.

Legacy material must not be mass-rewritten to match present state. Supersession changes authority, not history.

## Current vs Target Discipline

Existing implementation claims require current repository/runtime/migration/test/CI evidence. Target or deferred architecture must be labelled as such. HPR-P2-001 does not promote any P2 architecture, domain, module, API, database, data-governance or testing target to CURRENT.

## Permanent semantic transfer

[SEMANTIC_DECISIONS.md](../domain/SEMANTIC_DECISIONS.md) is CURRENT for the verified
source parent: 123 unique historical subjects, 19/104 historical dispositions and
104 subject-HMR mappings. It indexes lasting rules and current evidence rather than
replacing the roadmap or cloning execution statuses. [Domain](../domain/README.md)
and all 24 [module documents](../modules/README.md) use the same verification parent.
Legacy reviews/roadmaps remain byte-preserved execution/review history. The closed
57-row reconciliation is distinct from the complete historical catalogue.

Approved policy/mapping values, runtime inference/actuation, populated production-data
acceptance and physical survivability are not established by the transfer. Later
governance gates remain pending; this register does not close P2.

## Data governance baseline

The [data index](../data/README.md) registers [ownership](../data/DATA_GOVERNANCE.md),
[retention/archival](../data/RETENTION_ARCHIVAL.md), [provenance](../data/DATA_PROVENANCE.md)
and [legacy-data migration governance](../data/LEGACY_DATA_MIGRATION.md). All five are
CURRENT documentation at source parent `b36733fc05e789613485606e1e1dd1731b11af53`,
checked on 2026-10-09.
Business durations, source-owner/reuse approvals, canonical dataset precedence,
complete enforcement and executable import acceptance remain NOT ESTABLISHED; TARGET
admission requirements do not approve data or create workers. Untouched database and
operational sets retain their own historical applicability. HPR-P2-013 remains
pending, P3 deferred and P2 open. Exact-head documentation CI follows publication.

## Verification documentation baseline

[Testing index](../testing/README.md) registers [strategy](../testing/TEST_STRATEGY.md),
[architecture](../testing/ARCHITECTURE_TESTING.md), [database](../testing/DATABASE_TESTING.md),
[API](../testing/API_TESTING.md) and [requirements traceability](../testing/REQUIREMENTS_TRACEABILITY.md).
All six are CURRENT documentation at source parent
`35d9d949aa773a4d754c22d00181330f579f752b`, checked on 2026-10-09. The matrix
covers all 24 module roots and 57 closed HMR obligations with actual source evidence;
it does not clone the roadmap or invent executed cases/coverage. Prior full CI #604
and parent documentation CI #121 passed; no local Maven/runtime campaign occurred.
Per-class retained report/skip inspection, complete endpoint/performance coverage,
current deployed-data acceptance and fresh physical evidence are NOT ESTABLISHED.
Untouched canonical/legacy sources keep their own applicability; HPR-P2-013
remains pending and P2 open. Exact-head documentation CI follows publication.

## HPR-P2-012 validation applicability

Source parent `508337351eef03b82e2c6078a7c8523013efd063`, checked 2026-10-09. [Validation guide](DOCUMENTATION_VALIDATION.md) registers both maintained Python validators/tests and the reviewed manifest. The API snapshot is refreshed solely from verified successful CI #604 generation evidence; older API/P1 documentation retains its own applicability. Validation is bounded structural/governance evidence, not new business/physical approval. HPR-P2-013 remains pending and P2 open; exact-head production and documentation gates follow publication.

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

## HPR-P2-005 corrected API applicability — 2026-10-09

Verified source parent: `b39c87dcaf887ada1d22c6a88d1e1f51b9f7ecaa`;
preflight Documentation Validation #130 PASSED. [API index](../api/README.md),
[conventions](../api/API_CONVENTIONS.md) and [error model](../api/ERROR_MODEL.md)
identify actual Workbench/Telemetry paging and DTO differences, tracing headers,
global MVC mappings and five scoped advice overrides. Runtime facts remain distinct
from the unchanged generated contract and its missing common error-envelope coverage.
No universal page/filter/error-body policy is inferred.

Current execution disposition follows the Ultimate Roadmap: HPR-P2-005 correction
implemented and IN PROGRESS pending its exact-head documentation CI; HPR-P2-006 and
HPR-P2-013 PENDING. P2 remains OPEN; P3 DEFERRED. Earlier area-complete/closed prose
retains historical applicability and does not supersede this current disposition.
Database dictionary/ownership policy completeness remains unresolved under audit
Checks 10–11; this API correction does not remediate or reverify those documents.
All 57 HMR implementations and 123 semantic subjects retain their closed identities.
