/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InvalidValueObjectException
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Kernel
 * @Module      : kernel
 * @Package     : dz.sh.hidra.kernel.domain.exception
 *
 * @Description : Generic exception for invalid value object construction.
 *
 */
package dz.sh.hidra.kernel.domain.exception;

public class InvalidValueObjectException extends DomainException {

    public InvalidValueObjectException(String message) {
        super(message);
    }

    public InvalidValueObjectException(String message, Throwable cause) {
        super(message, cause);
    }
}
