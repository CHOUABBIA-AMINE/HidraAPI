/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaAlarmShelvingRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for AlarmShelving.
 *
 */
package dz.sh.hidra.modules.alarm.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.alarm.application.port.out.AlarmShelvingRepositoryPort;
import dz.sh.hidra.modules.alarm.domain.model.AlarmShelving;
import dz.sh.hidra.modules.alarm.infrastructure.persistence.mapper.AlarmPersistenceMapper;
import dz.sh.hidra.modules.alarm.infrastructure.persistence.repository.AlarmShelvingJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for AlarmShelving.
 */
@Component
public class JpaAlarmShelvingRepositoryAdapter implements AlarmShelvingRepositoryPort {

    private final AlarmShelvingJpaRepository repository;

    public JpaAlarmShelvingRepositoryAdapter(AlarmShelvingJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "AlarmShelvingJpaRepository must not be null.");
    }

    @Override
    public AlarmShelving save(AlarmShelving model) {
        return AlarmPersistenceMapper.toDomain(repository.save(AlarmPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<AlarmShelving> findById(String id) {
        return repository.findById(id).map(AlarmPersistenceMapper::toDomain);
    }
}
