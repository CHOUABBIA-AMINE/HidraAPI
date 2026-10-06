# HidraAPI Canonical API Documentation

## Status

CURRENT — canonical HPR-P2-005 API index.

## Contract Provenance

Version-controlled contract: `doc/api/openapi.yaml`

OpenAPI version: **3.1.0**

API information version: **v1**

Executable source SHA recorded by the generated contract: `6822f3ce79b305e1c22f48ff20ef0b0bd2c7f135`

Generation evidence:

- workflow: `HidraAPI CI`;
- run: **#566**;
- run ID: **37444404855**;
- result: **SUCCESS**;
- retained artifact ID: **11402224423**;
- retained artifact digest: `sha256:763472783957989e9aa4d56394d5d183fd835321d1c3cec2639d2a4fe8a06a44`.

The restored P1-complete repository tree was verified identical to the executable tree at that SHA. HPR-P2-001..005 changes preceding this documentation update are documentation-only, so HPR-P2-005 does not fabricate a new executable build result.

## Current Contract Summary

The retained generated contract contains:

- **240** paths;
- **259** HTTP operations;
- **227** component schemas;
- **256** operations requiring `hidraBearerJwt`;
- **1** operation requiring `externalOidcBearerJwt`;
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

HPR-P2-012 remains responsible for adding automated canonical documentation/OpenAPI drift validation.
