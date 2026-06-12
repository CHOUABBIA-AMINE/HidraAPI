/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaNotificationRetryPolicyRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for NotificationRetryPolicy.
 *
 */
package dz.sh.hidra.modules.notification.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.notification.application.port.out.NotificationRetryPolicyRepositoryPort;
import dz.sh.hidra.modules.notification.domain.model.NotificationRetryPolicy;
import dz.sh.hidra.modules.notification.infrastructure.persistence.mapper.NotificationPersistenceMapper;
import dz.sh.hidra.modules.notification.infrastructure.persistence.repository.NotificationRetryPolicyJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for NotificationRetryPolicy.
 */
@Component
public class JpaNotificationRetryPolicyRepositoryAdapter implements NotificationRetryPolicyRepositoryPort {

    private final NotificationRetryPolicyJpaRepository repository;

    public JpaNotificationRetryPolicyRepositoryAdapter(NotificationRetryPolicyJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "NotificationRetryPolicyJpaRepository must not be null.");
    }

    @Override
    public NotificationRetryPolicy save(NotificationRetryPolicy model) {
        return NotificationPersistenceMapper.toDomain(repository.save(NotificationPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<NotificationRetryPolicy> findById(String id) {
        return repository.findById(id).map(NotificationPersistenceMapper::toDomain);
    }
}
