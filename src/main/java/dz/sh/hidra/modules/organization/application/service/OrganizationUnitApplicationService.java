/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationUnitApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
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

import dz.sh.hidra.modules.organization.application.command.CreateOrganizationUnitCommand;
import dz.sh.hidra.modules.organization.application.dto.OrganizationUnitSummaryDto;
import dz.sh.hidra.modules.organization.application.mapper.OrganizationApplicationMapper;
import dz.sh.hidra.modules.organization.application.port.in.CreateOrganizationUnitUseCase;
import dz.sh.hidra.modules.organization.application.port.out.OrganizationUnitRepositoryPort;
import dz.sh.hidra.modules.organization.application.port.out.OrganizationUnitTypeRepositoryPort;
import dz.sh.hidra.modules.organization.domain.exception.InvalidOrganizationValueException;
import dz.sh.hidra.modules.organization.domain.model.OrganizationUnit;
import dz.sh.hidra.modules.organization.domain.model.OrganizationUnitType;
import dz.sh.hidra.modules.organization.domain.value.OrganizationId;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitStatus;
import java.time.Instant;
import java.util.Objects;
import org.springframework.stereotype.Service;

/**
 * Application service for organization units.
 */
@Service
public final class OrganizationUnitApplicationService implements CreateOrganizationUnitUseCase {

    private final OrganizationUnitRepositoryPort organizationUnitRepositoryPort;
    private final OrganizationUnitTypeRepositoryPort organizationUnitTypeRepositoryPort;

    public OrganizationUnitApplicationService(
            OrganizationUnitRepositoryPort organizationUnitRepositoryPort,
            OrganizationUnitTypeRepositoryPort organizationUnitTypeRepositoryPort
    ) {
        this.organizationUnitRepositoryPort = Objects.requireNonNull(
                organizationUnitRepositoryPort,
                "Organization unit repository port must not be null."
        );
        this.organizationUnitTypeRepositoryPort = Objects.requireNonNull(
                organizationUnitTypeRepositoryPort,
                "Organization unit type repository port must not be null."
        );
    }

    @Override
    public OrganizationUnitSummaryDto createOrganizationUnit(
            CreateOrganizationUnitCommand command
    ) {
        Objects.requireNonNull(
                command,
                "Create organization unit command must not be null."
        );

        OrganizationUnitType unitType = organizationUnitTypeRepositoryPort
                .findById(command.unitTypeId())
                .orElseThrow(() -> new InvalidOrganizationValueException(
                        "Organization unit type does not exist: " + command.unitTypeId()
                ));
        if (!unitType.active()) {
            throw new InvalidOrganizationValueException(
                    "Organization unit type is inactive and cannot be selected: "
                            + command.unitTypeId()
            );
        }

        Instant now = Instant.now();
        OrganizationUnit unit = new OrganizationUnit(
                OrganizationId.newId().value(),
                command.code().value(),
                command.nameAr(),
                command.nameFr(),
                command.nameEn(),
                command.unitTypeId(),
                command.parentUnitId(),
                command.status() == null
                        ? OrganizationUnitStatus.ACTIVE
                        : command.status(),
                command.validFrom(),
                null,
                now,
                now
        );

        return OrganizationApplicationMapper.toSummary(
                organizationUnitRepositoryPort.save(unit)
        );
    }
}
