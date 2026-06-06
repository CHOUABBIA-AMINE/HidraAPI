/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateOrganizationUnitService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.service
 *
 * @Description : Application service implementing organization unit creation use case.
 *
 */
package dz.sh.hidra.modules.organization.application.service;

import java.util.Objects;

import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.modules.organization.application.command.CreateOrganizationUnitCommand;
import dz.sh.hidra.modules.organization.application.dto.OrganizationUnitDto;
import dz.sh.hidra.modules.organization.application.mapper.OrganizationApplicationMapper;
import dz.sh.hidra.modules.organization.application.port.in.CreateOrganizationUnitUseCase;
import dz.sh.hidra.modules.organization.application.port.out.DomainEventPublisherPort;
import dz.sh.hidra.modules.organization.application.port.out.OrganizationUnitRepository;
import dz.sh.hidra.modules.organization.domain.event.OrganizationUnitCreatedEvent;
import dz.sh.hidra.modules.organization.domain.model.OperationalScopeReference;
import dz.sh.hidra.modules.organization.domain.model.OrganizationUnit;

/**
 * Implements the organization unit creation use case.
 */
public final class CreateOrganizationUnitService implements CreateOrganizationUnitUseCase {

    private final OrganizationUnitRepository organizationUnitRepository;
    private final DomainEventPublisherPort domainEventPublisher;
    private final OrganizationApplicationMapper mapper;

    public CreateOrganizationUnitService(
            OrganizationUnitRepository organizationUnitRepository,
            DomainEventPublisherPort domainEventPublisher,
            OrganizationApplicationMapper mapper) {

        this.organizationUnitRepository = Objects.requireNonNull(
                organizationUnitRepository,
                "Organization unit repository must not be null.");
        this.domainEventPublisher = Objects.requireNonNull(domainEventPublisher, "Domain event publisher must not be null.");
        this.mapper = Objects.requireNonNull(mapper, "Organization application mapper must not be null.");
    }

    @Override
    public OrganizationUnitDto createOrganizationUnit(CreateOrganizationUnitCommand command) {
        Objects.requireNonNull(command, "Create organization unit command must not be null.");

        if (organizationUnitRepository.existsByCode(command.code())) {
            throw new BusinessRuleViolationException("Organization unit code already exists.");
        }

        OperationalScopeReference scopeReference = toOperationalScopeReference(
                command.operationalScopeType(),
                command.operationalScopeId(),
                command.operationalScopeCode(),
                command.operationalScopeName());

        OrganizationUnit organizationUnit = command.type().isStationOrganizationUnit() && scopeReference != null
                ? OrganizationUnit.createStation(command.code(), command.name(), command.parentId(), scopeReference)
                : OrganizationUnit.create(command.code(), command.name(), command.type(), command.parentId(), scopeReference);

        OrganizationUnit savedOrganizationUnit = organizationUnitRepository.save(organizationUnit);
        domainEventPublisher.publish(OrganizationUnitCreatedEvent.occurred(
                savedOrganizationUnit.id(),
                savedOrganizationUnit.code(),
                savedOrganizationUnit.type()));

        return mapper.toDto(savedOrganizationUnit);
    }

    private static OperationalScopeReference toOperationalScopeReference(
            dz.sh.hidra.modules.organization.domain.value.OperationalScopeType type,
            String scopeId,
            String scopeCode,
            String scopeName) {

        if (type == null && scopeCode == null) {
            return null;
        }

        if (type == null || scopeCode == null) {
            throw new BusinessRuleViolationException("Operational scope type and code must be provided together.");
        }

        return new OperationalScopeReference(type, scopeId, scopeCode, scopeName);
    }
}
