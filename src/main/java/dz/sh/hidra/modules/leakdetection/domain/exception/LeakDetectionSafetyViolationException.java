/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakDetectionSafetyViolationException
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.domain.exception
 *
 * @Description : Leak detection safety violation exception.
 *
 */
package dz.sh.hidra.modules.leakdetection.domain.exception;

/**
 * Raised when leak detection attempts forbidden OT/control behavior.
 */
public class LeakDetectionSafetyViolationException extends LeakDetectionDomainException {

    private static final long serialVersionUID = 4272557461706859292L;

	public LeakDetectionSafetyViolationException(String message) {
        super(message);
    }
}
