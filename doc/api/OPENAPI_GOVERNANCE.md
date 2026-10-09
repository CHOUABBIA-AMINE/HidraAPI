# HidraAPI OpenAPI Governance

## Status

CURRENT — canonical OpenAPI generation/provenance control, refreshed by HPR-P2-012.

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

The current `x-hidra-ci-source-sha` is:

`617c2eec812e3a5734957ee9fa0360f6f5613032`

That is intentional: it identifies the executable revision that generated the contract, not the later documentation/CI commit that versions the snapshot.

## Historical HPR-P2-005 Provenance

- initial generator: `6822f3ce79b305e1c22f48ff20ef0b0bd2c7f135`;
- full CI run #566 / ID `37444404855`: SUCCESS;
- generated artifact ID `11402224423`;
- artifact digest `sha256:763472783957989e9aa4d56394d5d183fd835321d1c3cec2639d2a4fe8a06a44`;
- generated OpenAPI: 3.1.0;
- generated paths: 240;
- generated operations: 259;
- generated schemas: 227.

The restored P1-complete tree was verified identical to that executable source tree, and intervening P2 changes through the HPR-P2-005 base are documentation-only.

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

[Validation guide](../governance/DOCUMENTATION_VALIDATION.md) documents the reviewed manifest, structural checks and strict production runtime comparison. Offline documentation validation verifies canonical serialization, file digest/counts/source provenance, local references and representative security declarations. Production CI compares freshly generated content after removing only the source-SHA field, validating both generator identities separately. All other object values and array order remain significant. Supported backward compatibility remains a separate required gate.

A contract change requires a successful generated artifact and reviewed snapshot/provenance/manifest refresh; never widen an ignore list or hand-author a schema to silence a failure. These checks cover the repository JSON snapshot subset, not complete OpenAPI certification or production acceptance.
