/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : GetOrganizationUnitService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.service
 *
 * @Description : Application service implementing organization unit retrieval use case.
 *
 */
package dz.sh.hidra.modules.organization.application.service;

import java.util.Objects;
import java.util.Optional;

import dz.sh.hidra.modules.organization.application.dto.OrganizationUnitDto;
import dz.sh.hidra.modules.organization.application.mapper.OrganizationApplicationMapper;
import dz.sh.hidra.modules.organization.application.port.in.GetOrganizationUnitUseCase;
import dz.sh.hidra.modules.organization.application.port.out.OrganizationUnitRepository;
import dz.sh.hidra.modules.organization.application.query.GetOrganizationUnitByIdQuery;

/**
 * Implements the organization unit retrieval use case.
 *
 * <p>Business role:
 * This service retrieves organization units, including station-as-organization-unit structures,
 * without exposing topology assets or persistence entities.
 *
 * <p>Architecture role:
 * This is an application service implementing an inbound port. It depends only on application
 * outbound ports and application mapping.
 *
 * <p>Validation:
 * The query is required. Missing organization units are represented as Optional.empty().
 *
 * <p>Usage:
 * API controllers should call this service through the GetOrganizationUnitUseCase interface.
 */
public final class GetOrganizationUnitService implements GetOrganizationUnitUseCase {

    private final OrganizationUnitRepository organizationUnitRepository;
    private final OrganizationApplicationMapper mapper;

    public GetOrganizationUnitService(
            OrganizationUnitRepository organizationUnitRepository,
            OrganizationApplicationMapper mapper) {

        this.organizationUnitRepository = Objects.requireNonNull(
                organizationUnitRepository,
                "Organization unit repository must not be null.");
        this.mapper = Objects.requireNonNull(mapper, "Organization application mapper must not be null.");
    }

    @Override
    public Optional<OrganizationUnitDto> getOrganizationUnit(GetOrganizationUnitByIdQuery query) {
        Objects.requireNonNull(query, "Get organization unit query must not be null.");

        return organizationUnitRepository.findById(query.organizationUnitId())
                .map(mapper::toDto);
    }
}
