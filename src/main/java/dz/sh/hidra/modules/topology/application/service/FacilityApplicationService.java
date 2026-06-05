/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : FacilityApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.service
 *
 * @Description : Application service implementing facility use cases.
 *
 */
package dz.sh.hidra.modules.topology.application.service;

import java.math.BigDecimal;
import java.util.Objects;

import dz.sh.hidra.kernel.application.pagination.PageResult;
import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.modules.topology.application.command.CreateFacilityCommand;
import dz.sh.hidra.modules.topology.application.dto.FacilityDto;
import dz.sh.hidra.modules.topology.application.dto.GeoCoordinateDto;
import dz.sh.hidra.modules.topology.application.dto.OrganizationUnitReferenceDto;
import dz.sh.hidra.modules.topology.application.port.in.CreateFacilityUseCase;
import dz.sh.hidra.modules.topology.application.port.in.GetFacilityUseCase;
import dz.sh.hidra.modules.topology.application.port.in.ListFacilitiesUseCase;
import dz.sh.hidra.modules.topology.application.port.out.FacilityRepositoryPort;
import dz.sh.hidra.modules.topology.application.query.GetFacilityByIdQuery;
import dz.sh.hidra.modules.topology.application.query.ListFacilitiesQuery;
import dz.sh.hidra.modules.topology.domain.model.Facility;
import dz.sh.hidra.modules.topology.domain.service.TopologyRegistrationDomainService;
import dz.sh.hidra.modules.topology.domain.value.GeoCoordinate;
import dz.sh.hidra.modules.topology.domain.value.OrganizationUnitReference;

/**
 * Implements facility create, get, and list use cases.
 *
 * <p>Business role:
 * Coordinates physical facility creation and retrieval for stations, terminals, processing plants,
 * production field interfaces, gathering centers, storage facilities, and receipt/delivery facilities.
 *
 * <p>Architecture role:
 * This application service does not own organization units or people assignments.
 *
 * <p>Validation:
 * Facility code uniqueness and physical-facility domain rules are enforced before saving.
 *
 * <p>Usage:
 * Wire this class as the implementation for facility use-case ports.
 */
public final class FacilityApplicationService
        implements CreateFacilityUseCase, GetFacilityUseCase, ListFacilitiesUseCase {

    private final FacilityRepositoryPort facilityRepository;
    private final TopologyRegistrationDomainService registrationDomainService;

    public FacilityApplicationService(
            FacilityRepositoryPort facilityRepository,
            TopologyRegistrationDomainService registrationDomainService) {

        this.facilityRepository = Objects.requireNonNull(facilityRepository, "Facility repository port must not be null.");
        this.registrationDomainService = Objects.requireNonNull(
                registrationDomainService,
                "Topology registration domain service must not be null.");
    }

    @Override
    public FacilityDto createFacility(CreateFacilityCommand command) {
        Objects.requireNonNull(command, "Create facility command must not be null.");

        if (facilityRepository.existsByCode(command.code())) {
            throw new BusinessRuleViolationException("Facility code already exists.");
        }

        Facility facility = Facility.create(
                command.code(),
                command.name(),
                command.facilityType(),
                command.productType(),
                command.coordinate(),
                command.organizationUnitReference());

        registrationDomainService.validateFacilityRegistration(facility);

        return toDto(facilityRepository.save(facility));
    }

    @Override
    public FacilityDto getFacility(GetFacilityByIdQuery query) {
        Objects.requireNonNull(query, "Get facility query must not be null.");

        return facilityRepository.findById(query.id())
                .map(FacilityApplicationService::toDto)
                .orElseThrow(() -> new BusinessRuleViolationException("Facility was not found."));
    }

    @Override
    public PageResult<FacilityDto> listFacilities(ListFacilitiesQuery query) {
        Objects.requireNonNull(query, "List facilities query must not be null.");

        return mapPage(facilityRepository.findAll(query));
    }

    private static FacilityDto toDto(Facility facility) {
        return new FacilityDto(
                facility.id().value(),
                facility.code().value(),
                facility.name().value(),
                facility.facilityType().name(),
                facility.productType().name(),
                facility.status().name(),
                toGeoCoordinateDto(facility.coordinate()),
                toOrganizationUnitReferenceDto(facility.organizationUnitReference()),
                facility.createdAt(),
                facility.updatedAt());
    }

    private static GeoCoordinateDto toGeoCoordinateDto(GeoCoordinate coordinate) {
        if (coordinate == null) {
            return null;
        }

        return new GeoCoordinateDto(
                BigDecimal.valueOf(coordinate.latitude()).stripTrailingZeros(),
                BigDecimal.valueOf(coordinate.longitude()).stripTrailingZeros());
    }

    private static OrganizationUnitReferenceDto toOrganizationUnitReferenceDto(OrganizationUnitReference reference) {
        if (reference == null) {
            return null;
        }

        return new OrganizationUnitReferenceDto(
                reference.referenceType(),
                reference.referenceId(),
                reference.referenceCode(),
                reference.referenceName());
    }

    private static PageResult<FacilityDto> mapPage(PageResult<Facility> pageResult) {
        Objects.requireNonNull(pageResult, "Page result must not be null.");

        return PageResult.of(
                pageResult.items().stream().map(FacilityApplicationService::toDto).toList(),
                pageResult.page(),
                pageResult.size(),
                pageResult.totalElements());
    }

}
