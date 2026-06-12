/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaNotificationRecipientProfileRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for NotificationRecipientProfile.
 *
 */
package dz.sh.hidra.modules.notification.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.notification.application.port.out.NotificationRecipientProfileRepositoryPort;
import dz.sh.hidra.modules.notification.domain.model.NotificationRecipientProfile;
import dz.sh.hidra.modules.notification.infrastructure.persistence.mapper.NotificationPersistenceMapper;
import dz.sh.hidra.modules.notification.infrastructure.persistence.repository.NotificationRecipientProfileJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for NotificationRecipientProfile.
 */
@Component
public class JpaNotificationRecipientProfileRepositoryAdapter implements NotificationRecipientProfileRepositoryPort {

    private final NotificationRecipientProfileJpaRepository repository;

    public JpaNotificationRecipientProfileRepositoryAdapter(NotificationRecipientProfileJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "NotificationRecipientProfileJpaRepository must not be null.");
    }

    @Override
    public NotificationRecipientProfile save(NotificationRecipientProfile model) {
        return NotificationPersistenceMapper.toDomain(repository.save(NotificationPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<NotificationRecipientProfile> findById(String id) {
        return repository.findById(id).map(NotificationPersistenceMapper::toDomain);
    }
}
