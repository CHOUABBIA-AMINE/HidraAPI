# HidraAPI Threat Model

## Status

CURRENT — application threat register derived from verified implementation and forensic P0 findings.

## Verification Baseline

Repository head: `359ae6d77bb9bb8f499941634760f4c375abb9ac`

This is not a claim of completed enterprise threat modeling for infrastructure or OT networks. Network topology and production deployment controls remain **NOT ESTABLISHED**.

## 1. Threat Register

| ID | Threat | Affected boundary | Verified current control | Residual status |
|---|---|---|---|---|
| THR-001 | Generic Workbench leaks credential or sensitive persistence fields | API -> Workbench -> persistence | Fail-closed resource/field policy; credential/secret resource prohibition; password/secret field prohibition; regressions; architecture guardrail | P0 source defect remediated; full P0 verification still pending HPR-P0-007 |
| THR-002 | Caller spoofs actor identity through HTTP metadata | Request metadata -> audit/log actor | `X-Actor-Id` removed from supported header contract; request filter ignores spoofed actor; actor resolved from Spring Security; regression test | P0 source defect remediated; full P0 verification pending |
| THR-003 | Unauthenticated caller invokes protected API | Client -> API | Stateless Spring Security JWT authentication; protected endpoints authenticated; public paths explicitly enumerated | CURRENT control |
| THR-004 | External OIDC token is accepted by ordinary Hidra protected endpoints | OIDC -> API | Dedicated OIDC completion filter chain and separate external decoder; ordinary API uses Hidra JWT decoder | CURRENT control |
| THR-005 | Missing OIDC validator configuration accidentally permits external tokens | OIDC -> completion bridge | External decoder rejects attempted token validation when issuer/JWK-set are absent | CURRENT fail-closed control |
| THR-006 | Weak/missing Hidra HMAC signing material | Token issuer/validator | Missing secret rejected; minimum 32 UTF-8 bytes; HS256 encoder/decoder use externalized secret | CURRENT control; rotation procedure NOT ESTABLISHED |
| THR-007 | Raw LOCAL password is stored or repository-defaulted | LOCAL auth -> credential store | BCrypt PasswordEncoder; persistent hash field; bootstrap password external with no repository default | CURRENT control |
| THR-008 | LDAP bind credentials are committed into repository defaults | HidraAPI -> LDAP | Bind password externalized through environment/configuration | CURRENT control; rotation procedure NOT ESTABLISHED |
| THR-009 | Database credentials are committed into production config | HidraAPI -> PostgreSQL | Production datasource URL/username/password are required external inputs | CURRENT control; rotation procedure NOT ESTABLISHED |
| THR-010 | Sensitive technical values are emitted through structured logging context | Application -> logs | `SensitiveValueMasker` masks password/secret/token/authorization/credential/API-key keyed values | CURRENT control; centralized log access policy NOT ESTABLISHED |
| THR-011 | Browser cross-origin access is unintentionally broad | Browser -> API | CORS explicit; allowed origins default empty; credentials false | CURRENT control |
| THR-012 | Error responses expose internal exception/stack details in production | API -> caller | production error message/binding/stacktrace/exception disclosure disabled | CURRENT control |
| THR-013 | OpenAPI/Swagger expands production attack surface unintentionally | Caller -> API metadata | production OpenAPI and Swagger UI disabled by default | CURRENT configuration default |
| THR-014 | Authorization claim injection from external provider becomes Hidra authorization truth | External auth -> Hidra JWT | access-token issuer builds claims from normalized Hidra principal and Hidra-owned roles/permissions | CURRENT control |
| THR-015 | Compromise of shared HS256 secret permits Hidra token forgery | Secret store -> token trust | externalization and minimum length only | **OPEN RISK** — secret-manager product, rotation procedure, overlap strategy, and emergency invalidation procedure NOT ESTABLISHED |
| THR-016 | TLS/private-key/certificate compromise or missing transport protection | Network/TLS boundary | No application evidence sufficient to establish production TLS architecture | **NOT ESTABLISHED** — requires approved runtime architecture |
| THR-017 | Identity provider / LDAP outage causes authentication availability loss | External identity dependencies | LDAP timeouts; OIDC configuration validation | Availability/failover operating procedure NOT ESTABLISHED |
| THR-018 | Security event is not detected/escalated operationally | Logs/metrics -> operators | Actuator/Prometheus and authentication/audit persistence exist | Alert rules, SIEM/SOC integration, severity model, and on-call ownership NOT ESTABLISHED |
| THR-019 | Database loss/corruption prevents security/audit recovery | PostgreSQL | Flyway validation and production clean-disabled | Backup/restore, HA, RTO/RPO handled by P1 and NOT ESTABLISHED now |

## 2. Explicit Security Assumptions

The current application security model assumes:

1. production security remains enabled and JWT authentication mode is not intentionally disabled;
2. externalized secrets are supplied by an operational mechanism outside the repository;
3. the environment protects the runtime process and its secret inputs;
4. PostgreSQL integrity and availability are provided by infrastructure not yet documented by P1;
5. external OIDC/LDAP trust configuration is supplied correctly by deployment owners.

These are implementation assumptions, not proof that production infrastructure satisfies them.

## 3. Deferred / Out-of-Scope for Current P0

The following are not upgraded to current requirements by this document:

- OT/IT segmentation;
- industrial firewall/DMZ architecture;
- MQTT/Sparkplug security;
- PostGIS/TimescaleDB security specialization;
- autonomous control or AI-agent security.

They remain outside current implemented scope unless later roadmap decisions approve them.

## 4. P0 Exit Dependency

Security threat remediation is not declared fully verified until `HPR-P0-007` completes full exact-head Maven, architecture/security, database/Flyway startup, and deterministic OpenAPI verification.
