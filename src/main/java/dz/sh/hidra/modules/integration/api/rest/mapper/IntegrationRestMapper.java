/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.api.rest.mapper
 *
 * @Description : Maps integration REST models to application models.
 *
 */
package dz.sh.hidra.modules.integration.api.rest.mapper;
import dz.sh.hidra.modules.integration.api.rest.request.RecordExchangeMessageRequest;
import dz.sh.hidra.modules.integration.api.rest.request.RegisterExternalSystemRequest;
import dz.sh.hidra.modules.integration.api.rest.request.StartIntegrationJobRunRequest;
import dz.sh.hidra.modules.integration.api.rest.response.ExternalSystemResponse;
import dz.sh.hidra.modules.integration.api.rest.response.IntegrationExchangeMessageResponse;
import dz.sh.hidra.modules.integration.api.rest.response.IntegrationJobRunResponse;
import dz.sh.hidra.modules.integration.application.command.RecordExchangeMessageCommand;
import dz.sh.hidra.modules.integration.application.command.RegisterExternalSystemCommand;
import dz.sh.hidra.modules.integration.application.command.StartIntegrationJobRunCommand;
import dz.sh.hidra.modules.integration.application.dto.ExternalSystemSummaryDto;
import dz.sh.hidra.modules.integration.application.dto.IntegrationExchangeMessageSummaryDto;
import dz.sh.hidra.modules.integration.application.dto.IntegrationJobRunSummaryDto;

/**
 * Maps integration REST models to application models.
 */
public final class IntegrationRestMapper {

    private IntegrationRestMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static RecordExchangeMessageCommand toCommand(RecordExchangeMessageRequest request) {
        return new RecordExchangeMessageCommand(
                request.jobRunId(),
                request.externalSystemId(),
                request.endpointId(),
                request.direction(),
                request.messageTypeId(),
                request.externalMessageId(),
                request.payloadFormatId(),
                request.payloadStorageMode(),
                request.payloadSanitized(),
                request.payloadReference(),
                request.payloadHash(),
                request.contentLengthBytes(),
                request.receivedOrSentAt(),
                request.correlationId(),
                request.status()
        );
    }

    public static RegisterExternalSystemCommand toCommand(RegisterExternalSystemRequest request) {
        return new RegisterExternalSystemCommand(
                request.code(),
                request.nameAr(),
                request.nameFr(),
                request.nameEn(),
                request.systemTypeId(),
                request.ownerOrganizationUnitId(),
                request.environment(),
                request.criticality(),
                request.description()
        );
    }

    public static StartIntegrationJobRunCommand toCommand(StartIntegrationJobRunRequest request) {
        return new StartIntegrationJobRunCommand(
                request.jobDefinitionId(),
                request.runNumber(),
                request.triggerType(),
                request.triggeredByActorId(),
                request.correlationId()
        );
    }

    public static IntegrationExchangeMessageResponse toResponse(IntegrationExchangeMessageSummaryDto dto) {
        return new IntegrationExchangeMessageResponse(
                dto.id(),
                dto.jobRunId(),
                dto.externalSystemId(),
                dto.direction(),
                dto.externalMessageId(),
                dto.payloadHash(),
                dto.status(),
                dto.receivedOrSentAt()
        );
    }

    public static ExternalSystemResponse toResponse(ExternalSystemSummaryDto dto) {
        return new ExternalSystemResponse(
                dto.id(),
                dto.code(),
                dto.nameFr(),
                dto.systemTypeId(),
                dto.environment(),
                dto.criticality(),
                dto.status(),
                dto.createdAt()
        );
    }

    public static IntegrationJobRunResponse toResponse(IntegrationJobRunSummaryDto dto) {
        return new IntegrationJobRunResponse(
                dto.id(),
                dto.jobDefinitionId(),
                dto.runNumber(),
                dto.triggerType(),
                dto.status(),
                dto.correlationId(),
                dto.startedAt(),
                dto.completedAt()
        );
    }
}
