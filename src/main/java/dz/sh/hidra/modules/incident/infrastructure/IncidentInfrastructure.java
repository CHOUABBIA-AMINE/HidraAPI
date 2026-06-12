/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentInfrastructure
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.infrastructure
 *
 * @Description : Incident infrastructure constants.
 *
 */
package dz.sh.hidra.modules.incident.infrastructure;

/**
 * Incident infrastructure constants.
 */
public final class IncidentInfrastructure {

    public static final String TABLE_PREFIX = "hidra_incident";

    private IncidentInfrastructure() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }
}
