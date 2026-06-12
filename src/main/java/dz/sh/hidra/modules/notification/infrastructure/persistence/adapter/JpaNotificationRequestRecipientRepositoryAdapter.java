/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaNotificationRequestRecipientRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for NotificationRequestRecipient.
 *
 */
package dz.sh.hidra.modules.notification.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.notification.application.port.out.NotificationRequestRecipientRepositoryPort;
import dz.sh.hidra.modules.notification.domain.model.NotificationRequestRecipient;
import dz.sh.hidra.modules.notification.infrastructure.persistence.mapper.NotificationPersistenceMapper;
import dz.sh.hidra.modules.notification.infrastructure.persistence.repository.NotificationRequestRecipientJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for NotificationRequestRecipient.
 */
@Component
public class JpaNotificationRequestRecipientRepositoryAdapter implements NotificationRequestRecipientRepositoryPort {

    private final NotificationRequestRecipientJpaRepository repository;

    public JpaNotificationRequestRecipientRepositoryAdapter(NotificationRequestRecipientJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "NotificationRequestRecipientJpaRepository must not be null.");
    }

    @Override
    public NotificationRequestRecipient save(NotificationRequestRecipient model) {
        return NotificationPersistenceMapper.toDomain(repository.save(NotificationPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<NotificationRequestRecipient> findById(String id) {
        return repository.findById(id).map(NotificationPersistenceMapper::toDomain);
    }
}
