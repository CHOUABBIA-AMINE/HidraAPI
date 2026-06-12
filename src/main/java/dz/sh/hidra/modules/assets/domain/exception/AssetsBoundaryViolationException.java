/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetsBoundaryViolationException
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.domain.exception
 *
 * @Description : Assets boundary violation exception.
 *
 */
package dz.sh.hidra.modules.assets.domain.exception;

/**
 * Raised when assets attempts to own a foreign lifecycle.
 */
public class AssetsBoundaryViolationException extends AssetsDomainException {

    private static final long serialVersionUID = 3154037366781274808L;

	public AssetsBoundaryViolationException(String message) {
        super(message);
    }
}
