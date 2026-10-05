# HidraAPI Disaster Recovery Runbook

## Status

**APPROVED PROCEDURE — HPR-P1-005**

Execution base: `354c34323e75e8a3684b0d811182cea9a06e591d`

Pre-task documentation validation: run #2 / run id `37342061352` completed **SUCCESS**.

This runbook operationalizes the approved disaster-recovery objectives in `DISASTER_RECOVERY_OBJECTIVES.md`.

Approved targets remain:

- **RTO ≤ 60 minutes**
- **RPO ≤ 5 minutes**
- continuous PostgreSQL WAL archiving;
- PITR capability;
- at least one successful recoverable base/full backup every 24 hours;
- 35-day operational retention;
- monthly recovery points retained according to enterprise records policy;
- at least one protected backup copy outside the live primary/standby failure domain.

This runbook is deliberately product-neutral. Replace product-neutral actions with approved platform commands only after infrastructure selection.

## 1. Activation Conditions

Use this runbook when normal HA/failover is insufficient or unsafe, including:

- loss of the database failure domain;
- destructive logical corruption replicated to standbys;
- malicious deletion or compromise;
- unrecoverable primary/standby cluster failure;
- need to recover to a known trusted historical point;
- backup/PITR recovery exercise authorized under HPR-P1-012.

Do not invoke DR merely for a healthy-node rolling restart or an ordinary controlled PostgreSQL switchover.

## 2. Declaration Authority

DR starts only after formal declaration by the authorized Incident Commander / Operations duty authority under the approved incident-governance model.

For suspected compromise, Security Incident Command must confirm whether the proposed recovery point and target environment are trusted enough to proceed.

Record immediately:

- incident/exercise identifier;
- declaration timestamp;
- declaration authority role;
- affected production environment;
- reason ordinary HA is insufficient;
- suspected compromise/corruption window;
- initial candidate recovery objective.

The **RTO clock starts at formal DR declaration**.

## 3. Recovery Roles

| Role | Responsibility |
|---|---|
| Incident Commander / Operations authority | Declares DR, coordinates priority, owns incident-level decisions |
| Database Operations | Backup selection, restore, WAL/PITR, PostgreSQL role/consistency verification |
| Platform/Operations | Recovery environment, network/runtime dependencies, stable endpoint, secrets/configuration, HidraAPI startup, traffic |
| Application Engineering | Flyway/JPA/application validation and defect diagnosis |
| Security | Containment and trust approval for compromise-related recovery |
| Accountable Operations/Business authority | Final restored-service acceptance |

No application code or ordinary application operator may independently promote a database or declare a recovered service fit for production.

## 4. Immediate Safety Gate

Before restoring anything:

1. stop or isolate traffic to any environment that may continue unsafe writes;
2. prevent an old primary or compromised database from later reappearing as an independent writer;
3. preserve incident evidence required by the incident process;
4. confirm recovery credentials and backup material can be accessed without placing secrets into tickets, logs, chat, or Git;
5. identify whether recovery must target a point before corruption/compromise.

**STOP** if single-writer authority cannot be guaranteed.

**STOP** if Security has not approved a recovery point in a compromise scenario.

## 5. Select the Recovery Point

Database Operations chooses a candidate recovery point using:

- the incident/corruption timeline;
- retained base/full backups;
- available WAL continuity;
- the approved **RPO ≤ 5 minutes** objective;
- Security guidance when a trusted-before-compromise point is required.

Record:

- source backup identifier and completion time;
- intended recovery timestamp or equivalent PostgreSQL recovery target;
- expected maximum data-loss interval;
- WAL range required;
- reason the point is trusted.

Do not default automatically to "latest possible" if the latest state may contain the destructive change.

## 6. Establish the Recovery Database Environment

Platform/Operations and Database Operations establish a trusted recovery target that is isolated from conflicting writable database nodes.

Requirements:

- production-equivalent PostgreSQL major/version compatibility as approved by operations;
- adequate storage/capacity for restore;
- protected credentials;
- no uncontrolled application traffic;
- no competing writable primary;
- access to the selected backup and required WAL.

Do not connect normal production application traffic yet.

## 7. Restore the Base/Full Backup

Database Operations restores the selected recoverable base/full backup using the approved backup platform procedure.

During restore, record:

- restore start time;
- backup identifier;
- target database environment;
- restore completion time;
- errors or warnings.

**STOP** on unrecoverable backup errors, missing required backup components, or integrity failures.

Do not substitute a standby copy for the required independent backup merely because it is faster.

## 8. Apply WAL / PITR

Configure recovery toward the approved target point and apply archived WAL.

Requirements:

- WAL continuity must be verified;
- replay must stop at the intended safe recovery target;
- the achieved recovery point must be recorded;
- any missing WAL that prevents reaching the target must be escalated immediately.

After PITR completes, record:

- achieved recovery timestamp / available recovery position evidence;
- WAL range applied;
- calculated data-loss interval relative to the incident boundary/latest required production state.

If the achieved interval exceeds **5 minutes**, mark the RPO as **NOT MET** even if service can technically start.

## 9. Establish Single-Writer PostgreSQL Authority

Before application startup:

1. verify exactly one PostgreSQL instance is authorized to accept writes;
2. verify any old primary is isolated/fenced or otherwise cannot become an independent writer;
3. verify recovered PostgreSQL is internally consistent enough for controlled application validation;
4. verify replication/standby re-establishment is not creating a dual-writer risk.

**STOP** if write authority is ambiguous.

DR recovery must never trade split-brain risk for speed.

## 10. Restore the Stable Database Endpoint

Platform/Operations restores or redirects the approved stable PostgreSQL service endpoint to the recovered writable primary.

HidraAPI must continue to use the stable endpoint through `HIDRA_DATASOURCE_URL`; node-specific database addresses must not become a permanent application configuration workaround.

Validate endpoint connectivity from the intended application runtime before starting production traffic.

## 11. Restore Runtime Secrets and Configuration

Restore approved externalized production configuration through the approved secret/configuration mechanism.

At minimum validate presence of required runtime inputs such as:

- production profile activation;
- datasource URL;
- datasource username/password;
- applicable JWT/OIDC/LDAP production inputs;
- bootstrap state as approved;
- CORS/runtime settings required for production.

Do not place raw secret values into runbook evidence.

If credentials may have been compromised, complete the approved emergency rotation/invalidations before normal traffic restoration.

## 12. Start HidraAPI in Recovery Mode / Controlled Production Startup

Start HidraAPI using the explicit production profile:

`SPRING_PROFILES_ACTIVE=production`

Keep normal user traffic disabled until all acceptance gates pass.

Verify:

- process starts;
- application does not fall back to an unintended profile;
- datasource connectivity succeeds;
- required external identity/directory dependencies are reachable or their approved degraded behavior is understood;
- health/readiness surfaces are available.

## 13. Flyway and JPA Validation

The recovered database must match the application schema expectations.

Verify:

- Flyway validation succeeds;
- no unauthorized clean/reset occurs;
- JPA `ddl-auto=validate` succeeds;
- migration history is consistent with the deployed application revision;
- no ad hoc schema mutation is performed merely to force startup.

**STOP** if Flyway or JPA validation fails.

Escalate schema mismatch to Database Operations + Application Engineering. Do not bypass schema validation to meet RTO.

## 14. Security Acceptance

Before restoring traffic, verify at minimum:

- protected authentication succeeds;
- authorization boundaries remain enforced;
- recovered credentials/secrets are trusted;
- production error disclosure remains restricted;
- audit/security attribution is functioning sufficiently for restored operation.

For compromise recovery, Security must explicitly approve the trust state.

**STOP** if authentication/authorization is materially broken or the recovered trust state is unresolved.

## 15. Functional Read Acceptance

Execute representative authorized read checks against business-critical data.

Validate that:

- expected domain records exist around the recovery point;
- obvious corruption is not present;
- recovered data timestamps align with the intended PITR point;
- read paths required for operational use function correctly.

Use pre-approved recovery test cases where available. Do not invent production data or unsafe diagnostic writes.

## 16. Functional Write Acceptance

Where safe and authorized, execute a controlled representative write and verify:

- the write reaches the single recovered primary;
- transaction commit succeeds;
- a subsequent read observes the expected result;
- audit attribution is correct;
- no second database authority receives an independent write.

Use a reversible or explicitly approved recovery-test transaction.

If a safe write test cannot be performed, document why and require accountable acceptance before traffic restoration.

## 17. Application HA / Realtime Constraints During Recovery

If multiple HidraAPI nodes are restored:

- admit only ready nodes to new traffic;
- do not rely on REST sticky sessions;
- preserve the HPR-P1-002 node-local cache correctness restriction;
- do not claim clustered realtime HA while the in-process STOMP simple broker remains unresolved;
- do not permit node-local background executors to become uncontrolled distributed job scheduling.

DR completion does not waive the application HA constraints.

## 18. Traffic Restoration Gate

Traffic may be restored only after all mandatory acceptance checks pass or an explicitly accountable authority accepts a documented degraded condition.

Mandatory technical checks:

- single writable PostgreSQL primary;
- stable database endpoint works;
- Flyway validation passes;
- JPA schema validation passes;
- HidraAPI production profile is active;
- health/readiness is acceptable;
- authentication/authorization works;
- representative reads pass;
- representative write passes or documented exception is accepted;
- Security trust approval exists where required.

Platform/Operations restores traffic gradually according to the selected infrastructure's approved mechanism.

Do not invent load-balancer or ingress commands in this runbook.

## 19. Stop the RTO Clock

The **RTO clock stops only when the accountable Operations/Business authority accepts the recovered production service** after the technical recovery gate passes.

Record:

- DR declaration time;
- technical-ready time;
- production-acceptance time;
- total elapsed RTO;
- whether **RTO ≤ 60 minutes** was met.

If 60 minutes is exceeded, service may still be restored, but the exercise/incident must record **RTO NOT MET** and produce remediation actions.

## 20. Measure RPO

Using the selected/achieved recovery point and the latest required recoverable production state, calculate the achieved data-loss interval.

Record:

- requested/required recovery point;
- achieved recovery point;
- calculated loss interval;
- whether **RPO ≤ 5 minutes** was met.

Do not claim RPO from configured WAL archival alone; measure it from the actual recovery evidence.

## 21. Post-Recovery Stabilization

After traffic restoration:

1. monitor application health and database errors;
2. confirm no old primary rejoins as writer;
3. validate backup/WAL protection resumes from the recovered authority;
4. re-establish the approved PostgreSQL HA posture when safe;
5. validate application-node readiness and traffic distribution;
6. rotate any credentials exposed or suspected during the incident;
7. preserve logs/evidence;
8. track degraded functions and recovery follow-up actions.

A temporarily recovered single-node database/application state must not be mislabeled as restored HA.

## 22. Abort / Escalation Conditions

Immediately stop or escalate recovery when any of the following occurs:

- trusted recovery point cannot be established;
- backup integrity is uncertain;
- required WAL is missing/corrupt;
- dual-writer/split-brain risk exists;
- old primary cannot be fenced/isolated;
- recovered database consistency is uncertain;
- stable database endpoint cannot be made authoritative;
- Flyway validation fails;
- JPA schema validation fails;
- required secrets cannot be restored securely;
- authentication/authorization validation fails;
- Security rejects the recovery point/environment;
- representative business validation indicates material corruption.

Do not bypass these gates solely to achieve the RTO.

## 23. Rollback / Re-Recovery

If the selected recovery point proves invalid after controlled validation but before general traffic restoration:

1. keep traffic disabled;
2. preserve evidence from the failed attempt;
3. select an earlier trusted recovery point;
4. rebuild/restore into a clean target as required;
5. repeat restore + PITR + acceptance.

Do not "repair forward" destructive historical corruption without an explicitly approved data-remediation plan.

## 24. Evidence Record

Retain the following without secrets:

- incident/exercise ID;
- environment;
- declaration authority role;
- DR declaration timestamp;
- recovery personnel/roles;
- reason DR was invoked;
- selected backup;
- selected PITR target;
- restore start/end;
- WAL replay evidence;
- achieved recovery point;
- single-writer verification;
- stable endpoint verification;
- Flyway result;
- JPA validation result;
- application health result;
- security acceptance result;
- representative read result;
- representative write result;
- traffic restoration time;
- production acceptance time;
- achieved RTO;
- achieved RPO;
- target-met/not-met status;
- deviations/degraded capabilities;
- follow-up actions.

## 25. Exercise Requirements

This runbook must be exercised under HPR-P1-012.

At minimum the survivability exercise must demonstrate:

- restoration from retained backup;
- PITR to an intentionally selected target;
- PostgreSQL single-writer recovery;
- HidraAPI startup against recovered data;
- Flyway/JPA validation;
- security acceptance;
- representative read/write behavior;
- measured RTO;
- measured RPO.

A desk review of this runbook is not sufficient to close production DR readiness.

## 26. Product-Specific Implementation Binding

The P1 stack is now selected and partially bound to executable repository artifacts:

- backup/WAL/PITR: pgBackRest under `ops/production/postgres/pgbackrest/`;
- backup execution/health/PITR exercise: `ops/production/postgres/scripts/`;
- PostgreSQL HA/failover: Patroni + etcd under `ops/production/postgres/`;
- stable PostgreSQL endpoint: HAProxy;
- runtime secrets: HashiCorp Vault selected, deployment binding remains HPR-P1-018;
- application hosting: Linux VMs + systemd;
- application traffic management: HAProxy;
- monitoring/alerting: Prometheus + Alertmanager + Grafana/Loki selected, executable rules remain HPR-P1-019.

Still unresolved:

- exact independent repository storage product/medium;
- enterprise monthly recovery-point policy identifier and exact hold duration;
- production-equivalent restore/PITR execution evidence.

The executable pgBackRest restore/PITR procedure does not replace HPR-P1-012 measured DR acceptance.

## 27. Production Readiness

The DR operating procedure is now documented.

It has **not yet been executed against the approved production-equivalent infrastructure**, and measured RTO/RPO evidence is not yet available.

Therefore disaster-recovery readiness and overall P1 production survivability remain **NOT ESTABLISHED** until HPR-P1-012 succeeds.
