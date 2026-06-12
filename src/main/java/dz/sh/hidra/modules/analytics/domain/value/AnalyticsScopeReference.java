/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsScopeReference
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.domain.value
 *
 * @Description : Neutral analytical scope reference.
 *
 */
package dz.sh.hidra.modules.analytics.domain.value;

/**
 * Neutral analytical scope reference.
 *
 * @param scopeType analytical scope type
 * @param scopeId analytical scope identifier
 * @param scopeCodeSnapshot scope code snapshot
 * @param scopeLabelSnapshot scope label snapshot
 * @param topologySnapshotId topology snapshot reference when applicable
 */
public record AnalyticsScopeReference(
        String scopeType,
        String scopeId,
        String scopeCodeSnapshot,
        String scopeLabelSnapshot,
        String topologySnapshotId
) {
}
