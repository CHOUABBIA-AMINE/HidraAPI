/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RemainingLifeClassifier
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.domain.service
 *
 * @Description : Classifies remaining life into severity buckets.
 *
 */
package dz.sh.hidra.modules.integrity.domain.service;

import dz.sh.hidra.modules.integrity.domain.value.FindingSeverity;

import java.math.BigDecimal;

/**
 * Classifies remaining life into severity buckets.
 */
public class RemainingLifeClassifier {

    public FindingSeverity classifyYears(BigDecimal remainingYears) {
        if (remainingYears == null) {
            return FindingSeverity.UNKNOWN;
        }
        if (remainingYears.compareTo(BigDecimal.ONE) <= 0) {
            return FindingSeverity.CRITICAL;
        }
        if (remainingYears.compareTo(BigDecimal.valueOf(3)) <= 0) {
            return FindingSeverity.HIGH;
        }
        if (remainingYears.compareTo(BigDecimal.valueOf(5)) <= 0) {
            return FindingSeverity.MEDIUM;
        }
        return FindingSeverity.LOW;
    }
}
