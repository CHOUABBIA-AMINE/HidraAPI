/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaAlarmAcknowledgementRepositoryAdapter
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

import dz.sh.hidra.modules.alarm.application.port.out.*;
import dz.sh.hidra.modules.alarm.domain.model.*;
import dz.sh.hidra.modules.alarm.domain.service.AlarmLifecycleGuard;
import dz.sh.hidra.modules.alarm.domain.value.AlarmLifecycleEventType;
import dz.sh.hidra.modules.alarm.infrastructure.persistence.mapper.AlarmPersistenceMapper;
import dz.sh.hidra.modules.alarm.infrastructure.persistence.repository.AlarmAcknowledgementJpaRepository;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class JpaAlarmAcknowledgementRepositoryAdapter implements AlarmAcknowledgementRepositoryPort {
    private final AlarmAcknowledgementJpaRepository repository;
    private final AlarmRepositoryPort alarms;
    private final AlarmLifecycleEventRepositoryPort events;
    public JpaAlarmAcknowledgementRepositoryAdapter(AlarmAcknowledgementJpaRepository repository,
            AlarmRepositoryPort alarms, AlarmLifecycleEventRepositoryPort events) {
        this.repository=Objects.requireNonNull(repository);this.alarms=Objects.requireNonNull(alarms);this.events=Objects.requireNonNull(events);
    }
    @Override @Transactional
    public AlarmAcknowledgement save(AlarmAcknowledgement model) {
        Alarm alarm=alarms.findByIdForUpdate(model.alarmId())
                .orElseThrow(()->new IllegalArgumentException("Unknown alarm: "+model.alarmId()));
        var existing=repository.findById(model.id()).map(AlarmPersistenceMapper::toDomain);
        if(existing.isPresent()) {
            if(!existing.get().equals(model)) throw new IllegalStateException("Acknowledgement evidence is immutable.");
            return existing.get();
        }
        new AlarmLifecycleGuard().ensureCanAcknowledge(alarm);
        AlarmAcknowledgement saved=AlarmPersistenceMapper.toDomain(repository.saveAndFlush(AlarmPersistenceMapper.toEntity(model)));
        Alarm updated=alarm.withAcknowledgement(model.acknowledgedAt(),model.acknowledgedByActorId());
        alarms.save(updated);
        events.append(new AlarmLifecycleEvent(AlarmLifecycleEvent.operationId("ACKNOWLEDGED",model.id()),
                alarm.id(),AlarmLifecycleEventType.ACKNOWLEDGED,alarm.currentState(),updated.currentState(),null,
                model.comment(),model.acknowledgedByActorId(),model.acknowledgedByDisplayName(),
                model.organizationUnitId(),model.organizationUnitCode(),null,model.acknowledgedAt(),model.correlationId(),null));
        return saved;
    }
    @Override public Optional<AlarmAcknowledgement> findById(String id) {return repository.findById(id).map(AlarmPersistenceMapper::toDomain);}
}
