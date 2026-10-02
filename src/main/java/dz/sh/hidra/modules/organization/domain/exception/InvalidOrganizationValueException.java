/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InvalidOrganizationValueException
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.exception
 *
 * @Description : Invalid organization value exception.
 *
 */
package dz.sh.hidra.modules.organization.domain.exception;

/**
 * Raised when an organization value is invalid.
 */
public class InvalidOrganizationValueException extends OrganizationDomainException {

    private static final long serialVersionUID = 4305136665113784248L;

	public InvalidOrganizationValueException(String message) {
        super(message);
    }
}
