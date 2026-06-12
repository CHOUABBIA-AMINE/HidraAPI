/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentStatus
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.domain.value
 *
 * @Description : Defines DocumentStatus values.
 *
 */
package dz.sh.hidra.modules.documents.domain.value;

/**
 * Defines DocumentStatus values.
 */
public enum DocumentStatus {
    DRAFT, ACTIVE, UNDER_REVIEW, APPROVED, OBSOLETE, ARCHIVED, REJECTED, QUARANTINED, DELETED_LOGICAL
}
