/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaAlarmLifecycleEventRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for AlarmLifecycleEvent.
 *
 */
package dz.sh.hidra.modules.alarm.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.alarm.application.port.out.AlarmLifecycleEventRepositoryPort;
import dz.sh.hidra.modules.alarm.domain.model.AlarmLifecycleEvent;
import dz.sh.hidra.modules.alarm.infrastructure.persistence.mapper.AlarmPersistenceMapper;
import dz.sh.hidra.modules.alarm.infrastructure.persistence.repository.AlarmLifecycleEventJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for AlarmLifecycleEvent.
 */
@Component
public class JpaAlarmLifecycleEventRepositoryAdapter implements AlarmLifecycleEventRepositoryPort {

    private final AlarmLifecycleEventJpaRepository repository;

    public JpaAlarmLifecycleEventRepositoryAdapter(AlarmLifecycleEventJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "AlarmLifecycleEventJpaRepository must not be null.");
    }

    @Override
    public AlarmLifecycleEvent save(AlarmLifecycleEvent model) {
        return AlarmPersistenceMapper.toDomain(repository.save(AlarmPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<AlarmLifecycleEvent> findById(String id) {
        return repository.findById(id).map(AlarmPersistenceMapper::toDomain);
    }
}
