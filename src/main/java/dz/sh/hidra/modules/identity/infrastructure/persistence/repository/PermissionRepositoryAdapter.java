/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PermissionRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence.repository
 *
 * @Description : Persistence adapter implementing the identity PermissionRepository port.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence.repository;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.modules.identity.application.port.out.PermissionRepository;
import dz.sh.hidra.modules.identity.domain.model.Permission;
import dz.sh.hidra.modules.identity.domain.value.PermissionCode;
import dz.sh.hidra.modules.identity.domain.value.PermissionId;
import dz.sh.hidra.modules.identity.infrastructure.persistence.mapper.IdentityPersistenceMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Persistence adapter implementing the identity PermissionRepository port.
 *
 * <p>Business role: persists and loads identity permission catalog entries for
 * application use cases.</p>
 *
 * <p>Architecture role: infrastructure adapter from the application outbound
 * {@link PermissionRepository} port to Spring Data JPA.</p>
 *
 * <p>Validation responsibility: rejects null dependencies and arguments and delegates
 * domain rebuilding to {@link IdentityPersistenceMapper}.</p>
 *
 * <p>Usage: inject as the application {@link PermissionRepository} implementation.</p>
 */
@Repository
public class PermissionRepositoryAdapter implements PermissionRepository {

    private final PermissionJpaRepository permissionJpaRepository;
    private final IdentityPersistenceMapper mapper;

    public PermissionRepositoryAdapter(
            PermissionJpaRepository permissionJpaRepository,
            IdentityPersistenceMapper mapper
    ) {
        this.permissionJpaRepository = requireNonNull(permissionJpaRepository, "PermissionJpaRepository");
        this.mapper = requireNonNull(mapper, "IdentityPersistenceMapper");
    }

    @Override
    public Permission save(Permission permission) {
        Permission requiredPermission = requireNonNull(permission, "Permission");
        return mapper.toPermissionDomain(permissionJpaRepository.save(mapper.toPermissionEntity(requiredPermission)));
    }

    @Override
    public Optional<Permission> findById(PermissionId permissionId) {
        PermissionId requiredPermissionId = requireNonNull(permissionId, "PermissionId");
        return permissionJpaRepository.findById(requiredPermissionId.value())
                .map(mapper::toPermissionDomain);
    }

    @Override
    public Optional<Permission> findByCode(PermissionCode permissionCode) {
        PermissionCode requiredPermissionCode = requireNonNull(permissionCode, "PermissionCode");
        return permissionJpaRepository.findByCode(requiredPermissionCode.value())
                .map(mapper::toPermissionDomain);
    }

    @Override
    public List<Permission> findAll() {
        return permissionJpaRepository.findAll().stream()
                .map(mapper::toPermissionDomain)
                .toList();
    }

    @Override
    public boolean existsByCode(PermissionCode permissionCode) {
        PermissionCode requiredPermissionCode = requireNonNull(permissionCode, "PermissionCode");
        return permissionJpaRepository.existsByCode(requiredPermissionCode.value());
    }

    private static <T> T requireNonNull(T value, String fieldName) {
        if (value == null) {
            throw new InvalidValueObjectException(fieldName + " must not be null.");
        }
        return value;
    }
}
