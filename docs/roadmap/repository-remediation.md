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
| `HRA-010` | `fix(organization): persist employee birth fields` | Add `dateOfBirth`, `birthLocalityId`, `birthPlaceAr`, `birthPlaceFr`, `birthPlaceEn` to `EmployeeJpaEntity`. | JPA entity can represent all canonical birth state; no API/schema change yet. | HRA-003 | **Completed** — `EmployeeJpaEntity` now represents all five canonical birth fields. Live Flyway inspection confirmed the backing columns do not yet exist, so the fields are deliberately `@Transient` until HRA-013 performs the immutable schema migration. A deprecated delegating constructor preserves the pre-HRA-011 mapper call shape without fabricating birth data; HRA-011 must now wire both mapper directions. The HRA-003 guardrail was tightened: domain/entity shape drift is now zero, while the exact five birth fields remain the only mapper drift. |
| `HRA-011` | `fix(organization): round trip employee birth data` | Update both directions of `OrganizationPersistenceMapper`; stop using a compatibility constructor where it drops canonical state; add round-trip tests. | Employee -> JPA -> Employee preserves all birth fields. | HRA-010 | **Completed** — both mapper directions now carry `dateOfBirth`, `birthLocalityId`, `birthPlaceAr`, `birthPlaceFr`, and `birthPlaceEn`; the temporary HRA-010 compatibility constructor was removed; a concrete Employee -> JPA -> Employee round-trip test preserves the complete record; HRA-003 now requires zero mapper drift. The five fields remain `@Transient` only until HRA-013 adds database columns. |
| `HRA-012` | `refactor(organization): align employee personal data contracts` | Give every Employee birth field an explicit application/REST read/write policy; update registration or add a dedicated personal-data update use case as supported by current design. | No canonical birth field is silently ignored in an intended write/read flow. | HRA-011 | **Completed** — the existing registration flow is the current Employee creation/write path, so `dateOfBirth`, `birthLocalityId`, `birthPlaceAr`, `birthPlaceFr`, and `birthPlaceEn` now flow through REST request → application command → Employee creation → application summary → REST response. Focused application and REST-mapper tests protect the contract. Legacy caller-supplied display/contact fields remain intentionally unchanged for HRA-020/HRA-022 rather than being removed prematurely. |
| `HRA-013` | `feat(organization): migrate employee birth data schema` | Inspect live Flyway/schema and add only missing immutable schema changes; preserve optional birthplace locality semantics. | PostgreSQL/Hibernate validation and integration round-trip pass. | HRA-012 + live schema evidence | **Completed** — live Flyway inspection proved all five Employee birth columns were absent after `V20260927_005`. Added immutable `V20260928_001` with nullable `date_of_birth`, `birth_locality_id`, `birth_place_ar`, `birth_place_fr`, and `birth_place_en`; added an index and same-module FK from optional `birth_locality_id` to `hidra_org_administrative_locality(id)` with `ON DELETE RESTRICT`. `EmployeeJpaEntity` now uses real `@Column` mappings instead of `@Transient`, HRA-003 verifies the exact column names, and a Testcontainers PostgreSQL migration test verifies schema shape, persistence, and orphan-locality rejection. |

---

## 5. Phase C — Complete Employee address/contact/display-name ownership

| Code | Exact commit message | Scope | Exit criteria | Prerequisite | Status |
|---|---|---|---|---|---|
| `HRA-020` | `refactor(organization): route employee contacts through contact points` | Make `OrganizationContactPoint` the new-write path for employee EMAIL/MOBILE/PHONE; add missing contact-point application use cases. | New Employee writes no longer require direct Employee email/mobile state. | HRA-013 | **Completed** — added a validated `CreateOrganizationContactPointUseCase` and application service over the existing contact-point repository/target validator. Employee registration remains wire-compatible with email/mobile inputs but persists new Employee compatibility contact fields as `null` and writes supplied EMAIL/MOBILE values as canonical active `OrganizationContactPoint` records targeting the newly created employee. Registration is transactional so employee/contact writes share one transaction. Existing legacy Employee contact columns/read access remain untouched for HRA-021/HRA-023. CI repair: both transactional Spring application services are intentionally non-final so Spring CGLIB transaction proxies can be created, matching the repository's established transactional-service convention. |
| `HRA-021` | `data(organization): backfill employee contact points` | Preflight and backfill deterministic legacy email/mobile values into contact points; quarantine ambiguity. | Backfill is idempotent and reconciled; no legacy column deletion. | HRA-020 + live data evidence | **Completed with repository-evidence gate** — repository inspection found the legacy `hidra_org_employee.email_address/mobile_number` columns but no production/seed Employee contact dataset, so no precedence rule was invented. `V20260928_002` trims and backfills EMAIL/MOBILE only when the employee has no existing canonical contact point of that type; exact or conflicting existing contacts are never updated or duplicated, and conflicting legacy values remain in the retained Employee columns as the explicit quarantine surface. IDs are deterministic and `ON CONFLICT` protected, making the data statements idempotent. PostgreSQL/Testcontainers tests cover unambiguous backfill, exact-match reconciliation, conflict quarantine, blank suppression, deterministic ID length, and re-execution idempotency. No legacy column is deleted. |
| `HRA-022` | `refactor(organization): derive employee display names at boundaries` | Stop accepting caller-supplied display names as authoritative; return derived Arabic/Latin display names. | Structured names are authoritative; API tests prove deterministic display output. | HRA-012 | **Completed** — the registration REST/application write contracts no longer accept `displayNameAr/displayNameLt`; new Employee writes set the legacy compatibility fields to `null`. `OrganizationApplicationMapper` returns `Employee.arabicDisplayName()` / `latinDisplayName()`, and the JPA administration query adapter derives the same values from structured Arabic/Latin name columns instead of reading legacy display columns. Directory search also uses structured/derived names. Tests prove stale compatibility display values cannot override boundary output and partial structured names derive deterministically. Legacy persistence columns remain in place for the HRA-023 cutover gate. |
| `HRA-023` | `refactor(organization): retire employee compatibility fields` | Remove legacy Employee contact/display Java/schema state only after parity, consumer migration, and rollback evidence. | No direct compatibility persistence remains and no data is lost. | HRA-021 + HRA-022 + consumer signoff | **Blocked / Cutover gate not satisfied** — HRA-021 intentionally preserves conflicting legacy email/mobile values and repository evidence contains no production reconciliation proving parity. Live Organization read contracts still expose direct Employee `emailAddress`/`mobileNumber` through `EmployeeSummaryDto`/`EmployeeResponse` and `OrganizationAdministrationQueryUseCase.EmployeeView`, with `JpaOrganizationAdministrationQueryAdapter` reading the legacy columns. The domain model, JPA entity, persistence mapper, and HRA-003 guardrail still retain the four compatibility fields by design. Repository search found no explicit consumer signoff and no approved rollback/recovery evidence authorizing destructive removal. No Java field or database column is deleted until those gates are closed. |

---

## 6. Phase D — Decide and repair or remove the domain-event architecture

| Code | Exact commit message | Scope | Exit criteria | Prerequisite | Status |
|---|---|---|---|---|---|
| `HRA-030` | `docs(architecture): decide domain event and outbox strategy` | Choose one canonical path: implement events/outbox or remove fictional scaffolding. | ADR/roadmap decision exists; no ambiguous dual path. | HRA-002 | **Completed — Path B selected by ADR-0006.** Source/live-main evidence shows 100 unreferenced module event records, 24 incompatible module event interfaces, 24 publisher ports, 24 in-memory publisher implementations, and no serializer/outbox repository/publisher implementation or runtime event emission. HidraAPI will remove disconnected event/outbox scaffolding instead of implementing infrastructure without a proven producer/consumer requirement. |
| `HRA-031A` | `refactor(events): unify module domain event contract` | If events are retained, reconcile module event interfaces with kernel `DomainEvent`, event IDs, timestamps, and version metadata. | Retained module events are assignable to the canonical event contract. | HRA-030 Path A | **Skipped — ADR-0006 selected Path B.** |
| `HRA-032A` | `feat(events): implement transactional outbox pipeline` | Implement serializer, outbox repository adapter, publisher, Spring wiring, and transactional tests. | Durable outbox semantics are proven end-to-end. | HRA-031A | **Skipped — ADR-0006 selected Path B; no proven runtime consumer justifies outbox implementation.** |
| `HRA-033A` | `feat(events): publish domain events from state transitions` | Emit retained events from real state-changing use cases and remove replaced in-memory publisher stubs. | Every retained event has a real producer or an explicitly documented external producer. | HRA-032A | **Skipped — ADR-0006 selected Path B.** |
| `HRA-031B` | `refactor(events): remove unused event scaffolding` | If events are not currently required, remove unreferenced module event records/publishers and unused outbox contracts after consumer verification. | Dead event architecture is gone without breaking external consumers. | HRA-030 Path B | **Completed** — final live-main consumer verification found no application/service imports of module event packages and no outbox implementations/runtime consumers. Removed the closed generic-event subgraph: 100 module event records, 24 incompatible module event interfaces, 24 module event package descriptors, 24 publisher ports, 24 in-memory publisher implementations, 23 now-empty module messaging package descriptors, and 9 unused platform outbox/messaging source contracts/models/package descriptors. `notification`'s real `AsyncNotificationPushAdapter`, persisted lifecycle/audit business records, workflow audit-outbox-reference business state, kernel event primitives, and dormant platform configuration were explicitly retained. No database migration was edited or dropped. |

---

## 7. Phase E — Remove proven dead Java scaffolding

| Code | Exact commit message | Scope | Exit criteria | Prerequisite | Status |
|---|---|---|---|---|---|
| `HRA-040` | `docs(architecture): classify static orphan candidates` | Classify all 210 zero-incoming candidates as DELETE or evidence-backed KEEP. | Every candidate has an evidence-backed disposition. | HRA-001 | **Completed** — `docs/architecture/static-orphan-classification.md` reconciles all 210 forensic zero-incoming candidates: 11 evidence-backed KEEP and 199 DELETE. The 199 DELETE count includes the 100 event records already removed by HRA-031B plus 99 live non-event candidates. KEEP requires explicit repository evidence (kernel/module data definitions, workflow roadmap, or HRA-080 semantic-review ownership). HRA-041 is constrained to DELETE candidates that still have zero consumers on its exact execution head. |
| `HRA-041` | `refactor(codebase): remove confirmed orphan types` | Delete only HRA-040 DELETE candidates and update imports/tests/docs. | Full build passes; no deletion is based on filename or intuition alone. | HRA-040 | **Completed** — exact-head verification rechecked all 99 live HRA-040 DELETE candidates. `LeakCaseView` and `LeakCandidateView` were retained/reclassified KEEP because the leak-detection query port, controller, and service consume them. The remaining 97 zero-consumer candidates were removed. Together with the 100 event records already removed by HRA-031B, all 197 final DELETE dispositions are now physically absent. Classification is corrected to 13 KEEP / 197 DELETE; no deletion was based on filename alone. |

---

## 8. Phase F — Harden domain integrity repository-wide

| Code | Exact commit message | Scope | Exit criteria | Prerequisite | Status |
|---|---|---|---|---|---|
| `HRA-050` | `docs(domain): classify required invariant gaps` | Review domain/JPA nullability mismatches and classify true business requirements vs migration/persistence-only constraints. | Module-by-module invariant matrix exists. | HRA-003 | **Completed** — `docs/architecture/domain-invariant-gap-classification.md` classifies all 465 same-named domain/JPA pairs and 3,385 matched JPA `nullable=false` fields. HRA-051 exact-head revalidation reconciled the aggregate classification before code changes: 2,025 required-field guards, 76 not-yet-enforced temporal rules, and 6 not-yet-enforced self-reference rules remain (2,107 new checks); 379 required fields are already guarded, 326 unguarded generic-text constraints and 655 audit timestamps remain persistence-only. |
| `HRA-051` | `refactor(codebase): enforce classified domain invariants` | **Single consolidated repository-wide task / single commit.** Apply every HRA-050-approved invariant gap across all affected modules in one batch: required IDs/codes/types/statuses/references, locally decidable date ordering, and self-reference rules. Do not add repository lookups, cross-module existence checks, or database uniqueness checks to domain constructors. | Every HRA-050 ENFORCE disposition is implemented in the same commit; invalid domain state fails before persistence; focused module tests plus repository validation pass; no unclassified invariant change is included. | HRA-050 | **Completed** — exact-head revalidation avoided duplicating 21 Organization invariants already present, then added exactly 2,107 new fail-fast constructor checks across 457 domain records: 2,025 required-field guards, 76 temporal-order guards, and 6 self-reference guards. Added `DomainInvariantGuardrailTest` to lock those source counts. A local Java 21 compilation of 1,130 kernel/domain source files passed with only four pre-existing Employee display-name deprecation warnings. |

Domain constructors must not perform repository lookups, cross-module existence checks, or database uniqueness checks.

**HRA-051 execution policy:** HRA-051 is deliberately consolidated. Once HRA-050 is complete, all approved module-specific invariant repairs are executed together as one repository-wide task and one commit. Do not split HRA-051 by module unless this roadmap is explicitly amended first.

---

## 9. Phase G — Make the domain/persistence split intentional

| Code | Exact commit message | Scope | Exit criteria | Prerequisite | Status |
|---|---|---|---|---|---|
| `HRA-060` | `docs(domain): classify anemic persistence mirrors` | Classify the 393 behaviorless exact domain/JPA mirrors as REAL DOMAIN, READ/PERSISTENCE MODEL, or BOUNDARY MODEL. | Every pair has a deliberate disposition. | HRA-051 complete | Planned |
| `HRA-061` | `refactor(codebase): simplify classified domain persistence mirrors` | **Single consolidated repository-wide task / single commit.** Apply every HRA-060-approved mirror simplification across all affected modules in one batch. Preserve framework-independent domain models, do not add JPA annotations to domain records, and change only pairs explicitly approved by HRA-060. | Every HRA-060 simplification disposition selected for implementation is completed in the same commit; behaviorless mirror count decreases as classified; mapper round-trip guardrails and repository validation remain green; no unclassified mirror is changed. | HRA-060 | Planned |

**HRA-061 execution policy:** HRA-061 is deliberately consolidated. Once HRA-060 is complete, all approved domain/persistence mirror changes are executed together as one repository-wide task and one commit. Do not split HRA-061 by module unless this roadmap is explicitly amended first.

---

## 10. Phase H — Rationalize adjacent-layer DTO duplication

| Code | Exact commit message | Scope | Exit criteria | Prerequisite | Status |
|---|---|---|---|---|---|
| `HRA-070` | `docs(api): classify duplicate boundary contracts` | Review the 65 exact Request/Command and 59 exact Response/SummaryDto pairs. | Every pair is KEEP SEPARATE or approved for consolidation/generated mapping. | HRA-002 | **Completed** — live-main classification records all 124 exact pairs. Cross-layer type consolidation is rejected by the current API/application policy; all 65 Request/Command and 59 Response/SummaryDto pairs retain separate types and are approved only for later compile-time generated field mapping. Five same-named but non-exact request/command pairs are explicitly excluded because they perform server/security/path/multipart/value-object enrichment. |
| `HRA-071` | `refactor(alarm): simplify boundary dto mapping` | Pilot compile-time generated mapping for the HRA-070-approved exact alarm pairs only; retain security-enriched alarm mappings as hand-written code. | `RaiseAlarmRequest`→`RaiseAlarmCommand` and `AlarmSummaryDto`→`AlarmResponse` are generated with strict unmapped-property failures; acknowledgement/closure actor enrichment is unchanged; focused mapper coverage is added. | HRA-070 | **Completed** — introduced MapStruct compile-time mapping support, generated only the two exact alarm boundary mappings, preserved the existing static facade and hand-written authenticated acknowledgement/closure mappings, and added exact record-equality tests. |
| `HRA-072` | `refactor(analytics): simplify boundary dto mapping` | Generate the eight HRA-070-approved exact analytics request/command and summary/response mappings. | All analytics exact-pair mappings use strict compile-time generation; existing static mapper API and wire/application types remain unchanged; focused equality tests cover all eight mappings. | HRA-071 | **Completed** — moved all four exact analytics Request→Command and all four SummaryDto→Response mappings behind `AnalyticsGeneratedRestMapper`, preserved the static `AnalyticsRestMapper` facade, and added focused equality tests for every approved pair. |
| `HRA-073` | `refactor(codebase): simplify remaining boundary dto mappings` | Consolidated execution of the remaining HRA-07x module slices, explicitly authorized as one commit: generate only the 114 still-approved exact pairs across 22 modules and retain every non-exact/security/path/multipart/value-object mapping by hand. | All 124 HRA-070 exact pairs are implemented with strict compile-time generation; the remaining 114 are runtime-covered as exact record mappings; API/application types and wire contracts remain separate and unchanged. | HRA-072 | **Completed** — consolidated remainder authorized by the user; added 22 strict generated mappers, routed 114 exact facade mappings through them, preserved manual exceptions, and added one repository-wide reflection contract test covering all 114 generated methods. |

### HRA-070 classification matrix

HRA-070 rechecked the adjacent API/application boundary against live `main` at
`615ecdff72f48834b016f6ac0cf05ee0adddf4d7`.

Repository policy requires application services to remain independent of REST request/response
types and requires API mappers to convert REST requests into application commands/queries and
application DTOs into REST responses. Structural equality therefore does **not** authorize
cross-layer type consolidation.

For the **65 exact Request/Command pairs** and **59 exact Response/SummaryDto pairs**, the
approved disposition is:

- retain both API and application types;
- allow a later HRA-071 module task to replace purely mechanical field-for-field mapper code
  with **compile-time generated mapping**;
- do not introduce runtime reflection mapping;
- do not expose application DTOs directly as REST wire contracts;
- keep hand-written mapping where identity, security, path variables, multipart content, value
  conversion, or other enrichment exists.

The exact-pair matrix is:

| Module | Exact pair | Disposition |
|---|---|---|
| alarm | `RaiseAlarmRequest` ↔ `RaiseAlarmCommand` | GENERATED MAPPING IMPLEMENTED (HRA-071); KEEP TYPES SEPARATE |
| analytics | `CreateAnalyticsDatasetRequest` ↔ `CreateAnalyticsDatasetCommand` | GENERATED MAPPING IMPLEMENTED (HRA-072); KEEP TYPES SEPARATE |
| analytics | `CreateAnalyticsInsightRequest` ↔ `CreateAnalyticsInsightCommand` | GENERATED MAPPING IMPLEMENTED (HRA-072); KEEP TYPES SEPARATE |
| analytics | `RunMetricEvaluationRequest` ↔ `RunMetricEvaluationCommand` | GENERATED MAPPING IMPLEMENTED (HRA-072); KEEP TYPES SEPARATE |
| analytics | `RunProjectionRequest` ↔ `RunProjectionCommand` | GENERATED MAPPING IMPLEMENTED (HRA-072); KEEP TYPES SEPARATE |
| assets | `CreateMaintenanceWorkOrderRequest` ↔ `CreateMaintenanceWorkOrderCommand` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| assets | `RecordAssetConditionRequest` ↔ `RecordAssetConditionCommand` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| assets | `RegisterMaintainableAssetRequest` ↔ `RegisterMaintainableAssetCommand` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| audit | `RecordAuditAccessRequest` ↔ `RecordAuditAccessCommand` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| audit | `RecordAuditEventRequest` ↔ `RecordAuditEventCommand` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| audit | `RequestAuditExportRequest` ↔ `RequestAuditExportCommand` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| configuration | `CreateConfigurationDefinitionRequest` ↔ `CreateConfigurationDefinitionCommand` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| configuration | `CreateFeatureFlagRequest` ↔ `CreateFeatureFlagCommand` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| configuration | `SetConfigurationValueRequest` ↔ `SetConfigurationValueCommand` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| custody | `CreateCustodyTransferTicketRequest` ↔ `CreateCustodyTransferTicketCommand` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| custody | `OpenCustodyDiscrepancyRequest` ↔ `OpenCustodyDiscrepancyCommand` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| custody | `OpenCustodyMeasurementPeriodRequest` ↔ `OpenCustodyMeasurementPeriodCommand` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| documents | `LinkDocumentToTargetRequest` ↔ `LinkDocumentToTargetCommand` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| documents | `RegisterDocumentRequest` ↔ `RegisterDocumentCommand` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| documents | `UploadDocumentVersionRequest` ↔ `UploadDocumentVersionCommand` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| hse | `CloseHseCaseRequest` ↔ `CloseHseCaseCommand` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| hse | `CreateHseCapaRequest` ↔ `CreateHseCapaCommand` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| hse | `OpenHseCaseRequest` ↔ `OpenHseCaseCommand` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| identity | `CreateUserRequest` ↔ `CreateUserCommand` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| incident | `CloseIncidentRequest` ↔ `CloseIncidentCommand` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| incident | `OpenIncidentRequest` ↔ `OpenIncidentCommand` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| incident | `RecordIncidentResponseActionRequest` ↔ `RecordIncidentResponseActionCommand` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| integration | `RecordExchangeMessageRequest` ↔ `RecordExchangeMessageCommand` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| integration | `RegisterExternalSystemRequest` ↔ `RegisterExternalSystemCommand` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| integration | `StartIntegrationJobRunRequest` ↔ `StartIntegrationJobRunCommand` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| integrity | `CreateIntegrityAssessmentRequest` ↔ `CreateIntegrityAssessmentCommand` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| integrity | `CreateIntegrityProgramRequest` ↔ `CreateIntegrityProgramCommand` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| integrity | `OpenIntegrityCaseRequest` ↔ `OpenIntegrityCaseCommand` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| leakdetection | `CreateLeakCandidateRequest` ↔ `CreateLeakCandidateCommand` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| leakdetection | `EscalateLeakCaseRequest` ↔ `EscalateLeakCaseCommand` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| leakdetection | `OpenLeakCaseRequest` ↔ `OpenLeakCaseCommand` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| monitoring | `CreateMonitoringRuleRequest` ↔ `CreateMonitoringRuleCommand` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| monitoring | `RecordDeviationRequest` ↔ `RecordDeviationCommand` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| notification | `CreateNotificationMessageRequest` ↔ `CreateNotificationMessageCommand` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| notification | `ReceiveNotificationRequestRequest` ↔ `ReceiveNotificationRequestCommand` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| notification | `RecordDeliveryAttemptRequest` ↔ `RecordDeliveryAttemptCommand` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| organization | `AssignEmployeeRequest` ↔ `AssignEmployeeCommand` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| organization | `RegisterEmployeeRequest` ↔ `RegisterEmployeeCommand` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| party | `AssignPartyRoleRequest` ↔ `AssignPartyRoleCommand` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| party | `RegisterPartyRequest` ↔ `RegisterPartyCommand` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| planning | `CreateOperationalPlanRequest` ↔ `CreateOperationalPlanCommand` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| planning | `CreatePlanningPeriodRequest` ↔ `CreatePlanningPeriodCommand` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| reporting | `CreateReportDefinitionRequest` ↔ `CreateReportDefinitionCommand` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| reporting | `GenerateReportArtifactRequest` ↔ `GenerateReportArtifactCommand` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| reporting | `QueueReportRunRequest` ↔ `QueueReportRunCommand` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| reporting | `RequestReportRequest` ↔ `RequestReportCommand` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| risk | `AddRiskEvidenceRequest` ↔ `AddRiskEvidenceCommand` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| risk | `CreateRiskAssessmentRequest` ↔ `CreateRiskAssessmentCommand` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| risk | `CreateRiskRegisterRequest` ↔ `CreateRiskRegisterCommand` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| simulation | `CreateSimulationModelRequest` ↔ `CreateSimulationModelCommand` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| simulation | `CreateSimulationScenarioRequest` ↔ `CreateSimulationScenarioCommand` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| simulation | `PublishSimulationRecommendationRequest` ↔ `PublishSimulationRecommendationCommand` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| simulation | `QueueSimulationRunRequest` ↔ `QueueSimulationRunCommand` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| telemetry | `CreateTelemetrySourceRequest` ↔ `CreateTelemetrySourceCommand` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| telemetry | `RegisterTelemetryPointRequest` ↔ `RegisterTelemetryPointCommand` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| topology | `CreatePipelineSystemRequest` ↔ `CreatePipelineSystemCommand` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| topology | `RegisterFacilityRequest` ↔ `RegisterFacilityCommand` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| workflow | `CreateWorkflowTaskRequest` ↔ `CreateWorkflowTaskCommand` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| workflow | `RecordWorkflowActionRequest` ↔ `RecordWorkflowActionCommand` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| workflow | `StartWorkflowInstanceRequest` ↔ `StartWorkflowInstanceCommand` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| alarm | `AlarmResponse` ↔ `AlarmSummaryDto` | GENERATED MAPPING IMPLEMENTED (HRA-071); KEEP TYPES SEPARATE |
| analytics | `AnalyticsDatasetResponse` ↔ `AnalyticsDatasetSummaryDto` | GENERATED MAPPING IMPLEMENTED (HRA-072); KEEP TYPES SEPARATE |
| analytics | `AnalyticsInsightResponse` ↔ `AnalyticsInsightSummaryDto` | GENERATED MAPPING IMPLEMENTED (HRA-072); KEEP TYPES SEPARATE |
| analytics | `AnalyticsProjectionRunResponse` ↔ `AnalyticsProjectionRunSummaryDto` | GENERATED MAPPING IMPLEMENTED (HRA-072); KEEP TYPES SEPARATE |
| analytics | `MetricEvaluationRunResponse` ↔ `MetricEvaluationRunSummaryDto` | GENERATED MAPPING IMPLEMENTED (HRA-072); KEEP TYPES SEPARATE |
| assets | `AssetConditionResponse` ↔ `AssetConditionSummaryDto` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| assets | `MaintainableAssetResponse` ↔ `MaintainableAssetSummaryDto` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| assets | `MaintenanceWorkOrderResponse` ↔ `MaintenanceWorkOrderSummaryDto` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| audit | `AuditAccessRecordResponse` ↔ `AuditAccessRecordSummaryDto` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| audit | `AuditEventResponse` ↔ `AuditEventSummaryDto` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| audit | `AuditExportRequestResponse` ↔ `AuditExportRequestSummaryDto` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| configuration | `ConfigurationDefinitionResponse` ↔ `ConfigurationDefinitionSummaryDto` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| configuration | `ConfigurationValueResponse` ↔ `ConfigurationValueSummaryDto` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| configuration | `FeatureFlagResponse` ↔ `FeatureFlagSummaryDto` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| custody | `CustodyDiscrepancyResponse` ↔ `CustodyDiscrepancySummaryDto` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| custody | `CustodyMeasurementPeriodResponse` ↔ `CustodyMeasurementPeriodSummaryDto` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| custody | `CustodyTransferTicketResponse` ↔ `CustodyTransferTicketSummaryDto` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| documents | `DocumentResponse` ↔ `DocumentSummaryDto` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| documents | `DocumentTargetLinkResponse` ↔ `DocumentTargetLinkSummaryDto` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| documents | `DocumentVersionResponse` ↔ `DocumentVersionSummaryDto` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| hse | `HseCapaResponse` ↔ `HseCapaSummaryDto` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| hse | `HseCaseResponse` ↔ `HseCaseSummaryDto` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| identity | `UserResponse` ↔ `UserSummaryDto` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| incident | `IncidentResponse` ↔ `IncidentSummaryDto` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| integration | `ExternalSystemResponse` ↔ `ExternalSystemSummaryDto` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| integration | `IntegrationExchangeMessageResponse` ↔ `IntegrationExchangeMessageSummaryDto` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| integration | `IntegrationJobRunResponse` ↔ `IntegrationJobRunSummaryDto` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| integrity | `IntegrityAssessmentResponse` ↔ `IntegrityAssessmentSummaryDto` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| integrity | `IntegrityCaseResponse` ↔ `IntegrityCaseSummaryDto` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| integrity | `IntegrityProgramResponse` ↔ `IntegrityProgramSummaryDto` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| leakdetection | `LeakCandidateResponse` ↔ `LeakCandidateSummaryDto` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| leakdetection | `LeakCaseResponse` ↔ `LeakCaseSummaryDto` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| monitoring | `DeviationResponse` ↔ `DeviationSummaryDto` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| monitoring | `MonitoringRuleResponse` ↔ `MonitoringRuleSummaryDto` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| notification | `NotificationDeliveryAttemptResponse` ↔ `NotificationDeliveryAttemptSummaryDto` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| notification | `NotificationMessageResponse` ↔ `NotificationMessageSummaryDto` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| notification | `NotificationRequestResponse` ↔ `NotificationRequestSummaryDto` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| organization | `EmployeeResponse` ↔ `EmployeeSummaryDto` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| organization | `OrganizationUnitResponse` ↔ `OrganizationUnitSummaryDto` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| party | `PartyResponse` ↔ `PartySummaryDto` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| planning | `OperationalPlanResponse` ↔ `OperationalPlanSummaryDto` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| planning | `PlanningPeriodResponse` ↔ `PlanningPeriodSummaryDto` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| reporting | `ReportDefinitionResponse` ↔ `ReportDefinitionSummaryDto` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| reporting | `ReportOutputArtifactResponse` ↔ `ReportOutputArtifactSummaryDto` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| reporting | `ReportRequestResponse` ↔ `ReportRequestSummaryDto` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| reporting | `ReportRunResponse` ↔ `ReportRunSummaryDto` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| risk | `RiskAssessmentResponse` ↔ `RiskAssessmentSummaryDto` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| risk | `RiskRegisterResponse` ↔ `RiskRegisterSummaryDto` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| simulation | `SimulationModelResponse` ↔ `SimulationModelSummaryDto` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| simulation | `SimulationRecommendationResponse` ↔ `SimulationRecommendationSummaryDto` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| simulation | `SimulationRunResponse` ↔ `SimulationRunSummaryDto` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| simulation | `SimulationScenarioResponse` ↔ `SimulationScenarioSummaryDto` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| telemetry | `TelemetryPointResponse` ↔ `TelemetryPointSummaryDto` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| telemetry | `TelemetrySourceResponse` ↔ `TelemetrySourceSummaryDto` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| topology | `FacilityResponse` ↔ `FacilitySummaryDto` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| topology | `PipelineSystemResponse` ↔ `PipelineSystemSummaryDto` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| workflow | `WorkflowActionResponse` ↔ `WorkflowActionSummaryDto` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| workflow | `WorkflowInstanceResponse` ↔ `WorkflowInstanceSummaryDto` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |
| workflow | `WorkflowTaskResponse` ↔ `WorkflowTaskSummaryDto` | GENERATED MAPPING IMPLEMENTED (HRA-073); KEEP TYPES SEPARATE |

The live tree also contains five same-named Request/Command pairs that are **not** part of the
65 exact-pair baseline and are explicitly excluded from generated field-for-field mapping:

| Pair | Evidence-backed reason |
|---|---|
| `AcknowledgeAlarmRequest` ↔ `AcknowledgeAlarmCommand` | Command adds authenticated actor ID and principal-derived display identity. |
| `CloseAlarmRequest` ↔ `CloseAlarmCommand` | Command adds server-resolved authenticated actor ID. |
| `UploadDocumentBinaryVersionRequest` ↔ `UploadDocumentBinaryVersionCommand` | Command adds server-owned filename/content-type plus the binary `InputStream`. |
| `CreateOrganizationUnitRequest` ↔ `CreateOrganizationUnitCommand` | API carries raw `String code`; mapper converts it to domain `OrganizationCode`. |
| `ExecuteWorkflowTransitionRequest` ↔ `ExecuteWorkflowTransitionCommand` | Command adds path-owned task/transition IDs plus authenticated actor identity and effective permissions. |

No Java source, API wire contract, application contract, dependency, or generated-mapper tooling
is changed by HRA-070. Implementation is reserved for one module-scoped HRA-071 task at a time.

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
| `HRA-090` | `refactor(workflow): expose planning workflow contract` | Export Workflow contracts consumed by Planning; stop Planning importing Workflow's internal application package. | Planning depends only on deliberate Workflow contract packages; ArchUnit enforces it. | HRA-002 | **Completed** — introduced the narrow `workflow.application.contract.planning.PlanningWorkflowContract`, adapted existing Workflow internal query/transition use cases behind it, migrated Planning approval orchestration and tests to the exported contract, removed the Planning→Workflow transitional allowlist, and taught ArchUnit to allow only the deliberate contract package. |
| `HRA-091` | `refactor(topology): expose operational scope resolution contract` | Export the Topology scope-resolution contract consumed by Organization. | Organization no longer imports Topology's internal application package. | HRA-002 | **Completed** — relocated the topology-owned operational-scope resolver into the deliberate `topology.application.contract.organization.TopologyOperationalScopeTargetContract`, migrated the Topology implementation plus Organization adapter/tests, deleted the obsolete private `port.in` contract, removed the final transitional cross-module allowlist entry, and updated ArchUnit to permit only the exported contract package. |

---

## 13. Phase K — Stop API/domain representation leakage

| Code | Exact commit message | Scope | Exit criteria | Prerequisite | Status |
|---|---|---|---|---|---|
| `HRA-100` | `refactor(planning): return application revision result` | Return an application result DTO from plan-revision updates rather than a domain `PlanRevision` to REST. | Planning controller no longer depends on the domain model for this flow. | HRA-002 | **Completed** — `UpdatePlanRevisionUseCase` now returns an application-owned `Result` record containing the existing response fields with status represented as a string; `PlanRevisionUpdateApplicationService` maps the persisted domain revision into that result; `PlanRevisionCommandController` no longer imports `PlanRevision` or `PlanRevisionStatus`; focused service/controller tests cover the application-result boundary without changing the REST response shape. |
| `HRA-101` | `refactor(identity): isolate authentication web contract` | Adapt Spring principal/session details into application-level authentication inputs/results. | Authentication controller no longer exposes unnecessary domain representation. | HRA-002 | **Completed** — introduced application-owned `AuthenticatedPrincipalInput` and flattened `AuthenticationResult`; direct and OIDC completion use cases now exchange those contracts instead of `HidraPrincipal`/`LoginSession`; `IdentityAuthenticationWebMapper` owns Spring-injected principal adaptation and REST-result mapping; the authentication controller no longer imports Identity domain models; LOCAL/LDAP/AD/OIDC API tests preserve the existing response and provider-selection behavior. |
| `HRA-102` | `docs(api): define domain enum exposure policy` | Decide whether REST exposure of domain enums is an intentional wire contract; define mapping policy. | Architecture guardrail can distinguish permitted values from forbidden domain-model leakage. | HRA-002 | **Completed** — documented the evidence-backed enum exception: same-module actual Java enums may be exposed directly only as deliberate stable wire vocabulary; cross-module domain types, aggregates, non-enum value/domain objects, persistence types, and unstable/internal enum vocabularies remain forbidden and must be mapped. The policy defines API compatibility obligations and a structural guardrail classifier that can distinguish permitted enum vocabulary from domain-model leakage. Coding policy §10.8 now carries the normative rule. |

---

## 14. Phase L — Make scalar-ID referential integrity explicit

| Code | Exact commit message | Scope | Exit criteria | Prerequisite | Status |
|---|---|---|---|---|---|
| `HRA-110` | `docs(persistence): inventory scalar reference integrity` | Classify non-primary entity `*Id` fields as same-module references, cross-module stable references, historical snapshots, external IDs, or non-relational identifiers. | Every mandatory scalar reference has a documented integrity owner. | HRA-001 | Planned |
| `HRA-111` | `fix(codebase): enforce classified internal reference integrity` | **Single consolidated repository-wide task / single commit.** Apply every HRA-110-approved same-module integrity repair across all affected modules using fail-closed domain/application validation plus DB FK/check constraints where evidence requires them. Add only new Flyway migrations; never modify an applied migration; never add cross-module database FKs. Include Testcontainers coverage for the consolidated schema changes. | Every HRA-110 same-module ENFORCE disposition is implemented in the same commit; mandatory internal references are protected at domain/application and DB layers; all new migrations are additive and validated; cross-module stable references remain free of DB FKs; repository validation passes. | HRA-110 + live schema evidence | Planned |

**HRA-111 execution policy:** HRA-111 is deliberately consolidated. After HRA-110 and live-schema verification, all approved same-module referential-integrity repairs are executed together as one repository-wide task and one commit. The batch may contain multiple new Flyway migrations when ordering or module ownership requires it, but they belong to the same HRA-111 commit. Applied migrations are immutable and cross-module DB foreign keys remain forbidden.

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
HRA-070 → HRA-071 → HRA-072 → HRA-073 (consolidated remainder)
HRA-090 / HRA-091
HRA-100 / HRA-101 / HRA-102

After mapper/invariant safety rails:
HRA-050 → HRA-051 (consolidated invariant batch, one commit) → HRA-060 → HRA-061 (consolidated mirror batch, one commit)

After orphan classification:
HRA-080 → HRA-081

After live schema inspection:
HRA-110 → HRA-111 (consolidated integrity batch, one commit)

Finally:
HRA-120
```

## 17. Current execution point

`HRA-001` and `HRA-004` are complete. HRA-004 was explicitly prioritized to remove CI duplication before the high-frequency remediation sequence.

**Next task:** `HRA-060 — docs(domain): classify anemic persistence mirrors`.

Do not begin broad dead-code deletion, event deletion, DTO consolidation, or domain/JPA mirror reduction before the HRA-002/HRA-003 safety rails exist.
