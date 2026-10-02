/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaAlarmSuppressionRepositoryAdapterTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-02
 *
 * @Type        : Test
 * @Layer       : Test
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.infrastructure.persistence.adapter
 *
 * @Description : Verifies suppression persistence mapping remains exact and lossless.
 *
 */
package dz.sh.hidra.modules.alarm.infrastructure.persistence.adapter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import dz.sh.hidra.modules.alarm.application.dto.AlarmSuppressionDto;
import dz.sh.hidra.modules.alarm.domain.value.AlarmSuppressionScopeType;
import dz.sh.hidra.modules.alarm.domain.value.AlarmSuppressionStatus;
import java.time.Instant;
import org.junit.jupiter.api.Test;

class JpaAlarmSuppressionRepositoryAdapterTest {

    @Test
    void persistenceMappingRoundTripsEverySuppressionField() {
        AlarmSuppressionDto expected = new AlarmSuppressionDto(
                "suppression-1",
                AlarmSuppressionScopeType.TOPOLOGY_ASSET,
                "asset-1",
                null,
                "type-1",
                "PIPELINE",
                "asset-1",
                "reason-1",
                "planned maintenance",
                "actor-1",
                Instant.parse("2026-10-02T08:00:00Z"),
                Instant.parse("2026-10-02T10:00:00Z"),
                null,
                null,
                AlarmSuppressionStatus.ACTIVE,
                null,
                "corr-1"
        );

        var entity = JpaAlarmSuppressionRepositoryAdapter.toEntity(expected);
        assertEquals(expected, JpaAlarmSuppressionRepositoryAdapter.toDto(entity));
    }
}
