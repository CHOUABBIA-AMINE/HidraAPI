/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MetricValueSemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-05
 *
 * @Type        : Class
 * @Layer       : Analytics Test
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.semantic
 *
 * @Description : Verifies HMR-038 MetricValue scope-type requiredness.
 *
 */
package dz.sh.hidra.modules.analytics.semantic;

import dz.sh.hidra.modules.analytics.domain.exception.InvalidAnalyticsValueException;
import dz.sh.hidra.modules.analytics.domain.model.MetricValue;
import dz.sh.hidra.modules.analytics.domain.value.AnalyticsQualityStatus;
import java.math.BigDecimal;
import java.time.Instant;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MetricValueSemanticRemediationTest {

    private static final Instant START = Instant.parse("2026-10-05T00:00:00Z");

    @Test
    void rejectsBlankScopeType() {
        assertThatThrownBy(() -> metric(" "))
                .isInstanceOf(InvalidAnalyticsValueException.class)
                .hasMessageContaining("scope type");
    }

    @Test
    void normalizesValidScopeType() {
        MetricValue value = metric(" PIPELINE ");
        assertThat(value.scopeType()).isEqualTo("PIPELINE");
    }

    private static MetricValue metric(String scopeType) {
        return new MetricValue(
                "value-1",
                "run-1",
                "metric-1",
                "metric-version-1",
                scopeType,
                "pipeline-1",
                START,
                START.plusSeconds(60),
                new BigDecimal("1.000000"),
                null,
                null,
                AnalyticsQualityStatus.READY,
                START.plusSeconds(120)
        );
    }
}
