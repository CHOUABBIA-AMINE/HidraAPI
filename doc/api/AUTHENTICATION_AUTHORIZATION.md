# HidraAPI API Authentication and Authorization

## Status

CURRENT — generated security-contract baseline reconciled with current Spring Security configuration.

## Security Schemes

The canonical generated OpenAPI contract declares two HTTP bearer JWT schemes:

### `hidraBearerJwt`

Used for ordinary protected HidraAPI operations.

### `externalOidcBearerJwt`

Accepted by the OIDC completion bridge where the external OIDC bearer token is exchanged/completed into the Hidra authentication flow.

## Explicitly Public Product Operations

The generated contract declares an empty security requirement for exactly two operations:

- `POST /api/v1/identity/authentication/login` — operation ID `login`;
- `GET /api/v1/security/oidc` — operation ID `contract`.

## External OIDC Operation

Exactly one generated operation uses `externalOidcBearerJwt`:

- `POST /api/v1/identity/authentication/oidc/complete` — operation ID `completeOidc`.

## Hidra Bearer Operations

The other **256** generated operations require `hidraBearerJwt` in the version-controlled snapshot.

Bearer authentication does not imply that every authenticated principal is authorized for every operation. Application/route permission decisions and domain authorization remain runtime controls in addition to OpenAPI's authentication declaration.

## Runtime Security Alignment

Current Spring Security configuration permits the verified public login/OIDC contract path plus documentation and OPTIONS infrastructure where configured, and otherwise requires authentication.

Authenticated actor/audit identity is derived from the Spring Security context. Caller-supplied `X-Actor-Id` is not a trusted authenticated-identity mechanism.

## Scope Limitation

This document does not invent a production identity-provider hostname, LDAP server, issuer, audience, key material, permission matrix or token lifetime where repository/runtime evidence does not establish it.
