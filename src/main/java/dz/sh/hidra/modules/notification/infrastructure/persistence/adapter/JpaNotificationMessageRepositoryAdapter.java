/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaNotificationMessageRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-06
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for NotificationMessage.
 *
 */
package dz.sh.hidra.modules.notification.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.notification.application.port.out.NotificationMessageRepositoryPort;
import dz.sh.hidra.modules.notification.domain.model.NotificationMessage;
import dz.sh.hidra.modules.notification.infrastructure.persistence.mapper.NotificationPersistenceMapper;
import dz.sh.hidra.modules.notification.infrastructure.persistence.repository.NotificationMessageJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import dz.sh.hidra.modules.notification.domain.exception.InvalidNotificationValueException;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for NotificationMessage.
 */
@Component
public class JpaNotificationMessageRepositoryAdapter implements NotificationMessageRepositoryPort {

    private final NotificationMessageJpaRepository repository;

    public JpaNotificationMessageRepositoryAdapter(NotificationMessageJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "NotificationMessageJpaRepository must not be null.");
    }

    @Override
    @Transactional
    public NotificationMessage save(NotificationMessage model) {
        Objects.requireNonNull(model, "NotificationMessage must not be null.");
        if (!repository.hasValidComposition(model.id(), model.requestId(), model.recipientId(),
                model.templateId(), model.templateVersionId(), model.priorityId(), model.status().name())) {
            throw new InvalidNotificationValueException(
                    "NotificationMessage recipient, template version, required variables or priority is invalid.");
        }
        // Flush before the caller can dispatch: database guards remain authoritative under races.
        return NotificationPersistenceMapper.toDomain(repository.saveAndFlush(NotificationPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<NotificationMessage> findById(String id) {
        return repository.findById(id).map(NotificationPersistenceMapper::toDomain);
    }
}
