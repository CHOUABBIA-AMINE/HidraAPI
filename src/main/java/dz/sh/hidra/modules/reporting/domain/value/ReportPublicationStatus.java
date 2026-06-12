/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportPublicationStatus
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.domain.value
 *
 * @Description : Defines ReportPublicationStatus values.
 *
 */
package dz.sh.hidra.modules.reporting.domain.value;

/**
 * Defines ReportPublicationStatus values.
 */
public enum ReportPublicationStatus {
    DRAFT, PENDING_APPROVAL, APPROVED, REJECTED, PUBLISHED, RETRACTED, CANCELLED
}
