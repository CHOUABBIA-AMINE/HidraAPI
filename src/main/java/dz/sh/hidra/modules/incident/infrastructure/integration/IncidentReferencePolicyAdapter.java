/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentReferencePolicyAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.infrastructure.integration
 *
 * @Description : Enforces Incident lifecycle integrity and owner-controlled evidence.
 *
 */
package dz.sh.hidra.modules.incident.infrastructure.integration;

import dz.sh.hidra.modules.incident.application.port.out.IncidentReferencePolicyPort;
import dz.sh.hidra.modules.incident.domain.model.Incident;
import dz.sh.hidra.modules.identity.application.contract.incident.IncidentActorContract;
import dz.sh.hidra.modules.organization.application.contract.incident.IncidentOrganizationContract;
import dz.sh.hidra.modules.topology.application.contract.incident.IncidentTopologyContract;
import dz.sh.hidra.modules.workflow.application.contract.incident.IncidentWorkflowContract;
import java.time.Instant;
import java.util.Objects;
import org.springframework.stereotype.Component;
@Component
public final class IncidentReferencePolicyAdapter implements IncidentReferencePolicyPort {
    private final IncidentActorContract actors;
    private final IncidentOrganizationContract units;
    private final IncidentTopologyContract assets;
    private final IncidentWorkflowContract workflows;
    public IncidentReferencePolicyAdapter(IncidentActorContract actors,IncidentOrganizationContract units,
            IncidentTopologyContract assets,IncidentWorkflowContract workflows) {
        this.actors=Objects.requireNonNull(actors);this.units=Objects.requireNonNull(units);
        this.assets=Objects.requireNonNull(assets);this.workflows=Objects.requireNonNull(workflows);
    }
    public Reference currentActor(Instant at) {var a=actors.currentActor(at);return new Reference(a.id(),null,a.displayName());}
    public Reference organizationUnit(String id) {var x=units.resolve(id).orElseThrow(() -> new IllegalArgumentException("Unknown Organization unit."));return new Reference(x.id(),x.code(),x.label());}
    public Reference topologyAsset(String type,String id) {var x=assets.resolve(type,id).orElseThrow(() -> new IllegalArgumentException("Unknown typed Topology asset."));return new Reference(x.id(),x.code(),x.label());}
    public void validate(Incident x,Incident old) {
        if(old==null || !Objects.equals(old.createdByActorId(),x.createdByActorId())) {
            if(!currentActor(Instant.now()).id().equals(x.createdByActorId())) throw new SecurityException("Incident creator must be authenticated actor.");
        }
        if(x.responsibleActorId()!=null && (old==null || !Objects.equals(old.responsibleActorId(),x.responsibleActorId()))
                && actors.eligibleActor(x.responsibleActorId(),Instant.now()).isEmpty()) throw new IllegalArgumentException("Unknown eligible responsible actor.");
        if(x.responsibleOrganizationUnitId()!=null && (old==null || !Objects.equals(old.responsibleOrganizationUnitId(),x.responsibleOrganizationUnitId()))) organizationUnit(x.responsibleOrganizationUnitId());
        if((x.topologyAssetId()==null)!=(x.topologyAssetTypeCode()==null)) throw new IllegalArgumentException("Topology type and identifier must be supplied together.");
        if(x.topologyAssetId()!=null && (old==null || !Objects.equals(old.topologyAssetId(),x.topologyAssetId()) || !Objects.equals(old.topologyAssetTypeCode(),x.topologyAssetTypeCode()))) topologyAsset(x.topologyAssetTypeCode(),x.topologyAssetId());
        if(x.workflowInstanceId()!=null && (old==null || !Objects.equals(old.workflowInstanceId(),x.workflowInstanceId())) && !workflows.exists(x.workflowInstanceId())) throw new IllegalArgumentException("Unknown Workflow instance.");
    }
}
