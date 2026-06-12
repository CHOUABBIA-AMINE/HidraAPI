/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JobRunStepStatus
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.domain.value
 *
 * @Description : Defines JobRunStepStatus values.
 *
 */
package dz.sh.hidra.modules.integration.domain.value;

/**
 * Defines JobRunStepStatus values.
 */
public enum JobRunStepStatus {
    PENDING, RUNNING, COMPLETED, FAILED, SKIPPED
}
