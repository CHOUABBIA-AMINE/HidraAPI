/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringBaselinePublicationRequests
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-12
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.request
 *
 * @Description : Implements the exact engineering baseline publication contract.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.request;

import java.util.UUID;

public final class EngineeringBaselinePublicationRequests {
    private EngineeringBaselinePublicationRequests() {}
    public record Submit(String companyPartyId, String pipelineSystemId, UUID requestId,
                         long version, String canonicalSha256) {}
    public record WorkflowDecision(String definitionId, int definitionVersion, String targetTypeId,
                                   String purposeId, String instanceId, String taskId, String actionId) {}
    public record Publish(String companyPartyId, String pipelineSystemId, UUID requestId,
                          long expectedEpoch, WorkflowDecision decision) {}
}
