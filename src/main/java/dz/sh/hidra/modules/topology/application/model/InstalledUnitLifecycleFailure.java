/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InstalledUnitLifecycleFailure
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-29
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.model
 *
 * @Description : Implements the protected installed-unit lifecycle within its owning boundary.
 *
 */
package dz.sh.hidra.modules.topology.application.model;

/** Stable owner meaning; diagnostic causes remain private to the application boundary. */
public final class InstalledUnitLifecycleFailure extends IllegalStateException {
    public enum Kind { INVALID, UNAUTHENTICATED, DENIED, NOT_FOUND, CONFLICT, UNAVAILABLE, INDETERMINATE }
    private final Kind kind;
    public InstalledUnitLifecycleFailure(Kind kind,Throwable cause) {
        super("Installed-unit operation " + java.util.Objects.requireNonNull(kind).name(),cause);this.kind=kind;
    }
    public Kind kind(){return kind;}
}
