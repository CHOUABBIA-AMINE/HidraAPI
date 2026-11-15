/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : CustodyMeasurementCaptureUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-15
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.application.port.in
 * @Description : Defines signed capture and exact historical measurement lookup.
 */
package dz.sh.hidra.modules.custody.application.port.in;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
public interface CustodyMeasurementCaptureUseCase {
    record Command(UUID commandId,String meterRunSnapshotId,String measurementTypeId,
                   UUID productPublicationId,String agreementPartyId,String unitId,String intervalEvidenceId) {}
    record Receipt(String measurementId,String periodId,UUID commandId,String meterRunSnapshotId,
        String meteringSystemId,String measurementTypeId,UUID productPublicationId,String agreementPartyId,
        String companyId,String transferPointId,String assetType,String assetId,
        String trustedReadingId,String rawReadingId,String assessmentId,String pointId,String bindingId,
        String intervalEvidenceId,BigDecimal observedValue,String unitId,String quantityBasis,
        Instant intervalStart,Instant intervalEnd,String actorId,Instant capturedAt,
        boolean acceptedForCustody,boolean replay) {
        public Receipt replayed() {
            return new Receipt(measurementId,periodId,commandId,meterRunSnapshotId,meteringSystemId,
                measurementTypeId,productPublicationId,agreementPartyId,companyId,transferPointId,
                assetType,assetId,trustedReadingId,rawReadingId,assessmentId,pointId,bindingId,
                intervalEvidenceId,observedValue,unitId,quantityBasis,intervalStart,intervalEnd,
                actorId,capturedAt,acceptedForCustody,true);
        }
    }
    Receipt capture(String periodId,Command command);
    Receipt read(String periodId,String measurementId);
}
