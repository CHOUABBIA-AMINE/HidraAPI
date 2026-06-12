/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : FindMaintainableAssetByIdQuery
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.application.query
 *
 * @Description : Query to find maintainable asset by ID.
 *
 */
package dz.sh.hidra.modules.assets.application.query;

/**
 * Query to find maintainable asset by ID.
 */
public record FindMaintainableAssetByIdQuery(String maintainableAssetId) {
}
