/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportRequestStatus
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.domain.value
 *
 * @Description : Defines ReportRequestStatus values.
 *
 */
package dz.sh.hidra.modules.reporting.domain.value;

/**
 * Defines ReportRequestStatus values.
 */
public enum ReportRequestStatus {
    DRAFT, SUBMITTED, APPROVED, REJECTED, QUEUED, RUNNING, COMPLETED, CANCELLED
}
