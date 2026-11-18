/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : SyntheticExchangeUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-18
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.application.port.in
 * @Description : Five scoped T26 synthetic exchange operations.
 */
package dz.sh.hidra.modules.integration.application.port.in;
import dz.sh.hidra.modules.integration.domain.model.SyntheticExchange.Scope;
import java.time.Instant;
import java.util.List;

public interface SyntheticExchangeUseCase {
    record Submit(String requestKey,String mappingProfileId,String schemaVersionId,String titleFr) {}
    record Replay(String requestKey,int expectedAttempt,String reason) {}
    record Mapping(String profileId,String schemaVersionId,String schemaChecksum,String mappingDigest,
            String documentTypeId,String classificationId) {}
    record Exchange(String exchangeId,String status,String mappingProfileId,String schemaVersionId,
            String schemaChecksum,String mappingDigest,String payloadHash,int latestAttempt,
            String failureCode,String documentId,boolean replayed) {}
    record AttemptView(int ordinal,String status,String actorId,String jobRunId,
            String messageId,String inboundId,String deadLetterId,String failureCode,
            String reason,Instant claimedAt,Instant completedAt) {}
    Mapping mapping(Scope scope,String profileId);
    Exchange submit(Scope scope,Submit request);
    Exchange detail(Scope scope,String exchangeId);
    List<AttemptView> attempts(Scope scope,String exchangeId);
    Exchange replay(Scope scope,String exchangeId,Replay request);
}
