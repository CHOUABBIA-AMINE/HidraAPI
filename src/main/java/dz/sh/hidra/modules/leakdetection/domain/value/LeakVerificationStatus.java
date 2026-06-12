/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakVerificationStatus
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.domain.value
 *
 * @Description : Defines LeakVerificationStatus values.
 *
 */
package dz.sh.hidra.modules.leakdetection.domain.value;

/**
 * Defines LeakVerificationStatus values.
 */
public enum LeakVerificationStatus {
    REQUESTED, IN_PROGRESS, CONFIRMED, REJECTED, CANCELLED, COMPLETED
}
