/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaNotificationTemplateTranslationRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for NotificationTemplateTranslation.
 *
 */
package dz.sh.hidra.modules.notification.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.notification.application.port.out.NotificationTemplateTranslationRepositoryPort;
import dz.sh.hidra.modules.notification.domain.model.NotificationTemplateTranslation;
import dz.sh.hidra.modules.notification.infrastructure.persistence.mapper.NotificationPersistenceMapper;
import dz.sh.hidra.modules.notification.infrastructure.persistence.repository.NotificationTemplateTranslationJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for NotificationTemplateTranslation.
 */
@Component
public class JpaNotificationTemplateTranslationRepositoryAdapter implements NotificationTemplateTranslationRepositoryPort {

    private final NotificationTemplateTranslationJpaRepository repository;

    public JpaNotificationTemplateTranslationRepositoryAdapter(NotificationTemplateTranslationJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "NotificationTemplateTranslationJpaRepository must not be null.");
    }

    @Override
    public NotificationTemplateTranslation save(NotificationTemplateTranslation model) {
        return NotificationPersistenceMapper.toDomain(repository.save(NotificationPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<NotificationTemplateTranslation> findById(String id) {
        return repository.findById(id).map(NotificationPersistenceMapper::toDomain);
    }
}
