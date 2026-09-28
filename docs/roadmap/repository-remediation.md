# HidraAPI Repository Remediation Roadmap

**Roadmap namespace:** `HRA-*`  
**Scope:** repository-wide remediation derived from the forensic static audit of the supplied source snapshot.  
**Execution rule:** follow `AGENTS.md`; execute exactly one roadmap code per task and use the exact commit message defined here.

## 1. Governing rules

1. Evidence before deletion: a zero-incoming Java reference is a deletion candidate, not proof that reflection, configuration, SQL, serialization, or an external consumer does not exist.
2. Data integrity before cleanup: confirmed data-loss defects are fixed before dead-code, DTO, enum, or mirror-model reduction.
3. One coherent roadmap code per commit.
4. Do not mass-add JPA object associations; scalar identifiers may remain where ownership and integrity are explicit.
5. Bounded contexts communicate through deliberately exported contracts rather than another module's internal domain, infrastructure, or private application packages.
6. Do not remove compatibility state before data, consumer, and rollback parity is proven.
7. Guardrails must precede broad refactors.

## 2. Audit baseline

The repository-wide forensic audit established this remediation baseline from raw Java source:

- 4,988 Java files read.
- 4,914 production Java files and 74 tests in the audited snapshot.
- 1,783 production model/enum types.
- 467 domain models.
- 465 JPA entities.
- 431 DTO/request/response/command/query/event/projection records.
- 82 record value objects.
- 338 enums.
- 465 same-named domain/JPA model pairs.
- 451 exact structural domain/JPA clones.
- 393 exact domain/JPA clones with no detected business invariant/meaningful domain behavior beyond normalization/helper plumbing.
- 210 zero-incoming model/enum candidates.
- 100/100 module domain-event records with zero incoming Java references.
- 24 module event-publisher implementations unreferenced/unwired.
- 65 exact REST Request / application Command pairs.
- 59 exact REST Response / application SummaryDto pairs.
- 30 explicit production imports crossing top-level module/platform/kernel boundaries.
- 136 API-to-domain imports, including 125 domain enums.
- 0 unresolved explicit internal imports.
- 0 duplicate JPA table mappings.
- 0 duplicate entity columns inside an entity.
- 0 production explicit-import cycles.

Confirmed high-priority defects include:

1. Organization `Employee` birth fields exist in the domain but are absent from `EmployeeJpaEntity` and are dropped by `OrganizationPersistenceMapper`.
2. Employee REST/application contracts remain aligned to legacy display/contact state rather than the canonical birth/contact/name ownership now present in the domain.
3. Hundreds of domain/JPA pairs allow JPA-required text fields to normalize to null rather than fail in the domain.
4. The domain-event/outbox design is disconnected: module event contracts do not implement the kernel event contract, event publishers are unwired, and platform outbox/serializer contracts lack a complete implementation path.
5. Hundreds of exact domain/JPA mirrors are behaviorless.
6. API/domain representation leakage and cross-module internal-package coupling remain.
7. Java/JPA persistence relies almost entirely on scalar ID references rather than ORM associations; same-module database integrity must therefore be inspected and enforced deliberately rather than guessed.

The authoritative detailed baseline is recorded in
`docs/architecture/forensic-static-audit-baseline.md`.

---

## 3. Phase A — Freeze the baseline and add safety rails

| Code | Exact commit message | Scope | Exit criteria | Prerequisite | Status |
|---|---|---|---|---|---|
| `HRA-001` | `docs(architecture): record forensic remediation baseline` | Version this repository remediation roadmap and the evidence-backed forensic baseline. | Baseline metrics and confirmed defects are versioned; no production behavior changes. | none | **Completed** |
| `HRA-002` | `test(architecture): enforce repository structural guardrails` | Extend architecture/static-analysis guardrails for cross-module private-package imports, existing layer rules, duplicate JPA tables/columns, and deliberate exported-contract allowlists. | Existing main stays green; representative violations can fail the rules. | HRA-001 | **Completed** — added cross-module private-package enforcement, exact transitional exceptions for the audited Planning→Workflow and Organization→Topology dependencies, stale-exception detection, a representative classifier test, and JPA table/column uniqueness guards. HRA-090/HRA-091 must remove the temporary exceptions when exported contracts replace the current imports. |
| `HRA-003` | `test(persistence): detect domain mapper field drift` | Add domain-to-JPA mapper completeness/round-trip protection, beginning with Organization Employee and explicit exclusions for derived/compatibility state. | A canonical domain field silently dropped by persistence makes CI fail. | HRA-002 | **Completed** — added `OrganizationPersistenceDriftGuardrailTest`. The detector compares canonical Employee record components with `EmployeeJpaEntity` fields and inspects both mapper directions. The exact five audited birth fields are the only temporary allowed drift; any additional missing component fails CI. Derived display-name methods are excluded by record-component comparison, while persisted compatibility display/contact components remain guarded until their later cutover. HRA-010/HRA-011 must shrink and then remove the birth-field quarantine as persistence and mapping are repaired. |
| `HRA-004` | `ci: optimize repository verification workflow` | Remove redundant Maven executions from GitHub Actions, retain one wrapper-based `clean verify`, keep PostgreSQL/OpenAPI verification for code changes, and skip the heavy workflow for documentation-only pushes to `main`. | Code changes still run full Maven verification plus deterministic OpenAPI generation; documentation-only pushes do not start the heavy workflow; CI no longer recompiles/retests the repository six times. | HRA-001 | **Completed** |

---

## 4. Phase B — Fix the confirmed Organization Employee data-loss path

| Code | Exact commit message | Scope | Exit criteria | Prerequisite | Status |
|---|---|---|---|---|---|
| `HRA-010` | `fix(organization): persist employee birth fields` | Add `dateOfBirth`, `birthLocalityId`, `birthPlaceAr`, `birthPlaceFr`, `birthPlaceEn` to `EmployeeJpaEntity`. | JPA entity can represent all canonical birth state; no API/schema change yet. | HRA-003 | Planned |
| `HRA-011` | `fix(organization): round trip employee birth data` | Update both directions of `OrganizationPersistenceMapper`; stop using a compatibility constructor where it drops canonical state; add round-trip tests. | Employee -> JPA -> Employee preserves all birth fields. | HRA-010 | Planned |
| `HRA-012` | `refactor(organization): align employee personal data contracts` | Give every Employee birth field an explicit application/REST read/write policy; update registration or add a dedicated personal-data update use case as supported by current design. | No canonical birth field is silently ignored in an intended write/read flow. | HRA-011 | Planned |
| `HRA-013` | `feat(organization): migrate employee birth data schema` | Inspect live Flyway/schema and add only missing immutable schema changes; preserve optional birthplace locality semantics. | PostgreSQL/Hibernate validation and integration round-trip pass. | HRA-012 + live schema evidence | Planned / Evidence-gated |

---

## 5. Phase C — Complete Employee address/contact/display-name ownership

| Code | Exact commit message | Scope | Exit criteria | Prerequisite | Status |
|---|---|---|---|---|---|
| `HRA-020` | `refactor(organization): route employee contacts through contact points` | Make `OrganizationContactPoint` the new-write path for employee EMAIL/MOBILE/PHONE; add missing contact-point application use cases. | New Employee writes no longer require direct Employee email/mobile state. | HRA-013 | Planned |
| `HRA-021` | `data(organization): backfill employee contact points` | Preflight and backfill deterministic legacy email/mobile values into contact points; quarantine ambiguity. | Backfill is idempotent and reconciled; no legacy column deletion. | HRA-020 + live data evidence | Planned / Data-gated |
| `HRA-022` | `refactor(organization): derive employee display names at boundaries` | Stop accepting caller-supplied display names as authoritative; return derived Arabic/Latin display names. | Structured names are authoritative; API tests prove deterministic display output. | HRA-012 | Planned |
| `HRA-023` | `refactor(organization): retire employee compatibility fields` | Remove legacy Employee contact/display Java/schema state only after parity, consumer migration, and rollback evidence. | No direct compatibility persistence remains and no data is lost. | HRA-021 + HRA-022 + consumer signoff | Planned / Cutover-gated |

---

## 6. Phase D — Decide and repair or remove the domain-event architecture

| Code | Exact commit message | Scope | Exit criteria | Prerequisite | Status |
|---|---|---|---|---|---|
| `HRA-030` | `docs(architecture): decide domain event and outbox strategy` | Choose one canonical path: implement events/outbox or remove fictional scaffolding. | ADR/roadmap decision exists; no ambiguous dual path. | HRA-002 | Planned |
| `HRA-031A` | `refactor(events): unify module domain event contract` | If events are retained, reconcile module event interfaces with kernel `DomainEvent`, event IDs, timestamps, and version metadata. | Retained module events are assignable to the canonical event contract. | HRA-030 Path A | Planned |
| `HRA-032A` | `feat(events): implement transactional outbox pipeline` | Implement serializer, outbox repository adapter, publisher, Spring wiring, and transactional tests. | Durable outbox semantics are proven end-to-end. | HRA-031A | Planned |
| `HRA-033A` | `feat(events): publish domain events from state transitions` | Emit retained events from real state-changing use cases and remove replaced in-memory publisher stubs. | Every retained event has a real producer or an explicitly documented external producer. | HRA-032A | Planned |
| `HRA-031B` | `refactor(events): remove unused event scaffolding` | If events are not currently required, remove unreferenced module event records/publishers and unused outbox contracts after consumer verification. | Dead event architecture is gone without breaking external consumers. | HRA-030 Path B | Planned |

---

## 7. Phase E — Remove proven dead Java scaffolding

| Code | Exact commit message | Scope | Exit criteria | Prerequisite | Status |
|---|---|---|---|---|---|
| `HRA-040` | `docs(architecture): classify static orphan candidates` | Classify all 210 zero-incoming candidates as DELETE or evidence-backed KEEP. | Every candidate has an evidence-backed disposition. | HRA-001 | Planned |
| `HRA-041` | `refactor(codebase): remove confirmed orphan types` | Delete only HRA-040 DELETE candidates and update imports/tests/docs. | Full build passes; no deletion is based on filename or intuition alone. | HRA-040 | Planned |

---

## 8. Phase F — Harden domain integrity repository-wide

| Code | Exact commit message | Scope | Exit criteria | Prerequisite | Status |
|---|---|---|---|---|---|
| `HRA-050` | `docs(domain): classify required invariant gaps` | Review domain/JPA nullability mismatches and classify true business requirements vs migration/persistence-only constraints. | Module-by-module invariant matrix exists. | HRA-003 | Planned |
| `HRA-051..N` | `refactor(<module>): enforce domain invariants` | One module per task: required IDs/codes/types/statuses/references, date ordering, self-reference rules where locally decidable. | Invalid domain state fails before persistence; focused tests pass. | HRA-050 | Planned |

Domain constructors must not perform repository lookups, cross-module existence checks, or database uniqueness checks.

---

## 9. Phase G — Make the domain/persistence split intentional

| Code | Exact commit message | Scope | Exit criteria | Prerequisite | Status |
|---|---|---|---|---|---|
| `HRA-060` | `docs(domain): classify anemic persistence mirrors` | Classify the 393 behaviorless exact domain/JPA mirrors as REAL DOMAIN, READ/PERSISTENCE MODEL, or BOUNDARY MODEL. | Every pair has a deliberate disposition. | HRA-051..N substantially complete | Planned |
| `HRA-061..N` | `refactor(<module>): simplify domain persistence model split` | Execute classifications module by module without adding JPA annotations to framework-independent domain records. | Behaviorless mirror count decreases; mapper round-trip guards stay green. | HRA-060 | Planned |

---

## 10. Phase H — Rationalize adjacent-layer DTO duplication

| Code | Exact commit message | Scope | Exit criteria | Prerequisite | Status |
|---|---|---|---|---|---|
| `HRA-070` | `docs(api): classify duplicate boundary contracts` | Review the 65 exact Request/Command and 59 exact Response/SummaryDto pairs. | Every pair is KEEP SEPARATE or approved for consolidation/generated mapping. | HRA-002 | Planned |
| `HRA-071..N` | `refactor(<module>): simplify boundary dto mapping` | Consolidate only approved pairs; preserve API stability/versioning/security transformations. | Duplicate count decreases without wire-contract regressions. | HRA-070 | Planned |

---

## 11. Phase I — Normalize duplicate enum vocabularies

Mandatory review examples include:

- `LeakDetectionProfileStatus` / `LeakDetectionRuleStatus`
- `RoleStatus` / `PermissionStatus`
- `DelegationStatus` / `GrantStatus`
- `ReportTemplateVersionStatus` / `ReportDefinitionStatus`
- `ResponsibilityAssigneeType` / `ContactPointTargetType`
- `MappingProfileStatus` / `ExternalSystemStatus`
- `SchemaVersionStatus` / `ContractStatus`

| Code | Exact commit message | Scope | Exit criteria | Prerequisite | Status |
|---|---|---|---|---|---|
| `HRA-080` | `docs(domain): review duplicate enum vocabularies` | Make semantic KEEP/MERGE/DELETE decisions; identical constants alone do not justify merging. | Every reviewed pair has an explicit semantic decision. | HRA-040 | Planned |
| `HRA-081` | `refactor(domain): remove redundant enum vocabularies` | Apply only approved merges/deletions with persistence/API compatibility handling. | No persisted literal or API contract changes silently. | HRA-080 | Planned |

---

## 12. Phase J — Replace cross-module internal-package coupling

| Code | Exact commit message | Scope | Exit criteria | Prerequisite | Status |
|---|---|---|---|---|---|
| `HRA-090` | `refactor(workflow): expose planning workflow contract` | Export Workflow contracts consumed by Planning; stop Planning importing Workflow's internal application package. | Planning depends only on deliberate Workflow contract packages; ArchUnit enforces it. | HRA-002 | Planned |
| `HRA-091` | `refactor(topology): expose operational scope resolution contract` | Export the Topology scope-resolution contract consumed by Organization. | Organization no longer imports Topology's internal application package. | HRA-002 | Planned |

---

## 13. Phase K — Stop API/domain representation leakage

| Code | Exact commit message | Scope | Exit criteria | Prerequisite | Status |
|---|---|---|---|---|---|
| `HRA-100` | `refactor(planning): return application revision result` | Return an application result DTO from plan-revision updates rather than a domain `PlanRevision` to REST. | Planning controller no longer depends on the domain model for this flow. | HRA-002 | Planned |
| `HRA-101` | `refactor(identity): isolate authentication web contract` | Adapt Spring principal/session details into application-level authentication inputs/results. | Authentication controller no longer exposes unnecessary domain representation. | HRA-002 | Planned |
| `HRA-102` | `docs(api): define domain enum exposure policy` | Decide whether REST exposure of domain enums is an intentional wire contract; define mapping policy. | Architecture guardrail can distinguish permitted values from forbidden domain-model leakage. | HRA-002 | Planned |

---

## 14. Phase L — Make scalar-ID referential integrity explicit

| Code | Exact commit message | Scope | Exit criteria | Prerequisite | Status |
|---|---|---|---|---|---|
| `HRA-110` | `docs(persistence): inventory scalar reference integrity` | Classify non-primary entity `*Id` fields as same-module references, cross-module stable references, historical snapshots, external IDs, or non-relational identifiers. | Every mandatory scalar reference has a documented integrity owner. | HRA-001 | Planned |
| `HRA-111..N` | `fix(<module>): enforce internal reference integrity` | Verify/add fail-closed same-module FK/check constraints and Testcontainers coverage; never add cross-module DB FKs. | Mandatory internal references are protected at domain/application and DB layers. | HRA-110 + live schema evidence | Planned |

---

## 15. Final closure

| Code | Exact commit message | Scope | Exit criteria | Prerequisite | Status |
|---|---|---|---|---|---|
| `HRA-120` | `test(architecture): close forensic remediation baseline` | Re-run the same forensic methodology and compare against the baseline. | No confirmed data loss; no unapproved cross-module private coupling; no unclassified orphan; no retained fictional event architecture; all remaining duplication is deliberate/documented. | accepted remediation tracks | Planned |

## 16. Recommended execution order

```text
HRA-001
  ↓
HRA-004
  ↓
HRA-002
  ↓
HRA-003
  ↓
HRA-010 → HRA-011 → HRA-012 → HRA-013
                         ↓
                HRA-020 / HRA-022
                    ↓       ↓
                  HRA-021   |
                    ↓       |
                    └─→ HRA-023

After HRA-002:
HRA-030 → Path A or Path B
HRA-040 → HRA-041
HRA-070 → HRA-071..N
HRA-090 / HRA-091
HRA-100 / HRA-101 / HRA-102

After mapper/invariant safety rails:
HRA-050 → HRA-051..N → HRA-060 → HRA-061..N

After orphan classification:
HRA-080 → HRA-081

After live schema inspection:
HRA-110 → HRA-111..N

Finally:
HRA-120
```

## 17. Current execution point

`HRA-001` and `HRA-004` are complete. HRA-004 was explicitly prioritized to remove CI duplication before the high-frequency remediation sequence.

**Next task:** `HRA-010 — fix(organization): persist employee birth fields`.

Do not begin broad dead-code deletion, event deletion, DTO consolidation, or domain/JPA mirror reduction before the HRA-002/HRA-003 safety rails exist.
