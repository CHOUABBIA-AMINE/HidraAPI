/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.mapper
 *
 * @Description : Maps topology REST models to application models.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.mapper;
import dz.sh.hidra.modules.topology.api.rest.request.CreatePipelineSystemRequest;
import dz.sh.hidra.modules.topology.api.rest.request.RegisterFacilityRequest;
import dz.sh.hidra.modules.topology.api.rest.response.FacilityResponse;
import dz.sh.hidra.modules.topology.api.rest.response.PipelineSystemResponse;
import dz.sh.hidra.modules.topology.application.command.CreatePipelineSystemCommand;
import dz.sh.hidra.modules.topology.application.command.RegisterFacilityCommand;
import dz.sh.hidra.modules.topology.application.dto.FacilitySummaryDto;
import dz.sh.hidra.modules.topology.application.dto.PipelineSystemSummaryDto;
import java.util.Objects;

/**
 * Maps topology REST models to application models.
 */
public final class TopologyRestMapper {

    private static final TopologyGeneratedRestMapper GENERATED = TopologyGeneratedRestMapper.INSTANCE;

    private TopologyRestMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static CreatePipelineSystemCommand toCommand(CreatePipelineSystemRequest request) {
        return GENERATED.toCommand(Objects.requireNonNull(request, "CreatePipelineSystemRequest must not be null."));
    }

    public static RegisterFacilityCommand toCommand(RegisterFacilityRequest request) {
        return GENERATED.toCommand(Objects.requireNonNull(request, "RegisterFacilityRequest must not be null."));
    }

    public static PipelineSystemResponse toResponse(PipelineSystemSummaryDto dto) {
        return GENERATED.toResponse(Objects.requireNonNull(dto, "PipelineSystemSummaryDto must not be null."));
    }

    public static FacilityResponse toResponse(FacilitySummaryDto dto) {
        return GENERATED.toResponse(Objects.requireNonNull(dto, "FacilitySummaryDto must not be null."));
    }
}
