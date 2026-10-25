/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringLocalExchangeFailure
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-25
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.model
 *
 * @Description : Implements bounded local editor exchange through existing protected owners.
 *
 */
package dz.sh.hidra.modules.topology.application.model;

public final class EngineeringLocalExchangeFailure extends RuntimeException {
    public enum Reason { CONFLICT, NOT_AUTHORED }
    private final Reason reason;
    public EngineeringLocalExchangeFailure(Reason reason){super("Engineering local exchange unavailable");this.reason=java.util.Objects.requireNonNull(reason);}
    public Reason reason(){return reason;}
}
