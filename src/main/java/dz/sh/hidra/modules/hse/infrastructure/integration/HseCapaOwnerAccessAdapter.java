/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : HseCapaOwnerAccessAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-18
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.infrastructure.integration
 * @Description : Sole HSE cross-owner integration bridge into five approved scalar exports.
 */
package dz.sh.hidra.modules.hse.infrastructure.integration;

import dz.sh.hidra.modules.hse.application.port.out.*;
import dz.sh.hidra.modules.identity.application.contract.hse.HseCapaScopedAuthorizationContract;
import dz.sh.hidra.modules.topology.application.contract.hse.HseFacilityOwnershipContract;
import dz.sh.hidra.modules.incident.application.contract.hse.HseIncidentSourceContract;
import dz.sh.hidra.modules.documents.application.contract.hse.HseControlledDocumentEvidenceContract;
import dz.sh.hidra.modules.workflow.application.contract.hse.HseCapaDecisionContract;
import java.util.Optional;
import org.springframework.stereotype.Component;
@Component
public final class HseCapaOwnerAccessAdapter implements HseCapaAuthorityPort,
        HseCapaFacilityPort,HseCapaIncidentPort,HseCapaDocumentsPort,HseCapaWorkflowPort {
    private final HseCapaScopedAuthorizationContract authority;
    private final HseFacilityOwnershipContract topology;
    private final HseIncidentSourceContract incident;
    private final HseControlledDocumentEvidenceContract documents;
    private final HseCapaDecisionContract workflow;
    public HseCapaOwnerAccessAdapter(HseCapaScopedAuthorizationContract authority,
            HseFacilityOwnershipContract topology,HseIncidentSourceContract incident,
            HseControlledDocumentEvidenceContract documents,HseCapaDecisionContract workflow){
        this.authority=authority;this.topology=topology;this.incident=incident;
        this.documents=documents;this.workflow=workflow;
    }
    @Override public HseCapaAuthorityPort.Actor require(String permission,String company,String facility){
        var actor=authority.require(permission,company,facility);
        return new HseCapaAuthorityPort.Actor(actor.id(),actor.displayName());
    }
    @Override public Optional<HseCapaFacilityPort.Facility> active(String facility){
        return topology.active(facility).map(v->new HseCapaFacilityPort.Facility(
                v.id(),v.companyId(),v.code(),v.label()));
    }
    @Override public Optional<HseCapaIncidentPort.Source> reviewed(String id,String company,String facility){
        return incident.reviewed(id,company,facility).map(v->new HseCapaIncidentPort.Source(
                v.id(),v.code(),v.title(),v.companyId(),v.facilityId(),v.reviewerId(),v.reviewVersion()));
    }
    @Override public Optional<HseCapaDocumentsPort.Evidence> accepted(String company,String facility,String document,String version){
        return documents.accepted(company,facility,document,version).map(v->new HseCapaDocumentsPort.Evidence(
                v.documentId(),v.versionId(),v.acceptedLinkId(),v.checksumSha256(),
                v.contentLength(),v.companyId(),v.facilityId()));
    }
    @Override public Optional<HseCapaWorkflowPort.Decision> approved(String actionId,String type,String target){
        return workflow.approved(actionId,type,target).map(v->new HseCapaWorkflowPort.Decision(
                v.actionId(),v.instanceId(),v.taskId(),v.targetId(),v.approverId(),v.digest()));
    }
}
