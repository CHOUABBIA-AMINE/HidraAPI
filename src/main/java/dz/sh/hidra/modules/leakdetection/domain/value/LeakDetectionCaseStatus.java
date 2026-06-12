/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakDetectionCaseStatus
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.domain.value
 *
 * @Description : Defines LeakDetectionCaseStatus values.
 *
 */
package dz.sh.hidra.modules.leakdetection.domain.value;

/**
 * Defines LeakDetectionCaseStatus values.
 */
public enum LeakDetectionCaseStatus {
    OPEN, UNDER_INVESTIGATION, VERIFIED_LEAK, DISMISSED, ESCALATED, CLOSED
}
