/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskScoreCalculator
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.domain.service
 *
 * @Description : Calculates simple matrix-compatible risk scores.
 *
 */
package dz.sh.hidra.modules.risk.domain.service;

import java.math.BigDecimal;

/**
 * Calculates simple matrix-compatible risk scores.
 */
public class RiskScoreCalculator {

    public BigDecimal multiply(BigDecimal likelihoodScore, BigDecimal consequenceScore) {
        if (likelihoodScore == null || consequenceScore == null) {
            return BigDecimal.ZERO;
        }
        return likelihoodScore.multiply(consequenceScore);
    }
}
