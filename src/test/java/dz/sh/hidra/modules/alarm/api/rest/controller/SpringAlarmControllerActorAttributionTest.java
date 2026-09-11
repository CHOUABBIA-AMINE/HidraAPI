/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SpringAlarmControllerActorAttributionTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-11
 *
 * @Type        : Test
 * @Layer       : API
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.api.rest.controller
 *
 * @Description : Verifies server-derived actor attribution for alarm acknowledgement and closure.
 *
 */
package dz.sh.hidra.modules.alarm.api.rest.controller;

import dz.sh.hidra.kernel.domain.value.ActorId;
import dz.sh.hidra.modules.alarm.api.rest.request.AcknowledgeAlarmRequest;
import dz.sh.hidra.modules.alarm.api.rest.request.CloseAlarmRequest;
import dz.sh.hidra.modules.alarm.application.command.AcknowledgeAlarmCommand;
import dz.sh.hidra.modules.alarm.application.command.CloseAlarmCommand;
import dz.sh.hidra.modules.alarm.application.port.in.AcknowledgeAlarmUseCase;
import dz.sh.hidra.modules.alarm.application.port.in.CloseAlarmUseCase;
import dz.sh.hidra.modules.alarm.application.port.in.RaiseAlarmUseCase;
import dz.sh.hidra.modules.alarm.domain.value.AlarmClosureType;
import dz.sh.hidra.platform.security.AuthenticatedPrincipal;
import dz.sh.hidra.platform.security.CurrentActorResolver;
import dz.sh.hidra.platform.security.CurrentSecurityContext;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class SpringAlarmControllerActorAttributionTest {

    @Test
    void acknowledgementUsesAuthenticatedActorAndPrincipalName() {
        AtomicReference<AcknowledgeAlarmCommand> captured = new AtomicReference<>();
        AcknowledgeAlarmUseCase acknowledge = command -> {
            captured.set(command);
            return "ack-1";
        };
        SpringAlarmController controller = controller(acknowledge, command -> "close-1");

        controller.acknowledgeAlarm(new AcknowledgeAlarmRequest(
                "alarm-1",
                "org-1",
                "ORG-1",
                "checked",
                "corr-1"
        ));

        assertEquals("actor-authenticated", captured.get().acknowledgedByActorId());
        assertEquals("operator.user", captured.get().acknowledgedByDisplayName());
        assertEquals("org-1", captured.get().organizationUnitId());
        assertEquals("ORG-1", captured.get().organizationUnitCode());
    }

    @Test
    void closureUsesAuthenticatedActor() {
        AtomicReference<CloseAlarmCommand> captured = new AtomicReference<>();
        CloseAlarmUseCase close = command -> {
            captured.set(command);
            return "close-1";
        };
        SpringAlarmController controller = controller(command -> "ack-1", close);

        controller.closeAlarm(new CloseAlarmRequest(
                "alarm-1",
                AlarmClosureType.NORMALIZED,
                "reason-1",
                "normalized",
                false,
                null,
                "corr-1"
        ));

        assertEquals("actor-authenticated", captured.get().closedByActorId());
        assertNull(captured.get().reviewWorkflowInstanceId());
    }

    private static SpringAlarmController controller(
            AcknowledgeAlarmUseCase acknowledge,
            CloseAlarmUseCase close
    ) {
        RaiseAlarmUseCase raise = command -> null;
        CurrentSecurityContext securityContext = new CurrentSecurityContext() {
            @Override
            public Optional<AuthenticatedPrincipal> currentPrincipal() {
                return Optional.of(new AuthenticatedPrincipal(
                        ActorId.of("actor-authenticated"),
                        "operator.user",
                        true
                ));
            }

            @Override
            public void clear() {
                // No mutable security state in this test context.
            }
        };
        return new SpringAlarmController(
                acknowledge,
                close,
                raise,
                new CurrentActorResolver(securityContext)
        );
    }
}
