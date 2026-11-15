/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : CustodyMeasurementCapturePort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-15
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.application.port.out
 * @Description : Provides Custody-owned period, publication and immutable receipt storage.
 */
package dz.sh.hidra.modules.custody.application.port.out;

import dz.sh.hidra.modules.custody.application.port.in.CustodyMeasurementCaptureUseCase;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
public interface CustodyMeasurementCapturePort {
    record Context(String periodId,String agreementId,String transferPointId,String companyAssetType,
        String facilityId,String meteringSystemId,String meterRunId,String agreementPartyId,String partyId,
        String partyRoleId,String productTypeId,Instant periodStart,Instant periodEnd,
        Instant agreementStart,Instant agreementEnd,Instant transferStart,Instant transferEnd,
        Instant meterStart,Instant meterEnd,Instant partyStart,Instant partyEnd) {}
    record Scope(String transferPointId,String assetType,String assetId) {}
    Optional<Scope> scope(String periodId);
    Optional<Context> lockContext(String periodId,String meterRunId,String agreementPartyId);
    boolean currentProduct(Context context,String companyId,UUID publicationId);
    boolean measurementTypeActive(String typeId);
    Optional<Stored> byCommand(String periodId,UUID commandId);
    Optional<CustodyMeasurementCaptureUseCase.Receipt> read(String periodId,String measurementId);
    boolean sourceExists(String periodId,String meterRunId,String measurementTypeId,String intervalEvidenceId);
    void append(CustodyMeasurementCaptureUseCase.Receipt receipt,String fingerprint);
    record Stored(CustodyMeasurementCaptureUseCase.Receipt receipt,String fingerprint){}
}
