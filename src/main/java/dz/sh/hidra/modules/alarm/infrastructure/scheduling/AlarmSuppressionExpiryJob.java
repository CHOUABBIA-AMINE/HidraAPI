/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmSuppressionExpiryJob
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-02
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.infrastructure.scheduling
 *
 * @Description : Schedules backend-owned alarm suppression expiry evaluation.
 *
 */
package dz.sh.hidra.modules.alarm.infrastructure.scheduling;

import dz.sh.hidra.modules.alarm.domain.value.AlarmId;
import java.time.Instant;
import java.util.Objects;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Reuses the platform's existing Spring scheduling mechanism.
 */
@Component
public final class AlarmSuppressionExpiryJob {

    static final String SYSTEM_ACTOR_ID = "hidra-alarm-suppression-expiry";

    private final AlarmSuppressionExpiryOrchestrator orchestrator;

    public AlarmSuppressionExpiryJob(AlarmSuppressionExpiryOrchestrator orchestrator) {
        this.orchestrator = Objects.requireNonNull(
                orchestrator, "Alarm suppression expiry orchestrator must not be null."
        );
    }

    @Scheduled(fixedDelayString = "${hidra.alarm.suppression-expiry-ms:60000}")
    public void expireDueSuppressions() {
        orchestrator.expireDue(
                Instant.now(),
                SYSTEM_ACTOR_ID,
                AlarmId.newId().value()
        );
    }
}
