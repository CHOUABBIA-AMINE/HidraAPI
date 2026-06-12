/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentModule
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident
 *
 * @Description : Defines incident module constants.
 *
 */
package dz.sh.hidra.modules.incident;

/**
 * Incident module constants.
 */
public final class IncidentModule {

    public static final String MODULE_NAME = "incident";
    public static final String TABLE_PREFIX = "hidra_incident";

    private IncidentModule() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }
}
