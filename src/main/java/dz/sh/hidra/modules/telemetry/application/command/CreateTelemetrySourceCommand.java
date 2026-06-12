/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateTelemetrySourceCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
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
}
