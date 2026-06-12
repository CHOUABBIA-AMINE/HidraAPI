/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlannedValue
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.domain.value
 *
 * @Description : Represents a planned numeric or state value.
 *
 */
package dz.sh.hidra.modules.planning.domain.value;

import java.math.BigDecimal;

/**
 * Planned numeric or state value.
 *
 * @param numericValue numeric value
 * @param textValue state/text value
 * @param unitId unit reference
 */
public record PlannedValue(
        BigDecimal numericValue,
        String textValue,
        String unitId
) {
}
