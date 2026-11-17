/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : RiskAlarmEvidenceContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-17
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.application.contract.risk
 * @Description : Alarm-owned protected evidence on one active facility and company.
 */
package dz.sh.hidra.modules.alarm.application.contract.risk;

import java.time.Instant;
import java.util.Optional;
public interface RiskAlarmEvidenceContract {
    record AlarmEvidence(String id,String alarmNumber,Instant raisedAt) { }
    Optional<AlarmEvidence> resolve(String alarmId,String companyPartyId,String facilityId);
}
