/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ListOrganizationUnitsService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.service
 *
 * @Description : Application service implementing organization unit listing use case.
 *
 */
package dz.sh.hidra.modules.organization.application.service;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

import dz.sh.hidra.kernel.application.pagination.PageRequest;
import dz.sh.hidra.kernel.application.pagination.PageResult;
import dz.sh.hidra.modules.organization.application.dto.OrganizationUnitDto;
import dz.sh.hidra.modules.organization.application.mapper.OrganizationApplicationMapper;
import dz.sh.hidra.modules.organization.application.port.in.ListOrganizationUnitsUseCase;
import dz.sh.hidra.modules.organization.application.port.out.OrganizationUnitRepository;
import dz.sh.hidra.modules.organization.application.query.ListOrganizationUnitsQuery;
import dz.sh.hidra.modules.organization.domain.model.OrganizationUnit;

/**
 * Implements the organization unit listing use case.
 *
 * <p>Business role:
 * This service lists organization units such as divisions, regions, station organization units,
 * and teams using the repository methods already defined by ORG-011.
 *
 * <p>Architecture role:
 * This application service does not depend on REST, persistence entities, Spring, JPA, identity,
 * topology, platform, or infrastructure code.
 *
 * <p>Validation:
 * The query and page request are required. Because ORG-011 does not define a broad find-all
 * organization unit port method, unscoped listing returns an empty page until richer query ports
 * are added later.
 *
 * <p>Usage:
 * Use this service through ListOrganizationUnitsUseCase.
 */
public final class ListOrganizationUnitsService implements ListOrganizationUnitsUseCase {

    private final OrganizationUnitRepository organizationUnitRepository;
    private final OrganizationApplicationMapper mapper;

    public ListOrganizationUnitsService(
            OrganizationUnitRepository organizationUnitRepository,
            OrganizationApplicationMapper mapper) {

        this.organizationUnitRepository = Objects.requireNonNull(
                organizationUnitRepository,
                "Organization unit repository must not be null.");
        this.mapper = Objects.requireNonNull(mapper, "Organization application mapper must not be null.");
    }

    @Override
    public PageResult<OrganizationUnitDto> listOrganizationUnits(ListOrganizationUnitsQuery query) {
        Objects.requireNonNull(query, "List organization units query must not be null.");

        PageRequest pageRequest = query.pageRequest();
        List<OrganizationUnit> candidates = candidates(query);

        List<OrganizationUnitDto> filteredUnits = candidates.stream()
                .filter(unit -> query.status() == null || unit.status() == query.status())
                .filter(unit -> matchesSearchText(unit, query.searchText()))
                .map(mapper::toDto)
                .toList();

        return page(filteredUnits, pageRequest);
    }

    private List<OrganizationUnit> candidates(ListOrganizationUnitsQuery query) {
        if (query.parentId() != null) {
            return organizationUnitRepository.findChildrenOf(query.parentId());
        }

        if (query.type() != null) {
            return organizationUnitRepository.findByType(query.type());
        }

        return List.of();
    }

    private static boolean matchesSearchText(OrganizationUnit unit, String searchText) {
        if (searchText == null || searchText.isBlank()) {
            return true;
        }

        String normalized = searchText.toLowerCase(Locale.ROOT);
        return unit.code().value().toLowerCase(Locale.ROOT).contains(normalized)
                || unit.name().value().toLowerCase(Locale.ROOT).contains(normalized);
    }

    private static PageResult<OrganizationUnitDto> page(List<OrganizationUnitDto> items, PageRequest pageRequest) {
        int fromIndex = Math.min(pageRequest.page() * pageRequest.size(), items.size());
        int toIndex = Math.min(fromIndex + pageRequest.size(), items.size());
        return PageResult.of(items.subList(fromIndex, toIndex), pageRequest.page(), pageRequest.size(), items.size());
    }
}
