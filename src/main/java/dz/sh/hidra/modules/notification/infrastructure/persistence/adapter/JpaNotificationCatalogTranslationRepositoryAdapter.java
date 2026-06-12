/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaNotificationCatalogTranslationRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for NotificationCatalogTranslation.
 *
 */
package dz.sh.hidra.modules.notification.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.notification.application.port.out.NotificationCatalogTranslationRepositoryPort;
import dz.sh.hidra.modules.notification.domain.model.NotificationCatalogTranslation;
import dz.sh.hidra.modules.notification.infrastructure.persistence.mapper.NotificationPersistenceMapper;
import dz.sh.hidra.modules.notification.infrastructure.persistence.repository.NotificationCatalogTranslationJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for NotificationCatalogTranslation.
 */
@Component
public class JpaNotificationCatalogTranslationRepositoryAdapter implements NotificationCatalogTranslationRepositoryPort {

    private final NotificationCatalogTranslationJpaRepository repository;

    public JpaNotificationCatalogTranslationRepositoryAdapter(NotificationCatalogTranslationJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "NotificationCatalogTranslationJpaRepository must not be null.");
    }

    @Override
    public NotificationCatalogTranslation save(NotificationCatalogTranslation model) {
        return NotificationPersistenceMapper.toDomain(repository.save(NotificationPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<NotificationCatalogTranslation> findById(String id) {
        return repository.findById(id).map(NotificationPersistenceMapper::toDomain);
    }
}
