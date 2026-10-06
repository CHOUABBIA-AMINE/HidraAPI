# HidraAPI Schema Ownership

## Status

CURRENT — HPR-P2-006 persistence ownership baseline.

## Ownership Rule

The physical database is one PostgreSQL application database, while business persistence ownership follows the 24 implemented module boundaries.

A module owns the persistence entities and migration-created tables representing its business state. Another business module must not bypass the application-contract boundary by importing the provider's persistence entity/repository implementation.

## Current Persistence Inventory by Module

| Module | JPA persistence entities | Initial schema migration |
|---|---:|---|
| alarm | 12 | `V20260611_008__create_alarm_tables.sql` |
| analytics | 28 | `V20260611_023__create_analytics_tables.sql` |
| assets | 25 | `V20260611_014__create_assets_tables.sql` |
| audit | 15 | `V20260611_017__create_audit_tables.sql` |
| configuration | 16 | `V20260611_020__create_configuration_tables.sql` |
| custody | 20 | `V20260611_015__create_custody_tables.sql` |
| documents | 11 | `V20260611_018__create_documents_tables.sql` |
| hse | 17 | `V20260611_012__create_hse_tables.sql` |
| identity | 26 | `V20260611_001__create_identity_tables.sql` |
| incident | 14 | `V20260611_010__create_incident_tables.sql` |
| integration | 24 | `V20260611_019__create_integration_tables.sql` |
| integrity | 22 | `V20260611_013__create_integrity_tables.sql` |
| leakdetection | 14 | `V20260611_009__create_leakdetection_tables.sql` |
| monitoring | 11 | `V20260611_007__create_monitoring_tables.sql` |
| notification | 24 | `V20260611_021__create_notification_tables.sql` |
| organization | 18 | `V20260611_002__create_organization_tables.sql` |
| party | 30 | `V20260611_003__create_party_tables.sql` |
| planning | 16 | `V20260611_006__create_planning_tables.sql` |
| reporting | 22 | `V20260611_024__create_reporting_tables.sql` |
| risk | 24 | `V20260611_011__create_risk_tables.sql` |
| simulation | 25 | `V20260611_022__create_simulation_tables.sql` |
| telemetry | 16 | `V20260611_005__create_telemetry_tables.sql` |
| topology | 22 | `V20260611_004__create_topology_tables.sql` |
| workflow | 17 | `V20260611_016__create_workflow_tables.sql` |
| **Total** | **469** | **24 initial module schema migrations** |

Later migrations refine constraints, add/remove fields, and remediate selected tables. The ordered migration chain, not the initial create migration alone, defines current physical schema.

## Cross-Module References

Cross-module database references may exist as IDs, snapshots or foreign-key relationships where migrations establish them. They do not change domain ownership.

Application code must continue to collaborate through application ports/contracts rather than treating a foreign table as a shared aggregate.

## Platform Boundary

Platform technical persistence is not a license for unrestricted business-table access. The architecture guardrail restricts generic platform JPA access to the reviewed Workbench boundary with fail-closed exposure policy.

## Database Owner vs Business Owner

Database Operations owns production PostgreSQL operation, HA, backup, recovery and controlled schema execution.

Business/module ownership determines semantic responsibility for persisted business state.

These are complementary responsibilities, not competing schema authorities.
