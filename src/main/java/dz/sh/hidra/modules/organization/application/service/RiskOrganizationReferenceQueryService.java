/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskOrganizationReferenceQueryService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-05
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.service
 *
 * @Description : Resolves OrganizationUnit references for Risk.
 *
 */
package dz.sh.hidra.modules.organization.application.service;

import dz.sh.hidra.modules.organization.application.contract.risk.RiskOrganizationReferenceContract;
import dz.sh.hidra.modules.organization.application.port.out.OrganizationUnitRepositoryPort;
import dz.sh.hidra.modules.organization.domain.model.OrganizationUnit;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public final class RiskOrganizationReferenceQueryService
        implements RiskOrganizationReferenceContract {

    private final OrganizationUnitRepositoryPort repository;

    public RiskOrganizationReferenceQueryService(OrganizationUnitRepositoryPort repository) {
        this.repository = Objects.requireNonNull(repository, "OrganizationUnitRepositoryPort must not be null.");
    }

    @Override
    public Optional<ScopeView> resolveOrganizationUnit(String organizationUnitId) {
        if (organizationUnitId == null || organizationUnitId.isBlank()) {
            return Optional.empty();
        }
        return repository.findById(organizationUnitId.trim())
                .map(unit -> new ScopeView(unit.id(), unit.code(), label(unit)));
    }

    private static String label(OrganizationUnit unit) {
        if (unit.nameFr() != null && !unit.nameFr().isBlank()) return unit.nameFr();
        if (unit.nameEn() != null && !unit.nameEn().isBlank()) return unit.nameEn();
        return unit.nameAr();
    }
}
