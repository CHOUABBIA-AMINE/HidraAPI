/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DeadLetterStatus
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.domain.value
 *
 * @Description : Defines DeadLetterStatus values.
 *
 */
package dz.sh.hidra.modules.integration.domain.value;

/**
 * Defines DeadLetterStatus values.
 */
public enum DeadLetterStatus {
    OPEN, UNDER_REVIEW, REPLAYED, IGNORED, RESOLVED
}
