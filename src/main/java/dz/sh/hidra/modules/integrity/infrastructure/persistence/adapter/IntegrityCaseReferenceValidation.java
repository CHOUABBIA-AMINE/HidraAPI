/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrityCaseReferenceValidation
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.infrastructure.persistence.adapter
 *
 * @Description : Validates IntegrityCase provenance through explicit owner-controlled references.
 *
 */
package dz.sh.hidra.modules.integrity.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.integrity.domain.model.IntegrityCase;
import dz.sh.hidra.modules.integrity.domain.exception.InvalidIntegrityValueException;
import dz.sh.hidra.modules.integrity.infrastructure.configuration.IntegrityCatalogFieldPolicy;
import dz.sh.hidra.modules.integrity.infrastructure.persistence.repository.*;
import dz.sh.hidra.modules.topology.application.contract.integrity.IntegrityCaseTopologyReferenceContract;
import dz.sh.hidra.modules.identity.application.contract.integrity.IntegrityCaseActorReferenceContract;
import dz.sh.hidra.modules.organization.application.contract.integrity.IntegrityOrganizationUnitReferenceContract;
import dz.sh.hidra.modules.workflow.application.contract.integrity.IntegrityCaseWorkflowReferenceContract;
import java.time.Instant;
import java.util.Objects;
import org.springframework.stereotype.Component;
@Component
public class IntegrityCaseReferenceValidation {
    private final IntegrityCatalogFieldPolicy policy;
    private final IntegrityCatalogEntryJpaRepository catalogs;
    private final PipelineDefectJpaRepository defects;
    private final IntegrityCaseTopologyReferenceContract topology;
    private final IntegrityCaseActorReferenceContract actors;
    private final IntegrityOrganizationUnitReferenceContract units;
    private final IntegrityCaseWorkflowReferenceContract workflows;
    public IntegrityCaseReferenceValidation(IntegrityCatalogFieldPolicy policy,IntegrityCatalogEntryJpaRepository catalogs,PipelineDefectJpaRepository defects,
            IntegrityCaseTopologyReferenceContract topology,IntegrityCaseActorReferenceContract actors,
            IntegrityOrganizationUnitReferenceContract units,IntegrityCaseWorkflowReferenceContract workflows) {
        this.policy=Objects.requireNonNull(policy);this.catalogs=Objects.requireNonNull(catalogs);this.defects=Objects.requireNonNull(defects);
        this.topology=Objects.requireNonNull(topology);this.actors=Objects.requireNonNull(actors);this.units=Objects.requireNonNull(units);this.workflows=Objects.requireNonNull(workflows);
    }
    /** Existing row and owner/local references are checked inside the case-save transaction. */
    public IntegrityCase validate(IntegrityCase value,IntegrityCase old) {
        Objects.requireNonNull(value);
        boolean newType=old==null || !Objects.equals(value.caseTypeId(),old.caseTypeId());
        String family=policy.requiredFamily("CASE_TYPE",newType);
        var type=catalogs.findByIdForShare(value.caseTypeId()).orElseThrow(() -> new InvalidIntegrityValueException("Known Integrity case type required."));
        if(!value.caseTypeId().equals(type.id()) || !family.equals(type.catalogName()) || (newType && !type.active()))
            throw new InvalidIntegrityValueException("Exact eligible Integrity case-type catalog family required.");
        if(value.primaryDefectId()!=null && defects.findByIdForShare(value.primaryDefectId()).filter(d -> value.primaryDefectId().equals(d.id())).isEmpty())
            throw new InvalidIntegrityValueException("Known optional primary defect required.");
        // Current Integrity policy establishes existence, not defect status or topology equality.
        boolean newTarget=old==null || !Objects.equals(value.topologyAssetTypeCode(),old.topologyAssetTypeCode())
                || !Objects.equals(value.topologyAssetId(),old.topologyAssetId());
        String snapshot=value.topologyAssetCodeSnapshot();
        if(newTarget) snapshot=topology.resolve(value.topologyAssetTypeCode(),value.topologyAssetId())
                .filter(a -> value.topologyAssetId().equals(a.id())).orElseThrow(() -> new InvalidIntegrityValueException("Known typed Topology asset required.")).code();
        else if(!Objects.equals(snapshot,old.topologyAssetCodeSnapshot()))
            throw new InvalidIntegrityValueException("Historical Topology snapshot cannot be overwritten.");
        if(changed(value.openedByActorId(),old==null?null:old.openedByActorId()) && !actors.eligible(value.openedByActorId(),Instant.now()))
            throw new InvalidIntegrityValueException("Eligible optional opening actor required.");
        if(changed(value.responsibleOrganizationUnitId(),old==null?null:old.responsibleOrganizationUnitId()) && !units.exists(value.responsibleOrganizationUnitId()))
            throw new InvalidIntegrityValueException("Known optional responsible organization unit required.");
        if(changed(value.workflowInstanceId(),old==null?null:old.workflowInstanceId()) && !workflows.matches(value.workflowInstanceId(),value.id()))
            throw new InvalidIntegrityValueException("Workflow must target this IntegrityCase with configured type/purpose binding.");
        return new IntegrityCase(value.id(),value.caseNumber(),value.title(),value.description(),value.caseTypeId(),value.status(),value.severityId(),
                value.topologyAssetTypeCode(),value.topologyAssetId(),snapshot,value.primaryDefectId(),value.sourceIncidentId(),value.sourceHseCaseId(),
                value.responsibleOrganizationUnitId(),value.workflowInstanceId(),value.openedAt(),value.closedAt(),value.openedByActorId(),value.createdAt(),value.updatedAt());
    }
    private boolean changed(String value,String old) {return value!=null && !Objects.equals(value,old);}
}
