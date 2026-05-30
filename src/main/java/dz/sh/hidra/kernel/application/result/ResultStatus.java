/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ResultStatus
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Enum
 * @Layer       : Kernel
 * @Module      : kernel
 * @Package     : dz.sh.hidra.kernel.application.result
 *
 * @Description : Generic operation status for framework-independent application results.
 *
 */
package dz.sh.hidra.kernel.application.result;

public enum ResultStatus {
    SUCCESS,
    FAILURE,
    NOT_FOUND,
    VALIDATION_FAILED,
    FORBIDDEN,
    CONFLICT
}
