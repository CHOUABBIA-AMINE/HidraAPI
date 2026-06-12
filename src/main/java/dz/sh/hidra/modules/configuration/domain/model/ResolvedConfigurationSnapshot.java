/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ResolvedConfigurationSnapshot
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.domain.model
 *
 * @Description : Resolved effective configuration snapshot.
 *
 */
package dz.sh.hidra.modules.configuration.domain.model;

import dz.sh.hidra.modules.configuration.domain.value.*;
import java.time.Instant;

    /**
     * Resolved effective configuration snapshot.
     *
         * @param id id
     * @param snapshotNumber snapshotNumber
     * @param profileId profileId
     * @param namespaceId namespaceId
     * @param targetModule targetModule
     * @param scopeType scopeType
     * @param scopeId scopeId
     * @param environment environment
     * @param resolvedValuesJson resolvedValuesJson
     * @param hashValue hashValue
     * @param status status
     * @param resolvedAt resolvedAt
     * @param expiresAt expiresAt
     */
    public record ResolvedConfigurationSnapshot(
            String id,
        String snapshotNumber,
        String profileId,
        String namespaceId,
        String targetModule,
        ConfigurationScopeType scopeType,
        String scopeId,
        String environment,
        String resolvedValuesJson,
        String hashValue,
        ResolvedSnapshotStatus status,
        Instant resolvedAt,
        Instant expiresAt
    ) {

        public ResolvedConfigurationSnapshot {
        id = normalize(id);
        snapshotNumber = normalize(snapshotNumber);
        profileId = normalize(profileId);
        namespaceId = normalize(namespaceId);
        targetModule = normalize(targetModule);
        scopeId = normalize(scopeId);
        environment = normalize(environment);
        resolvedValuesJson = normalize(resolvedValuesJson);
        hashValue = normalize(hashValue);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
