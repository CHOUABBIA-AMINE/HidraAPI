/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : FacilityApplicationService
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.service
 *
 * @Description : Application service for facilities.
 *
 */
package dz.sh.hidra.modules.topology.application.service;

import dz.sh.hidra.modules.topology.application.command.RegisterFacilityCommand;
import dz.sh.hidra.modules.topology.application.dto.FacilitySummaryDto;
import dz.sh.hidra.modules.topology.application.mapper.TopologyApplicationMapper;
import dz.sh.hidra.modules.topology.application.port.in.RegisterFacilityUseCase;
import dz.sh.hidra.modules.topology.application.port.out.FacilityRepositoryPort;
import dz.sh.hidra.modules.topology.domain.model.Facility;
import dz.sh.hidra.modules.topology.domain.value.*;
import java.time.Instant;
import java.util.Objects;
public class FacilityApplicationService implements RegisterFacilityUseCase {
    private final FacilityRepositoryPort repositoryPort;
    public FacilityApplicationService(FacilityRepositoryPort repositoryPort) { this.repositoryPort = Objects.requireNonNull(repositoryPort, "Facility repository port must not be null."); }
    public FacilitySummaryDto registerFacility(RegisterFacilityCommand command) {
        Instant now = Instant.now();
        Facility facility = new Facility(TopologyId.newId().value(), command.code(), command.nameAr(), command.nameFr(), command.nameEn(), command.facilityTypeId(), command.facilityKind() == null ? FacilityKind.OTHER : command.facilityKind(), command.ownerPartyId(), command.ownerPartyCodeSnapshot(), command.ownerPartyNameSnapshot(), command.latitude(), command.longitude(), command.elevationMeters(), FacilityStatus.PLANNED, null, null, now, now);
        return TopologyApplicationMapper.toSummary(repositoryPort.save(facility));
    }
}
