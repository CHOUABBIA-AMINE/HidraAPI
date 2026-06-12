/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskModuleConfiguration
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Infrastructure
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.infrastructure.configuration
 *
 * @Description : Risk infrastructure configuration.
 *
 */
package dz.sh.hidra.modules.risk.infrastructure.configuration;

/**
 * Risk infrastructure configuration.
 */
public record RiskModuleConfiguration(
        boolean matrixScoringEnabled,
        boolean treatmentWorkflowEnabled,
        boolean riskAcceptanceWorkflowEnabled,
        boolean aggregationSnapshotEnabled
) {

    public static RiskModuleConfiguration defaults() {
        return new RiskModuleConfiguration(true, true, true, true);
    }
}
