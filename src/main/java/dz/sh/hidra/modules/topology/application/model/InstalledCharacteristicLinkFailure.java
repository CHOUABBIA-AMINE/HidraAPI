/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InstalledCharacteristicLinkFailure
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-01
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.model
 *
 * @Description : Non-disclosing protected draft-link failure classification.
 *
 */
package dz.sh.hidra.modules.topology.application.model;
public final class InstalledCharacteristicLinkFailure extends RuntimeException {
    public enum Kind { INVALID, UNAUTHENTICATED, DENIED, NOT_FOUND, CONFLICT, UNAVAILABLE, INDETERMINATE }
    private final Kind kind;
    public InstalledCharacteristicLinkFailure(Kind kind){
        super("Protected characteristic-link operation not completed");
        this.kind=java.util.Objects.requireNonNull(kind);
    }
    public Kind kind(){return kind;}
}
