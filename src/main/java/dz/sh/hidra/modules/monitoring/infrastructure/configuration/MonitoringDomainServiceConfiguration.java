/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MonitoringDomainServiceConfiguration
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.infrastructure.configuration
 *
 * @Description : Exposes monitoring domain services as Spring beans.
 *
 */
package dz.sh.hidra.modules.monitoring.infrastructure.configuration;

import dz.sh.hidra.modules.monitoring.domain.service.DeviationSeverityClassifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Exposes monitoring domain services as Spring beans.
 */
@Configuration
public class MonitoringDomainServiceConfiguration {

    @Bean
    DeviationSeverityClassifier deviationSeverityClassifier() {
        return new DeviationSeverityClassifier();
    }
}
