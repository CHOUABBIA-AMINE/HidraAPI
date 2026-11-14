/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : IncidentAlarmEvidenceQueryService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-14
 * @Type        : Class
 * @Layer       : Application
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.application.service
 * @Description : Incident-targeted owner READ of qualified T12 Alarm provenance.
 */
package dz.sh.hidra.modules.alarm.application.service;

import dz.sh.hidra.modules.alarm.application.contract.incident.IncidentAlarmEvidenceContract;
import dz.sh.hidra.modules.alarm.application.port.in.EvidenceLinkedAlarmUseCase;
import dz.sh.hidra.modules.alarm.application.port.out.AlarmRepositoryPort;
import dz.sh.hidra.modules.alarm.application.port.out.AlarmEvaluationTriggerPort;
import dz.sh.hidra.modules.alarm.domain.exception.AlarmResourceNotFoundException;
import dz.sh.hidra.modules.topology.application.contract.alarm.AlarmTopologyOwnershipContract;
import java.util.NoSuchElementException;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class IncidentAlarmEvidenceQueryService implements IncidentAlarmEvidenceContract {
    private final EvidenceLinkedAlarmUseCase evidence;
    private final AlarmRepositoryPort alarms;
    private final AlarmEvaluationTriggerPort triggers;
    private final AlarmTopologyOwnershipContract owners;
    public IncidentAlarmEvidenceQueryService(EvidenceLinkedAlarmUseCase evidence,
            AlarmRepositoryPort alarms, AlarmEvaluationTriggerPort triggers,
            AlarmTopologyOwnershipContract owners) {
        this.evidence=Objects.requireNonNull(evidence);
        this.alarms=Objects.requireNonNull(alarms);
        this.triggers=Objects.requireNonNull(triggers);
        this.owners=Objects.requireNonNull(owners);
    }
    @Override @Transactional
    public Evidence authorized(String alarmId) {
        // No direct lookup before the source owner checks both Alarm and Monitoring READ.
        EvidenceLinkedAlarmUseCase.Result r;
        try {
            r=evidence.evidence(alarmId);
        } catch (AlarmResourceNotFoundException unavailable) {
            // Keep Alarm's internal failure type within Alarm. Incident receives
            // a non-disclosing source-owner absence for SQL count masking.
            throw new org.springframework.security.access.AccessDeniedException("Protected Alarm origin unavailable.");
        }
        var mapping=triggers.byAlarm(r.alarmId()).orElseThrow(NoSuchElementException::new);
        var a=alarms.findById(r.alarmId()).orElseThrow(NoSuchElementException::new);
        var o=owners.currentOwner(a.topologyAssetTypeCode(),a.topologyAssetId())
                .orElseThrow(NoSuchElementException::new);
        if(!mapping.evaluationId().equals(r.evaluationId())
                || !mapping.companyPartyId().equals(o.companyPartyId())
                || !mapping.assetType().equals(o.typeCode())
                || !mapping.assetId().equals(o.assetId()))
            throw new NoSuchElementException("Protected Alarm origin changed.");
        return new Evidence(a.id(),r.evaluationId(),mapping.companyPartyId(),
                o.typeCode(),o.assetId(),r.evaluatedAt(),r.currentState());
    }
}
