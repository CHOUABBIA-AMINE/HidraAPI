/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaNotificationDeliveryAttemptRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for NotificationDeliveryAttempt.
 *
 */
package dz.sh.hidra.modules.notification.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.notification.application.port.out.NotificationDeliveryAttemptRepositoryPort;
import dz.sh.hidra.modules.notification.domain.model.NotificationDeliveryAttempt;
import dz.sh.hidra.modules.notification.infrastructure.persistence.mapper.NotificationPersistenceMapper;
import dz.sh.hidra.modules.notification.infrastructure.persistence.repository.NotificationDeliveryAttemptJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for NotificationDeliveryAttempt.
 */
@Component
public class JpaNotificationDeliveryAttemptRepositoryAdapter implements NotificationDeliveryAttemptRepositoryPort {

    private final NotificationDeliveryAttemptJpaRepository repository;

    public JpaNotificationDeliveryAttemptRepositoryAdapter(NotificationDeliveryAttemptJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "NotificationDeliveryAttemptJpaRepository must not be null.");
    }

    @Override
    public NotificationDeliveryAttempt save(NotificationDeliveryAttempt model) {
        return NotificationPersistenceMapper.toDomain(repository.save(NotificationPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<NotificationDeliveryAttempt> findById(String id) {
        return repository.findById(id).map(NotificationPersistenceMapper::toDomain);
    }
}
