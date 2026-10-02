/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmSuppressionRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-02
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.application.port.out
 *
 * @Description : Persistence boundary for governed alarm suppression evidence.
 *
 */
package dz.sh.hidra.modules.alarm.application.port.out;

import dz.sh.hidra.modules.alarm.application.dto.AlarmSuppressionDto;
import dz.sh.hidra.modules.alarm.application.dto.AlarmSuppressionPageDto;
import dz.sh.hidra.modules.alarm.application.query.AlarmSuppressionQuery;
import dz.sh.hidra.modules.alarm.domain.value.AlarmSuppressionScopeType;
import dz.sh.hidra.modules.alarm.domain.value.AlarmSuppressionStatus;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Application-owned suppression persistence contract.
 */
public interface AlarmSuppressionRepositoryPort {

    AlarmSuppressionDto save(AlarmSuppressionDto suppression);

    Optional<AlarmSuppressionDto> findById(String id);

    boolean existsByExactScopeAndStatus(
            AlarmSuppressionScopeType scopeType,
            String scopeReferenceId,
            AlarmSuppressionStatus status
    );

    List<AlarmSuppressionDto> findDueForExpiry(Instant asOf);

    AlarmSuppressionPageDto find(AlarmSuppressionQuery query);
}
