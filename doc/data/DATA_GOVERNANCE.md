# HidraAPI Data Governance Baseline

## Status and applicability

CURRENT — HPR-P2-010 source-derived governance baseline, verified on 2026-10-09
against parent `b36733fc05e789613485606e1e1dd1731b11af53`. Metadata and ownership follow [the data index](README.md).
TARGET admission requirements below are visibly separated from implemented controls;
unknown owner approvals and business values remain NOT ESTABLISHED.

## Current ownership and data responsibilities

The categories below describe source responsibilities, not an invented confidentiality
classification. Master/reference identity, raw facts, derived output, security state
and immutable evidence can coexist in one module. The linked module/subject rules
remain authoritative for the exact write path, eligibility and nullable references.

| Owning module | Source-backed data responsibility | Canonical evidence |
|---|---|---|
| alarm | Formal alarms and raised/action/closure/shelving lifecycle evidence | [alarm module](../modules/alarm.md#permanent-semantic-decisions) |
| analytics | Curated dataset/version and lineage metadata; metrics, projections and advisory insights | [analytics module](../modules/analytics.md#permanent-semantic-decisions) |
| assets | Maintainable identity, condition observations and maintenance work orders | [assets module](../modules/assets.md#permanent-semantic-decisions) |
| audit | Append-only event/access/before-after evidence and controlled exports; retention metadata | [audit module](../modules/audit.md#permanent-semantic-decisions) |
| configuration | Governed setting definitions, environment/scoped values and feature flags | [configuration module](../modules/configuration.md#permanent-semantic-decisions) |
| custody | Official transfer periods/tickets, reconciliation and discrepancy evidence; product identity | [custody module](../modules/custody.md#permanent-semantic-decisions) |
| documents | Controlled document identity, content versions, storage/checksum/retention metadata and target links | [documents module](../modules/documents.md#permanent-semantic-decisions) |
| hse | Cases, permits, CAPA and governed closure evidence | [hse module](../modules/hse.md#permanent-semantic-decisions) |
| identity | Security identities, credential/provider/session metadata, grants and authorization decisions | [identity module](../modules/identity.md#permanent-semantic-decisions) |
| incident | Incident identity, response work, relationships, resolution and closure evidence | [incident module](../modules/incident.md#permanent-semantic-decisions) |
| integration | External-system registry, exchange/run provenance and dead-letter resolution evidence | [integration module](../modules/integration.md#permanent-semantic-decisions) |
| integrity | Integrity programs, assessments, defects and technical cases | [integrity module](../modules/integrity.md#permanent-semantic-decisions) |
| leakdetection | Suspected leak candidates, controlled cases and neutral escalation evidence | [leakdetection module](../modules/leakdetection.md#permanent-semantic-decisions) |
| monitoring | Operational rule/evaluation and actual-versus-planned deviation intelligence | [monitoring module](../modules/monitoring.md#permanent-semantic-decisions) |
| notification | Templates/versions, requests, rendered messages and immutable delivery attempts | [notification module](../modules/notification.md#permanent-semantic-decisions) |
| organization | Internal people/structure, geography, contacts and effective-dated responsibility registry | [organization module](../modules/organization.md#permanent-semantic-decisions) |
| party | External counterparty master identity and effective-dated business roles | [party module](../modules/party.md#permanent-semantic-decisions) |
| planning | Periods, operational plans/revisions, nominations and expected targets | [planning module](../modules/planning.md#permanent-semantic-decisions) |
| reporting | Formal report definition/request/run and generated artifact evidence | [reporting module](../modules/reporting.md#permanent-semantic-decisions) |
| risk | Risk registers, scoring matrices, governed assessments and owner-proven evidence links | [risk module](../modules/risk.md#permanent-semantic-decisions) |
| simulation | Model/scenario/run, optimization candidate and advisory recommendation evidence | [simulation module](../modules/simulation.md#permanent-semantic-decisions) |
| telemetry | Source/point identity, raw readings, assessment/trust and downstream trusted evidence | [telemetry module](../modules/telemetry.md#permanent-semantic-decisions) |
| topology | Physical/logical network identities, graph connections and owned catalog references | [topology module](../modules/topology.md#permanent-semantic-decisions) |
| workflow | Process definition, instance/task and append-only action/state history | [workflow module](../modules/workflow.md#permanent-semantic-decisions) |

Topology physical identity, Assets maintainability, Organization responsibility,
Identity security users and Party counterparties remain separate. A consumer snapshot
or scalar owner reference does not transfer aggregate ownership. Owner existence,
active eligibility, context compatibility and owner-confirmed approval are separate
facts, as documented in [permanent semantics](../domain/SEMANTIC_DECISIONS.md).

## Current controls and their limits

- The supported actor-sensitive paths obtain authenticated Identity evidence and
  canonical snapshots. Caller IDs/headers cannot stand in for that authority; each
  subject's actual path is linked in [provenance](DATA_PROVENANCE.md).
- Audit input sanitation and reference-only secret controls apply on their coded
  paths. They do not make arbitrary legacy attachments safe or prove every persisted
  record has the same sanitation. Existing [trust boundaries](../security/TRUST_BOUNDARIES.md) and [secret controls](../security/SECRETS_AND_CERTIFICATES.md) retain their own verification baselines.
- [AuditInputPolicy](../../src/main/java/dz/sh/hidra/modules/audit/application/service/AuditInputPolicy.java) bounds/sanitizes Audit inputs. Business-data permissions remain owned by existing
  authorization/use cases; governance creates no grants or roles.
- Analytics/Simulation outputs remain advisory under their source boundaries;
  curated/trusted labels are not universal approval to mutate operational truth.
- Backup recovery copies and observability stores have their own approved controls;
  business records require their own [retention decisions](RETENTION_ARCHIVAL.md).

## TARGET governance decision record

For a future dataset or policy approval, record the actual owning module and accountable
owner decision, immutable source/version, intended use, approved audience and reuse
limits, data meaning/quality, permitted write contract, retention/hold/disposal decision,
approval evidence and verification point. These are admission requirements, not new
implemented policy fields or a prescribed enterprise classification vocabulary.

## NOT ESTABLISHED

No named human steward, blanket confidentiality label, legal retention period,
universal lineage completeness, dataset import approval or extra access right is
established here. Unknown facts remain explicit and require their real owner; source
repository custody alone cannot supply business authority. There is no new operational
standard, privacy-law opinion or destructive data operation in this documentation task.
