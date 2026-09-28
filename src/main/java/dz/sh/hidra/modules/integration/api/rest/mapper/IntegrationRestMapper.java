/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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
import java.util.Objects;

/**
 * Maps integration REST models to application models.
 */
public final class IntegrationRestMapper {

    private static final IntegrationGeneratedRestMapper GENERATED = IntegrationGeneratedRestMapper.INSTANCE;

    private IntegrationRestMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static RecordExchangeMessageCommand toCommand(RecordExchangeMessageRequest request) {
        return GENERATED.toCommand(Objects.requireNonNull(request, "RecordExchangeMessageRequest must not be null."));
    }

    public static RegisterExternalSystemCommand toCommand(RegisterExternalSystemRequest request) {
        return GENERATED.toCommand(Objects.requireNonNull(request, "RegisterExternalSystemRequest must not be null."));
    }

    public static StartIntegrationJobRunCommand toCommand(StartIntegrationJobRunRequest request) {
        return GENERATED.toCommand(Objects.requireNonNull(request, "StartIntegrationJobRunRequest must not be null."));
    }

    public static IntegrationExchangeMessageResponse toResponse(IntegrationExchangeMessageSummaryDto dto) {
        return GENERATED.toResponse(Objects.requireNonNull(dto, "IntegrationExchangeMessageSummaryDto must not be null."));
    }

    public static ExternalSystemResponse toResponse(ExternalSystemSummaryDto dto) {
        return GENERATED.toResponse(Objects.requireNonNull(dto, "ExternalSystemSummaryDto must not be null."));
    }

    public static IntegrationJobRunResponse toResponse(IntegrationJobRunSummaryDto dto) {
        return GENERATED.toResponse(Objects.requireNonNull(dto, "IntegrationJobRunSummaryDto must not be null."));
    }
}
