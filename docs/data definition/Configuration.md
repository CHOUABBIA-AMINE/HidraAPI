# HIDRA Configuration Module — Data Definition Document

```text
Document code : HIDRA-CONFIGURATION-DDD
Repository    : HidraAPI
Module        : configuration
Package root  : dz.sh.hidra.modules.configuration
Table prefix  : hidra_configuration_*
Product       : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
Owner         : Sonatrach / TRC : Digitalization Initiative
Author        : Abir MEDJERAB
UpdatedOn     : 2026-06-11
Status        : Active DDD reference
Version       : 1.1
```

---

## 1. Purpose

The `configuration` module owns governed runtime settings, feature flags, scoped overrides, configuration profiles, validation rules, change requests, deployments, and resolved configuration snapshots.

Configuration is not a generic shared catalog module and must not absorb module-owned business taxonomies.

---

## 2. Canonical implementation identity

```text
Module name   : configuration
Package root  : dz.sh.hidra.modules.configuration
Table prefix  : hidra_configuration_*
```

Forbidden table usage:

```text
Configuration must not create hidra_topology_* catalog tables.
Configuration must not create hidra_telemetry_* catalog tables.
Configuration must not create hidra_monitoring_* catalog tables.
Configuration must not create hidra_hse_* catalog tables.
Configuration must not create hidra_asset_* catalog tables.
Configuration must not create hidra_identity_* permission tables.
```

---

## 3. Ownership

Configuration owns:

```text
ConfigurationNamespace
ConfigurationDefinition
ConfigurationDefinitionVersion
ConfigurationValue
ScopedConfigurationOverride
ConfigurationProfile
ConfigurationProfileEntry
FeatureFlag
FeatureFlagRule
ConfigurationValidationRule
ConfigurationChangeRequest
ConfigurationDeployment
ResolvedConfigurationSnapshot
ConfigurationExternalReference
ConfigurationCatalogEntry
ConfigurationCatalogTranslation
```

Configuration does not own:

```text
FacilityType
EquipmentType
TelemetryQualityCode
WorkflowDefinition
MonitoringThreshold
AlarmSeverity
IncidentClassification
HseComplianceObligation
CustodyCalculationFormula
AssetMaintenanceStrategy
IdentityPermission
OrganizationHierarchy
```

---

## 4. Business taxonomy rule

If a value has business meaning inside one bounded context, that bounded context owns it.

Examples:

```text
FacilityType belongs to topology.
TelemetryUnit and telemetry validation rules belong to telemetry.
Monitoring severity/rule types belong to monitoring.
HSE classifications and obligations belong to HSE.
Asset maintenance strategy belongs to assets.
Custody calculation formulas belong to custody.
Workflow definitions belong to workflow.
Identity permissions belong to identity.
```

Configuration may hold runtime settings that govern how those modules behave, but not the business taxonomy itself.

---

## 5. Entity catalogue

| Entity | Table | Purpose |
|---|---|---|
| ConfigurationNamespace | `hidra_configuration_namespace` | Namespace for settings. |
| ConfigurationDefinition | `hidra_configuration_definition` | Definition of a governed setting. |
| ConfigurationDefinitionVersion | `hidra_configuration_definition_version` | Versioned setting definition. |
| ConfigurationValue | `hidra_configuration_value` | Effective value. |
| ScopedConfigurationOverride | `hidra_configuration_scoped_override` | Scoped override by module/org/context. |
| ConfigurationProfile | `hidra_configuration_profile` | Group of settings. |
| ConfigurationProfileEntry | `hidra_configuration_profile_entry` | Profile entry. |
| FeatureFlag | `hidra_configuration_feature_flag` | Feature flag. |
| FeatureFlagRule | `hidra_configuration_feature_flag_rule` | Rule for feature enablement. |
| ConfigurationValidationRule | `hidra_configuration_validation_rule` | Validation rule for values. |
| ConfigurationChangeRequest | `hidra_configuration_change_request` | Governance request for config change. |
| ConfigurationDeployment | `hidra_configuration_deployment` | Deployment record. |
| ResolvedConfigurationSnapshot | `hidra_configuration_resolved_snapshot` | Resolved effective values snapshot. |
| ConfigurationExternalReference | `hidra_configuration_external_reference` | Neutral external reference. |
| ConfigurationCatalogEntry | `hidra_configuration_catalog_entry` | Configuration-owned catalog. |
| ConfigurationCatalogTranslation | `hidra_configuration_catalog_translation` | Multilingual catalog labels. |

---

## 6. Allowed configuration scope

Configuration may hold:

```text
cross-cutting parameters
runtime toggles
scoped overrides
activation metadata
operator preferences
technical references requiring governance
feature flag rules
```

Configuration must not become a dumping ground for module-owned business data.

---

## 7. Documentation and annotation rule

Domain, application, and infrastructure configuration models must not use `@Schema` or OpenAPI annotations.

`@Schema` is allowed only in configuration API request/response models.
