/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DeviationSeverityClassifier
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.domain.service
 *
 * @Description : Classifies deviation severity.
 *
 */
package dz.sh.hidra.modules.monitoring.domain.service;

import dz.sh.hidra.modules.monitoring.domain.value.DeviationSeverity;

import java.math.BigDecimal;

/**
 * Classifies deviation severity from absolute percentage difference.
 */
public class DeviationSeverityClassifier {

    public DeviationSeverity classify(BigDecimal differencePercent) {
        if (differencePercent == null) {
            return DeviationSeverity.INFO;
        }
        BigDecimal absolute = differencePercent.abs();
        if (absolute.compareTo(BigDecimal.valueOf(50)) >= 0) {
            return DeviationSeverity.CRITICAL;
        }
        if (absolute.compareTo(BigDecimal.valueOf(25)) >= 0) {
            return DeviationSeverity.HIGH;
        }
        if (absolute.compareTo(BigDecimal.valueOf(10)) >= 0) {
            return DeviationSeverity.MEDIUM;
        }
        if (absolute.compareTo(BigDecimal.ZERO) > 0) {
            return DeviationSeverity.LOW;
        }
        return DeviationSeverity.INFO;
    }
}
