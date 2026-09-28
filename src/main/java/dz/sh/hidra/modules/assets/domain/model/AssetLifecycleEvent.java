/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetLifecycleEvent
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import dz.sh.hidra.modules.assets.domain.exception.InvalidAssetsValueException;
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
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidAssetsValueException("AssetLifecycleEvent id must not be blank.");
        }
        // HRA-051 required: maintainableAssetId
        if (maintainableAssetId == null || maintainableAssetId.isBlank()) {
            throw new InvalidAssetsValueException("AssetLifecycleEvent maintainable asset id must not be blank.");
        }
        // HRA-051 required: eventType
        if (eventType == null) {
            throw new InvalidAssetsValueException("AssetLifecycleEvent event type must not be null.");
        }
        // HRA-051 required: newStatus
        if (newStatus == null) {
            throw new InvalidAssetsValueException("AssetLifecycleEvent new status must not be null.");
        }
        // HRA-051 required: eventAt
        if (eventAt == null) {
            throw new InvalidAssetsValueException("AssetLifecycleEvent event at must not be null.");
        }

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
