/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : SyntheticExchangeOwnerPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-18
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.application.port.out
 * @Description : Integration outbound bridge to live authority, physical ownership and Documents draft receipt.
 */
package dz.sh.hidra.modules.integration.application.port.out;
import dz.sh.hidra.modules.integration.domain.model.SyntheticExchange.Scope;
public interface SyntheticExchangeOwnerPort {
    enum Operation { READ, EXECUTE, REPLAY }
    record Draft(String documentId,boolean replayed) {}
    final class TransientReceiverFailure extends RuntimeException {
        public TransientReceiverFailure(Throwable cause) { super("T26 receiver transient",cause); }
    }
    String require(Scope scope,Operation action);
    void requireDraft(Scope scope);
    Draft createDraft(Scope scope,String exchangeId,String title,String documentTypeId,String classificationId);
}
