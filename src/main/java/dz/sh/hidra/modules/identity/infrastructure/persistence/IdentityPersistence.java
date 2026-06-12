/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityPersistence
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Utility
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence
 *
 * @Description : Defines identity persistence constants.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence;

/**
 * Identity persistence constants.
 */
public final class IdentityPersistence {

    public static final String USER_TABLE = "hidra_identity_user";
    public static final String ROLE_TABLE = "hidra_identity_role";
    public static final String PERMISSION_TABLE = "hidra_identity_permission";

    private IdentityPersistence() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }
}
