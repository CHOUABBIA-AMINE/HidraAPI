/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InstalledCharacteristicLinkRequests
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-01
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.request
 *
 * @Description : Explicit draft-link REST input types; no persistence model exposure.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.request;
import java.time.Instant;
public final class InstalledCharacteristicLinkRequests {
    private InstalledCharacteristicLinkRequests(){}
    public record CharacteristicLinkDraftSave(String companyPartyId,String requestId,Long expectedEpoch,
            Long linkRevision,String sourceId,String sourceRevisionId,String sourceSha256,
            String kind,Instant effectiveFrom,Instant effectiveUntil,Instant recordedAt,
            String evidenceReference,String installationId){
        @Override public String toString(){return "CharacteristicDraftSave[redacted]";}
    }
    /** These are immutable Workflow lookup keys, never caller-provided actor/decision evidence. */
    public record CharacteristicLinkWorkflowReference(String definitionId,int definitionVersion,
            String targetTypeId,String purposeId,String instanceId,String taskId,String actionId){
        @Override public String toString(){return "CharacteristicLinkWorkflowReference[redacted]";}
    }
    /** The exact target SHA is computed from stored DRAFT data inside the protected transaction. */
    public record CharacteristicLinkApprovalCommand(String companyPartyId,String requestId,
            Long expectedEpoch,CharacteristicLinkWorkflowReference workflow){
        @Override public String toString(){return "CharacteristicLinkApprovalCommand[redacted]";}
    }
}
