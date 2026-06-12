/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationDomainException
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.exception
 *
 * @Description : Base organization domain exception.
 *
 */
package dz.sh.hidra.modules.organization.domain.exception;

/**
 * Base exception for organization domain failures.
 */
public class OrganizationDomainException extends RuntimeException {

    private static final long serialVersionUID = 8726108678857929660L;

	public OrganizationDomainException(String message) {
        super(requireMessage(message));
    }

    public OrganizationDomainException(String message, Throwable cause) {
        super(requireMessage(message), cause);
    }

    private static String requireMessage(String message) {
        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("Organization exception message must not be null or blank.");
        }
        return message.trim();
    }
}
