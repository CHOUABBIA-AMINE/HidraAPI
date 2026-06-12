/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrityCaseStatus
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.domain.value
 *
 * @Description : Defines IntegrityCaseStatus values.
 *
 */
package dz.sh.hidra.modules.integrity.domain.value;

/**
 * Defines IntegrityCaseStatus values.
 */
public enum IntegrityCaseStatus {
    OPEN, UNDER_REVIEW, ACTION_REQUIRED, MONITORING, RESOLVED, CLOSED, CANCELLED
}
