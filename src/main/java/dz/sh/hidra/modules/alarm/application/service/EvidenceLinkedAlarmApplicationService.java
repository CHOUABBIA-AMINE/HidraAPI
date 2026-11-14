/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EvidenceLinkedAlarmApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-14
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.application.service
 *
 * @Description : Atomically raises and reads scoped evaluation-linked alarms.
 *
 */
package dz.sh.hidra.modules.alarm.application.service;

import dz.sh.hidra.modules.alarm.application.command.RaiseAlarmCommand;
import dz.sh.hidra.modules.alarm.application.port.in.EvidenceLinkedAlarmUseCase;
import dz.sh.hidra.modules.alarm.application.port.in.RaiseAlarmUseCase;
import dz.sh.hidra.modules.alarm.application.port.out.AlarmEvaluationTriggerPort;
import dz.sh.hidra.modules.alarm.application.port.out.AlarmEvidenceLinkPort;
import dz.sh.hidra.modules.alarm.application.port.out.AlarmLifecycleActorPort;
import dz.sh.hidra.modules.alarm.application.port.out.AlarmRepositoryPort;
import dz.sh.hidra.modules.alarm.application.port.out.AlarmResourceAuthorizationPort;
import dz.sh.hidra.modules.alarm.application.port.out.MonitoringEvaluationEvidencePort;
import dz.sh.hidra.modules.alarm.application.port.out.MonitoringEvaluationEvidencePort.Snapshot;
import dz.sh.hidra.modules.alarm.domain.exception.AlarmEvaluationTriggerConflictException;
import dz.sh.hidra.modules.alarm.domain.exception.AlarmResourceNotFoundException;
import dz.sh.hidra.modules.alarm.domain.model.Alarm;
import dz.sh.hidra.modules.alarm.domain.model.AlarmEvaluationTrigger;
import dz.sh.hidra.modules.alarm.domain.value.AlarmSourceType;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EvidenceLinkedAlarmApplicationService implements EvidenceLinkedAlarmUseCase {
    private final MonitoringEvaluationEvidencePort monitoring;
    private final AlarmResourceAuthorizationPort scopes;
    private final AlarmEvaluationTriggerPort triggers;
    private final AlarmEvidenceLinkPort links;
    private final RaiseAlarmUseCase raising;
    private final AlarmRepositoryPort alarms;
    private final AlarmLifecycleActorPort actors;

    public EvidenceLinkedAlarmApplicationService(MonitoringEvaluationEvidencePort monitoring,
            AlarmResourceAuthorizationPort scopes, AlarmEvaluationTriggerPort triggers,
            AlarmEvidenceLinkPort links, RaiseAlarmUseCase raising, AlarmRepositoryPort alarms,
            AlarmLifecycleActorPort actors) {
        this.monitoring = Objects.requireNonNull(monitoring);
        this.scopes = Objects.requireNonNull(scopes);
        this.triggers = Objects.requireNonNull(triggers);
        this.links = Objects.requireNonNull(links);
        this.raising = Objects.requireNonNull(raising);
        this.alarms = Objects.requireNonNull(alarms);
        this.actors = Objects.requireNonNull(actors);
    }

    @Override
    @Transactional
    public Result raise(Command command) {
        Objects.requireNonNull(command, "Alarm command must not be null.");
        String evaluationId = required(command.evaluationId(), 80);
        String alarmType = required(command.alarmTypeId(), 80);
        String severity = required(command.severityId(), 80);
        String title = required(command.titleFr(), 255);
        // Owner READ must be evaluated before the protected source becomes visible.
        Snapshot source = monitoring.authorized(evaluationId);
        scopes.requireAsset("alarm:alarms:execute", source.assetType(), source.assetId());
        requireQualifying(source);
        // The database lock must precede the first trigger mapping read.
        triggers.serialize(evaluationId);
        var prior = triggers.byEvaluation(evaluationId);
        if (prior.isPresent()) {
            AlarmEvaluationTrigger trigger = prior.orElseThrow();
            requireSameSource(trigger, source);
            if (!trigger.sameCommand(alarmType, severity, title)) {
                throw new AlarmEvaluationTriggerConflictException();
            }
            if (!links.hasEvaluation(trigger.alarmId(), evaluationId)) {
                throw new IllegalStateException("Missing immutable Alarm evaluation evidence.");
            }
            return result(alarm(trigger.alarmId()), source, false);
        }

        String actor = actors.currentActor().id();
        String correlation = UUID.randomUUID().toString();
        var created = raising.raiseAlarm(new RaiseAlarmCommand(
                "T12-" + UUID.randomUUID(), alarmType, severity, null,
                null, title, null, null, null, null,
                AlarmSourceType.MANUAL, null, null, evaluationId, null, null,
                source.assetType(), source.assetId(), source.assetId(), null,
                source.evaluatedAt(), null, null, null, null, correlation));
        Alarm alarm = alarm(created.id());
        var mapping = new AlarmEvaluationTrigger(evaluationId, alarm.id(), source.ruleId(),
                source.ruleRevision(), source.companyPartyId(), source.assetType(), source.assetId(),
                source.unitId(), source.targetId(), source.approvalId(), source.trustedReadingId(),
                source.evaluatedAt(), alarmType, severity, title, actor, Instant.now());
        triggers.save(mapping);
        links.linkEvaluation(alarm.id(), evaluationId);
        return result(alarm, source, true);
    }

    @Override
    // Alarm READ authorization uses a PostgreSQL SELECT FOR SHARE identity fence;
    // the outer transaction must allow that lock even though this use case only returns data.
    @Transactional
    public Result evidence(String alarmId) {
        // The Alarm owner authorizes before even revealing whether it has an evidence mapping.
        scopes.requireAlarm("alarm:alarms:read", alarmId);
        AlarmEvaluationTrigger trigger = triggers.byAlarm(alarmId)
                .orElseThrow(AlarmResourceNotFoundException::new);
        Snapshot source = monitoring.authorized(trigger.evaluationId());
        requireSameSource(trigger, source);
        if (!links.hasEvaluation(alarmId, trigger.evaluationId())) {
            throw new IllegalStateException("Missing immutable Alarm evaluation evidence.");
        }
        return result(alarm(alarmId), source, false);
    }

    private static void requireSameSource(AlarmEvaluationTrigger trigger, Snapshot source) {
        if (!trigger.sameSource(source.ruleId(), source.ruleRevision(), source.companyPartyId(),
                source.assetType(), source.assetId(), source.unitId(), source.targetId(),
                source.approvalId(), source.trustedReadingId(), source.evaluatedAt())) {
            throw new AlarmResourceNotFoundException();
        }
    }

    private static void requireQualifying(Snapshot source) {
        if (!"COMPUTED".equals(source.outcome()) || source.difference() == null
                || source.difference().signum() == 0 || source.targetId() == null
                || source.approvalId() == null || source.trustedReadingId() == null) {
            throw new IllegalArgumentException("The evaluation does not qualify for manual Alarm creation.");
        }
    }

    private Alarm alarm(String id) {
        return alarms.findById(id).orElseThrow(AlarmResourceNotFoundException::new);
    }

    private static Result result(Alarm alarm, Snapshot source, boolean created) {
        return new Result(alarm.id(), source.evaluationId(), source.ruleId(), source.ruleRevision(),
                alarm.currentState().name(), source.unitId(), source.difference(),
                source.targetId(), source.approvalId(), source.trustedReadingId(),
                source.evaluatedAt(), created);
    }

    private static String required(String text, int max) {
        if (text == null || text.isBlank() || !text.equals(text.strip()) || text.length() > max) {
            throw new IllegalArgumentException("Invalid evaluation-linked Alarm request.");
        }
        return text;
    }
}
