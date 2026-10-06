# Identity Module

## Status

CURRENT — canonical HPR-P2-004 module document.

## Verification Baseline

Source baseline: `f3c703048402f3dcf1a520aceea64e64c4861035`

Package root: `dz.sh.hidra.modules.identity`

This document describes source-visible current state. It does not promote legacy `docs/**` material to authority and does not substitute for the canonical API/database contracts created by later P2 tasks.

## Responsibility

Owns users, principals, roles, permissions, identity providers, local credentials, sessions, grants and authorization/authentication evidence.

The module has the current Hexagonal structure `api/`, `application/`, `domain/` and `infrastructure/`. Its private domain, application implementation and persistence internals remain owned by this module.

## Domain Model

Current domain model types:

- `AuthenticationEvent`
- `AuthorizationDecision`
- `AuthorizationDelegationGrant`
- `ExternalRoleMapping`
- `GroupRoleGrant`
- `HidraPrincipal`
- `IdentityProvider`
- `LocalCredential`
- `LoginSession`
- `Permission`
- `Role`
- `RolePermissionGrant`
- `User`
- `UserPermissionGrant`
- `UserRoleGrant`

Domain policies: `AuthorizationEvaluationRequest`

Domain services: `AuthorizationPolicyEvaluator`

Canonical semantic context: `../domain/DOMAIN_OWNERSHIP.md`.

## Application and API Surface

Current inbound/use-case ports:

- `AuthenticateDirectUserUseCase`
- `BootstrapLocalAdministratorUseCase`
- `CompleteAuthenticatedPrincipalUseCase`
- `CreateUserUseCase`
- `EvaluatePermissionUseCase`
- `IdentityAdministrationCommandUseCase`
- `IdentityAdministrationQueryUseCase`

Current application services:

- `AuthenticationCompletionApplicationService`
- `AuthenticationSessionLifecycleApplicationService`
- `DirectAuthenticationApplicationService`
- `IdentityAdministrationCommandApplicationService`
- `IdentityAuthorizationApplicationService`
- `IdentityUserApplicationService`
- `LocalAdministratorBootstrapApplicationService`
- `LocalAuthenticationOutcomeApplicationService`
- `ReportingAccessAuthorizationQueryService`

Current API/controller classes:

- `IdentityAdministrationCommandController`
- `IdentityAdministrationQueryController`
- `IdentityAuthenticationController`
- `IdentityController`
- `SpringIdentityController`

These class inventories establish implemented adapters/use-case surfaces. Exact HTTP paths, request/response schemas, authentication requirements and compatibility semantics are HPR-P2-005 scope.

## Persistence

Current JPA persistence entity count: **26**.

Persistence entities:

- `AttributeDefinitionJpaEntity`
- `AuthenticationEventJpaEntity`
- `AuthorizationDecisionJpaEntity`
- `AuthorizationDelegationGrantJpaEntity`
- `AuthorizationPolicyJpaEntity`
- `AuthorizationPolicyRuleJpaEntity`
- `AuthorizationPolicyVersionJpaEntity`
- `ExternalGroupMappingJpaEntity`
- `ExternalIdentityJpaEntity`
- `ExternalPermissionMappingJpaEntity`
- `ExternalRoleMappingJpaEntity`
- `GroupJpaEntity`
- `GroupRoleGrantJpaEntity`
- `IdentityProviderJpaEntity`
- `IdentitySynchronizationJobJpaEntity`
- `IdentitySynchronizationRecordJpaEntity`
- `LocalCredentialJpaEntity`
- `LoginSessionJpaEntity`
- `PermissionJpaEntity`
- `RoleJpaEntity`
- `RolePermissionGrantJpaEntity`
- `SubjectSecurityAttributeJpaEntity`
- `UserGroupMembershipJpaEntity`
- `UserJpaEntity`
- `UserPermissionGrantJpaEntity`
- `UserRoleGrantJpaEntity`

Current persistence repository-adapter classes:

- `IdentityPersistenceAdapter`
- `JpaAuthenticationEventRepositoryAdapter`
- `JpaAuthorizationDecisionRepositoryAdapter`
- `JpaAuthorizationDelegationGrantRepositoryAdapter`
- `JpaExternalRoleMappingRepositoryAdapter`
- `JpaGroupRoleGrantRepositoryAdapter`
- `JpaIdentityProviderRepositoryAdapter`
- `JpaLocalCredentialRepositoryAdapter`
- `JpaLoginSessionRepositoryAdapter`
- `JpaPermissionRepositoryAdapter`
- `JpaRolePermissionGrantRepositoryAdapter`
- `JpaRoleRepositoryAdapter`
- `JpaUserPermissionGrantRepositoryAdapter`
- `JpaUserRepositoryAdapter`
- `JpaUserRoleGrantRepositoryAdapter`

Table/schema ownership and the generated data dictionary are HPR-P2-006 scope; class presence is not used here to invent database constraints or retention policy.

## Cross-Module Boundary

Exported contracts owned by this module:

- consumer `reporting`: `ReportingAccessAuthorizationContract`

Current outbound application ports used to reach persistence or collaborating capabilities:

- `AccessTokenIssuerPort`
- `AuthenticationEventRepositoryPort`
- `AuthorizationDecisionRepositoryPort`
- `AuthorizationDelegationGrantRepositoryPort`
- `DirectAuthenticationPort`
- `ExternalRoleMappingRepositoryPort`
- `GroupRoleGrantRepositoryPort`
- `IdentityProviderRepositoryPort`
- `LdapCredentialVerificationPort`
- `LocalCredentialRepositoryPort`
- `LocalPasswordHashPort`
- `LoginSessionRepositoryPort`
- `PermissionRepositoryPort`
- `RolePermissionGrantRepositoryPort`
- `RoleRepositoryPort`
- `UserPermissionGrantRepositoryPort`
- `UserRepositoryPort`
- `UserRoleGrantRepositoryPort`

Cross-module collaboration must preserve the canonical architecture rule: no direct import of another module's private domain, infrastructure or non-exported application packages.

## Current-State Limits

- File/class presence documents implementation structure, not proof that every business workflow, external dependency or production integration is exercised.
- HPR-P2-004 does not resolve legacy HMR/HMSR semantic obligations; HPR-P2-007/008 remain the reconciliation/remediation authority.
- `agents`, `environment` and `otsecurity` are not current implemented module roots and are not implied by this document.
- Authentication/authorization capability is source-visible, but exact external production IdP/LDAP provider identity is runtime/environment evidence rather than module documentation.
