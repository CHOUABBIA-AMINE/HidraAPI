/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaLeakDetectionCaseRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-06
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for LeakDetectionCase.
 *
 */
package dz.sh.hidra.modules.leakdetection.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.leakdetection.application.port.out.LeakDetectionCaseRepositoryPort;
import dz.sh.hidra.modules.leakdetection.domain.model.LeakDetectionCase;
import dz.sh.hidra.modules.leakdetection.infrastructure.persistence.mapper.LeakDetectionPersistenceMapper;
import dz.sh.hidra.modules.leakdetection.infrastructure.persistence.repository.LeakDetectionCaseJpaRepository;
import dz.sh.hidra.modules.leakdetection.domain.exception.InvalidLeakDetectionValueException;
import dz.sh.hidra.modules.organization.application.contract.leakdetection.LeakDetectionOrganizationUnitReferenceContract;
import dz.sh.hidra.modules.topology.application.contract.leakdetection.LeakDetectionTopologyAssetContract;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

/**
 * Database-backed repository adapter for LeakDetectionCase.
 */
@Component
public class JpaLeakDetectionCaseRepositoryAdapter implements LeakDetectionCaseRepositoryPort {

    private final LeakDetectionCaseJpaRepository repository;

    private final LeakDetectionTopologyAssetContract topologyContract;
    private final LeakDetectionOrganizationUnitReferenceContract organizationContract;

    public JpaLeakDetectionCaseRepositoryAdapter(
            LeakDetectionCaseJpaRepository repository,
            LeakDetectionTopologyAssetContract topologyContract,
            LeakDetectionOrganizationUnitReferenceContract organizationContract
    ) {
        this.repository = Objects.requireNonNull(repository, "LeakDetectionCaseJpaRepository must not be null.");
        this.topologyContract = Objects.requireNonNull(topologyContract, "Topology contract must not be null.");
        this.organizationContract = Objects.requireNonNull(organizationContract, "Organization contract must not be null.");
    }

    @Override
    public LeakDetectionCase save(LeakDetectionCase model) {
        Objects.requireNonNull(model, "LeakDetectionCase must not be null.");
        var resolution = topologyContract.resolve(model.topologyAssetType(), model.topologyAssetId());
        if (!resolution.supported() || !resolution.exists()) {
            throw new InvalidLeakDetectionValueException(
                    "LeakDetectionCase requires an existing supported Topology asset."
            );
        }
        if (model.owningOrganizationUnitId() != null
                && !organizationContract.exists(model.owningOrganizationUnitId())) {
            throw new InvalidLeakDetectionValueException(
                    "LeakDetectionCase owning unit must reference an existing OrganizationUnit."
            );
        }
        return LeakDetectionPersistenceMapper.toDomain(repository.save(LeakDetectionPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<LeakDetectionCase> findById(String id) {
        return repository.findById(id).map(LeakDetectionPersistenceMapper::toDomain);
    }

    @Override
    public List<LeakDetectionCase> findAll(int page, int size) {
        return repository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "updatedAt")))
                .stream()
                .map(LeakDetectionPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public long count() {
        return repository.count();
    }
}
