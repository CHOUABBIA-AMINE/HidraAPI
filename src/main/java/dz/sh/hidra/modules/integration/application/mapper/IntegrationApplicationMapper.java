/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationApplicationMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.application.mapper
 *
 * @Description : Maps integration domain models to DTOs.
 *
 */
package dz.sh.hidra.modules.integration.application.mapper;

import dz.sh.hidra.modules.integration.application.dto.ExternalSystemSummaryDto;
import dz.sh.hidra.modules.integration.application.dto.IntegrationExchangeMessageSummaryDto;
import dz.sh.hidra.modules.integration.application.dto.IntegrationJobRunSummaryDto;
import dz.sh.hidra.modules.integration.domain.model.ExternalSystem;
import dz.sh.hidra.modules.integration.domain.model.IntegrationExchangeMessage;
import dz.sh.hidra.modules.integration.domain.model.IntegrationJobRun;

/**
 * Maps integration domain models to DTOs.
 */
public final class IntegrationApplicationMapper {

    private IntegrationApplicationMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static ExternalSystemSummaryDto toSummary(ExternalSystem externalSystem) {
        return new ExternalSystemSummaryDto(externalSystem.id(), externalSystem.code(), externalSystem.nameFr(), externalSystem.systemTypeId(), externalSystem.environment(), externalSystem.criticality(), externalSystem.status(), externalSystem.createdAt());
    }

    public static IntegrationJobRunSummaryDto toSummary(IntegrationJobRun jobRun) {
        return new IntegrationJobRunSummaryDto(jobRun.id(), jobRun.jobDefinitionId(), jobRun.runNumber(), jobRun.triggerType(), jobRun.status(), jobRun.correlationId(), jobRun.startedAt(), jobRun.completedAt());
    }

    public static IntegrationExchangeMessageSummaryDto toSummary(IntegrationExchangeMessage message) {
        return new IntegrationExchangeMessageSummaryDto(message.id(), message.jobRunId(), message.externalSystemId(), message.direction(), message.externalMessageId(), message.payloadHash(), message.status(), message.receivedOrSentAt());
    }
}
