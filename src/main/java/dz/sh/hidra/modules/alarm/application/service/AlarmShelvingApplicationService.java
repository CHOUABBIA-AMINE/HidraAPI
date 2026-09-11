/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmShelvingApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-11
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.application.service
 *
 * @Description : Implements audited, time-bounded alarm shelving with explicit unshelving.
 *
 */
package dz.sh.hidra.modules.alarm.application.service;

import dz.sh.hidra.modules.alarm.application.port.in.ManageAlarmShelvingUseCase;
import dz.sh.hidra.modules.alarm.application.port.out.AlarmRepositoryPort;
import dz.sh.hidra.modules.alarm.application.port.out.AlarmShelvingRepositoryPort;
import dz.sh.hidra.modules.alarm.domain.model.AlarmShelving;
import dz.sh.hidra.modules.alarm.domain.value.AlarmId;
import dz.sh.hidra.modules.alarm.domain.value.AlarmShelvingStatus;
import java.time.Instant;
import java.util.Objects;
import org.springframework.stereotype.Service;

@Service
public final class AlarmShelvingApplicationService implements ManageAlarmShelvingUseCase {

    private final AlarmRepositoryPort alarmRepositoryPort;
    private final AlarmShelvingRepositoryPort shelvingRepositoryPort;

    public AlarmShelvingApplicationService(
            AlarmRepositoryPort alarmRepositoryPort,
            AlarmShelvingRepositoryPort shelvingRepositoryPort
    ) {
        this.alarmRepositoryPort = Objects.requireNonNull(alarmRepositoryPort, "AlarmRepositoryPort must not be null.");
        this.shelvingRepositoryPort = Objects.requireNonNull(shelvingRepositoryPort, "AlarmShelvingRepositoryPort must not be null.");
    }

    @Override
    public String shelve(ShelveAlarmCommand command) {
        Objects.requireNonNull(command, "ShelveAlarmCommand must not be null.");
        alarmRepositoryPort.findById(command.alarmId())
                .orElseThrow(() -> new IllegalArgumentException("Unknown alarm: " + command.alarmId()));
        Instant now = Instant.now();
        if (command.shelvedUntil() == null || !command.shelvedUntil().isAfter(now)) {
            throw new IllegalArgumentException("shelvedUntil must be after the current instant.");
        }
        AlarmShelving shelving = new AlarmShelving(
                AlarmId.newId().value(), command.alarmId(), command.shelvingReasonId(), command.reasonText(),
                command.actorId(), now, command.shelvedUntil(), null, null, AlarmShelvingStatus.ACTIVE,
                command.correlationId()
        );
        return shelvingRepositoryPort.save(shelving).id();
    }

    @Override
    public String unshelve(UnshelveAlarmCommand command) {
        Objects.requireNonNull(command, "UnshelveAlarmCommand must not be null.");
        AlarmShelving existing = shelvingRepositoryPort.findById(command.shelvingId())
                .orElseThrow(() -> new IllegalArgumentException("Unknown alarm shelving: " + command.shelvingId()));
        if (!Objects.equals(existing.alarmId(), command.alarmId())) {
            throw new IllegalArgumentException("Alarm shelving does not belong to alarm: " + command.alarmId());
        }
        if (existing.status() != AlarmShelvingStatus.ACTIVE) {
            throw new IllegalStateException("Only ACTIVE shelving can be unshelved.");
        }
        AlarmShelving completed = new AlarmShelving(
                existing.id(), existing.alarmId(), existing.shelvingReasonId(), existing.reasonText(),
                existing.shelvedByActorId(), existing.shelvedAt(), existing.shelvedUntil(), Instant.now(),
                command.actorId(), AlarmShelvingStatus.COMPLETED,
                command.correlationId() == null ? existing.correlationId() : command.correlationId()
        );
        return shelvingRepositoryPort.save(completed).id();
    }
}
