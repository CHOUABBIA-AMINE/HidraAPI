/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyDiscrepancyStatus
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.domain.value
 *
 * @Description : Defines CustodyDiscrepancyStatus values.
 *
 */
package dz.sh.hidra.modules.custody.domain.value;

/**
 * Defines CustodyDiscrepancyStatus values.
 */
public enum CustodyDiscrepancyStatus {
    OPEN, UNDER_REVIEW, ACCEPTED, REJECTED, RESOLVED, CLOSED, CANCELLED
}
