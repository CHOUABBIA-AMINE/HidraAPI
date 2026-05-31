/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlatformMetricsTags
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.observability.metrics
 *
 * @Description : Provides standard technical metric tags for platform instrumentation.
 *
 */
package dz.sh.hidra.platform.observability.metrics;

import io.micrometer.core.instrument.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PlatformMetricsTags {

    private final String application;
    private final String environment;

    public PlatformMetricsTags(
            @Value("${spring.application.name:hidra-api}") String application,
            @Value("${hidra.environment:unknown}") String environment) {
        this.application = normalize(application, "hidra-api");
        this.environment = normalize(environment, "unknown");
    }

    public List<Tag> commonTags(String module) {
        return List.of(
                Tag.of("application", application),
                Tag.of("environment", environment),
                Tag.of("module", normalize(module, "platform")));
    }

    private static String normalize(String value, String defaultValue) {
        if (value == null || value.isBlank()) {
            return defaultValue;
        }
        return value.trim();
    }
}
