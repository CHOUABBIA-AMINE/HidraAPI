/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaAlarmEvidenceLinkRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for AlarmEvidenceLink.
 *
 */
package dz.sh.hidra.modules.alarm.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.alarm.application.port.out.AlarmEvidenceLinkRepositoryPort;
import dz.sh.hidra.modules.alarm.domain.model.AlarmEvidenceLink;
import dz.sh.hidra.modules.alarm.infrastructure.persistence.mapper.AlarmPersistenceMapper;
import dz.sh.hidra.modules.alarm.infrastructure.persistence.repository.AlarmEvidenceLinkJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for AlarmEvidenceLink.
 */
@Component
public class JpaAlarmEvidenceLinkRepositoryAdapter implements AlarmEvidenceLinkRepositoryPort {

    private final AlarmEvidenceLinkJpaRepository repository;

    public JpaAlarmEvidenceLinkRepositoryAdapter(AlarmEvidenceLinkJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "AlarmEvidenceLinkJpaRepository must not be null.");
    }

    @Override
    public AlarmEvidenceLink save(AlarmEvidenceLink model) {
        return AlarmPersistenceMapper.toDomain(repository.save(AlarmPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<AlarmEvidenceLink> findById(String id) {
        return repository.findById(id).map(AlarmPersistenceMapper::toDomain);
    }
}
