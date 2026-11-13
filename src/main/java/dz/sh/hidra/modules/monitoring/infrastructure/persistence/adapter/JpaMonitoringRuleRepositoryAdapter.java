/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaMonitoringRuleRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-13
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for MonitoringRule.
 *
 */
package dz.sh.hidra.modules.monitoring.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.monitoring.application.port.out.MonitoringRuleRepositoryPort;
import dz.sh.hidra.modules.monitoring.domain.model.MonitoringRule;
import dz.sh.hidra.modules.monitoring.infrastructure.persistence.mapper.MonitoringPersistenceMapper;
import dz.sh.hidra.modules.monitoring.infrastructure.persistence.repository.MonitoringRuleJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for MonitoringRule.
 */
@Component
public class JpaMonitoringRuleRepositoryAdapter implements MonitoringRuleRepositoryPort {

    private final MonitoringRuleJpaRepository repository;

    public JpaMonitoringRuleRepositoryAdapter(MonitoringRuleJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "MonitoringRuleJpaRepository must not be null.");
    }

    @Override
    public MonitoringRule save(MonitoringRule model) {
        // The same transaction inserts the rule's foreign-key-dependent scope through JDBC.
        return MonitoringPersistenceMapper.toDomain(
                repository.saveAndFlush(MonitoringPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<MonitoringRule> findById(String id) {
        return repository.findById(id).map(MonitoringPersistenceMapper::toDomain);
    }
}
