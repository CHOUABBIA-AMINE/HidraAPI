/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmEscalationRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.application.port.out
 *
 * @Description : Repository port for AlarmEscalation.
 *
 */
package dz.sh.hidra.modules.alarm.application.port.out;

import dz.sh.hidra.modules.alarm.domain.model.AlarmEscalation;

import java.util.Optional;

/**
 * Repository port for AlarmEscalation.
 */
public interface AlarmEscalationRepositoryPort {

    AlarmEscalation save(AlarmEscalation model);

    Optional<AlarmEscalation> findById(String id);
}
