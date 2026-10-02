# HidraAPI Persistence Migration Inventory

## 1. Purpose

This document is the repository-level persistence index referenced by the README.

Flyway SQL under:

```text
src/main/resources/db/migration
```

is the executable persistence source of truth. This document does **not** replace the SQL
and must not be used to infer table columns that are not shown here.

---

## 2. Current migration sequence

The live v0.2.0 repository uses the following date-based module migration sequence:

| Order | Migration | Module |
|---:|---|---|
| 001 | `V20260611_001__create_identity_tables.sql` | identity |
| 002 | `V20260611_002__create_organization_tables.sql` | organization |
| 003 | `V20260611_003__create_party_tables.sql` | party |
| 004 | `V20260611_004__create_topology_tables.sql` | topology |
| 005 | `V20260611_005__create_telemetry_tables.sql` | telemetry |
| 006 | `V20260611_006__create_planning_tables.sql` | planning |
| 007 | `V20260611_007__create_monitoring_tables.sql` | monitoring |
| 008 | `V20260611_008__create_alarm_tables.sql` | alarm |
| 009 | `V20260611_009__create_leakdetection_tables.sql` | leakdetection |
| 010 | `V20260611_010__create_incident_tables.sql` | incident |
| 011 | `V20260611_011__create_risk_tables.sql` | risk |
| 012 | `V20260611_012__create_hse_tables.sql` | hse |
| 013 | `V20260611_013__create_integrity_tables.sql` | integrity |
| 014 | `V20260611_014__create_assets_tables.sql` | assets |
| 015 | `V20260611_015__create_custody_tables.sql` | custody |
| 016 | `V20260611_016__create_workflow_tables.sql` | workflow |
| 017 | `V20260611_017__create_audit_tables.sql` | audit |
| 018 | `V20260611_018__create_documents_tables.sql` | documents |
| 019 | `V20260611_019__create_integration_tables.sql` | integration |
| 020 | `V20260611_020__create_configuration_tables.sql` | configuration |
| 021 | `V20260611_021__create_notification_tables.sql` | notification |
| 022 | `V20260611_022__create_simulation_tables.sql` | simulation |
| 023 | `V20260611_023__create_analytics_tables.sql` | analytics |
| 024 | `V20260611_024__create_reporting_tables.sql` | reporting |

This sequence supersedes older roadmap examples such as `V010__create_identity_tables.sql`
or `V020__create_organization_tables.sql`. Do not rename applied/current migrations only
to make them match historical roadmap filenames.

---

## 3. Ownership rules

Each module owns its persistence schema and adapters.

```text
domain model          -> no JPA annotations
application           -> outbound repository ports
infrastructure        -> JPA entities, Spring Data repositories, mappers, adapters
Flyway SQL            -> schema evolution
```

A module must not directly mutate another module's tables.

Cross-context relationships should use stable identifiers/references or explicit
integration contracts rather than JPA aggregate graphs spanning bounded contexts.

---

## 4. Validation

Run against a clean PostgreSQL database before release:

```bash
mvn -q -DskipTests compile
mvn -q test
mvn -q clean verify
```

Where Flyway plugin/database configuration is available, also run the project-approved
Flyway validation command.

The Boot smoke test introduced by STB2-006 starts PostgreSQL with Testcontainers and keeps
`spring.jpa.hibernate.ddl-auto=validate`, which is intended to surface divergence between
JPA mappings and migrations.
