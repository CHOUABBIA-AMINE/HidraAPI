/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowCatalogDto
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.dto
 *
 * @Description : Application DTO for workflow catalog entries.
 *
 */
package dz.sh.hidra.modules.workflow.application.dto;

import java.time.Instant;
import java.util.List;

/**
 * Application DTO for workflow catalog entries.
 *
 * <p>Architecture role:
 * Application-layer workflow data transfer projection. It is technology-neutral and must not depend
 * on REST, persistence, Spring, JPA, telemetry implementation classes, topology implementation
 * classes, planning, monitoring, incidents, audit implementation, integration, analytics, reporting,
 * or notification.
 */
public record WorkflowCatalogDto(
        String id,
        String catalogName,
        String code,
        boolean active,
        int sortOrder,
        boolean systemDefined,
        List<WorkflowCatalogTranslationDto> translations,
        Instant createdAt,
        Instant updatedAt) {

    public WorkflowCatalogDto {
    }
}
