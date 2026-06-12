/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ExecutionResultStatus
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.domain.value
 *
 * @Description : Defines ExecutionResultStatus values.
 *
 */
package dz.sh.hidra.modules.assets.domain.value;

/**
 * Defines ExecutionResultStatus values.
 */
public enum ExecutionResultStatus {
    SUCCESS, PARTIAL, FAILED, DEFERRED, CANCELLED
}
