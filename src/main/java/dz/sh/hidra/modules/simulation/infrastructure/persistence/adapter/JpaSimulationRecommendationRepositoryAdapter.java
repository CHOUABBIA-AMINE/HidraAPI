/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaSimulationRecommendationRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for SimulationRecommendation.
 *
 */
package dz.sh.hidra.modules.simulation.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.simulation.application.port.out.SimulationRecommendationRepositoryPort;
import dz.sh.hidra.modules.simulation.domain.model.SimulationRecommendation;
import dz.sh.hidra.modules.simulation.infrastructure.persistence.mapper.SimulationPersistenceMapper;
import dz.sh.hidra.modules.simulation.infrastructure.persistence.repository.SimulationRecommendationJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityManager;
import dz.sh.hidra.modules.audit.application.contract.simulation.SimulationRecommendationAuditContract;
import dz.sh.hidra.modules.simulation.domain.exception.InvalidSimulationValueException;
import dz.sh.hidra.modules.simulation.domain.value.SimulationRecommendationStatus;
import dz.sh.hidra.modules.simulation.infrastructure.persistence.repository.SimulationCatalogEntryJpaRepository;
import dz.sh.hidra.modules.simulation.infrastructure.persistence.repository.SimulationOptimizationCandidateJpaRepository;
import dz.sh.hidra.modules.simulation.infrastructure.persistence.repository.SimulationRunJpaRepository;
import java.time.Instant;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for SimulationRecommendation.
 */
@Component
public class JpaSimulationRecommendationRepositoryAdapter implements SimulationRecommendationRepositoryPort {

    private final SimulationRecommendationJpaRepository repository;

    private final SimulationCatalogEntryJpaRepository catalogs;
    private final SimulationOptimizationCandidateJpaRepository candidates;
    private final SimulationRunJpaRepository runs;
    private final SimulationRecommendationAuditContract audit;
    private final EntityManager entityManager;

    @Autowired
    public JpaSimulationRecommendationRepositoryAdapter(SimulationRecommendationJpaRepository repository,
            SimulationCatalogEntryJpaRepository catalogs, SimulationOptimizationCandidateJpaRepository candidates,
            SimulationRunJpaRepository runs, SimulationRecommendationAuditContract audit, EntityManager entityManager) {
        this.repository=Objects.requireNonNull(repository);
        this.catalogs=Objects.requireNonNull(catalogs);
        this.candidates=Objects.requireNonNull(candidates);
        this.runs=Objects.requireNonNull(runs);
        this.audit=Objects.requireNonNull(audit);
        this.entityManager=Objects.requireNonNull(entityManager);
    }

    /** Compatibility callers may read; writes require the complete owner boundary. */
    public JpaSimulationRecommendationRepositoryAdapter(SimulationRecommendationJpaRepository repository) {
        this.catalogs=null; this.candidates=null; this.runs=null; this.audit=null; this.entityManager=null;
        this.repository = Objects.requireNonNull(repository, "SimulationRecommendationJpaRepository must not be null.");
    }

    @Override
    @Transactional
    public SimulationRecommendation save(SimulationRecommendation model) {
        Objects.requireNonNull(model);
        if (model.recommendationStatus()==SimulationRecommendationStatus.PUBLISHED) {
            throw new InvalidSimulationValueException("PUBLISHED recommendations require the audited publication operation.");
        }
        validate(model,repository.findById(model.id()).map(SimulationPersistenceMapper::toDomain));
        return SimulationPersistenceMapper.toDomain(repository.save(SimulationPersistenceMapper.toEntity(model)));
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public SimulationRecommendation publish(SimulationRecommendation model) {
        Objects.requireNonNull(model);
        if(model.recommendationStatus()!=SimulationRecommendationStatus.PUBLISHED) {
            throw new InvalidSimulationValueException("Publication requires PUBLISHED status.");
        }
        if(audit==null || entityManager==null) {
            throw new InvalidSimulationValueException("Audited publication is unavailable.");
        }
        if(repository.existsById(model.id())) {
            throw new InvalidSimulationValueException("Publication must create a new recommendation.");
        }
        validate(model,Optional.empty());
        Instant occurredAt=Instant.now();
        var published=new SimulationRecommendation(model.id(),model.runId(),model.candidateId(),
                model.recommendationTypeId(),SimulationRecommendationStatus.PUBLISHED,model.title(),model.description(),
                model.confidenceLevelId(),model.targetModule(),model.targetProposalReference(),model.publishedByActorId(),
                occurredAt,model.createdAt());
        var entity=SimulationPersistenceMapper.toEntity(published);
        // Persist rather than merge: duplicate/concurrent IDs cannot overwrite existing evidence.
        entityManager.persist(entity);
        entityManager.flush();
        String receipt=audit.appendPublished(new SimulationRecommendationAuditContract.PublicationEvidence(
                published.id(),published.runId(),published.candidateId(),published.recommendationTypeId(),
                published.publishedByActorId(),occurredAt));
        if(receipt==null || receipt.isBlank())throw new IllegalStateException("Audit publication receipt is missing.");
        return SimulationPersistenceMapper.toDomain(entity);
    }

    private void validate(SimulationRecommendation model,Optional<SimulationRecommendation> prior) {
        if(catalogs==null || candidates==null || runs==null) {
            throw new InvalidSimulationValueException("Recommendation owner validation is unavailable.");
        }
        if(!runs.existsById(model.runId()))throw new InvalidSimulationValueException("Simulation run does not exist.");
        if(model.candidateId()!=null && !candidates.existsById(model.candidateId())) {
            throw new InvalidSimulationValueException("Simulation candidate does not exist.");
        }
        requireCatalog(model.recommendationTypeId(),"SIMULATION_RECOMMENDATION_TYPE",
                prior.isEmpty() || !Objects.equals(prior.get().recommendationTypeId(),model.recommendationTypeId()));
        if(model.confidenceLevelId()!=null) {
            requireCatalog(model.confidenceLevelId(),"SIMULATION_CONFIDENCE_LEVEL",
                    prior.isEmpty() || !Objects.equals(prior.get().confidenceLevelId(),model.confidenceLevelId()));
        }
    }
    private void requireCatalog(String id,String family,boolean requireActive) {
        var entry=catalogs.findLockedById(id).orElseThrow(() ->
                new InvalidSimulationValueException("Simulation catalog reference does not exist."));
        if(!family.equals(entry.catalogName()) || (requireActive && !entry.active())) {
            throw new InvalidSimulationValueException("An eligible "+family+" is required.");
        }
    }

    @Override
    public Optional<SimulationRecommendation> findById(String id) {
        return repository.findById(id).map(SimulationPersistenceMapper::toDomain);
    }
}
