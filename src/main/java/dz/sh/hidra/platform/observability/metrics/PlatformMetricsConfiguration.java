/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlatformMetricsConfiguration
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.observability.metrics
 *
 * @Description : Registers common technical metric tag conventions for platform meters.
 *
 */
package dz.sh.hidra.platform.observability.metrics;

import io.micrometer.core.instrument.config.MeterFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class PlatformMetricsConfiguration {

    @Bean
    public MeterFilter platformCommonTagsMeterFilter(PlatformMetricsTags platformMetricsTags) {
        return MeterFilter.commonTags(platformMetricsTags.commonTags("platform"));
    }
}
