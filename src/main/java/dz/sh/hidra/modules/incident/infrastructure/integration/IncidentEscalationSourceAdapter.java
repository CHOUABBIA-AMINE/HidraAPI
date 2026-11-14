/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : IncidentEscalationSourceAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-14
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.infrastructure.integration
 * @Description : Select exact owner-authorized Alarm or suspected Leak provenance.
 */
package dz.sh.hidra.modules.incident.infrastructure.integration;

import dz.sh.hidra.modules.incident.application.port.out.IncidentEscalationSourcePort;
import dz.sh.hidra.modules.incident.domain.value.IncidentEscalationSourceType;
import dz.sh.hidra.modules.alarm.application.contract.incident.IncidentAlarmEvidenceContract;
import dz.sh.hidra.modules.leakdetection.application.contract.incident.IncidentLeakCandidateEvidenceContract;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
public class IncidentEscalationSourceAdapter implements IncidentEscalationSourcePort {
    private final IncidentAlarmEvidenceContract alarms;
    private final IncidentLeakCandidateEvidenceContract candidates;
    public IncidentEscalationSourceAdapter(IncidentAlarmEvidenceContract alarms,
                                         IncidentLeakCandidateEvidenceContract candidates) {
        this.alarms=Objects.requireNonNull(alarms);
        this.candidates=Objects.requireNonNull(candidates);
    }
    @Override
    public Snapshot authorized(IncidentEscalationSourceType type, String id) {
        if (type==IncidentEscalationSourceType.ALARM) {
            var x=alarms.authorized(id);
            return new Snapshot(type,x.alarmId(),x.evaluationId(),x.companyId(),
                    x.assetType(),x.assetId(),x.evaluatedAt(),x.state());
        }
        if (type==IncidentEscalationSourceType.LEAK_CANDIDATE) {
            var x=candidates.authorized(id);
            return new Snapshot(type,x.candidateId(),x.evaluationId(),x.companyId(),
                    x.assetType(),x.assetId(),x.evaluatedAt(),x.state());
        }
        throw new IllegalArgumentException("Unsupported Incident source type.");
    }
}
