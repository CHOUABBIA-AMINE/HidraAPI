/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowRestMapperTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Test
 * @Layer       : Test
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.api.rest
 *
 * @Description : Tests workflow REST mapper request/query/response conversions.
 *
 */
package dz.sh.hidra.modules.workflow.api.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import dz.sh.hidra.kernel.application.pagination.PageResult;
import dz.sh.hidra.modules.workflow.api.rest.mapper.WorkflowRestMapper;
import dz.sh.hidra.modules.workflow.api.rest.response.WorkflowDefinitionResponse;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowDefinitionDto;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowDefinitionStatus;

/**
 * Tests workflow REST mapper request/query/response conversions.
 */
class WorkflowRestMapperTest {

    private final WorkflowRestMapper mapper = new WorkflowRestMapper();

    @Test
    void mapsCreateDefinitionRequestToApplicationCommand() {
        var command = mapper.toCommand(WorkflowRestApiTestFixtures.createDefinitionRequest());

        assertEquals("TELEMETRY_READING_VALIDATION", command.code().value());
        assertEquals("Validation télémétrie", command.name().nameFr());
        assertEquals("TELEMETRY_VALIDATION", command.type().code());
    }

    @Test
    void mapsListDefinitionQueryParametersToApplicationQueryWithSort() {
        var query = mapper.toListWorkflowDefinitionsQuery(
                "workflow-type-telemetry-validation",
                "TELEMETRY_VALIDATION",
                "active",
                "telemetry",
                2,
                50,
                "code",
                "desc");

        assertEquals("workflow-type-telemetry-validation", query.type().id());
        assertEquals(WorkflowDefinitionStatus.ACTIVE, query.status());
        assertEquals("telemetry", query.searchTerm());
        assertEquals(2, query.pageRequest().page());
        assertEquals(50, query.pageRequest().size());
        assertEquals("code", query.pageRequest().sortField());
    }

    @Test
    void mapsDefinitionDtoToResponseWithNestedCollections() {
        WorkflowDefinitionResponse response = mapper.toResponse(WorkflowRestApiTestFixtures.definitionDto());

        assertEquals("definition-001", response.id());
        assertEquals("ACTIVE", response.status());
        assertEquals("Validation télémétrie", response.name().nameFr());
        assertEquals(1, response.steps().size());
        assertEquals(1, response.transitions().size());
    }

    @Test
    void mapsPageResultToWorkflowPageResponse() {
        PageResult<WorkflowDefinitionDto> page = WorkflowRestApiTestFixtures.definitionPage();

        var response = mapper.toPageResponse(page, mapper::toResponse);

        assertEquals(1, response.items().size());
        assertEquals(0, response.page());
        assertEquals(20, response.size());
        assertEquals(1L, response.totalElements());
        assertNotNull(response.items().get(0));
    }
}
