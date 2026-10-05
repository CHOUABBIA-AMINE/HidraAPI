# HidraAPI Security Incident Response

## Status

CURRENT as the canonical minimum application-response procedure.

**APPROVED INCIDENT-GOVERNANCE DECISIONS — OPERATING PROCEDURE INTEGRATION PENDING HPR-P0-014**.

Approval provenance: the project owner explicitly accepted the HPR-P0-013 incident-governance recommendations on 2026-10-05.

Personal contact details, external-provider contact coordinates, jurisdiction-specific notification deadlines, and SIEM/SOC product selection remain outside this repository unless separately approved.

## Verification Baseline

Repository head inspected before HPR-P0-013 decision capture: `ad2fee6b064e66dfa50aca2dd89c75a218bc5d36`

CI evidence: HidraAPI CI run #526 completed successfully on this exact head.

## 1. Purpose

Define the minimum repository-supported response sequence for application security incidents without inventing an organizational SOC process.

This document does not replace the P1 disaster-recovery runbook.

## 2. Current Evidence Sources

Repository-backed evidence available to responders includes:

- application logs with correlation/request IDs;
- authenticated actor resolution from Spring Security;
- Identity authentication/session/event persistence;
- Audit module persistence;
- Actuator health/metrics/Prometheus endpoints;
- Git commit history;
- Flyway migration history;
- CI workflow history;
- configuration values supplied by deployment environment.

Centralized log retention, SIEM, and alert routing are not established.

## 3. Minimum Response Sequence

### 3.1 Detect and preserve

1. Record the first known timestamp and affected environment.
2. Preserve correlation/request IDs, authenticated principal/actor identifiers, affected API path, and relevant authentication/audit records.
3. Preserve the exact deployed application commit SHA and configuration/profile identity.
4. Preserve relevant CI/deployment evidence.
5. Do not alter historical audit evidence to simplify investigation.

### 3.2 Classify the affected trust boundary

Use `doc/security/TRUST_BOUNDARIES.md` and identify whether the event involves:

- ordinary Hidra bearer authentication;
- external OIDC completion;
- LOCAL credential authentication;
- LDAP/AD;
- authorization/permission enforcement;
- Workbench data exposure;
- actor/audit attribution;
- database credentials/data;
- application logs;
- externalized secrets;
- network/TLS boundary.

### 3.3 Contain

Containment must be selected from verified controls and approved operating authority.

Possible technical containment actions, only when applicable and authorized, include:

- disable the affected user/credential/provider through existing Identity administration paths;
- remove/deny an approved Workbench exposure entry;
- disable administrator bootstrap if it was enabled;
- rotate an exposed externalized database/LDAP/JWT secret through the deployment secret mechanism;
- disable OpenAPI/Swagger UI through production configuration;
- restrict/remove compromised external OIDC trust configuration;
- deploy a verified corrective commit.

JWT HMAC rotation/invalidation follows the approved lifecycle decision in `doc/security/SECRETS_AND_CERTIFICATES.md`: coordinated replacement across all serving nodes, controlled restart/redeployment, reauthentication, retired-token rejection verification, and evidence preservation. Exact runtime commands remain pending HPR-P0-014.

### 3.4 Eradicate and correct

1. Identify the exact source/configuration defect.
2. Add regression coverage for the reproduced exploit/failure condition where feasible.
3. Preserve architecture/module boundaries while correcting it.
4. Use additive Flyway migration if schema change is required; never rewrite applied migrations.
5. Run the repository verification gates applicable to the correction.

### 3.5 Recover

1. Restore only from an approved deployment artifact/commit.
2. Verify authentication and authorization behavior.
3. Verify health/readiness.
4. Verify required database/Flyway startup.
5. Verify affected audit/security records remain accessible.
6. For data-loss/corruption events, follow the P1 DR process when established.

### 3.6 Close

Record:

- incident identifier assigned by the responsible organization;
- affected environment;
- first/last known timestamps;
- affected identities/resources;
- root cause;
- containment action;
- corrective commit(s);
- test/CI evidence;
- secret/certificate rotation evidence if applicable;
- remaining risk;
- follow-up roadmap item if the fix cannot be completed immediately.

## 4. Incident-Specific Guidance

### 4.1 Suspected Workbench sensitive-data exposure

- remove the affected Workbench exposure configuration;
- preserve request/audit evidence;
- determine which records/fields were reachable;
- inspect for credential/secret exposure;
- rotate exposed secrets where appropriate and operationally approved;
- verify `HidraOperationalWorkbenchExposurePolicy` and architecture/regression tests remain intact.

### 4.2 Suspected JWT signing-secret compromise

- treat token forgery as possible;
- preserve issuer/audience/token evidence without logging raw bearer tokens;
- invoke security/operations authority for secret rotation;
- plan for existing-token invalidation impact.

Routine/emergency key-rotation policy is approved in `doc/security/SECRETS_AND_CERTIFICATES.md`; platform-specific execution commands remain pending HPR-P0-014.

### 4.3 Suspected LOCAL credential compromise

- disable/lock the affected credential/user through the existing Identity lifecycle if operationally available;
- do not expose or copy `passwordHash` into support artifacts;
- require credential replacement according to the responsible organization's policy.

Password policy/reset operating procedure beyond implemented credential lifecycle is not established here.

### 4.4 Suspected external OIDC / LDAP compromise

- isolate the affected provider configuration where authorized;
- preserve provider/user mapping evidence;
- verify that external claims/groups were not incorrectly promoted to Hidra authorization truth;
- coordinate with the external provider owner.

External-provider escalation ownership is approved below. Personal/provider-specific contact details must remain in the organization's controlled operational contact system rather than Git.

### 4.5 Actor-attribution anomaly

- treat caller-provided actor headers as untrusted;
- verify the authenticated principal from Spring Security / Identity evidence;
- verify deployed code includes the HPR-P0-005 correction.

## 5. Approved Incident Governance Model

These decisions are approved for HidraAPI incident governance. They define roles and decision rights, not named individuals.

### 5.1 Incident authority

- **Security Incident Commander** is the authoritative role for security-incident command, containment authorization, severity confirmation, and security-risk disposition.
- **Platform/Application Technical Lead** owns technical reproduction, containment implementation, corrective changes, and verification for HidraAPI.
- **Platform/Operations duty authority** owns runtime isolation, deployment, secret/certificate replacement execution, and recovery actions within its operational scope.
- **Database Operations** owns database-incident technical response.
- **Identity/Directory Operations** owns LDAP/OIDC operational response and external identity-provider coordination.
- **Legal/Compliance** owns regulatory/legal notification decisions.
- **Business/Operations leadership** owns operational-impact acceptance and business communication for affected pipeline/operational domains.

Named contacts and phone/email details must remain in the organization's controlled on-call/contact system, not this repository.

### 5.2 Escalation chain

Approved role-based escalation path:

`Detection/SOC or reporting source -> Security Incident Commander -> Platform/Application Technical Lead -> Platform/Operations duty authority -> specialist owner(s) as required`

Specialist owners include:

- Identity/Directory Operations for LDAP/OIDC/provider incidents;
- Database Operations for PostgreSQL/data-integrity incidents;
- Network/Infrastructure Operations for TLS/network/perimeter incidents once that production architecture is established;
- Legal/Compliance when confidentiality, personal data, operational integrity, contractual, or regulatory impact may require notification;
- Business/Operations leadership when operational pipeline decision support or business continuity is affected.

### 5.3 Severity taxonomy

The approved model is four levels:

#### SEV-1 — Critical

Examples include:

- confirmed JWT-signing-secret/private-key/critical credential compromise;
- confirmed unauthorized privileged access;
- confirmed sensitive-data exfiltration;
- widespread authentication/authorization bypass;
- compromise materially affecting operational pipeline decision support or operator safety context.

Response expectation: **immediate escalation on detection** to the Security Incident Commander and required technical/operational owners.

#### SEV-2 — High

Examples include:

- serious exploitable security defect with limited confirmed impact;
- compromise of a restricted account;
- significant authorization failure;
- outage or degradation of a security control with credible exposure risk.

Response expectation: escalate to the Security Incident Commander and responsible technical owners **within 30 minutes of confirmation/classification**.

#### SEV-3 — Moderate

Examples include:

- contained vulnerability without confirmed exploitation;
- suspicious activity without confirmed compromise;
- material security misconfiguration with limited exposure.

Response expectation: handle through the normal security/engineering response workflow with documented ownership and tracking.

#### SEV-4 — Low

Examples include:

- low-impact hardening deficiencies;
- informational security-control gaps;
- minor configuration/documentation weaknesses without credible active exposure.

Response expectation: track through normal engineering/security backlog and roadmap governance.

The severity level may be raised or lowered by the Security Incident Commander as evidence changes.

### 5.4 Containment authority

For SEV-1/SEV-2 incidents, the Security Incident Commander may authorize emergency containment including, when technically applicable:

- disable or lock an affected identity/credential/provider;
- remove Workbench exposure configuration;
- rotate JWT/database/LDAP secrets according to the approved lifecycle;
- replace/revoke compromised certificate/private-key material;
- restrict/remove external OIDC trust configuration;
- restrict traffic through approved infrastructure controls;
- roll back or deploy a verified corrective application commit.

Emergency containment may proceed outside the normal change window when necessary to reduce credible security impact.

### 5.5 External IdP / LDAP / provider escalation

- **Identity/Directory Operations** owns escalation to external OIDC/LDAP/identity-provider operators.
- Provider-specific contacts, contract numbers, and support channels belong in the organization's controlled operational directory, not Git.
- HidraAPI incident evidence must preserve issuer/provider identity and relevant configuration references without exposing secrets.

### 5.6 Database escalation

- **Database Operations** owns incidents involving database credentials, unauthorized DB access, corruption, backup/restore security, or DB integrity.
- The Security Incident Commander retains overall incident command for security events.

### 5.7 Legal / regulatory notification ownership

- **Legal/Compliance** is the decision authority for legal, contractual, privacy, regulatory, or external notification requirements.
- Engineering and Security must escalate promptly when confidentiality, integrity, personal data, regulated infrastructure, or significant operational impact may be involved.
- This repository does **not** invent jurisdiction-specific notification deadlines or regulatory obligations.

### 5.8 Business / operational notification ownership

- **Business/Operations leadership** owns operational-impact communication and acceptance for affected business/pipeline domains.
- The Security Incident Commander and technical lead provide verified technical facts and residual-risk status.

### 5.9 Evidence retention and integrity

Approved ownership model:

- Security owns incident-evidence governance;
- Platform/Application, Identity, Database, and Infrastructure teams preserve evidence from systems they operate;
- Legal/Compliance determines whether litigation/regulatory hold overrides ordinary retention policy.

Evidence should include, as applicable:

- deployed commit SHA;
- environment/profile identity;
- correlation/request IDs;
- authenticated principal/actor identifiers;
- relevant audit/authentication/session records;
- affected resource identifiers;
- configuration version/reference without raw secrets;
- CI/deployment evidence;
- secret/certificate rotation evidence;
- provider/database evidence;
- incident timeline and decision log.

Evidence must be access-restricted and integrity-protected/immutable where the operating platform supports it.

The **retention duration remains governed by the organization's approved cyber/audit/legal retention policy**. No duration is invented in this repository.

### 5.10 Post-incident review

A documented post-incident review is required for **SEV-1 and SEV-2** incidents.

Minimum PIR content:

- incident timeline;
- detection source;
- scope and impact;
- root cause;
- containment and eradication actions;
- corrective commits/configuration changes;
- secret/certificate rotations where applicable;
- test/CI/recovery verification;
- residual risk;
- control failures or monitoring gaps;
- roadmap/follow-up actions.

### 5.11 Closure authority

Incident closure requires:

- **Security Incident Commander** confirmation that security containment and residual-risk disposition are complete; and
- **affected technical owner** confirmation that recovery/verification is complete.

Where legal/regulatory notification obligations apply, **Legal/Compliance** must confirm its required actions before final closure.

## 6. Decisions Remaining Outside Repository Scope

The governance model above is approved.

The following remain intentionally outside this repository until separate organizational/platform decisions exist:

- named/personal emergency contacts;
- SOC/on-call tool/product selection;
- SIEM product selection;
- cyber-insurance process;
- law-enforcement engagement procedure;
- jurisdiction-specific notification deadlines;
- concrete production communication channels.

HPR-P0-014 will operationalize the approved governance model together with the approved secret/certificate lifecycle into concrete repository procedures where the actual runtime/platform architecture provides enough evidence.
