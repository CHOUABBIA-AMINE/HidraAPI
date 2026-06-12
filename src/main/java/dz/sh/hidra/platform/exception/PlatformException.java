/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlatformException
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.exception
 *
 * @Description : Defines the base technical platform exception.
 *
 */
package dz.sh.hidra.platform.exception;

/**
 * Base technical exception for platform infrastructure failures.
 */
public class PlatformException extends RuntimeException {

    private static final long serialVersionUID = 3572415077860796518L;

	public PlatformException(String message) {
        super(requireMessage(message));
    }

    public PlatformException(String message, Throwable cause) {
        super(requireMessage(message), cause);
    }

    private static String requireMessage(String message) {
        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("Platform exception message must not be null or blank.");
        }
        return message.trim();
    }
}
