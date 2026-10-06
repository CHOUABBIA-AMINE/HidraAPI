# HidraAPI API Conventions

## Status

CURRENT — conventions evidenced by the generated HPR-P2-005 contract and current security/runtime source.

## URI Versioning

Current product APIs use the `/api/v1/**` namespace and the generated OpenAPI metadata reports version `v1`.

## Representation

JSON is the dominant request representation in the generated contract. The contract also contains a multipart request surface and a `text/event-stream` response surface where explicitly declared.

Consumers must use the media type, schema and requiredness declared for the specific operation. HPR-P2-005 does not invent a repository-wide media type when the generated operation says otherwise.

## Parameters and Validation

Path/query/header parameters and request schemas are governed by the generated contract. Generated minimum/maximum, required-property, enum, format and other constraints are part of the versioned snapshot where present.

Runtime validation may be stricter where implementation rules are not represented by OpenAPI; undocumented runtime behavior must not be silently promoted into the contract.

## Operation IDs

Generated operations carry operation IDs. The current repository compatibility checker does **not** compare operation-ID changes, so operation IDs must not be represented as the sole backward-compatibility guarantee.

## Response Documentation

At the HPR-P2-005 source baseline the generated OpenAPI response declarations contain success responses only: HTTP 200 and 201. That is a documentation limitation, not a claim that runtime failures cannot occur.

Canonical error-contract status is documented separately in `ERROR_MODEL.md`.

## Pagination and Query Shapes

Pagination/query conventions are operation-specific and come from generated parameters and schemas. Do not infer a universal page size, filtering language or sorting contract beyond what each generated operation declares.

## Correlation and Request Metadata

Correlation/request metadata exists in parts of the application, but HPR-P2-005 does not declare a universal client-supplied correlation header unless the generated operation explicitly exposes it.
