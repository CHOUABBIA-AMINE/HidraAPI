/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MonitoringModuleConfiguration
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Infrastructure
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.infrastructure.configuration
 *
 * @Description : Monitoring infrastructure configuration.
 *
 */
package dz.sh.hidra.modules.monitoring.infrastructure.configuration;

/**
 * Monitoring infrastructure configuration.
 */
public record MonitoringModuleConfiguration(
        boolean evaluationEnabled,
        boolean alertCandidateEnabled,
        boolean riskSignalEnabled,
        boolean operationalStateSnapshotEnabled
) {

    public static MonitoringModuleConfiguration defaults() {
        return new MonitoringModuleConfiguration(true, true, true, true);
    }
}
