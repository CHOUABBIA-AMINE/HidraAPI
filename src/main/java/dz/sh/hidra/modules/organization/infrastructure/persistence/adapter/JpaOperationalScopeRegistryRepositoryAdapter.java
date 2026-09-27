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
import dz.sh.hidra.modules.organization.domain.value.OperationalScopeReference;
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
 * <p>Business role: idempotently turns a validated owner reference into the separate
 * generated registry identity used by responsibility assignments.</p>
 *
 * <p>Architecture role: infrastructure implementation of the registry repository port.
 * It does not perform owner authorization or resolution.</p>
 *
 * <p>Validation: {@link OperationalScopeReference} has already enforced local shape;
 * owner validation happens before this adapter. Database uniqueness is the final
 * concurrency guard.</p>
 *
 * <p>Usage: registration receives one canonical reference. Query methods may still
 * decompose registry keys for persistence lookup.</p>
 */
@Component
public class JpaOperationalScopeRegistryRepositoryAdapter
        implements OperationalScopeRegistryRepositoryPort {

    private final OperationalScopeJpaRepository repository;

    public JpaOperationalScopeRegistryRepositoryAdapter(
            OperationalScopeJpaRepository repository
    ) {
        this.repository = Objects.requireNonNull(
                repository,
                "OperationalScopeJpaRepository must not be null."
        );
    }

    @Override
    public OperationalScope register(OperationalScopeReference reference) {
        Objects.requireNonNull(
                reference,
                "Operational scope reference must not be null."
        );

        Optional<OperationalScope> existing =
                findCanonical(reference.type(), reference.targetId());
        if (existing.isPresent()) {
            return existing.get();
        }

        try {
            OperationalScopeJpaEntity saved = repository.saveAndFlush(
                    OperationalScopePersistenceMapper.newEntity(
                            reference.type(),
                            reference.targetId()
                    )
            );
            return OperationalScopePersistenceMapper.toDomain(saved);
        } catch (DataIntegrityViolationException race) {
            return findCanonical(reference.type(), reference.targetId())
                    .orElseThrow(() -> race);
        }
    }

    @Override
    public Optional<OperationalScope> findById(Long scopeId) {
        if (scopeId == null || scopeId <= 0) {
            return Optional.empty();
        }
        return repository.findById(scopeId)
                .map(OperationalScopePersistenceMapper::toDomain);
    }

    @Override
    public Optional<OperationalScope> findByTypeAndTargetId(
            OperationalScopeType type,
            String targetId
    ) {
        if (type == null
                || type == OperationalScopeType.GLOBAL
                || type == OperationalScopeType.CUSTOM) {
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
}
