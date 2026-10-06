# HidraAPI Canonical Module Documentation

## Status

CURRENT — canonical HPR-P2-004 module index.

## Verification Baseline

Source baseline: `f3c703048402f3dcf1a520aceea64e64c4861035`

The executable/source tree is unchanged by HPR-P2-004; these documents inventory the live module roots at that baseline.

## Implemented Modules

| Module | Canonical document |
|---|---|
| Alarm | `alarm.md` |
| Analytics | `analytics.md` |
| Assets | `assets.md` |
| Audit | `audit.md` |
| Configuration | `configuration.md` |
| Custody | `custody.md` |
| Documents | `documents.md` |
| HSE | `hse.md` |
| Identity | `identity.md` |
| Incident | `incident.md` |
| Integration | `integration.md` |
| Integrity | `integrity.md` |
| Leak Detection | `leakdetection.md` |
| Monitoring | `monitoring.md` |
| Notification | `notification.md` |
| Organization | `organization.md` |
| Party | `party.md` |
| Planning | `planning.md` |
| Reporting | `reporting.md` |
| Risk | `risk.md` |
| Simulation | `simulation.md` |
| Telemetry | `telemetry.md` |
| Topology | `topology.md` |
| Workflow | `workflow.md` |

Exactly **24** current module documents are registered here.

## Boundary Rules

Each current module root contains `api/`, `application/`, `domain/` and `infrastructure/`. Module internals remain private except deliberately exported application contracts documented by the canonical architecture set.

These module documents are source inventories and responsibility summaries. They do not replace:

- `doc/domain/**` for canonical ubiquitous language and cross-domain semantic rules;
- HPR-P2-005 for deterministic OpenAPI and API compatibility/auth/error/versioning documentation;
- HPR-P2-006 for schema ownership, Flyway policy and data dictionary;
- HPR-P2-007/008 for exact-current-source reconciliation and execution of unresolved HMR/HMSR obligations.

## Not Implemented as Current Module Roots

No current-state module document exists for:

- `agents`;
- `environment`;
- `otsecurity`.

They are not present as current `dz.sh.hidra.modules.*` source roots and must not be represented as implemented modules without future approved implementation evidence.
