/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreatePositionService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.service
 *
 * @Description : Application service implementing position creation use case.
 *
 */
package dz.sh.hidra.modules.organization.application.service;

import java.util.Objects;

import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.modules.organization.application.command.CreatePositionCommand;
import dz.sh.hidra.modules.organization.application.dto.PositionDto;
import dz.sh.hidra.modules.organization.application.mapper.OrganizationApplicationMapper;
import dz.sh.hidra.modules.organization.application.port.in.CreatePositionUseCase;
import dz.sh.hidra.modules.organization.application.port.out.DomainEventPublisherPort;
import dz.sh.hidra.modules.organization.application.port.out.PositionRepository;
import dz.sh.hidra.modules.organization.domain.event.PositionCreatedEvent;
import dz.sh.hidra.modules.organization.domain.model.Position;

/**
 * Implements the position creation use case.
 *
 * <p>Business role:
 * This service creates operational positions or functions such as Station Team Leader, Station
 * Boss, Region Director, Gas Flux Director, or Department Chief. These are not identity roles.
 *
 * <p>Architecture role:
 * This application service implements an inbound port and depends only on outbound ports, domain
 * model, events, and application mapping.
 *
 * <p>Validation:
 * The command is required. Position-code uniqueness is checked through the outbound repository
 * port. Position value objects validate code and title formats.
 *
 * <p>Usage:
 * Wire this service later as the CreatePositionUseCase implementation.
 */
public final class CreatePositionService implements CreatePositionUseCase {

    private final PositionRepository positionRepository;
    private final DomainEventPublisherPort domainEventPublisher;
    private final OrganizationApplicationMapper mapper;

    public CreatePositionService(
            PositionRepository positionRepository,
            DomainEventPublisherPort domainEventPublisher,
            OrganizationApplicationMapper mapper) {

        this.positionRepository = Objects.requireNonNull(positionRepository, "Position repository must not be null.");
        this.domainEventPublisher = Objects.requireNonNull(domainEventPublisher, "Domain event publisher must not be null.");
        this.mapper = Objects.requireNonNull(mapper, "Organization application mapper must not be null.");
    }

    @Override
    public PositionDto createPosition(CreatePositionCommand command) {
        Objects.requireNonNull(command, "Create position command must not be null.");

        if (positionRepository.existsByCode(command.code())) {
            throw new BusinessRuleViolationException("Position code already exists.");
        }

        Position position = Position.create(command.code(), command.title(), command.description());
        Position savedPosition = positionRepository.save(position);

        domainEventPublisher.publish(PositionCreatedEvent.occurred(savedPosition.id(), savedPosition.code()));

        return mapper.toDto(savedPosition);
    }
}
