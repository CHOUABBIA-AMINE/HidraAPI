/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationUnitApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.service
 *
 * @Description : Application service for organization units.
 *
 */
package dz.sh.hidra.modules.organization.application.service;

import org.springframework.stereotype.Service;

import dz.sh.hidra.modules.organization.application.command.CreateOrganizationUnitCommand;
import dz.sh.hidra.modules.organization.application.dto.OrganizationUnitSummaryDto;
import dz.sh.hidra.modules.organization.application.mapper.OrganizationApplicationMapper;
import dz.sh.hidra.modules.organization.application.port.in.CreateOrganizationUnitUseCase;
import dz.sh.hidra.modules.organization.application.port.out.OrganizationUnitRepositoryPort;
import dz.sh.hidra.modules.organization.domain.model.OrganizationUnit;
import dz.sh.hidra.modules.organization.domain.value.OrganizationId;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitStatus;

import java.time.Instant;
import java.util.Objects;

/**
 * Application service for organization units.
 */
@Service
public final class OrganizationUnitApplicationService implements CreateOrganizationUnitUseCase {

    private final OrganizationUnitRepositoryPort organizationUnitRepositoryPort;

    public OrganizationUnitApplicationService(OrganizationUnitRepositoryPort organizationUnitRepositoryPort) {
        this.organizationUnitRepositoryPort = Objects.requireNonNull(organizationUnitRepositoryPort, "Organization unit repository port must not be null.");
    }

    @Override
    public OrganizationUnitSummaryDto createOrganizationUnit(CreateOrganizationUnitCommand command) {
        Objects.requireNonNull(command, "Create organization unit command must not be null.");
        Instant now = Instant.now();
        OrganizationUnit unit = new OrganizationUnit(
                OrganizationId.newId().value(),
                command.code(),
                command.nameAr(),
                command.nameFr(),
                command.nameEn(),
                command.unitTypeId(),
                command.parentUnitId(),
                command.status() == null ? OrganizationUnitStatus.ACTIVE : command.status(),
                null,
                null,
                null,
                null,
                command.validFrom(),
                null,
                now,
                now
        );
        return OrganizationApplicationMapper.toSummary(organizationUnitRepositoryPort.save(unit));
    }
}
