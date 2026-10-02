/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InvalidValueObjectException
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Kernel
 * @Module      : kernel
 * @Package     : dz.sh.hidra.kernel.exception
 *
 * @Description : Represents invalid value-object internal state.
 *
 */
package dz.sh.hidra.kernel.exception;

/**
 * Raised when a value object is created with invalid internal state.
 */
public class InvalidValueObjectException extends DomainException {

    private static final long serialVersionUID = -1093560691776731278L;

	public InvalidValueObjectException(String message) {
        super(message);
    }

    public InvalidValueObjectException(String message, Throwable cause) {
        super(message, cause);
    }
}
