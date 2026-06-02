/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RoleRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence.repository
 *
 * @Description : Persistence adapter implementing the identity RoleRepository port.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence.repository;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.modules.identity.application.port.out.RoleRepository;
import dz.sh.hidra.modules.identity.domain.model.Role;
import dz.sh.hidra.modules.identity.domain.value.RoleCode;
import dz.sh.hidra.modules.identity.domain.value.RoleId;
import dz.sh.hidra.modules.identity.infrastructure.persistence.mapper.IdentityPersistenceMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Persistence adapter implementing the identity RoleRepository port.
 *
 * <p>Business role: persists and loads identity role aggregates for application use
 * cases.</p>
 *
 * <p>Architecture role: infrastructure adapter from the application outbound
 * {@link RoleRepository} port to Spring Data JPA.</p>
 *
 * <p>Validation responsibility: rejects null dependencies and arguments and delegates
 * domain rebuilding to {@link IdentityPersistenceMapper}.</p>
 *
 * <p>Usage: inject as the application {@link RoleRepository} implementation.</p>
 */
@Repository
public class RoleRepositoryAdapter implements RoleRepository {

    private final RoleJpaRepository roleJpaRepository;
    private final IdentityPersistenceMapper mapper;

    public RoleRepositoryAdapter(
            RoleJpaRepository roleJpaRepository,
            IdentityPersistenceMapper mapper
    ) {
        this.roleJpaRepository = requireNonNull(roleJpaRepository, "RoleJpaRepository");
        this.mapper = requireNonNull(mapper, "IdentityPersistenceMapper");
    }

    @Override
    public Role save(Role role) {
        Role requiredRole = requireNonNull(role, "Role");
        return mapper.toRoleDomain(roleJpaRepository.save(mapper.toRoleEntity(requiredRole)));
    }

    @Override
    public Optional<Role> findById(RoleId roleId) {
        RoleId requiredRoleId = requireNonNull(roleId, "RoleId");
        return roleJpaRepository.findById(requiredRoleId.value())
                .map(mapper::toRoleDomain);
    }

    @Override
    public Optional<Role> findByCode(RoleCode roleCode) {
        RoleCode requiredRoleCode = requireNonNull(roleCode, "RoleCode");
        return roleJpaRepository.findByCode(requiredRoleCode.value())
                .map(mapper::toRoleDomain);
    }

    @Override
    public List<Role> findAll() {
        return roleJpaRepository.findAll().stream()
                .map(mapper::toRoleDomain)
                .toList();
    }

    @Override
    public boolean existsByCode(RoleCode roleCode) {
        RoleCode requiredRoleCode = requireNonNull(roleCode, "RoleCode");
        return roleJpaRepository.existsByCode(requiredRoleCode.value());
    }

    private static <T> T requireNonNull(T value, String fieldName) {
        if (value == null) {
            throw new InvalidValueObjectException(fieldName + " must not be null.");
        }
        return value;
    }
}
