/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyRestMapper
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Utility
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.mapper
 *
 * @Description : Maps topology REST models to application models.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.mapper;

import dz.sh.hidra.modules.topology.api.rest.request.*;
import dz.sh.hidra.modules.topology.api.rest.response.*;
import dz.sh.hidra.modules.topology.application.command.*;
import dz.sh.hidra.modules.topology.application.dto.*;
public final class TopologyRestMapper {
    private TopologyRestMapper() { throw new UnsupportedOperationException("Utility class must not be instantiated."); }
    public static CreatePipelineSystemCommand toCommand(CreatePipelineSystemRequest r) { return new CreatePipelineSystemCommand(r.code(), r.nameAr(), r.nameFr(), r.nameEn(), r.systemType(), r.description()); }
    public static RegisterFacilityCommand toCommand(RegisterFacilityRequest r) { return new RegisterFacilityCommand(r.code(), r.nameAr(), r.nameFr(), r.nameEn(), r.facilityTypeId(), r.facilityKind(), r.ownerPartyId(), r.ownerPartyCodeSnapshot(), r.ownerPartyNameSnapshot(), r.latitude(), r.longitude(), r.elevationMeters()); }
    public static PipelineSystemResponse toResponse(PipelineSystemSummaryDto d) { return new PipelineSystemResponse(d.id(), d.code(), d.nameAr(), d.nameFr(), d.nameEn(), d.systemType(), d.status()); }
    public static FacilityResponse toResponse(FacilitySummaryDto d) { return new FacilityResponse(d.id(), d.code(), d.nameAr(), d.nameFr(), d.nameEn(), d.facilityKind(), d.status()); }
}
