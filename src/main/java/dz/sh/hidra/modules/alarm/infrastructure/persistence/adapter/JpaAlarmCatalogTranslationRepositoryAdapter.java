/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaAlarmCatalogTranslationRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for AlarmCatalogTranslation.
 *
 */
package dz.sh.hidra.modules.alarm.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.alarm.application.port.out.AlarmCatalogTranslationRepositoryPort;
import dz.sh.hidra.modules.alarm.domain.model.AlarmCatalogTranslation;
import dz.sh.hidra.modules.alarm.infrastructure.persistence.mapper.AlarmPersistenceMapper;
import dz.sh.hidra.modules.alarm.infrastructure.persistence.repository.AlarmCatalogTranslationJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for AlarmCatalogTranslation.
 */
@Component
public class JpaAlarmCatalogTranslationRepositoryAdapter implements AlarmCatalogTranslationRepositoryPort {

    private final AlarmCatalogTranslationJpaRepository repository;

    public JpaAlarmCatalogTranslationRepositoryAdapter(AlarmCatalogTranslationJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "AlarmCatalogTranslationJpaRepository must not be null.");
    }

    @Override
    public AlarmCatalogTranslation save(AlarmCatalogTranslation model) {
        return AlarmPersistenceMapper.toDomain(repository.save(AlarmPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<AlarmCatalogTranslation> findById(String id) {
        return repository.findById(id).map(AlarmPersistenceMapper::toDomain);
    }
}
