/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmShelvingExpiryOrchestrator
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

import dz.sh.hidra.modules.alarm.application.port.out.AlarmShelvingRepositoryPort;
import dz.sh.hidra.modules.alarm.infrastructure.persistence.repository.AlarmShelvingJpaRepository;
import java.time.Instant;
import java.util.Objects;
import org.springframework.stereotype.Service;

@Service
public class AlarmShelvingExpiryOrchestrator {
    private static final System.Logger LOGGER=System.getLogger(AlarmShelvingExpiryOrchestrator.class.getName());
    private final AlarmShelvingJpaRepository repository;
    private final AlarmShelvingRepositoryPort shelving;
    public AlarmShelvingExpiryOrchestrator(AlarmShelvingJpaRepository repository,AlarmShelvingRepositoryPort shelving) {
        this.repository=Objects.requireNonNull(repository);this.shelving=Objects.requireNonNull(shelving);
    }
    public int expireDue(Instant asOf,String systemActorId,String correlationId) {
        Objects.requireNonNull(asOf);int count=0;
        // Each call crosses the transactional outgoing adapter proxy: no self-invocation transaction gap.
        for(String id:repository.findDueIds(asOf)) {
            try {
                if(shelving.expireIfDue(id,asOf,systemActorId,correlationId)) count++;
            } catch(RuntimeException failure) {
                // The individual adapter transaction has rolled back; other due rows can progress.
                LOGGER.log(System.Logger.Level.WARNING,"Shelving expiry failed for " + id,failure);
            }
        }
        return count;
    }
}
