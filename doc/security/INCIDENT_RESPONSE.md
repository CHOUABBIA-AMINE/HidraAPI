# HidraAPI Security Incident Response

## Status

CURRENT as the canonical minimum application-response procedure.

Operational contact roster, incident severity taxonomy, SIEM/SOC tooling, and production escalation chain are **TBD — SECURITY / OPERATIONS DECISION REQUIRED**.

## Verification Baseline

Repository head: `359ae6d77bb9bb8f499941634760f4c375abb9ac`

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

Because JWT HMAC rotation/invalidation sequencing is not yet operationally defined, responders must not improvise a rotation sequence that could create an uncontrolled outage. Escalate to the designated security/operations owner.

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

Routine/emergency key-rotation procedure is not yet established.

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

External provider incident contacts are TBD.

### 4.5 Actor-attribution anomaly

- treat caller-provided actor headers as untrusted;
- verify the authenticated principal from Spring Security / Identity evidence;
- verify deployed code includes the HPR-P0-005 correction.

## 5. Organizational Decisions Still Required

The repository does not define:

- security incident commander;
- SOC/on-call contact;
- severity levels;
- notification timelines;
- legal/regulatory notification obligations;
- evidence-retention period;
- external provider escalation contacts;
- cyber-insurance/law-enforcement process.

These remain `TBD — SECURITY / OPERATIONS / BUSINESS DECISION REQUIRED`.
