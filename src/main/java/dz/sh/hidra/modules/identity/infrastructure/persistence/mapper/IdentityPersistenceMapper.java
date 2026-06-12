/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityPersistenceMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence.mapper
 *
 * @Description : Maps identity domain models to database-backed JPA entities.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence.mapper;

import dz.sh.hidra.modules.identity.domain.model.*;
import dz.sh.hidra.modules.identity.domain.value.*;
import dz.sh.hidra.modules.identity.infrastructure.persistence.entity.*;

/**
 * Maps identity domain models to database-backed JPA entities.
 */
public final class IdentityPersistenceMapper {

    private IdentityPersistenceMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    private static ScopeType scopeType(AuthorizationScope scope) {
        return scope == null ? null : scope.scopeType();
    }

    private static String scopeReferenceId(AuthorizationScope scope) {
        return scope == null ? null : scope.scopeReferenceId();
    }

    private static String scopeCodeSnapshot(AuthorizationScope scope) {
        return scope == null ? null : scope.scopeCodeSnapshot();
    }

    private static AuthorizationScope toAuthorizationScope(ScopeType scopeType, String scopeReferenceId, String scopeCodeSnapshot) {
        if (scopeType == null && scopeReferenceId == null && scopeCodeSnapshot == null) {
            return null;
        }
        return new AuthorizationScope(scopeType, scopeReferenceId, scopeCodeSnapshot);
    }


    public static UserJpaEntity toEntity(User model) {
        return new UserJpaEntity(
                    model.id(),
                    model.username(),
                    model.emailAddress(),
                    model.displayName(),
                    model.userType(),
                    model.status(),
                    model.employeeReferenceId(),
                    model.lastAuthenticatedAt(),
                    model.failedLoginCount(),
                    model.lockedUntil(),
                    model.createdAt(),
                    model.activatedAt(),
                    model.suspendedAt(),
                    model.disabledAt(),
                    model.updatedAt()
        );
    }

    public static User toDomain(UserJpaEntity entity) {
        return new User(
                    entity.id(),
                    entity.username(),
                    entity.emailAddress(),
                    entity.displayName(),
                    entity.userType(),
                    entity.status(),
                    entity.employeeReferenceId(),
                    entity.lastAuthenticatedAt(),
                    entity.failedLoginCount(),
                    entity.lockedUntil(),
                    entity.createdAt(),
                    entity.activatedAt(),
                    entity.suspendedAt(),
                    entity.disabledAt(),
                    entity.updatedAt()
        );
    }

    public static GroupJpaEntity toEntity(Group model) {
        return new GroupJpaEntity(
                    model.id(),
                    model.code(),
                    model.nameAr(),
                    model.nameFr(),
                    model.nameEn(),
                    model.description(),
                    model.groupType(),
                    model.sourceProviderId(),
                    model.status(),
                    model.createdAt(),
                    model.updatedAt()
        );
    }

    public static Group toDomain(GroupJpaEntity entity) {
        return new Group(
                    entity.id(),
                    entity.code(),
                    entity.nameAr(),
                    entity.nameFr(),
                    entity.nameEn(),
                    entity.description(),
                    entity.groupType(),
                    entity.sourceProviderId(),
                    entity.status(),
                    entity.createdAt(),
                    entity.updatedAt()
        );
    }

    public static RoleJpaEntity toEntity(Role model) {
        return new RoleJpaEntity(
                    model.id(),
                    model.code(),
                    model.nameAr(),
                    model.nameFr(),
                    model.nameEn(),
                    model.description(),
                    model.roleType(),
                    model.status(),
                    model.createdAt(),
                    model.updatedAt()
        );
    }

    public static Role toDomain(RoleJpaEntity entity) {
        return new Role(
                    entity.id(),
                    entity.code(),
                    entity.nameAr(),
                    entity.nameFr(),
                    entity.nameEn(),
                    entity.description(),
                    entity.roleType(),
                    entity.status(),
                    entity.createdAt(),
                    entity.updatedAt()
        );
    }

    public static PermissionJpaEntity toEntity(Permission model) {
        return new PermissionJpaEntity(
                    model.id(),
                    model.code(),
                    model.nameAr(),
                    model.nameFr(),
                    model.nameEn(),
                    model.description(),
                    model.permissionDomain(),
                    model.resourceType(),
                    model.action(),
                    model.sensitive(),
                    model.status(),
                    model.createdAt(),
                    model.updatedAt()
        );
    }

    public static Permission toDomain(PermissionJpaEntity entity) {
        return new Permission(
                    entity.id(),
                    entity.code(),
                    entity.nameAr(),
                    entity.nameFr(),
                    entity.nameEn(),
                    entity.description(),
                    entity.permissionDomain(),
                    entity.resourceType(),
                    entity.action(),
                    entity.sensitive(),
                    entity.status(),
                    entity.createdAt(),
                    entity.updatedAt()
        );
    }

    public static AuthorizationDecisionJpaEntity toEntity(AuthorizationDecision model) {
        return new AuthorizationDecisionJpaEntity(
                    model.id(),
                    model.userId(),
                    model.permissionCode(),
                    model.resourceType(),
                    model.resourceReferenceId(),
                    model.scope() == null ? null : model.scope().scopeType(),
                    model.scope() == null ? null : model.scope().scopeReferenceId(),
                    model.scope() == null ? null : model.scope().scopeCodeSnapshot(),
                    model.decision(),
                    model.reasonCode(),
                    model.reasonMessage(),
                    model.matchedGrantIds(),
                    model.matchedPolicyRuleIds(),
                    model.externalClaimsUsed(),
                    model.evaluatedAt(),
                    model.correlationId(),
                    model.requestId()
        );
    }

    public static AuthorizationDecision toDomain(AuthorizationDecisionJpaEntity entity) {
        return new AuthorizationDecision(
                    entity.id(),
                    entity.userId(),
                    entity.permissionCode(),
                    entity.resourceType(),
                    entity.resourceReferenceId(),
                    new AuthorizationScope(entity.scopeType(), entity.scopeReferenceId(), entity.scopeCodeSnapshot()),
                    entity.decision(),
                    entity.reasonCode(),
                    entity.reasonMessage(),
                    entity.matchedGrantIds(),
                    entity.matchedPolicyRuleIds(),
                    entity.externalClaimsUsed(),
                    entity.evaluatedAt(),
                    entity.correlationId(),
                    entity.requestId()
        );
    }

    public static IdentityProviderJpaEntity toEntity(IdentityProvider model) {
        return new IdentityProviderJpaEntity(
                    model.id(),
                    model.code(),
                    model.name(),
                    model.providerType(),
                    model.issuerUri(),
                    model.authorizationEndpoint(),
                    model.tokenEndpoint(),
                    model.jwksUri(),
                    model.directoryBaseDn(),
                    model.userSearchBase(),
                    model.groupSearchBase(),
                    model.usernameAttribute(),
                    model.emailAttribute(),
                    model.displayNameAttribute(),
                    model.externalIdAttribute(),
                    model.groupMembershipAttribute(),
                    model.syncEnabled(),
                    model.justInTimeProvisioningEnabled(),
                    model.status(),
                    model.metadata(),
                    model.secretReference(),
                    model.createdAt(),
                    model.updatedAt()
        );
    }

    public static IdentityProvider toDomain(IdentityProviderJpaEntity entity) {
        return new IdentityProvider(
                    entity.id(),
                    entity.code(),
                    entity.name(),
                    entity.providerType(),
                    entity.issuerUri(),
                    entity.authorizationEndpoint(),
                    entity.tokenEndpoint(),
                    entity.jwksUri(),
                    entity.directoryBaseDn(),
                    entity.userSearchBase(),
                    entity.groupSearchBase(),
                    entity.usernameAttribute(),
                    entity.emailAttribute(),
                    entity.displayNameAttribute(),
                    entity.externalIdAttribute(),
                    entity.groupMembershipAttribute(),
                    entity.syncEnabled(),
                    entity.justInTimeProvisioningEnabled(),
                    entity.status(),
                    entity.metadata(),
                    entity.secretReference(),
                    entity.createdAt(),
                    entity.updatedAt()
        );
    }

    public static AttributeDefinitionJpaEntity toEntity(AttributeDefinition model) {
        return new AttributeDefinitionJpaEntity(
                    model.id(),
                    model.code(),
                    model.name(),
                    model.attributeTarget(),
                    model.dataType(),
                    model.description(),
                    model.multiValued(),
                    model.sensitive(),
                    model.status(),
                    model.createdAt(),
                    model.updatedAt()
        );
    }

    public static AttributeDefinition toDomain(AttributeDefinitionJpaEntity entity) {
        return new AttributeDefinition(
                    entity.id(),
                    entity.code(),
                    entity.name(),
                    entity.attributeTarget(),
                    entity.dataType(),
                    entity.description(),
                    entity.multiValued(),
                    entity.sensitive(),
                    entity.status(),
                    entity.createdAt(),
                    entity.updatedAt()
        );
    }

    public static AuthenticationEventJpaEntity toEntity(AuthenticationEvent model) {
        return new AuthenticationEventJpaEntity(
                    model.id(),
                    model.userId(),
                    model.identityProviderId(),
                    model.externalIdentityId(),
                    model.externalSubject(),
                    model.eventType(),
                    model.protocol(),
                    model.clientIp(),
                    model.userAgent(),
                    model.failureReason(),
                    model.riskScore(),
                    model.occurredAt(),
                    model.correlationId()
        );
    }

    public static AuthenticationEvent toDomain(AuthenticationEventJpaEntity entity) {
        return new AuthenticationEvent(
                    entity.id(),
                    entity.userId(),
                    entity.identityProviderId(),
                    entity.externalIdentityId(),
                    entity.externalSubject(),
                    entity.eventType(),
                    entity.protocol(),
                    entity.clientIp(),
                    entity.userAgent(),
                    entity.failureReason(),
                    entity.riskScore(),
                    entity.occurredAt(),
                    entity.correlationId()
        );
    }

    public static AuthorizationDelegationGrantJpaEntity toEntity(AuthorizationDelegationGrant model) {
        return new AuthorizationDelegationGrantJpaEntity(
                    model.id(),
                    model.delegatorUserId(),
                    model.delegateUserId(),
                    model.permissionId(),
                    model.roleId(),
                    scopeType(model.scope()),
                    scopeReferenceId(model.scope()),
                    scopeCodeSnapshot(model.scope()),
                    model.approvedByWorkflowId(),
                    model.validFrom(),
                    model.validTo(),
                    model.status(),
                    model.createdAt(),
                    model.revokedAt()
        );
    }

    public static AuthorizationDelegationGrant toDomain(AuthorizationDelegationGrantJpaEntity entity) {
        return new AuthorizationDelegationGrant(
                    entity.id(),
                    entity.delegatorUserId(),
                    entity.delegateUserId(),
                    entity.permissionId(),
                    entity.roleId(),
                    toAuthorizationScope(entity.scopeType(), entity.scopeReferenceId(), entity.scopeCodeSnapshot()),
                    entity.approvedByWorkflowId(),
                    entity.validFrom(),
                    entity.validTo(),
                    entity.status(),
                    entity.createdAt(),
                    entity.revokedAt()
        );
    }

    public static AuthorizationPolicyJpaEntity toEntity(AuthorizationPolicy model) {
        return new AuthorizationPolicyJpaEntity(
                    model.id(),
                    model.code(),
                    model.name(),
                    model.description(),
                    model.policyDomain(),
                    model.status(),
                    model.createdAt(),
                    model.updatedAt()
        );
    }

    public static AuthorizationPolicy toDomain(AuthorizationPolicyJpaEntity entity) {
        return new AuthorizationPolicy(
                    entity.id(),
                    entity.code(),
                    entity.name(),
                    entity.description(),
                    entity.policyDomain(),
                    entity.status(),
                    entity.createdAt(),
                    entity.updatedAt()
        );
    }

    public static AuthorizationPolicyRuleJpaEntity toEntity(AuthorizationPolicyRule model) {
        return new AuthorizationPolicyRuleJpaEntity(
                    model.id(),
                    model.policyVersionId(),
                    model.ruleCode(),
                    model.effect(),
                    model.priority(),
                    model.subjectExpression(),
                    model.resourceExpression(),
                    model.actionExpression(),
                    model.contextExpression(),
                    model.obligationExpression(),
                    model.status(),
                    model.createdAt()
        );
    }

    public static AuthorizationPolicyRule toDomain(AuthorizationPolicyRuleJpaEntity entity) {
        return new AuthorizationPolicyRule(
                    entity.id(),
                    entity.policyVersionId(),
                    entity.ruleCode(),
                    entity.effect(),
                    entity.priority(),
                    entity.subjectExpression(),
                    entity.resourceExpression(),
                    entity.actionExpression(),
                    entity.contextExpression(),
                    entity.obligationExpression(),
                    entity.status(),
                    entity.createdAt()
        );
    }

    public static AuthorizationPolicyVersionJpaEntity toEntity(AuthorizationPolicyVersion model) {
        return new AuthorizationPolicyVersionJpaEntity(
                    model.id(),
                    model.policyId(),
                    model.versionNumber(),
                    model.status(),
                    model.effectiveFrom(),
                    model.effectiveTo(),
                    model.approvedByWorkflowId(),
                    model.createdAt(),
                    model.activatedAt()
        );
    }

    public static AuthorizationPolicyVersion toDomain(AuthorizationPolicyVersionJpaEntity entity) {
        return new AuthorizationPolicyVersion(
                    entity.id(),
                    entity.policyId(),
                    entity.versionNumber(),
                    entity.status(),
                    entity.effectiveFrom(),
                    entity.effectiveTo(),
                    entity.approvedByWorkflowId(),
                    entity.createdAt(),
                    entity.activatedAt()
        );
    }

    public static ExternalGroupMappingJpaEntity toEntity(ExternalGroupMapping model) {
        return new ExternalGroupMappingJpaEntity(
                    model.id(),
                    model.identityProviderId(),
                    model.groupId(),
                    model.externalGroupId(),
                    model.externalGroupName(),
                    model.externalGroupDn(),
                    model.claimName(),
                    model.mappingMode(),
                    model.autoCreateMembership(),
                    model.status(),
                    model.lastSyncedAt(),
                    model.createdAt(),
                    model.updatedAt()
        );
    }

    public static ExternalGroupMapping toDomain(ExternalGroupMappingJpaEntity entity) {
        return new ExternalGroupMapping(
                    entity.id(),
                    entity.identityProviderId(),
                    entity.groupId(),
                    entity.externalGroupId(),
                    entity.externalGroupName(),
                    entity.externalGroupDn(),
                    entity.claimName(),
                    entity.mappingMode(),
                    entity.autoCreateMembership(),
                    entity.status(),
                    entity.lastSyncedAt(),
                    entity.createdAt(),
                    entity.updatedAt()
        );
    }

    public static ExternalIdentityJpaEntity toEntity(ExternalIdentity model) {
        return new ExternalIdentityJpaEntity(
                    model.id(),
                    model.userId(),
                    model.identityProviderId(),
                    model.externalSubject(),
                    model.externalImmutableId(),
                    model.externalUsername(),
                    model.externalEmail(),
                    model.externalDisplayName(),
                    model.externalDistinguishedName(),
                    model.externalAttributesSnapshot(),
                    model.lastLoginAt(),
                    model.lastSyncedAt(),
                    model.status(),
                    model.createdAt(),
                    model.updatedAt()
        );
    }

    public static ExternalIdentity toDomain(ExternalIdentityJpaEntity entity) {
        return new ExternalIdentity(
                    entity.id(),
                    entity.userId(),
                    entity.identityProviderId(),
                    entity.externalSubject(),
                    entity.externalImmutableId(),
                    entity.externalUsername(),
                    entity.externalEmail(),
                    entity.externalDisplayName(),
                    entity.externalDistinguishedName(),
                    entity.externalAttributesSnapshot(),
                    entity.lastLoginAt(),
                    entity.lastSyncedAt(),
                    entity.status(),
                    entity.createdAt(),
                    entity.updatedAt()
        );
    }

    public static ExternalPermissionMappingJpaEntity toEntity(ExternalPermissionMapping model) {
        return new ExternalPermissionMappingJpaEntity(
                    model.id(),
                    model.identityProviderId(),
                    model.permissionId(),
                    model.externalPermissionCode(),
                    model.claimName(),
                    model.mappingMode(),
                    model.effect(),
                    model.status(),
                    model.createdAt(),
                    model.updatedAt()
        );
    }

    public static ExternalPermissionMapping toDomain(ExternalPermissionMappingJpaEntity entity) {
        return new ExternalPermissionMapping(
                    entity.id(),
                    entity.identityProviderId(),
                    entity.permissionId(),
                    entity.externalPermissionCode(),
                    entity.claimName(),
                    entity.mappingMode(),
                    entity.effect(),
                    entity.status(),
                    entity.createdAt(),
                    entity.updatedAt()
        );
    }

    public static ExternalRoleMappingJpaEntity toEntity(ExternalRoleMapping model) {
        return new ExternalRoleMappingJpaEntity(
                    model.id(),
                    model.identityProviderId(),
                    model.roleId(),
                    model.externalRoleCode(),
                    model.claimName(),
                    model.mappingMode(),
                    scopeType(model.scope()),
                    scopeReferenceId(model.scope()),
                    scopeCodeSnapshot(model.scope()),
                    model.status(),
                    model.createdAt(),
                    model.updatedAt()
        );
    }

    public static ExternalRoleMapping toDomain(ExternalRoleMappingJpaEntity entity) {
        return new ExternalRoleMapping(
                    entity.id(),
                    entity.identityProviderId(),
                    entity.roleId(),
                    entity.externalRoleCode(),
                    entity.claimName(),
                    entity.mappingMode(),
                    toAuthorizationScope(entity.scopeType(), entity.scopeReferenceId(), entity.scopeCodeSnapshot()),
                    entity.status(),
                    entity.createdAt(),
                    entity.updatedAt()
        );
    }

    public static GroupRoleGrantJpaEntity toEntity(GroupRoleGrant model) {
        return new GroupRoleGrantJpaEntity(
                    model.id(),
                    model.groupId(),
                    model.roleId(),
                    scopeType(model.scope()),
                    scopeReferenceId(model.scope()),
                    scopeCodeSnapshot(model.scope()),
                    model.grantReason(),
                    model.approvedByWorkflowId(),
                    model.validFrom(),
                    model.validTo(),
                    model.status(),
                    model.createdAt()
        );
    }

    public static GroupRoleGrant toDomain(GroupRoleGrantJpaEntity entity) {
        return new GroupRoleGrant(
                    entity.id(),
                    entity.groupId(),
                    entity.roleId(),
                    toAuthorizationScope(entity.scopeType(), entity.scopeReferenceId(), entity.scopeCodeSnapshot()),
                    entity.grantReason(),
                    entity.approvedByWorkflowId(),
                    entity.validFrom(),
                    entity.validTo(),
                    entity.status(),
                    entity.createdAt()
        );
    }

    public static IdentitySynchronizationJobJpaEntity toEntity(IdentitySynchronizationJob model) {
        return new IdentitySynchronizationJobJpaEntity(
                    model.id(),
                    model.identityProviderId(),
                    model.syncType(),
                    model.triggerType(),
                    model.startedAt(),
                    model.completedAt(),
                    model.status(),
                    model.usersCreated(),
                    model.usersUpdated(),
                    model.usersDisabled(),
                    model.groupsCreated(),
                    model.groupsUpdated(),
                    model.membershipsUpdated(),
                    model.errorMessage(),
                    model.correlationId()
        );
    }

    public static IdentitySynchronizationJob toDomain(IdentitySynchronizationJobJpaEntity entity) {
        return new IdentitySynchronizationJob(
                    entity.id(),
                    entity.identityProviderId(),
                    entity.syncType(),
                    entity.triggerType(),
                    entity.startedAt(),
                    entity.completedAt(),
                    entity.status(),
                    entity.usersCreated(),
                    entity.usersUpdated(),
                    entity.usersDisabled(),
                    entity.groupsCreated(),
                    entity.groupsUpdated(),
                    entity.membershipsUpdated(),
                    entity.errorMessage(),
                    entity.correlationId()
        );
    }

    public static IdentitySynchronizationRecordJpaEntity toEntity(IdentitySynchronizationRecord model) {
        return new IdentitySynchronizationRecordJpaEntity(
                    model.id(),
                    model.jobId(),
                    model.recordType(),
                    model.externalReference(),
                    model.localReferenceId(),
                    model.operation(),
                    model.status(),
                    model.message(),
                    model.occurredAt()
        );
    }

    public static IdentitySynchronizationRecord toDomain(IdentitySynchronizationRecordJpaEntity entity) {
        return new IdentitySynchronizationRecord(
                    entity.id(),
                    entity.jobId(),
                    entity.recordType(),
                    entity.externalReference(),
                    entity.localReferenceId(),
                    entity.operation(),
                    entity.status(),
                    entity.message(),
                    entity.occurredAt()
        );
    }

    public static LoginSessionJpaEntity toEntity(LoginSession model) {
        return new LoginSessionJpaEntity(
                    model.id(),
                    model.userId(),
                    model.identityProviderId(),
                    model.externalIdentityId(),
                    model.startedAt(),
                    model.lastSeenAt(),
                    model.expiresAt(),
                    model.clientIp(),
                    model.userAgent(),
                    model.status(),
                    model.correlationId()
        );
    }

    public static LoginSession toDomain(LoginSessionJpaEntity entity) {
        return new LoginSession(
                    entity.id(),
                    entity.userId(),
                    entity.identityProviderId(),
                    entity.externalIdentityId(),
                    entity.startedAt(),
                    entity.lastSeenAt(),
                    entity.expiresAt(),
                    entity.clientIp(),
                    entity.userAgent(),
                    entity.status(),
                    entity.correlationId()
        );
    }

    public static RolePermissionGrantJpaEntity toEntity(RolePermissionGrant model) {
        return new RolePermissionGrantJpaEntity(
                    model.id(),
                    model.roleId(),
                    model.permissionId(),
                    model.effect(),
                    model.conditionExpression(),
                    model.validFrom(),
                    model.validTo(),
                    model.status(),
                    model.createdAt()
        );
    }

    public static RolePermissionGrant toDomain(RolePermissionGrantJpaEntity entity) {
        return new RolePermissionGrant(
                    entity.id(),
                    entity.roleId(),
                    entity.permissionId(),
                    entity.effect(),
                    entity.conditionExpression(),
                    entity.validFrom(),
                    entity.validTo(),
                    entity.status(),
                    entity.createdAt()
        );
    }

    public static SubjectSecurityAttributeJpaEntity toEntity(SubjectSecurityAttribute model) {
        return new SubjectSecurityAttributeJpaEntity(
                    model.id(),
                    model.subjectType(),
                    model.subjectId(),
                    model.attributeDefinitionId(),
                    model.attributeValue(),
                    model.sourceProviderId(),
                    model.validFrom(),
                    model.validTo(),
                    model.status(),
                    model.createdAt(),
                    model.updatedAt()
        );
    }

    public static SubjectSecurityAttribute toDomain(SubjectSecurityAttributeJpaEntity entity) {
        return new SubjectSecurityAttribute(
                    entity.id(),
                    entity.subjectType(),
                    entity.subjectId(),
                    entity.attributeDefinitionId(),
                    entity.attributeValue(),
                    entity.sourceProviderId(),
                    entity.validFrom(),
                    entity.validTo(),
                    entity.status(),
                    entity.createdAt(),
                    entity.updatedAt()
        );
    }

    public static UserGroupMembershipJpaEntity toEntity(UserGroupMembership model) {
        return new UserGroupMembershipJpaEntity(
                    model.id(),
                    model.userId(),
                    model.groupId(),
                    model.membershipType(),
                    model.sourceProviderId(),
                    model.sourceMappingId(),
                    model.validFrom(),
                    model.validTo(),
                    model.status(),
                    model.createdAt(),
                    model.updatedAt()
        );
    }

    public static UserGroupMembership toDomain(UserGroupMembershipJpaEntity entity) {
        return new UserGroupMembership(
                    entity.id(),
                    entity.userId(),
                    entity.groupId(),
                    entity.membershipType(),
                    entity.sourceProviderId(),
                    entity.sourceMappingId(),
                    entity.validFrom(),
                    entity.validTo(),
                    entity.status(),
                    entity.createdAt(),
                    entity.updatedAt()
        );
    }

    public static UserPermissionGrantJpaEntity toEntity(UserPermissionGrant model) {
        return new UserPermissionGrantJpaEntity(
                    model.id(),
                    model.userId(),
                    model.permissionId(),
                    model.effect(),
                    scopeType(model.scope()),
                    scopeReferenceId(model.scope()),
                    scopeCodeSnapshot(model.scope()),
                    model.grantReason(),
                    model.approvedByWorkflowId(),
                    model.emergencyAccess(),
                    model.validFrom(),
                    model.validTo(),
                    model.status(),
                    model.createdAt(),
                    model.revokedAt()
        );
    }

    public static UserPermissionGrant toDomain(UserPermissionGrantJpaEntity entity) {
        return new UserPermissionGrant(
                    entity.id(),
                    entity.userId(),
                    entity.permissionId(),
                    entity.effect(),
                    toAuthorizationScope(entity.scopeType(), entity.scopeReferenceId(), entity.scopeCodeSnapshot()),
                    entity.grantReason(),
                    entity.approvedByWorkflowId(),
                    entity.emergencyAccess(),
                    entity.validFrom(),
                    entity.validTo(),
                    entity.status(),
                    entity.createdAt(),
                    entity.revokedAt()
        );
    }

    public static UserRoleGrantJpaEntity toEntity(UserRoleGrant model) {
        return new UserRoleGrantJpaEntity(
                    model.id(),
                    model.userId(),
                    model.roleId(),
                    scopeType(model.scope()),
                    scopeReferenceId(model.scope()),
                    scopeCodeSnapshot(model.scope()),
                    model.grantReason(),
                    model.approvedByWorkflowId(),
                    model.validFrom(),
                    model.validTo(),
                    model.status(),
                    model.createdAt(),
                    model.revokedAt(),
                    model.revokedReason()
        );
    }

    public static UserRoleGrant toDomain(UserRoleGrantJpaEntity entity) {
        return new UserRoleGrant(
                    entity.id(),
                    entity.userId(),
                    entity.roleId(),
                    toAuthorizationScope(entity.scopeType(), entity.scopeReferenceId(), entity.scopeCodeSnapshot()),
                    entity.grantReason(),
                    entity.approvedByWorkflowId(),
                    entity.validFrom(),
                    entity.validTo(),
                    entity.status(),
                    entity.createdAt(),
                    entity.revokedAt(),
                    entity.revokedReason()
        );
    }

}
