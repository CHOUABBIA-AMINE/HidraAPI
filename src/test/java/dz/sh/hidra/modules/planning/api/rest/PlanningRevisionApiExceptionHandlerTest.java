/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningRevisionApiExceptionHandlerTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-12
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.api.rest
 *
 * @Description : Verifies deterministic HTTP conflict semantics for stale planning revisions.
 *
 */
package dz.sh.hidra.modules.planning.api.rest;

import dz.sh.hidra.modules.planning.domain.exception.PlanningRevisionConflictException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlanningRevisionApiExceptionHandlerTest {

    @Test
    void mapsRevisionConflictToStable409ProblemCode() {
        PlanningRevisionApiExceptionHandler handler = new PlanningRevisionApiExceptionHandler();
        ProblemDetail detail = handler.conflict(new PlanningRevisionConflictException(
                "Plan revision changed after it was loaded. Refetch the revision before retrying."
        ));

        assertEquals(HttpStatus.CONFLICT.value(), detail.getStatus());
        assertEquals("PLANNING_REVISION_CONFLICT", detail.getTitle());
        assertEquals("PLANNING_REVISION_CONFLICT", detail.getProperties().get("code"));
    }
}
