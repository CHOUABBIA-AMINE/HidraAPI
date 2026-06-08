/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowCatalogControllerTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Test
 * @Layer       : Test
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.api.rest
 *
 * @Description : Tests workflow catalog REST controller delegation.
 *
 */
package dz.sh.hidra.modules.workflow.api.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import dz.sh.hidra.modules.workflow.api.rest.controller.WorkflowCatalogController;
import dz.sh.hidra.modules.workflow.api.rest.mapper.WorkflowRestMapper;
import dz.sh.hidra.modules.workflow.application.port.in.GetWorkflowCatalogTypeUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.ListWorkflowCatalogTypesUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.ResolveWorkflowCatalogTypeUseCase;

/**
 * Tests workflow catalog REST controller delegation.
 */
class WorkflowCatalogControllerTest {

    @Test
    void getResolveAndListCatalogEndpointsReturnMappedResponses() {
        WorkflowCatalogController controller = new WorkflowCatalogController(
                WorkflowRestApiTestFixtures.fixedUseCase(GetWorkflowCatalogTypeUseCase.class, WorkflowRestApiTestFixtures.catalogDto()),
                WorkflowRestApiTestFixtures.fixedUseCase(ListWorkflowCatalogTypesUseCase.class, WorkflowRestApiTestFixtures.catalogPage()),
                WorkflowRestApiTestFixtures.fixedUseCase(ResolveWorkflowCatalogTypeUseCase.class, WorkflowRestApiTestFixtures.catalogDto()),
                new WorkflowRestMapper());

        assertEquals("NORMAL", controller.getCatalogType("workflow-priority-normal").getBody().code());
        assertEquals("workflow-priority-normal", controller.resolveCatalogType("PRIORITY", "NORMAL", "fr").getBody().id());
        assertEquals(1, controller.listCatalogTypes("PRIORITY", true, "fr", 0, 20, "code", "ASC").getBody().items().size());
    }
}
