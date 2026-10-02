/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmRestMapperTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Test
 * @Layer       : API Test
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.api.rest.mapper
 *
 * @Description : Verifies generated exact alarm boundary mappings preserve every component.
 *
 */
package dz.sh.hidra.modules.alarm.api.rest.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import dz.sh.hidra.modules.alarm.api.rest.request.RaiseAlarmRequest;
import dz.sh.hidra.modules.alarm.api.rest.response.AlarmResponse;
import dz.sh.hidra.modules.alarm.application.command.RaiseAlarmCommand;
import dz.sh.hidra.modules.alarm.application.dto.AlarmSummaryDto;
import dz.sh.hidra.modules.alarm.domain.value.AlarmSourceType;
import dz.sh.hidra.modules.alarm.domain.value.AlarmState;
import java.time.Instant;
import org.junit.jupiter.api.Test;

class AlarmRestMapperTest {

    @Test
    void generatedRaiseAlarmMappingPreservesExactContract() {
        Instant firstDetectedAt = Instant.parse("2026-09-28T10:15:30Z");
        RaiseAlarmRequest request = new RaiseAlarmRequest(
                "AL-001",
                "type-1",
                "severity-1",
                "priority-1",
                "إنذار",
                "Alarme",
                "Alarm",
                "وصف",
                "Description FR",
                "Description EN",
                AlarmSourceType.MANUAL,
                "source-1",
                "candidate-1",
                "evaluation-1",
                "reading-1",
                "plan-target-1",
                "PIPELINE",
                "asset-1",
                "PL-001",
                "Pipeline 1",
                firstDetectedAt,
                "org-1",
                "ORG-1",
                "TRC",
                "workflow-1",
                "corr-1"
        );

        RaiseAlarmCommand expected = new RaiseAlarmCommand(
                "AL-001",
                "type-1",
                "severity-1",
                "priority-1",
                "إنذار",
                "Alarme",
                "Alarm",
                "وصف",
                "Description FR",
                "Description EN",
                AlarmSourceType.MANUAL,
                "source-1",
                "candidate-1",
                "evaluation-1",
                "reading-1",
                "plan-target-1",
                "PIPELINE",
                "asset-1",
                "PL-001",
                "Pipeline 1",
                firstDetectedAt,
                "org-1",
                "ORG-1",
                "TRC",
                "workflow-1",
                "corr-1"
        );

        assertEquals(expected, AlarmRestMapper.toCommand(request));
    }

    @Test
    void generatedAlarmResponseMappingPreservesExactContract() {
        Instant raisedAt = Instant.parse("2026-09-28T10:15:30Z");
        Instant acknowledgedAt = Instant.parse("2026-09-28T10:16:30Z");
        Instant closedAt = Instant.parse("2026-09-28T10:20:30Z");

        AlarmSummaryDto dto = new AlarmSummaryDto(
                "alarm-1",
                "AL-001",
                "type-1",
                "severity-1",
                "Alarme",
                AlarmSourceType.MANUAL,
                "PIPELINE",
                "asset-1",
                "PL-001",
                AlarmState.CLOSED,
                raisedAt,
                acknowledgedAt,
                closedAt
        );

        AlarmResponse expected = new AlarmResponse(
                "alarm-1",
                "AL-001",
                "type-1",
                "severity-1",
                "Alarme",
                AlarmSourceType.MANUAL,
                "PIPELINE",
                "asset-1",
                "PL-001",
                AlarmState.CLOSED,
                raisedAt,
                acknowledgedAt,
                closedAt
        );

        assertEquals(expected, AlarmRestMapper.toResponse(dto));
    }
}
