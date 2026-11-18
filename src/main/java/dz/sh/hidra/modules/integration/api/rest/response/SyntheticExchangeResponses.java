/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : SyntheticExchangeResponses
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-18
 * @Type        : Class
 * @Layer       : API
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.api.rest.response
 * @Description : T26 bounded synthetic exchange REST contract.
 */
package dz.sh.hidra.modules.integration.api.rest.response;
import java.time.Instant;
import java.util.List;
public final class SyntheticExchangeResponses {
    private SyntheticExchangeResponses() {}
    public record T26SyntheticMapping(String mappingProfileId,String schemaVersionId,
            String schemaChecksum,String mappingDigest,String documentTypeId,String classificationId) {}
    public record T26SyntheticExchange(String exchangeId,String status,String mappingProfileId,
            String schemaVersionId,String schemaChecksum,String mappingDigest,String payloadHash,
            int latestAttempt,String failureCode,String documentId,boolean replayed) {}
    public record T26SyntheticAttempt(int ordinal,String status,String actorId,String jobRunId,
            String messageId,String inboundId,String deadLetterId,String failureCode,String reason,
            Instant claimedAt,Instant completedAt) {}
    public record T26SyntheticAttempts(List<T26SyntheticAttempt> attempts) {}
}
