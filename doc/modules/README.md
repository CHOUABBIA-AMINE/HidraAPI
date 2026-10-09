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
