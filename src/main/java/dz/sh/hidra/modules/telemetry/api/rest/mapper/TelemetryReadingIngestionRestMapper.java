/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryReadingIngestionRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-10
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.api.rest.mapper
 *
 * @Description : Maps untrusted REST request into application command and canonical receipt.
 *
 */
package dz.sh.hidra.modules.telemetry.api.rest.mapper;


import dz.sh.hidra.modules.telemetry.api.rest.request.IngestTelemetryReadingRequest;
import dz.sh.hidra.modules.telemetry.api.rest.response.IngestTelemetryReadingResponse;
import dz.sh.hidra.modules.telemetry.application.command.IngestTelemetryReadingCommand;
import dz.sh.hidra.modules.telemetry.application.port.in.IngestTelemetryReadingUseCase;
public final class TelemetryReadingIngestionRestMapper {
    private TelemetryReadingIngestionRestMapper() { }
    public static IngestTelemetryReadingCommand toCommand(String point, IngestTelemetryReadingRequest input) {
        return new IngestTelemetryReadingCommand(point,input.sourceId(),input.sourceSequenceNumber(),
                input.numericValue(),input.textValue(),input.booleanValue(),
                input.unitId(),input.qualityCodeId(),input.sourceTimestamp());
    }
    public static IngestTelemetryReadingResponse toResponse(IngestTelemetryReadingUseCase.Outcome result) {
        var r=result.reading();
        return new IngestTelemetryReadingResponse(r.id(),r.pointId(),result.sourceId(),
                r.numericValue(),r.textValue(),r.booleanValue(),result.effectiveUnitId(),
                r.qualityCodeId(),r.sourceTimestamp(),r.receivedAt(),r.state().name(),result.replayed());
    }
}
