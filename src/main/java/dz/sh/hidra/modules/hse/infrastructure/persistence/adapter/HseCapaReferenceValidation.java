/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HseCapaReferenceValidation
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

import dz.sh.hidra.modules.hse.domain.model.HseCorrectivePreventiveAction;
import dz.sh.hidra.modules.hse.infrastructure.configuration.HseCatalogFieldPolicy;
import dz.sh.hidra.modules.hse.infrastructure.persistence.repository.HseCatalogEntryJpaRepository;
import dz.sh.hidra.modules.identity.application.contract.hse.HseActorContract;
import dz.sh.hidra.modules.organization.application.contract.hse.HseOrganizationReferenceContract;
import dz.sh.hidra.modules.assets.application.contract.hse.HseWorkOrderReferenceContract;
import dz.sh.hidra.modules.workflow.application.contract.hse.HseWorkflowReferenceContract;
import java.time.Instant;
import java.util.Objects;
import org.springframework.stereotype.Component;
@Component
public class HseCapaReferenceValidation {
    private final HseCatalogFieldPolicy policy;
    private final HseCatalogEntryJpaRepository catalogs;
    private final HseActorContract actors;
    private final HseOrganizationReferenceContract units;
    private final HseWorkOrderReferenceContract workOrders;
    private final HseWorkflowReferenceContract workflows;
    public HseCapaReferenceValidation(HseCatalogFieldPolicy policy,HseCatalogEntryJpaRepository catalogs,HseActorContract actors,
            HseOrganizationReferenceContract units,HseWorkOrderReferenceContract workOrders,HseWorkflowReferenceContract workflows) {
        this.policy=Objects.requireNonNull(policy);this.catalogs=Objects.requireNonNull(catalogs);this.actors=Objects.requireNonNull(actors);
        this.units=Objects.requireNonNull(units);this.workOrders=Objects.requireNonNull(workOrders);this.workflows=Objects.requireNonNull(workflows);
    }
    public HseCorrectivePreventiveAction validate(HseCorrectivePreventiveAction value,HseCorrectivePreventiveAction old) {
        boolean newType=old==null || !Objects.equals(value.actionTypeId(),old.actionTypeId());
        String family=policy.requiredFamily("CAPA_ACTION_TYPE",newType);
        var type=catalogs.findByIdForShare(value.actionTypeId()).orElseThrow(() -> new IllegalArgumentException("Known CAPA action type required."));
        if(!value.actionTypeId().equals(type.id()) || !family.equals(type.catalogName()) || (newType && !type.active()))
            throw new IllegalArgumentException("Exact eligible CAPA action-type family required.");
        String actorSnapshot=value.ownerDisplayNameSnapshot(),unitSnapshot=value.ownerOrganizationUnitNameSnapshot();
        if(changed(value.ownerActorId(),old==null?null:old.ownerActorId()))
            actorSnapshot=actors.eligibleActor(value.ownerActorId(),Instant.now()).filter(a -> value.ownerActorId().equals(a.id()))
                    .orElseThrow(() -> new IllegalArgumentException("Eligible CAPA owner required.")).displayName();
        else if(old!=null && !Objects.equals(actorSnapshot,old.ownerDisplayNameSnapshot()))
            throw new IllegalArgumentException("Historical CAPA owner snapshot cannot be overwritten.");
        if(changed(value.verifiedByActorId(),old==null?null:old.verifiedByActorId())
                && actors.eligibleActor(value.verifiedByActorId(),Instant.now()).filter(a -> value.verifiedByActorId().equals(a.id())).isEmpty())
            throw new IllegalArgumentException("Eligible CAPA verifier required.");
        if(changed(value.ownerOrganizationUnitId(),old==null?null:old.ownerOrganizationUnitId()))
            unitSnapshot=units.resolve(value.ownerOrganizationUnitId()).filter(u -> value.ownerOrganizationUnitId().equals(u.id()))
                    .orElseThrow(() -> new IllegalArgumentException("Known CAPA organization unit required.")).label();
        else if(old!=null && !Objects.equals(unitSnapshot,old.ownerOrganizationUnitNameSnapshot()))
            throw new IllegalArgumentException("Historical CAPA unit snapshot cannot be overwritten.");
        if(changed(value.linkedWorkOrderId(),old==null?null:old.linkedWorkOrderId()) && !workOrders.exists(value.linkedWorkOrderId()))
            throw new IllegalArgumentException("Known Assets work order required.");
        if(value.workflowTaskId()!=null && (old==null || !Objects.equals(value.workflowTaskId(),old.workflowTaskId()) || !Objects.equals(value.hseCaseId(),old.hseCaseId()))
                && !workflows.taskMatches(value.workflowTaskId(),value.hseCaseId(),value.id()))
            throw new IllegalArgumentException("Workflow task must target actual HSE case or CAPA with configured binding.");
        return new HseCorrectivePreventiveAction(value.id(),value.hseCaseId(),value.actionNumber(),value.actionTypeId(),value.title(),value.description(),
                value.ownerActorId(),actorSnapshot,value.ownerOrganizationUnitId(),unitSnapshot,value.targetDate(),value.completedAt(),value.verificationRequired(),
                value.verifiedByActorId(),value.verifiedAt(),value.status(),value.linkedWorkOrderId(),value.workflowTaskId(),value.createdAt(),value.updatedAt());
    }
    private boolean changed(String value,String old) {return value!=null && !Objects.equals(value,old);}
}
