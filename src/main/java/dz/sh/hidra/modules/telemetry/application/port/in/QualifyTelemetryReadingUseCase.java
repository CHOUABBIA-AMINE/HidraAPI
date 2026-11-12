/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : QualifyTelemetryReadingUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-12
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.port.in
 *
 * @Description : Qualifies synthetic raw observations using locked approved quality policies.
 *
 */
package dz.sh.hidra.modules.telemetry.application.port.in;

import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryQualityAssessmentPort.Assessment;

public interface QualifyTelemetryReadingUseCase {
    Assessment qualify(String companyPartyId, String pointId, String readingId);
    Assessment find(String companyPartyId, String pointId, String readingId);
}
