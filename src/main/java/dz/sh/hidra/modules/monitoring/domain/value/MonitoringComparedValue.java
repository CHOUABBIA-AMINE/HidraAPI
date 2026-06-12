/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MonitoringComparedValue
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.domain.value
 *
 * @Description : Represents compared actual and expected values.
 *
 */
package dz.sh.hidra.modules.monitoring.domain.value;

import java.math.BigDecimal;

/**
 * Compared actual and expected values.
 *
 * @param actualValue actual value snapshot
 * @param expectedValue expected value snapshot
 * @param differenceValue actual minus expected
 * @param differencePercent difference percentage
 * @param unitId unit reference
 */
public record MonitoringComparedValue(
        BigDecimal actualValue,
        BigDecimal expectedValue,
        BigDecimal differenceValue,
        BigDecimal differencePercent,
        String unitId
) {
}
