/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyInfrastructure
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Utility
 * @Layer       : Infrastructure
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.infrastructure
 *
 * @Description : Custody infrastructure constants.
 *
 */
package dz.sh.hidra.modules.custody.infrastructure;

/**
 * Custody infrastructure constants.
 */
public final class CustodyInfrastructure {

    public static final String TABLE_PREFIX = "hidra_custody_";

    private CustodyInfrastructure() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }
}
