/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentReviewStatus
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.domain.value
 *
 * @Description : Defines DocumentReviewStatus values.
 *
 */
package dz.sh.hidra.modules.documents.domain.value;

/**
 * Defines DocumentReviewStatus values.
 */
public enum DocumentReviewStatus {
    REQUESTED, IN_PROGRESS, APPROVED, REJECTED, CANCELLED
}
