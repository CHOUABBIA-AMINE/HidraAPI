/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ValidationFailureAction
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.domain.value
 *
 * @Description : Defines ValidationFailureAction values.
 *
 */
package dz.sh.hidra.modules.telemetry.domain.value;

/**
 * Defines ValidationFailureAction values.
 */
public enum ValidationFailureAction {
    WARN, REJECT, QUARANTINE, REQUIRE_REVIEW
}
