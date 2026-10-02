/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ConfigurationBoundaryViolationException
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.domain.exception
 *
 * @Description : Configuration boundary violation exception.
 *
 */
package dz.sh.hidra.modules.configuration.domain.exception;

/**
 * Raised when configuration attempts to own module business taxonomies or secret values.
 */
public class ConfigurationBoundaryViolationException extends ConfigurationDomainException {

    private static final long serialVersionUID = -8279234451582044583L;

	public ConfigurationBoundaryViolationException(String message) {
        super(message);
    }
}
