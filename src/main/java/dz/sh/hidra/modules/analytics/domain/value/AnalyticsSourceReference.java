/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsSourceReference
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.domain.value
 *
 * @Description : Neutral analytical source reference.
 *
 */
package dz.sh.hidra.modules.analytics.domain.value;

/**
 * Neutral analytical source reference.
 *
 * @param sourceModule source module name
 * @param sourceObjectType source object type
 * @param sourceObjectId source object identifier
 * @param sourceSnapshotId source snapshot identifier
 * @param sourceVersion source version
 */
public record AnalyticsSourceReference(
        String sourceModule,
        String sourceObjectType,
        String sourceObjectId,
        String sourceSnapshotId,
        String sourceVersion
) {
}
