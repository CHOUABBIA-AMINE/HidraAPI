/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MaintainableAssetConflictException
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-12
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.domain.exception
 *
 * @Description : Raised when a maintainable asset update uses a stale concurrency precondition.
 *
 */
package dz.sh.hidra.modules.assets.domain.exception;

public final class MaintainableAssetConflictException extends AssetsDomainException {

    private static final long serialVersionUID = 7391161842911540536L;

    public MaintainableAssetConflictException(String message) {
        super(message);
    }
}
