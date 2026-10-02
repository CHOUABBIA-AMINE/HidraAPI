/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityDomainServiceConfiguration
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.configuration
 *
 * @Description : Exposes identity domain services as Spring beans.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.configuration;

import dz.sh.hidra.modules.identity.domain.service.AuthorizationPolicyEvaluator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Exposes identity domain services as Spring beans.
 */
@Configuration
public class IdentityDomainServiceConfiguration {

    @Bean
    AuthorizationPolicyEvaluator authorizationPolicyEvaluator() {
        return new AuthorizationPolicyEvaluator();
    }
}
