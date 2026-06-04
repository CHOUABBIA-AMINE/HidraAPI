/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PositionControllerTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.api.rest.controller
 *
 * @Description : Unit tests for PositionController API behavior.
 *
 */
package dz.sh.hidra.modules.organization.api.rest.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Instant;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import dz.sh.hidra.modules.organization.api.rest.mapper.OrganizationRestMapper;
import dz.sh.hidra.modules.organization.api.rest.request.CreatePositionRequest;
import dz.sh.hidra.modules.organization.api.rest.response.PositionResponse;
import dz.sh.hidra.modules.organization.application.command.CreatePositionCommand;
import dz.sh.hidra.modules.organization.application.dto.PositionDto;
import dz.sh.hidra.modules.organization.application.port.in.CreatePositionUseCase;

/**
 * Tests the position REST controller.
 *
 * <p>Business role:
 * Verifies API behavior for operational positions and confirms that positions are not identity
 * roles.
 *
 * <p>Architecture role:
 * This API-layer unit test uses a fake application inbound port and does not access repositories,
 * persistence, identity, topology, platform, or Spring Boot test context.
 */
class PositionControllerTest {

    @Test
    void shouldCreatePositionThroughApplicationPort() {
        PositionController controller = controller();

        ResponseEntity<PositionResponse> response = controller.createPosition(new CreatePositionRequest(
                "STATION_TEAM_LEADER",
                "Station Team Leader",
                "Leads station team activities"));

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("STATION_TEAM_LEADER", response.getBody().code());
        assertEquals("Station Team Leader", response.getBody().title());
        assertEquals(true, response.getBody().active());
    }

    @Test
    void shouldExposeListPositionsAsNotImplementedPlaceholder() {
        PositionController controller = controller();

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> controller.listPositions("leader", true, 0, 20));

        assertEquals(HttpStatus.NOT_IMPLEMENTED, exception.getStatusCode());
    }

    private static PositionController controller() {
        return new PositionController(new FakeCreatePositionUseCase(), new OrganizationRestMapper());
    }

    private static final class FakeCreatePositionUseCase implements CreatePositionUseCase {

        @Override
        public PositionDto createPosition(CreatePositionCommand command) {
            Instant now = Instant.parse("2026-06-01T10:15:30Z");
            return new PositionDto(
                    "pos_010",
                    command.code().value(),
                    command.title().value(),
                    command.description(),
                    true,
                    now,
                    now);
        }
    }
}
