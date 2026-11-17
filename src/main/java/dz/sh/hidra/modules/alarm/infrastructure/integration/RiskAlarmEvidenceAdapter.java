/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : RiskAlarmEvidenceAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-17
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.infrastructure.integration
 * @Description : Resolve protected Alarm evidence scoped to the exact current Topology owner.
 */
package dz.sh.hidra.modules.alarm.infrastructure.integration;

import dz.sh.hidra.modules.alarm.application.contract.risk.RiskAlarmEvidenceContract;
import dz.sh.hidra.modules.alarm.application.port.out.AlarmResourceAuthorizationPort;
import dz.sh.hidra.modules.alarm.infrastructure.persistence.repository.AlarmJpaRepository;
import java.util.Optional;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class RiskAlarmEvidenceAdapter implements RiskAlarmEvidenceContract {
    private final AlarmJpaRepository alarms;
    private final AlarmResourceAuthorizationPort rights;
    public RiskAlarmEvidenceAdapter(AlarmJpaRepository alarms,AlarmResourceAuthorizationPort rights) {
        this.alarms=alarms;this.rights=rights;
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Optional<AlarmEvidence> resolve(String id,String company,String facility){
        if(id==null||id.isBlank()||company==null||company.isBlank()||facility==null||facility.isBlank())
            return Optional.empty();
        // The Risk caller has already checked the frozen company/facility against
        // current Topology ownership and the current Risk author grant.
        // Alarm's own authorization checks the current Alarm owner and native
        // company plus asset grants before resolving or revealing any source detail.
        try { rights.requireAlarm("alarm:alarms:read",id); }
        catch(RuntimeException unavailable) { return Optional.empty(); }
        return alarms.findById(id)
                .filter(a->"FACILITY".equals(a.topologyAssetTypeCode())
                        &&facility.equals(a.topologyAssetId()))
                .map(a->new AlarmEvidence(a.id(),a.alarmNumber(),a.raisedAt()));
    }
}
