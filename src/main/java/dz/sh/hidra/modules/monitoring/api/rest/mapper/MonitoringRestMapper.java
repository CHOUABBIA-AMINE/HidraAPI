/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MonitoringRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
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

/**
 * Maps monitoring REST models to application models.
 */
public final class MonitoringRestMapper {

    private MonitoringRestMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static CreateMonitoringRuleCommand toCommand(CreateMonitoringRuleRequest request) {
        return new CreateMonitoringRuleCommand(
                request.code(),
                request.nameAr(),
                request.nameFr(),
                request.nameEn(),
                request.ruleType(),
                request.evaluationFrequencyId(),
                request.topologyAssetType(),
                request.topologyAssetId(),
                request.topologyAssetCode(),
                request.telemetryPointId(),
                request.planningTargetTypeId(),
                request.expression(),
                request.createdByActorId()
        );
    }

    public static RecordDeviationCommand toCommand(RecordDeviationRequest request) {
        return new RecordDeviationCommand(
                request.evaluationId(),
                request.planTargetId(),
                request.expectedFlowStateId(),
                request.trustedTelemetryReadingId(),
                request.telemetryPointId(),
                request.topologyAssetType(),
                request.topologyAssetId(),
                request.topologyAssetCode(),
                request.actualValue(),
                request.expectedValue(),
                request.differenceValue(),
                request.differencePercent(),
                request.unitId(),
                request.severity(),
                request.reasonCode(),
                request.reasonMessage()
        );
    }

    public static MonitoringRuleResponse toResponse(MonitoringRuleSummaryDto dto) {
        return new MonitoringRuleResponse(
                dto.id(),
                dto.code(),
                dto.nameFr(),
                dto.ruleType(),
                dto.status(),
                dto.topologyAssetType(),
                dto.topologyAssetId(),
                dto.telemetryPointId()
        );
    }

    public static DeviationResponse toResponse(DeviationSummaryDto dto) {
        return new DeviationResponse(
                dto.id(),
                dto.planTargetId(),
                dto.topologyAssetType(),
                dto.topologyAssetId(),
                dto.actualValue(),
                dto.expectedValue(),
                dto.differencePercent(),
                dto.severity(),
                dto.status(),
                dto.detectedAt()
        );
    }
}
