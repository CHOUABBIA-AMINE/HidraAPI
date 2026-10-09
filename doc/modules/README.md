# HidraAPI Canonical Module Documentation

## Status

CURRENT — canonical module inventory and HPR-P2-009 permanent semantic index.

## Verification Baseline

Source baseline: `44d4fe773d69ed51dd90820140c8d9e7aee6cba2`

HPR-P2-004 established this set; HPR-P2-009 refreshes every inventory and transfers lasting semantics at the parent above, verified on 2026-10-09. The executable source tree is unchanged by this documentation task.

## Implemented Modules

| Module | Canonical document |
|---|---|
| Alarm | [alarm](alarm.md) |
| Analytics | [analytics](analytics.md) |
| Assets | [assets](assets.md) |
| Audit | [audit](audit.md) |
| Configuration | [configuration](configuration.md) |
| Custody | [custody](custody.md) |
| Documents | [documents](documents.md) |
| HSE | [hse](hse.md) |
| Identity | [identity](identity.md) |
| Incident | [incident](incident.md) |
| Integration | [integration](integration.md) |
| Integrity | [integrity](integrity.md) |
| Leak Detection | [leakdetection](leakdetection.md) |
| Monitoring | [monitoring](monitoring.md) |
| Notification | [notification](notification.md) |
| Organization | [organization](organization.md) |
| Party | [party](party.md) |
| Planning | [planning](planning.md) |
| Reporting | [reporting](reporting.md) |
| Risk | [risk](risk.md) |
| Simulation | [simulation](simulation.md) |
| Telemetry | [telemetry](telemetry.md) |
| Topology | [topology](topology.md) |
| Workflow | [workflow](workflow.md) |

Exactly **24** current module documents are registered here.

## Boundary Rules

Each current module root contains `api/`, `application/`, `domain/` and `infrastructure/`. Module internals remain private except deliberately exported application contracts documented by the canonical architecture set.

These module documents are source inventories and responsibility summaries. They do not replace:

- `doc/domain/**` for canonical ubiquitous language and cross-domain semantic rules;
- HPR-P2-005 for deterministic OpenAPI and API compatibility/auth/error/versioning documentation;
- HPR-P2-006 for schema ownership, Flyway policy and data dictionary;
- [reconciliation](../model-remediation/RECONCILIATION.md) for the completed HPR-P2-008 execution evidence;
- [permanent semantic decisions](../domain/SEMANTIC_DECISIONS.md) for 123 reviewed subjects and their exact source/test/migration evidence.

## Not Implemented as Current Module Roots

No current-state module document exists for:

- `agents`;
- `environment`;
- `otsecurity`.

They are not present as current `dz.sh.hidra.modules.*` source roots and must not be represented as implemented modules without future approved implementation evidence.

## Metadata inheritance and verification

Each module document inherits CURRENT applicability at the exact source parent above,
accountability of its owning business module, source evidence through file links and
last verification point HPR-P2-009 / 2026-10-09. TARGET and DEFERRED material is visibly
separate. No named human owner, operational retention value or unapproved policy
mapping is invented. Unresolved business facts remain NOT ESTABLISHED or delegated
to their already registered future tasks.

Inventories: 24 modules; 148 domain/model Java files (124 types and 24 package-info
files); 470 `@Entity` classes; 70 application/contract Java files excluding package-info;
63 exported packages matching both architecture guardrails; 139 unique migration
versions. Class inventories exclude package-info, so their totals are not Java-file
counts. Private owned entities exceed the reviewed domain population and are not
silently assigned legacy review IDs. Imported owner-contract inventories describe
source dependencies, not proof of provider availability in an actual deployment.

No local Maven verification was required or run for this documentation-only change.
Prior cumulative Java 21 production CI #604 passed on
`617c2eec812e3a5734957ee9fa0360f6f5613032`; current source/test evidence is inspected
separately from that historical runtime result.

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
