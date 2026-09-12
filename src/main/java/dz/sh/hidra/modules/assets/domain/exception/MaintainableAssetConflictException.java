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
 * @Description : Signals a stale maintainable-asset metadata update.
 *
 */
package dz.sh.hidra.modules.assets.domain.exception;

public final class MaintainableAssetConflictException extends AssetsDomainException {

    private static final long serialVersionUID = 1L;

    public MaintainableAssetConflictException(String message) {
        super(message);
    }
}
