/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationHierarchyException
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.exception
 *
 * @Description : Exception for invalid organization hierarchy rules.
 *
 */
package dz.sh.hidra.modules.organization.domain.exception;

/**
 * Exception for invalid organization hierarchy rules.
 *
 * <p>Business role:
 * This exception represents invalid organization hierarchy behavior, such as self-parenting, parent-child cycles, or invalid hierarchy changes between divisions, regions, station organization units, and teams.
 *
 * <p>Architecture role:
 * This exception belongs to the organization domain layer. It carries organization business
 * failure semantics without depending on REST, HTTP status codes, Spring, JPA, platform,
 * identity, topology, or infrastructure code.
 *
 * <p>Validation:
 * Use this exception when organization unit hierarchy rules are violated.
 *
 * <p>Usage:
 * Throw this exception from organization unit aggregate behavior, hierarchy policies, or domain services that validate parent-child relationships.
 */
public class OrganizationHierarchyException extends OrganizationDomainException {

    private static final long serialVersionUID = 1L;

    /**
     * Creates an exception with a domain failure message.
     *
     * @param message failure message
     */
    public OrganizationHierarchyException(String message) {
        super(message);
    }

    /**
     * Creates an exception with a domain failure message and root cause.
     *
     * @param message failure message
     * @param cause root cause
     */
    public OrganizationHierarchyException(String message, Throwable cause) {
        super(message, cause);
    }
}
