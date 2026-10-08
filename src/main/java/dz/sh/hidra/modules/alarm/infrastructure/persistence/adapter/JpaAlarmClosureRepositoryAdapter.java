/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaAlarmClosureRepositoryAdapter
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
import dz.sh.hidra.modules.alarm.domain.value.*;
import dz.sh.hidra.modules.alarm.infrastructure.persistence.mapper.AlarmPersistenceMapper;
import dz.sh.hidra.modules.alarm.infrastructure.persistence.repository.AlarmClosureJpaRepository;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class JpaAlarmClosureRepositoryAdapter implements AlarmClosureRepositoryPort {
    private final AlarmClosureJpaRepository repository;
    private final AlarmRepositoryPort alarms;
    private final AlarmLifecycleEventRepositoryPort events;
    public JpaAlarmClosureRepositoryAdapter(AlarmClosureJpaRepository repository,
            AlarmRepositoryPort alarms,AlarmLifecycleEventRepositoryPort events) {
        this.repository=Objects.requireNonNull(repository);this.alarms=Objects.requireNonNull(alarms);this.events=Objects.requireNonNull(events);
    }
    @Override @Transactional
    public AlarmClosure save(AlarmClosure model) {
        Alarm alarm=alarms.findByIdForUpdate(model.alarmId())
                .orElseThrow(()->new IllegalArgumentException("Unknown alarm: "+model.alarmId()));
        var existing=repository.findById(model.id()).map(AlarmPersistenceMapper::toDomain);
        if(existing.isPresent()) {
            if(!existing.get().equals(model)) throw new IllegalStateException("Closure evidence is immutable.");
            return existing.get();
        }
        if(repository.existsByAlarmId(model.alarmId())) throw new IllegalStateException("Alarm already has closure evidence.");
        boolean cancelled=model.closureType()==AlarmClosureType.CANCELLED;
        new AlarmLifecycleGuard().ensureCanClose(alarm,cancelled);
        AlarmClosure saved=AlarmPersistenceMapper.toDomain(repository.saveAndFlush(AlarmPersistenceMapper.toEntity(model)));
        Alarm updated=alarm.withClosure(model.closedAt(),cancelled);alarms.save(updated);
        events.append(new AlarmLifecycleEvent(AlarmLifecycleEvent.operationId("CLOSURE",model.id()),alarm.id(),
                cancelled?AlarmLifecycleEventType.CANCELLED:AlarmLifecycleEventType.CLOSED,
                alarm.currentState(),updated.currentState(),model.closureReasonId(),model.closureComment(),
                model.closedByActorId(),null,null,null,null,model.closedAt(),model.correlationId(),null));
        return saved;
    }
    @Override public Optional<AlarmClosure> findById(String id) {return repository.findById(id).map(AlarmPersistenceMapper::toDomain);}
}
