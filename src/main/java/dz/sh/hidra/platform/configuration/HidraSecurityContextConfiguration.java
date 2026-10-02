/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HidraSecurityContextConfiguration
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Class
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.configuration
 *
 * @Description : Publishes platform security context helper beans.
 *
 */
package dz.sh.hidra.platform.configuration;

import dz.sh.hidra.platform.security.CurrentActorResolver;
import dz.sh.hidra.platform.security.CurrentSecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Publishes platform security helper beans.
 */
@Configuration(proxyBeanMethods = false)
public class HidraSecurityContextConfiguration {

    @Bean
    CurrentActorResolver currentActorResolver(CurrentSecurityContext currentSecurityContext) {
        return new CurrentActorResolver(currentSecurityContext);
    }
}
