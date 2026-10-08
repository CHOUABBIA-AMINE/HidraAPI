/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrityAssessmentReferenceValidation
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.infrastructure.persistence.adapter
 *
 * @Description : Validates owner-controlled IntegrityAssessment references and preserves historical evidence.
 *
 */
package dz.sh.hidra.modules.integrity.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.integrity.domain.model.IntegrityAssessment;
import dz.sh.hidra.modules.integrity.domain.exception.InvalidIntegrityValueException;
import dz.sh.hidra.modules.identity.application.contract.integrity.IntegrityAssessmentActorReferenceContract;
import dz.sh.hidra.modules.workflow.application.contract.integrity.IntegrityAssessmentWorkflowReferenceContract;
import dz.sh.hidra.modules.integrity.infrastructure.persistence.repository.IntegrityProgramJpaRepository;
import java.time.Instant;
import java.util.Objects;
import org.springframework.stereotype.Component;
@Component
public final class IntegrityAssessmentReferenceValidation {
    private final IntegrityAssessmentActorReferenceContract actors;
    private final IntegrityAssessmentWorkflowReferenceContract workflow;
    private final IntegrityProgramJpaRepository programId;
    public IntegrityAssessmentReferenceValidation(IntegrityAssessmentActorReferenceContract actors, IntegrityAssessmentWorkflowReferenceContract workflow, IntegrityProgramJpaRepository programId) {
        this.actors=Objects.requireNonNull(actors); this.workflow=Objects.requireNonNull(workflow);
        this.programId=Objects.requireNonNull(programId);
    }
    /** Called inside the parent save transaction after locking existing provenance. */
    public void validate(IntegrityAssessment model,IntegrityAssessment previous) {
        Objects.requireNonNull(model);
        Instant at=Instant.now();
        if(model.programId()!=null && !programId.existsById(model.programId())) throw new InvalidIntegrityValueException("Missing local programId reference.");
        if(changed(model.assessedByActorId(), previous==null?null:previous.assessedByActorId()) && !actors.eligible(model.assessedByActorId(),at))
            throw new InvalidIntegrityValueException("Ineligible Identity assessedByActorId reference.");
        if(changed(model.reviewedByActorId(), previous==null?null:previous.reviewedByActorId()) && !actors.eligible(model.reviewedByActorId(),at))
            throw new InvalidIntegrityValueException("Ineligible Identity reviewedByActorId reference.");
        if(changed(model.approvedByActorId(), previous==null?null:previous.approvedByActorId()) && !actors.eligible(model.approvedByActorId(),at))
            throw new InvalidIntegrityValueException("Ineligible Identity approvedByActorId reference.");
        if(changed(model.workflowInstanceId(),previous==null?null:previous.workflowInstanceId()) && !workflow.matches(model.workflowInstanceId(),model.id()))
            throw new InvalidIntegrityValueException("Workflow does not target this IntegrityAssessment context.");
    }
    private static boolean changed(String current,String previous) {return current!=null && !Objects.equals(current,previous);}
}
