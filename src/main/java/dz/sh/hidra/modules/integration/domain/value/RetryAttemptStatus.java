/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RetryAttemptStatus
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.domain.value
 *
 * @Description : Defines RetryAttemptStatus values.
 *
 */
package dz.sh.hidra.modules.integration.domain.value;

/**
 * Defines RetryAttemptStatus values.
 */
public enum RetryAttemptStatus {
    SCHEDULED, RUNNING, SUCCEEDED, FAILED, ABANDONED
}
