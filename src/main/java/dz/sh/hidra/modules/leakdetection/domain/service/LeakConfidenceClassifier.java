/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakConfidenceClassifier
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.domain.service
 *
 * @Description : Classifies leak confidence into severity levels.
 *
 */
package dz.sh.hidra.modules.leakdetection.domain.service;

import dz.sh.hidra.modules.leakdetection.domain.value.LeakSeverityLevel;

import java.math.BigDecimal;

/**
 * Classifies leak confidence into severity levels.
 */
public class LeakConfidenceClassifier {

    public LeakSeverityLevel classify(BigDecimal confidenceScore) {
        if (confidenceScore == null) {
            return LeakSeverityLevel.UNKNOWN;
        }
        if (confidenceScore.compareTo(BigDecimal.valueOf(0.90)) >= 0) {
            return LeakSeverityLevel.CRITICAL;
        }
        if (confidenceScore.compareTo(BigDecimal.valueOf(0.75)) >= 0) {
            return LeakSeverityLevel.HIGH;
        }
        if (confidenceScore.compareTo(BigDecimal.valueOf(0.50)) >= 0) {
            return LeakSeverityLevel.MEDIUM;
        }
        return LeakSeverityLevel.LOW;
    }
}
