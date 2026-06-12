/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaAlarmCommentRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for AlarmComment.
 *
 */
package dz.sh.hidra.modules.alarm.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.alarm.application.port.out.AlarmCommentRepositoryPort;
import dz.sh.hidra.modules.alarm.domain.model.AlarmComment;
import dz.sh.hidra.modules.alarm.infrastructure.persistence.mapper.AlarmPersistenceMapper;
import dz.sh.hidra.modules.alarm.infrastructure.persistence.repository.AlarmCommentJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for AlarmComment.
 */
@Component
public class JpaAlarmCommentRepositoryAdapter implements AlarmCommentRepositoryPort {

    private final AlarmCommentJpaRepository repository;

    public JpaAlarmCommentRepositoryAdapter(AlarmCommentJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "AlarmCommentJpaRepository must not be null.");
    }

    @Override
    public AlarmComment save(AlarmComment model) {
        return AlarmPersistenceMapper.toDomain(repository.save(AlarmPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<AlarmComment> findById(String id) {
        return repository.findById(id).map(AlarmPersistenceMapper::toDomain);
    }
}
