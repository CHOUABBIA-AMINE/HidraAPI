/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyApplicationMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.mapper
 *
 * @Description : Maps topology domain models to DTOs.
 *
 */
package dz.sh.hidra.modules.topology.application.mapper;

import dz.sh.hidra.modules.topology.application.dto.*;
import dz.sh.hidra.modules.topology.domain.model.*;
public final class TopologyApplicationMapper {
    private TopologyApplicationMapper() { throw new UnsupportedOperationException("Utility class must not be instantiated."); }
    public static PipelineSystemSummaryDto toSummary(PipelineSystem model) { return new PipelineSystemSummaryDto(model.id(), model.code(), model.nameAr(), model.nameFr(), model.nameEn(), model.systemType(), model.status()); }
    public static FacilitySummaryDto toSummary(Facility model) { return new FacilitySummaryDto(model.id(), model.code(), model.nameAr(), model.nameFr(), model.nameEn(), model.facilityKind(), model.status()); }
}
