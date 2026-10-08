/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HseOrganizationReferenceQueryService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.service
 *
 * @Description : Enforces HSE-owned lifecycle integrity through owner-controlled references.
 *
 */
package dz.sh.hidra.modules.organization.application.service;

import dz.sh.hidra.modules.organization.application.contract.hse.HseOrganizationReferenceContract;
import dz.sh.hidra.modules.organization.application.port.out.OrganizationUnitRepositoryPort;
import dz.sh.hidra.modules.organization.domain.model.OrganizationUnit;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public final class HseOrganizationReferenceQueryService
        implements HseOrganizationReferenceContract {

    private final OrganizationUnitRepositoryPort repository;

    public HseOrganizationReferenceQueryService(OrganizationUnitRepositoryPort repository) {
        this.repository = Objects.requireNonNull(repository, "OrganizationUnitRepositoryPort must not be null.");
    }

    @Override
    public Optional<Unit> resolve(String organizationUnitId) {
        if (organizationUnitId == null || organizationUnitId.isBlank()) {
            return Optional.empty();
        }
        return repository.findById(organizationUnitId.trim())
                .map(unit -> new Unit(unit.id(), unit.code(), label(unit)));
    }

    private static String label(OrganizationUnit unit) {
        if (unit.nameFr() != null && !unit.nameFr().isBlank()) return unit.nameFr();
        if (unit.nameEn() != null && !unit.nameEn().isBlank()) return unit.nameEn();
        return unit.nameAr();
    }
}
