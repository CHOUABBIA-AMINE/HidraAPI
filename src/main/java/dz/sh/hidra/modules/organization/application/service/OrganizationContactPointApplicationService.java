/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationContactPointApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.service
 *
 * @Description : Creates canonical Organization contact points with authoritative target validation.
 *
 */
package dz.sh.hidra.modules.organization.application.service;

import dz.sh.hidra.modules.organization.application.command.CreateOrganizationContactPointCommand;
import dz.sh.hidra.modules.organization.application.port.in.CreateOrganizationContactPointUseCase;
import dz.sh.hidra.modules.organization.application.port.out.OrganizationContactPointRepositoryPort;
import dz.sh.hidra.modules.organization.domain.model.OrganizationContactPoint;
import dz.sh.hidra.modules.organization.domain.value.OrganizationId;
import java.time.Instant;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Application service for canonical Organization contact-point writes.
 *
 * <p>The target validator owns repository-backed employee/unit existence checks. This
 * service owns contact-point creation only; it does not write compatibility contact
 * fields on Employee.</p>
 */
@Service
public final class OrganizationContactPointApplicationService
        implements CreateOrganizationContactPointUseCase {

    private final OrganizationContactPointRepositoryPort contactPointRepository;
    private final OrganizationContactPointTargetValidator targetValidator;

    public OrganizationContactPointApplicationService(
            OrganizationContactPointRepositoryPort contactPointRepository,
            OrganizationContactPointTargetValidator targetValidator
    ) {
        this.contactPointRepository = Objects.requireNonNull(
                contactPointRepository,
                "OrganizationContactPointRepositoryPort must not be null."
        );
        this.targetValidator = Objects.requireNonNull(
                targetValidator,
                "OrganizationContactPointTargetValidator must not be null."
        );
    }

    @Override
    @Transactional
    public String createContactPoint(CreateOrganizationContactPointCommand command) {
        Objects.requireNonNull(command, "Create contact-point command must not be null.");

        Instant now = Instant.now();
        OrganizationContactPoint saved = contactPointRepository.save(
                new OrganizationContactPoint(
                        OrganizationId.newId().value(),
                        command.contactPointType(),
                        targetValidator.validate(command.target()),
                        command.label(),
                        command.value(),
                        command.primaryContact(),
                        command.emergencyContact(),
                        command.active(),
                        now,
                        now
                )
        );
        return saved.id();
    }
}
