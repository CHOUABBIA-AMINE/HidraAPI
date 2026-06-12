/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryDevice
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.domain.model
 *
 * @Description : Physical or logical telemetry-producing device under a source.
 *
 */
package dz.sh.hidra.modules.telemetry.domain.model;

import dz.sh.hidra.modules.telemetry.domain.value.*;
import java.time.Instant;

    /**
     * Physical or logical telemetry-producing device under a source.
     *
         * @param id id
     * @param sourceId sourceId
     * @param code code
     * @param nameAr nameAr
     * @param nameFr nameFr
     * @param nameEn nameEn
     * @param deviceTypeId deviceTypeId
     * @param externalReference externalReference
     * @param manufacturerPartyId manufacturerPartyId
     * @param modelReference modelReference
     * @param serialNumber serialNumber
     * @param firmwareVersion firmwareVersion
     * @param status status
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record TelemetryDevice(
            String id,
        String sourceId,
        String code,
        String nameAr,
        String nameFr,
        String nameEn,
        String deviceTypeId,
        String externalReference,
        String manufacturerPartyId,
        String modelReference,
        String serialNumber,
        String firmwareVersion,
        TelemetryLifecycleStatus status,
        Instant createdAt,
        Instant updatedAt
    ) {

        public TelemetryDevice {
        id = normalize(id);
        sourceId = normalize(sourceId);
        code = normalize(code);
        nameAr = normalize(nameAr);
        nameFr = normalize(nameFr);
        nameEn = normalize(nameEn);
        deviceTypeId = normalize(deviceTypeId);
        externalReference = normalize(externalReference);
        manufacturerPartyId = normalize(manufacturerPartyId);
        modelReference = normalize(modelReference);
        serialNumber = normalize(serialNumber);
        firmwareVersion = normalize(firmwareVersion);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
