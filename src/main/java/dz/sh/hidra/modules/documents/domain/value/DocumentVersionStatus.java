/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentVersionStatus
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.domain.value
 *
 * @Description : Defines DocumentVersionStatus values.
 *
 */
package dz.sh.hidra.modules.documents.domain.value;

/**
 * Defines DocumentVersionStatus values.
 */
public enum DocumentVersionStatus {
    DRAFT, SUBMITTED, APPROVED, REJECTED, CURRENT, SUPERSEDED, ARCHIVED
}
