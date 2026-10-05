# HidraAPI Secrets and Certificates

## Status

CURRENT for repository-backed secret handling; certificate/TLS operations are **NOT ESTABLISHED**.

## Verification Baseline

Repository head: `359ae6d77bb9bb8f499941634760f4c375abb9ac`

## 1. Repository Secret Policy

Production configuration states that production must be configured through environment variables, externalized configuration, or a secrets manager.

This repository does not select or prove a specific secrets-manager product.

No secret value should be documented in this file.

## 2. Verified Secret Inputs

| Secret / sensitive input | Configuration source | Repository default | Current enforcement |
|---|---|---|---|
| PostgreSQL password | `HIDRA_DATASOURCE_PASSWORD` | none in production profile | required external production property |
| Hidra JWT HS256 secret | `HIDRA_JWT_HMAC_SECRET` | blank | encoder/decoder reject missing value; minimum 32 UTF-8 bytes |
| LOCAL administrator bootstrap password | `HIDRA_SECURITY_BOOTSTRAP_PASSWORD` | blank | bootstrap disabled by default; password external |
| LDAP bind password | `HIDRA_LDAP_BIND_PASSWORD` | blank | externalized |
| LDAP bind DN | `HIDRA_LDAP_BIND_DN` | blank | externalized; sensitive operational identity |
| OIDC client ID | `HIDRA_OIDC_CLIENT_ID` | blank | externalized identifier; browser PKCE contract states no client secret belongs in repository |
| External OIDC issuer/JWK URL | `HIDRA_JWT_ISSUER_URI`, `HIDRA_JWT_JWK_SET_URI` | blank | externalized trust configuration |

## 3. JWT Signing Material

**CURRENT**

Hidra access-token issuance and ordinary API validation share externalized HS256 signing material.

Implementation constraints:

- missing secret fails;
- shorter-than-32-byte UTF-8 value fails;
- algorithm is HS256.

## 4. Secret Logging Controls

**CURRENT**

`SensitiveValueMasker` masks structured logging values when the key vocabulary indicates:

- password;
- secret;
- token;
- authorization;
- credential;
- API key.

Production Hibernate bind logging is WARN, reducing accidental parameter disclosure.

This does not prove that every arbitrary application log statement is safe; code must still avoid directly logging secret material.

## 5. Rotation Status

The following are **NOT ESTABLISHED**:

- routine JWT HMAC rotation cadence;
- dual-key/overlap strategy for JWT rotation;
- emergency JWT invalidation procedure;
- datasource password rotation cadence;
- LDAP bind password rotation cadence;
- bootstrap-password handling after provisioning;
- secrets-manager product/ownership;
- automatic rotation tooling.

These require an approved operations/security procedure before production readiness is claimed.

## 6. Certificate / TLS Status

**NOT ESTABLISHED**

Repository evidence does not establish:

- where TLS terminates;
- who owns server certificates;
- server certificate/private-key storage;
- renewal cadence;
- expiry monitoring;
- mTLS;
- LDAP TLS certificate trust policy;
- PostgreSQL TLS certificate trust policy;
- external OIDC TLS/JWK transport ownership.

Do not infer certificate lifecycle from HTTPS-capable libraries or issuer/JWK URLs.

## 7. Required Future Operating Decisions

Before certificate/secret operations can be marked CURRENT, responsible owners must approve:

1. secret store/product;
2. access-control model for secret retrieval;
3. rotation cadence and emergency rotation authority;
4. JWT rotation/invalidation strategy;
5. database/LDAP credential rotation sequence;
6. TLS termination architecture;
7. certificate authority/trust model;
8. certificate renewal/expiry monitoring;
9. evidence retention for rotations.

Until then, these items remain `TBD — SECURITY / OPERATIONS DECISION REQUIRED`.
