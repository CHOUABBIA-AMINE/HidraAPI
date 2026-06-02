/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportingLineException
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.exception
 *
 * @Description : Exception for invalid matrix reporting line rules.
 *
 */
package dz.sh.hidra.modules.organization.domain.exception;

/**
 * Exception for invalid matrix reporting line rules.
 *
 * <p>Business role:
 * This exception represents invalid reporting-line behavior, such as self-reporting, multiple active primary LINE reporting relationships, invalid matrix reporting lines, or assigning a disabled employee as manager.
 *
 * <p>Architecture role:
 * This exception belongs to the organization domain layer. It carries organization business
 * failure semantics without depending on REST, HTTP status codes, Spring, JPA, platform,
 * identity, topology, or infrastructure code.
 *
 * <p>Validation:
 * Use this exception when reporting-line rules for LINE, OPERATIONAL, FUNCTIONAL, ADMINISTRATIVE, TECHNICAL, or DOTTED_LINE relationships are violated.
 *
 * <p>Usage:
 * Throw this exception from ReportingLine behavior, reporting-line policies, or domain services that protect matrix reporting invariants.
 */
public class ReportingLineException extends OrganizationDomainException {

    private static final long serialVersionUID = 1L;

    /**
     * Creates an exception with a domain failure message.
     *
     * @param message failure message
     */
    public ReportingLineException(String message) {
        super(message);
    }

    /**
     * Creates an exception with a domain failure message and root cause.
     *
     * @param message failure message
     * @param cause root cause
     */
    public ReportingLineException(String message, Throwable cause) {
        super(message, cause);
    }
}
