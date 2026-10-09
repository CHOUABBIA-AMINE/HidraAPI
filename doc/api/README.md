# HidraAPI Canonical API Documentation

## Status

CURRENT — canonical API index; snapshot refreshed by HPR-P2-012.

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
