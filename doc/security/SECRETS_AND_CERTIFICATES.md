# HidraAPI Secrets and Certificates

## Status

CURRENT for repository-backed secret handling.

**CURRENT OPERATING PROCEDURE BASELINE** for secret rotation, emergency invalidation, certificate renewal/revocation, emergency authority, and recovery verification. Platform-specific command syntax remains deferred until the production runtime/secrets/certificate tooling is selected.

Approval provenance: the project owner explicitly accepted the HPR-P0-012 security/operations recommendations on 2026-10-05.

## Verification Baseline

Repository head inspected before HPR-P0-014 operationalization: `6b2d2bbfe1bb28c6fef663f635ad98fd7fc6d5d1`

CI evidence: HidraAPI CI run #527 completed successfully on this exact head.

Decision provenance: HPR-P0-012 owner approval on 2026-10-05.

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

## 9. Operational Rotation and Certificate Procedures

These procedures are the approved repository-level operating baseline. Replace the bracketed platform action with the equivalent workflow in the selected production platform; do not invent product-specific commands here.

### 9.1 Routine JWT signing-secret rotation

Owner: Platform/Operations. Security owns policy and audit.

Preconditions:
1. confirm environment and current deployed SHA;
2. open an authorized change reference;
3. generate at least 256 bits of random signing material;
4. keep the raw value out of tickets, chat, Git, CI logs, and shell history;
5. identify all serving HidraAPI nodes/instances.

Execution:
1. place the new value in the approved protected secret source;
2. update the runtime binding for `HIDRA_JWT_HMAC_SECRET`;
3. restart/redeploy all serving nodes in a coordinated change;
4. verify every serving node is healthy;
5. authenticate and obtain a new Hidra JWT;
6. call a protected API successfully with the new token;
7. verify a token signed with the retired secret is rejected;
8. remove/disable the retired secret from active runtime configuration;
9. record the evidence defined in section 9.6.

Routine cadence: every 90 days.

Rollback rule: if the new secret cannot be made consistent across all serving nodes, remove inconsistent nodes from service and restore one known-good signing secret across the serving set before reopening access. Do not operate with mixed unknown signing material.

### 9.2 Emergency JWT compromise rotation

Authority: Security Incident Commander. Execution: Platform/Operations duty authority.

1. classify the event under `INCIDENT_RESPONSE.md`;
2. assume forged tokens may exist;
3. generate replacement signing material immediately;
4. replace `HIDRA_JWT_HMAC_SECRET` across the affected environment;
5. restart/redeploy all serving nodes in a coordinated operation;
6. require reauthentication;
7. verify newly issued tokens succeed;
8. verify a token signed with the retired secret fails;
9. preserve incident/change and verification evidence;
10. continue investigation for misuse during the exposure window.

There is no approved dual-key overlap in the current implementation.

### 9.3 PostgreSQL credential rotation

Owner: Database Operations with Platform/Operations coordination.

Routine cadence: every 90 days or stricter enterprise policy.

1. create/change the dedicated HidraAPI database credential using the approved database/secret workflow;
2. update the protected value mapped to `HIDRA_DATASOURCE_PASSWORD`;
3. restart/reload HidraAPI as required by the selected runtime;
4. verify application startup and `/actuator/health`;
5. verify approved database connectivity/read-write behavior;
6. verify Flyway/JPA startup remains healthy;
7. disable/retire the previous credential;
8. verify the retired credential no longer authenticates;
9. preserve rotation evidence.

Emergency rotation follows the same sequence immediately under incident authority.

### 9.4 LDAP bind credential rotation

Owner: Identity/Directory Operations with Platform/Operations coordination.

Routine cadence: every 90 days unless stricter directory policy applies.

1. rotate the dedicated bind-account password in the directory;
2. update the protected value mapped to `HIDRA_LDAP_BIND_PASSWORD`;
3. restart/reload HidraAPI if required by the runtime configuration mechanism;
4. verify directory connectivity/search;
5. execute an approved LDAP authentication test;
6. verify the retired bind credential no longer works;
7. preserve evidence.

If LDAP is disabled in the environment, record the check as not applicable.

### 9.5 Administrator bootstrap handling

1. keep bootstrap disabled during normal operation;
2. create an authorized provisioning change;
3. inject `HIDRA_SECURITY_BOOTSTRAP_PASSWORD` only through the approved secret mechanism;
4. enable bootstrap only for the provisioning event;
5. create and verify the intended administrator identity;
6. disable bootstrap;
7. remove the bootstrap password from active runtime inputs;
8. verify normal authentication continues;
9. preserve evidence without retaining the password.

### 9.6 Rotation evidence record

Every rotation record must include: change/incident identifier; environment; secret/credential type without its value; accountable roles; start/end timestamps; deployed SHA before/after if changed; health result; authentication result; dependency-connectivity result; retired-material rejection result where verifiable; rollback yes/no; residual issues/follow-up.

### 9.7 Certificate renewal procedure

Owner: Platform/Infrastructure.

At the approved 30-day action threshold, or earlier through automation:
1. identify certificate, subject/SAN scope, endpoint, issuing CA, and environment;
2. verify replacement identity scope;
3. generate/request replacement key/certificate through the approved certificate platform;
4. store private key material only in the approved protected facility;
5. deploy the replacement to the approved TLS termination point;
6. reload/restart the termination component according to the selected platform;
7. verify served chain, hostname/SAN coverage, validity, and trust;
8. verify HidraAPI health and protected API access through TLS;
9. remove retired certificate/key from active serving configuration;
10. preserve issuance/deployment/verification evidence.

Monitoring thresholds remain 45/30/14/7 days.

### 9.8 Certificate/private-key compromise procedure

Authority: Security Incident Commander with Platform/Infrastructure execution.

1. treat the private key as compromised;
2. identify every endpoint using it;
3. revoke/invalidate the affected certificate through the controlling CA where supported;
4. generate a new private key;
5. obtain a replacement certificate;
6. deploy it to all affected termination points;
7. remove compromised key material from active runtime/configuration;
8. verify clients receive the replacement certificate;
9. verify retired material is no longer active where technically possible;
10. preserve CA/revocation/deployment evidence;
11. continue incident investigation for misuse.

### 9.9 Recovery acceptance gate

A rotation/replacement is not complete until applicable checks pass:
- HidraAPI starts successfully;
- `/actuator/health` is healthy;
- protected API authentication/authorization succeeds;
- PostgreSQL connectivity succeeds when DB credentials changed;
- LDAP authentication/search succeeds when LDAP credentials changed;
- TLS endpoint serves the expected replacement certificate when certificates changed;
- retired secret/credential/token signature/certificate is rejected or inactive where verifiable;
- audit/security records remain available;
- evidence record is complete.

If any mandatory check fails, keep the change/incident open and invoke rollback or containment.

## 10. Platform-Specific Items Still Not Established

HPR-P0-014 establishes the operating sequence, but these still depend on P1 production architecture/tool selection:
- exact secrets-manager product and CLI/API syntax;
- deployment/restart command syntax;
- automatic rotation implementation;
- certificate-management product;
- load balancer/ingress product;
- trust-store location/update command;
- environment-specific hostnames/certificate subjects;
- monitoring/notification product.

These do not block the repository runbook baseline, but must be filled from actual production architecture before claiming automated production execution.
