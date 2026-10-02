/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ConfigurationDomainException
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.domain.exception
 *
 * @Description : Base configuration domain exception.
 *
 */
package dz.sh.hidra.modules.configuration.domain.exception;

/**
 * Base exception for configuration domain failures.
 */
public class ConfigurationDomainException extends RuntimeException {

    private static final long serialVersionUID = 6734683381977041839L;

	public ConfigurationDomainException(String message) {
        super(requireMessage(message));
    }

    public ConfigurationDomainException(String message, Throwable cause) {
        super(requireMessage(message), cause);
    }

    private static String requireMessage(String message) {
        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("Configuration exception message must not be null or blank.");
        }
        return message.trim();
    }
}
