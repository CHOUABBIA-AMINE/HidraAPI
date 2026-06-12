/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HseModuleConfiguration
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Infrastructure
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.infrastructure.configuration
 *
 * @Description : HSE infrastructure configuration.
 *
 */
package dz.sh.hidra.modules.hse.infrastructure.configuration;

/**
 * HSE infrastructure configuration.
 */
public record HseModuleConfiguration(
        boolean capaWorkflowEnabled,
        boolean complianceAssessmentEnabled,
        boolean permitToWorkEnabled,
        boolean closureEvidenceRequired
) {

    public static HseModuleConfiguration defaults() {
        return new HseModuleConfiguration(true, true, true, true);
    }
}
