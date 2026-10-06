# HidraAPI API Overview

## Status

CURRENT — source-generated HTTP API overview.

## Version and Namespace

The generated contract declares OpenAPI **3.1.0** with API information version **v1**. Current product endpoints are predominantly namespaced under `/api/v1/**`.

No production hostname or public ingress URL is established by this document. The `127.0.0.1:8080` server recorded in the generated contract is the deterministic CI generation address.

## Generated Surface

The canonical HPR-P2-005 snapshot contains **240 paths**, **259 HTTP operations**, and **227 component schemas**.

It represents HTTP controller surfaces across HidraAPI business modules and platform capabilities. The API contract is generated from the running application rather than inferred from module documentation.

## Realtime Boundaries

The HTTP contract includes one `text/event-stream` response surface. WebSocket/STOMP is also a current platform/runtime capability, but the STOMP protocol is not represented as a complete HTTP OpenAPI contract and must not be inferred from `openapi.yaml`.

## Authentication Distribution

The generated operation security declarations are:

- 256 operations: `hidraBearerJwt`;
- 1 operation: `externalOidcBearerJwt`;
- 2 operations: explicitly public.

The exact public/OIDC operations are documented in `AUTHENTICATION_AUTHORIZATION.md`.

## Contract Scope

`openapi.yaml` is authoritative for generated HTTP paths, methods, parameters, request bodies, response bodies, schemas, operation security and generated metadata at its source SHA.

It is not by itself proof of runtime availability, authorization for a particular user, performance characteristics, production routing, database semantics or non-HTTP integration protocols.
