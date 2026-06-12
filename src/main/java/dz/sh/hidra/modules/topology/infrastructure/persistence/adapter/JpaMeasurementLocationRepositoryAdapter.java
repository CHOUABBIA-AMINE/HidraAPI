/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaMeasurementLocationRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for MeasurementLocation.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.topology.application.port.out.MeasurementLocationRepositoryPort;
import dz.sh.hidra.modules.topology.domain.model.MeasurementLocation;
import dz.sh.hidra.modules.topology.infrastructure.persistence.mapper.TopologyPersistenceMapper;
import dz.sh.hidra.modules.topology.infrastructure.persistence.repository.MeasurementLocationJpaRepository;
import org.springframework.stereotype.Component;
import java.util.Objects;
import java.util.Optional;
@Component
public class JpaMeasurementLocationRepositoryAdapter implements MeasurementLocationRepositoryPort {
    private final MeasurementLocationJpaRepository repository;
    public JpaMeasurementLocationRepositoryAdapter(MeasurementLocationJpaRepository repository) { this.repository = Objects.requireNonNull(repository, "MeasurementLocationJpaRepository must not be null."); }
    public MeasurementLocation save(MeasurementLocation model) { return TopologyPersistenceMapper.toDomain(repository.save(TopologyPersistenceMapper.toEntity(model))); }
    public Optional<MeasurementLocation> findById(String id) { return repository.findById(id).map(TopologyPersistenceMapper::toDomain); }
}
