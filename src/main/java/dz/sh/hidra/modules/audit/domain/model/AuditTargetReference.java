/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditTargetReference
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.domain.model
 *
 * @Description : Normalized target object reference.
 *
 */
package dz.sh.hidra.modules.audit.domain.model;

import java.time.Instant;

    /**
     * Normalized target object reference.
     *
         * @param id id
     * @param auditEventId auditEventId
     * @param targetModule targetModule
     * @param targetType targetType
     * @param targetId targetId
     * @param targetCodeSnapshot targetCodeSnapshot
     * @param targetLabelSnapshot targetLabelSnapshot
     * @param targetVersion targetVersion
     * @param topologyAssetTypeCode topologyAssetTypeCode
     * @param topologyAssetId topologyAssetId
     * @param topologyAssetCodeSnapshot topologyAssetCodeSnapshot
     * @param capturedAt capturedAt
     */
    public record AuditTargetReference(
            String id,
        String auditEventId,
        String targetModule,
        String targetType,
        String targetId,
        String targetCodeSnapshot,
        String targetLabelSnapshot,
        String targetVersion,
        String topologyAssetTypeCode,
        String topologyAssetId,
        String topologyAssetCodeSnapshot,
        Instant capturedAt
    ) {

        public AuditTargetReference {
        id = normalize(id);
        auditEventId = normalize(auditEventId);
        targetModule = normalize(targetModule);
        targetType = normalize(targetType);
        targetId = normalize(targetId);
        targetCodeSnapshot = normalize(targetCodeSnapshot);
        targetLabelSnapshot = normalize(targetLabelSnapshot);
        targetVersion = normalize(targetVersion);
        topologyAssetTypeCode = normalize(topologyAssetTypeCode);
        topologyAssetId = normalize(topologyAssetId);
        topologyAssetCodeSnapshot = normalize(topologyAssetCodeSnapshot);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
