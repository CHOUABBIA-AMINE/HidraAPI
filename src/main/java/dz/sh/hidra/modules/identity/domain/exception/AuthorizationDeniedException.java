/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuthorizationDeniedException
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.exception
 *
 * @Description : Represents an authorization denial.
 *
 */
package dz.sh.hidra.modules.identity.domain.exception;

/**
 * Raised when identity authorization denies an operation.
 */
public class AuthorizationDeniedException extends IdentityDomainException {

    public AuthorizationDeniedException(String message) {
        super(message);
    }
}
