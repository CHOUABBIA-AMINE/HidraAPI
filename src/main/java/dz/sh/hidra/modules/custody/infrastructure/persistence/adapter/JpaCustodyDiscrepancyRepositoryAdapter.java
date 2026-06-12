/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaCustodyDiscrepancyRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for CustodyDiscrepancy.
 *
 */
package dz.sh.hidra.modules.custody.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.custody.application.port.out.CustodyDiscrepancyRepositoryPort;
import dz.sh.hidra.modules.custody.domain.model.CustodyDiscrepancy;
import dz.sh.hidra.modules.custody.infrastructure.persistence.mapper.CustodyPersistenceMapper;
import dz.sh.hidra.modules.custody.infrastructure.persistence.repository.CustodyDiscrepancyJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for CustodyDiscrepancy.
 */
@Component
public class JpaCustodyDiscrepancyRepositoryAdapter implements CustodyDiscrepancyRepositoryPort {

    private final CustodyDiscrepancyJpaRepository repository;

    public JpaCustodyDiscrepancyRepositoryAdapter(CustodyDiscrepancyJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "CustodyDiscrepancyJpaRepository must not be null.");
    }

    @Override
    public CustodyDiscrepancy save(CustodyDiscrepancy model) {
        return CustodyPersistenceMapper.toDomain(repository.save(CustodyPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<CustodyDiscrepancy> findById(String id) {
        return repository.findById(id).map(CustodyPersistenceMapper::toDomain);
    }
}
