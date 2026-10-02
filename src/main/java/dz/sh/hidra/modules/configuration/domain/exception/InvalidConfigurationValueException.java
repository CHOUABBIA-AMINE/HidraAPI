/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InvalidConfigurationValueException
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.domain.exception
 *
 * @Description : Invalid configuration value exception.
 *
 */
package dz.sh.hidra.modules.configuration.domain.exception;

/**
 * Raised when a configuration value is invalid.
 */
public class InvalidConfigurationValueException extends ConfigurationDomainException {

    private static final long serialVersionUID = 5591222960044968529L;

	public InvalidConfigurationValueException(String message) {
        super(message);
    }
}
