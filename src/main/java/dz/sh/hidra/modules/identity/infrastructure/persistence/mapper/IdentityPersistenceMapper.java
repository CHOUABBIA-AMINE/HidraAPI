/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityPersistenceMapper
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Utility
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence.mapper
 *
 * @Description : Maps identity domain models to database-backed JPA entities.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence.mapper;

import dz.sh.hidra.modules.identity.domain.model.*;
import dz.sh.hidra.modules.identity.domain.value.AuthorizationScope;
import dz.sh.hidra.modules.identity.infrastructure.persistence.entity.*;

/**
 * Maps identity domain models to database-backed JPA entities.
 */
public final class IdentityPersistenceMapper {

    private IdentityPersistenceMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
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

}
