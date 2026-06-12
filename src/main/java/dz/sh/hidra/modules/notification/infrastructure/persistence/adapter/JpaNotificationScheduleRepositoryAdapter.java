/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaNotificationScheduleRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for NotificationSchedule.
 *
 */
package dz.sh.hidra.modules.notification.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.notification.application.port.out.NotificationScheduleRepositoryPort;
import dz.sh.hidra.modules.notification.domain.model.NotificationSchedule;
import dz.sh.hidra.modules.notification.infrastructure.persistence.mapper.NotificationPersistenceMapper;
import dz.sh.hidra.modules.notification.infrastructure.persistence.repository.NotificationScheduleJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for NotificationSchedule.
 */
@Component
public class JpaNotificationScheduleRepositoryAdapter implements NotificationScheduleRepositoryPort {

    private final NotificationScheduleJpaRepository repository;

    public JpaNotificationScheduleRepositoryAdapter(NotificationScheduleJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "NotificationScheduleJpaRepository must not be null.");
    }

    @Override
    public NotificationSchedule save(NotificationSchedule model) {
        return NotificationPersistenceMapper.toDomain(repository.save(NotificationPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<NotificationSchedule> findById(String id) {
        return repository.findById(id).map(NotificationPersistenceMapper::toDomain);
    }
}
