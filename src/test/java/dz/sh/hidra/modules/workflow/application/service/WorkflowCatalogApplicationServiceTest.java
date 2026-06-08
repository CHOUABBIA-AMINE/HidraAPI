/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowCatalogApplicationServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Test
 * @Layer       : Test
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.service
 *
 * @Description : Application service tests for workflow catalog lookups.
 *
 */
package dz.sh.hidra.modules.workflow.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.modules.workflow.application.query.GetWorkflowCatalogTypeQuery;
import dz.sh.hidra.modules.workflow.application.query.ListWorkflowCatalogTypesQuery;
import dz.sh.hidra.modules.workflow.application.query.ResolveWorkflowCatalogTypeQuery;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowTypeCatalog;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowCatalogId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowCode;

/**
 * Application service tests for workflow catalog lookups.
 */
class WorkflowCatalogApplicationServiceTest {

    @Test
    void getResolveAndListWorkflowCatalogTypesReturnDtoProjections() {
        WorkflowApplicationServiceTestFixtures.InMemoryCatalogRepository repository = new WorkflowApplicationServiceTestFixtures.InMemoryCatalogRepository();
        WorkflowTypeCatalog catalog = WorkflowApplicationServiceTestFixtures.catalog();
        repository.add(catalog);

        WorkflowCatalogApplicationService service = new WorkflowCatalogApplicationService(repository);

        var byId = service.getWorkflowCatalogType(new GetWorkflowCatalogTypeQuery(catalog.id()));
        var resolved = service.resolveWorkflowCatalogType(new ResolveWorkflowCatalogTypeQuery(
                "PRIORITY",
                WorkflowCode.of("NORMAL"),
                "fr"));
        var page = service.listWorkflowCatalogTypes(new ListWorkflowCatalogTypesQuery(
                "PRIORITY",
                true,
                "fr",
                WorkflowApplicationServiceTestFixtures.pageRequest()));

        assertEquals("NORMAL", byId.code());
        assertEquals(catalog.id().value(), resolved.id());
        assertEquals(1, page.items().size());
        assertEquals("Normale", byId.translations().get(0).name());
    }

    @Test
    void getWorkflowCatalogTypeThrowsWhenCatalogIsMissing() {
        WorkflowCatalogApplicationService service = new WorkflowCatalogApplicationService(
                new WorkflowApplicationServiceTestFixtures.InMemoryCatalogRepository());

        assertThrows(
                BusinessRuleViolationException.class,
                () -> service.getWorkflowCatalogType(new GetWorkflowCatalogTypeQuery(
                        WorkflowCatalogId.of("missing-catalog"))));
    }
}
