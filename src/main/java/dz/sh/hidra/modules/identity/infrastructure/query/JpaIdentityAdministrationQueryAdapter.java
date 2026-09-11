/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaIdentityAdministrationQueryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.query
 *
 * @Description : Provides read-only JPA projections for identity administration and effective grants.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.query;

import dz.sh.hidra.modules.identity.application.port.in.IdentityAdministrationQueryUseCase;
import dz.sh.hidra.modules.identity.domain.value.GrantEffect;
import dz.sh.hidra.modules.identity.infrastructure.persistence.entity.PermissionJpaEntity;
import dz.sh.hidra.modules.identity.infrastructure.persistence.entity.RoleJpaEntity;
import dz.sh.hidra.modules.identity.infrastructure.persistence.entity.RolePermissionGrantJpaEntity;
import dz.sh.hidra.modules.identity.infrastructure.persistence.entity.UserJpaEntity;
import dz.sh.hidra.modules.identity.infrastructure.persistence.entity.UserPermissionGrantJpaEntity;
import dz.sh.hidra.modules.identity.infrastructure.persistence.entity.UserRoleGrantJpaEntity;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Read-only identity administration projection adapter.
 */
@Component
@Transactional(readOnly = true)
public class JpaIdentityAdministrationQueryAdapter implements IdentityAdministrationQueryUseCase {

    private final EntityManager entityManager;

    public JpaIdentityAdministrationQueryAdapter(EntityManager entityManager) {
        this.entityManager = Objects.requireNonNull(entityManager, "EntityManager must not be null.");
    }

    @Override
    public Page<UserView> users(String query, int page, int size) {
        List<UserView> results = entityManager
                .createQuery("select e from UserJpaEntity e order by e.username", UserJpaEntity.class)
                .getResultList().stream()
                .filter(entity -> matches(query, entity.username(), entity.emailAddress(), entity.displayName()))
                .map(this::userView)
                .toList();
        return page(results, page, size);
    }

    @Override
    public UserView user(String id) {
        UserJpaEntity entity = entityManager.find(UserJpaEntity.class, id);
        if (entity == null) {
            throw new NoSuchElementException("Unknown identity user: " + id);
        }
        return userView(entity);
    }

    @Override
    public Page<RoleView> roles(String query, int page, int size) {
        List<RoleView> results = entityManager
                .createQuery("select e from RoleJpaEntity e order by e.code", RoleJpaEntity.class)
                .getResultList().stream()
                .filter(entity -> matches(query, entity.code(), entity.nameFr(), entity.nameEn(), entity.nameAr()))
                .map(this::roleView)
                .toList();
        return page(results, page, size);
    }

    @Override
    public Page<PermissionView> permissions(String query, int page, int size) {
        List<PermissionView> results = entityManager
                .createQuery("select e from PermissionJpaEntity e order by e.code", PermissionJpaEntity.class)
                .getResultList().stream()
                .filter(entity -> matches(query, entity.code(), entity.permissionDomain(), entity.resourceType(), entity.action()))
                .map(this::permissionView)
                .toList();
        return page(results, page, size);
    }

    @Override
    public PrincipalView principal(String subject, List<String> authorities) {
        List<String> safeAuthorities = authorities == null ? List.of() : List.copyOf(authorities);
        UserJpaEntity user = entityManager
                .createQuery("select e from UserJpaEntity e where e.username = :subject or e.id = :subject", UserJpaEntity.class)
                .setParameter("subject", subject)
                .getResultStream()
                .findFirst()
                .orElse(null);
        if (user == null) {
            return new PrincipalView(subject, "AUTHENTICATION", null, null, null, null, safeAuthorities, List.of());
        }

        Map<String, String> permissionCodes = new LinkedHashMap<>();
        entityManager.createQuery("select e from PermissionJpaEntity e", PermissionJpaEntity.class)
                .getResultList()
                .forEach(permission -> permissionCodes.put(permission.id(), permission.code()));

        Instant now = Instant.now();
        Set<String> allow = new LinkedHashSet<>();
        Set<String> deny = new LinkedHashSet<>();

        entityManager.createQuery(
                        "select e from UserPermissionGrantJpaEntity e where e.userId = :userId",
                        UserPermissionGrantJpaEntity.class
                )
                .setParameter("userId", user.id())
                .getResultList().stream()
                .filter(grant -> active(grant.status(), grant.validFrom(), grant.validTo(), now))
                .forEach(grant -> apply(permissionCodes.get(grant.permissionId()), grant.effect(), allow, deny));

        List<String> roleIds = entityManager.createQuery(
                        "select e from UserRoleGrantJpaEntity e where e.userId = :userId",
                        UserRoleGrantJpaEntity.class
                )
                .setParameter("userId", user.id())
                .getResultList().stream()
                .filter(grant -> active(grant.status(), grant.validFrom(), grant.validTo(), now))
                .map(UserRoleGrantJpaEntity::roleId)
                .distinct()
                .toList();

        if (!roleIds.isEmpty()) {
            entityManager.createQuery(
                            "select e from RolePermissionGrantJpaEntity e where e.roleId in :roleIds",
                            RolePermissionGrantJpaEntity.class
                    )
                    .setParameter("roleIds", roleIds)
                    .getResultList().stream()
                    .filter(grant -> active(grant.status(), grant.validFrom(), grant.validTo(), now))
                    .forEach(grant -> apply(permissionCodes.get(grant.permissionId()), grant.effect(), allow, deny));
        }

        allow.removeAll(deny);
        return new PrincipalView(
                subject,
                "IDENTITY_USER",
                user.id(),
                user.username(),
                user.displayName(),
                user.employeeReferenceId(),
                safeAuthorities,
                allow.stream().sorted().toList()
        );
    }

    private UserView userView(UserJpaEntity entity) {
        return new UserView(
                entity.id(),
                entity.username(),
                entity.emailAddress(),
                entity.displayName(),
                name(entity.userType()),
                name(entity.status()),
                entity.employeeReferenceId()
        );
    }

    private RoleView roleView(RoleJpaEntity entity) {
        return new RoleView(
                entity.id(),
                entity.code(),
                entity.nameAr(),
                entity.nameFr(),
                entity.nameEn(),
                entity.description(),
                name(entity.roleType()),
                name(entity.status())
        );
    }

    private PermissionView permissionView(PermissionJpaEntity entity) {
        return new PermissionView(
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
                name(entity.status())
        );
    }

    private static void apply(String code, GrantEffect effect, Set<String> allow, Set<String> deny) {
        if (code == null || code.isBlank()) {
            return;
        }
        if ("DENY".equals(name(effect))) {
            deny.add(code);
        } else {
            allow.add(code);
        }
    }

    private static boolean active(Object status, Instant from, Instant to, Instant now) {
        return "ACTIVE".equals(name(status))
                && (from == null || !from.isAfter(now))
                && (to == null || to.isAfter(now));
    }

    private static String name(Object value) {
        return value == null ? null : value.toString();
    }

    private static boolean matches(String query, Object... values) {
        if (query == null || query.isBlank()) {
            return true;
        }
        String normalized = query.toLowerCase(Locale.ROOT);
        for (Object value : values) {
            if (value != null && value.toString().toLowerCase(Locale.ROOT).contains(normalized)) {
                return true;
            }
        }
        return false;
    }

    private static <T> Page<T> page(List<T> all, int requestedPage, int requestedSize) {
        int page = Math.max(0, requestedPage);
        int size = Math.min(200, Math.max(1, requestedSize));
        int from = Math.min(all.size(), page * size);
        int to = Math.min(all.size(), from + size);
        int totalPages = all.isEmpty() ? 0 : (all.size() + size - 1) / size;
        return new Page<>(List.copyOf(all.subList(from, to)), page, size, all.size(), totalPages, to < all.size());
    }
}
