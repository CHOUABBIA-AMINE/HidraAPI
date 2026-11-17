/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AbsoluteDeviationMetricV1
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-17
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.domain.service
 *
 * @Description : Deterministic absolute deviation using exact representable decimals.
 *
 */
package dz.sh.hidra.modules.analytics.domain.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class AbsoluteDeviationMetricV1 {
    public static final String CODE = "ABSOLUTE_DEVIATION";
    public static final String METHOD = "ABSOLUTE_DEVIATION_V1";
    public static final String REVISION = "1";
    private AbsoluteDeviationMetricV1() { }

    public record Outcome(String errorCode, BigDecimal value) {
        public Outcome {
            if ((errorCode == null) == (value == null))
                throw new IllegalArgumentException("Exactly one outcome is required.");
        }
        public boolean successful() { return value != null; }
    }

    public static Outcome evaluate(String sourceOutcome, BigDecimal actual, BigDecimal expected) {
        if (!"COMPUTED".equals(sourceOutcome) || actual == null || expected == null)
            return new Outcome("SOURCE_UNAVAILABLE", null);
        BigDecimal difference = actual.subtract(expected).abs();
        try {
            BigDecimal exact = difference.setScale(6, RoundingMode.UNNECESSARY);
            if (exact.precision() > 18) return new Outcome("VALUE_OUT_OF_RANGE", null);
            return new Outcome(null, exact);
        } catch (ArithmeticException cannotRepresent) {
            return new Outcome("VALUE_OUT_OF_RANGE", null);
        }
    }
}
