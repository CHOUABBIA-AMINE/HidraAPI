/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringNetworkDraftFailure
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-19
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.model
 *
 * @Description : Implements the bounded company-scoped engineering draft contract.
 *
 */
package dz.sh.hidra.modules.topology.application.model;

public final class EngineeringNetworkDraftFailure extends RuntimeException {
    public enum Reason { DENIED, NOT_FOUND, CONFLICT, UNAVAILABLE, INDETERMINATE }
    private final Reason reason;
    public EngineeringNetworkDraftFailure(Reason reason){super("Engineering draft "+reason);this.reason=java.util.Objects.requireNonNull(reason);}
    public Reason reason(){return reason;}
}
