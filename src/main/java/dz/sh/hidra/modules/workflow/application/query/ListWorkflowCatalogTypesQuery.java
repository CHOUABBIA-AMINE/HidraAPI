/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ListWorkflowCatalogTypesQuery
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.query
 *
 * @Description : Query to list workflow catalog entries.
 *
 */
package dz.sh.hidra.modules.workflow.application.query;

import dz.sh.hidra.kernel.application.pagination.PageRequest;
import dz.sh.hidra.kernel.application.query.Query;
import java.util.Objects;

/**
 * Query to list workflow catalog entries.
 *
 * <p>Architecture role:
 * Application-layer query contract for the workflow module. It carries validated
 * workflow domain value objects and catalog references only. It must not depend on persistence,
 * REST, Spring, JPA, telemetry implementation classes, topology implementation classes, planning,
 * monitoring, incidents, audit implementation, integration, analytics, reporting, or notification.
 */
public record ListWorkflowCatalogTypesQuery(
        String catalogName,
        Boolean active,
        String locale,
        PageRequest pageRequest) implements Query {

    public ListWorkflowCatalogTypesQuery {
        catalogName = Objects.requireNonNull(catalogName, "ListWorkflowCatalogTypesQuery catalogName must not be null.");
        pageRequest = Objects.requireNonNull(pageRequest, "ListWorkflowCatalogTypesQuery pageRequest must not be null.");
    }
}
