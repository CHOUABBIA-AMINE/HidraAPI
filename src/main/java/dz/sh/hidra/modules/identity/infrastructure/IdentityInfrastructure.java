/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityInfrastructure
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Utility
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure
 *
 * @Description : Defines identity infrastructure constants.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure;

/**
 * Identity infrastructure constants.
 */
public final class IdentityInfrastructure {

    public static final String TABLE_PREFIX = "hidra_identity_";

    private IdentityInfrastructure() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }
}
