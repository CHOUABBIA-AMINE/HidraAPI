/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaAlarmSuppressionRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for AlarmSuppression.
 *
 */
package dz.sh.hidra.modules.alarm.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.alarm.application.port.out.AlarmSuppressionRepositoryPort;
import dz.sh.hidra.modules.alarm.domain.model.AlarmSuppression;
import dz.sh.hidra.modules.alarm.infrastructure.persistence.mapper.AlarmPersistenceMapper;
import dz.sh.hidra.modules.alarm.infrastructure.persistence.repository.AlarmSuppressionJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for AlarmSuppression.
 */
@Component
public class JpaAlarmSuppressionRepositoryAdapter implements AlarmSuppressionRepositoryPort {

    private final AlarmSuppressionJpaRepository repository;

    public JpaAlarmSuppressionRepositoryAdapter(AlarmSuppressionJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "AlarmSuppressionJpaRepository must not be null.");
    }

    @Override
    public AlarmSuppression save(AlarmSuppression model) {
        return AlarmPersistenceMapper.toDomain(repository.save(AlarmPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<AlarmSuppression> findById(String id) {
        return repository.findById(id).map(AlarmPersistenceMapper::toDomain);
    }
}
