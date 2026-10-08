/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaAlarmLifecycleEventRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.infrastructure.persistence.adapter
 *
 * @Description : Enforces Alarm-owned transactional lifecycle evidence and integrity.
 *
 */
package dz.sh.hidra.modules.alarm.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.alarm.application.port.out.AlarmLifecycleEventRepositoryPort;
import dz.sh.hidra.modules.alarm.domain.model.AlarmLifecycleEvent;
import dz.sh.hidra.modules.alarm.infrastructure.persistence.mapper.AlarmPersistenceMapper;
import dz.sh.hidra.modules.alarm.infrastructure.persistence.repository.AlarmLifecycleEventJpaRepository;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class JpaAlarmLifecycleEventRepositoryAdapter implements AlarmLifecycleEventRepositoryPort {
    private final AlarmLifecycleEventJpaRepository events;
    public JpaAlarmLifecycleEventRepositoryAdapter(AlarmLifecycleEventJpaRepository events) {this.events = Objects.requireNonNull(events);}
    @Override @Transactional
    public AlarmLifecycleEvent append(AlarmLifecycleEvent event) {
        // Never merge an existing event: timeline evidence is append-only.
        if (events.existsById(event.id())) throw new IllegalStateException("Lifecycle event already exists: " + event.id());
        return AlarmPersistenceMapper.toDomain(events.saveAndFlush(AlarmPersistenceMapper.toEntity(event)));
    }
    @Override public Optional<AlarmLifecycleEvent> findById(String id) {
        return events.findById(id).map(AlarmPersistenceMapper::toDomain);
    }
}
