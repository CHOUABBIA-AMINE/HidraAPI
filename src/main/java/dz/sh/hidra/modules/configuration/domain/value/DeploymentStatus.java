/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DeploymentStatus
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.domain.value
 *
 * @Description : Defines DeploymentStatus values.
 *
 */
package dz.sh.hidra.modules.configuration.domain.value;

/**
 * Defines DeploymentStatus values.
 */
public enum DeploymentStatus {
    PLANNED, RUNNING, SUCCEEDED, FAILED, ROLLED_BACK, CANCELLED
}
