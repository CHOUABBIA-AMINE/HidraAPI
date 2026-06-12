/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateTelemetrySourceRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.api.rest.request
 *
 * @Description : REST request to create telemetry source.
 *
 */
package dz.sh.hidra.modules.telemetry.api.rest.request;

/**
 * REST request to create telemetry source.
 */
public record CreateTelemetrySourceRequest(
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
