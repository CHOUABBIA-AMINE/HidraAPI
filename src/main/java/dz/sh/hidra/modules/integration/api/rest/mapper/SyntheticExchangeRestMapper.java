/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : SyntheticExchangeRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-18
 * @Type        : Class
 * @Layer       : API
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.api.rest.mapper
 * @Description : T26 bounded synthetic exchange REST contract.
 */
package dz.sh.hidra.modules.integration.api.rest.mapper;
import dz.sh.hidra.modules.integration.api.rest.request.SyntheticExchangeRequests;
import dz.sh.hidra.modules.integration.api.rest.response.SyntheticExchangeResponses;
import dz.sh.hidra.modules.integration.application.port.in.SyntheticExchangeUseCase;
import java.util.List;
public final class SyntheticExchangeRestMapper {
    private SyntheticExchangeRestMapper() {}
    public static SyntheticExchangeUseCase.Submit submit(SyntheticExchangeRequests.T26SubmitExchange in) {
        return new SyntheticExchangeUseCase.Submit(in.requestKey(),in.mappingProfileId(),
                in.schemaVersionId(),in.titleFr());
    }
    public static SyntheticExchangeUseCase.Replay replay(SyntheticExchangeRequests.T26ReplayExchange in) {
        return new SyntheticExchangeUseCase.Replay(in.requestKey(),in.expectedAttempt(),in.reason());
    }
    public static SyntheticExchangeResponses.T26SyntheticMapping mapping(SyntheticExchangeUseCase.Mapping m) {
        return new SyntheticExchangeResponses.T26SyntheticMapping(m.profileId(),m.schemaVersionId(),
                m.schemaChecksum(),m.mappingDigest(),m.documentTypeId(),m.classificationId());
    }
    public static SyntheticExchangeResponses.T26SyntheticExchange exchange(SyntheticExchangeUseCase.Exchange e) {
        return new SyntheticExchangeResponses.T26SyntheticExchange(e.exchangeId(),e.status(),
                e.mappingProfileId(),e.schemaVersionId(),e.schemaChecksum(),e.mappingDigest(),
                e.payloadHash(),e.latestAttempt(),e.failureCode(),e.documentId(),e.replayed());
    }
    public static SyntheticExchangeResponses.T26SyntheticAttempts attempts(
            List<SyntheticExchangeUseCase.AttemptView> attempts) {
        return new SyntheticExchangeResponses.T26SyntheticAttempts(attempts.stream()
                .map(a->new SyntheticExchangeResponses.T26SyntheticAttempt(a.ordinal(),a.status(),
                        a.actorId(),a.jobRunId(),a.messageId(),a.inboundId(),a.deadLetterId(),
                        a.failureCode(),a.reason(),a.claimedAt(),a.completedAt())).toList());
    }
}
