/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaNotificationSuppressionRuleRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for NotificationSuppressionRule.
 *
 */
package dz.sh.hidra.modules.notification.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.notification.application.port.out.NotificationSuppressionRuleRepositoryPort;
import dz.sh.hidra.modules.notification.domain.model.NotificationSuppressionRule;
import dz.sh.hidra.modules.notification.infrastructure.persistence.mapper.NotificationPersistenceMapper;
import dz.sh.hidra.modules.notification.infrastructure.persistence.repository.NotificationSuppressionRuleJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for NotificationSuppressionRule.
 */
@Component
public class JpaNotificationSuppressionRuleRepositoryAdapter implements NotificationSuppressionRuleRepositoryPort {

    private final NotificationSuppressionRuleJpaRepository repository;

    public JpaNotificationSuppressionRuleRepositoryAdapter(NotificationSuppressionRuleJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "NotificationSuppressionRuleJpaRepository must not be null.");
    }

    @Override
    public NotificationSuppressionRule save(NotificationSuppressionRule model) {
        return NotificationPersistenceMapper.toDomain(repository.save(NotificationPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<NotificationSuppressionRule> findById(String id) {
        return repository.findById(id).map(NotificationPersistenceMapper::toDomain);
    }
}
