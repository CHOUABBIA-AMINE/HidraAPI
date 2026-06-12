/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ComplianceStatus
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.domain.value
 *
 * @Description : Defines ComplianceStatus values.
 *
 */
package dz.sh.hidra.modules.party.domain.value;

/**
 * Defines ComplianceStatus values.
 */
public enum ComplianceStatus {
    CLEARED, WATCHLIST, BLOCKED, PENDING_REVIEW, EXPIRED
}
