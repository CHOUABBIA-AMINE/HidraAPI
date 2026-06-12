/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetrySource
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.domain.model
 *
 * @Description : Acquisition source such as SCADA, historian, OPC server, API feed, manual import source, or edge gateway.
 *
 */
package dz.sh.hidra.modules.telemetry.domain.model;

import dz.sh.hidra.modules.telemetry.domain.value.*;
import java.time.Instant;

    /**
     * Acquisition source such as SCADA, historian, OPC server, API feed, manual import source, or edge gateway.
     *
         * @param id id
     * @param code code
     * @param nameAr nameAr
     * @param nameFr nameFr
     * @param nameEn nameEn
     * @param sourceTypeId sourceTypeId
     * @param protocolId protocolId
     * @param endpointUri endpointUri
     * @param externalReference externalReference
     * @param status status
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record TelemetrySource(
            String id,
        String code,
        String nameAr,
        String nameFr,
        String nameEn,
        String sourceTypeId,
        String protocolId,
        String endpointUri,
        String externalReference,
        TelemetryLifecycleStatus status,
        Instant createdAt,
        Instant updatedAt
    ) {

        public TelemetrySource {
        id = normalize(id);
        code = normalize(code);
        nameAr = normalize(nameAr);
        nameFr = normalize(nameFr);
        nameEn = normalize(nameEn);
        sourceTypeId = normalize(sourceTypeId);
        protocolId = normalize(protocolId);
        endpointUri = normalize(endpointUri);
        externalReference = normalize(externalReference);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
