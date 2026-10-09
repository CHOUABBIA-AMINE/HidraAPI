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
| risk | 25 | `V20260611_011__create_risk_tables.sql` |
| simulation | 25 | `V20260611_022__create_simulation_tables.sql` |
| telemetry | 16 | `V20260611_005__create_telemetry_tables.sql` |
| topology | 22 | `V20260611_004__create_topology_tables.sql` |
| workflow | 17 | `V20260611_016__create_workflow_tables.sql` |
| **Total** | **470** | **24 initial module schema migrations** |

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

## Reviewed physical naming and coverage

Current capture and full provenance: [database index](README.md). All 481 tables and one sequence have a source-backed owner. The 470 JPA tables supply module ownership; 11 non-JPA tables and the identity sequence require explicit metadata. Entity count is not table count.

Most modules use `hidra_<module>_*`; actual exceptions are:

| Module | Physical naming |
|---|---|
| assets | `hidra_asset_*` |
| organization | `hidra_org_*` |
| leakdetection | `hidra_leak_detection_*` |
| alarm | `hidra_alarm` plus `hidra_alarm_*` |
| incident | `hidra_incident` plus `hidra_incident_*` |
| risk | `hidra_risk_*` plus `hidra_residual_risk_assessment` |

Names alone never grant ownership. The exact relation entries, JPA paths and migration evidence in the [dictionary](DATA_DICTIONARY.md) control exceptions. [Reviewed metadata](../../.github/database-dictionary-ownership.json) links each override to existing source.

| Non-JPA relation | Owner | Purpose |
|---|---|---|
| flyway_schema_history | flyway | Technical execution/history evidence |
| hidra_custody_planning_product_policy | custody | Product eligibility and approval provenance |
| hidra_telemetry_planning_unit_role | telemetry | Quantity/rate unit-role eligibility |
| hidra_telemetry_planning_unit_pair | telemetry | Quantity/rate compatibility |
| hidra_planning_nomination_reference_mapping | planning | Per-nomination legacy-to-owner mapping |
| hidra_planning_target_value_policy | planning | Numeric/text target representation policy |
| hidra_hse_catalog_field_policy | hse | Field-role/catalog-family metadata |
| hidra_integrity_catalog_field_policy | integrity | Field-role/catalog-family metadata |
| hidra_incident_closure_policy | incident | Classification/severity closure requirements |
| hidra_incident_relationship_policy | incident | Direction/reciprocal relationship policy |
| hidra_integration_job_run_sequence | integration | Per-job run counter table; not a SQL sequence |
| hidra_org_operational_scope_id_seq | organization | Registry identity sequence |

This classifies ownership and structure, not approved products, unit pairs, catalog families, per-record mappings or policy values. Those approvals remain external owner decisions; no values are provisioned here.

## Actual reference forms and new-change requirements

The reviewed catalog contains 688 foreign keys; all reference relations with the same resolved owner. Zero cross-owner physical FKs were found in this capture. This does not imply that cross-module references are absent: IDs and snapshots do not necessarily have database FKs. For example, Nomination retains source/destination asset IDs, shipper-party ID and code snapshot; OperationalScope stores target identity without owning provider state. HMR-080 product/unit owner references use explicit policy/mapping structures and owner contracts rather than turning provider tables into Planning aggregates.

For new same-module FKs, review aggregate lifecycle, ordered composite keys, delete/update actions, nullability and historical-data preflight. Use explicit RESTRICT/NO ACTION where loss of historical evidence must be prevented; CASCADE needs a documented lifecycle justification, not a blanket prohibition or default.

For new cross-module IDs/snapshots/FKs, require both module owners to review meaning, existence/eligibility validation, historical snapshots, failure behavior, deletion/reassignment and migration order. Prefer provider application contracts for semantic checks. A proposed physical FK additionally requires explicit boundary and deployment-coupling justification plus Database Operations review. These are governance admission requirements, not a claim that every historical change carried recorded approvals. A DB FK does not enforce catalog family, eligibility, workflow approval or transfer aggregate ownership.

No provider persistence imports or direct writes are admitted by these rules. Generic platform access remains confined to its reviewed Workbench boundary. Database Operations owns execution/backup/recovery; semantic owners approve meaning and actual mappings.

## Retained physical columns and multi-module review

Three physical columns have no current JPA mapping: `hidra_org_reporting_line.reporting_line_type` is the HMR-028 trigger-synchronized compatibility projection; `hidra_topology_equipment.legacy_equipment_kind` and `hidra_topology_equipment_type.legacy_equipment_kind` retain HMR-054 historical values. They are included in the dictionary and must not be deleted or interpreted as current catalog authority merely because Java no longer maps them.

TelemetryPoint has 18 columns and validated owner-unit FK; TopologyConnection has 12 columns, catalog/segment FKs and a no-self-loop check; Nomination has 26 columns, five checks, the ordered `(scenario_id, revision_id)` composite FK and HMR-080 type trigger. Organization, Assets and Leakdetection prefix samples and non-JPA policies were reviewed against the complete migration chain. See exact constraints/indexes/functions in the dictionary; existence alone does not establish production data approval.

Current HPR-P2-006 content is implemented pending Stage B exact-head documentation/full CI. P2 OPEN/P3 DEFERRED; original 82-migration P1 evidence and earlier generation identities retain historical applicability described in the index.
