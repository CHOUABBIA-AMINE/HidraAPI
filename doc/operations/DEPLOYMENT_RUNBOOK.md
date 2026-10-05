# HidraAPI Production Deployment Runbook

## Status

**APPROVED PRODUCT-NEUTRAL PROCEDURE — HPR-P1-007**

Execution base: `59c8b913e703e08386bab49850405e307d16002d`

Pre-task documentation validation: run #4 / run id `37343323013`: **SUCCESS**.

This runbook defines the minimum production deployment sequence supported by current repository evidence. It does not choose a VM, container, orchestrator, cloud, load balancer, reverse proxy, secret manager, TLS product, or CD platform.

## 1. Preconditions

Before a production deployment, record:

- exact application commit SHA;
- exact deployable artifact identity/version/digest where the selected platform provides one;
- approved change/release identifier;
- target environment = production;
- responsible Platform/Operations role;
- Database Operations availability when schema migration may occur;
- rollback artifact/revision;
- current database backup/HA health according to approved operations procedures.

Do not deploy from an untracked local build.

## 2. Production Profile

Production must start with:

`SPRING_PROFILES_ACTIVE=production`

Verify the process is using the production profile before admitting traffic.

Do not rely on the repository default profile.

## 3. Production Configuration

All production secrets and environment-specific values must be injected externally.

At minimum ensure the configuration required by `ENVIRONMENT_CONFIGURATION.md` is present for the selected authentication/runtime mode.

Never commit production passwords, signing material, private keys, raw tokens, or secret-manager exports to Git.

## 4. PostgreSQL Pre-Deployment Gate

Before starting a new application revision:

1. verify the stable PostgreSQL endpoint is authoritative and reachable;
2. verify exactly one writable PostgreSQL primary exists;
3. verify required HA posture/standby health or explicitly record an approved degraded state;
4. verify production credentials are valid;
5. verify the database is not in an unresolved failover/recovery state;
6. verify required backup/WAL protection is operational according to the DR baseline.

Deployment must stop if write authority is ambiguous.

## 5. Flyway / Migration Gate

Flyway remains the authoritative schema migration mechanism.

Requirements:

- `spring.flyway.validate-on-migrate=true`;
- `spring.flyway.clean-disabled=true`;
- JPA remains `ddl-auto=validate`;
- applied migrations are immutable;
- schema changes use new additive/forward migrations;
- deployment coordination must prevent uncontrolled concurrent migration attempts from multiple starting nodes.

Until the runtime platform provides an approved migration-leader mechanism, Platform/Operations must serialize the migration-bearing startup or otherwise prove only one migration authority can act at a time.

Do not run migrations independently against standbys.

## 6. Deployment Sequence

Use this logical sequence:

1. confirm change approval and deployment artifact identity;
2. confirm production configuration/secrets are available;
3. confirm PostgreSQL pre-deployment gate;
4. remove/drain one application node from traffic where rolling deployment is supported;
5. stop that node gracefully;
6. deploy the approved artifact/revision;
7. start with the explicit production profile;
8. allow Flyway validation/migration only under the controlled migration authority;
9. verify JPA schema validation;
10. verify application liveness;
11. verify readiness and dependency connectivity;
12. verify protected authentication/authorization;
13. execute representative safe API/read acceptance;
14. execute a controlled write acceptance when the change/risk requires it;
15. return the node to traffic only after readiness/acceptance succeeds;
16. repeat for remaining nodes while maintaining the approved application redundancy where the selected platform supports rolling operation;
17. complete post-deployment verification and record evidence.

If the selected deployment platform cannot support rolling operation, the approved change procedure must explicitly record the availability impact rather than pretending zero downtime.

## 7. Application HA Constraints During Deployment

During a multi-node deployment:

- do not remove all HidraAPI nodes simultaneously unless an explicitly approved outage is intended;
- traffic may reach only ready nodes;
- REST correctness must not depend on sticky sessions;
- node-local cache must not become authoritative shared state;
- current in-process STOMP realtime must remain under its approved single-node/disabled constraint until clustered realtime is implemented;
- application-local background execution must not create uncontrolled duplicate distributed work.

## 8. Startup Acceptance Gate

A node is not deployment-successful merely because its process starts.

Before admission to traffic, verify:

- production profile active;
- PostgreSQL connectivity through the stable endpoint;
- Flyway validation/migration successful;
- JPA schema validation successful;
- `/actuator/health` reachable according to the approved runtime boundary;
- liveness healthy;
- readiness healthy;
- authentication works;
- authorization remains enforced;
- production error disclosure remains restricted;
- required identity/directory integration works when enabled;
- no secret values appear in deployment evidence/logging;
- representative application function succeeds.

## 9. Post-Deployment Verification

After the intended node set is deployed:

- verify the expected application revision is running on every active node;
- verify required node count/redundancy;
- verify traffic is reaching only ready nodes;
- verify the stable database endpoint remains authoritative;
- verify database connection pool health/no obvious exhaustion;
- verify PostgreSQL HA remains in the approved state or record degradation;
- verify application logs retain correlation/request/actor context;
- verify Actuator/Prometheus surfaces remain available according to policy;
- verify no unexpected Flyway migration remains pending/failing;
- record deployment completion and residual issues.

## 10. Rollback Decision

Rollback is required when the new revision cannot pass the acceptance gate and a safe corrective forward action is not approved within the change window.

Application rollback and database rollback are different operations.

### 10.1 Application rollback

If the database schema remains compatible with the prior application revision:

1. drain/remove the failed node from traffic;
2. stop gracefully;
3. deploy the previously approved application artifact;
4. start with production profile;
5. repeat Flyway/JPA/startup/security acceptance;
6. restore traffic only after readiness succeeds.

### 10.2 Database/schema rollback

Do **not** delete, edit, or rewrite an applied Flyway migration.

If a schema/data change must be reversed:

- use an explicitly reviewed new corrective migration when safe; or
- invoke the approved database/DR recovery procedure when true recovery to an earlier state is required.

Do not restore an old database backup merely as an application deployment rollback shortcut.

## 11. Abort Conditions

Stop deployment and escalate when:

- exact artifact/revision is unknown;
- production profile cannot be confirmed;
- required secrets/configuration are missing or exposed;
- PostgreSQL write authority is ambiguous;
- stable database endpoint is unavailable;
- Flyway validation/migration fails;
- JPA validation fails;
- authentication/authorization fails;
- readiness remains unhealthy;
- representative acceptance fails materially;
- concurrent migration behavior is uncontrolled;
- database HA/failover is unstable;
- security containment requires deployment suspension.

## 12. Evidence to Retain

Record without raw secrets:

- change/release identifier;
- deployed commit SHA;
- artifact identity/digest where available;
- deployment start/end;
- operator/role;
- production profile confirmation;
- environment/configuration reference/version;
- database endpoint reference without credentials;
- Flyway result;
- JPA validation result;
- health/readiness result;
- authentication/authorization result;
- representative acceptance result;
- nodes deployed and final node count;
- rollback action if any;
- deviations/degraded conditions;
- final acceptance authority.

## 13. Product-Specific Gap

The following remain intentionally unresolved:

- package/image distribution mechanism;
- VM/container/orchestrator commands;
- service manager;
- load-balancer/ingress commands;
- secret-manager commands;
- TLS termination commands;
- migration-leader implementation;
- release promotion automation;
- deployment approval UI/workflow;
- automatic rollback mechanism.

HPR-P1-009 may implement controlled deployment automation only after the production deployment target is explicitly approved.

## 14. Production Readiness

The deployment procedure is documented, but a production deployment platform and measured execution evidence are not yet established.

Production readiness therefore remains **NOT ESTABLISHED**.
