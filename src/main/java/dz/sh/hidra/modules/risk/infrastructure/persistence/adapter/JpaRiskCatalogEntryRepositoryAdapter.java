/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaRiskCatalogEntryRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for RiskCatalogEntry.
 *
 */
package dz.sh.hidra.modules.risk.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.risk.application.port.out.RiskCatalogEntryRepositoryPort;
import dz.sh.hidra.modules.risk.domain.model.RiskCatalogEntry;
import dz.sh.hidra.modules.risk.infrastructure.persistence.mapper.RiskPersistenceMapper;
import dz.sh.hidra.modules.risk.infrastructure.persistence.repository.RiskCatalogEntryJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for RiskCatalogEntry.
 */
@Component
public class JpaRiskCatalogEntryRepositoryAdapter implements RiskCatalogEntryRepositoryPort {

    private final RiskCatalogEntryJpaRepository repository;

    public JpaRiskCatalogEntryRepositoryAdapter(RiskCatalogEntryJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "RiskCatalogEntryJpaRepository must not be null.");
    }

    @Override
    public RiskCatalogEntry save(RiskCatalogEntry model) {
        return RiskPersistenceMapper.toDomain(repository.save(RiskPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<RiskCatalogEntry> findById(String id) {
        return repository.findById(id).map(RiskPersistenceMapper::toDomain);
    }
}
