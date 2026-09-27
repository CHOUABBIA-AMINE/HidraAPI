/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaOperationalScopeRegistryRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-27
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.persistence.adapter
 *
 * @Description : JPA adapter for canonical operational-scope registry identities.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.organization.application.port.out.OperationalScopeRegistryRepositoryPort;
import dz.sh.hidra.modules.organization.domain.model.OperationalScope;
import dz.sh.hidra.modules.organization.domain.value.OperationalScopeType;
import dz.sh.hidra.modules.organization.infrastructure.persistence.entity.OperationalScopeJpaEntity;
import dz.sh.hidra.modules.organization.infrastructure.persistence.mapper.OperationalScopePersistenceMapper;
import dz.sh.hidra.modules.organization.infrastructure.persistence.repository.OperationalScopeJpaRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed registry adapter.
 *
 * <p>The adapter is idempotent for an already registered canonical key and retries
 * lookup after a uniqueness race. It does not perform target-owner authorization
 * or existence validation; callers must use the application validator first.</p>
 */
@Component
public class JpaOperationalScopeRegistryRepositoryAdapter implements OperationalScopeRegistryRepositoryPort {

    private final OperationalScopeJpaRepository repository;

    public JpaOperationalScopeRegistryRepositoryAdapter(OperationalScopeJpaRepository repository) {
        this.repository = Objects.requireNonNull(
                repository,
                "OperationalScopeJpaRepository must not be null."
        );
    }

    @Override
    public OperationalScope register(OperationalScopeType type, String targetId) {
        Objects.requireNonNull(type, "Operational scope type must not be null.");

        String normalizedTargetId = normalizeTargetId(type, targetId);

        Optional<OperationalScope> existing = findCanonical(type, normalizedTargetId);
        if (existing.isPresent()) {
            return existing.get();
        }

        try {
            OperationalScopeJpaEntity saved = repository.saveAndFlush(
                    OperationalScopePersistenceMapper.newEntity(type, normalizedTargetId)
            );
            return OperationalScopePersistenceMapper.toDomain(saved);
        } catch (DataIntegrityViolationException race) {
            return findCanonical(type, normalizedTargetId)
                    .orElseThrow(() -> race);
        }
    }

    @Override
    public Optional<OperationalScope> findById(Long scopeId) {
        if (scopeId == null || scopeId <= 0) {
            return Optional.empty();
        }
        return repository.findById(scopeId).map(OperationalScopePersistenceMapper::toDomain);
    }

    @Override
    public Optional<OperationalScope> findByTypeAndTargetId(
            OperationalScopeType type,
            String targetId
    ) {
        if (type == null || type == OperationalScopeType.GLOBAL || type == OperationalScopeType.CUSTOM) {
            return Optional.empty();
        }

        String normalizedTargetId = targetId == null || targetId.isBlank()
                ? null
                : targetId.trim();

        if (normalizedTargetId == null) {
            return Optional.empty();
        }

        return repository.findByScopeTypeAndTargetId(type, normalizedTargetId)
                .map(OperationalScopePersistenceMapper::toDomain);
    }

    @Override
    public Optional<OperationalScope> findGlobal() {
        return repository.findByScopeType(OperationalScopeType.GLOBAL)
                .map(OperationalScopePersistenceMapper::toDomain);
    }

    private Optional<OperationalScope> findCanonical(
            OperationalScopeType type,
            String targetId
    ) {
        if (type == OperationalScopeType.GLOBAL) {
            return findGlobal();
        }
        return findByTypeAndTargetId(type, targetId);
    }

    private static String normalizeTargetId(
            OperationalScopeType type,
            String targetId
    ) {
        String normalized = targetId == null || targetId.isBlank()
                ? null
                : targetId.trim();

        if (type == OperationalScopeType.GLOBAL) {
            if (normalized != null) {
                throw new IllegalArgumentException("GLOBAL scope must not reference a target ID.");
            }
            return null;
        }

        if (type == OperationalScopeType.CUSTOM) {
            throw new IllegalArgumentException(
                    "CUSTOM scope requires an approved namespace and owner contract."
            );
        }

        if (normalized == null) {
            throw new IllegalArgumentException("Entity-backed scope requires a target ID.");
        }

        return normalized;
    }
}
