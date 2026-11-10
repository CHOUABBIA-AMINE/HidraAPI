# FD-02 — production-first single-node API smoke (HAPI-932)

**Scope:** Development-only synthetic qualification of one real Java 21 HidraAPI JAR and a single fresh PostgreSQL 16 database using production-profile configuration. This is neither a production deployment nor complete six-module business acceptance.

## Goal and decision rule

The smoke **fails** if the JAR cannot build and reach readiness, Flyway cannot migrate/validate the fresh schema, an ordinary non-superuser runtime principal cannot authenticate and execute a native Identity write/read, an unauthorized client gains protected data, the runtime account has DDL/protected-source read privileges, or the actual second JVM loses the first one's committed user ID. No step relaxes the repository's current authentication, authorization, audit or transaction rules.

Module-level acceptance remains with existing Full CI and native PostgreSQL test suites. FD-02 intentionally does not fabricate independent Workflow approvals or claim production IdP, company, asset, UI, HA, backup, SITE, industrial or release qualification.

## Repeatable isolated execution

Run in a disposable GitHub Actions hosted runner via **HidraAPI CI Qualification (isolated)**, `fd02-runtime` job. The job runs on the FD-02 file changes on `main` or by explicit `workflow_dispatch`. The PostgreSQL 16 service uses a throwaway credential derived from the isolated job identity (never a production secret). `scripts/qualification/fd02_one_node_runtime.sh` generates separate random migration, runtime, bootstrap and JWT credentials for that invocation. It creates a fresh `fd02_qualification` database, applies the same packaged Flyway/PostgreSQL libraries to the source migrations, validates them, provisions a single synthetic LOCAL provider, then starts a production-profile JAR with **only** the non-superuser runtime database identity. It disables application Flyway auto-migration because migration/validation is handled by the separate non-superuser schema owner; Hibernate schema validation stays active.

The runtime's temporary privileges are sufficient for the bounded test: schema usage, table reads except explicit protected engineering exclusions, and Identity writes. They are **not** a production privilege specification. DDL and protected engineering reads must fail for the ordinary account. Any required additional production writer is a separate configuration decision; do not broaden the runtime role or omit a denial merely to turn this smoke green.

The initial JVM enables the controlled LOCAL administrator bootstrap and uses an explicit native HTTP issuer; the second JVM disables bootstrap, retains the database and signing key, and must return the same administrator and newly registered synthetic user IDs through genuine HTTP. No SQL fixture is used for the business registration; the sole synthetic SQL reference fixture is the active LOCAL provider.

The independent client checks the actual `/api/v1/identity/authentication/login`, `/api/v1/identity/me` and `/api/v1/identity/users` routes, protected readback, missing/invalid bearer and invalid password denials. It also exercises available Organization, Party, Topology, Configuration and Workflow read/capability routes; these calls prove *route reachability*, not an end-to-end business process.

## Evidence and limitations

Read the job outcome and its attached `hidra-fd02-<sha>` artifact. Only after **the runtime job itself succeeds** may the single-node smoke be described as qualified at that exact SHA. `evidence.json` retains SHA, JAR digest, PostgreSQL major/minor version number, migration count, nonprivileged role checks, true JVM restart result and HTTP check totals. `initial.json` and `restart.json` include only route/status metadata and synthetic IDs. No credentials, JWTs, request bodies or protected errors are published; private runner build/migration/application logs are discarded on cleanup. The Python fixture tests and shell syntax checks alone **do not** constitute the runtime proof.

The test does not select TLS termination, secret manager, topology writer policy, production database permission matrix or any deployment target. Production release requires separate infrastructure and operations authorization.
