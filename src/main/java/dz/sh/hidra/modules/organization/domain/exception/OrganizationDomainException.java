/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationDomainException
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.exception
 *
 * @Description : Base exception for organization domain failures.
 *
 */
package dz.sh.hidra.modules.organization.domain.exception;

import dz.sh.hidra.kernel.domain.exception.DomainException;

/**
 * Base exception for organization domain failures.
 *
 * <p>Business role:
 * This exception is the base organization-specific failure type for employee, organization unit, position, assignment, hierarchy, and reporting-line domain errors.
 *
 * <p>Architecture role:
 * This exception belongs to the organization domain layer. It carries organization business
 * failure semantics without depending on REST, HTTP status codes, Spring, JPA, platform,
 * identity, topology, or infrastructure code.
 *
 * <p>Validation:
 * Use this base type when the failure belongs to organization but does not yet have a more specific exception type.
 *
 * <p>Usage:
 * Throw this exception only from organization domain or application logic when a more precise organization exception does not exist.
 */
public class OrganizationDomainException extends DomainException {

    private static final long serialVersionUID = 1L;

    /**
     * Creates an exception with a domain failure message.
     *
     * @param message failure message
     */
    public OrganizationDomainException(String message) {
        super(message);
    }

    /**
     * Creates an exception with a domain failure message and root cause.
     *
     * @param message failure message
     * @param cause root cause
     */
    public OrganizationDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
