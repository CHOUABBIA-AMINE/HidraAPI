# HidraAPI Secrets and Certificates

## Status

CURRENT for repository-backed secret handling.

**APPROVED OPERATING DECISIONS — IMPLEMENTATION/OPERATING PROCEDURE PENDING HPR-P0-014** for secret rotation, TLS termination ownership, certificate lifecycle, emergency authority, and recovery verification.

Approval provenance: the project owner explicitly accepted the HPR-P0-012 security/operations recommendations on 2026-10-05.

## Verification Baseline

Repository head inspected before HPR-P0-012 decision capture: `64dacaf85720df75d4b4079ccb8630215f6b3410`

CI evidence: HidraAPI CI run #525 completed successfully on this exact head.

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

## 5. Approved Secret Lifecycle Decisions

These are **approved operating decisions**. They do not prove that automation or the production platform already implements them.

### 5.1 Ownership model

- **Security** owns secret-handling policy, risk acceptance, emergency security authority, and approval of minimum cryptographic standards.
- **Platform/Operations** owns production secret generation/deployment, runtime retrieval, routine rotation, emergency replacement, and verification.
- **Identity/Directory Operations** owns LDAP/AD service-account lifecycle.
- **Database Operations** owns PostgreSQL service-account lifecycle.
- **Application Engineering** must not receive routine production-secret access merely because it maintains HidraAPI.
- Production secrets must use the organization's approved centralized secret-management capability once the production platform is selected.
- No specific product such as Vault, cloud secret manager, Kubernetes Secrets, or HSM product is selected by this decision.

### 5.2 Access-control model

- Runtime access must follow least privilege and environment separation.
- Production service credentials must be dedicated to HidraAPI and must not be shared with human administrators or unrelated applications.
- Production secrets, private keys, and personal emergency contacts must never be committed to Git, baked into container/application images, or copied into repository documentation.

### 5.3 Hidra JWT HS256 signing secret

Approved baseline:

- routine rotation cadence: **every 90 days**;
- emergency rotation: **immediately after suspected or confirmed disclosure**;
- generation: use a cryptographically secure generator/secrets platform with **at least 256 bits of random entropy**;
- the value must not be a human-selected password;
- current algorithm remains HS256 for P0; migration to asymmetric signing is not part of HPR-P0-012.

Current code validates one shared HS256 secret, so the approved P0 emergency invalidation sequence is:

1. generate a new secret;
2. update the protected runtime secret value for every HidraAPI node/environment in scope;
3. perform a coordinated restart/redeployment so all serving nodes use the same new value;
4. reject old tokens naturally because their signatures no longer validate;
5. require affected users/clients to authenticate again;
6. verify authentication, protected API access, and rejection of a token signed with the retired secret;
7. preserve rotation and verification evidence.

**No dual-key overlap is claimed or approved as currently implemented.** A future `kid`/JWKS/asymmetric-signing design may be evaluated separately if seamless rotation becomes an approved requirement.

### 5.4 PostgreSQL service credential

Approved baseline:

- owner: Database Operations, executed with Platform/Operations coordination;
- dedicated per-environment service identity;
- routine password rotation: **every 90 days**;
- emergency rotation: immediate after suspected disclosure, unauthorized exposure, or compromise of the controlling secret store/account;
- sequence: provision/update database credential -> update runtime secret -> restart/reload application as required by the selected platform -> verify database connectivity, application health, and invalidity of the retired credential.

### 5.5 LDAP bind credential

Approved baseline:

- owner: Identity/Directory Operations;
- dedicated non-human service account;
- minimum directory privileges required for HidraAPI authentication/search;
- routine bind-password rotation: **every 90 days**, unless a stricter enterprise directory policy applies;
- emergency rotation: immediate after suspected disclosure;
- verification: LDAP authentication/search succeeds with the new credential and the retired credential no longer works.

### 5.6 Administrator bootstrap credential

The administrator bootstrap password is approved as **one-time provisioning material**:

1. bootstrap remains disabled by default;
2. enable only for an authorized provisioning event;
3. provide the password through the approved external secret mechanism;
4. verify successful administrator provisioning;
5. remove the bootstrap password from runtime configuration/secret injection;
6. disable bootstrap again.

The bootstrap password is not an ongoing production operator credential.

### 5.7 Automatic rotation tooling

The policy and cadence are approved, but **automatic rotation tooling remains NOT ESTABLISHED** until the production secret-management/runtime platform is selected and implemented.

## 6. Approved TLS and Certificate Lifecycle Decisions

These are approved target operating decisions. They do not claim that the production network/deployment topology already exists.

### 6.1 TLS termination ownership

- **Platform/Infrastructure** owns TLS termination and server-certificate deployment.
- Preferred target: terminate TLS at an enterprise-controlled ingress, reverse proxy, or load-balancing tier selected during production architecture.
- Use TLS onward to HidraAPI where the approved network architecture requires end-to-end encryption.
- No ingress/load-balancer product is selected by this document.

### 6.2 Certificate authority and trust model

- Internal service certificates should use the organization's approved **enterprise/private CA**.
- Publicly exposed endpoints, if later approved, should use an approved public CA where public trust is required.
- Self-signed production certificates are not the normal operating model.
- LDAP, PostgreSQL, external OIDC/JWK, and internal-service trust stores must follow the enterprise trust policy when their concrete TLS architecture is established.

### 6.3 Private-key storage

- Private keys must be generated/stored through the approved certificate/secrets platform or hardware-backed facility when available.
- Production private keys must not be stored in Git, Maven resources, application/container images, environment templates, or developer workstations.

### 6.4 Renewal and expiry monitoring

Preferred operating model: automated certificate renewal.

Approved monitoring thresholds:

- **45 days** before expiry: informational;
- **30 days**: warning and renewal action threshold;
- **14 days**: critical escalation;
- **7 days**: critical escalation requiring immediate operational action.

The concrete monitoring product and notification channel remain implementation decisions for Platform/Operations.

### 6.5 Revocation / key compromise

On suspected private-key compromise:

1. Security/Platform authority declares emergency certificate replacement;
2. revoke or otherwise invalidate the affected certificate through the controlling CA where supported;
3. generate a new private key and certificate;
4. deploy the replacement to affected endpoints;
5. remove the compromised key material from active runtime/configuration;
6. verify clients no longer accept/use the retired certificate/key where technically applicable;
7. preserve CA, deployment, and verification evidence.

### 6.6 mTLS

mTLS is **not approved as a current implemented control** merely by this lifecycle decision. Whether specific service-to-service paths require mTLS remains a production architecture/security decision tied to actual network trust zones.

## 7. Approved Emergency Authority

For emergency secret/certificate compromise, the approved authority model is role-based:

- **Security Incident Commander**: authorizes emergency security containment/rotation;
- **Platform/Operations duty authority**: executes runtime secret/certificate replacement and recovery verification;
- **Database Operations** or **Identity/Directory Operations** must participate when their owned credential is affected.

Emergency security rotation may proceed outside the ordinary change window when necessary to contain a credible compromise. Organizational incident-governance details are captured separately by HPR-P0-013.

## 8. Mandatory Recovery Verification

Every production secret/certificate rotation must record and verify, as applicable:

- exact environment and affected service;
- timestamp;
- accountable operational role;
- change/incident reference;
- application startup and `/actuator/health`;
- protected API authentication/authorization;
- database connectivity for datasource rotations;
- LDAP/OIDC authentication for identity-provider rotations;
- rejection/failure of the retired secret, credential, token signature, or certificate where technically verifiable;
- completion status and residual issues.

Evidence must not contain raw secrets, private keys, passwords, or bearer tokens.

## 9. Implementation Status

HPR-P0-012 approves the lifecycle decisions above.

The following remain **pending HPR-P0-014 operationalization**:

- exact operator runbook commands for the selected runtime/secret platform;
- automation implementation;
- platform-specific rollback/reload mechanics;
- concrete certificate-management product integration;
- environment-specific trust-store configuration.

Those details must be based on the actual approved production architecture rather than invented here.
