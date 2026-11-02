/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InstalledCharacteristicLinkResponses
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-01
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.response
 *
 * @Description : Explicit immutable link response and bounded historical page without source payloads.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.response;
import java.time.Instant;
import java.util.List;
public final class InstalledCharacteristicLinkResponses {
    private InstalledCharacteristicLinkResponses(){}
    @io.swagger.v3.oas.annotations.media.Schema(name="TopologyInstalledCharacteristicLinkRecord")
    public record Link(String linkId,long linkRevision,String companyPartyId,String installedUnitId,
            String sourceId,String sourceRevisionId,String sourceSha256,String kind,
            Instant effectiveFrom,Instant effectiveUntil,Instant recordedAt,
            String evidenceReference,String installationId,String state){}
    @io.swagger.v3.oas.annotations.media.Schema(name="TopologyInstalledCharacteristicLinkPage")
    public record Page(Link link,List<Link> history,boolean replayed,Long nextBeforeRevision){
        public Page{history=List.copyOf(history);}
    }
    @io.swagger.v3.oas.annotations.media.Schema(name="TopologyInstalledCharacteristicApprovalWorkflow")
    public record WorkflowEvidence(String definitionId,int definitionVersion,
            String targetTypeId,String targetTypeCode,String purposeId,String purposeCode,
            String instanceId,String taskId,String actionId,String actorUserId,Instant actedAt){}
    @io.swagger.v3.oas.annotations.media.Schema(name="TopologyInstalledCharacteristicApprovalDecision")
    public record ApprovalDecision(Link target,String draftActorUserId,String approvingActorUserId,
            String targetSha256,WorkflowEvidence workflow,Instant approvedAt){}
    @io.swagger.v3.oas.annotations.media.Schema(name="TopologyInstalledCharacteristicApprovalPage")
    public record ApprovalPage(ApprovalDecision approval,List<ApprovalDecision> history,
            boolean replayed,Long nextBeforeRevision){
        public ApprovalPage{history=List.copyOf(history);}
    }
}
