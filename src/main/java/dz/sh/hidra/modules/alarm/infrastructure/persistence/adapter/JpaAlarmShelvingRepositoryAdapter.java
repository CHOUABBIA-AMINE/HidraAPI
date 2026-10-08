/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaAlarmShelvingRepositoryAdapter
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
import dz.sh.hidra.modules.alarm.domain.policy.AlarmShelvingPolicy;
import dz.sh.hidra.modules.alarm.domain.value.*;
import dz.sh.hidra.modules.alarm.infrastructure.persistence.mapper.AlarmPersistenceMapper;
import dz.sh.hidra.modules.alarm.infrastructure.persistence.repository.*;
import dz.sh.hidra.modules.alarm.infrastructure.persistence.validation.AlarmCatalogValidation;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class JpaAlarmShelvingRepositoryAdapter implements AlarmShelvingRepositoryPort {
    private final AlarmShelvingJpaRepository repository;
    private final AlarmRepositoryPort alarms;
    private final AlarmLifecycleEventRepositoryPort events;
    private final AlarmCatalogValidation catalogs;
    private final AlarmSuppressionJpaRepository suppressions;
    private final AlarmLifecycleActorPort actors;
    public JpaAlarmShelvingRepositoryAdapter(AlarmShelvingJpaRepository repository,AlarmRepositoryPort alarms,
            AlarmLifecycleEventRepositoryPort events,AlarmCatalogValidation catalogs,
            AlarmSuppressionJpaRepository suppressions,AlarmLifecycleActorPort actors) {
        this.repository=Objects.requireNonNull(repository);this.alarms=Objects.requireNonNull(alarms);
        this.events=Objects.requireNonNull(events);this.catalogs=Objects.requireNonNull(catalogs);
        this.suppressions=Objects.requireNonNull(suppressions);this.actors=Objects.requireNonNull(actors);
    }
    @Override @Transactional
    public AlarmShelving save(AlarmShelving model) {
        Alarm alarm=alarms.findByIdForUpdate(model.alarmId()).orElseThrow(()->new IllegalArgumentException("Unknown alarm: "+model.alarmId()));
        var existing=repository.findByIdForUpdate(model.id()).map(AlarmPersistenceMapper::toDomain);
        if(existing.isPresent() && existing.get().equals(model)) return existing.get();
        if(existing.isEmpty()) return create(model,alarm);
        return finish(model,existing.get(),alarm);
    }
    private AlarmShelving create(AlarmShelving model,Alarm alarm) {
        if(model.status()!=AlarmShelvingStatus.ACTIVE || model.unshelvedAt()!=null || model.unshelvedByActorId()!=null)
            throw new IllegalArgumentException("Fresh shelving must be ACTIVE with no invented finish evidence.");
        AlarmShelvingPolicy.ensureCanShelve(alarm);
        if(repository.existsByAlarmIdAndStatus(alarm.id(),AlarmShelvingStatus.ACTIVE)
                || suppressions.existsByScopeTypeAndScopeReferenceIdAndStatus(AlarmSuppressionScopeType.ALARM,alarm.id(),AlarmSuppressionStatus.ACTIVE))
            throw new IllegalStateException("ACTIVE shelving or ALARM-scoped suppression already exists.");
        catalogs.requireShelvingReason(model.shelvingReasonId());
        var saved=AlarmPersistenceMapper.toDomain(repository.saveAndFlush(AlarmPersistenceMapper.toEntity(model)));
        var updated=alarm.withState(AlarmState.SHELVED,AlarmShelvingPolicy.changedAt(alarm,model.shelvedAt()));alarms.save(updated);
        events.append(new AlarmLifecycleEvent(AlarmLifecycleEvent.operationId("SHELVED",model.id()),alarm.id(),
                AlarmLifecycleEventType.SHELVED,alarm.currentState(),AlarmState.SHELVED,model.shelvingReasonId(),
                model.reasonText(),model.shelvedByActorId(),null,null,null,null,model.shelvedAt(),model.correlationId(),null));
        return saved;
    }
    private AlarmShelving finish(AlarmShelving model,AlarmShelving existing,Alarm alarm) {
        if(existing.status()!=AlarmShelvingStatus.ACTIVE || model.status()==AlarmShelvingStatus.ACTIVE || model.unshelvedAt()==null)
            throw new IllegalStateException("Only ACTIVE shelving can finish once with occurrence evidence.");
        var permitted=new AlarmShelving(existing.id(),existing.alarmId(),existing.shelvingReasonId(),existing.reasonText(),
                existing.shelvedByActorId(),existing.shelvedAt(),existing.shelvedUntil(),model.unshelvedAt(),
                model.unshelvedByActorId(),model.status(),model.correlationId());
        if(!permitted.equals(model)) throw new IllegalStateException("Shelving creation evidence is immutable.");
        if(model.unshelvedAt().isBefore(existing.shelvedAt())) throw new IllegalArgumentException("Shelving finish cannot precede its start.");
        if(model.status()==AlarmShelvingStatus.EXPIRED && !model.unshelvedAt().equals(existing.shelvedUntil()))
            throw new IllegalArgumentException("Expiry occurrence must be the contractual end instant.");
        AlarmLifecycleEvent source=events.findById(AlarmLifecycleEvent.operationId("SHELVED",model.id())).orElse(null);
        var restored=AlarmShelvingPolicy.restorationState(alarm,source);
        var saved=AlarmPersistenceMapper.toDomain(repository.saveAndFlush(AlarmPersistenceMapper.toEntity(model)));
        alarms.save(alarm.withState(restored,AlarmShelvingPolicy.changedAt(alarm,model.unshelvedAt())));
        String actor=model.unshelvedByActorId()==null?actors.currentActor().id():model.unshelvedByActorId();
        events.append(new AlarmLifecycleEvent(AlarmLifecycleEvent.operationId("UNSHELVED",model.id()),alarm.id(),
                AlarmLifecycleEventType.UNSHELVED,alarm.currentState(),restored,model.shelvingReasonId(),
                model.status()==AlarmShelvingStatus.EXPIRED?"Automatic shelving expiry":"Shelving finished",
                actor,null,null,null,null,model.unshelvedAt(),model.correlationId(),null));
        return saved;
    }
    @Override @Transactional
    public boolean expireIfDue(String id,Instant asOf,String systemActorId,String correlationId) {
        Objects.requireNonNull(asOf);
        if(systemActorId==null || systemActorId.isBlank()) throw new IllegalArgumentException("Expiry actor is required.");
        var alarmId=repository.findAlarmId(id);if(alarmId.isEmpty()) return false;
        alarms.findByIdForUpdate(alarmId.get()).orElseThrow(()->new IllegalStateException("Unknown alarm: "+alarmId.get()));
        var row=repository.findByIdForUpdate(id).map(AlarmPersistenceMapper::toDomain);
        if(row.isEmpty() || row.get().status()!=AlarmShelvingStatus.ACTIVE || row.get().shelvedUntil().isAfter(asOf)) return false;
        var existing=row.get();
        save(new AlarmShelving(existing.id(),existing.alarmId(),existing.shelvingReasonId(),existing.reasonText(),
                existing.shelvedByActorId(),existing.shelvedAt(),existing.shelvedUntil(),existing.shelvedUntil(),
                systemActorId,AlarmShelvingStatus.EXPIRED,correlationId==null?existing.correlationId():correlationId));
        return true;
    }
    @Override public Optional<AlarmShelving> findById(String id) {return repository.findById(id).map(AlarmPersistenceMapper::toDomain);}
}
