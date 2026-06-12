/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InvalidIdentityValueException
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.exception
 *
 * @Description : Represents invalid identity value object state.
 *
 */
package dz.sh.hidra.modules.identity.domain.exception;

/**
 * Raised when an identity value object or entity field is invalid.
 */
public class InvalidIdentityValueException extends IdentityDomainException {

    public InvalidIdentityValueException(String message) {
        super(message);
    }
}
