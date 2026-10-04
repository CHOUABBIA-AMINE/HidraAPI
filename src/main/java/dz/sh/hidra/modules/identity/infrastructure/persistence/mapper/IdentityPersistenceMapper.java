/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityPersistenceMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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
                    entity.storedPermissionId(),
                    entity.effect(),
                    entity.conditionExpression(),
                    entity.validFrom(),
                    entity.validTo(),
                    entity.status(),
                    entity.createdAt()
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
                    entity.storedPermissionId(),
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
