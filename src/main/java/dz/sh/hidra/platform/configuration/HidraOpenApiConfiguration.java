/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HidraOpenApiConfiguration
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Class
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.configuration
 *
 * @Description : Defines the Hidra OpenAPI metadata bean.
 *
 */
package dz.sh.hidra.platform.configuration;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Defines explicit OpenAPI metadata instead of relying only on SpringDoc defaults.
 */
@Configuration(proxyBeanMethods = false)
public class HidraOpenApiConfiguration {

    @Bean
    OpenAPI hidraOpenApi(
            @Value("${hidra.application.name:HidraAPI}") String name,
            @Value("${hidra.application.description:Hydrocarbon Intelligence for Data, Risk, and Analytics}") String description,
            @Value("${hidra.api.version:v1}") String version
    ) {
        return new OpenAPI()
                .info(new Info()
                        .title(name)
                        .description(description)
                        .version(version)
                        .license(new License().name("MIT License")));
    }
}
