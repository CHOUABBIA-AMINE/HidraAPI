/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaTelemetryValidationRuleRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for TelemetryValidationRule.
 *
 */
package dz.sh.hidra.modules.telemetry.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryValidationRuleRepositoryPort;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryValidationRule;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.mapper.TelemetryPersistenceMapper;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.repository.TelemetryValidationRuleJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for TelemetryValidationRule.
 */
@Component
public class JpaTelemetryValidationRuleRepositoryAdapter implements TelemetryValidationRuleRepositoryPort {

    private final TelemetryValidationRuleJpaRepository repository;

    public JpaTelemetryValidationRuleRepositoryAdapter(TelemetryValidationRuleJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "TelemetryValidationRuleJpaRepository must not be null.");
    }

    @Override
    public TelemetryValidationRule save(TelemetryValidationRule model) {
        return TelemetryPersistenceMapper.toDomain(
                repository.save(TelemetryPersistenceMapper.toEntity(model))
        );
    }

    @Override
    public Optional<TelemetryValidationRule> findById(String id) {
        return repository.findById(id).map(TelemetryPersistenceMapper::toDomain);
    }
}
