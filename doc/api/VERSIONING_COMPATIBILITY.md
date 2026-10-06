# HidraAPI API Versioning and Compatibility

## Status

CURRENT — v1 namespace and executable backward-compatibility gate.

## Version Baseline

Current generated metadata reports API version `v1`, and product endpoints are predominantly under `/api/v1/**`.

A future incompatible contract generation must not silently reuse the same compatibility expectations without an approved versioning decision.

## Compatibility Base Selection

The current `HidraAPI CI` workflow selects the comparison base as follows:

- pull request: PR base SHA;
- push: event `before` SHA;
- manual workflow dispatch: current commit parent.

The workflow builds and starts that exact base revision independently, generates its OpenAPI document, and compares it with the current generated document.

## Enforced Breaking-Change Classes

`.github/scripts/openapi_compatibility.py` currently rejects supported breaking changes including:

- removed paths;
- removed operations;
- removed parameters;
- a parameter becoming required;
- new required parameters;
- a request body becoming required;
- new required request properties;
- removed request content types;
- request enum value contraction;
- schema type changes;
- removed response codes;
- removed response content types;
- removed response properties;
- weakening previously required response properties;
- response enum expansion;
- public operation becoming authenticated;
- changed `oneOf`/`anyOf`/`allOf` alternative count where compared.

## Gate Limitations

The checker is intentionally evidence-specific, not a proof of all forms of semantic compatibility. For example, the current script does not use operation-ID changes alone as a breaking-change signal and cannot detect undocumented behavioral changes.

## Change Rule

Executable API changes require the repository's full CI/OpenAPI generation and compatibility gate. The committed canonical snapshot must then be regenerated from the successful executable contract; endpoint/schema content must not be hand-edited to evade the compatibility gate.
