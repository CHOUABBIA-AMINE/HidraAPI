/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OutboundRecordStatus
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.domain.value
 *
 * @Description : Defines OutboundRecordStatus values.
 *
 */
package dz.sh.hidra.modules.integration.domain.value;

/**
 * Defines OutboundRecordStatus values.
 */
public enum OutboundRecordStatus {
    PENDING, MAPPED, SENT, ACKNOWLEDGED, REJECTED, FAILED, DEAD_LETTERED
}
