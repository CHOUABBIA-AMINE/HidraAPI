/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HseCaseStatus
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.domain.value
 *
 * @Description : Defines HseCaseStatus values.
 *
 */
package dz.sh.hidra.modules.hse.domain.value;

/**
 * Defines HseCaseStatus values.
 */
public enum HseCaseStatus {
    DRAFT, OPEN, UNDER_REVIEW, IN_PROGRESS, CONTROLLED, RESOLVED, CLOSED, CANCELLED, REOPENED
}
