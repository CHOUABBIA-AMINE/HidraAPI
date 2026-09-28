/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsProjectionSnapshot
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import dz.sh.hidra.modules.analytics.domain.exception.InvalidAnalyticsValueException;
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
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidAnalyticsValueException("AnalyticsProjectionSnapshot id must not be blank.");
        }
        // HRA-051 required: projectionDefinitionId
        if (projectionDefinitionId == null || projectionDefinitionId.isBlank()) {
            throw new InvalidAnalyticsValueException("AnalyticsProjectionSnapshot projection definition id must not be blank.");
        }
        // HRA-051 required: projectionRunId
        if (projectionRunId == null || projectionRunId.isBlank()) {
            throw new InvalidAnalyticsValueException("AnalyticsProjectionSnapshot projection run id must not be blank.");
        }
        // HRA-051 required: snapshotCode
        if (snapshotCode == null || snapshotCode.isBlank()) {
            throw new InvalidAnalyticsValueException("AnalyticsProjectionSnapshot snapshot code must not be blank.");
        }
        // HRA-051 required: snapshotStatus
        if (snapshotStatus == null) {
            throw new InvalidAnalyticsValueException("AnalyticsProjectionSnapshot snapshot status must not be null.");
        }
        // HRA-051 order: periodStart <= periodEnd
        if (periodStart != null && periodEnd != null && periodEnd.isBefore(periodStart)) {
            throw new InvalidAnalyticsValueException("AnalyticsProjectionSnapshot period end must not be before period start.");
        }

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
