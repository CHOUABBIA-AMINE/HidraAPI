/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateOrganizationUnitServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.service
 *
 * @Description : Unit tests for CreateOrganizationUnitService.
 *
 */
package dz.sh.hidra.modules.organization.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import dz.sh.hidra.kernel.domain.event.DomainEvent;
import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.modules.organization.application.command.CreateOrganizationUnitCommand;
import dz.sh.hidra.modules.organization.application.dto.OrganizationUnitDto;
import dz.sh.hidra.modules.organization.application.mapper.OrganizationApplicationMapper;
import dz.sh.hidra.modules.organization.application.port.out.DomainEventPublisherPort;
import dz.sh.hidra.modules.organization.application.port.out.OrganizationUnitRepository;
import dz.sh.hidra.modules.organization.domain.model.OperationalScopeReference;
import dz.sh.hidra.modules.organization.domain.model.OrganizationUnit;
import dz.sh.hidra.modules.organization.domain.value.OperationalScopeType;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitCode;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitId;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitName;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitTypeReference;

/**
 * Tests the organization unit creation application service.
 */
class CreateOrganizationUnitServiceTest {

    @Test
    void shouldCreateStationOrganizationUnitWithOperationalScopeAndPublishEvent() {
        InMemoryOrganizationUnitRepository organizationUnitRepository = new InMemoryOrganizationUnitRepository();
        CapturingDomainEventPublisher eventPublisher = new CapturingDomainEventPublisher();
        CreateOrganizationUnitService service = new CreateOrganizationUnitService(
                organizationUnitRepository,
                eventPublisher,
                new OrganizationApplicationMapper());

        OrganizationUnitDto createdUnit = service.createOrganizationUnit(new CreateOrganizationUnitCommand(
                OrganizationUnitCode.of("CS_EAST_01"),
                OrganizationUnitName.of("Compression Station East 01"),
                OrganizationUnitTypeReference.STATION,
                null,
                OperationalScopeType.TOPOLOGY_COMPRESSION_STATION,
                "station-001",
                "CS-EAST-01",
                "Compression Station East 01"));

        assertEquals("CS_EAST_01", createdUnit.code());
        assertEquals("STATION", createdUnit.typeCode());
        assertEquals("organization-out-station", createdUnit.typeId());
        assertEquals("TOPOLOGY_COMPRESSION_STATION", createdUnit.operationalScopeType());
        assertEquals(1, organizationUnitRepository.savedOrganizationUnits.size());
        assertEquals(1, eventPublisher.events.size());
        assertEquals("organization.unit.created", eventPublisher.events.get(0).eventType());
    }

    @Test
    void shouldRejectDuplicateOrganizationUnitCode() {
        InMemoryOrganizationUnitRepository organizationUnitRepository = new InMemoryOrganizationUnitRepository();
        organizationUnitRepository.save(OrganizationUnit.create(
                OrganizationUnitCode.of("REGION_EAST"),
                OrganizationUnitName.of("Operational East Region"),
                OrganizationUnitTypeReference.REGION,
                null,
                null));

        CreateOrganizationUnitService service = new CreateOrganizationUnitService(
                organizationUnitRepository,
                new CapturingDomainEventPublisher(),
                new OrganizationApplicationMapper());

        CreateOrganizationUnitCommand command = new CreateOrganizationUnitCommand(
                OrganizationUnitCode.of("REGION_EAST"),
                OrganizationUnitName.of("Duplicate Region"),
                OrganizationUnitTypeReference.REGION,
                null,
                null,
                null,
                null,
                null);

        assertThrows(BusinessRuleViolationException.class, () -> service.createOrganizationUnit(command));
    }

    @Test
    void shouldRejectIncompleteOperationalScope() {
        CreateOrganizationUnitService service = new CreateOrganizationUnitService(
                new InMemoryOrganizationUnitRepository(),
                new CapturingDomainEventPublisher(),
                new OrganizationApplicationMapper());

        CreateOrganizationUnitCommand command = new CreateOrganizationUnitCommand(
                OrganizationUnitCode.of("CS_EAST_02"),
                OrganizationUnitName.of("Compression Station East 02"),
                OrganizationUnitTypeReference.STATION,
                null,
                OperationalScopeType.TOPOLOGY_COMPRESSION_STATION,
                "station-002",
                null,
                "Compression Station East 02");

        assertThrows(BusinessRuleViolationException.class, () -> service.createOrganizationUnit(command));
    }

    private static final class InMemoryOrganizationUnitRepository implements OrganizationUnitRepository {

        private final List<OrganizationUnit> savedOrganizationUnits = new ArrayList<>();

        @Override
        public OrganizationUnit save(OrganizationUnit organizationUnit) {
            savedOrganizationUnits.removeIf(existing -> existing.id().equals(organizationUnit.id()));
            savedOrganizationUnits.add(organizationUnit);
            return organizationUnit;
        }

        @Override
        public Optional<OrganizationUnit> findById(OrganizationUnitId id) {
            return savedOrganizationUnits.stream().filter(unit -> unit.id().equals(id)).findFirst();
        }

        @Override
        public Optional<OrganizationUnit> findByCode(OrganizationUnitCode code) {
            return savedOrganizationUnits.stream().filter(unit -> unit.code().equals(code)).findFirst();
        }

        @Override
        public boolean existsByCode(OrganizationUnitCode code) {
            return findByCode(code).isPresent();
        }

        @Override
        public List<OrganizationUnit> findChildrenOf(OrganizationUnitId parentId) {
            return savedOrganizationUnits.stream()
                    .filter(unit -> unit.parentId().map(parentId::equals).orElse(false))
                    .toList();
        }

        @Override
        public List<OrganizationUnit> findByType(OrganizationUnitTypeReference type) {
            return savedOrganizationUnits.stream()
                    .filter(unit -> unit.type().equals(type))
                    .toList();
        }

        @Override
        public List<OrganizationUnit> findByOperationalScope(OperationalScopeReference operationalScopeReference) {
            return savedOrganizationUnits.stream()
                    .filter(unit -> unit.operationalScopeReference().map(operationalScopeReference::equals).orElse(false))
                    .toList();
        }
    }

    private static final class CapturingDomainEventPublisher implements DomainEventPublisherPort {

        private final List<DomainEvent> events = new ArrayList<>();

        @Override
        public void publish(DomainEvent domainEvent) {
            events.add(domainEvent);
        }
    }
}
