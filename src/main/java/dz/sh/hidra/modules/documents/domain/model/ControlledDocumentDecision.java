/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : ControlledDocumentDecision
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-17
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.domain.model
 * @Description : Immutable controlled-document custodian acceptance receipt.
 */
package dz.sh.hidra.modules.documents.domain.model;

import java.time.Instant;
import java.util.Objects;

public record ControlledDocumentDecision(
        String id, String documentId, String versionId, String previousVersionId,
        String requestId, String payloadDigest, String actorId, String reason,
        String action, String checksumSha256, long contentLength, String companyId,
        String facilityId, String linkRoleId, String linkId, Instant decidedAt
) {
    public ControlledDocumentDecision {
        for (String v : new String[] {id,documentId,versionId,requestId,payloadDigest,
                actorId,reason,action,checksumSha256,companyId,facilityId,linkRoleId,linkId}) {
            if (v==null || v.isBlank()) throw new IllegalArgumentException("Decision evidence is incomplete.");
        }
        if (!action.equals("ACCEPT") && !action.equals("SUPERSEDE"))
            throw new IllegalArgumentException("Unsupported custodian decision.");
        if ((previousVersionId==null)!=action.equals("ACCEPT"))
            throw new IllegalArgumentException("Predecessor and decision disagree.");
        if (contentLength<0 || !checksumSha256.matches("[0-9a-f]{64}") || decidedAt==null)
            throw new IllegalArgumentException("Invalid immutable binary evidence.");
        Objects.requireNonNull(decidedAt);
    }
}
