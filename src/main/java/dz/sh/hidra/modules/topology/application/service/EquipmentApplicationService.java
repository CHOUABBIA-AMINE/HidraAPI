/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EquipmentApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.service
 *
 * @Description : Application service implementing equipment registration use case.
 *
 */
package dz.sh.hidra.modules.topology.application.service;

import java.util.Objects;

import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.modules.topology.application.command.RegisterEquipmentCommand;
import dz.sh.hidra.modules.topology.application.dto.EquipmentDto;
import dz.sh.hidra.modules.topology.application.port.in.RegisterEquipmentUseCase;
import dz.sh.hidra.modules.topology.application.port.out.EquipmentRepositoryPort;
import dz.sh.hidra.modules.topology.domain.model.Equipment;
import dz.sh.hidra.modules.topology.domain.service.TopologyRegistrationDomainService;

/**
 * Implements topology equipment registration use case.
 *
 * <p>Business role:
 * Coordinates registration of physical component/equipment references attached to topology assets.
 *
 * <p>Architecture role:
 * This service does not implement maintenance, inspection, reliability, telemetry, workflow, or
 * persistence behavior.
 *
 * <p>Validation:
 * Code uniqueness and neutral parent asset rules are enforced before saving.
 *
 * <p>Usage:
 * Wire this class as the implementation for equipment registration use-case port.
 */
public final class EquipmentApplicationService implements RegisterEquipmentUseCase {

    private final EquipmentRepositoryPort equipmentRepository;
    private final TopologyRegistrationDomainService registrationDomainService;

    public EquipmentApplicationService(
            EquipmentRepositoryPort equipmentRepository,
            TopologyRegistrationDomainService registrationDomainService) {

        this.equipmentRepository = Objects.requireNonNull(equipmentRepository, "Equipment repository port must not be null.");
        this.registrationDomainService = Objects.requireNonNull(
                registrationDomainService,
                "Topology registration domain service must not be null.");
    }

    @Override
    public EquipmentDto registerEquipment(RegisterEquipmentCommand command) {
        Objects.requireNonNull(command, "Register equipment command must not be null.");

        if (equipmentRepository.existsByCode(command.code())) {
            throw new BusinessRuleViolationException("Equipment code already exists.");
        }

        Equipment equipment = Equipment.create(
                command.code(),
                command.name(),
                command.equipmentType(),
                command.parentAssetType(),
                command.parentAssetId());

        registrationDomainService.validateEquipmentRegistration(equipment);

        return toDto(equipmentRepository.save(equipment));
    }

    private static EquipmentDto toDto(Equipment equipment) {
        return new EquipmentDto(
                equipment.id().value(),
                equipment.code().value(),
                equipment.name().value(),
                equipment.equipmentType().name(),
                equipment.parentAssetType().name(),
                equipment.parentAssetId(),
                equipment.status().name(),
                equipment.createdAt(),
                equipment.updatedAt());
    }
}
