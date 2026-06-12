/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakDetectionModuleConfiguration
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Infrastructure
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.infrastructure.configuration
 *
 * @Description : Leak detection infrastructure configuration.
 *
 */
package dz.sh.hidra.modules.leakdetection.infrastructure.configuration;

/**
 * Leak detection infrastructure configuration.
 */
public record LeakDetectionModuleConfiguration(
        boolean automaticCandidateCreationEnabled,
        boolean localizationEnabled,
        boolean verificationWorkflowEnabled,
        boolean escalationEnabled
) {

    public static LeakDetectionModuleConfiguration defaults() {
        return new LeakDetectionModuleConfiguration(true, true, true, true);
    }
}
