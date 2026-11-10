# HidraAPI functional delivery roadmap

**10 November 2026 · Active API task roadmap · Gas-first GZ2 synthetic pilot**

## 0. Control Status

| Field | Value |
|---|---|
| Status | ACTIVE |
| Authority | Sole task register and execution source for HidraAPI |
| Owner adoption | 2026-11-10 |
| Active task selection | None. Separate owner selection and exact task admission are required. |

The task register controls scope, dependencies, status and acceptance. Existing technical policy and verified implementation evidence retain their applicability. Task status advances only with recorded evidence. The initial status for each task is PLANNED. The first candidate is T01.

## 1. Outcome and scope

The first delivery target is a complete external REST workflow. A client can retrieve an approved engineering configuration and operating target, submit a measurement, retrieve qualified evidence, calculate a reproducible deviation, and record an authorized operator response. Every accepted step produces a durable result that another client can retrieve after an application restart.

Start with **scoped Planning authoring and independent approval**. It closes the first business decision loop and creates the authoritative target used by Monitoring. Build engineering baseline release and trusted Telemetry as distinct prerequisites for the integrated workflow. After the first integrated proof, complete the remaining business lifecycles and add non-numerical scenario readiness.

This plan derives its scope and acceptance criteria from the [HidraAPI Functional API and Workflow Assessment](https://chatgpt.com/library?file_id=libfile_9aacabc70b488191a51c695c8b0b1045&account_id=personal). The assessment inspected 24 modules, 387 REST operations and 13 workflows at [HidraAPI commit 85ac6d3](https://github.com/CHOUABBIA-AMINE/HidraAPI/commit/85ac6d387ef5cf1421dd22a89b9c56079d665e1e). The sequence, scope boundaries and proposed acceptance gates below are defined within this document. Identifiers beginning with **FG** and **A** refer only to the assessment’s findings and acceptance cases.

**Delivery boundary.** This is a proposed backend plan for software qualification with synthetic fixtures. The first integrated result includes a reproducible operator decision. Numerical execution, field calibration, real SCADA/OT integration, deployment, SITE acceptance and autonomous control remain outside its delivery scope. The future Hidra-owned steady and transient engine uses the versioned inputs established here.

## 2. Starting point

| Capability | Reuse from assessed source | Remaining work |
|---|---|---|
| Native API foundation | Signed LOCAL access, current authorization patterns, Organization and Party reference data, typed Configuration, selected Workflow actions, native PostgreSQL/JVM evidence for bounded journeys. | Apply exact scope and actor authority to each new owner transition and query. |
| Engineering | Existing network, station, equipment, product and manufacturer operations. Combined baseline draft and validation. | Scoped discovery, independent approval of the exact combined baseline, publication and effective/historical reads. |
| Planning | Period and plan creation, typed reads, revision metadata concurrency and approval actions for already linked instances. | Company-owned periods, scope-aware reads/counts, client-authored revisions and targets, atomic submission, independent decision and final readback. |
| Telemetry and Monitoring | Native raw ingestion, replay and readback. Rule registration and supplied deviation intake. | Commissioned bindings, trusted publication, rule activation and authoritative evaluation from approved and qualified inputs. |
| Operational response | Manual alarms and authorized handling. Selected leak, incident and HSE operations. | Correlation from qualified evidence, deduplication, escalation and typed owner outcomes. |
| Broader API | Partial create/read/lifecycle paths across remaining business modules. | Complete workflow-specific commands, results, histories and typed queries where the assessment identified gaps. |

The assessed local checks passed ten focused API tests and eleven frontend adapter tests, and the frontend build passed. The latest-pair native HTTP/PostgreSQL workflow was unverified. Completion gates below require new end-to-end evidence for the selected API delivery.

## 3. Delivery sequence

The table defines the proposed order of delivery. A row can start once its own prerequisites are satisfied. Planning does not require an ingested measurement to create a target. Monitoring joins the approved target with a trusted observation. The engineering release is required for envelope-based evaluation and for the integrated GZ2 workflow.

| Increment | Deliverable | Entry dependencies | Exit evidence |
|---|---|---|---|
| 1. Planning (T01–T03) | Company-owned period → asset-scoped plan → versioned revision and target → independent Workflow decision → authorized readback. | Current company and resource authorization, effective responsibility, eligible asset and point/unit references, and an active Workflow definition for the exact revision. | Signed REST over PostgreSQL proves approve and reject, scoped counts, CAS, atomic rollback and restart readback. A03 and the Planning slice of A18. FG-03 and FG-18. |
| 2. Engineering release (T04–T07) | Discover exact eligible inputs → complete baseline draft → independent approval → publication → current/as-of/history. | Published network, station, installed equipment, manufacturer and product revisions for the supported GZ2 fixture. | The published manifest retains exact input IDs and digest. Supersession preserves history. Stale or withdrawn sources and unauthorized publication are rejected. A01/A02. FG-01 and FG-02. |
| 3. Trusted Telemetry (T08–T09) | Commission a source, point and effective binding → ingest → assess → publish and retrieve trusted evidence. | Current source principal, exact asset association, unit/quality policy and a valid effective binding. | Correct raw replay, one qualified result where eligible, traceable evidence and explicit stale, rejected and mismatched cases. A04. FG-04. |
| 4. Monitoring evaluation (T10–T11) | Activate a versioned rule → evaluate authoritative inputs → persist result and deviation → explain it through REST. | Accepted outputs from 1 and 3. Output from 2 when the rule uses engineering limits or baseline context. | Deterministic actual/expected/difference and exact evidence references. Explicit result for stale, missing, uncertain and incompatible observations. A05. FG-05. |
| 5. Operational response (T12–T14) | Raise a correlated alarm or suspected leak → acknowledge → escalate through authorized human action → retrieve owner outcome. | Scoped evaluation or other explicitly eligible evidence from 4, plus an admitted trigger, deduplication and escalation policy. | Replayed evidence does not duplicate the outcome. Human decision, case link and timeline remain retrievable. A06 and the escalation slice of A07. FG-06 and the escalation slice of FG-07. |
| 6. Integrated GZ2 software acceptance (T15) | Run the complete engineering-to-operator journey on one exact API commit with an independent client. | Required outputs from 1–5 are accepted and compatible. | Fresh PostgreSQL and signed HTTP proof with actual generated OpenAPI, persisted readback, negative cases and independently reviewed evidence. |
| 7. Remaining business lifecycles (T16–T30) | Complete investigations, maintenance, Custody, Risk, Analytics, Reporting, Documents, Integration, Notification and Audit. | Exact owner prerequisites for each workflow. Documents capability is available before report artifact completion depends on it. | Each selected workflow can be initiated, progressed, completed and retrieved through authorized REST. FG-07–18. |
| 8. Scenario readiness and configuration (T31–T32) | Add engine-neutral scenario versioning and reconstruction. Deliver historical configuration retirement only for a proven consumer need. | Accepted engineering inputs and a defined capability/validation policy. | Historical scenario reconstructs after supersession. Configuration lookup remains reproducible. FG-19 and FG-20. FG-21 remains deferred. |

Increment 6 is the proposed first integrated development milestone. The remainder is sequenced by functional dependency and risk. Calendar estimates require implementation sizing, staffing and CI feedback.

## 4. Explicit delivery task register

The roadmap initially contains **32 registered tasks**. Use these IDs for HidraAPI task selection and evidence. Each task delivers a named API capability or a verifiable acceptance result. An owner is the accountable bounded context, with collaborators shown where needed. Dependencies are prerequisites for accepting the result and take precedence over task numbering. “Existing foundation” means the assessed starting capabilities in §2.

A task includes its required authorization, REST contract, persistence, failure handling and tests. Confirm exact routes, payloads and errors during task preparation. Each task inherits the definition of done in §7.

### Increment 1 — Planning and independent approval

| Task | Owner | Depends on | Deliverable | Pass condition | Status |
|---|---|---|---|---|---|
| **T01 · Make periods and plans company-scoped** | Planning with Identity, Organization and Topology | Existing foundation | Complete company-owned period and exact-asset plan creation, list, detail and count. Derive actor server-side. Inspect period-code uniqueness and add forward-only ownership constraints where required. | Two companies and two assets in the same company prove that entitled actors see only authorized records and counts. Revocation and wrong ownership fail before disclosure. Authorized data survives restart. **FG-03 / A03.** | PLANNED |
| **T02 · Author versioned plan revisions and targets** | Planning with Topology and Telemetry | T01 | Expose draft creation, target create/update and scoped revision/target readback. Generate identifiers and revision versions server-side. Validate point, asset, unit and effective window. | A client authors and retrieves a draft with returned IDs. Stale writes, mismatched points and incompatible units fail atomically. Existing metadata CAS remains compatible. **FG-03 / A03.** | PLANNED |
| **T03 · Submit and independently decide a revision** | Planning with Workflow and Identity | T02 | Bind submission to the exact revision and active Workflow definition in one transaction. Freeze submitted targets. Publish the decision and activate targets only on committed approval. | Distinct signed reviewer approves or rejects. Self-review, revocation, wrong target and concurrent/repeated actions are handled as specified. Forced failure leaves no orphan Workflow instance. A restarted client retrieves the exact target and decision. **FG-03/18 / A03/A18.** | PLANNED |

### Increment 2 — Approved engineering configuration

| Task | Owner | Depends on | Deliverable | Pass condition | Status |
|---|---|---|---|---|---|
| **T04 · Discover eligible engineering source revisions** | Topology with equipment, manufacturer and product owners | Existing foundation | Expose scoped lookup by business key and revision for network, station, installed unit, manufacturer and product sources. Return version, eligibility and scope-safe provenance. | An engineer starts from a business key and discovers the exact eligible revisions. Foreign, withdrawn or unavailable inputs cannot leak through detail or counts. **FG-02 / A02.** | PLANNED |
| **T05 · Complete validation of combined baseline drafts** | Topology with source owners | T04 | Complete the existing draft/completeness path for one supported multi-owner GZ2 configuration. Return exact references and explicit completeness, unit, validity and eligibility diagnostics. | REST validation succeeds for the complete fixture and identifies missing, mismatched and withdrawn input versions. The draft identifies the exact sources without asserting publication. **FG-01 / A01.** | PLANNED |
| **T06 · Approve and publish an exact baseline** | Topology with Workflow and Identity | T05 | Submit an immutable manifest and digest, bind the independent decision and atomically advance the publication head under current source eligibility and expected version. Return publication receipt. | A distinct eligible reviewer approves the exact digest and publication occurs once. A substituted decision, withdrawn source, stale head or failed transaction cannot advance publication. **FG-01/18 / A01/A18.** | PLANNED |
| **T07 · Retrieve effective and historical baselines** | Topology | T06 | Publish exact-version, current, as-of, history and comparison responses with source and decision lineage. | A second client retrieves the original digest and source IDs after supersession and restart. As-of selection is deterministic. Unauthorized history and counts stay protected. **FG-01 / A01.** | PLANNED |

### Increment 3 — Trusted Telemetry

| Task | Owner | Depends on | Deliverable | Pass condition | Status |
|---|---|---|---|---|---|
| **T08 · Commission scoped sources, points and bindings** | Telemetry with Topology and Identity | Existing foundation | Expose typed commissioning and lookup of source, point, mapping and effective binding. Enforce producer, unit, asset and time-window eligibility. | An authorized producer uses a configured binding. Foreign asset, wrong point/unit and expired or conflicting binding are rejected. Historical binding remains resolvable. **FG-04 / A04.** | PLANNED |
| **T09 · Publish and retrieve trusted observations** | Telemetry | T08 and existing raw-ingestion path | Reuse raw replay. Add governed quality assessment and trusted publication/readback with raw ID, assessment, binding version, unit, timestamps and eligibility reason. | Exact replay leaves one raw observation. A valid assessment yields one trusted result. Stale, bad-quality, conflicting and mismatched evidence cannot yield a trusted result. Restart readback preserves lineage. **FG-04 / A04.** | PLANNED |

### Increment 4 — Authoritative Monitoring

| Task | Owner | Depends on | Deliverable | Pass condition | Status |
|---|---|---|---|---|---|
| **T10 · Version and activate Monitoring rules** | Monitoring with Topology | Existing rule registration and eligible references | Complete rule creation, activation and scoped effective/history reads. Capture rule version, unit, window and supported evidence policy. | An authorized client activates a valid rule and retrieves its exact effective version. Invalid or stale transitions fail and historical versions remain readable. **FG-05 / A05.** | PLANNED |
| **T11 · Compute and explain an authoritative deviation** | Monitoring with Planning and Telemetry | T03, T09, T10. T07 for engineering-envelope rules | Resolve exact approved target and trusted observation. Compute and persist actual, expected, difference and evidence lineage. Expose typed result/detail/history. | Compatible 105 against 100 yields +5 in the agreed unit. Repeated inputs are deterministic. Missing, stale, uncertain and incompatible observations return explicit states. **FG-05 / A05.** | PLANNED |

### Increment 5 — Operational response

| Task | Owner | Depends on | Deliverable | Pass condition | Status |
|---|---|---|---|---|---|
| **T12 · Raise and acknowledge an evidence-linked alarm** | Alarm with Monitoring | T11 | Accept eligible scoped evidence, apply correlation/deduplication and expose alarm, exact evidence and operator acknowledgement. | Replay of the same qualifying trigger yields one alarm outcome. An authorized operator acknowledges it. Unauthorized actors cannot read or mutate it. **FG-06 / A06.** | PLANNED |
| **T13 · Create a suspected leak candidate from eligible evidence** | Leak Detection with Monitoring or Telemetry | T11 or T09, according to the admitted trigger policy | Create a candidate with exact source references, uncertainty, status and duplicate policy. Return candidate detail without promoting suspicion to confirmation. | Replaying the eligible trigger preserves one candidate. Insufficient evidence is handled explicitly. The candidate and its evidence remain retrievable under exact scope. **FG-06 / A06.** | PLANNED |
| **T14 · Escalate a correlated alarm or leak to a case** | Incident with Alarm, Leak Detection and Workflow | T12, T13 | Add authorized human escalation, exact target linkage and a typed receipt/history of the new or linked case. | Eligible operator escalates once and sees the linked case and decision timeline after restart. Wrong scope or target cannot create a case. **FG-06/07/18 / A06/A07/A18.** | PLANNED |

### Increment 6 — Integrated development acceptance

| Task | Owner | Depends on | Deliverable | Pass condition | Status |
|---|---|---|---|---|---|
| **T15 · Qualify the signed GZ2 workflow end to end** | API integration and QA with participating domain owners | T03, T07, T09, T11, T14 | Run the first integrated journey on one API commit using a synthetic GZ2 fixture, native JWT/HTTP and fresh PostgreSQL. Retain exact runtime OpenAPI, schema and case evidence. | The baseline, target, observation, deviation and human escalation reconstruct after restart and supersession. Foreign and revoked principals cannot discover protected records or totals. Independent review accepts the software evidence. **A01–A06, A13, A18 and A21 as applicable.** | PLANNED |

### Increment 7 — Remaining business lifecycles

| Task | Owner | Depends on | Deliverable | Pass condition | Status |
|---|---|---|---|---|---|
| **T16 · Complete incident and leak investigations** | Incident and Leak Detection | T14 | Expose scoped assignment, investigation actions, evidence timeline, disposition and closure. | An authorized investigator progresses a case and a separate eligible reviewer completes its required disposition. Actions and evidence survive restart. **FG-07 / A07.** | PLANNED |
| **T17 · Verify and close HSE corrective actions** | HSE with Incident, Documents and Workflow | T16, T24 | Assign corrective/preventive actions, capture evidence, verify effectiveness and close with reason and authority. | Closure requires the admitted decision and linked evidence. Foreign actors cannot view case history or counts. **FG-07/18 / A07/A18.** | PLANNED |
| **T18 · Hand off an integrity finding and verify maintenance** | Integrity and Assets | Eligible Topology and maintainable-asset references | Expose approved finding → assigned work order → execution → verification, with typed outcome/history on both sides. | Correct asset and finding are linked through completion. Wrong binding or stale transition fails atomically. Both owners retain exact outcome evidence. **FG-08 / A08.** | PLANNED |
| **T19 · Author and govern Planning nominations** | Planning with Party and Workflow where required | T03 | Create a scoped nomination, link it to an eligible plan revision and expose allowed disposition and historical reads. | A signed client creates and retrieves the nomination and exact revision. Invalid relationship or unauthorized disposition is denied. **FG-03 extension / A03.** | PLANNED |
| **T20 · Capture and retrieve qualified custody measurements** | Custody with Telemetry, Party and product owners | T09 and eligible meter/product/Party references | Bind scoped measurements to meter, product, party, units, basis, interval and qualified source evidence. | Authorized readback preserves exact source, quantity, basis and measurement window. Wrong meter, product or unit is rejected. **FG-09 / A09.** | PLANNED |
| **T21 · Reconcile, finalize and correct custody transfers** | Custody | T20 | Expose period/ticket/discrepancy read and disposition, reconciliation, finalization and versioned correction. | A synthetic ticket is reconciled and finalized. Correction retains original and superseding records with attributable reason and evidence. **FG-09 / A09.** | PLANNED |
| **T22 · Expose governed Risk results and decision history** | Risk with owner evidence providers | Eligible evidence and the existing scoring/approval path | Expose scoped assessment, score inputs, evidence, independent decision and approved history. | Later source or method changes do not alter the approved historical result. Revoked and unrelated principals cannot read it. **FG-10 / A10.** | PLANNED |
| **T23 · Execute and retrieve a deterministic analytics metric** | Analytics with source owners | One supported metric and eligible versioned input, such as T11 | Execute the metric, persist durable run/result state and expose source lineage, units and version. | Synthetic inputs produce a predictable value after restart. Bad inputs have explicit failed/unavailable outcomes. **FG-11 / A11.** | PLANNED |
| **T24 · Discover and supersede controlled Documents** | Documents with target owners | Existing byte upload/download and owner target references | Expose scoped discovery, exact version/link history, acceptance and supersession where required. | An authorized client can discover and retrieve exact bytes and hash for old and current versions. Revoked users cannot download. **FG-14 / A14.** | PLANNED |
| **T25 · Render and download an authorized report** | Reporting with Documents and Workflow | T24 and an eligible approved report input, such as T23 | Admit template and parameters, render a durable artifact and expose run status and scoped byte retrieval. | Downloaded bytes match the stored hash and exact input version. Failed rendering cannot produce a downloadable success. **FG-12 / A12.** | PLANNED |
| **T26 · Execute and replay a governed synthetic exchange** | Integration with receiving owner | Supported synthetic mapping and receiving contract | Expose mapping version, job/attempt/result, dead letter and privileged replay for one exchange. | Simulated transient failure and authorized replay yield one receiving outcome and a durable attempt history. **FG-15 / A15.** | PLANNED |
| **T27 · Deliver a recipient-scoped notification inbox** | Notification | One eligible business event and a supported delivery channel | Expose inbox, delivery state and recipient acknowledgement independently of business workflow state. | Intended recipient reads and acknowledges one notification. Foreign recipient cannot see it. The underlying task state is unchanged by acknowledgement. **FG-16 / A16.** | PLANNED |
| **T28 · Search and export scoped Audit evidence** | Audit with Identity and Documents where needed | Durable owner events and authorized export storage | Expose typed event search, export state, manifest/hash and scoped retrieval. Recheck current grant at download. | Export contains only the admitted company, asset and time window. Download after revocation is denied and access evidence is retained. **FG-17 / A17.** | PLANNED |
| **T29 · Verify typed owner read coverage** | Business resource owners with Identity | Each affected owner workflow. Check the early workflows during T15. | Verify scoped list/detail/history and pagination/count for affected workflows. Close residual read gaps within the responsible owner. | Independent clients can find completed records by business key. Unauthorized, unavailable and legitimately empty results are distinct. **FG-13 / A13.** | PLANNED |
| **T30 · Verify authority for supported Workflow targets** | Workflow with participating target owners | Each supported owner workflow. Check Planning, baseline and escalation during T15. | Verify definition eligibility, exact target linkage, current/delegated authority and supported transitions. Close residual owner-specific gaps. | Wrong-target, stale, revoked and self-review cases fail where applicable. Terminal state and decision provenance remain retrievable. **FG-18 / A18.** | PLANNED |

### Increment 8 — Scenario readiness and optional configuration

| Task | Owner | Depends on | Deliverable | Pass condition | Status |
|---|---|---|---|---|---|
| **T31 · Version and reconstruct engine-neutral scenarios** | Simulation with Topology | T07 | Expose scenario create/version/validate/read with exact baseline digest, units, boundary conditions, intended steady/transient mode and capability state. | Historical scenario reconstructs after baseline supersession. Availability and validation states do not imply computed results. **FG-19 / A19/A21.** | PLANNED |
| **T32 · Preview and retire configuration with effective dating** | Configuration | Confirmed consumer need and existing configuration lifecycle | Add scoped impact preview and an effective-dated retirement action with historical reads. | Authorized client gets the correct result on both sides of the effective boundary. Earlier consumers retain the accepted configuration interpretation. **FG-20 / A20.** | PLANNED |

**Deferred — FG-21.** Numerical execution, calibration and industrial integrations are outside the 32 proposed tasks. Apply A21 to delivered contracts so clients can distinguish readiness, trust, engineering qualification and computation.

## 5. Detailed delivery specifications

### 1. Scoped Planning and independent approval

**Business result.** An authorized author starts from eligible reference data and obtains an immutable, independently approved target. Monitoring can retrieve its exact effective value, unit, time window and revision.

| Delivery area | Required behavior | Proof |
|---|---|---|
| Period and plan | Each period has one authoritative company. Resolve the authenticated actor and current company grant server-side. Validate the plan’s exact asset/company binding and organizational responsibility. Carry ownership through descendants. | Two synthetic companies. A period and plan created through REST remain visible only to entitled principals. Same-company access to asset A does not imply access to asset B. |
| Revision and targets | Generate revision IDs, sequence and version tokens server-side. Allow editing only in an eligible draft. Check target type, engineering unit, point/asset binding and effective window. Freeze submitted targets and retain previous approved revisions. | Bad unit, wrong binding, stale version and concurrent writers yield deterministic outcomes. Old approved evidence remains reconstructible after a newer revision. |
| Submission and review | Bind the active Workflow definition to the exact revision. Persist submission, immutable target snapshot and Workflow link in the same PostgreSQL transaction. Recheck independent reviewer authority at decision time. Activate a target only on committed approval. | Force a downstream error and verify no orphan instance or partial Planning state. Exercise self-review denial, revoke, reject, repeat transition and two simultaneous submissions. |
| Read and counts | Provide typed period/plan/revision/target/approval responses. Filter authorized rows in the database before pagination and count. Keep foreign and missing cases non-disclosing. | Compare list, detail, counts and approval access for author, reviewer, unrelated asset and revoked principal. Verify authorized reads from a restarted JVM. |
| REST and storage | Retain accepted APIs where compatible. Add explicit authoring and submission commands, typed DTOs, statuses, version fields and deterministic problem responses. Inspect current global period-code uniqueness and specify the correct uniqueness scope before any forward-only DDL. | Fresh PostgreSQL migration and JPA proof. Existing metadata CAS and error contract pass regressions. Runtime OpenAPI documents the added client journey. |

Planning owns periods, plans, revisions and targets. Workflow owns tasks and decisions. Identity, Organization, Topology and Telemetry provide their respective owner contracts. The initial deliverable includes the full target approval journey. **Nomination authoring is an extension** of the Planning workflow and receives its own acceptance in increment 7. This preserves a coherent first result without making the downstream Monitoring calculation depend on nomination lifecycle work.

### 2. Approved engineering configuration

1. Provide typed, scope-aware discovery by business key for eligible network, station, installed equipment, product and manufacturer revisions. Return exact version identifiers and eligible state.
2. Validate the combined baseline against the current sources. Freeze a deterministic manifest with digest, exact input versions, units, validity and source provenance.
3. Bind independent approval to that exact digest and the relevant company/asset scope. At publication, recheck authority, eligibility and expected head/version. Apply idempotent replay and preserve decision evidence.
4. Expose exact, current and effective/as-of queries, plus history and comparison. Retain historical reconstruction when a later baseline becomes effective.

**Acceptance.** A signed engineer discovers eligible sources, drafts and submits a manifest, and a distinct eligible reviewer authorizes publication. A second client retrieves the same digest and versions. A concurrent publish, stale expected head, wrong approval target or withdrawn source cannot silently advance the head. Test unauthorized source discovery and history. An approved software record preserves source status and provenance separately from engineering qualification.

### 3. Trusted Telemetry

1. Expose typed commissioning and lookup of sources, points and effective mappings/bindings. Validate current company, exact physical asset, engineering unit, authorized producer and reference validity.
2. Reuse native raw ingestion and idempotent replay. Preserve original observation timestamp, source sequence, raw payload/digest and binding provenance. Distinguish duplicate replay from conflicting payload.
3. Expose governed quality assessment and trusted publication. Retain the raw observation reference, assessment, scope, source binding version, unit and applicable time policy.
4. Return explicit eligibility and reasons for missing, stale, future-skewed, mismatched or rejected samples. Admit the late-data and binding-selection policy before implementation.

**Acceptance.** One signed producer ingests a valid sample and a repeat, then attempts conflicting replay. A qualified observation is published once. Another actor retrieves it after restart. Bad quality, revoked producer rights, expired mappings and incompatible units cannot produce a trusted result.

### 4. Authoritative Monitoring

1. Version and activate a rule with an explicit scope, unit, evaluation window and authorized input policy.
2. Retrieve the exact effective approved Planning target and trusted Telemetry reading through owner contracts. Resolve an accepted engineering envelope when required.
3. Calculate actual, expected and difference server-side. Persist evaluation identity, rule/version, source IDs, timestamps, units, quality, reason and durable result. Preserve the distinction between computed results and imported/recorded deviations.
4. Expose typed evaluation/result/history queries. Keep unknown, missing, stale and incompatible outcomes explicit.

**Acceptance.** A synthetic target of 100 and compatible trusted actual of 105 yield a reproducible +5. Repeating the same evidence follows deterministic replay policy. Unavailable or incompatible evidence never appears as a valid numeric deviation.

### 5. Correlated operational response

1. Define admitted evidence classes and versioned trigger policy. Create a correlated alarm or suspected leak under current scope. Persist correlation key, exact source evidence, trigger policy version and explanation.
2. Reuse permitted Alarm transitions and require current authority for acknowledgement or escalation. Resolve exact Workflow and Incident/Leak Detection target where required.
3. Return typed operator timeline, disposition and linked case evidence. Keep suspected, confirmed and resolved states distinct. A human decision is attributable to the current actor and is independent from any assertion of field execution.

**Acceptance.** Replay of one evidence trigger results in one permitted correlation outcome. Authorized actors acknowledge and escalate. Unauthorized actors cannot discover or mutate the alarm or linked case. A restarted client retrieves exact evidence and history.

### 6. Integrated GZ2 software proof

Run the complete journey with one pinned API revision, fresh PostgreSQL, synthetic fixtures and separate author, reviewer, producer, operator and foreign-company principals.

1. Discover and publish an engineering baseline. Create and independently approve the exact Planning target used by the rule.
2. Ingest a signed observation, apply the trusted publication contract and retrieve its raw-to-qualified lineage.
3. Evaluate the rule and retrieve the deviation with input versions and units. Raise the associated alarm and record a human decision.
4. Restart the application and retrieve exact immutable history. Repeat protected access with a revoked and a foreign actor. Supersede one relevant source version and verify the historical evaluation remains reconstructible.
5. Record exact runtime OpenAPI, PostgreSQL/Flyway evidence, raw case outcomes and run provenance. Qualify the backend software only after independent review of the full journey.

### 7. Remaining business lifecycles

| Owner and assessment finding | Deliverable | Acceptance |
|---|---|---|
| Incident, Leak Detection and HSE · FG-07 | Assigned investigation, evidence timeline, disposition, CAPA verification and closure. | A07. Read the same action/evidence history after restart. Independent eligible review and foreign-scope denial are proven. |
| Integrity and Assets · FG-08 | Verified finding → assigned work order → execution → verification, with linked condition and case history. | A08. Wrong asset and stale order fail atomically. Exact evidence is retrievable from both owners. |
| Planning · residual FG-03 | Govern nomination creation, linkage to an eligible revision and authorized disposition. | A03 nomination scenario. Retrieve the persisted nomination and exact revision under correct scope. |
| Custody · FG-09 | Typed measurement read, period/ticket/discrepancy reconciliation, finalization and immutable corrections. | A09. Verify exact quantity basis, units, evidence and both original and corrected records. |
| Risk · FG-10 | Typed risk result, score-input/evidence and independent decision/history APIs. | A10. Later changes to source evidence cannot alter the historical approved decision. |
| Analytics · FG-11 | Versioned deterministic metric, durable execution status, result and provenance query. | A11. Synthetic inputs produce a predictable result after restart. Invalid inputs have explicit failure state. |
| Reporting · FG-12 | Eligible report definition/template, validated parameters, reproducible execution and durable Documents artifact, with scoped status and download. | A12. Retrieved bytes match the recorded hash and exact inputs. Failed rendering yields no successful artifact. |
| Resource owners and Identity · FG-13 | Typed owner-scoped detail, filtering, history and pagination/count per affected workflow. Deliver these reads with their respective owner workflows. | A13. No protected global counts or raw persistence-model disclosure. Empty and unauthorized/unavailable results are distinct. |
| Documents · FG-14 | Scoped discovery, exact version reads, target links and supersession. Reuse known-version byte upload/download. | A14. History retrieves original bytes/hash. Revocation prevents download. |
| Integration · FG-15 | One governed synthetic exchange with versioned mapping, attempt/result, privileged retry and dead-letter handling. | A15. A transient failure followed by authorized replay yields one downstream outcome and retains attempt history. |
| Notification · FG-16 | Recipient-scoped inbox, delivery status and explicit acknowledgement. | A16. Notification acknowledgement is independent of Workflow task completion. A different recipient cannot read protected evidence. |
| Audit · FG-17 | Scoped event search, export request/state, manifest/hash and authorized retrieval. | A17. Only admitted company, asset and time data are exported. Download rechecks current authority. |
| Workflow and business owners · FG-18 | Include target-specific eligibility, exact linkage, delegated authority and supported transitions with each owner delivery. | A18. Wrong-target, self-review where prohibited, revoked actor and concurrent replay cases fail safely. |

### 8. Scenario readiness and optional configuration

- **Scenario readiness (FG-19).** Version the engine-neutral scenario, accepted baseline digest, boundary conditions, units and intended steady/transient mode. Validate and reconstruct exact input history. Publish a truthful capability state. **A19** reconstructs the scenario after a baseline is superseded.
- **Configuration retirement (FG-20).** Add effective-dated retirement and impact preview where a consumer requires it. **A20** verifies authorized historical reads on both sides of the effective boundary.
- **Deferred capability (FG-21).** Preserve the Hidra-owned engine boundary and operator authority. **A21** tests the difference between raw and trusted observations, documentary and qualified engineering evidence, and scenario readiness and solver results.

## 6. Coverage of the assessed workflows

| Workflow | Planned proof |
|---|---|
| W1 Network and equipment | 2 for scoped discovery and published sources. 6 for integrated provenance. |
| W2 Manufacturer and product information | 2 for selection of exact eligible revisions. |
| W3 Engineering configuration approval and publication | 2 for release lifecycle. 6 for exact-version reconstruction. |
| W4 Telemetry and qualification | 3 for commissioning, raw replay and trusted evidence. |
| W5 Monitoring conditions and deviations | 4 for authoritative computation. 6 for joined evidence. |
| W6 Alarm lifecycle | 5 for correlated raise, acknowledgement and escalation. 6 for native replay. |
| W7 Leak and incident investigation | 5 for initial handoff. 7 for full disposition and CAPA. |
| W8 Integrity and maintenance | 7 for completed finding-to-order lifecycle. |
| W9 Plans, nominations and plan/actual | 1 for approved targets, 4 for plan/actual, 7 for nomination authoring. |
| W10 Custody and measurements | 7 for reconciliation and correction history. |
| W11 Authorized approvals | 1 for Planning, 2 for engineering, and owner-specific tests for additional targets. |
| W12 Analytics, reporting and recommendations | 7 for deterministic analytics and reporting results. 8 for non-numerical scenario readiness. |
| W13 Documents, integration and notification | 7 for each owner lifecycle and authorized readback. |

## 7. Definition of done and first handoff

For each increment, size the smallest coherent external journey and assign one accountable API owner. Record exact source paths, proposed REST shapes and errors, authority and transaction model, applicable migration, test cases and expected evidence before implementation.

| Gate | Required evidence |
|---|---|
| Functional | An independent client creates or discovers prerequisites, performs permitted operations with returned IDs and version tokens, and retrieves the durable final state. |
| Security | Signed JWT, current company/exact resource authority, non-disclosure of protected existence and totals, reviewer independence where applicable and revoke/DENY tests. |
| Persistence | Fresh PostgreSQL/Flyway/JPA proof where applicable. Constraints, forced rollback, replay, concurrency and restart readback cover the changed behavior. |
| Compatibility | Actual runtime OpenAPI and compatible existing client behavior, apart from explicitly approved changes. Publish exact routes, scopes, business errors and examples for frontend consumers. |
| Evidence | Exact source SHA, test cases and raw outcomes, workflow/run details and artifact provenance. Report implementation, test, software acceptance and industrial acceptance separately. |

**First handoff.** Prepare **T01**, then **T02** and **T03** in dependency order. For T01, settle period-code uniqueness, exact company and asset query predicates, REST shapes and the native test ledger. The first Planning increment is complete when T03 proves one signed author-to-independent-reviewer journey through persisted target readback. A task requires separate selection before implementation.
