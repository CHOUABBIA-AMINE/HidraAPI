/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetsOrganizationUnitReferenceQueryService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-05
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.service
 *
 * @Description : Resolves OrganizationUnit existence for Assets.
 *
 */
package dz.sh.hidra.modules.organization.application.service;

import dz.sh.hidra.modules.organization.application.contract.assets.AssetsOrganizationUnitReferenceContract;
import dz.sh.hidra.modules.organization.application.port.out.OrganizationUnitRepositoryPort;
import java.util.Objects;
import org.springframework.stereotype.Service;

@Service
public final class AssetsOrganizationUnitReferenceQueryService
        implements AssetsOrganizationUnitReferenceContract {

    private final OrganizationUnitRepositoryPort repository;

    public AssetsOrganizationUnitReferenceQueryService(OrganizationUnitRepositoryPort repository) {
        this.repository = Objects.requireNonNull(repository, "OrganizationUnitRepositoryPort must not be null.");
    }

    @Override
    public boolean exists(String organizationUnitId) {
        return organizationUnitId != null
                && !organizationUnitId.isBlank()
                && repository.findById(organizationUnitId.trim()).isPresent();
    }
}
