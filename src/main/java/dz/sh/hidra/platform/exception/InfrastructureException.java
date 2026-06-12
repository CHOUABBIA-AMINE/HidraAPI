/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InfrastructureException
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.exception
 *
 * @Description : Represents a technical infrastructure-level failure.
 *
 */
package dz.sh.hidra.platform.exception;

/**
 * Technical exception for infrastructure-level failures.
 */
public class InfrastructureException extends PlatformException {

    private static final long serialVersionUID = 6891743581016380920L;

	public InfrastructureException(String message) {
        super(message);
    }

    public InfrastructureException(String message, Throwable cause) {
        super(message, cause);
    }
}
