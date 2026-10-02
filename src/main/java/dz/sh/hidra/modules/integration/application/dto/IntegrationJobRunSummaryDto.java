/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationJobRunSummaryDto
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.application.dto
 *
 * @Description : Integration job run summary DTO.
 *
 */
package dz.sh.hidra.modules.integration.application.dto;

import dz.sh.hidra.modules.integration.domain.value.JobRunStatus;
import dz.sh.hidra.modules.integration.domain.value.JobTriggerType;

import java.time.Instant;

/**
 * Integration job run summary DTO.
 */
public record IntegrationJobRunSummaryDto(
        String id,
        String jobDefinitionId,
        long runNumber,
        JobTriggerType triggerType,
        JobRunStatus status,
        String correlationId,
        Instant startedAt,
        Instant completedAt
) {
}
