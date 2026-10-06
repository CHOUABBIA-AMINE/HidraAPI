# HidraAPI API Error Model

## Status

CURRENT LIMITATION — a shared machine-readable API error envelope is **NOT ESTABLISHED** by the HPR-P2-005 generated contract.

## Generated Evidence

The canonical OpenAPI snapshot documents only successful response codes:

- HTTP 200 for 254 generated operations;
- HTTP 201 for 5 generated operations.

Repository inspection for HPR-P2-005 did not establish a shared generated `ProblemDetail`, global REST-controller-advice error schema, or equivalent common OpenAPI error component.

## Interpretation

This does **not** mean runtime requests cannot fail. Authentication failures, authorization failures, validation errors, missing resources, conflicts and server errors can exist at runtime.

It means HPR-P2-005 cannot truthfully define their status-code/body contract as canonical without generated/source evidence.

## Governance Rule

Do not invent a common 4xx/5xx envelope or response taxonomy in documentation.

A future standardized error contract must be implemented in executable code, represented in generated OpenAPI, covered by tests, and evaluated by compatibility governance before becoming CURRENT.

Until then, clients must not rely on an undocumented universal error-body schema.
