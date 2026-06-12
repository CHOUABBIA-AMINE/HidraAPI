/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JobTriggerType
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.domain.value
 *
 * @Description : Defines JobTriggerType values.
 *
 */
package dz.sh.hidra.modules.integration.domain.value;

/**
 * Defines JobTriggerType values.
 */
public enum JobTriggerType {
    SCHEDULED, MANUAL, EVENT, RETRY, REPLAY
}
