/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InvalidLeakDetectionValueException
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.domain.exception
 *
 * @Description : Invalid leak detection value exception.
 *
 */
package dz.sh.hidra.modules.leakdetection.domain.exception;

/**
 * Raised when a leak detection value is invalid.
 */
public class InvalidLeakDetectionValueException extends LeakDetectionDomainException {

    private static final long serialVersionUID = -8280745524870532003L;

	public InvalidLeakDetectionValueException(String message) {
        super(message);
    }
}
