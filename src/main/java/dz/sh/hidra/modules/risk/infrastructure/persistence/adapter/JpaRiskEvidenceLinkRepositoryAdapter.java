/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaRiskEvidenceLinkRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for RiskEvidenceLink.
 *
 */
package dz.sh.hidra.modules.risk.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.risk.application.port.out.RiskEvidenceLinkRepositoryPort;
import dz.sh.hidra.modules.risk.domain.model.RiskEvidenceLink;
import dz.sh.hidra.modules.risk.infrastructure.persistence.mapper.RiskPersistenceMapper;
import dz.sh.hidra.modules.risk.infrastructure.persistence.repository.RiskEvidenceLinkJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for RiskEvidenceLink.
 */
@Component
public class JpaRiskEvidenceLinkRepositoryAdapter implements RiskEvidenceLinkRepositoryPort {

    private final RiskEvidenceLinkJpaRepository repository;

    public JpaRiskEvidenceLinkRepositoryAdapter(RiskEvidenceLinkJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "RiskEvidenceLinkJpaRepository must not be null.");
    }

    @Override
    public RiskEvidenceLink save(RiskEvidenceLink model) {
        return RiskPersistenceMapper.toDomain(repository.save(RiskPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<RiskEvidenceLink> findById(String id) {
        return repository.findById(id).map(RiskPersistenceMapper::toDomain);
    }
}
