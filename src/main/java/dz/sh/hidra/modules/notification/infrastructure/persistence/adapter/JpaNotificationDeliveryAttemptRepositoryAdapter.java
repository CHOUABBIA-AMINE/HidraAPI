/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaNotificationDeliveryAttemptRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-06
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for NotificationDeliveryAttempt.
 *
 */
package dz.sh.hidra.modules.notification.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.notification.application.port.out.NotificationDeliveryAttemptRepositoryPort;
import dz.sh.hidra.modules.notification.domain.exception.InvalidNotificationValueException;
import dz.sh.hidra.modules.notification.domain.model.NotificationDeliveryAttempt;
import dz.sh.hidra.modules.notification.infrastructure.persistence.mapper.NotificationPersistenceMapper;
import dz.sh.hidra.modules.notification.infrastructure.persistence.repository.NotificationDeliveryAttemptJpaRepository;
import dz.sh.hidra.modules.notification.infrastructure.persistence.repository.NotificationMessageJpaRepository;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.Objects;
import java.util.Optional;

@Component
public class JpaNotificationDeliveryAttemptRepositoryAdapter implements NotificationDeliveryAttemptRepositoryPort {
    private final NotificationDeliveryAttemptJpaRepository repository;
    private final NotificationMessageJpaRepository messages;
    private final EntityManager entityManager;

    public JpaNotificationDeliveryAttemptRepositoryAdapter(
            NotificationDeliveryAttemptJpaRepository repository,
            NotificationMessageJpaRepository messages, EntityManager entityManager) {
        this.repository = Objects.requireNonNull(repository);
        this.messages = Objects.requireNonNull(messages);
        this.entityManager = Objects.requireNonNull(entityManager);
    }

    @Override
    @Transactional
    public NotificationDeliveryAttempt save(NotificationDeliveryAttempt model) {
        Objects.requireNonNull(model, "NotificationDeliveryAttempt must not be null.");
        var message = messages.findById(model.messageId()).orElseThrow(() ->
                new InvalidNotificationValueException("Delivery attempt must reference an existing message."));
        if (!Objects.equals(model.channelId(), message.channelId())) {
            throw new InvalidNotificationValueException("Delivery attempt channel must match the message channel.");
        }
        if (repository.existsById(model.id())) {
            throw new InvalidNotificationValueException("Delivery attempts are append-only; ID already exists.");
        }
        // persist never merges; the primary key closes concurrent duplicate-ID races.
        // Database triggers reject update/delete even through another persistence path.
        entityManager.persist(NotificationPersistenceMapper.toEntity(model));
        entityManager.flush();
        return model;
    }

    @Override
    public Optional<NotificationDeliveryAttempt> findById(String id) {
        return repository.findById(id).map(NotificationPersistenceMapper::toDomain);
    }
}
