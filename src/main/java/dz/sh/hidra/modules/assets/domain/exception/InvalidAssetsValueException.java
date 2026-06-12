/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InvalidAssetsValueException
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.domain.exception
 *
 * @Description : Invalid assets value exception.
 *
 */
package dz.sh.hidra.modules.assets.domain.exception;

/**
 * Raised when an assets value is invalid.
 */
public class InvalidAssetsValueException extends AssetsDomainException {

    private static final long serialVersionUID = -1185597402738677952L;

	public InvalidAssetsValueException(String message) {
        super(message);
    }
}
