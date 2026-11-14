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
    private final dz.sh.hidra.modules.incident.application.port.out.IncidentEscalationAuthorizationPort scope;
    private final dz.sh.hidra.modules.incident.application.port.out.IncidentEscalationStorePort records;
    private final org.springframework.transaction.PlatformTransactionManager transactionManager;
    public IncidentEscalationSourceAdapter(IncidentAlarmEvidenceContract alarms,
                                         IncidentLeakCandidateEvidenceContract candidates,
                                         dz.sh.hidra.modules.incident.application.port.out.IncidentEscalationAuthorizationPort scope,
                                         dz.sh.hidra.modules.incident.application.port.out.IncidentEscalationStorePort records,
                                         org.springframework.transaction.PlatformTransactionManager transactionManager) {
        this.alarms=Objects.requireNonNull(alarms);
        this.candidates=Objects.requireNonNull(candidates);
        this.scope=Objects.requireNonNull(scope);
        this.records=Objects.requireNonNull(records);
        this.transactionManager=Objects.requireNonNull(transactionManager);
    }

    @Override
    public boolean readableCorrelation(
            dz.sh.hidra.modules.incident.application.port.out.IncidentEscalationStorePort.Correlation correlation) {
        Objects.requireNonNull(correlation);
        var transaction=new org.springframework.transaction.support.TransactionTemplate(transactionManager);
        transaction.setPropagationBehavior(org.springframework.transaction.TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        try {
            return Boolean.TRUE.equals(transaction.execute(status -> {
                scope.requireAsset("incident:incidents:read",correlation.companyId(),
                        correlation.assetType(),correlation.assetId());
                var history=records.timeline(correlation.incidentId());
                if(history.isEmpty())
                    throw new java.util.NoSuchElementException("No verified escalation origin.");
                for(var event:history) {
                    var source=authorized(event.sourceType(),event.sourceId());
                    if(!correlation.evaluationId().equals(source.evaluationId())
                            ||!correlation.companyId().equals(source.companyId())
                            ||!correlation.assetType().equals(source.assetType())
                            ||!correlation.assetId().equals(source.assetId()))
                        throw new java.util.NoSuchElementException("Source ownership does not match.");
                }
                return Boolean.TRUE;
            }));
        } catch (org.springframework.security.access.AccessDeniedException |
                 java.util.NoSuchElementException | IllegalArgumentException denied) {
            // A denied source rolls back only this nested transaction, never the
            // outer Incident list/count transaction. Do not swallow SQL failures.
            return false;
        }
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
