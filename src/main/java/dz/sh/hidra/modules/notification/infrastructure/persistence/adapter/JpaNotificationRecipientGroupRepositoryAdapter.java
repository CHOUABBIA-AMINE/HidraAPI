/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaNotificationRecipientGroupRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for NotificationRecipientGroup.
 *
 */
package dz.sh.hidra.modules.notification.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.notification.application.port.out.NotificationRecipientGroupRepositoryPort;
import dz.sh.hidra.modules.notification.domain.model.NotificationRecipientGroup;
import dz.sh.hidra.modules.notification.infrastructure.persistence.mapper.NotificationPersistenceMapper;
import dz.sh.hidra.modules.notification.infrastructure.persistence.repository.NotificationRecipientGroupJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for NotificationRecipientGroup.
 */
@Component
public class JpaNotificationRecipientGroupRepositoryAdapter implements NotificationRecipientGroupRepositoryPort {

    private final NotificationRecipientGroupJpaRepository repository;

    public JpaNotificationRecipientGroupRepositoryAdapter(NotificationRecipientGroupJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "NotificationRecipientGroupJpaRepository must not be null.");
    }

    @Override
    public NotificationRecipientGroup save(NotificationRecipientGroup model) {
        return NotificationPersistenceMapper.toDomain(repository.save(NotificationPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<NotificationRecipientGroup> findById(String id) {
        return repository.findById(id).map(NotificationPersistenceMapper::toDomain);
    }
}
