# HRA-120 — Forensic Remediation Closure Replay

**Initial replay:** 2026-09-29  
**Roadmap:** `HRA-120 — test(architecture): close forensic remediation baseline`  
**Initial execution head:** `43ecfacb54e2b4667d0a0a5b26201c22ad10b30b`  
**Final rerun:** 2026-09-29  
**Final execution head:** `d6d1454b816b819bc90c2c4ef3e382a59cd6d4c0`  
**Current result:** **COMPLETED — all forensic closure gates pass.**

## Method

HRA-120 replayed the HRA-001 static-forensic method over the user-supplied `src.zip`,
`docs.zip`, and `pom.xml`, reconciled to the live HRA-111 head. The replay inspected package and
import boundaries, domain/JPA shapes, incoming static references, module event/publisher remnants,
REST boundary representation, duplicate JPA mappings, and the accepted HRA classification evidence.

The supplied checkout used CRLF line endings. After CRLF-to-LF normalization, sampled Git blob
hashes for the POM, roadmap, HRA-110/HRA-111 evidence, HRA-111 migrations/test, and
`EvaluatePermissionRequest.java` matched live `main`.

The original HRA-001 detector implementation was not versioned, so this closure does not manufacture
precision for historical classifier categories whose executable detector is unavailable. It uses
reproducible current-source scans plus the versioned classification documents and guardrail tests.

## Baseline comparison

| Metric | HRA-001 baseline | HRA-120 replay |
|---|---:|---:|
| Java files | 4,988 | 3,681 |
| Production Java files | 4,914 | 3,592 |
| Test Java files | 74 | 89 |
| Domain model source types | 467 | 123 |
| JPA entities | 465 | 465 |
| Enums | 338 | 335 |
| Same-named domain/JPA pairs | 465 | 122 |
| Exact domain/JPA structural pairs | 451 | 110 |
| Deliberate normalization-only REAL_DOMAIN mirror pairs | n/a | 51 |
| Module domain-event Java files | 100 historical candidates | 0 |
| Module event-publisher remnants | 24 | 0 |
| Exact Request/Command pairs | 65 | 65 |
| Exact Response/SummaryDto pairs | 59 | 59 |
| Unapproved cross-module private imports | present | 0 |
| Approved exported cross-module import edges | n/a | 3 |
| API-to-domain imports | 136 | 134 |
| API-to-domain enum imports | 125 | 125 |
| Direct REST request/response non-enum domain representation | not separately counted | **1** |
| Unresolved explicit internal imports | 0 | 0 |
| Duplicate JPA table mappings | 0 | 0 |
| Duplicate entity columns | 0 | 0 |
| Explicit production import cycles | 0 | 0 |
| API-to-infrastructure imports | 0 | 0 |
| Application-to-infrastructure imports | 0 | 0 |

The metrics describe the pre-HRA-120 head. This audit commit adds one architecture test, so the
post-commit raw Java count is expected to become 3,682: 3,592 production and 90 test files.

## Exit-gate replay

### Confirmed data-loss path — CLOSED

The HRA-001 Employee birth-data defect is closed across domain, JPA, mapper, Flyway migration, and
focused migration/mapper tests. HRA-023 remains a separately blocked destructive Organization
compatibility cutover; its reconciliation gate is not evidence that the birth-data defect returned.

### Unapproved cross-module private coupling — CLOSED

The replay finds zero business-module imports into another module's private domain, infrastructure,
or non-exported application packages. The only remaining cross-module edges use the approved
Planning-to-Workflow and Organization-to-Topology exported application contracts.

### Static orphan classification — CLOSED WITH ONE NEW CLASSIFIED RESIDUAL

All 197 HRA-041 DELETE dispositions remain absent. Eleven historical KEEP candidates remain
zero-incoming with documented KEEP evidence, while `LeakCaseView` and `LeakCandidateView` now
have active consumers.

One newly zero-incoming domain-value enum is present:

- `topology.ProjectionType` — **DELETE_RESIDUAL / classified, not removed by HRA-120**.

It has no incoming Java reference and aligns with the retired Topology projection path. HRA-120 is
an audit/test task, so production deletion requires a separately authorized code task.

### Fictional event/outbox architecture — CLOSED

The module source tree contains zero module `domain/event` Java files and zero module event-publisher
implementation files from the HRA-030/HRA-031B fictional event subgraph.

### Remaining duplication — DELIBERATE / DOCUMENTED

HRA-060/HRA-061 deliberately retain 51 REAL_DOMAIN normalization-only domain/JPA pairs and retired
the 343 persistence/read mirrors. HRA-070 through HRA-073 deliberately retain layer-owned
Request/Command and Response/SummaryDto types under generated mapping policy. HRA-080/HRA-081 retain
all seven reviewed duplicate enum vocabularies; zero merge/delete action was authorized.

## Initial closure blocker — resolved by HRA-121

HRA-102 forbids direct API exposure of a non-enum domain record/value object. The replay finds exactly
one direct REST wire representation:

```text
dz.sh.hidra.modules.identity.api.rest.request.EvaluatePermissionRequest
  -> dz.sh.hidra.modules.identity.domain.value.AuthorizationScope
```

`AuthorizationScope` is a domain record and `EvaluatePermissionRequest.scope` is a REST request
record component consumed through `@RequestBody`. This is wire representation, not exception
translation or mapper-internal adaptation.

HRA-120 must not silently approve an HRA-102 exception or alter production contracts under a
test/closure task. Therefore the forensic baseline is not closed on this head.

## Validation evidence

- Java 21 was available for local source/test syntax checks.
- Maven was not installed in the supplied execution environment; no local Maven pass is claimed.
- Docker was not installed; no local Testcontainers pass is claimed.
- HRA-111 CI run `36540197630` completed successfully on exact execution head `43ecfacb54e2b4667d0a0a5b26201c22ad10b30b`.
- HRA-111 migrations contain 551 new same-module foreign keys and matching validation statements.
- The HRA-120 source guardrail was syntax-checked and its source-level assertions were replayed
  locally against the supplied snapshot.

## Initial closure decision — superseded by final rerun

**HRA-120 status: BLOCKED.**

The accepted remediation tracks materially reduced the HRA-001 risk surface and all HRA-120 exit
gates except direct REST/domain-record representation now pass. Closure requires an explicitly
authorized follow-up that replaces the Identity REST use of `AuthorizationScope` with an
API/application-owned boundary representation. The separately classified
`topology.ProjectionType` residual may also be deleted only under an authorized code task.

HRA-023 remains separately blocked by its Organization reconciliation/cutover gate and is not
bypassed by this audit.


## HRA-121 corrective follow-up

HRA-121 removes the direct HRA-102 REST/domain-record representation identified by this replay
without changing Identity authorization semantics.

The REST boundary now owns `AuthorizationScopeRequest(scopeType, scopeReferenceId,
scopeCodeSnapshot)`. The same-module `ScopeType` enum remains deliberately reusable under the
HRA-102 enum-wire-vocabulary policy, while `IdentityRestMapper` alone constructs the existing
domain `AuthorizationScope` before creating `EvaluatePermissionQuery`.

Focused mapper/controller tests lock:

- the established nested scope component names;
- GLOBAL normalization through the domain constructor;
- non-global scope reference/code normalization;
- optional null-scope behavior; and
- controller forwarding of the mapped application query.

`ForensicRemediationClosureTest` now requires the direct REST request/response non-enum domain
representation set to be empty rather than pinning the former exception.

HRA-120 remains **BLOCKED** pending the separately authorized HRA-122 removal of the classified
`topology.ProjectionType` residual and a subsequent full HRA-120 replay. HRA-121 does not alter
LOCAL, LDAP/AD, OIDC authentication, HidraPrincipal normalization, JWT issuance, authorization
policy semantics, or database schema.


## HRA-122 corrective follow-up

HRA-122 removes the separately classified Topology residual without broadening into Analytics or
other projection-related concepts.

Exact-head revalidation before deletion confirmed that
`dz.sh.hidra.modules.topology.domain.value.ProjectionType` had no Java, configuration,
serialization, or reflection consumer. The complete enum vocabulary also appeared nowhere else.
The unrelated Analytics `projectionType` field remains unchanged and continues to be owned by the
Analytics module as string state.

`ProjectionType.java` is now removed, and `ForensicRemediationClosureTest` verifies that the
source file stays absent while the orphan-classification evidence records
`DELETE_RESIDUAL → REMOVED_HRA_122`.

After HRA-121 and HRA-122, both concrete blockers found by the 2026-09-29 HRA-120 replay have been
corrected. **HRA-120 remains BLOCKED only pending a fresh replay of the same forensic methodology
against the new live head.** This HRA-122 task does not itself claim forensic closure.


## HRA-120 final rerun

HRA-120 was rerun on 2026-09-29 against exact live head
`d6d1454b816b819bc90c2c4ef3e382a59cd6d4c0`, after completion of HRA-121 and HRA-122.

### Exact-head validation

GitHub Actions run `36547801339` (HidraAPI CI #423) completed successfully on the exact rerun
head. That run executed the repository verification suite including
`ForensicRemediationClosureTest`, so the existing closure guardrails passed without adding an
exception.

Current tree facts at the rerun head:

| Metric | Final rerun |
|---|---:|
| Java files | 3,684 |
| Production Java files | 3,592 |
| Test Java files | 92 |
| Domain model source types | 123 |
| JPA entities | 465 |
| Flyway migrations | 34 |
| Module domain-event Java files | 0 |
| Module event-publisher Java files | 0 |
| Topology `ProjectionType.java` | absent |
| API-owned `AuthorizationScopeRequest` | present and consumed |

### Final exit-gate replay

- **Confirmed data-loss path:** CLOSED. The Employee birth fields remain represented in the domain,
  JPA entity, persistence mapper, and immutable Flyway migration; HRA-121/HRA-122 did not modify
  that path.
- **Unapproved cross-module private coupling:** CLOSED. The exact-head forensic/architecture
  guardrails passed with no private cross-module dependency exception reintroduced.
- **Unclassified orphan:** CLOSED. The initial replay's only newly discovered orphan,
  `topology.ProjectionType`, was evidence-classified and removed by HRA-122. The only production
  type added by HRA-121 is `AuthorizationScopeRequest`, which has live incoming references from
  `EvaluatePermissionRequest` and `IdentityRestMapper` plus focused tests. No new unclassified
  production orphan is introduced by the corrective delta.
- **Fictional event architecture:** CLOSED. There are zero module `domain/event` Java files and
  zero module event-publisher Java files.
- **Forbidden REST/domain representation:** CLOSED. HRA-121 replaced the direct domain-record wire
  exposure with the API-owned scope record. The exact-head closure guardrail requires the set of
  REST request/response imports of non-enum domain representation to be empty and passed in CI #423.
- **Remaining duplication:** DELIBERATE / DOCUMENTED. HRA-121/HRA-122 did not alter the accepted
  HRA-060/HRA-061 mirror dispositions, HRA-070 through HRA-073 boundary-type policy, or HRA-080
  duplicate-enum decisions.

### Final closure decision

**HRA-120 status: COMPLETED.**

All HRA-120 forensic exit gates pass on exact head
`d6d1454b816b819bc90c2c4ef3e382a59cd6d4c0`. No production change is included in this final
rerun commit; it records and pins the successful closure result.

HRA-023 remains separately blocked by its Organization reconciliation/cutover evidence gate. That
blocked destructive cutover is not bypassed or implicitly approved by repository-remediation
closure.
