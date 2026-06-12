/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringMeasurement
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.domain.value
 *
 * @Description : Engineering measurement value.
 *
 */
package dz.sh.hidra.modules.integrity.domain.value;

import java.math.BigDecimal;

/**
 * Engineering measurement value.
 *
 * @param value numeric value
 * @param unitId unit reference
 */
public record EngineeringMeasurement(
        BigDecimal value,
        String unitId
) {
}
