/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HidraRealtimeConfigurationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-05
 *
 * @Type        : Class
 * @Layer       : Platform Test
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.realtime
 *
 * @Description : Verifies that realtime infrastructure can be disabled on non-active P1 nodes.
 *
 */
package dz.sh.hidra.platform.realtime;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.AnnotatedElementUtils;

class HidraRealtimeConfigurationTest {

    @Test
    void bindsRealtimeBrokerCreationToRealtimeEnabledProperty() {
        ConditionalOnProperty condition = AnnotatedElementUtils.findMergedAnnotation(
                HidraRealtimeConfiguration.class,
                ConditionalOnProperty.class
        );

        assertThat(condition).isNotNull();
        assertThat(condition.prefix()).isEqualTo("hidra.platform.realtime");
        assertThat(condition.name()).containsExactly("enabled");
        assertThat(condition.havingValue()).isEqualTo("true");
        assertThat(condition.matchIfMissing()).isTrue();
    }
}
