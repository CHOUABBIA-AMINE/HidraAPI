/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaCustodyMeasurementPeriodRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for CustodyMeasurementPeriod.
 *
 */
package dz.sh.hidra.modules.custody.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.custody.application.port.out.CustodyMeasurementPeriodRepositoryPort;
import dz.sh.hidra.modules.custody.domain.model.CustodyMeasurementPeriod;
import dz.sh.hidra.modules.custody.infrastructure.persistence.mapper.CustodyPersistenceMapper;
import dz.sh.hidra.modules.custody.infrastructure.persistence.repository.CustodyMeasurementPeriodJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for CustodyMeasurementPeriod.
 */
@Component
public class JpaCustodyMeasurementPeriodRepositoryAdapter implements CustodyMeasurementPeriodRepositoryPort {

    private final CustodyMeasurementPeriodJpaRepository repository;

    public JpaCustodyMeasurementPeriodRepositoryAdapter(CustodyMeasurementPeriodJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "CustodyMeasurementPeriodJpaRepository must not be null.");
    }

    @Override
    public CustodyMeasurementPeriod save(CustodyMeasurementPeriod model) {
        return CustodyPersistenceMapper.toDomain(repository.save(CustodyPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<CustodyMeasurementPeriod> findById(String id) {
        return repository.findById(id).map(CustodyPersistenceMapper::toDomain);
    }
}
