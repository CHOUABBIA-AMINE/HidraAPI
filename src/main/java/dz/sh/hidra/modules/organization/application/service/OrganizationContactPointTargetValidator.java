/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationContactPointTargetValidator
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-27
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.service
 *
 * @Description : Validates that a typed Organization contact-point target exists in its owning repository.
 *
 */
package dz.sh.hidra.modules.organization.application.service;

import dz.sh.hidra.modules.organization.application.port.out.EmployeeRepositoryPort;
import dz.sh.hidra.modules.organization.application.port.out.OrganizationUnitRepositoryPort;
import dz.sh.hidra.modules.organization.domain.value.ContactPointTargetReference;

import java.util.Objects;
import org.springframework.stereotype.Component;

/**
 * Validates existence of Organization-owned contact targets before a write use case persists contact data.
 *
 * <p>Business role: prevents contact-point records from being attached to nonexistent
 * employees or organization units.</p>
 *
 * <p>Architecture role: application-layer validator using only existing outbound
 * Organization repository ports. It does not introduce identity/topology dependencies
 * and does not create a new REST surface.</p>
 *
 * <p>Validation: target type/id shape is already guaranteed by the domain value object;
 * this class verifies current existence in the authoritative Organization repository.
 * No employee/unit lifecycle eligibility rule is imposed because none is currently
 * established for contact points.</p>
 *
 * <p>Usage: future/current contact-point write application services must invoke this
 * validator before repository save. Read paths do not require it.</p>
 */
@Component
public final class OrganizationContactPointTargetValidator {

    private final EmployeeRepositoryPort employeeRepository;
    private final OrganizationUnitRepositoryPort organizationUnitRepository;

    public OrganizationContactPointTargetValidator(
            EmployeeRepositoryPort employeeRepository,
            OrganizationUnitRepositoryPort organizationUnitRepository
    ) {
        this.employeeRepository = Objects.requireNonNull(
                employeeRepository,
                "EmployeeRepositoryPort must not be null."
        );
        this.organizationUnitRepository = Objects.requireNonNull(
                organizationUnitRepository,
                "OrganizationUnitRepositoryPort must not be null."
        );
    }

    /**
     * Validates that the referenced Organization target currently exists.
     *
     * @param target governed target reference
     * @return the same validated canonical target reference
     * @throws IllegalArgumentException when the referenced target does not exist
     */
    public ContactPointTargetReference validate(ContactPointTargetReference target) {
        Objects.requireNonNull(target, "Contact-point target must not be null.");

        boolean exists = switch (target.type()) {
            case EMPLOYEE -> employeeRepository.findById(target.targetId()).isPresent();
            case ORGANIZATION_UNIT -> organizationUnitRepository.findById(target.targetId()).isPresent();
        };

        if (!exists) {
            throw new IllegalArgumentException(
                    "Organization contact-point target not found: "
                            + target.type() + "/" + target.targetId()
            );
        }

        return target;
    }
}
