/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsProjectionSnapshot
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.domain.model
 *
 * @Description : Published projection state.
 *
 */
package dz.sh.hidra.modules.analytics.domain.model;

import dz.sh.hidra.modules.analytics.domain.value.*;
import java.time.Instant;

    /**
     * Published projection state.
     *
         * @param id id
     * @param projectionDefinitionId projectionDefinitionId
     * @param projectionRunId projectionRunId
     * @param snapshotCode snapshotCode
     * @param periodStart periodStart
     * @param periodEnd periodEnd
     * @param snapshotStatus snapshotStatus
     * @param publishedAt publishedAt
     * @param publishedByActorId publishedByActorId
     * @param createdAt createdAt
     */
    public record AnalyticsProjectionSnapshot(
            String id,
        String projectionDefinitionId,
        String projectionRunId,
        String snapshotCode,
        Instant periodStart,
        Instant periodEnd,
        AnalyticsSnapshotStatus snapshotStatus,
        Instant publishedAt,
        String publishedByActorId,
        Instant createdAt
    ) {

        public AnalyticsProjectionSnapshot {
        id = normalize(id);
        projectionDefinitionId = normalize(projectionDefinitionId);
        projectionRunId = normalize(projectionRunId);
        snapshotCode = normalize(snapshotCode);
        publishedByActorId = normalize(publishedByActorId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
