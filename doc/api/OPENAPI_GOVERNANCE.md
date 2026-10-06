# HidraAPI OpenAPI Governance

## Status

CURRENT — canonical HPR-P2-005 OpenAPI generation and provenance control.

## Source of Generation

The repository CI generates the current contract by:

1. running the Java/Maven verification gate;
2. starting the built HidraAPI executable with `SPRING_PROFILES_ACTIVE=test`;
3. enabling Springdoc API docs and disabling Swagger UI;
4. waiting for application health;
5. retrieving `/v3/api-docs`;
6. verifying required security schemes and representative protected/OIDC/public operations;
7. adding `x-hidra-ci-source-sha`;
8. serializing deterministically with sorted JSON keys;
9. generating the exact comparison-base contract;
10. applying `.github/scripts/openapi_compatibility.py`;
11. retaining the generated contract as a workflow artifact.

## Canonical Snapshot

`doc/api/openapi.yaml` is the version-controlled canonical snapshot.

For deterministic compactness it is stored using JSON syntax, which is valid YAML 1.2. Its semantic object is reconstructed without endpoint/schema edits from the generated CI artifact.

The preserved `x-hidra-ci-source-sha` remains:

`6822f3ce79b305e1c22f48ff20ef0b0bd2c7f135`

That is intentional: it identifies the executable revision that generated the contract, not the later documentation-only commit that versions the snapshot.

## HPR-P2-005 Provenance

- full CI run #566 / ID `37444404855`: SUCCESS;
- generated artifact ID `11402224423`;
- artifact digest `sha256:763472783957989e9aa4d56394d5d183fd835321d1c3cec2639d2a4fe8a06a44`;
- generated OpenAPI: 3.1.0;
- generated paths: 240;
- generated operations: 259;
- generated schemas: 227.

The restored P1-complete tree was verified identical to that executable source tree, and intervening P2 changes through the HPR-P2-005 base are documentation-only.

## Update Procedure

When executable API behavior changes:

1. change executable source/tests as authorized by its roadmap task;
2. run full exact-head CI;
3. allow CI to generate current and base OpenAPI;
4. pass security-contract assertions;
5. pass backward-compatibility checking;
6. retain the generated artifact;
7. replace the committed snapshot with the successful generated contract;
8. update API governance/provenance metadata when appropriate.

Do not hand-author endpoint/schema changes directly in `openapi.yaml`.

## Drift Control

HPR-P2-012 remains responsible for automated canonical-document/OpenAPI snapshot drift validation. HPR-P2-005 establishes the canonical artifact and governance rule but does not prematurely modify CI workflows.
