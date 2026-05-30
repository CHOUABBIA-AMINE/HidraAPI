/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ApiErrorCode
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Enum
 * @Layer       : Kernel
 * @Module      : kernel
 * @Package     : dz.sh.hidra.kernel.api.error
 *
 * @Description : Generic framework-independent API error categories.
 *
 */
package dz.sh.hidra.kernel.api.error;

public enum ApiErrorCode {
    VALIDATION_ERROR,
    DOMAIN_ERROR,
    AUTHENTICATION_ERROR,
    AUTHORIZATION_ERROR,
    NOT_FOUND,
    CONFLICT,
    INTERNAL_ERROR,
    INFRASTRUCTURE_ERROR
}
