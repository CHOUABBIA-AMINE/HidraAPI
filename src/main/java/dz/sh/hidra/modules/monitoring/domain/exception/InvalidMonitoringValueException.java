/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InvalidMonitoringValueException
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.domain.exception
 *
 * @Description : Invalid monitoring value exception.
 *
 */
package dz.sh.hidra.modules.monitoring.domain.exception;

/**
 * Raised when a monitoring value is invalid.
 */
public class InvalidMonitoringValueException extends MonitoringDomainException {

    private static final long serialVersionUID = -8083025215309915643L;

	public InvalidMonitoringValueException(String message) {
        super(message);
    }
}
