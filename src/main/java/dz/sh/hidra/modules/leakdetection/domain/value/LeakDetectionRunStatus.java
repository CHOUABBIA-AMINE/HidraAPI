/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakDetectionRunStatus
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.domain.value
 *
 * @Description : Defines LeakDetectionRunStatus values.
 *
 */
package dz.sh.hidra.modules.leakdetection.domain.value;

/**
 * Defines LeakDetectionRunStatus values.
 */
public enum LeakDetectionRunStatus {
    SCHEDULED, RUNNING, COMPLETED, FAILED, CANCELLED
}
