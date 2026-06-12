/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JobRunStatus
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.domain.value
 *
 * @Description : Defines JobRunStatus values.
 *
 */
package dz.sh.hidra.modules.integration.domain.value;

/**
 * Defines JobRunStatus values.
 */
public enum JobRunStatus {
    PENDING, RUNNING, COMPLETED, COMPLETED_WITH_ERRORS, FAILED, CANCELLED
}
