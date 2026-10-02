/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskModule
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk
 *
 * @Description : Defines risk module constants.
 *
 */
package dz.sh.hidra.modules.risk;

/**
 * Risk module constants.
 */
public final class RiskModule {

    public static final String MODULE_NAME = "risk";
    public static final String TABLE_PREFIX = "hidra_risk_";

    private RiskModule() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }
}
