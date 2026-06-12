/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportingModuleConfiguration
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Infrastructure
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.infrastructure.configuration
 *
 * @Description : Reporting infrastructure configuration.
 *
 */
package dz.sh.hidra.modules.reporting.infrastructure.configuration;

/**
 * Reporting infrastructure configuration.
 */
public record ReportingModuleConfiguration(
        boolean sourceOfTruthMutationBlocked,
        boolean authorizationRequiredForRestrictedReports,
        boolean artifactChecksumRequired,
        boolean reproducibilitySnapshotsRequired
) {

    public static ReportingModuleConfiguration defaults() {
        return new ReportingModuleConfiguration(true, true, true, true);
    }
}
