# Frontend ↔ Backend Gap Remediation 02

```text
Roadmap code    : FRONTEND-BACKEND-GAP-002
Commit message  : FRONTEND-BACKEND-GAP-002
Source register : CHOUABBIA-AMINE/HidraWEB/docs/roadmap/HidraAPI-Frontend-Backend-Gap-Register.md
Backend baseline: af4c3b4723619a25dd9a94f4d27f5a36adab982e
Branch          : frontend-backend-gap-remediation-02-hardening
Status          : In Progress
```

## Objective

Close or explicitly disposition every confirmed backend-owned gap currently recorded by HidraWEB, without moving business rules to the frontend and without violating HidraAPI module ownership.

This is one cross-cutting remediation task because the consumer register is the acceptance checklist. Each change remains physically owned by its existing module.

## Gap acceptance checklist

| Gap | Backend action in this task | Status |
|---|---|---|
| GAP-SEC-001 | Verify and harden authenticated principal contract for both Basic and JWT authentication. | In Progress |
| GAP-SEC-002 | Publish repository-side browser OIDC/JWT contract and configuration metadata; external IdP registration remains environment-owned. | In Progress |
| GAP-SEC-003 | Enforce effective Hidra permissions at the backend route boundary and publish effective principal permissions. | In Progress |
| GAP-CONTRACT-001 | Produce deterministic OpenAPI artifact in CI and retain it as a workflow artifact. | In Progress |
| GAP-REALTIME-001 | Publish a typed realtime event envelope/catalog with destinations and recovery semantics. | Deferred pending verified domain publishers |
| GAP-ID-001 | Verify dedicated identity read/query API merged by remediation 01. | In Progress |
| GAP-ID-002 | Verify and harden role/permission/grant administration contracts merged by remediation 01. | In Progress |
| GAP-ORG-001 | Add dedicated organization hierarchy/employee/assignment read APIs. | In Progress |
| GAP-TOPO-001 | Verify strongly typed map responses merged by remediation 01. | In Progress |
| GAP-TOPO-002 | Verify pipeline-system and pipeline map layers merged by remediation 01. | In Progress |
| GAP-TOPO-003 | Verify count-aware paging/filter-before-slice semantics merged by remediation 01. | In Progress |
| GAP-TEL-001 | Add telemetry reading/history/trend query contract. | In Progress |
| GAP-TEL-002 | Add telemetry reading-state and quality reference contract. | In Progress |
| GAP-MON-001 | Add monitoring rule/deviation read/query contract. | In Progress |
| GAP-WF-001 | Add authenticated-principal task inbox/query contract. | In Progress |
| GAP-WF-002 | Add workflow instance/task timeline/history query contract. | In Progress |
| GAP-WF-003 | Add backend-provided available-action/transition metadata. | In Progress |
| GAP-WF-004 | Add an authoritative workflow transition execution contract that advances task/instance state and records audit history. | Open — issue #57 |
| GAP-ALARM-001 | Add active/history/detail alarm query contracts. | In Progress |
| GAP-ALARM-002 | Add audited alarm shelving/unshelving contract when supported by the existing alarm model; otherwise document the model limitation explicitly. | In Progress |
| GAP-ALARM-004 | Decide whether suppression is distinct from shelving before publishing any suppression mutation contract. | Open — issue #58 |
| GAP-ALARM-005 | Derive acknowledgement/closure actor identity from the authenticated principal; remove authoritative browser-selected actor identity from the public request contract. | Implemented — issue #56, CI #34615308694 green |
| GAP-ENG-001 | Publish one concurrency-protected maintainable-asset update mutation with explicit `expectedUpdatedAt`, deterministic stale conflict, and refreshed token response. | VERIFIED — PR #81 / issue #79 |
| GAP-DOC-001 | Publish authoritative multipart document-version upload and browser-readable version-content retrieval contracts; keep storage-object identity backend-owned. | In Progress — issue #83 |

## Architecture rules

- API controllers expose DTOs/application-port records only, never JPA entities.
- Domain packages stay free of Spring, JPA, REST, and foreign aggregate classes.
- Query adapters live in the owning module infrastructure package.
- Cross-module references remain opaque IDs.
- Workflow owns process state and transition availability; target modules own business facts.
- Authentication transport remains platform-owned; identity owns user/role/permission meaning.
- No frontend-driven authorization is trusted as an enforcement boundary.
- Audit actor identity is derived from the authenticated platform security context, not selected by a browser request.

## Validation

Required before completion:

```bash
./mvnw -B -q -DskipTests compile
./mvnw -B -q test
./mvnw -B -q clean verify
```

Additionally verify:

- application context starts in existing integration tests;
- architecture guardrails remain green;
- `/v3/api-docs` contains the new contracts;
- CI publishes the OpenAPI artifact;
- all repository-owned gaps above have implementation evidence;
- external IdP registration/configuration is clearly separated from repository-owned work.

## GAP-DOC-001 contract — HWEB-014-03 document-transfer prerequisite

```text
Roadmap task              : FRONTEND-BACKEND-GAP-002 (GAP-DOC-001 slice)
Backend issue             : CHOUABBIA-AMINE/HidraAPI#83
Owning module             : documents
Multipart upload          : POST /api/v1/documents/document-versions/upload
Multipart metadata part   : metadata (UploadDocumentBinaryVersionRequest JSON)
Multipart binary part     : file
Content retrieval         : GET /api/v1/documents/document-versions/{versionId}/content
Storage object ownership  : backend-generated opaque id
Derived upload evidence   : content length, SHA-256 checksum, MIME type, original filename
Default max upload        : 52428800 bytes (configurable with hidra.documents.upload.max-bytes)
Storage root              : configurable with hidra.documents.storage.root
Download disposition      : attachment with UTF-8 filename
Range behavior            : unsupported; response publishes Accept-Ranges: none
Browser CORS exposure     : Content-Disposition, Content-Length, Accept-Ranges
Direct storage URI        : not exposed
Legacy JSON metadata POST : retained for compatibility; HWEB-014-03 must use multipart contract
Deterministic errors      : DOCUMENTS_CONTENT_INVALID / DOCUMENTS_CONTENT_NOT_FOUND /
                            DOCUMENTS_CONTENT_STORAGE_FAILURE
Permission source         : backend route descriptor; frontend must not infer permission strings
```

HWEB-014-03 may consume only the multipart upload and version-scoped content route above. It must not synthesize object-store URLs, client-generate `storageObjectId`, or infer range/resume behavior. The backend computes byte length and SHA-256 from the received stream and persists that evidence with the storage object/version metadata.

The browser client is cross-origin in the supported HidraWEB runtime model. Therefore the repository-owned default CORS contract must expose `Content-Disposition`, `Content-Length`, and `Accept-Ranges` so authenticated JavaScript can read the server-authored filename and transfer evidence. A frontend filename fallback or direct unauthenticated object navigation is not an acceptable substitute.

## GAP-ENG-001 contract — HWEB-011-06 concurrency prerequisite

```text
Roadmap task          : FRONTEND-BACKEND-GAP-002 (GAP-ENG-001 slice)
Backend issue         : CHOUABBIA-AMINE/HidraAPI#79
Owning module         : assets
Aggregate             : MaintainableAsset
Mutation              : PATCH /api/v1/assets/maintainable-assets/{assetId}
Mutable field         : assetName only
Client precondition   : expectedUpdatedAt
Token source          : MaintainableAssetResponse.updatedAt
Repository discipline : pessimistic write lock before token comparison
Stale response        : HTTP 409 / ASSETS_MAINTAINABLE_ASSET_CONFLICT
Retry rule            : refetch the maintainable asset before any retry
Preserved semantics   : lifecycle status, topology references, ownership/manufacturer references,
                        installation/commissioning/retirement timestamps and all other fields remain unchanged
Permission source     : backend route descriptor for PATCH; frontend must not infer the permission string
```

This contract intentionally promotes `MaintainableAsset.updatedAt` to a write precondition only for the route above. It must not be generalized to other assets resources or lifecycle commands without a separately published backend contract.

## Completion evidence

### GAP-ALARM-005 — trusted acknowledgement/closure actor attribution

```text
Issue               : #56
Implementation SHA  : 53f8e3e32e83d61247a989b7295da7f8ad249042
CI run              : 34615308694
Result              : PASS
Repository compile  : PASS
Repository tests    : PASS
Repository verify   : PASS
Acceptance compile  : PASS
Acceptance tests    : PASS
Acceptance verify   : PASS
OpenAPI publication : PASS
```

The public `AcknowledgeAlarmRequest` and `CloseAlarmRequest` no longer carry the authoritative actor identifier. `SpringAlarmController` resolves actor identity through `CurrentActorResolver`; acknowledgement display identity is derived from the authenticated principal name. Alarm application/domain persistence remains unchanged and receives server-derived identity through its application command.

### GAP-ENG-001 — maintainable asset optimistic precondition

```text
Issue                  : #79
Product PR             : #81
Final product head     : 5d56ced94a02b56ad8d9f7ecda1da1218a0026a7
Initial CI run         : 34724307897 — FAILED (Spring proxying only; transactional service was final)
Corrected exact-head CI: 34724457582 — SUCCESS
Merge SHA              : 2e6f93c14e330c8cc839a5de75ecc7b893f9872c
Post-merge CI          : 34724675473 — SUCCESS
OpenAPI artifact id    : 10307945855
OpenAPI artifact name  : hidra-api-openapi-2e6f93c14e330c8cc839a5de75ecc7b893f9872c
OpenAPI artifact digest: sha256:20b15395d1b2feec853167e88b2b6357650f51c03fb7811ffe60fd1362544c7f
Repository compile     : PASS
Repository tests       : PASS
Repository verify      : PASS
Acceptance compile     : PASS
Acceptance tests       : PASS
Acceptance verify      : PASS
OpenAPI publication    : PASS
Result                 : VERIFIED
```

The implementation publishes `PATCH /api/v1/assets/maintainable-assets/{assetId}` with request fields `expectedUpdatedAt` and `assetName`. The service acquires a pessimistic write lock before comparing the exact client token, preserves every field except `assetName`, returns a refreshed `updatedAt`, and maps stale requests to deterministic `409 ASSETS_MAINTAINABLE_ASSET_CONFLICT` with an explicit refetch-before-retry rule.

The initial exact-head CI failure was infrastructure wiring only: `@Transactional` required Spring proxying, but `AssetsApplicationService` was `final`. Removing only that modifier resolved the context-load failure without changing contract or business semantics.

GAP-ENG-001 is verified and the backend prerequisite for HidraWEB HWEB-011-06 concurrency testing is satisfied. The cross-cutting roadmap remains `In Progress` because `GAP-WF-004`, `GAP-ALARM-004`, `GAP-DOC-001`, and other remaining repository-owned gaps are not all resolved.
