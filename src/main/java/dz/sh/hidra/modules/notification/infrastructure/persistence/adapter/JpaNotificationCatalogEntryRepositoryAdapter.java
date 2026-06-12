/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaNotificationCatalogEntryRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for NotificationCatalogEntry.
 *
 */
package dz.sh.hidra.modules.notification.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.notification.application.port.out.NotificationCatalogEntryRepositoryPort;
import dz.sh.hidra.modules.notification.domain.model.NotificationCatalogEntry;
import dz.sh.hidra.modules.notification.infrastructure.persistence.mapper.NotificationPersistenceMapper;
import dz.sh.hidra.modules.notification.infrastructure.persistence.repository.NotificationCatalogEntryJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for NotificationCatalogEntry.
 */
@Component
public class JpaNotificationCatalogEntryRepositoryAdapter implements NotificationCatalogEntryRepositoryPort {

    private final NotificationCatalogEntryJpaRepository repository;

    public JpaNotificationCatalogEntryRepositoryAdapter(NotificationCatalogEntryJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "NotificationCatalogEntryJpaRepository must not be null.");
    }

    @Override
    public NotificationCatalogEntry save(NotificationCatalogEntry model) {
        return NotificationPersistenceMapper.toDomain(repository.save(NotificationPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<NotificationCatalogEntry> findById(String id) {
        return repository.findById(id).map(NotificationPersistenceMapper::toDomain);
    }
}
