/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InvalidPermissionCodeException
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.exception
 *
 * @Description : Exception for invalid identity permission code values.
 *
 */
package dz.sh.hidra.modules.identity.domain.exception;

/**
 * Exception raised when an identity permission code violates the required format.
 *
 * <p>Business role: protects the permission catalog from malformed business access
 * meanings.</p>
 *
 * <p>Architecture role: identity-specific domain exception used by permission code value
 * objects and policies without depending on HTTP, Spring Security, JPA, or platform
 * code.</p>
 *
 * <p>Validation responsibility: reports invalid permission code input, including blank
 * values and values that do not follow the {@code context:resource:action} format.</p>
 *
 * <p>Usage: throw from permission-code validation paths when raw permission text cannot
 * be accepted.</p>
 */
public class InvalidPermissionCodeException extends IdentityDomainException {

    private static final long serialVersionUID = 5272929901708679968L;

    public InvalidPermissionCodeException(String message) {
        super(message);
    }

    public InvalidPermissionCodeException(String message, Throwable cause) {
        super(message, cause);
    }
}
