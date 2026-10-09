# HidraAPI Canonical API Documentation

## Status

CURRENT — canonical API index; snapshot refreshed by HPR-P2-012; runtime conventions/error documentation corrected under HPR-P2-005 on 2026-10-09.

## Historical HPR-P2-005 Contract Provenance

Version-controlled contract: `doc/api/openapi.yaml`

OpenAPI version: **3.1.0**

API information version: **v1**

Historical initial generator: `6822f3ce79b305e1c22f48ff20ef0b0bd2c7f135`

Generation evidence:

- workflow: `HidraAPI CI`;
- run: **#566**;
- run ID: **37444404855**;
- result: **SUCCESS**;
- retained artifact ID: **11402224423**;
- retained artifact digest: `sha256:763472783957989e9aa4d56394d5d183fd835321d1c3cec2639d2a4fe8a06a44`.

The restored P1-complete repository tree was verified identical to the executable tree at that SHA. HPR-P2-001..005 changes preceding this documentation update are documentation-only, so HPR-P2-005 does not fabricate a new executable build result.

## HPR-P2-012 refreshed generation provenance

Current generator: `617c2eec812e3a5734957ee9fa0360f6f5613032`; successful production CI #604,
run `37841205677`; artifact `11577329466`,
`hidra-api-openapi-617c2eec812e3a5734957ee9fa0360f6f5613032`. ZIP SHA-256:
`2ff35c384c54b2d49e50aa2a9e646a67c62427e8afcc8db92e17e5368a91b334`.
Generated member hidra-api.json SHA-256:
`1ee03ad711b79c7ef42767a6aa3c685f4857800f09f03002deeac49e4e2d23d4`.
Sorted compact snapshot SHA-256:
`ba9511455ebabb51c704ef510d08f4fdc766f29d8def0a34ef7a8f1acf86d9fd`.
These are distinct file/archive digests, not Git object IDs. The object is copied
from verified generated evidence without endpoint/schema/security edits.
HPR-P2-012 source applicability: parent `508337351eef03b82e2c6078a7c8523013efd063`, checked 2026-10-09.
This commit is the validator/snapshot consumer, not its executable generator.

## Current Contract Summary

The retained generated contract contains:

- **244** paths;
- **263** HTTP operations;
- **231** component schemas;
- **259** operations requiring `hidraBearerJwt`;
- **2** operations requiring `externalOidcBearerJwt`;
- **2** explicitly public operations.

The generated server URL is the CI generation endpoint `http://127.0.0.1:8080`; it is not a production base-URL declaration.

## Canonical Set

- `openapi.yaml` — deterministic generated API contract snapshot.
- `API_OVERVIEW.md` — API boundary and generated-contract summary.
- `API_CONVENTIONS.md` — current source-visible API conventions.
- `AUTHENTICATION_AUTHORIZATION.md` — OpenAPI/runtime authentication boundaries.
- `ERROR_MODEL.md` — current error-contract evidence and explicit gaps.
- `VERSIONING_COMPATIBILITY.md` — v1 and backward-compatibility controls.
- `OPENAPI_GOVERNANCE.md` — generation, provenance and change-control procedure.

## Authority

Executable controllers/request-response types, security configuration and generated `/v3/api-docs` remain the source from which the contract is generated. The committed snapshot makes that generated contract reviewable and version controlled.

If executable evidence and this snapshot ever diverge, the divergence is a defect: regenerate from the executable source and reconcile it rather than hand-editing endpoint/schema content.

[Validation controls](../governance/DOCUMENTATION_VALIDATION.md) implement canonical documentation checks, deterministic snapshot integrity and production runtime equality.

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

## HPR-P2-005 API documentation audit correction — 2026-10-09

Current source verification parent: `b39c87dcaf887ada1d22c6a88d1e1f51b9f7ecaa`.
Preflight Documentation Validation #130, run 37920910941, PASSED on that SHA.
[API conventions](API_CONVENTIONS.md) now documents representative Workbench and
Telemetry query/paging/filter/sort rules, actual response DTOs and tracing headers.
[Error model](ERROR_MODEL.md) now documents global MVC mappings and five scoped
advice classes, body differences, validation/not-found cases, and filter boundaries.
These are source-derived current descriptions, not a new universal error contract.

The unchanged committed snapshot has 258 HTTP-200 and five HTTP-201 declarations;
its 244-path/263-operation/231-schema summary and generator provenance above govern
current generated counts. Untouched API guides retain their original generation
applicability; older numeric summaries must not override the current snapshot.
This bounded correction does not reverify every untouched guide or change auth/API
behavior. Shared generated 4xx/5xx/error-envelope coverage remains NOT ESTABLISHED.

Ownership follows the canonical register and existing repository/module authorities;
this correction appoints no new named owner or grants new business/security approval.
Source links identify the responsible controller/advice/adapter rather than treating
an index as executable truth. Future changes must reconcile these source-derived
rules with the generated contract and current implementation.

HPR-P2-005 correction implementation is complete; exact-head Documentation Validation
on its publication is PENDING. P2 remains OPEN for HPR-P2-006 and HPR-P2-013; P3 is
DEFERRED. The current roadmap supersedes dated CLOSED/pending-CI statements for phase
execution. Prior generation/CI provenance remains historical evidence at its SHA.
No runtime tests, schema changes, deployment, import or release were performed here.
