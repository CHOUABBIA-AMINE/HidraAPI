/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ResolvedConfigurationProjection
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Infrastructure
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.infrastructure.projection
 *
 * @Description : Configuration resolved snapshot projection.
 *
 */
package dz.sh.hidra.modules.configuration.infrastructure.projection;

import dz.sh.hidra.modules.configuration.domain.value.ResolvedSnapshotStatus;

import java.time.Instant;

/**
 * Configuration resolved snapshot projection.
 */
public record ResolvedConfigurationProjection(
        String snapshotId,
        String targetModule,
        String scopeType,
        String scopeId,
        String environment,
        String hashValue,
        ResolvedSnapshotStatus status,
        Instant resolvedAt
) {
}
