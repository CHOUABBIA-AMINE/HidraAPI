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
import dz.sh.hidra.modules.leakdetection.application.port.out.LeakCandidateResourceAuthorizationPort;
import dz.sh.hidra.modules.leakdetection.application.port.out.LeakCandidateResourceAuthorizationPort.AssetRef;
import dz.sh.hidra.modules.leakdetection.infrastructure.persistence.repository.LeakCandidateJpaRepository;
import dz.sh.hidra.modules.leakdetection.infrastructure.persistence.entity.LeakDetectionCaseJpaEntity;
import dz.sh.hidra.modules.leakdetection.infrastructure.persistence.entity.LeakCandidateJpaEntity;
import dz.sh.hidra.modules.leakdetection.domain.model.LeakDetectionCase;
import dz.sh.hidra.modules.leakdetection.infrastructure.persistence.mapper.LeakDetectionPersistenceMapper;
import dz.sh.hidra.modules.leakdetection.infrastructure.persistence.repository.LeakDetectionCaseJpaRepository;
import dz.sh.hidra.modules.leakdetection.domain.exception.InvalidLeakDetectionValueException;
import dz.sh.hidra.modules.organization.application.contract.leakdetection.LeakDetectionOrganizationUnitReferenceContract;
import dz.sh.hidra.modules.topology.application.contract.leakdetection.LeakDetectionTopologyAssetContract;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;
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
    private final LeakCandidateResourceAuthorizationPort currentScope;
    private final LeakCandidateJpaRepository candidateRepository;

    /** Historical semantic unit fixture; only the injected runtime constructor grants protected reads. */
    @Deprecated
    public JpaLeakDetectionCaseRepositoryAdapter(LeakDetectionCaseJpaRepository repository,
            LeakDetectionTopologyAssetContract topologyContract,
            LeakDetectionOrganizationUnitReferenceContract organizationContract) {
        this(repository,topologyContract,organizationContract,null,null);
    }

    @Autowired
    public JpaLeakDetectionCaseRepositoryAdapter(
            LeakDetectionCaseJpaRepository repository,
            LeakDetectionTopologyAssetContract topologyContract,
            LeakDetectionOrganizationUnitReferenceContract organizationContract,
            LeakCandidateResourceAuthorizationPort currentScope,
            LeakCandidateJpaRepository candidateRepository
    ) {
        this.repository = Objects.requireNonNull(repository, "LeakDetectionCaseJpaRepository must not be null.");
        this.topologyContract = Objects.requireNonNull(topologyContract, "Topology contract must not be null.");
        this.organizationContract = Objects.requireNonNull(organizationContract, "Organization contract must not be null.");
        this.currentScope=currentScope;
        this.candidateRepository=candidateRepository;
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
        requireRuntimeScope();
        var row=repository.findById(id);
        if(row.isEmpty()) return Optional.empty();
        var entity=row.orElseThrow();
        // Verify the primary candidate actually belongs to the case's exact physical asset.
        requireCandidateOwned(entity);
        return row.map(LeakDetectionPersistenceMapper::toDomain);
    }


    private void requireRuntimeScope() {
        if(currentScope==null || candidateRepository==null)
            throw new NoSuchElementException("Protected Leak case unavailable.");
    }
    private void requireCandidateOwned(LeakDetectionCaseJpaEntity item) {
        var candidate=candidateRepository.findById(item.primaryCandidateId())
                .orElseThrow(JpaLeakDetectionCaseRepositoryAdapter::unavailable);
        if(!item.topologyAssetType().equals(candidate.topologyAssetType())
                ||!item.topologyAssetId().equals(candidate.topologyAssetId()))throw unavailable();
        currentScope.requireCandidate("leakdetection:candidates:read",candidate.id());
        currentScope.requireAsset("leakdetection:candidates:read",
                item.topologyAssetType(),item.topologyAssetId());
    }
    private static NoSuchElementException unavailable() {
        return new NoSuchElementException("Protected Leak case unavailable.");
    }
    private Specification<LeakDetectionCaseJpaEntity> authorized(List<AssetRef> assets) {
        return (root,query,cb)->{
            var source=query.subquery(String.class);
            var candidate=source.from(LeakCandidateJpaEntity.class);
            source.select(candidate.get("id")).where(
                cb.equal(candidate.get("id"),root.get("primaryCandidateId")),
                cb.equal(candidate.get("topologyAssetType"),root.get("topologyAssetType")),
                cb.equal(candidate.get("topologyAssetId"),root.get("topologyAssetId")));
            Predicate[] pairs=assets.stream().map(a->cb.and(
                    cb.equal(root.get("topologyAssetType"),a.type()),
                    cb.equal(root.get("topologyAssetId"),a.id()))).toArray(Predicate[]::new);
            return cb.and(cb.or(pairs),cb.exists(source));
        };
    }
    private List<AssetRef> currentAssets() {
        requireRuntimeScope();
        return currentScope.permittedAssets("leakdetection:candidates:read");
    }
    @Override
    public List<LeakDetectionCase> findAll(int page, int size) {
        var assets=currentAssets();
        if(assets.isEmpty())return List.of();
        return repository.findAll(authorized(assets),
                PageRequest.of(page,size,Sort.by(Sort.Direction.DESC,"updatedAt")))
                .getContent().stream().map(LeakDetectionPersistenceMapper::toDomain).toList();
    }
    @Override
    public long count() {
        var assets=currentAssets();
        return assets.isEmpty()?0:repository.count(authorized(assets));
    }
}
