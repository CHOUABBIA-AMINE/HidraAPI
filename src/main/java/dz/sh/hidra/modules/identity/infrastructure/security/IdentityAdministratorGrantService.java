/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityAdministratorGrantService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-19
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.security
 *
 * @Description : Validates currently active, global Hidra administrator grants against Identity-owned persistence.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.security;

import dz.sh.hidra.modules.identity.domain.value.GrantStatus;
import dz.sh.hidra.modules.identity.domain.value.RoleStatus;
import dz.sh.hidra.modules.identity.domain.value.RoleType;
import dz.sh.hidra.modules.identity.domain.value.ScopeType;
import dz.sh.hidra.modules.identity.domain.value.UserStatus;
import dz.sh.hidra.modules.identity.infrastructure.persistence.entity.UserRoleGrantJpaEntity;
import dz.sh.hidra.modules.identity.infrastructure.persistence.repository.RoleJpaRepository;
import dz.sh.hidra.modules.identity.infrastructure.persistence.repository.UserJpaRepository;
import dz.sh.hidra.modules.identity.infrastructure.persistence.repository.UserRoleGrantJpaRepository;
import java.time.Instant;
import java.util.Objects;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class IdentityAdministratorGrantService {

    private static final String ADMIN_ROLE_CODE = "HIDRA_ADMIN";

    private final UserJpaRepository users;
    private final RoleJpaRepository roles;
    private final UserRoleGrantJpaRepository grants;

    public IdentityAdministratorGrantService(
            UserJpaRepository users,
            RoleJpaRepository roles,
            UserRoleGrantJpaRepository grants
    ) {
        this.users = Objects.requireNonNull(users);
        this.roles = Objects.requireNonNull(roles);
        this.grants = Objects.requireNonNull(grants);
    }

    @Transactional(readOnly = true)
    public boolean hasActiveGlobalAdministratorGrant(String userId) {
        if (userId == null || userId.isBlank()) {
            return false;
        }
        Instant now = Instant.now();
        boolean activeUser = users.findById(userId)
                .filter(user -> user.status() == UserStatus.ACTIVE)
                .filter(user -> user.lockedUntil() == null || !now.isBefore(user.lockedUntil()))
                .isPresent();
        if (!activeUser) {
            return false;
        }
        return roles.findFirstByCode(ADMIN_ROLE_CODE)
                .filter(role -> role.status() == RoleStatus.ACTIVE && role.roleType() == RoleType.ADMIN)
                .map(role -> grants.findByUserIdAndRoleIdAndStatus(userId, role.id(), GrantStatus.ACTIVE)
                        .stream().anyMatch(grant -> isValidGlobalGrant(grant, now)))
                .orElse(false);
    }

    private static boolean isValidGlobalGrant(UserRoleGrantJpaEntity grant, Instant now) {
        return grant.scopeType() == ScopeType.GLOBAL
                && grant.scopeReferenceId() == null
                && grant.revokedAt() == null
                && grant.validFrom() != null
                && !grant.validFrom().isAfter(now)
                && (grant.validTo() == null || grant.validTo().isAfter(now));
    }
}
