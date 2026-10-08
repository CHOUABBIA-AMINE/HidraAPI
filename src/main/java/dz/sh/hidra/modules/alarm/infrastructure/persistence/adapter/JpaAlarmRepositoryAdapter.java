/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaAlarmRepositoryAdapter
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
import dz.sh.hidra.modules.alarm.domain.value.*;
import dz.sh.hidra.modules.alarm.infrastructure.persistence.mapper.AlarmPersistenceMapper;
import dz.sh.hidra.modules.alarm.infrastructure.persistence.repository.AlarmJpaRepository;
import dz.sh.hidra.modules.alarm.infrastructure.persistence.validation.AlarmCatalogValidation;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class JpaAlarmRepositoryAdapter implements AlarmRepositoryPort {
    private final AlarmJpaRepository repository;
    private final AlarmCatalogValidation catalogs;
    private final AlarmLifecycleEventRepositoryPort events;
    private final AlarmLifecycleActorPort actors;
    public JpaAlarmRepositoryAdapter(AlarmJpaRepository repository, AlarmCatalogValidation catalogs,
            AlarmLifecycleEventRepositoryPort events, AlarmLifecycleActorPort actors) {
        this.repository = Objects.requireNonNull(repository); this.catalogs = Objects.requireNonNull(catalogs);
        this.events = Objects.requireNonNull(events); this.actors = Objects.requireNonNull(actors);
    }
    @Override @Transactional
    public Alarm save(Alarm model) {
        var existing = repository.findByIdForUpdate(model.id());
        if (existing.isEmpty() && model.currentState() != AlarmState.RAISED) {
            throw new IllegalArgumentException("New formal Alarm must start RAISED.");
        }
        catalogs.validate(model);
        Alarm saved = AlarmPersistenceMapper.toDomain(repository.saveAndFlush(AlarmPersistenceMapper.toEntity(model)));
        if (existing.isEmpty()) {
            var actor = actors.currentActor();
            events.append(new AlarmLifecycleEvent(AlarmLifecycleEvent.operationId("RAISED", model.id()),
                    model.id(), AlarmLifecycleEventType.RAISED, null, AlarmState.RAISED, null, null,
                    actor.id(), actor.displayName(), model.owningOrganizationUnitId(),
                    model.owningOrganizationUnitCode(), model.owningOrganizationUnitNameSnapshot(),
                    model.raisedAt(), model.correlationId(), null));
        }
        return saved;
    }
    @Override public Optional<Alarm> findById(String id) {return repository.findById(id).map(AlarmPersistenceMapper::toDomain);}
    @Override @Transactional public Optional<Alarm> findByIdForUpdate(String id) {
        return repository.findByIdForUpdate(id).map(AlarmPersistenceMapper::toDomain);
    }
    @Override public boolean hasActiveShelving(String id) {return repository.hasActiveShelving(id);}
}
