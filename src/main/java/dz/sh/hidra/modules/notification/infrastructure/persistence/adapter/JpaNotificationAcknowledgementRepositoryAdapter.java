/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaNotificationAcknowledgementRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for NotificationAcknowledgement.
 *
 */
package dz.sh.hidra.modules.notification.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.notification.application.port.out.NotificationAcknowledgementRepositoryPort;
import dz.sh.hidra.modules.notification.domain.model.NotificationAcknowledgement;
import dz.sh.hidra.modules.notification.infrastructure.persistence.mapper.NotificationPersistenceMapper;
import dz.sh.hidra.modules.notification.infrastructure.persistence.repository.NotificationAcknowledgementJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for NotificationAcknowledgement.
 */
@Component
public class JpaNotificationAcknowledgementRepositoryAdapter implements NotificationAcknowledgementRepositoryPort {

    private final NotificationAcknowledgementJpaRepository repository;

    public JpaNotificationAcknowledgementRepositoryAdapter(NotificationAcknowledgementJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "NotificationAcknowledgementJpaRepository must not be null.");
    }

    @Override
    public NotificationAcknowledgement save(NotificationAcknowledgement model) {
        return NotificationPersistenceMapper.toDomain(repository.save(NotificationPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<NotificationAcknowledgement> findById(String id) {
        return repository.findById(id).map(NotificationPersistenceMapper::toDomain);
    }
}
