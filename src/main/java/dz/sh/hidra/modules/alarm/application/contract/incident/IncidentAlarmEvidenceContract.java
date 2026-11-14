/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : IncidentAlarmEvidenceContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-14
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.application.contract.incident
 * @Description : Only the Incident-approved scalar read of protected T12 Alarm evidence.
 */
package dz.sh.hidra.modules.alarm.application.contract.incident;

import java.time.Instant;

public interface IncidentAlarmEvidenceContract {
    record Evidence(String alarmId, String evaluationId, String companyId,
                    String assetType, String assetId, Instant evaluatedAt, String state) {}
    Evidence authorized(String alarmId);
}
