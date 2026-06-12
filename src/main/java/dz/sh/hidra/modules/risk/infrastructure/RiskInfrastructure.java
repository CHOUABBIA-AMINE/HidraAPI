/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskInfrastructure
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.infrastructure
 *
 * @Description : Risk infrastructure constants.
 *
 */
package dz.sh.hidra.modules.risk.infrastructure;

/**
 * Risk infrastructure constants.
 */
public final class RiskInfrastructure {

    public static final String TABLE_PREFIX = "hidra_risk_";

    private RiskInfrastructure() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }
}
