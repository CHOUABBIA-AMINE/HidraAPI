/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
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

/**
 * Maps topology REST models to application models.
 */
public final class TopologyRestMapper {

    private TopologyRestMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static CreatePipelineSystemCommand toCommand(CreatePipelineSystemRequest request) {
        return new CreatePipelineSystemCommand(
                request.code(),
                request.nameAr(),
                request.nameFr(),
                request.nameEn(),
                request.systemType(),
                request.description()
        );
    }

    public static RegisterFacilityCommand toCommand(RegisterFacilityRequest request) {
        return new RegisterFacilityCommand(
                request.code(),
                request.nameAr(),
                request.nameFr(),
                request.nameEn(),
                request.facilityTypeId(),
                request.facilityKind(),
                request.ownerPartyId(),
                request.ownerPartyCodeSnapshot(),
                request.ownerPartyNameSnapshot(),
                request.latitude(),
                request.longitude(),
                request.elevationMeters()
        );
    }

    public static PipelineSystemResponse toResponse(PipelineSystemSummaryDto dto) {
        return new PipelineSystemResponse(
                dto.id(),
                dto.code(),
                dto.nameAr(),
                dto.nameFr(),
                dto.nameEn(),
                dto.systemType(),
                dto.status()
        );
    }

    public static FacilityResponse toResponse(FacilitySummaryDto dto) {
        return new FacilityResponse(
                dto.id(),
                dto.code(),
                dto.nameAr(),
                dto.nameFr(),
                dto.nameEn(),
                dto.facilityKind(),
                dto.status()
        );
    }
}
