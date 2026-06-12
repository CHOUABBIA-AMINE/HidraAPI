/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ConfigurationValidationRuleRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.application.port.out
 *
 * @Description : Repository port for ConfigurationValidationRule.
 *
 */
package dz.sh.hidra.modules.configuration.application.port.out;

import dz.sh.hidra.modules.configuration.domain.model.ConfigurationValidationRule;

import java.util.Optional;

/**
 * Repository port for ConfigurationValidationRule.
 */
public interface ConfigurationValidationRuleRepositoryPort {

    ConfigurationValidationRule save(ConfigurationValidationRule model);

    Optional<ConfigurationValidationRule> findById(String id);
}
