/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringBaselinePublicationResponses
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-12
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.response
 *
 * @Description : Implements the exact engineering baseline publication contract.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.response;

import dz.sh.hidra.modules.topology.api.rest.response.EngineeringBaselineDraftResponses.SourcePin;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public final class EngineeringBaselinePublicationResponses {
    private EngineeringBaselinePublicationResponses() {}

    public record Submitted(UUID submissionId, UUID manifestId, long version, String canonicalSha256,
                            String companyPartyId, String pipelineSystemId, String workflowTargetTypeId,
                            String workflowPurposeId, String workflowTargetCode,
                            String workflowPurposeCode, String workflowLocator, String workflowCorrelationId,
                            String submittedBy, Instant submittedAt) {}

    public record Published(UUID receiptId, long epoch, String state, Submitted submission,
                            List<SourcePin> pins, Instant effectiveFrom, Instant effectiveUntil,
                            String reviewerId, String workflowInstanceId, String workflowTaskId,
                            String workflowActionId, Instant reviewedAt, String publishedBy,
                            Instant publishedAt, boolean engineeringQualified) {}
}
