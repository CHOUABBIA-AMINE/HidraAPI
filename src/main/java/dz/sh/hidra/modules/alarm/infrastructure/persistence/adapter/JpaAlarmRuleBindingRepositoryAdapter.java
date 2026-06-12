/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaAlarmRuleBindingRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for AlarmRuleBinding.
 *
 */
package dz.sh.hidra.modules.alarm.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.alarm.application.port.out.AlarmRuleBindingRepositoryPort;
import dz.sh.hidra.modules.alarm.domain.model.AlarmRuleBinding;
import dz.sh.hidra.modules.alarm.infrastructure.persistence.mapper.AlarmPersistenceMapper;
import dz.sh.hidra.modules.alarm.infrastructure.persistence.repository.AlarmRuleBindingJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for AlarmRuleBinding.
 */
@Component
public class JpaAlarmRuleBindingRepositoryAdapter implements AlarmRuleBindingRepositoryPort {

    private final AlarmRuleBindingJpaRepository repository;

    public JpaAlarmRuleBindingRepositoryAdapter(AlarmRuleBindingJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "AlarmRuleBindingJpaRepository must not be null.");
    }

    @Override
    public AlarmRuleBinding save(AlarmRuleBinding model) {
        return AlarmPersistenceMapper.toDomain(repository.save(AlarmPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<AlarmRuleBinding> findById(String id) {
        return repository.findById(id).map(AlarmPersistenceMapper::toDomain);
    }
}
