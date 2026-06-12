/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaNotificationTemplateVersionRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for NotificationTemplateVersion.
 *
 */
package dz.sh.hidra.modules.notification.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.notification.application.port.out.NotificationTemplateVersionRepositoryPort;
import dz.sh.hidra.modules.notification.domain.model.NotificationTemplateVersion;
import dz.sh.hidra.modules.notification.infrastructure.persistence.mapper.NotificationPersistenceMapper;
import dz.sh.hidra.modules.notification.infrastructure.persistence.repository.NotificationTemplateVersionJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for NotificationTemplateVersion.
 */
@Component
public class JpaNotificationTemplateVersionRepositoryAdapter implements NotificationTemplateVersionRepositoryPort {

    private final NotificationTemplateVersionJpaRepository repository;

    public JpaNotificationTemplateVersionRepositoryAdapter(NotificationTemplateVersionJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "NotificationTemplateVersionJpaRepository must not be null.");
    }

    @Override
    public NotificationTemplateVersion save(NotificationTemplateVersion model) {
        return NotificationPersistenceMapper.toDomain(repository.save(NotificationPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<NotificationTemplateVersion> findById(String id) {
        return repository.findById(id).map(NotificationPersistenceMapper::toDomain);
    }
}
