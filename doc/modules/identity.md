# Identity Module

## Status

CURRENT — canonical module inventory and HPR-P2-009 permanent semantics.

## Verification Baseline

Source baseline: `44d4fe773d69ed51dd90820140c8d9e7aee6cba2`

Package root: `dz.sh.hidra.modules.identity`

Verified on 2026-10-09 against the source parent above. Metadata follows [the module index](README.md); business accountability follows this owning module, without an invented named human owner. Historical HPR-P2-004 established the inventory; HPR-P2-009 refreshes source applicability and lasting semantics. The canonical [API](../api/README.md) and [database](../database/README.md) sets retain their separate authority.

## Responsibility

Owns users, principals, roles, permissions, identity providers, local credentials, sessions, grants and authorization/authentication evidence.

The module has the current Hexagonal structure `api/`, `application/`, `domain/` and `infrastructure/`. Its private domain, application implementation and persistence internals remain owned by this module.

## Domain Model

Current domain model types (package-info excluded):

- [AuthenticationEvent](../../src/main/java/dz/sh/hidra/modules/identity/domain/model/AuthenticationEvent.java)
- [AuthorizationDecision](../../src/main/java/dz/sh/hidra/modules/identity/domain/model/AuthorizationDecision.java)
- [AuthorizationDelegationGrant](../../src/main/java/dz/sh/hidra/modules/identity/domain/model/AuthorizationDelegationGrant.java)
- [ExternalRoleMapping](../../src/main/java/dz/sh/hidra/modules/identity/domain/model/ExternalRoleMapping.java)
- [GroupRoleGrant](../../src/main/java/dz/sh/hidra/modules/identity/domain/model/GroupRoleGrant.java)
- [HidraPrincipal](../../src/main/java/dz/sh/hidra/modules/identity/domain/model/HidraPrincipal.java)
- [IdentityProvider](../../src/main/java/dz/sh/hidra/modules/identity/domain/model/IdentityProvider.java)
- [LocalCredential](../../src/main/java/dz/sh/hidra/modules/identity/domain/model/LocalCredential.java)
- [LoginSession](../../src/main/java/dz/sh/hidra/modules/identity/domain/model/LoginSession.java)
- [Permission](../../src/main/java/dz/sh/hidra/modules/identity/domain/model/Permission.java)
- [Role](../../src/main/java/dz/sh/hidra/modules/identity/domain/model/Role.java)
- [RolePermissionGrant](../../src/main/java/dz/sh/hidra/modules/identity/domain/model/RolePermissionGrant.java)
- [User](../../src/main/java/dz/sh/hidra/modules/identity/domain/model/User.java)
- [UserPermissionGrant](../../src/main/java/dz/sh/hidra/modules/identity/domain/model/UserPermissionGrant.java)
- [UserRoleGrant](../../src/main/java/dz/sh/hidra/modules/identity/domain/model/UserRoleGrant.java)

Domain policies:

- [AuthorizationEvaluationRequest](../../src/main/java/dz/sh/hidra/modules/identity/domain/policy/AuthorizationEvaluationRequest.java)
- [AuthorizationEvidence](../../src/main/java/dz/sh/hidra/modules/identity/domain/policy/AuthorizationEvidence.java)
- [AuthorizationJson](../../src/main/java/dz/sh/hidra/modules/identity/domain/policy/AuthorizationJson.java)

Domain services:

- [AuthorizationExpressionEvaluator](../../src/main/java/dz/sh/hidra/modules/identity/domain/service/AuthorizationExpressionEvaluator.java)
- [AuthorizationPolicyEvaluator](../../src/main/java/dz/sh/hidra/modules/identity/domain/service/AuthorizationPolicyEvaluator.java)

Canonical semantic context: [ownership](../domain/DOMAIN_OWNERSHIP.md) and [permanent decisions](../domain/SEMANTIC_DECISIONS.md).

## Application and API Surface

Current inbound/use-case ports:

- [AuthenticateDirectUserUseCase](../../src/main/java/dz/sh/hidra/modules/identity/application/port/in/AuthenticateDirectUserUseCase.java)
- [BootstrapLocalAdministratorUseCase](../../src/main/java/dz/sh/hidra/modules/identity/application/port/in/BootstrapLocalAdministratorUseCase.java)
- [CompleteAuthenticatedPrincipalUseCase](../../src/main/java/dz/sh/hidra/modules/identity/application/port/in/CompleteAuthenticatedPrincipalUseCase.java)
- [CreateUserUseCase](../../src/main/java/dz/sh/hidra/modules/identity/application/port/in/CreateUserUseCase.java)
- [EvaluatePermissionUseCase](../../src/main/java/dz/sh/hidra/modules/identity/application/port/in/EvaluatePermissionUseCase.java)
- [IdentityAdministrationCommandUseCase](../../src/main/java/dz/sh/hidra/modules/identity/application/port/in/IdentityAdministrationCommandUseCase.java)
- [IdentityAdministrationQueryUseCase](../../src/main/java/dz/sh/hidra/modules/identity/application/port/in/IdentityAdministrationQueryUseCase.java)

Current application services:

- [AuthenticationCompletionApplicationService](../../src/main/java/dz/sh/hidra/modules/identity/application/service/AuthenticationCompletionApplicationService.java)
- [AuthenticationSessionLifecycleApplicationService](../../src/main/java/dz/sh/hidra/modules/identity/application/service/AuthenticationSessionLifecycleApplicationService.java)
- [CustodyTransferTicketActorReferenceQueryService](../../src/main/java/dz/sh/hidra/modules/identity/application/service/CustodyTransferTicketActorReferenceQueryService.java)
- [DirectAuthenticationApplicationService](../../src/main/java/dz/sh/hidra/modules/identity/application/service/DirectAuthenticationApplicationService.java)
- [DocumentsActorQueryService](../../src/main/java/dz/sh/hidra/modules/identity/application/service/DocumentsActorQueryService.java)
- [HseActorQueryService](../../src/main/java/dz/sh/hidra/modules/identity/application/service/HseActorQueryService.java)
- [IdentityAdministrationCommandApplicationService](../../src/main/java/dz/sh/hidra/modules/identity/application/service/IdentityAdministrationCommandApplicationService.java)
- [IdentityAuthorizationApplicationService](../../src/main/java/dz/sh/hidra/modules/identity/application/service/IdentityAuthorizationApplicationService.java)
- [IdentityUserApplicationService](../../src/main/java/dz/sh/hidra/modules/identity/application/service/IdentityUserApplicationService.java)
- [IncidentActorQueryService](../../src/main/java/dz/sh/hidra/modules/identity/application/service/IncidentActorQueryService.java)
- [IntegrationResolverQueryService](../../src/main/java/dz/sh/hidra/modules/identity/application/service/IntegrationResolverQueryService.java)
- [IntegrityAssessmentActorReferenceQueryService](../../src/main/java/dz/sh/hidra/modules/identity/application/service/IntegrityAssessmentActorReferenceQueryService.java)
- [IntegrityCaseActorReferenceQueryService](../../src/main/java/dz/sh/hidra/modules/identity/application/service/IntegrityCaseActorReferenceQueryService.java)
- [LocalAdministratorBootstrapApplicationService](../../src/main/java/dz/sh/hidra/modules/identity/application/service/LocalAdministratorBootstrapApplicationService.java)
- [LocalAuthenticationOutcomeApplicationService](../../src/main/java/dz/sh/hidra/modules/identity/application/service/LocalAuthenticationOutcomeApplicationService.java)
- [MaintenanceWorkOrderActorReferenceQueryService](../../src/main/java/dz/sh/hidra/modules/identity/application/service/MaintenanceWorkOrderActorReferenceQueryService.java)
- [PlanningCreatorQueryService](../../src/main/java/dz/sh/hidra/modules/identity/application/service/PlanningCreatorQueryService.java)
- [ReportingAccessAuthorizationQueryService](../../src/main/java/dz/sh/hidra/modules/identity/application/service/ReportingAccessAuthorizationQueryService.java)
- [RiskActorQueryService](../../src/main/java/dz/sh/hidra/modules/identity/application/service/RiskActorQueryService.java)
- [WorkflowActorQueryService](../../src/main/java/dz/sh/hidra/modules/identity/application/service/WorkflowActorQueryService.java)

Current API/controller classes:

- [IdentityAdministrationCommandController](../../src/main/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityAdministrationCommandController.java)
- [IdentityAdministrationQueryController](../../src/main/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityAdministrationQueryController.java)
- [IdentityAuthenticationController](../../src/main/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityAuthenticationController.java)
- [IdentityController](../../src/main/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityController.java)
- [IdentityOidcAuthorizationController](../../src/main/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityOidcAuthorizationController.java)
- [SpringIdentityController](../../src/main/java/dz/sh/hidra/modules/identity/api/rest/controller/SpringIdentityController.java)

These inventories identify source-visible adapters/use cases, not proof of every external integration. Exact wire contracts and compatibility rules are maintained in [the API set](../api/README.md).

## Persistence

Current JPA entity count: **26** (classes annotated `@Entity`, excluding package-info).

Persistence entities:

- [AttributeDefinitionJpaEntity](../../src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/AttributeDefinitionJpaEntity.java)
- [AuthenticationEventJpaEntity](../../src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/AuthenticationEventJpaEntity.java)
- [AuthorizationDecisionJpaEntity](../../src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/AuthorizationDecisionJpaEntity.java)
- [AuthorizationDelegationGrantJpaEntity](../../src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/AuthorizationDelegationGrantJpaEntity.java)
- [AuthorizationPolicyJpaEntity](../../src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/AuthorizationPolicyJpaEntity.java)
- [AuthorizationPolicyRuleJpaEntity](../../src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/AuthorizationPolicyRuleJpaEntity.java)
- [AuthorizationPolicyVersionJpaEntity](../../src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/AuthorizationPolicyVersionJpaEntity.java)
- [ExternalGroupMappingJpaEntity](../../src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/ExternalGroupMappingJpaEntity.java)
- [ExternalIdentityJpaEntity](../../src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/ExternalIdentityJpaEntity.java)
- [ExternalPermissionMappingJpaEntity](../../src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/ExternalPermissionMappingJpaEntity.java)
- [ExternalRoleMappingJpaEntity](../../src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/ExternalRoleMappingJpaEntity.java)
- [GroupJpaEntity](../../src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/GroupJpaEntity.java)
- [GroupRoleGrantJpaEntity](../../src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/GroupRoleGrantJpaEntity.java)
- [IdentityProviderJpaEntity](../../src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/IdentityProviderJpaEntity.java)
- [IdentitySynchronizationJobJpaEntity](../../src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/IdentitySynchronizationJobJpaEntity.java)
- [IdentitySynchronizationRecordJpaEntity](../../src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/IdentitySynchronizationRecordJpaEntity.java)
- [LocalCredentialJpaEntity](../../src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/LocalCredentialJpaEntity.java)
- [LoginSessionJpaEntity](../../src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/LoginSessionJpaEntity.java)
- [PermissionJpaEntity](../../src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/PermissionJpaEntity.java)
- [RoleJpaEntity](../../src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/RoleJpaEntity.java)
- [RolePermissionGrantJpaEntity](../../src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/RolePermissionGrantJpaEntity.java)
- [SubjectSecurityAttributeJpaEntity](../../src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/SubjectSecurityAttributeJpaEntity.java)
- [UserGroupMembershipJpaEntity](../../src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/UserGroupMembershipJpaEntity.java)
- [UserJpaEntity](../../src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/UserJpaEntity.java)
- [UserPermissionGrantJpaEntity](../../src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/UserPermissionGrantJpaEntity.java)
- [UserRoleGrantJpaEntity](../../src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/UserRoleGrantJpaEntity.java)

Persistence repository adapters and reference validators:

- [IdentityPersistenceAdapter](../../src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/IdentityPersistenceAdapter.java)
- [JpaAuthenticationEventRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaAuthenticationEventRepositoryAdapter.java)
- [JpaAuthorizationDecisionRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaAuthorizationDecisionRepositoryAdapter.java)
- [JpaAuthorizationDelegationGrantRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaAuthorizationDelegationGrantRepositoryAdapter.java)
- [JpaAuthorizationEvidenceAdapter](../../src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaAuthorizationEvidenceAdapter.java)
- [JpaExternalRoleMappingRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaExternalRoleMappingRepositoryAdapter.java)
- [JpaGroupRoleGrantRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaGroupRoleGrantRepositoryAdapter.java)
- [JpaIdentityProviderRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaIdentityProviderRepositoryAdapter.java)
- [JpaLocalCredentialRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaLocalCredentialRepositoryAdapter.java)
- [JpaLoginSessionRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaLoginSessionRepositoryAdapter.java)
- [JpaPermissionRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaPermissionRepositoryAdapter.java)
- [JpaRolePermissionGrantRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaRolePermissionGrantRepositoryAdapter.java)
- [JpaRoleRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaRoleRepositoryAdapter.java)
- [JpaUserPermissionGrantRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaUserPermissionGrantRepositoryAdapter.java)
- [JpaUserRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaUserRepositoryAdapter.java)
- [JpaUserRoleGrantRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaUserRoleGrantRepositoryAdapter.java)

Table/schema ownership and the generated dictionary remain in [the database set](../database/README.md). Entity presence does not invent constraints, retention policy or production-data approval.

## Cross-Module Boundary

Exported application contracts owned by this module:

- [MaintenanceWorkOrderActorReferenceContract](../../src/main/java/dz/sh/hidra/modules/identity/application/contract/assets/MaintenanceWorkOrderActorReferenceContract.java)
- [CustodyTransferTicketActorReferenceContract](../../src/main/java/dz/sh/hidra/modules/identity/application/contract/custody/CustodyTransferTicketActorReferenceContract.java)
- [DocumentsActorContract](../../src/main/java/dz/sh/hidra/modules/identity/application/contract/documents/DocumentsActorContract.java)
- [HseActorContract](../../src/main/java/dz/sh/hidra/modules/identity/application/contract/hse/HseActorContract.java)
- [IncidentActorContract](../../src/main/java/dz/sh/hidra/modules/identity/application/contract/incident/IncidentActorContract.java)
- [IntegrationResolverContract](../../src/main/java/dz/sh/hidra/modules/identity/application/contract/integration/IntegrationResolverContract.java)
- [IntegrityAssessmentActorReferenceContract](../../src/main/java/dz/sh/hidra/modules/identity/application/contract/integrity/IntegrityAssessmentActorReferenceContract.java)
- [IntegrityCaseActorReferenceContract](../../src/main/java/dz/sh/hidra/modules/identity/application/contract/integrity/IntegrityCaseActorReferenceContract.java)
- [PlanningCreatorContract](../../src/main/java/dz/sh/hidra/modules/identity/application/contract/planning/PlanningCreatorContract.java)
- [ReportingAccessAuthorizationContract](../../src/main/java/dz/sh/hidra/modules/identity/application/contract/reporting/ReportingAccessAuthorizationContract.java)
- [RiskActorContract](../../src/main/java/dz/sh/hidra/modules/identity/application/contract/risk/RiskActorContract.java)
- [WorkflowActorContract](../../src/main/java/dz/sh/hidra/modules/identity/application/contract/workflow/WorkflowActorContract.java)

Imported scalar contracts supplied by collaborating owners:

- [IdentityEmployeeReferenceContract](../../src/main/java/dz/sh/hidra/modules/organization/application/contract/identity/IdentityEmployeeReferenceContract.java)

Outbound application ports (persistence and collaborating capabilities):

- [AccessTokenIssuerPort](../../src/main/java/dz/sh/hidra/modules/identity/application/port/out/AccessTokenIssuerPort.java)
- [AuthenticationEventRepositoryPort](../../src/main/java/dz/sh/hidra/modules/identity/application/port/out/AuthenticationEventRepositoryPort.java)
- [AuthorizationAssertionPort](../../src/main/java/dz/sh/hidra/modules/identity/application/port/out/AuthorizationAssertionPort.java)
- [AuthorizationDecisionRepositoryPort](../../src/main/java/dz/sh/hidra/modules/identity/application/port/out/AuthorizationDecisionRepositoryPort.java)
- [AuthorizationDecisionSettingsPort](../../src/main/java/dz/sh/hidra/modules/identity/application/port/out/AuthorizationDecisionSettingsPort.java)
- [AuthorizationDelegationGrantRepositoryPort](../../src/main/java/dz/sh/hidra/modules/identity/application/port/out/AuthorizationDelegationGrantRepositoryPort.java)
- [AuthorizationEvidencePort](../../src/main/java/dz/sh/hidra/modules/identity/application/port/out/AuthorizationEvidencePort.java)
- [DirectAuthenticationPort](../../src/main/java/dz/sh/hidra/modules/identity/application/port/out/DirectAuthenticationPort.java)
- [ExternalRoleMappingRepositoryPort](../../src/main/java/dz/sh/hidra/modules/identity/application/port/out/ExternalRoleMappingRepositoryPort.java)
- [GroupRoleGrantRepositoryPort](../../src/main/java/dz/sh/hidra/modules/identity/application/port/out/GroupRoleGrantRepositoryPort.java)
- [IdentityProviderRepositoryPort](../../src/main/java/dz/sh/hidra/modules/identity/application/port/out/IdentityProviderRepositoryPort.java)
- [LdapCredentialVerificationPort](../../src/main/java/dz/sh/hidra/modules/identity/application/port/out/LdapCredentialVerificationPort.java)
- [LocalCredentialRepositoryPort](../../src/main/java/dz/sh/hidra/modules/identity/application/port/out/LocalCredentialRepositoryPort.java)
- [LocalPasswordHashPort](../../src/main/java/dz/sh/hidra/modules/identity/application/port/out/LocalPasswordHashPort.java)
- [LoginSessionRepositoryPort](../../src/main/java/dz/sh/hidra/modules/identity/application/port/out/LoginSessionRepositoryPort.java)
- [PermissionRepositoryPort](../../src/main/java/dz/sh/hidra/modules/identity/application/port/out/PermissionRepositoryPort.java)
- [RolePermissionGrantRepositoryPort](../../src/main/java/dz/sh/hidra/modules/identity/application/port/out/RolePermissionGrantRepositoryPort.java)
- [RoleRepositoryPort](../../src/main/java/dz/sh/hidra/modules/identity/application/port/out/RoleRepositoryPort.java)
- [UserPermissionGrantRepositoryPort](../../src/main/java/dz/sh/hidra/modules/identity/application/port/out/UserPermissionGrantRepositoryPort.java)
- [UserRepositoryPort](../../src/main/java/dz/sh/hidra/modules/identity/application/port/out/UserRepositoryPort.java)
- [UserRoleGrantRepositoryPort](../../src/main/java/dz/sh/hidra/modules/identity/application/port/out/UserRoleGrantRepositoryPort.java)

Export scope is checked by [architecture guardrails](../../src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java) and [forensic closure](../../src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java). Consumers use deliberately exported contracts; private domain, infrastructure and non-exported application packages remain private. Owner existence, eligibility and approval are separate predicates and cannot be substituted for one another.

## Current-State Limits

- Source/class presence is structural evidence; this documentation transfer executes no runtime test or external system.
- Legacy reviews/roadmaps remain unchanged history. HPR-P2-008 is closed; durable rules now live in [the semantic register](../domain/SEMANTIC_DECISIONS.md), with execution evidence in [reconciliation](../model-remediation/RECONCILIATION.md).
- Optional references and historical replay follow the subject-specific rules; no universal active-only rule is implied.
- Retention values, owner-approved policy contents and workload/physical survivability are not established by documentation.
- `agents`, `environment` and `otsecurity` are not implemented module roots. Target/deferred capabilities require separately admitted implementation.

## Permanent Semantic Decisions

Identity owns security users, authentication and authorization. Direct permission/delegation grants are bounded under their own contracts; ordinary role grants retain optional end/reason. Explicit deny and actual participating evidence govern decisions.

The linked decisions carry the precise per-subject exceptions and source/test/migration evidence:

| Subject | Canonical decision |
|---|---|
| Role | [HMSR-007 reconciled rule](../domain/SEMANTIC_DECISIONS.md#identity-role) |
| IdentityProvider | [HMSR-010 reconciled rule](../domain/SEMANTIC_DECISIONS.md#identity-identityprovider) |
| Permission | [HMSR-011 reconciled rule](../domain/SEMANTIC_DECISIONS.md#identity-permission) |
| ExternalRoleMapping | [HMSR-045 reconciled rule](../domain/SEMANTIC_DECISIONS.md#identity-externalrolemapping) |
| GroupRoleGrant | [HMSR-046 reconciled rule](../domain/SEMANTIC_DECISIONS.md#identity-grouprolegrant) |
| RolePermissionGrant | [HMSR-047 reconciled rule](../domain/SEMANTIC_DECISIONS.md#identity-rolepermissiongrant) |
| User | [HMSR-075 reconciled rule](../domain/SEMANTIC_DECISIONS.md#identity-user) |
| AuthenticationEvent | [HMSR-099 reconciled rule](../domain/SEMANTIC_DECISIONS.md#identity-authenticationevent) |
| AuthorizationDecision | [HMSR-100 reconciled rule](../domain/SEMANTIC_DECISIONS.md#identity-authorizationdecision) |
| AuthorizationDelegationGrant | [HMSR-101 reconciled rule](../domain/SEMANTIC_DECISIONS.md#identity-authorizationdelegationgrant) |
| HidraPrincipal | [HMSR-102 reconciled rule](../domain/SEMANTIC_DECISIONS.md#identity-hidraprincipal) |
| LocalCredential | [HMSR-103 reconciled rule](../domain/SEMANTIC_DECISIONS.md#identity-localcredential) |
| LoginSession | [HMSR-104 reconciled rule](../domain/SEMANTIC_DECISIONS.md#identity-loginsession) |
| UserPermissionGrant | [HMSR-105 reconciled rule](../domain/SEMANTIC_DECISIONS.md#identity-userpermissiongrant) |
| UserRoleGrant | [HMSR-106 reconciled rule](../domain/SEMANTIC_DECISIONS.md#identity-userrolegrant) |
