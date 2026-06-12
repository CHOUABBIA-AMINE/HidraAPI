/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaNotificationStatusHistoryRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for NotificationStatusHistory.
 *
 */
package dz.sh.hidra.modules.notification.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.notification.application.port.out.NotificationStatusHistoryRepositoryPort;
import dz.sh.hidra.modules.notification.domain.model.NotificationStatusHistory;
import dz.sh.hidra.modules.notification.infrastructure.persistence.mapper.NotificationPersistenceMapper;
import dz.sh.hidra.modules.notification.infrastructure.persistence.repository.NotificationStatusHistoryJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for NotificationStatusHistory.
 */
@Component
public class JpaNotificationStatusHistoryRepositoryAdapter implements NotificationStatusHistoryRepositoryPort {

    private final NotificationStatusHistoryJpaRepository repository;

    public JpaNotificationStatusHistoryRepositoryAdapter(NotificationStatusHistoryJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "NotificationStatusHistoryJpaRepository must not be null.");
    }

    @Override
    public NotificationStatusHistory save(NotificationStatusHistory model) {
        return NotificationPersistenceMapper.toDomain(repository.save(NotificationPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<NotificationStatusHistory> findById(String id) {
        return repository.findById(id).map(NotificationPersistenceMapper::toDomain);
    }
}
