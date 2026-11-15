/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : CustodyMeasurementCaptureResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-15
 * @Type        : Class
 * @Layer       : REST Adapter
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.api.rest.response
 * @Description : Exposes immutable interval quantity and provenance without fiscal acceptance.
 */
package dz.sh.hidra.modules.custody.api.rest.response;

import dz.sh.hidra.modules.custody.application.port.in.CustodyMeasurementCaptureUseCase;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
public record CustodyMeasurementCaptureResponse(String measurementId,String periodId,UUID commandId,
    String meterRunSnapshotId,String meteringSystemId,String measurementTypeId,
    UUID productPublicationId,String agreementPartyId,String companyId,String transferPointId,
    String assetType,String assetId,String trustedReadingId,String rawReadingId,
    String assessmentId,String pointId,String bindingId,String intervalEvidenceId,
    BigDecimal observedValue,String unitId,String quantityBasis,Instant intervalStart,Instant intervalEnd,
    String actorId,Instant capturedAt,boolean acceptedForCustody,boolean replay) {
    public static CustodyMeasurementCaptureResponse from(CustodyMeasurementCaptureUseCase.Receipt x){
        return new CustodyMeasurementCaptureResponse(x.measurementId(),x.periodId(),x.commandId(),
            x.meterRunSnapshotId(),x.meteringSystemId(),x.measurementTypeId(),x.productPublicationId(),
            x.agreementPartyId(),x.companyId(),x.transferPointId(),x.assetType(),x.assetId(),
            x.trustedReadingId(),x.rawReadingId(),x.assessmentId(),x.pointId(),x.bindingId(),
            x.intervalEvidenceId(),x.observedValue(),x.unitId(),x.quantityBasis(),x.intervalStart(),
            x.intervalEnd(),x.actorId(),x.capturedAt(),x.acceptedForCustody(),x.replay());
    }
}
