/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrityModuleConfiguration
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Infrastructure
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.infrastructure.configuration
 *
 * @Description : Integrity infrastructure configuration.
 *
 */
package dz.sh.hidra.modules.integrity.infrastructure.configuration;

/**
 * Integrity infrastructure configuration.
 */
public record IntegrityModuleConfiguration(
        boolean assessmentWorkflowEnabled,
        boolean recommendationEventEnabled,
        boolean remainingLifeEstimationEnabled,
        boolean evidenceRequiredForClosure
) {

    public static IntegrityModuleConfiguration defaults() {
        return new IntegrityModuleConfiguration(true, true, true, true);
    }
}
