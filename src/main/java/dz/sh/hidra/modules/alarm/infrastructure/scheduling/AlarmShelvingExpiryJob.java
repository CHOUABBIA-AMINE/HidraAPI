/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmShelvingExpiryJob
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.infrastructure.scheduling
 *
 * @Description : Enforces Alarm-owned transactional lifecycle evidence and integrity.
 *
 */
package dz.sh.hidra.modules.alarm.infrastructure.scheduling;

import dz.sh.hidra.modules.alarm.domain.value.AlarmId;
import java.time.Instant;
import java.util.Objects;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public final class AlarmShelvingExpiryJob {
    public static final String SYSTEM_ACTOR_ID="hidra-alarm-shelving-expiry";
    private final AlarmShelvingExpiryOrchestrator orchestrator;
    public AlarmShelvingExpiryJob(AlarmShelvingExpiryOrchestrator orchestrator) {this.orchestrator=Objects.requireNonNull(orchestrator);}
    @Scheduled(fixedDelayString="${hidra.alarm.shelving-expiry-ms:60000}")
    public void expireDueShelvings() {orchestrator.expireDue(Instant.now(),SYSTEM_ACTOR_ID,AlarmId.newId().value());}
}
