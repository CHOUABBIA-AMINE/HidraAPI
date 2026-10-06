/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakDetectionOrganizationUnitReferenceQueryService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-06
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.service
 *
 * @Description : Resolves OrganizationUnit existence for Leak Detection.
 *
 */
package dz.sh.hidra.modules.organization.application.service;

import dz.sh.hidra.modules.organization.application.contract.leakdetection.LeakDetectionOrganizationUnitReferenceContract;
import dz.sh.hidra.modules.organization.application.port.out.OrganizationUnitRepositoryPort;
import java.util.Objects;
import org.springframework.stereotype.Service;

@Service
public final class LeakDetectionOrganizationUnitReferenceQueryService
        implements LeakDetectionOrganizationUnitReferenceContract {

    private final OrganizationUnitRepositoryPort repository;

    public LeakDetectionOrganizationUnitReferenceQueryService(OrganizationUnitRepositoryPort repository) {
        this.repository = Objects.requireNonNull(repository, "OrganizationUnitRepositoryPort must not be null.");
    }

    @Override
    public boolean exists(String organizationUnitId) {
        return organizationUnitId != null
                && !organizationUnitId.isBlank()
                && repository.findById(organizationUnitId.trim()).isPresent();
    }
}
