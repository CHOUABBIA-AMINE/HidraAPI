/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HseCaseReferenceValidation
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.infrastructure.persistence.adapter
 *
 * @Description : Enforces HSE-owned lifecycle integrity through owner-controlled references.
 *
 */
package dz.sh.hidra.modules.hse.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.hse.domain.model.HseCase;
import dz.sh.hidra.modules.identity.application.contract.hse.HseActorContract;
import dz.sh.hidra.modules.organization.application.contract.hse.HseOrganizationReferenceContract;
import dz.sh.hidra.modules.workflow.application.contract.hse.HseWorkflowReferenceContract;
import java.time.Instant;
import java.util.Objects;
import org.springframework.stereotype.Component;
@Component
public class HseCaseReferenceValidation {
    private final HseActorContract actors;
    private final HseOrganizationReferenceContract units;
    private final HseWorkflowReferenceContract workflows;
    public HseCaseReferenceValidation(HseActorContract actors,HseOrganizationReferenceContract units,HseWorkflowReferenceContract workflows) {
        this.actors=Objects.requireNonNull(actors);this.units=Objects.requireNonNull(units);this.workflows=Objects.requireNonNull(workflows);
    }
    public void validate(HseCase value,HseCase old) {
        if(changed(value.reportedByActorId(),old==null?null:old.reportedByActorId())
                && actors.eligibleActor(value.reportedByActorId(),Instant.now()).filter(a -> value.reportedByActorId().equals(a.id())).isEmpty())
            throw new IllegalArgumentException("Eligible reporting actor required.");
        if(changed(value.responsibleOrganizationUnitId(),old==null?null:old.responsibleOrganizationUnitId())
                && units.resolve(value.responsibleOrganizationUnitId()).filter(u -> value.responsibleOrganizationUnitId().equals(u.id())).isEmpty())
            throw new IllegalArgumentException("Known responsible organization unit required.");
        if(changed(value.workflowInstanceId(),old==null?null:old.workflowInstanceId())
                && !workflows.caseMatches(value.workflowInstanceId(),value.id()))
            throw new IllegalArgumentException("Workflow must target this HSE case with an active binding.");
        if(old!=null && Objects.equals(value.reportedByActorId(),old.reportedByActorId())
                && !Objects.equals(value.reportedByDisplayNameSnapshot(),old.reportedByDisplayNameSnapshot()))
            throw new IllegalArgumentException("Historical reporter snapshot cannot be overwritten.");
        if(old!=null && Objects.equals(value.responsibleOrganizationUnitId(),old.responsibleOrganizationUnitId())
                && !Objects.equals(value.responsibleOrganizationUnitNameSnapshot(),old.responsibleOrganizationUnitNameSnapshot()))
            throw new IllegalArgumentException("Historical organization snapshot cannot be overwritten.");
    }
    private boolean changed(String value,String old) {return value!=null && !Objects.equals(value,old);}
}
