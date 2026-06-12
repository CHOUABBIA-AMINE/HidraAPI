/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaInspectionCampaignRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for InspectionCampaign.
 *
 */
package dz.sh.hidra.modules.integrity.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.integrity.application.port.out.InspectionCampaignRepositoryPort;
import dz.sh.hidra.modules.integrity.domain.model.InspectionCampaign;
import dz.sh.hidra.modules.integrity.infrastructure.persistence.mapper.IntegrityPersistenceMapper;
import dz.sh.hidra.modules.integrity.infrastructure.persistence.repository.InspectionCampaignJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for InspectionCampaign.
 */
@Component
public class JpaInspectionCampaignRepositoryAdapter implements InspectionCampaignRepositoryPort {

    private final InspectionCampaignJpaRepository repository;

    public JpaInspectionCampaignRepositoryAdapter(InspectionCampaignJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "InspectionCampaignJpaRepository must not be null.");
    }

    @Override
    public InspectionCampaign save(InspectionCampaign model) {
        return IntegrityPersistenceMapper.toDomain(repository.save(IntegrityPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<InspectionCampaign> findById(String id) {
        return repository.findById(id).map(IntegrityPersistenceMapper::toDomain);
    }
}
