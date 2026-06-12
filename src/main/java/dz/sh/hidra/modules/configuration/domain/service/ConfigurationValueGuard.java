/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ConfigurationValueGuard
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.domain.service
 *
 * @Description : Guards configuration values against business-taxonomy and secret-value misuse.
 *
 */
package dz.sh.hidra.modules.configuration.domain.service;

import dz.sh.hidra.modules.configuration.domain.exception.ConfigurationBoundaryViolationException;
import dz.sh.hidra.modules.configuration.domain.policy.ConfigurationBoundaryPolicy;

/**
 * Guards configuration values against business-taxonomy and secret-value misuse.
 */
public class ConfigurationValueGuard {

    public void ensureAllowedDefinition(String key) {
        if (ConfigurationBoundaryPolicy.isForbiddenBusinessTaxonomy(key)) {
            throw new ConfigurationBoundaryViolationException("Configuration must not own module business taxonomy.");
        }
    }

    public void ensureNoSecretMaterial(String rawValue) {
        if (ConfigurationBoundaryPolicy.containsSecretMaterial(rawValue)) {
            throw new ConfigurationBoundaryViolationException("Configuration values must not persist secret material; store references only.");
        }
    }
}
