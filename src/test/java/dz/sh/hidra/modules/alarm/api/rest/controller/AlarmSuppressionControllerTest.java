/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmSuppressionControllerTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-02
 *
 * @Type        : Test
 * @Layer       : Test
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.api.rest.controller
 *
 * @Description : Verifies suppression REST commands derive actor identity server-side.
 *
 */
package dz.sh.hidra.modules.alarm.api.rest.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dz.sh.hidra.kernel.domain.value.ActorId;
import dz.sh.hidra.modules.alarm.application.command.CreateAlarmSuppressionCommand;
import dz.sh.hidra.modules.alarm.application.dto.AlarmSuppressionDto;
import dz.sh.hidra.modules.alarm.application.port.in.AlarmSuppressionQueryUseCase;
import dz.sh.hidra.modules.alarm.application.port.in.ManageAlarmSuppressionUseCase;
import dz.sh.hidra.modules.alarm.domain.value.AlarmSuppressionScopeType;
import dz.sh.hidra.modules.alarm.domain.value.AlarmSuppressionStatus;
import dz.sh.hidra.platform.security.CurrentActorResolver;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class AlarmSuppressionControllerTest {

    @Test
    void createDerivesActorFromSecurityContext() {
        ManageAlarmSuppressionUseCase manage = mock(ManageAlarmSuppressionUseCase.class);
        AlarmSuppressionQueryUseCase query = mock(AlarmSuppressionQueryUseCase.class);
        CurrentActorResolver actors = mock(CurrentActorResolver.class);
        when(actors.currentActorId()).thenReturn(ActorId.of("actor-authenticated"));
        when(manage.createSuppression(any())).thenReturn(dto());

        var controller = new AlarmSuppressionController(manage, query, actors);
        controller.create(new AlarmSuppressionController.CreateSuppressionRequest(
                AlarmSuppressionScopeType.ALARM,
                "alarm-1",
                "alarm-1",
                null,
                null,
                null,
                "reason-1",
                "maintenance",
                Instant.parse("2026-10-03T10:00:00Z"),
                null
        ), "corr-1");

        ArgumentCaptor<CreateAlarmSuppressionCommand> command =
                ArgumentCaptor.forClass(CreateAlarmSuppressionCommand.class);
        verify(manage).createSuppression(command.capture());
        assertThat(command.getValue().actorId()).isEqualTo("actor-authenticated");
        assertThat(command.getValue().correlationId()).isEqualTo("corr-1");
    }

    private static AlarmSuppressionDto dto() {
        return new AlarmSuppressionDto(
                "suppression-1", AlarmSuppressionScopeType.ALARM, "alarm-1", "alarm-1",
                null, null, null, "reason-1", "maintenance", "actor-authenticated",
                Instant.parse("2026-10-02T10:00:00Z"),
                Instant.parse("2026-10-03T10:00:00Z"),
                null, null, AlarmSuppressionStatus.ACTIVE, null, "corr-1"
        );
    }
}
