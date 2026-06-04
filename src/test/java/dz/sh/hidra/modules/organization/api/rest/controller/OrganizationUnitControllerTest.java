/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationUnitControllerTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.api.rest.controller
 *
 * @Description : Unit tests for OrganizationUnitController API behavior.
 *
 */
package dz.sh.hidra.modules.organization.api.rest.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import dz.sh.hidra.kernel.application.pagination.PageResult;
import dz.sh.hidra.modules.organization.api.rest.mapper.OrganizationRestMapper;
import dz.sh.hidra.modules.organization.api.rest.request.CreateOrganizationUnitRequest;
import dz.sh.hidra.modules.organization.api.rest.request.UpdateOrganizationUnitRequest;
import dz.sh.hidra.modules.organization.api.rest.response.OrganizationUnitResponse;
import dz.sh.hidra.modules.organization.application.command.CreateOrganizationUnitCommand;
import dz.sh.hidra.modules.organization.application.dto.OrganizationUnitDto;
import dz.sh.hidra.modules.organization.application.port.in.CreateOrganizationUnitUseCase;
import dz.sh.hidra.modules.organization.application.port.in.GetOrganizationUnitUseCase;
import dz.sh.hidra.modules.organization.application.port.in.ListOrganizationUnitsUseCase;
import dz.sh.hidra.modules.organization.application.query.GetOrganizationUnitByIdQuery;
import dz.sh.hidra.modules.organization.application.query.ListOrganizationUnitsQuery;

/**
 * Tests the organization unit REST controller.
 *
 * <p>Business role:
 * Verifies API behavior for organization units, including station-as-organization-unit response
 * fields and neutral operational scope values.
 *
 * <p>Architecture role:
 * This API-layer unit test uses fake application inbound ports and does not access persistence,
 * identity, topology, platform, or Spring Boot test context.
 */
class OrganizationUnitControllerTest {

    @Test
    void shouldCreateStationOrganizationUnitThroughApplicationPort() {
        OrganizationUnitController controller = controller();

        ResponseEntity<OrganizationUnitResponse> response = controller.createOrganizationUnit(
                new CreateOrganizationUnitRequest(
                        "CS_EAST_10",
                        "Compression Station East 10",
                        "STATION",
                        null,
                        "TOPOLOGY_COMPRESSION_STATION",
                        "station-010",
                        "CS-EAST-10",
                        "Compression Station East 10"));

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("CS_EAST_10", response.getBody().code());
        assertEquals("STATION", response.getBody().type());
        assertEquals("TOPOLOGY_COMPRESSION_STATION", response.getBody().operationalScopeType());
    }

    @Test
    void shouldReturnOrganizationUnitWhenFound() {
        OrganizationUnitController controller = controller();

        ResponseEntity<OrganizationUnitResponse> response = controller.getOrganizationUnit("ou_010");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("ou_010", response.getBody().organizationUnitId());
    }

    @Test
    void shouldReturnNotFoundWhenOrganizationUnitDoesNotExist() {
        OrganizationUnitController controller = controller();

        ResponseEntity<OrganizationUnitResponse> response = controller.getOrganizationUnit("missing");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void shouldListOrganizationUnitsThroughApplicationPort() {
        OrganizationUnitController controller = controller();

        PageResult<OrganizationUnitResponse> response = controller.listOrganizationUnits(
                "station",
                "STATION",
                "ACTIVE",
                null,
                0,
                20);

        assertEquals(1, response.items().size());
        assertEquals("CS_EAST_10", response.items().get(0).code());
        assertEquals(1, response.totalElements());
    }

    @Test
    void shouldExposeUpdateOrganizationUnitAsNotImplementedPlaceholder() {
        OrganizationUnitController controller = controller();

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> controller.updateOrganizationUnit(
                        "ou_010",
                        new UpdateOrganizationUnitRequest(
                                "Updated Station",
                                null,
                                null,
                                null,
                                null,
                                null)));

        assertEquals(HttpStatus.NOT_IMPLEMENTED, exception.getStatusCode());
    }

    private static OrganizationUnitController controller() {
        OrganizationRestMapper mapper = new OrganizationRestMapper();
        return new OrganizationUnitController(
                new FakeCreateOrganizationUnitUseCase(),
                new FakeGetOrganizationUnitUseCase(),
                new FakeListOrganizationUnitsUseCase(),
                mapper);
    }

    private static OrganizationUnitDto unitDto(String organizationUnitId) {
        Instant now = Instant.parse("2026-06-01T10:15:30Z");
        return new OrganizationUnitDto(
                organizationUnitId,
                "CS_EAST_10",
                "Compression Station East 10",
                "ACTIVE",
                "STATION",
                null,
                "TOPOLOGY_COMPRESSION_STATION",
                "station-010",
                "CS-EAST-10",
                "Compression Station East 10",
                now,
                now);
    }

    private static final class FakeCreateOrganizationUnitUseCase implements CreateOrganizationUnitUseCase {

        @Override
        public OrganizationUnitDto createOrganizationUnit(CreateOrganizationUnitCommand command) {
            return unitDto("ou_010");
        }
    }

    private static final class FakeGetOrganizationUnitUseCase implements GetOrganizationUnitUseCase {

        @Override
        public Optional<OrganizationUnitDto> getOrganizationUnit(GetOrganizationUnitByIdQuery query) {
            if ("missing".equals(query.organizationUnitId().value())) {
                return Optional.empty();
            }
            return Optional.of(unitDto(query.organizationUnitId().value()));
        }
    }

    private static final class FakeListOrganizationUnitsUseCase implements ListOrganizationUnitsUseCase {

        @Override
        public PageResult<OrganizationUnitDto> listOrganizationUnits(ListOrganizationUnitsQuery query) {
            return PageResult.of(List.of(unitDto("ou_010")), query.pageRequest().page(), query.pageRequest().size(), 1);
        }
    }
}
