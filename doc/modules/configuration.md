# Configuration Module

## Status

CURRENT — canonical module inventory and HPR-P2-009 permanent semantics.

## Verification Baseline

Source baseline: `44d4fe773d69ed51dd90820140c8d9e7aee6cba2`

Package root: `dz.sh.hidra.modules.configuration`

Verified on 2026-10-09 against the source parent above. Metadata follows [the module index](README.md); business accountability follows this owning module, without an invented named human owner. Historical HPR-P2-004 established the inventory; HPR-P2-009 refreshes source applicability and lasting semantics. The canonical [API](../api/README.md) and [database](../database/README.md) sets retain their separate authority.

## Responsibility

Owns configuration definitions and values plus feature-flag state.

The module has the current Hexagonal structure `api/`, `application/`, `domain/` and `infrastructure/`. Its private domain, application implementation and persistence internals remain owned by this module.

## Domain Model

Current domain model types (package-info excluded):

- [ConfigurationDefinition](../../src/main/java/dz/sh/hidra/modules/configuration/domain/model/ConfigurationDefinition.java)
- [ConfigurationValue](../../src/main/java/dz/sh/hidra/modules/configuration/domain/model/ConfigurationValue.java)
- [FeatureFlag](../../src/main/java/dz/sh/hidra/modules/configuration/domain/model/FeatureFlag.java)

Domain policies:

- [ConfigurationBoundaryPolicy](../../src/main/java/dz/sh/hidra/modules/configuration/domain/policy/ConfigurationBoundaryPolicy.java)

Domain services:

- [ConfigurationValueGuard](../../src/main/java/dz/sh/hidra/modules/configuration/domain/service/ConfigurationValueGuard.java)

Canonical semantic context: [ownership](../domain/DOMAIN_OWNERSHIP.md) and [permanent decisions](../domain/SEMANTIC_DECISIONS.md).

## Application and API Surface

Current inbound/use-case ports:

- [CreateConfigurationDefinitionUseCase](../../src/main/java/dz/sh/hidra/modules/configuration/application/port/in/CreateConfigurationDefinitionUseCase.java)
- [CreateFeatureFlagUseCase](../../src/main/java/dz/sh/hidra/modules/configuration/application/port/in/CreateFeatureFlagUseCase.java)
- [SetConfigurationValueUseCase](../../src/main/java/dz/sh/hidra/modules/configuration/application/port/in/SetConfigurationValueUseCase.java)

Current application services:

- [ConfigurationApplicationService](../../src/main/java/dz/sh/hidra/modules/configuration/application/service/ConfigurationApplicationService.java)

Current API/controller classes:

- [ConfigurationController](../../src/main/java/dz/sh/hidra/modules/configuration/api/rest/controller/ConfigurationController.java)
- [SpringConfigurationController](../../src/main/java/dz/sh/hidra/modules/configuration/api/rest/controller/SpringConfigurationController.java)

These inventories identify source-visible adapters/use cases, not proof of every external integration. Exact wire contracts and compatibility rules are maintained in [the API set](../api/README.md).

## Persistence

Current JPA entity count: **16** (classes annotated `@Entity`, excluding package-info).

Persistence entities:

- [ConfigurationCatalogEntryJpaEntity](../../src/main/java/dz/sh/hidra/modules/configuration/infrastructure/persistence/entity/ConfigurationCatalogEntryJpaEntity.java)
- [ConfigurationCatalogTranslationJpaEntity](../../src/main/java/dz/sh/hidra/modules/configuration/infrastructure/persistence/entity/ConfigurationCatalogTranslationJpaEntity.java)
- [ConfigurationChangeRequestJpaEntity](../../src/main/java/dz/sh/hidra/modules/configuration/infrastructure/persistence/entity/ConfigurationChangeRequestJpaEntity.java)
- [ConfigurationDefinitionJpaEntity](../../src/main/java/dz/sh/hidra/modules/configuration/infrastructure/persistence/entity/ConfigurationDefinitionJpaEntity.java)
- [ConfigurationDefinitionVersionJpaEntity](../../src/main/java/dz/sh/hidra/modules/configuration/infrastructure/persistence/entity/ConfigurationDefinitionVersionJpaEntity.java)
- [ConfigurationDeploymentJpaEntity](../../src/main/java/dz/sh/hidra/modules/configuration/infrastructure/persistence/entity/ConfigurationDeploymentJpaEntity.java)
- [ConfigurationExternalReferenceJpaEntity](../../src/main/java/dz/sh/hidra/modules/configuration/infrastructure/persistence/entity/ConfigurationExternalReferenceJpaEntity.java)
- [ConfigurationNamespaceJpaEntity](../../src/main/java/dz/sh/hidra/modules/configuration/infrastructure/persistence/entity/ConfigurationNamespaceJpaEntity.java)
- [ConfigurationProfileEntryJpaEntity](../../src/main/java/dz/sh/hidra/modules/configuration/infrastructure/persistence/entity/ConfigurationProfileEntryJpaEntity.java)
- [ConfigurationProfileJpaEntity](../../src/main/java/dz/sh/hidra/modules/configuration/infrastructure/persistence/entity/ConfigurationProfileJpaEntity.java)
- [ConfigurationValidationRuleJpaEntity](../../src/main/java/dz/sh/hidra/modules/configuration/infrastructure/persistence/entity/ConfigurationValidationRuleJpaEntity.java)
- [ConfigurationValueJpaEntity](../../src/main/java/dz/sh/hidra/modules/configuration/infrastructure/persistence/entity/ConfigurationValueJpaEntity.java)
- [FeatureFlagJpaEntity](../../src/main/java/dz/sh/hidra/modules/configuration/infrastructure/persistence/entity/FeatureFlagJpaEntity.java)
- [FeatureFlagRuleJpaEntity](../../src/main/java/dz/sh/hidra/modules/configuration/infrastructure/persistence/entity/FeatureFlagRuleJpaEntity.java)
- [ResolvedConfigurationSnapshotJpaEntity](../../src/main/java/dz/sh/hidra/modules/configuration/infrastructure/persistence/entity/ResolvedConfigurationSnapshotJpaEntity.java)
- [ScopedConfigurationOverrideJpaEntity](../../src/main/java/dz/sh/hidra/modules/configuration/infrastructure/persistence/entity/ScopedConfigurationOverrideJpaEntity.java)

Persistence repository adapters and reference validators:

- [JpaConfigurationDefinitionRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/configuration/infrastructure/persistence/adapter/JpaConfigurationDefinitionRepositoryAdapter.java)
- [JpaConfigurationValueRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/configuration/infrastructure/persistence/adapter/JpaConfigurationValueRepositoryAdapter.java)
- [JpaFeatureFlagRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/configuration/infrastructure/persistence/adapter/JpaFeatureFlagRepositoryAdapter.java)

Table/schema ownership and the generated dictionary remain in [the database set](../database/README.md). Entity presence does not invent constraints, retention policy or production-data approval.

## Cross-Module Boundary

Exported application contracts owned by this module:

No source class is present in this category at the verified parent.

Imported scalar contracts supplied by collaborating owners:

No source class is present in this category at the verified parent.

Outbound application ports (persistence and collaborating capabilities):

- [ConfigurationAuditPort](../../src/main/java/dz/sh/hidra/modules/configuration/application/port/out/ConfigurationAuditPort.java)
- [ConfigurationDefinitionRepositoryPort](../../src/main/java/dz/sh/hidra/modules/configuration/application/port/out/ConfigurationDefinitionRepositoryPort.java)
- [ConfigurationDeploymentNotificationPort](../../src/main/java/dz/sh/hidra/modules/configuration/application/port/out/ConfigurationDeploymentNotificationPort.java)
- [ConfigurationModuleReferencePort](../../src/main/java/dz/sh/hidra/modules/configuration/application/port/out/ConfigurationModuleReferencePort.java)
- [ConfigurationSecretReferencePort](../../src/main/java/dz/sh/hidra/modules/configuration/application/port/out/ConfigurationSecretReferencePort.java)
- [ConfigurationValueRepositoryPort](../../src/main/java/dz/sh/hidra/modules/configuration/application/port/out/ConfigurationValueRepositoryPort.java)
- [ConfigurationWorkflowApprovalPort](../../src/main/java/dz/sh/hidra/modules/configuration/application/port/out/ConfigurationWorkflowApprovalPort.java)
- [FeatureFlagRepositoryPort](../../src/main/java/dz/sh/hidra/modules/configuration/application/port/out/FeatureFlagRepositoryPort.java)

Export scope is checked by [architecture guardrails](../../src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java) and [forensic closure](../../src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java). Consumers use deliberately exported contracts; private domain, infrastructure and non-exported application packages remain private. Owner existence, eligibility and approval are separate predicates and cannot be substituted for one another.

## Current-State Limits

- Source/class presence is structural evidence; this documentation transfer executes no runtime test or external system.
- Legacy reviews/roadmaps remain unchanged history. HPR-P2-008 is closed; durable rules now live in [the semantic register](../domain/SEMANTIC_DECISIONS.md), with execution evidence in [reconciliation](../model-remediation/RECONCILIATION.md).
- Optional references and historical replay follow the subject-specific rules; no universal active-only rule is implied.
- Retention values, owner-approved policy contents and workload/physical survivability are not established by documentation.
- `agents`, `environment` and `otsecurity` are not implemented module roots. Target/deferred capabilities require separately admitted implementation.

## Permanent Semantic Decisions

Configuration owns definitions, scoped values and feature flags. Environment/owning-module metadata is required where coded, optional definition-version integrity is local, and secret defaults/values share a reference-only boundary.

The linked decisions carry the precise per-subject exceptions and source/test/migration evidence:

| Subject | Canonical decision |
|---|---|
| ConfigurationDefinition | [HMSR-019 reconciled rule](../domain/SEMANTIC_DECISIONS.md#configuration-configurationdefinition) |
| FeatureFlag | [HMSR-028 reconciled rule](../domain/SEMANTIC_DECISIONS.md#configuration-featureflag) |
| ConfigurationValue | [HMSR-044 reconciled rule](../domain/SEMANTIC_DECISIONS.md#configuration-configurationvalue) |
