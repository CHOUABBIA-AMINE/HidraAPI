/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : SyntheticExchangeRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-18
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.application.port.out
 * @Description : Typed Integration-owned admission, pinned configuration and attempt persistence.
 */
package dz.sh.hidra.modules.integration.application.port.out;
import dz.sh.hidra.modules.integration.domain.model.SyntheticExchange;
import dz.sh.hidra.modules.integration.domain.model.SyntheticExchange.Attempt;
import dz.sh.hidra.modules.integration.domain.model.SyntheticExchange.Scope;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface SyntheticExchangeRepositoryPort {
    record Binding(String id,String profileId,String schemaVersionId,String schemaChecksum,
            String documentTypeId,String classificationId,String jobDefinitionId,
            String externalSystemId,String messageTypeId,String payloadFormatId,
            String mappingDigest) {}
    void lockKey(Scope scope,String requestKey);
    Binding activeBinding(Scope scope,String profileId);
    void requireCurrentBinding(SyntheticExchange exchange);
    Optional<SyntheticExchange> byKey(Scope scope,String key);
    Optional<SyntheticExchange> byId(Scope scope,String exchangeId,boolean lock);
    List<Attempt> attempts(String exchangeId);
    void insert(SyntheticExchange exchange,Attempt attempt);
    void insertReplay(SyntheticExchange exchange,Attempt attempt);
    void abandon(SyntheticExchange exchange,Attempt attempt,String safeCode);
    void succeeded(SyntheticExchange exchange,Attempt attempt,String documentId);
    void failed(SyntheticExchange exchange,Attempt attempt,String safeCode);
    boolean isReservedJob(String jobDefinitionId,String correlationId);
    boolean isReservedMessage(String runId,String externalMessageId);
}
