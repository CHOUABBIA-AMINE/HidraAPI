/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MonitoringRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.api.rest.mapper
 *
 * @Description : Maps monitoring REST models to application models.
 *
 */
package dz.sh.hidra.modules.monitoring.api.rest.mapper;
import dz.sh.hidra.modules.monitoring.api.rest.request.CreateMonitoringRuleRequest;
import dz.sh.hidra.modules.monitoring.api.rest.request.RecordDeviationRequest;
import dz.sh.hidra.modules.monitoring.api.rest.response.DeviationResponse;
import dz.sh.hidra.modules.monitoring.api.rest.response.MonitoringRuleResponse;
import dz.sh.hidra.modules.monitoring.application.command.CreateMonitoringRuleCommand;
import dz.sh.hidra.modules.monitoring.application.command.RecordDeviationCommand;
import dz.sh.hidra.modules.monitoring.application.dto.DeviationSummaryDto;
import dz.sh.hidra.modules.monitoring.application.dto.MonitoringRuleSummaryDto;
import java.util.Objects;

/**
 * Maps monitoring REST models to application models.
 */
public final class MonitoringRestMapper {

    private static final MonitoringGeneratedRestMapper GENERATED = MonitoringGeneratedRestMapper.INSTANCE;

    private MonitoringRestMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static CreateMonitoringRuleCommand toCommand(CreateMonitoringRuleRequest request) {
        return GENERATED.toCommand(Objects.requireNonNull(request, "CreateMonitoringRuleRequest must not be null."));
    }

    public static RecordDeviationCommand toCommand(RecordDeviationRequest request) {
        return GENERATED.toCommand(Objects.requireNonNull(request, "RecordDeviationRequest must not be null."));
    }

    public static MonitoringRuleResponse toResponse(MonitoringRuleSummaryDto dto) {
        return GENERATED.toResponse(Objects.requireNonNull(dto, "MonitoringRuleSummaryDto must not be null."));
    }

    public static DeviationResponse toResponse(DeviationSummaryDto dto) {
        return GENERATED.toResponse(Objects.requireNonNull(dto, "DeviationSummaryDto must not be null."));
    }
}
