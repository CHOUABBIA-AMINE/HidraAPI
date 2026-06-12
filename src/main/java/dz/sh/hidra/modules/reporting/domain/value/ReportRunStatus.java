/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportRunStatus
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.domain.value
 *
 * @Description : Defines ReportRunStatus values.
 *
 */
package dz.sh.hidra.modules.reporting.domain.value;

/**
 * Defines ReportRunStatus values.
 */
public enum ReportRunStatus {
    QUEUED, RUNNING, COMPLETED, FAILED, CANCELLED, EXPIRED
}
