/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakConfidenceScore
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.domain.value
 *
 * @Description : Leak confidence score.
 *
 */
package dz.sh.hidra.modules.leakdetection.domain.value;

import dz.sh.hidra.modules.leakdetection.domain.exception.InvalidLeakDetectionValueException;

import java.math.BigDecimal;

/**
 * Leak confidence score between 0 and 1.
 *
 * @param value confidence value
 */
public record LeakConfidenceScore(BigDecimal value) {

    public LeakConfidenceScore {
        if (value == null) {
            throw new InvalidLeakDetectionValueException("Leak confidence score must not be null.");
        }
        if (value.compareTo(BigDecimal.ZERO) < 0 || value.compareTo(BigDecimal.ONE) > 0) {
            throw new InvalidLeakDetectionValueException("Leak confidence score must be between 0 and 1.");
        }
    }
}
