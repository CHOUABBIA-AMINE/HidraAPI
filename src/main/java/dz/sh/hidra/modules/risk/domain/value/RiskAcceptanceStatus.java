/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskAcceptanceStatus
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.domain.value
 *
 * @Description : Defines RiskAcceptanceStatus values.
 *
 */
package dz.sh.hidra.modules.risk.domain.value;

/**
 * Defines RiskAcceptanceStatus values.
 */
public enum RiskAcceptanceStatus {
    REQUESTED, REVIEWED, ACCEPTED, EXPIRED, REJECTED, REVOKED
}
