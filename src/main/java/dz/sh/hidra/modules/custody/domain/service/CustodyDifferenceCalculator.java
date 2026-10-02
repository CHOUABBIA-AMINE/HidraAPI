/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyDifferenceCalculator
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.domain.service
 *
 * @Description : Calculates custody differences.
 *
 */
package dz.sh.hidra.modules.custody.domain.service;

import java.math.BigDecimal;

/**
 * Calculates custody differences.
 */
public class CustodyDifferenceCalculator {

    public BigDecimal difference(BigDecimal officialQuantity, BigDecimal measuredQuantity) {
        if (officialQuantity == null || measuredQuantity == null) {
            return BigDecimal.ZERO;
        }
        return officialQuantity.subtract(measuredQuantity);
    }

    public BigDecimal percentDifference(BigDecimal difference, BigDecimal baseQuantity) {
        if (difference == null || baseQuantity == null || BigDecimal.ZERO.compareTo(baseQuantity) == 0) {
            return BigDecimal.ZERO;
        }
        return difference.divide(baseQuantity, 6, java.math.RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
    }
}
