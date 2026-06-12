/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyReconciliationStatus
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.domain.value
 *
 * @Description : Defines CustodyReconciliationStatus values.
 *
 */
package dz.sh.hidra.modules.custody.domain.value;

/**
 * Defines CustodyReconciliationStatus values.
 */
public enum CustodyReconciliationStatus {
    DRAFT, IN_PROGRESS, BALANCED, DISCREPANCY_FOUND, APPROVED, CLOSED, CANCELLED
}
