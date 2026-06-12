/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaNotificationMessageVariableRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for NotificationMessageVariable.
 *
 */
package dz.sh.hidra.modules.notification.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.notification.application.port.out.NotificationMessageVariableRepositoryPort;
import dz.sh.hidra.modules.notification.domain.model.NotificationMessageVariable;
import dz.sh.hidra.modules.notification.infrastructure.persistence.mapper.NotificationPersistenceMapper;
import dz.sh.hidra.modules.notification.infrastructure.persistence.repository.NotificationMessageVariableJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for NotificationMessageVariable.
 */
@Component
public class JpaNotificationMessageVariableRepositoryAdapter implements NotificationMessageVariableRepositoryPort {

    private final NotificationMessageVariableJpaRepository repository;

    public JpaNotificationMessageVariableRepositoryAdapter(NotificationMessageVariableJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "NotificationMessageVariableJpaRepository must not be null.");
    }

    @Override
    public NotificationMessageVariable save(NotificationMessageVariable model) {
        return NotificationPersistenceMapper.toDomain(repository.save(NotificationPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<NotificationMessageVariable> findById(String id) {
        return repository.findById(id).map(NotificationPersistenceMapper::toDomain);
    }
}
