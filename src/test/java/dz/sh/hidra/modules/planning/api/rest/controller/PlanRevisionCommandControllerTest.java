/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanRevisionCommandControllerTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Class
 * @Layer       : API Test
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.api.rest.controller
 *
 * @Description : Verifies the planning revision REST response is mapped from the application result contract.
 *
 */
package dz.sh.hidra.modules.planning.api.rest.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import dz.sh.hidra.modules.planning.application.port.in.UpdatePlanRevisionUseCase;
import java.time.Instant;
import org.junit.jupiter.api.Test;

class PlanRevisionCommandControllerTest {

    @Test
    void mapsApplicationRevisionResultWithoutDomainRepresentation() {
        Instant submittedAt = Instant.parse("2026-09-12T08:30:00Z");
        Instant createdAt = Instant.parse("2026-09-12T09:00:00Z");
        Instant updatedAt = Instant.parse("2026-09-28T17:00:00Z");

        UpdatePlanRevisionUseCase useCase = (revisionId, command) ->
                new UpdatePlanRevisionUseCase.Result(
                        revisionId,
                        "PLAN-1",
                        2,
                        "R02",
                        "DRAFT",
                        command.changeReasonCodeId(),
                        command.changeReasonText(),
                        "REV-1",
                        "ACTOR-1",
                        submittedAt,
                        null,
                        null,
                        "WF-1",
                        createdAt,
                        updatedAt
                );

        PlanRevisionCommandController controller = new PlanRevisionCommandController(useCase);
        PlanRevisionCommandController.Response response = controller.update(
                "REV-2",
                new PlanRevisionCommandController.Request(
                        Instant.parse("2026-09-12T10:00:00Z"),
                        "OPS_CHANGE",
                        "Updated throughput assumptions"
                )
        );

        assertEquals("REV-2", response.id());
        assertEquals("PLAN-1", response.planId());
        assertEquals("DRAFT", response.status());
        assertEquals("OPS_CHANGE", response.changeReasonCodeId());
        assertEquals("Updated throughput assumptions", response.changeReasonText());
        assertEquals(updatedAt, response.updatedAt());
    }
}
