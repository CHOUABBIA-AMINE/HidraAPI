/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OpenApiConfiguration
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.configuration
 *
 * @Description : Provides central OpenAPI metadata values from application configuration.
 *
 */
package dz.sh.hidra.platform.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class OpenApiConfiguration {

    @Bean
    OpenApiMetadata openApiMetadata(
            @Value("${hidra.application.name:HidraAPI}") String applicationName,
            @Value("${hidra.application.description:Hydrocarbon Intelligence for Data, Risk, and Analytics}") String applicationDescription,
            @Value("${hidra.api.version:v1}") String apiVersion) {
        return new OpenApiMetadata(applicationName, applicationDescription, apiVersion);
    }

    public record OpenApiMetadata(String applicationName, String applicationDescription, String apiVersion) {
    }
}
