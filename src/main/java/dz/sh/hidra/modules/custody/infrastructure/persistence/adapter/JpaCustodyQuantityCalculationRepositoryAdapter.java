/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaCustodyQuantityCalculationRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for CustodyQuantityCalculation.
 *
 */
package dz.sh.hidra.modules.custody.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.custody.application.port.out.CustodyQuantityCalculationRepositoryPort;
import dz.sh.hidra.modules.custody.domain.model.CustodyQuantityCalculation;
import dz.sh.hidra.modules.custody.infrastructure.persistence.mapper.CustodyPersistenceMapper;
import dz.sh.hidra.modules.custody.infrastructure.persistence.repository.CustodyQuantityCalculationJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for CustodyQuantityCalculation.
 */
@Component
public class JpaCustodyQuantityCalculationRepositoryAdapter implements CustodyQuantityCalculationRepositoryPort {

    private final CustodyQuantityCalculationJpaRepository repository;

    public JpaCustodyQuantityCalculationRepositoryAdapter(CustodyQuantityCalculationJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "CustodyQuantityCalculationJpaRepository must not be null.");
    }

    @Override
    public CustodyQuantityCalculation save(CustodyQuantityCalculation model) {
        return CustodyPersistenceMapper.toDomain(repository.save(CustodyPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<CustodyQuantityCalculation> findById(String id) {
        return repository.findById(id).map(CustodyPersistenceMapper::toDomain);
    }
}
