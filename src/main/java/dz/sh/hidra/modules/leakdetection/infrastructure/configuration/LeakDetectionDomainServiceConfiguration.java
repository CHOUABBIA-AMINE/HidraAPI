/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakDetectionDomainServiceConfiguration
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.infrastructure.configuration
 *
 * @Description : Exposes leak detection domain services as Spring beans.
 *
 */
package dz.sh.hidra.modules.leakdetection.infrastructure.configuration;

import dz.sh.hidra.modules.leakdetection.domain.service.LeakConfidenceClassifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Exposes leak detection domain services as Spring beans.
 */
@Configuration
public class LeakDetectionDomainServiceConfiguration {

    @Bean
    public LeakConfidenceClassifier leakConfidenceClassifier() {
        return new LeakConfidenceClassifier();
    }
}
