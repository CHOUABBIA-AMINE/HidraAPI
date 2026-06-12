/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentModuleConfiguration
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Infrastructure
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.infrastructure.configuration
 *
 * @Description : Incident infrastructure configuration.
 *
 */
package dz.sh.hidra.modules.incident.infrastructure.configuration;

/**
 * Incident infrastructure configuration.
 */
public record IncidentModuleConfiguration(
        boolean timelineEnabled,
        boolean closureWorkflowReferenceEnabled,
        boolean evidenceRequiredForClosure,
        boolean responseActionRecordingEnabled
) {

    public static IncidentModuleConfiguration defaults() {
        return new IncidentModuleConfiguration(true, true, true, true);
    }
}
