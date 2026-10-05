# HidraAPI Database Operations Runbook

## Status

**APPROVED PRODUCT-NEUTRAL OPERATING PROCEDURE — HPR-P1-011**

Execution base: `b79648f5fa1e2a337e29213aad5b3a08447329db`

Pre-task documentation validation: run #7 / run id `37347126292` completed **SUCCESS**.

This runbook operationalizes the approved PostgreSQL high-availability and disaster-recovery models for routine and incident database operations.

It does not select a PostgreSQL HA manager, proxy, backup utility, object store, monitoring platform, operating-system cluster, cloud database service, or orchestration product.

## 1. Current Repository-Verified Database Contract

HidraAPI production currently uses:

- PostgreSQL as the authoritative persistence technology;
- externalized `HIDRA_DATASOURCE_URL`;
- externalized `HIDRA_DATASOURCE_USERNAME`;
- externalized `HIDRA_DATASOURCE_PASSWORD`;
- HikariCP;
- JPA `ddl-auto=validate`;
- Flyway;
- `spring.flyway.validate-on-migrate=true`;
- `spring.flyway.clean-disabled=true`.

Current production Hikari defaults are:

- maximum pool size: 30;
- minimum idle: 10;
- connection timeout: 30 seconds;
- validation timeout: 5 seconds;
- leak detection threshold: disabled.

These are implementation defaults, **not approved capacity thresholds**.

## 2. Ownership

| Area | Primary owner |
|---|---|
| PostgreSQL primary/standby roles | Database Operations |
| Replication/failover/switchover | Database Operations |
| Stable database endpoint | Database Operations + Platform/Operations |
| Flyway migration execution coordination | Database Operations + Platform/Operations |
| Application/Flyway/JPA compatibility diagnosis | Application Engineering |
| Backup/WAL/PITR execution | Database Operations |
| Runtime datasource configuration | Platform/Operations |
| Database security incident command | Security Incident Commander + Database Operations |
| Production service acceptance | Accountable Operations/Business authority |

Named operators and contacts remain in the controlled operational directory, not Git.

## 3. Pre-Change Database Gate

Before any production database-affecting maintenance or deployment:

1. identify the approved change/incident;
2. identify the exact HidraAPI revision involved;
3. verify exactly one writable PostgreSQL primary exists;
4. verify the stable application endpoint resolves to the current authority;
5. verify standby state and replication health;
6. verify backup/WAL protection is current enough to satisfy the approved operating objective;
7. verify production database credentials are available through the approved secret mechanism;
8. verify no unresolved migration/failover/recovery operation is already in progress;
9. identify rollback/recovery decision authority;
10. record the pre-change database state.

**STOP** if write authority is ambiguous.

## 4. Flyway Operating Policy

Flyway is the only approved schema migration authority for HidraAPI application schema evolution.

Rules:

- applied migration files are immutable;
- never edit an already-applied migration to force a checksum match;
- never use database `clean` in production;
- new schema changes use new forward/additive migrations;
- migrations run against the writable primary through the stable endpoint;
- standbys receive schema changes through PostgreSQL replication;
- standbys must not be migrated independently;
- multi-node application startup must not result in uncontrolled concurrent migration attempts.

The deployment/runtime platform must serialize migration authority or otherwise demonstrate safe single-migration execution.

## 5. Migration Execution Procedure

For a deployment containing database migration:

1. confirm exact application artifact/revision;
2. confirm current Flyway migration state/history;
3. verify database backup/WAL protection before change;
4. confirm the stable endpoint reaches the writable primary;
5. drain or coordinate application startup according to the deployment runbook;
6. allow one controlled migration-bearing startup/execution path;
7. verify Flyway completes successfully;
8. verify JPA schema validation succeeds;
9. verify representative read/write behavior;
10. allow remaining application nodes to start only after migration success is established;
11. record migration version(s), result and evidence.

Do not parallelize migration execution merely to reduce deployment time.

## 6. Migration Failure Handling

If Flyway fails:

1. stop additional application nodes from attempting uncontrolled migration;
2. preserve the Flyway error and migration-history evidence;
3. determine whether the migration failed before, during, or after a transactional boundary;
4. determine whether database state changed partially;
5. keep production traffic disabled or on the previous safe application state as applicable;
6. involve Application Engineering for migration logic/schema diagnosis;
7. use a new corrective migration when forward repair is safe and approved;
8. use DR/database recovery only when a clean recovery to an earlier state is actually required.

**Do not**:

- rewrite an applied migration;
- manually falsify Flyway history;
- bypass `validate-on-migrate`;
- disable JPA validation to force startup;
- run `clean`;
- execute undocumented ad hoc DDL as a routine shortcut.

Any exceptional Flyway history repair requires explicit Database Operations + Application Engineering review and retained evidence explaining why it is safe.

## 7. Connection Exhaustion / Pool Saturation

### 7.1 Detection

Investigate when the application exhibits:

- datasource acquisition failures/timeouts;
- sustained pending connection demand where exposed;
- all/near-all connections active for an abnormal duration;
- request failures correlated with database connection acquisition;
- database-side connection pressure.

No numeric paging threshold is invented here.

### 7.2 Immediate response

1. confirm whether PostgreSQL is reachable;
2. confirm current primary identity and stable endpoint correctness;
3. inspect application-side pool state/metrics where available;
4. inspect database-side active sessions/locks/long-running work using the approved database tooling;
5. identify whether the pressure is application demand, blocked transactions, slow queries, connection leakage, database resource saturation, or failover/recovery behavior;
6. preserve correlation/request/application revision evidence;
7. reduce unsafe load through approved traffic/change controls if required.

Do not simply raise the Hikari pool maximum without database-capacity evidence.

### 7.3 Recovery

Recovery may include, depending on evidence:

- resolving blocked/long-running transactions;
- correcting a database availability issue;
- removing a failing application node;
- rolling back a defective application revision;
- restoring the correct database endpoint;
- adjusting pool/database capacity only through approved performance/capacity change.

Connection-pool values remain capacity decisions, not emergency tuning defaults.

## 8. Primary Failure / Failover

When the writable primary is lost or unusable:

1. confirm the failure and identify eligible standby state;
2. prevent/fence the former primary from continuing as an independent writer;
3. Database Operations selects the eligible promotion target;
4. promote only under approved failover authority;
5. verify exactly one writable primary;
6. update/restore the stable database endpoint;
7. verify HidraAPI can establish new connections;
8. verify Flyway/JPA schema compatibility;
9. verify representative reads and controlled writes;
10. verify the former primary cannot return as an independent writer;
11. re-establish standby redundancy when safe;
12. retain failover evidence.

Do not assume in-flight transactions survive the failover.

Do not globally replay uncertain writes without explicit idempotency/business semantics.

## 9. Planned Switchover

Use controlled switchover for planned primary maintenance where the selected HA implementation supports it.

Procedure:

1. verify target standby health and replication state;
2. verify target eligibility;
3. confirm change authority;
4. quiesce/drain write activity if required;
5. ensure concurrent-writer protection;
6. promote the intended standby;
7. redirect/update the stable endpoint;
8. verify application reconnect;
9. verify representative reads/writes;
10. verify Flyway/JPA startup expectations;
11. return former primary only as a safe standby or keep isolated;
12. restore normal redundancy;
13. record elapsed time and deviations.

The exact promotion/fencing commands depend on the selected HA platform and are intentionally absent.

## 10. Standby Failure / Loss of Redundancy

Loss of the approved local HA standby is a degraded condition.

Actions:

1. confirm primary health;
2. confirm no hidden second writer exists;
3. record loss of redundancy;
4. notify Database Operations/Platform Operations according to the approved operating model;
5. restore/rebuild an eligible standby through the selected platform procedure;
6. verify replication catches up;
7. verify the intended synchronous/asynchronous mode;
8. verify monitoring reflects restored redundancy.

This runbook does not invent the maximum permissible degraded duration.

## 11. Backup Operations

Approved baseline:

- at least one successful recoverable base/full backup every 24 hours;
- continuous WAL archiving;
- PITR capability;
- 35-day operational retention;
- monthly recovery point retained per enterprise records policy;
- at least one protected copy independent of the live primary/standby failure domain.

Routine Database Operations must verify:

- latest successful recoverable backup;
- backup age against the 24-hour coverage objective;
- WAL archive continuity;
- retained recovery coverage;
- backup repository accessibility/integrity;
- evidence of the latest successful restore/PITR exercise.

A successful backup job that has never been proven restorable is insufficient recoverability evidence.

## 12. Restore / PITR

For an actual restore/PITR, follow `doc/operations/DISASTER_RECOVERY_RUNBOOK.md`.

Database Operations responsibilities include:

- select/verify the source backup;
- verify WAL availability;
- perform restore;
- apply PITR to the approved target;
- establish exactly one writable primary;
- provide achieved recovery-point evidence;
- verify database consistency and application compatibility;
- coordinate stable endpoint restoration.

RPO target remains **≤ 5 minutes**.

RTO target remains **≤ 60 minutes** from formal DR declaration to accepted production service.

## 13. WAL Archiving Failure

Treat loss of continuous WAL archival coverage as a recovery-protection incident.

Actions:

1. identify the last confirmed archived/recoverable WAL point;
2. determine whether PITR continuity is broken;
3. preserve the failure evidence;
4. restore archival function through the selected platform procedure;
5. determine whether a new base/full backup is needed to re-establish a clean recovery chain;
6. record the period of reduced/unknown recovery protection;
7. escalate if the approved RPO can no longer be supported.

Do not claim RPO compliance from configuration presence alone.

## 14. Backup Failure

If the required daily recoverable backup does not succeed:

1. verify whether another valid backup still covers the 24-hour objective;
2. determine whether WAL/PITR continuity remains intact;
3. retry/correct the backup through the approved platform;
4. verify the resulting backup is valid;
5. record the failure and remediation;
6. escalate when recoverable coverage falls outside the approved objective.

A replica does not substitute for the missed backup.

## 15. Credential Rotation

PostgreSQL service credential rotation follows the approved secret lifecycle.

Database Operations owns the credential change with Platform/Operations coordination.

Procedure requirements:

1. identify change/rotation authority;
2. create/update the replacement credential through the approved secret mechanism;
3. update PostgreSQL access as required;
4. update the runtime secret reference/injection;
5. restart/refresh application connectivity according to the selected platform;
6. verify HidraAPI database access;
7. verify the retired credential is no longer accepted where feasible;
8. retain non-secret rotation evidence.

Routine baseline remains 90 days unless stricter enterprise policy applies.

Emergency rotation occurs immediately after credible compromise.

## 16. Database Maintenance

For PostgreSQL maintenance affecting availability:

1. confirm HA posture and backup protection;
2. decide whether controlled switchover is required;
3. coordinate with Platform/Operations and change authority;
4. preserve single-writer guarantees;
5. perform maintenance on the non-authoritative node where possible;
6. verify replication before restoring that node to standby duty;
7. use controlled switchover for primary maintenance when safe;
8. verify application connectivity and read/write behavior;
9. restore approved redundancy;
10. record evidence.

Never perform simultaneous maintenance that removes all database recovery/failover capacity without an explicit approved outage and recovery plan.

## 17. Data Integrity / Corruption Incident

If corruption or destructive logical change is suspected:

1. stop or restrict unsafe writes as required;
2. preserve incident evidence;
3. involve Security if malicious activity is possible;
4. determine whether corruption replicated to standbys;
5. do not assume HA failover solves logical corruption;
6. identify a trusted recovery point;
7. invoke DR/PITR when recovery to an earlier state is required;
8. validate application/business data after recovery;
9. record achieved RPO/RTO and residual loss.

Do not "repair forward" unknown corruption without a reviewed remediation plan.

## 18. Database Security Incident

For unauthorized DB access, credential compromise, or suspicious database activity:

- Security Incident Commander owns incident command;
- Database Operations owns database containment/recovery actions;
- rotate compromised credentials according to approved emergency policy;
- preserve database/audit/log evidence without raw secrets;
- consider recovery-point trust before restoring from backup;
- use the DR runbook where a known-trusted historical point is required.

Availability goals do not override containment.

## 19. Observability Requirements

Database Operations must eventually have visibility into:

- current primary identity;
- standby availability;
- replication health;
- synchronous standby state where applicable;
- replication lag;
- failover/switchover events;
- connection pressure/exhaustion;
- recovery/replay state;
- backup coverage;
- WAL archive continuity;
- loss/restoration of redundancy.

HPR-P1-010 defines the product-neutral observability model.

Executable alerts remain blocked until monitoring platform and threshold/SLO decisions are approved.

## 20. Stop / Escalation Conditions

Stop the current database operation and escalate when:

- write authority is ambiguous;
- dual-primary/split-brain risk exists;
- stable endpoint points to an uncertain authority;
- backup source integrity is unknown;
- required WAL is missing/corrupt;
- migration leaves unknown partial state;
- Flyway validation cannot be reconciled safely;
- JPA schema validation fails;
- Security rejects the recovery point/environment;
- credentials cannot be rotated/restored securely;
- representative read/write validation fails materially.

Do not bypass these gates solely to meet an availability target.

## 21. Evidence to Retain

Without raw secrets, retain as applicable:

- change/incident/exercise identifier;
- environment;
- deployed application SHA;
- database role state before/after;
- standby/replication state;
- stable endpoint verification;
- migration versions/result;
- Flyway history/validation result;
- JPA validation result;
- backup identifier/date;
- WAL/PITR range;
- failover/switchover timestamps;
- application reconnect result;
- representative read/write result;
- credential-rotation reference;
- achieved RPO/RTO for recovery exercises/incidents;
- deviations/degraded state;
- corrective/follow-up actions.

## 22. Product-Specific Gaps

The following remain intentionally unresolved until infrastructure selection:

- PostgreSQL HA manager commands;
- fencing commands;
- stable endpoint implementation;
- backup utility commands;
- WAL archive implementation;
- backup repository/storage technology;
- monitoring/exporter/alerting product;
- database service-manager commands;
- platform-specific credential injection;
- cloud/database-service procedures.

The selected products must implement this runbook rather than silently redefine the operational controls.

## 23. HPR-P1-012 Readiness

This runbook is a prerequisite for measured survivability verification.

HPR-P1-012 may only close after repository evidence includes measured execution of:

- application node failover/removal behavior;
- PostgreSQL failover/switchover;
- stable endpoint recovery;
- restore from retained backup;
- PITR to an intentionally selected point;
- achieved RTO;
- achieved RPO;
- required application/database/security acceptance checks.

Desk review alone is insufficient.

## 24. Production Readiness

The database operating procedure is documented.

The selected production database tooling and measured failover/restore evidence are not yet established.

Therefore database operations readiness and overall P1 production survivability remain **NOT ESTABLISHED**.
