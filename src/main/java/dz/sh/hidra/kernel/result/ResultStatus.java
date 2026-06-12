/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ResultStatus
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Kernel
 * @Module      : kernel
 * @Package     : dz.sh.hidra.kernel.result
 *
 * @Description : Defines generic application result statuses.
 *
 */
package dz.sh.hidra.kernel.result;

/**
 * Generic framework-neutral application result statuses.
 */
public enum ResultStatus {
    SUCCESS,
    NOT_FOUND,
    VALIDATION_ERROR,
    CONFLICT,
    FORBIDDEN,
    FAILURE
}
