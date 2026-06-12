/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportingModule
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting
 *
 * @Description : Defines reporting module constants.
 *
 */
package dz.sh.hidra.modules.reporting;

/**
 * Reporting module constants.
 */
public final class ReportingModule {

    public static final String MODULE_NAME = "reporting";
    public static final String TABLE_PREFIX = "hidra_reporting_";

    private ReportingModule() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }
}
