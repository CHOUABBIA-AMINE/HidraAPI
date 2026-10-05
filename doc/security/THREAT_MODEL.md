# HidraAPI Threat Model

## Status

CURRENT — application threat register derived from verified implementation and forensic P0 findings.

## Verification Baseline

Repository head inspected for HPR-P0-011: `205a9a3010705bffd0a45c464a4e69f9b2a6b3a9`

CI evidence: HidraAPI CI run #524 completed successfully on this exact head.

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
| THR-020 | Authenticated caller registers a misleading or unauthorized telemetry source | API/operator -> telemetry source registration | Protected API, route authorization, request validation, source-code uniqueness, active SOURCE_TYPE/PROTOCOL catalog checks, DRAFT lifecycle creation | Operation-specific approval workflow and endpoint-level actor evidence are not established by the inspected telemetry controller; treat as residual governance risk, not a demonstrated authorization bypass |
| THR-021 | Authenticated caller registers a misleading or unauthorized telemetry point | API/operator -> telemetry point registration | Protected API, route authorization, request validation, point-code uniqueness per device, PLANNED lifecycle creation | Explicit registration approval workflow and endpoint-specific operator attribution are not established by the inspected controller; downstream device/asset trust remains outside this evidence |
| THR-022 | Caller abuses telemetry query access to enumerate operational reading/history data | API client -> telemetry query contract | Protected API, route authorization, controller-to-use-case boundary, bounded query parameters/page/limit inputs in current API | Fine-grained data-scope policy and OT data-classification rules are not established by current repository evidence |
| THR-023 | Operator attempts to acknowledge or close an alarm under another actor identity | API/operator -> alarm lifecycle | Actor identifiers are not accepted as authoritative request-body input; SpringAlarmController resolves actor from CurrentActorResolver; actor-attribution regression exists | Permission assignment and organizational approval policy are runtime/operational concerns; misuse by a legitimately authorized principal remains an insider-risk scenario |
| THR-024 | Operator shelves or unshelves an alarm without trustworthy actor attribution | API/operator -> alarm shelving lifecycle | AlarmQueryController resolves actor through CurrentActorResolver; request validation applies; correlation ID may be propagated | Business authorization/approval policy for shelving duration/reason is not fully established in this threat model; malicious action by an authorized account remains residual risk |
| THR-025 | False or inappropriate alarm creation influences operator decisions | API/operator or upstream application -> alarm raise path | Protected API, route authorization, validated RaiseAlarmRequest, application use-case/domain processing | Provenance/approval semantics for every alarm source category are not fully established here; this document does not claim a telemetry/SCADA ingestion path |

## 2. Telemetry and Operator Abuse Scenarios

The following scenarios satisfy the independent-audit requirement to record asset, actor/source, attack path, trust boundary, impact, current control, and residual risk. They describe abuse cases that must be considered; they are **not** findings that the abuse is currently exploitable.

| ID | Asset | Actor / source | Attack path | Trust boundary crossed | Potential impact | Verified current controls | Residual risk / status |
|---|---|---|---|---|---|---|---|
| THR-020 | Telemetry source registry and source metadata, including protocol/catalog references and endpoint metadata | Authenticated API principal with access to telemetry source registration | Submit a syntactically valid but operationally misleading source through `POST /api/v1/telemetry/sources` | API client -> Spring Security/route authorization -> `SpringTelemetryController` -> `CreateTelemetrySourceUseCase` -> application service | Incorrect source inventory, wrong protocol/source classification, downstream operator confusion, or later association of data with an inappropriate source | Protected API; route authorization; `@Valid` request validation; source code uniqueness; active `SOURCE_TYPE` and `PROTOCOL` catalog checks; source is created in DRAFT state | No repository-approved operational registration/approval workflow is established here. Endpoint-specific actor capture is not evidenced in `SpringTelemetryController`. This is a residual governance/insider-risk scenario, not a proven authorization defect. |
| THR-021 | Telemetry point registry and point-to-device metadata | Authenticated API principal with access to telemetry point registration | Submit a valid but misleading point through `POST /api/v1/telemetry/points` | API client -> security/route authorization -> `SpringTelemetryController` -> `RegisterTelemetryPointUseCase` -> application service | Incorrect point inventory, misleading signal/unit/range metadata, bad downstream monitoring assumptions | Protected API; route authorization; `@Valid`; point code uniqueness per device; application constructs the point in PLANNED state | Approval/provisioning workflow and endpoint-specific operator attribution are not established by the inspected controller. Device/field-system trust is outside repository evidence. |
| THR-022 | Telemetry reading history, latest values, trends, reading-state references, quality-code references | Authenticated API principal with telemetry query access | Repeated or broad reads through `/api/v1/telemetry/points/{pointId}/readings`, `/latest`, `/trend`, or reference endpoints | API client -> `TelemetryQueryController` -> `TelemetryQueryUseCase` -> query adapter | Operational-data disclosure, inference of operating state, excessive query load | Protected API; route authorization; controller-to-use-case boundary; pagination/size/limit parameters on current query API | Fine-grained data classification, per-asset visibility rules, rate limiting, and OT-specific confidentiality policy are NOT ESTABLISHED by this repository. |
| THR-023 | Alarm acknowledgement/closure history and operator accountability | Authenticated operator or compromised authorized account | Attempt to acknowledge or close an alarm while spoofing another actor | API client -> `SpringAlarmController` -> alarm use case | False accountability, premature closure, loss of operational traceability | `AcknowledgeAlarmRequest` and `CloseAlarmRequest` do not supply authoritative actor identity; controller derives actor through `CurrentActorResolver`; authenticated actor comes from Spring Security; actor-attribution tests exist | A legitimately authorized but malicious/compromised principal can still perform actions allowed by its permissions. Organizational approval/escalation policy is not established here. |
| THR-024 | Alarm shelving state and visibility of active alarms | Authenticated operator or compromised authorized account | Shelve an alarm for an inappropriate reason/duration or unshelve it unexpectedly | API client -> `AlarmQueryController` -> `ManageAlarmShelvingUseCase` | Reduced alarm visibility, delayed response, operator confusion, degraded situational awareness | Actor is derived through `CurrentActorResolver`; request body validates non-blank shelving reason and future shelving expiry when supplied; correlation ID can be propagated | Permission assignment, maximum shelving policy, independent approval, and SOC/operator review procedure are not established by this repository. |
| THR-025 | Alarm lifecycle and operator decision support | Authenticated API principal or upstream application allowed to raise alarms | Create a false/inappropriate alarm through `POST /api/v1/alarm/alarms` or equivalent alias | API client/upstream application -> `SpringAlarmController` -> `RaiseAlarmUseCase` | Alarm flooding, distraction, incorrect response prioritization, erosion of trust in alarm state | Protected API; route authorization; validated request; application/domain use case | Complete provenance/authorization semantics for every alarm source type are not established in this document. No SCADA or telemetry-ingestion transport is inferred. |

### Threat-model boundary note

Current repository evidence establishes telemetry **registration** and telemetry **query** HTTP boundaries. It does not establish:

- an HTTP telemetry-reading ingestion endpoint;
- direct SCADA/PLC connectivity;
- OPC, MQTT, Sparkplug, or historian transport;
- OT/IT network segmentation;
- device certificates or field-device identity;
- industrial-protocol trust/authentication.

Those areas remain NOT ESTABLISHED/DEFERRED and must not be represented as current controls or current vulnerabilities.

## 3. Explicit Security Assumptions

The current application security model assumes:

1. production security remains enabled and JWT authentication mode is not intentionally disabled;
2. externalized secrets are supplied by an operational mechanism outside the repository;
3. the environment protects the runtime process and its secret inputs;
4. PostgreSQL integrity and availability are provided by infrastructure not yet documented by P1;
5. external OIDC/LDAP trust configuration is supplied correctly by deployment owners.

These are implementation assumptions, not proof that production infrastructure satisfies them.

## 4. Deferred / Out-of-Scope for Current P0

The following are not upgraded to current requirements by this document:

- OT/IT segmentation;
- industrial firewall/DMZ architecture;
- MQTT/Sparkplug security;
- PostGIS/TimescaleDB security specialization;
- autonomous control or AI-agent security.

They remain outside current implemented scope unless later roadmap decisions approve them.

## 5. P0 Exit Dependency

Security threat remediation under the independent audit is not fully closed until `HPR-P0-015` re-runs the complete 13-check exact-head verification gate after HPR-P0-008..014.
