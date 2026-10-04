/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateTelemetrySourceCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.command
 *
 * @Description : Command to create telemetry source.
 *
 */
package dz.sh.hidra.modules.telemetry.application.command;

import dz.sh.hidra.modules.telemetry.domain.exception.InvalidTelemetryValueException;

/**
 * Command to create telemetry source.
 */
public record CreateTelemetrySourceCommand(
        String code,
        String nameAr,
        String nameFr,
        String nameEn,
        String sourceTypeId,
        String protocolId,
        String endpointUri,
        String externalReference
) {

    public CreateTelemetrySourceCommand {
        if (code == null || code.isBlank()) {
            throw new InvalidTelemetryValueException(
                    "TelemetrySource code must not be blank."
            );
        }
        if (nameFr == null || nameFr.isBlank()) {
            throw new InvalidTelemetryValueException(
                    "TelemetrySource French name must not be blank."
            );
        }
        if (sourceTypeId == null || sourceTypeId.isBlank()) {
            throw new InvalidTelemetryValueException(
                    "TelemetrySource source type id must not be blank."
            );
        }
        if (protocolId == null || protocolId.isBlank()) {
            throw new InvalidTelemetryValueException(
                    "TelemetrySource protocol id must not be blank."
            );
        }

        code = code.trim();
        nameAr = normalize(nameAr);
        nameFr = nameFr.trim();
        nameEn = normalize(nameEn);
        sourceTypeId = sourceTypeId.trim();
        protocolId = protocolId.trim();
        endpointUri = normalize(endpointUri);
        externalReference = normalize(externalReference);
    }

    private static String normalize(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }
}
