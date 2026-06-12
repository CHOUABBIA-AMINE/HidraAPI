/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetsDomainException
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.domain.exception
 *
 * @Description : Base assets domain exception.
 *
 */
package dz.sh.hidra.modules.assets.domain.exception;

/**
 * Base exception for assets domain failures.
 */
public class AssetsDomainException extends RuntimeException {

    private static final long serialVersionUID = 6778475930170955845L;

	public AssetsDomainException(String message) {
        super(requireMessage(message));
    }

    public AssetsDomainException(String message, Throwable cause) {
        super(requireMessage(message), cause);
    }

    private static String requireMessage(String message) {
        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("Assets exception message must not be null or blank.");
        }
        return message.trim();
    }
}
