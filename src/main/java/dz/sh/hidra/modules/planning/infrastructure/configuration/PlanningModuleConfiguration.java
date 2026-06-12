/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningModuleConfiguration
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Infrastructure
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.infrastructure.configuration
 *
 * @Description : Planning infrastructure configuration.
 *
 */
package dz.sh.hidra.modules.planning.infrastructure.configuration;

/**
 * Planning infrastructure configuration.
 */
public record PlanningModuleConfiguration(
        boolean revisioningEnabled,
        boolean forecastEnabled,
        boolean actualReviewSnapshotEnabled
) {

    public static PlanningModuleConfiguration defaults() {
        return new PlanningModuleConfiguration(true, true, true);
    }
}
