/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : StationInstalledConfigurationFailure
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-30
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.model
 *
 * @Description : Protected installed-station configuration ownership boundary.
 *
 */
package dz.sh.hidra.modules.topology.application.model;

public final class StationInstalledConfigurationFailure extends RuntimeException {
    public enum Kind { INVALID, UNAUTHENTICATED, DENIED, NOT_FOUND, CONFLICT, UNAVAILABLE, INDETERMINATE }
    private final Kind kind;
    public StationInstalledConfigurationFailure(Kind kind,Throwable cause) {
        super("Station configuration operation unavailable",cause);
        this.kind=java.util.Objects.requireNonNull(kind);
    }
    public Kind kind(){return kind;}
}
