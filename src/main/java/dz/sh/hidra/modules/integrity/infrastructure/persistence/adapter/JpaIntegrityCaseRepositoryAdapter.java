/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaIntegrityCaseRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for IntegrityCase.
 *
 */
package dz.sh.hidra.modules.integrity.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.integrity.application.port.out.IntegrityCaseRepositoryPort;
import dz.sh.hidra.modules.integrity.domain.model.IntegrityCase;
import dz.sh.hidra.modules.integrity.infrastructure.persistence.mapper.IntegrityPersistenceMapper;
import dz.sh.hidra.modules.integrity.infrastructure.persistence.repository.IntegrityCaseJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for IntegrityCase.
 */
@Component
public class JpaIntegrityCaseRepositoryAdapter implements IntegrityCaseRepositoryPort {

    private final IntegrityCaseJpaRepository repository;
    private final IntegrityCaseReferenceValidation references;

    public JpaIntegrityCaseRepositoryAdapter(IntegrityCaseJpaRepository repository, IntegrityCaseReferenceValidation references) {
        this.repository = Objects.requireNonNull(repository, "IntegrityCaseJpaRepository must not be null.");
        this.references=Objects.requireNonNull(references);
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public IntegrityCase save(IntegrityCase model) {
        Objects.requireNonNull(model);
        var previous=repository.findByIdForUpdate(model.id()).map(IntegrityPersistenceMapper::toDomain).orElse(null);
        var validated=references.validate(model,previous);
        return IntegrityPersistenceMapper.toDomain(repository.saveAndFlush(IntegrityPersistenceMapper.toEntity(validated)));
    }

    @Override
    public Optional<IntegrityCase> findById(String id) {
        return repository.findById(id).map(IntegrityPersistenceMapper::toDomain);
    }
}
