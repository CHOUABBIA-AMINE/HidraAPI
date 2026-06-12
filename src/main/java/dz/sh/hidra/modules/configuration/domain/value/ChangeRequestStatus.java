/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ChangeRequestStatus
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.domain.value
 *
 * @Description : Defines ChangeRequestStatus values.
 *
 */
package dz.sh.hidra.modules.configuration.domain.value;

/**
 * Defines ChangeRequestStatus values.
 */
public enum ChangeRequestStatus {
    DRAFT, SUBMITTED, UNDER_REVIEW, APPROVED, REJECTED, CANCELLED, DEPLOYED
}
