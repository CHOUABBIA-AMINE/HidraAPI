/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaAlarmCatalogEntryRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for AlarmCatalogEntry.
 *
 */
package dz.sh.hidra.modules.alarm.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.alarm.application.port.out.AlarmCatalogEntryRepositoryPort;
import dz.sh.hidra.modules.alarm.domain.model.AlarmCatalogEntry;
import dz.sh.hidra.modules.alarm.infrastructure.persistence.mapper.AlarmPersistenceMapper;
import dz.sh.hidra.modules.alarm.infrastructure.persistence.repository.AlarmCatalogEntryJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for AlarmCatalogEntry.
 */
@Component
public class JpaAlarmCatalogEntryRepositoryAdapter implements AlarmCatalogEntryRepositoryPort {

    private final AlarmCatalogEntryJpaRepository repository;

    public JpaAlarmCatalogEntryRepositoryAdapter(AlarmCatalogEntryJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "AlarmCatalogEntryJpaRepository must not be null.");
    }

    @Override
    public AlarmCatalogEntry save(AlarmCatalogEntry model) {
        return AlarmPersistenceMapper.toDomain(repository.save(AlarmPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<AlarmCatalogEntry> findById(String id) {
        return repository.findById(id).map(AlarmPersistenceMapper::toDomain);
    }
}
