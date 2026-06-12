/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetLifecycleEvent
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.domain.model
 *
 * @Description : Lifecycle event such as installed, commissioned, retired.
 *
 */
package dz.sh.hidra.modules.assets.domain.model;

import dz.sh.hidra.modules.assets.domain.value.*;
import java.time.Instant;

    /**
     * Lifecycle event such as installed, commissioned, retired.
     *
         * @param id id
     * @param maintainableAssetId maintainableAssetId
     * @param eventType eventType
     * @param oldStatus oldStatus
     * @param newStatus newStatus
     * @param eventReasonId eventReasonId
     * @param eventComment eventComment
     * @param actorId actorId
     * @param eventAt eventAt
     * @param correlationId correlationId
     * @param createdAt createdAt
     */
    public record AssetLifecycleEvent(
            String id,
        String maintainableAssetId,
        AssetLifecycleEventType eventType,
        AssetLifecycleStatus oldStatus,
        AssetLifecycleStatus newStatus,
        String eventReasonId,
        String eventComment,
        String actorId,
        Instant eventAt,
        String correlationId,
        Instant createdAt
    ) {

        public AssetLifecycleEvent {
        id = normalize(id);
        maintainableAssetId = normalize(maintainableAssetId);
        eventReasonId = normalize(eventReasonId);
        eventComment = normalize(eventComment);
        actorId = normalize(actorId);
        correlationId = normalize(correlationId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
