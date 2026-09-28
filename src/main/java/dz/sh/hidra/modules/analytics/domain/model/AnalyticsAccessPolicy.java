/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsAccessPolicy
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.domain.model
 *
 * @Description : Access policy reference for analytics assets.
 *
 */
package dz.sh.hidra.modules.analytics.domain.model;

import dz.sh.hidra.modules.analytics.domain.exception.InvalidAnalyticsValueException;
import java.time.Instant;

    /**
     * Access policy reference for analytics assets.
     *
         * @param id id
     * @param analyticsObjectType analyticsObjectType
     * @param analyticsObjectId analyticsObjectId
     * @param accessScopeType accessScopeType
     * @param accessScopeId accessScopeId
     * @param permissionCode permissionCode
     * @param active active
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record AnalyticsAccessPolicy(
            String id,
        String analyticsObjectType,
        String analyticsObjectId,
        String accessScopeType,
        String accessScopeId,
        String permissionCode,
        boolean active,
        Instant createdAt,
        Instant updatedAt
    ) {

        public AnalyticsAccessPolicy {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidAnalyticsValueException("AnalyticsAccessPolicy id must not be blank.");
        }
        // HRA-051 required: analyticsObjectId
        if (analyticsObjectId == null || analyticsObjectId.isBlank()) {
            throw new InvalidAnalyticsValueException("AnalyticsAccessPolicy analytics object id must not be blank.");
        }
        // HRA-051 required: accessScopeId
        if (accessScopeId == null || accessScopeId.isBlank()) {
            throw new InvalidAnalyticsValueException("AnalyticsAccessPolicy access scope id must not be blank.");
        }
        // HRA-051 required: permissionCode
        if (permissionCode == null || permissionCode.isBlank()) {
            throw new InvalidAnalyticsValueException("AnalyticsAccessPolicy permission code must not be blank.");
        }

        id = normalize(id);
        analyticsObjectType = normalize(analyticsObjectType);
        analyticsObjectId = normalize(analyticsObjectId);
        accessScopeType = normalize(accessScopeType);
        accessScopeId = normalize(accessScopeId);
        permissionCode = normalize(permissionCode);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
