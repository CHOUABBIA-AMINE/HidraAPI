/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskScoreValue
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.domain.value
 *
 * @Description : Risk score value.
 *
 */
package dz.sh.hidra.modules.risk.domain.value;

import dz.sh.hidra.modules.risk.domain.exception.InvalidRiskValueException;

import java.math.BigDecimal;

/**
 * Non-negative risk score value.
 *
 * @param value score value
 */
public record RiskScoreValue(BigDecimal value) {

    public RiskScoreValue {
        if (value == null) {
            throw new InvalidRiskValueException("Risk score value must not be null.");
        }
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidRiskValueException("Risk score value must not be negative.");
        }
    }
}
