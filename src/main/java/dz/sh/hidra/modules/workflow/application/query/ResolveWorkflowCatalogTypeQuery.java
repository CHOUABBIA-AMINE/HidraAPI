/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ResolveWorkflowCatalogTypeQuery
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.query
 *
 * @Description : Query to resolve a workflow catalog entry by catalog name and code.
 *
 */
package dz.sh.hidra.modules.workflow.application.query;

import dz.sh.hidra.kernel.application.query.Query;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowCode;
import java.util.Objects;

/**
 * Query to resolve a workflow catalog entry by catalog name and code.
 *
 * <p>Architecture role:
 * Application-layer query contract for the workflow module. It carries validated
 * workflow domain value objects and catalog references only. It must not depend on persistence,
 * REST, Spring, JPA, telemetry implementation classes, topology implementation classes, planning,
 * monitoring, incidents, audit implementation, integration, analytics, reporting, or notification.
 */
public record ResolveWorkflowCatalogTypeQuery(
        String catalogName,
        WorkflowCode code,
        String locale) implements Query {

    public ResolveWorkflowCatalogTypeQuery {
        catalogName = Objects.requireNonNull(catalogName, "ResolveWorkflowCatalogTypeQuery catalogName must not be null.");
        code = Objects.requireNonNull(code, "ResolveWorkflowCatalogTypeQuery code must not be null.");
    }
}
