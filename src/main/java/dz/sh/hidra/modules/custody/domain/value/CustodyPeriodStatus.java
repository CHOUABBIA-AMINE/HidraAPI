/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyPeriodStatus
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.domain.value
 *
 * @Description : Defines CustodyPeriodStatus values.
 *
 */
package dz.sh.hidra.modules.custody.domain.value;

/**
 * Defines CustodyPeriodStatus values.
 */
public enum CustodyPeriodStatus {
    OPEN, LOCKED, CALCULATED, APPROVED, CLOSED, REOPENED, CANCELLED
}
