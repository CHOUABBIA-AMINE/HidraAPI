/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityPersistenceMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-06
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence.mapper
 *
 * @Description : Maps identity domain models to and from persistence entities.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence.mapper;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.modules.identity.domain.model.Permission;
import dz.sh.hidra.modules.identity.domain.model.Role;
import dz.sh.hidra.modules.identity.domain.model.RolePermissionAssignment;
import dz.sh.hidra.modules.identity.domain.model.User;
import dz.sh.hidra.modules.identity.domain.model.UserRoleAssignment;
import dz.sh.hidra.modules.identity.domain.value.EmailAddress;
import dz.sh.hidra.modules.identity.domain.value.EmployeeReference;
import dz.sh.hidra.modules.identity.domain.value.PermissionCode;
import dz.sh.hidra.modules.identity.domain.value.PermissionId;
import dz.sh.hidra.modules.identity.domain.value.PermissionName;
import dz.sh.hidra.modules.identity.domain.value.RoleCode;
import dz.sh.hidra.modules.identity.domain.value.RoleId;
import dz.sh.hidra.modules.identity.domain.value.RoleName;
import dz.sh.hidra.modules.identity.domain.value.RoleStatus;
import dz.sh.hidra.modules.identity.domain.value.UserId;
import dz.sh.hidra.modules.identity.domain.value.UserStatus;
import dz.sh.hidra.modules.identity.domain.value.Username;
import dz.sh.hidra.modules.identity.infrastructure.persistence.entity.PermissionJpaEntity;
import dz.sh.hidra.modules.identity.infrastructure.persistence.entity.RoleJpaEntity;
import dz.sh.hidra.modules.identity.infrastructure.persistence.entity.RolePermissionJpaEntity;
import dz.sh.hidra.modules.identity.infrastructure.persistence.entity.UserJpaEntity;
import dz.sh.hidra.modules.identity.infrastructure.persistence.entity.UserRoleJpaEntity;

import java.util.List;

/**
 * Maps identity domain models to and from persistence entities.
 *
 * <p>Business role: preserves identity user, role, permission, and assignment state when
 * crossing the persistence boundary.</p>
 *
 * <p>Architecture role: infrastructure mapper between identity domain models and JPA
 * entities. It is the only component in this step that should know both shapes.</p>
 *
 * <p>Validation responsibility: rejects null source objects and delegates value
 * validation to domain value objects while rebuilding aggregates.</p>
 *
 * <p>Usage: inject into identity persistence repository adapters.</p>
 */
public final class IdentityPersistenceMapper {

    public UserJpaEntity toUserEntity(User user) {
        User requiredUser = requireNonNull(user, "User");

        List<UserRoleJpaEntity> roleAssignments = requiredUser.roleAssignments().stream()
                .map(this::toUserRoleEntity)
                .toList();

        String employeeReference = requiredUser.employeeReference() == null
                ? null
                : requiredUser.employeeReference().value();

        return UserJpaEntity.of(
                requiredUser.id().value(),
                requiredUser.username().value(),
                requiredUser.emailAddress().value(),
                requiredUser.status().name(),
                employeeReference,
                roleAssignments
        );
    }

    public User toUserDomain(UserJpaEntity entity) {
        UserJpaEntity requiredEntity = requireNonNull(entity, "UserJpaEntity");

        List<UserRoleAssignment> roleAssignments = requiredEntity.getRoleAssignments().stream()
                .map(assignment -> toUserRoleAssignment(requiredEntity.getId(), assignment))
                .toList();

        EmployeeReference employeeReference = requiredEntity.getEmployeeReference() == null
                ? null
                : EmployeeReference.of(requiredEntity.getEmployeeReference());

        return User.rehydrate(
                UserId.of(requiredEntity.getId()),
                Username.of(requiredEntity.getUsername()),
                EmailAddress.of(requiredEntity.getEmailAddress()),
                UserStatus.from(requiredEntity.getStatus()),
                employeeReference,
                roleAssignments
        );
    }

    public RoleJpaEntity toRoleEntity(Role role) {
        Role requiredRole = requireNonNull(role, "Role");

        List<RolePermissionJpaEntity> permissionAssignments = requiredRole.permissionAssignments().stream()
                .map(this::toRolePermissionEntity)
                .toList();

        return RoleJpaEntity.of(
                requiredRole.id().value(),
                requiredRole.code().value(),
                requiredRole.name().nameAr(),
                requiredRole.name().nameFr(),
                requiredRole.name().nameEn(),
                requiredRole.status().name(),
                permissionAssignments
        );
    }

    public Role toRoleDomain(RoleJpaEntity entity) {
        RoleJpaEntity requiredEntity = requireNonNull(entity, "RoleJpaEntity");

        List<RolePermissionAssignment> permissionAssignments = requiredEntity.getPermissionAssignments().stream()
                .map(assignment -> toRolePermissionAssignment(requiredEntity.getId(), assignment))
                .toList();

        return Role.rehydrate(
                RoleId.of(requiredEntity.getId()),
                RoleCode.of(requiredEntity.getCode()),
                RoleName.of(requiredEntity.getNameAr(), requiredEntity.getNameFr(), requiredEntity.getNameEn()),
                RoleStatus.from(requiredEntity.getStatus()),
                permissionAssignments
        );
    }

    public PermissionJpaEntity toPermissionEntity(Permission permission) {
        Permission requiredPermission = requireNonNull(permission, "Permission");

        return PermissionJpaEntity.of(
                requiredPermission.id().value(),
                requiredPermission.code().value(),
                requiredPermission.name().value(),
                requiredPermission.description()
        );
    }

    public Permission toPermissionDomain(PermissionJpaEntity entity) {
        PermissionJpaEntity requiredEntity = requireNonNull(entity, "PermissionJpaEntity");

        return Permission.create(
                PermissionId.of(requiredEntity.getId()),
                PermissionCode.of(requiredEntity.getCode()),
                PermissionName.of(requiredEntity.getName()),
                requiredEntity.getDescription()
        );
    }

    private UserRoleJpaEntity toUserRoleEntity(UserRoleAssignment assignment) {
        UserRoleAssignment requiredAssignment = requireNonNull(assignment, "UserRoleAssignment");

        return UserRoleJpaEntity.of(
                requiredAssignment.roleId().value(),
                requiredAssignment.roleCode().value(),
                requiredAssignment.assignedAt()
        );
    }

    private UserRoleAssignment toUserRoleAssignment(String userId, UserRoleJpaEntity entity) {
        UserRoleJpaEntity requiredEntity = requireNonNull(entity, "UserRoleJpaEntity");

        return UserRoleAssignment.assign(
                UserId.of(userId),
                RoleId.of(requiredEntity.getRoleId()),
                RoleCode.of(requiredEntity.getRoleCode()),
                requiredEntity.getAssignedAt()
        );
    }

    private RolePermissionJpaEntity toRolePermissionEntity(RolePermissionAssignment assignment) {
        RolePermissionAssignment requiredAssignment = requireNonNull(assignment, "RolePermissionAssignment");

        return RolePermissionJpaEntity.of(
                requiredAssignment.permissionId().value(),
                requiredAssignment.permissionCode().value(),
                requiredAssignment.assignedAt()
        );
    }

    private RolePermissionAssignment toRolePermissionAssignment(String roleId, RolePermissionJpaEntity entity) {
        RolePermissionJpaEntity requiredEntity = requireNonNull(entity, "RolePermissionJpaEntity");

        return RolePermissionAssignment.assign(
                RoleId.of(roleId),
                PermissionId.of(requiredEntity.getPermissionId()),
                PermissionCode.of(requiredEntity.getPermissionCode()),
                requiredEntity.getAssignedAt()
        );
    }

    private static <T> T requireNonNull(T value, String fieldName) {
        if (value == null) {
            throw new InvalidValueObjectException(fieldName + " must not be null.");
        }
        return value;
    }
}
