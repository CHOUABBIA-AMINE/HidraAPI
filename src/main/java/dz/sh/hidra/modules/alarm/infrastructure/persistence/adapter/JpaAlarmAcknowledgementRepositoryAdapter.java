/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaAlarmAcknowledgementRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for AlarmAcknowledgement.
 *
 */
package dz.sh.hidra.modules.alarm.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.alarm.application.port.out.AlarmAcknowledgementRepositoryPort;
import dz.sh.hidra.modules.alarm.domain.model.AlarmAcknowledgement;
import dz.sh.hidra.modules.alarm.infrastructure.persistence.mapper.AlarmPersistenceMapper;
import dz.sh.hidra.modules.alarm.infrastructure.persistence.repository.AlarmAcknowledgementJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for AlarmAcknowledgement.
 */
@Component
public class JpaAlarmAcknowledgementRepositoryAdapter implements AlarmAcknowledgementRepositoryPort {

    private final AlarmAcknowledgementJpaRepository repository;

    public JpaAlarmAcknowledgementRepositoryAdapter(AlarmAcknowledgementJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "AlarmAcknowledgementJpaRepository must not be null.");
    }

    @Override
    public AlarmAcknowledgement save(AlarmAcknowledgement model) {
        return AlarmPersistenceMapper.toDomain(repository.save(AlarmPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<AlarmAcknowledgement> findById(String id) {
        return repository.findById(id).map(AlarmPersistenceMapper::toDomain);
    }
}
