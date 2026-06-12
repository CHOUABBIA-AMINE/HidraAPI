/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaComplianceObligationRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for ComplianceObligation.
 *
 */
package dz.sh.hidra.modules.hse.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.hse.application.port.out.ComplianceObligationRepositoryPort;
import dz.sh.hidra.modules.hse.domain.model.ComplianceObligation;
import dz.sh.hidra.modules.hse.infrastructure.persistence.mapper.HsePersistenceMapper;
import dz.sh.hidra.modules.hse.infrastructure.persistence.repository.ComplianceObligationJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for ComplianceObligation.
 */
@Component
public class JpaComplianceObligationRepositoryAdapter implements ComplianceObligationRepositoryPort {

    private final ComplianceObligationJpaRepository repository;

    public JpaComplianceObligationRepositoryAdapter(ComplianceObligationJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "ComplianceObligationJpaRepository must not be null.");
    }

    @Override
    public ComplianceObligation save(ComplianceObligation model) {
        return HsePersistenceMapper.toDomain(repository.save(HsePersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<ComplianceObligation> findById(String id) {
        return repository.findById(id).map(HsePersistenceMapper::toDomain);
    }
}
