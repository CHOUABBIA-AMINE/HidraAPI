/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MaintenanceWorkOrderReferenceValidation
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.infrastructure.persistence.adapter
 *
 * @Description : Validates owner-controlled MaintenanceWorkOrder references and preserves historical evidence.
 *
 */
package dz.sh.hidra.modules.assets.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.assets.domain.model.MaintenanceWorkOrder;
import dz.sh.hidra.modules.assets.domain.exception.InvalidAssetsValueException;
import dz.sh.hidra.modules.identity.application.contract.assets.MaintenanceWorkOrderActorReferenceContract;
import dz.sh.hidra.modules.workflow.application.contract.assets.MaintenanceWorkOrderWorkflowReferenceContract;
import dz.sh.hidra.modules.integrity.application.contract.assets.MaintenanceRecommendationReferenceContract;
import dz.sh.hidra.modules.organization.application.contract.assets.AssetsOrganizationUnitReferenceContract;
import dz.sh.hidra.modules.assets.infrastructure.persistence.repository.MaintenancePlanJpaRepository;
import java.time.Instant;
import java.util.Objects;
import org.springframework.stereotype.Component;
@Component
public final class MaintenanceWorkOrderReferenceValidation {
    private final MaintenanceWorkOrderActorReferenceContract actors;
    private final MaintenanceWorkOrderWorkflowReferenceContract workflow;
    private final MaintenanceRecommendationReferenceContract recommendations;
    private final AssetsOrganizationUnitReferenceContract units;
    private final MaintenancePlanJpaRepository maintenancePlanId;
    public MaintenanceWorkOrderReferenceValidation(MaintenanceWorkOrderActorReferenceContract actors, MaintenanceWorkOrderWorkflowReferenceContract workflow, MaintenanceRecommendationReferenceContract recommendations, AssetsOrganizationUnitReferenceContract units, MaintenancePlanJpaRepository maintenancePlanId) {
        this.actors=Objects.requireNonNull(actors); this.workflow=Objects.requireNonNull(workflow);
        this.recommendations=Objects.requireNonNull(recommendations); this.units=Objects.requireNonNull(units);
        this.maintenancePlanId=Objects.requireNonNull(maintenancePlanId);
    }
    /** Called inside the parent save transaction after locking existing provenance. */
    public void validate(MaintenanceWorkOrder model,MaintenanceWorkOrder previous) {
        Objects.requireNonNull(model);
        Instant at=Instant.now();
        if(model.maintenancePlanId()!=null && !maintenancePlanId.existsById(model.maintenancePlanId())) throw new InvalidAssetsValueException("Missing local maintenancePlanId reference.");
        if(changed(model.assignedActorId(), previous==null?null:previous.assignedActorId()) && !actors.eligible(model.assignedActorId(),at))
            throw new InvalidAssetsValueException("Ineligible Identity assignedActorId reference.");
        if(changed(model.createdByActorId(), previous==null?null:previous.createdByActorId()) && !actors.eligible(model.createdByActorId(),at))
            throw new InvalidAssetsValueException("Ineligible Identity createdByActorId reference.");
        if(changed(model.sourceRecommendationId(), previous==null?null:previous.sourceRecommendationId()) && !recommendations.exists(model.sourceRecommendationId()))
            throw new InvalidAssetsValueException("Integrity recommendation does not exist.");
        if(changed(model.assignedOrganizationUnitId(), previous==null?null:previous.assignedOrganizationUnitId()) && !units.exists(model.assignedOrganizationUnitId()))
            throw new InvalidAssetsValueException("Assigned Organization unit does not exist.");
        if(changed(model.workflowInstanceId(),previous==null?null:previous.workflowInstanceId()) && !workflow.matches(model.workflowInstanceId(),model.id()))
            throw new InvalidAssetsValueException("Workflow does not target this MaintenanceWorkOrder context.");
    }
    private static boolean changed(String current,String previous) {return current!=null && !Objects.equals(current,previous);}
}
