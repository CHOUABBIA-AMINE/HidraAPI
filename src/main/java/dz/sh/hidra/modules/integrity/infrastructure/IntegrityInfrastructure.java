/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrityInfrastructure
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.infrastructure
 *
 * @Description : Integrity infrastructure constants.
 *
 */
package dz.sh.hidra.modules.integrity.infrastructure;

/**
 * Integrity infrastructure constants.
 */
public final class IntegrityInfrastructure {

    public static final String TABLE_PREFIX = "hidra_integrity_";

    private IntegrityInfrastructure() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }
}
