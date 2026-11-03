/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringBaselineDraftFailure
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-03
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.model
 *
 * @Description : Carries nondisclosing baseline draft failure categories.
 *
 */
package dz.sh.hidra.modules.topology.application.model;


public final class EngineeringBaselineDraftFailure extends RuntimeException {
    public enum Kind { NOT_FOUND, CONFLICT, UNAVAILABLE }
    private final Kind kind;
    public EngineeringBaselineDraftFailure(Kind kind){super("Engineering baseline draft not completed");this.kind=java.util.Objects.requireNonNull(kind);}
    public Kind kind(){return kind;}
}
