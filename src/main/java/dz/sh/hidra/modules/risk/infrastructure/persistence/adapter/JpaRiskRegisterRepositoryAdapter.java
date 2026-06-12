/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaRiskRegisterRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for RiskRegister.
 *
 */
package dz.sh.hidra.modules.risk.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.risk.application.port.out.RiskRegisterRepositoryPort;
import dz.sh.hidra.modules.risk.domain.model.RiskRegister;
import dz.sh.hidra.modules.risk.infrastructure.persistence.mapper.RiskPersistenceMapper;
import dz.sh.hidra.modules.risk.infrastructure.persistence.repository.RiskRegisterJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for RiskRegister.
 */
@Component
public class JpaRiskRegisterRepositoryAdapter implements RiskRegisterRepositoryPort {

    private final RiskRegisterJpaRepository repository;

    public JpaRiskRegisterRepositoryAdapter(RiskRegisterJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "RiskRegisterJpaRepository must not be null.");
    }

    @Override
    public RiskRegister save(RiskRegister model) {
        return RiskPersistenceMapper.toDomain(repository.save(RiskPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<RiskRegister> findById(String id) {
        return repository.findById(id).map(RiskPersistenceMapper::toDomain);
    }
}
