/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaTelemetryQuarantineRecordRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for TelemetryQuarantineRecord.
 *
 */
package dz.sh.hidra.modules.telemetry.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryQuarantineRecordRepositoryPort;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryQuarantineRecord;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.mapper.TelemetryPersistenceMapper;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.repository.TelemetryQuarantineRecordJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for TelemetryQuarantineRecord.
 */
@Component
public class JpaTelemetryQuarantineRecordRepositoryAdapter implements TelemetryQuarantineRecordRepositoryPort {

    private final TelemetryQuarantineRecordJpaRepository repository;

    public JpaTelemetryQuarantineRecordRepositoryAdapter(TelemetryQuarantineRecordJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "TelemetryQuarantineRecordJpaRepository must not be null.");
    }

    @Override
    public TelemetryQuarantineRecord save(TelemetryQuarantineRecord model) {
        return TelemetryPersistenceMapper.toDomain(
                repository.save(TelemetryPersistenceMapper.toEntity(model))
        );
    }

    @Override
    public Optional<TelemetryQuarantineRecord> findById(String id) {
        return repository.findById(id).map(TelemetryPersistenceMapper::toDomain);
    }
}
