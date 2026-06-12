/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmLifecycleEventRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.application.port.out
 *
 * @Description : Repository port for AlarmLifecycleEvent.
 *
 */
package dz.sh.hidra.modules.alarm.application.port.out;

import dz.sh.hidra.modules.alarm.domain.model.AlarmLifecycleEvent;

import java.util.Optional;

/**
 * Repository port for AlarmLifecycleEvent.
 */
public interface AlarmLifecycleEventRepositoryPort {

    AlarmLifecycleEvent save(AlarmLifecycleEvent model);

    Optional<AlarmLifecycleEvent> findById(String id);
}
