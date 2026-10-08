/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaRiskAssessmentRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.infrastructure.persistence.adapter
 *
 * @Description : Enforces governed Risk owner boundaries.
 *
 */
package dz.sh.hidra.modules.risk.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.risk.application.port.out.*;
import dz.sh.hidra.modules.risk.application.command.*;
import dz.sh.hidra.modules.risk.domain.model.RiskAssessment;
import dz.sh.hidra.modules.risk.domain.value.*;
import dz.sh.hidra.modules.risk.domain.exception.InvalidRiskValueException;
import dz.sh.hidra.modules.risk.infrastructure.persistence.mapper.RiskPersistenceMapper;
import dz.sh.hidra.modules.risk.infrastructure.persistence.repository.*;
import dz.sh.hidra.modules.risk.infrastructure.persistence.entity.*;
import dz.sh.hidra.modules.organization.application.contract.risk.RiskOrganizationReferenceContract;
import dz.sh.hidra.modules.topology.application.contract.risk.RiskTopologyScopeReferenceContract;
import dz.sh.hidra.modules.identity.application.contract.risk.RiskActorContract;
import dz.sh.hidra.modules.workflow.application.contract.risk.RiskAssessmentApprovalContract;
import dz.sh.hidra.modules.audit.application.contract.risk.RiskAssessmentAuditContract;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.util.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(rollbackFor=Exception.class)
public class JpaRiskAssessmentRepositoryAdapter implements RiskAssessmentRepositoryPort {
    private final RiskAssessmentJpaRepository repository;
    private final RiskAssessmentScopeJpaRepository scopes;
    private final RiskAssessmentScoringJpaRepository scoring;
    private final RiskCatalogEntryJpaRepository catalogs;
    private final RiskMatrixCellJpaRepository cells;
    private final RiskMatrixJpaRepository matrices;
    private final RiskControlJpaRepository controls;
    private final RiskTreatmentPlanJpaRepository treatments;
    private final RiskEvidenceLinkJpaRepository evidence;
    private final RiskEvidenceLookupPort evidenceLookup;
    private final RiskOrganizationReferenceContract organization;
    private final RiskTopologyScopeReferenceContract topology;
    private final RiskActorContract actors;
    private final RiskAssessmentApprovalContract workflow;
    private final RiskAssessmentAuditContract audit;
    private final EntityManager entityManager;

    public JpaRiskAssessmentRepositoryAdapter(RiskAssessmentJpaRepository repository,
            RiskAssessmentScopeJpaRepository scopes, RiskAssessmentScoringJpaRepository scoring,
            RiskCatalogEntryJpaRepository catalogs, RiskMatrixCellJpaRepository cells, RiskMatrixJpaRepository matrices,
            RiskControlJpaRepository controls, RiskTreatmentPlanJpaRepository treatments,
            RiskEvidenceLinkJpaRepository evidence, RiskEvidenceLookupPort evidenceLookup,
            RiskOrganizationReferenceContract organization, RiskTopologyScopeReferenceContract topology,
            RiskActorContract actors, RiskAssessmentApprovalContract workflow, RiskAssessmentAuditContract audit,
            EntityManager entityManager) {
        this.repository=repository; this.scopes=scopes; this.scoring=scoring; this.catalogs=catalogs;
        this.cells=cells; this.matrices=matrices; this.controls=controls; this.treatments=treatments;
        this.evidence=evidence; this.evidenceLookup=evidenceLookup; this.organization=organization;
        this.topology=topology; this.actors=actors; this.workflow=workflow; this.audit=audit; this.entityManager=entityManager;
    }
    private static void require(boolean condition, String message) {
        if (!condition) throw new InvalidRiskValueException(message);
    }
    private RiskAssessment locked(String id) {
        return repository.findLocked(id).map(RiskPersistenceMapper::toDomain).orElseThrow();
    }
    private void editable(RiskAssessment a) {
        require(a.status()!=RiskAssessmentStatus.APPROVED && a.status()!=RiskAssessmentStatus.ACTIVE,
                "Approved assessment is immutable; create a new review/revision.");
    }
    private void catalog(String id, String family, String oldId) {
        if (id==null) return;
        var entry=catalogs.findLocked(id).orElseThrow();
        require(family.equals(entry.catalogName()) && (Objects.equals(id,oldId) || entry.active()),
                "Assessment catalog must match eligible "+family);
    }
    private void catalogs(RiskAssessment a, RiskAssessment old) {
        catalog(a.assessmentTypeId(),"RISK_ASSESSMENT_TYPE",old==null?null:old.assessmentTypeId());
        catalog(a.methodologyId(),"RISK_METHODLOGY",old==null?null:old.methodologyId());
        catalog(a.inherentLikelihoodId(),"RISK_LIKELIHOOD_LEVEL",old==null?null:old.inherentLikelihoodId());
        catalog(a.residualLikelihoodId(),"RISK_LIKELIHOOD_LEVEL",old==null?null:old.residualLikelihoodId());
        catalog(a.inherentConsequenceId(),"RISK_CONSEQUENCE_LEVEL",old==null?null:old.inherentConsequenceId());
        catalog(a.residualConsequenceId(),"RISK_CONSEQUENCE_LEVEL",old==null?null:old.residualConsequenceId());
        catalog(a.inherentRatingId(),"RISK_RATING",old==null?null:old.inherentRatingId());
        catalog(a.residualRatingId(),"RISK_RATING",old==null?null:old.residualRatingId());
        catalog(a.confidenceLevelId(),"RISK_CONFIDENCE_LEVEL",old==null?null:old.confidenceLevelId());
    }
    private RiskAssessmentScopeJpaEntity scope(String assessmentId, RiskAssessmentScopeInput input, Instant at) {
        require(input!=null && input.scopeType()!=null && !input.scopeType().isBlank()
                && input.scopeId()!=null && !input.scopeId().isBlank(),"Complete assessment scope required.");
        String type=input.scopeType().trim().toUpperCase(Locale.ROOT), id=input.scopeId().trim();
        String code, label;
        if ("ORGANIZATION_UNIT".equals(type)) {
            var source=organization.resolveOrganizationUnit(id).filter(v -> id.equals(v.id())).orElseThrow();
            code=source.code(); label=source.label();
        } else {
            require(Set.of("PIPELINE_SYSTEM","PIPELINE","FACILITY","EQUIPMENT").contains(type),"Unsupported assessment scope type.");
            var source=topology.resolve(type,id).filter(v -> id.equals(v.id())).orElseThrow();
            code=source.code(); label=source.label();
        }
        return new RiskAssessmentScopeJpaEntity(RiskId.newId().value(),assessmentId,type,id,code,label,
                input.topologySnapshotId(),input.operationalPeriodStart(),input.operationalPeriodEnd(),
                input.included(),input.scopeNote(),at);
    }
    private void validateScopes(RiskAssessment a) {
        var existing=scopes.findByRiskAssessmentId(a.id());
        require(!existing.isEmpty(),"Assessment requires at least one scope.");
        for (var row:existing) scope(a.id(),new RiskAssessmentScopeInput(row.scopeType(),row.scopeId(),
                row.topologySnapshotId(),row.operationalPeriodStart(),row.operationalPeriodEnd(),row.included(),row.scopeNote()),Instant.now());
    }
    @Override public RiskAssessment create(RiskAssessment a, List<RiskAssessmentScopeInput> inputs) {
        Objects.requireNonNull(a);
        require(a.status()==RiskAssessmentStatus.DRAFT && !repository.existsById(a.id()),"Only a new DRAFT can be created.");
        require(inputs!=null && !inputs.isEmpty(),"Assessment requires structured scopes.");
        require(a.inherentScore()==null && a.residualScore()==null,"New assessment must use explicit scoring later.");
        var actor=actors.currentActor(Instant.now());
        require(a.assessedByActorId()==null || Objects.equals(actor.id(),a.assessedByActorId()),"Assessor must match authenticated actor.");
        var created=assessed(a,actor);
        catalogs(created,null);
        var children=inputs.stream().map(i -> scope(created.id(),i,created.createdAt())).toList();
        entityManager.persist(RiskPersistenceMapper.toEntity(created));
        for (var child:children) entityManager.persist(child);
        entityManager.flush();
        return created;
    }
    @Override public RiskAssessment save(RiskAssessment a) {
        var old=locked(a.id()); editable(old);
        require(a.status()==old.status(),"Use explicit submission/approval for status changes.");
        require(sameScoring(a,old) && Objects.equals(a.reviewedByActorId(),old.reviewedByActorId())
                && Objects.equals(a.reviewedByDisplayNameSnapshot(),old.reviewedByDisplayNameSnapshot())
                && Objects.equals(a.approvedByActorId(),old.approvedByActorId())
                && Objects.equals(a.approvedByDisplayNameSnapshot(),old.approvedByDisplayNameSnapshot())
                && Objects.equals(a.approvedAt(),old.approvedAt())
                && Objects.equals(a.workflowReferenceId(),old.workflowReferenceId())
                && Objects.equals(a.auditReferenceId(),old.auditReferenceId()),"Governed scoring/approval cannot use generic save.");
        catalogs(a,old); validateScopes(a);
        return persist(a);
    }
    private RiskAssessment persist(RiskAssessment a) {
        return RiskPersistenceMapper.toDomain(repository.saveAndFlush(RiskPersistenceMapper.toEntity(a)));
    }
    @Override @Transactional(readOnly=true) public Optional<RiskAssessment> findById(String id) {
        return repository.findById(id).map(RiskPersistenceMapper::toDomain);
    }
    private RiskMatrixCellJpaEntity selected(String id) {
        var cell=cells.findLocked(id).orElseThrow();
        var matrix=matrices.findLocked(cell.riskMatrixId()).orElseThrow();
        require(matrix.status()==RiskMatrixStatus.ACTIVE && matrix.version()!=null && !matrix.version().isBlank(),
                "Selected matrix must have an active explicit version.");
        catalog(cell.likelihoodLevelId(),"RISK_LIKELIHOOD_LEVEL",null);
        catalog(cell.consequenceLevelId(),"RISK_CONSEQUENCE_LEVEL",null);
        catalog(cell.ratingId(),"RISK_RATING",null);
        return cell;
    }
    private void residualContext(String assessmentId, String type, String id) {
        require(type!=null && id!=null && !id.isBlank(),"Residual scoring requires same-assessment treatment/control context.");
        boolean valid=switch(type) {
            case "CONTROL" -> controls.findById(id).filter(c -> assessmentId.equals(c.riskAssessmentId())).isPresent();
            case "TREATMENT_PLAN" -> treatments.findById(id).filter(t -> assessmentId.equals(t.riskAssessmentId())).isPresent();
            default -> false;
        };
        require(valid,"Residual context must belong to this assessment.");
    }
    @Override public RiskAssessment score(ScoreRiskAssessmentCommand command) {
        actors.currentActor(Instant.now());
        var a=locked(command.assessmentId()); editable(a);
        require(a.status()==RiskAssessmentStatus.DRAFT || a.status()==RiskAssessmentStatus.UNDER_REVIEW,"Assessment is not open for scoring.");
        var inherent=selected(command.inherentCellId());
        var residual=command.residualCellId()==null?null:selected(command.residualCellId());
        if (residual!=null) residualContext(a.id(),command.residualContextType(),command.residualContextId());
        else require(command.residualContextId()==null && command.residualContextType()==null,"Residual context without selected residual cell.");
        catalog(command.confidenceLevelId(),"RISK_CONFIDENCE_LEVEL",null);
        var im=matrices.findLocked(inherent.riskMatrixId()).orElseThrow();
        var rm=residual==null?null:matrices.findLocked(residual.riskMatrixId()).orElseThrow();
        scoring.save(new RiskAssessmentScoringJpaEntity(a.id(),inherent.id(),im.id(),im.version(),
                residual==null?null:residual.id(),rm==null?null:rm.id(),rm==null?null:rm.version(),
                command.residualContextType(),command.residualContextId()));
        return persist(scored(a,inherent,residual,command));
    }
    @Override public RiskAssessment submit(String id) {
        actors.currentActor(Instant.now());
        var a=locked(id); require(a.status()==RiskAssessmentStatus.DRAFT,"Only DRAFT assessment can be submitted.");
        validateScopes(a); catalogs(a,a);
        return persist(submitted(a));
    }
    @Override public RiskAssessment approve(ApproveRiskAssessmentCommand command) {
        var actor=actors.currentActor(Instant.now());
        var a=locked(command.assessmentId());
        require(a.status()==RiskAssessmentStatus.UNDER_REVIEW,"Only submitted assessment can be approved.");
        validateScopes(a); catalogs(a,a);
        var links=evidence.findByRiskAssessmentId(a.id());
        require(!links.isEmpty(),"Assessment must have owner-validated evidence before approval.");
        for (var link:links) evidenceLookup.validate(RiskPersistenceMapper.toDomain(link));
        validateScoring(a);
        var decision=workflow.approve(new RiskAssessmentApprovalContract.Request(a.id(),command.workflowInstanceId(),
                command.taskId(),command.transitionId(),command.reviewActionId(),command.expectedTaskUpdatedAt(),
                command.reasonId(),command.decisionNote(),command.commentText(),command.correlationId()));
        require(actor.id().equals(decision.approverId()),"Approval actor must match authenticated actor.");
        String receipt=audit.appendApproved(new RiskAssessmentAuditContract.ApprovalEvidence(a.id(),a.assessmentNumber(),
                decision.approverId(),decision.approverUsername(),decision.approverDisplayName(),decision.reviewerId(),
                decision.instanceId(),decision.taskId(),decision.actionId(),decision.reviewActionId(),decision.approvedAt()));
        return persist(approved(a,decision,receipt));
    }
    private void validateScoring(RiskAssessment a) {
        if (a.inherentScore()==null && a.residualScore()==null) return;
        var provenance=scoring.findById(a.id()).orElseThrow();
        var inherent=cells.findLocked(provenance.inherentCellId()).orElseThrow();
        var matrix=matrices.findLocked(provenance.inherentMatrixId()).orElseThrow();
        require(inherent.riskMatrixId().equals(matrix.id()) && matrix.version().equals(provenance.inherentMatrixVersion()),"Inherent matrix provenance changed.");
        var residual=provenance.residualCellId()==null?null:cells.findLocked(provenance.residualCellId()).orElseThrow();
        if (residual!=null) {
            var rm=matrices.findLocked(provenance.residualMatrixId()).orElseThrow();
            require(residual.riskMatrixId().equals(rm.id()) && rm.version().equals(provenance.residualMatrixVersion()),"Residual matrix provenance changed.");
            residualContext(a.id(),provenance.residualContextType(),provenance.residualContextId());
        }
        require(sameScoring(a,scored(a,inherent,residual,new ScoreRiskAssessmentCommand(a.id(),inherent.id(),
                residual==null?null:residual.id(),provenance.residualContextType(),provenance.residualContextId(),a.confidenceLevelId(),a.uncertaintyNote()))),
                "Assessment score tuple no longer matches its explicit matrix cells.");
    }
    private static boolean sameScoring(RiskAssessment a, RiskAssessment b) {
        return Objects.equals(a.inherentLikelihoodId(),b.inherentLikelihoodId())
                && Objects.equals(a.inherentConsequenceId(),b.inherentConsequenceId())
                && Objects.equals(a.inherentScore(),b.inherentScore())
                && Objects.equals(a.inherentRatingId(),b.inherentRatingId())
                && Objects.equals(a.residualLikelihoodId(),b.residualLikelihoodId())
                && Objects.equals(a.residualConsequenceId(),b.residualConsequenceId())
                && Objects.equals(a.residualScore(),b.residualScore())
                && Objects.equals(a.residualRatingId(),b.residualRatingId())
                && Objects.equals(a.confidenceLevelId(),b.confidenceLevelId());
    }
    private static RiskAssessment submitted(RiskAssessment a) {
        return new RiskAssessment(
                a.id(),
                a.riskRegisterId(),
                a.assessmentNumber(),
                a.title(),
                a.description(),
                a.assessmentTypeId(),
                a.methodologyId(),
                a.scopeId(),
                a.riskScenarioId(),
                RiskAssessmentStatus.UNDER_REVIEW,
                a.assessmentDate(),
                a.validFrom(),
                a.validTo(),
                a.assessedByActorId(),
                a.assessedByDisplayNameSnapshot(),
                a.reviewedByActorId(),
                a.reviewedByDisplayNameSnapshot(),
                a.approvedByActorId(),
                a.approvedByDisplayNameSnapshot(),
                a.approvedAt(),
                a.inherentLikelihoodId(),
                a.inherentConsequenceId(),
                a.inherentScore(),
                a.inherentRatingId(),
                a.residualLikelihoodId(),
                a.residualConsequenceId(),
                a.residualScore(),
                a.residualRatingId(),
                a.confidenceLevelId(),
                a.uncertaintyNote(),
                a.workflowReferenceId(),
                a.auditReferenceId(),
                a.createdAt(),
                Instant.now());
    }
    private static RiskAssessment scored(RiskAssessment a, RiskMatrixCellJpaEntity inherent, RiskMatrixCellJpaEntity residual, ScoreRiskAssessmentCommand command) {
        return new RiskAssessment(
                a.id(),
                a.riskRegisterId(),
                a.assessmentNumber(),
                a.title(),
                a.description(),
                a.assessmentTypeId(),
                a.methodologyId(),
                a.scopeId(),
                a.riskScenarioId(),
                a.status(),
                a.assessmentDate(),
                a.validFrom(),
                a.validTo(),
                a.assessedByActorId(),
                a.assessedByDisplayNameSnapshot(),
                a.reviewedByActorId(),
                a.reviewedByDisplayNameSnapshot(),
                a.approvedByActorId(),
                a.approvedByDisplayNameSnapshot(),
                a.approvedAt(),
                inherent.likelihoodLevelId(),
                inherent.consequenceLevelId(),
                inherent.scoreValue(),
                inherent.ratingId(),
                residual==null?null:residual.likelihoodLevelId(),
                residual==null?null:residual.consequenceLevelId(),
                residual==null?null:residual.scoreValue(),
                residual==null?null:residual.ratingId(),
                command.confidenceLevelId(),
                command.uncertaintyNote(),
                a.workflowReferenceId(),
                a.auditReferenceId(),
                a.createdAt(),
                Instant.now());
    }
    private static RiskAssessment approved(RiskAssessment a, RiskAssessmentApprovalContract.Approval decision, String receipt) {
        return new RiskAssessment(
                a.id(),
                a.riskRegisterId(),
                a.assessmentNumber(),
                a.title(),
                a.description(),
                a.assessmentTypeId(),
                a.methodologyId(),
                a.scopeId(),
                a.riskScenarioId(),
                RiskAssessmentStatus.APPROVED,
                a.assessmentDate(),
                a.validFrom(),
                a.validTo(),
                a.assessedByActorId(),
                a.assessedByDisplayNameSnapshot(),
                decision.reviewerId(),
                decision.reviewerDisplayName(),
                decision.approverId(),
                decision.approverDisplayName(),
                decision.approvedAt(),
                a.inherentLikelihoodId(),
                a.inherentConsequenceId(),
                a.inherentScore(),
                a.inherentRatingId(),
                a.residualLikelihoodId(),
                a.residualConsequenceId(),
                a.residualScore(),
                a.residualRatingId(),
                a.confidenceLevelId(),
                a.uncertaintyNote(),
                decision.instanceId(),
                receipt,
                a.createdAt(),
                Instant.now());
    }
    private static RiskAssessment assessed(RiskAssessment a, RiskActorContract.Actor actor) {
        return new RiskAssessment(a.id(), a.riskRegisterId(), a.assessmentNumber(), a.title(), a.description(), a.assessmentTypeId(), a.methodologyId(), a.scopeId(), a.riskScenarioId(), a.status(), a.assessmentDate(), a.validFrom(), a.validTo(), actor.id(), actor.displayName(), a.reviewedByActorId(), a.reviewedByDisplayNameSnapshot(), a.approvedByActorId(), a.approvedByDisplayNameSnapshot(), a.approvedAt(), a.inherentLikelihoodId(), a.inherentConsequenceId(), a.inherentScore(), a.inherentRatingId(), a.residualLikelihoodId(), a.residualConsequenceId(), a.residualScore(), a.residualRatingId(), a.confidenceLevelId(), a.uncertaintyNote(), a.workflowReferenceId(), a.auditReferenceId(), a.createdAt(), a.updatedAt());
    }
}
