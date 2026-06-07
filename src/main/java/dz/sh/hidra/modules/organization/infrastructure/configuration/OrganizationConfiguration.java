/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationConfiguration
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.configuration
 *
 * @Description : Spring bean configuration for the organization module.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.configuration;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dz.sh.hidra.modules.organization.application.mapper.OrganizationApplicationMapper;
import dz.sh.hidra.modules.organization.application.port.in.AssignEmployeeToUnitUseCase;
import dz.sh.hidra.modules.organization.application.port.in.CreateEmployeeUseCase;
import dz.sh.hidra.modules.organization.application.port.in.CreateOrganizationUnitUseCase;
import dz.sh.hidra.modules.organization.application.port.in.CreatePositionUseCase;
import dz.sh.hidra.modules.organization.application.port.in.GetEmployeeUseCase;
import dz.sh.hidra.modules.organization.application.port.in.GetOrganizationUnitUseCase;
import dz.sh.hidra.modules.organization.application.port.in.ListEmployeesUseCase;
import dz.sh.hidra.modules.organization.application.port.in.ListOrganizationUnitsUseCase;
import dz.sh.hidra.modules.organization.application.port.in.SetEmployeeReportingLineUseCase;
import dz.sh.hidra.modules.organization.application.port.out.DomainEventPublisherPort;
import dz.sh.hidra.modules.organization.application.port.out.EmployeeRepository;
import dz.sh.hidra.modules.organization.application.port.out.OrganizationUnitRepository;
import dz.sh.hidra.modules.organization.application.port.out.PositionRepository;
import dz.sh.hidra.modules.organization.application.service.AssignEmployeeToUnitService;
import dz.sh.hidra.modules.organization.application.service.CreateEmployeeService;
import dz.sh.hidra.modules.organization.application.service.CreateOrganizationUnitService;
import dz.sh.hidra.modules.organization.application.service.CreatePositionService;
import dz.sh.hidra.modules.organization.application.service.GetEmployeeService;
import dz.sh.hidra.modules.organization.application.service.GetOrganizationUnitService;
import dz.sh.hidra.modules.organization.application.service.ListEmployeesService;
import dz.sh.hidra.modules.organization.application.service.ListOrganizationUnitsService;
import dz.sh.hidra.modules.organization.application.service.SetEmployeeReportingLineService;
import dz.sh.hidra.modules.organization.domain.model.OperationalScopeReference;
import dz.sh.hidra.modules.organization.domain.model.OrganizationUnit;
import dz.sh.hidra.modules.organization.domain.policy.EmployeeAssignmentPolicy;
import dz.sh.hidra.modules.organization.domain.policy.EmployeeLifecyclePolicy;
import dz.sh.hidra.modules.organization.domain.policy.OrganizationHierarchyPolicy;
import dz.sh.hidra.modules.organization.domain.policy.ReportingLinePolicy;
import dz.sh.hidra.modules.organization.domain.repository.OrganizationUnitDomainRepository;
import dz.sh.hidra.modules.organization.domain.service.EmployeeAssignmentDomainService;
import dz.sh.hidra.modules.organization.domain.service.OrganizationHierarchyDomainService;
import dz.sh.hidra.modules.organization.domain.service.ReportingLineDomainService;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitCode;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitId;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitTypeReference;
import dz.sh.hidra.modules.organization.infrastructure.adapter.NoOpDomainEventPublisherAdapter;
import dz.sh.hidra.modules.organization.infrastructure.persistence.mapper.OrganizationPersistenceMapper;
import dz.sh.hidra.modules.organization.infrastructure.persistence.repository.EmployeeJpaRepository;
import dz.sh.hidra.modules.organization.infrastructure.persistence.repository.EmployeeRepositoryAdapter;
import dz.sh.hidra.modules.organization.infrastructure.persistence.repository.OrganizationUnitJpaRepository;
import dz.sh.hidra.modules.organization.infrastructure.persistence.repository.OrganizationUnitRepositoryAdapter;
import dz.sh.hidra.modules.organization.infrastructure.persistence.repository.PositionJpaRepository;
import dz.sh.hidra.modules.organization.infrastructure.persistence.repository.PositionRepositoryAdapter;

/**
 * Wires organization module beans.
 */
@Configuration
public class OrganizationConfiguration {

    @Bean
    OrganizationApplicationMapper organizationApplicationMapper() {
        return new OrganizationApplicationMapper();
    }

    @Bean
    OrganizationPersistenceMapper organizationPersistenceMapper() {
        return new OrganizationPersistenceMapper();
    }

    @Bean
    DomainEventPublisherPort organizationDomainEventPublisherPort() {
        return new NoOpDomainEventPublisherAdapter();
    }

    @Bean
    EmployeeRepository employeeRepository(
            EmployeeJpaRepository employeeJpaRepository,
            OrganizationPersistenceMapper mapper) {
        return new EmployeeRepositoryAdapter(employeeJpaRepository, mapper);
    }

    @Bean
    OrganizationUnitRepository organizationUnitRepository(
            OrganizationUnitJpaRepository organizationUnitJpaRepository,
            OrganizationPersistenceMapper mapper) {
        return new OrganizationUnitRepositoryAdapter(organizationUnitJpaRepository, mapper);
    }

    @Bean
    PositionRepository positionRepository(
            PositionJpaRepository positionJpaRepository,
            OrganizationPersistenceMapper mapper) {
        return new PositionRepositoryAdapter(positionJpaRepository, mapper);
    }

    @Bean
    OrganizationUnitDomainRepository organizationUnitDomainRepository(
            OrganizationUnitRepository organizationUnitRepository) {
        return new OrganizationUnitDomainRepositoryBridge(organizationUnitRepository);
    }

    @Bean
    OrganizationHierarchyPolicy organizationHierarchyPolicy() {
        return new OrganizationHierarchyPolicy();
    }

    @Bean
    EmployeeLifecyclePolicy employeeLifecyclePolicy() {
        return new EmployeeLifecyclePolicy();
    }

    @Bean
    EmployeeAssignmentPolicy employeeAssignmentPolicy() {
        return new EmployeeAssignmentPolicy();
    }

    @Bean
    ReportingLinePolicy reportingLinePolicy() {
        return new ReportingLinePolicy();
    }

    @Bean
    OrganizationHierarchyDomainService organizationHierarchyDomainService(
            OrganizationUnitDomainRepository repository,
            OrganizationHierarchyPolicy policy) {
        return new OrganizationHierarchyDomainService(repository, policy);
    }

    @Bean
    EmployeeAssignmentDomainService employeeAssignmentDomainService(EmployeeAssignmentPolicy policy) {
        return new EmployeeAssignmentDomainService(policy);
    }

    @Bean
    ReportingLineDomainService reportingLineDomainService(ReportingLinePolicy policy) {
        return new ReportingLineDomainService(policy);
    }

    @Bean
    CreateEmployeeUseCase createEmployeeUseCase(
            EmployeeRepository employeeRepository,
            DomainEventPublisherPort domainEventPublisher,
            OrganizationApplicationMapper mapper) {
        return new CreateEmployeeService(employeeRepository, domainEventPublisher, mapper);
    }

    @Bean
    GetEmployeeUseCase getEmployeeUseCase(
            EmployeeRepository employeeRepository,
            OrganizationApplicationMapper mapper) {
        return new GetEmployeeService(employeeRepository, mapper);
    }

    @Bean
    ListEmployeesUseCase listEmployeesUseCase(
            EmployeeRepository employeeRepository,
            OrganizationApplicationMapper mapper) {
        return new ListEmployeesService(employeeRepository, mapper);
    }

    @Bean
    CreateOrganizationUnitUseCase createOrganizationUnitUseCase(
            OrganizationUnitRepository organizationUnitRepository,
            DomainEventPublisherPort domainEventPublisher,
            OrganizationApplicationMapper mapper) {
        return new CreateOrganizationUnitService(organizationUnitRepository, domainEventPublisher, mapper);
    }

    @Bean
    GetOrganizationUnitUseCase getOrganizationUnitUseCase(
            OrganizationUnitRepository organizationUnitRepository,
            OrganizationApplicationMapper mapper) {
        return new GetOrganizationUnitService(organizationUnitRepository, mapper);
    }

    @Bean
    ListOrganizationUnitsUseCase listOrganizationUnitsUseCase(
            OrganizationUnitRepository organizationUnitRepository,
            OrganizationApplicationMapper mapper) {
        return new ListOrganizationUnitsService(organizationUnitRepository, mapper);
    }

    @Bean
    CreatePositionUseCase createPositionUseCase(
            PositionRepository positionRepository,
            DomainEventPublisherPort domainEventPublisher,
            OrganizationApplicationMapper mapper) {
        return new CreatePositionService(positionRepository, domainEventPublisher, mapper);
    }

    @Bean
    AssignEmployeeToUnitUseCase assignEmployeeToUnitUseCase(
            EmployeeRepository employeeRepository,
            OrganizationUnitRepository organizationUnitRepository,
            PositionRepository positionRepository,
            EmployeeAssignmentDomainService domainService,
            DomainEventPublisherPort domainEventPublisher,
            OrganizationApplicationMapper mapper) {
        return new AssignEmployeeToUnitService(
                employeeRepository,
                organizationUnitRepository,
                positionRepository,
                domainService,
                domainEventPublisher,
                mapper);
    }

    @Bean
    SetEmployeeReportingLineUseCase setEmployeeReportingLineUseCase(
            EmployeeRepository employeeRepository,
            ReportingLineDomainService domainService,
            DomainEventPublisherPort domainEventPublisher,
            OrganizationApplicationMapper mapper) {
        return new SetEmployeeReportingLineService(employeeRepository, domainService, domainEventPublisher, mapper);
    }

    private static final class OrganizationUnitDomainRepositoryBridge implements OrganizationUnitDomainRepository {

        private final OrganizationUnitRepository organizationUnitRepository;

        private OrganizationUnitDomainRepositoryBridge(OrganizationUnitRepository organizationUnitRepository) {
            this.organizationUnitRepository = organizationUnitRepository;
        }

        @Override
        public Optional<OrganizationUnit> findById(OrganizationUnitId id) {
            return organizationUnitRepository.findById(id);
        }

        @Override
        public Optional<OrganizationUnit> findByCode(OrganizationUnitCode code) {
            return organizationUnitRepository.findByCode(code);
        }

        @Override
        public boolean existsByCode(OrganizationUnitCode code) {
            return organizationUnitRepository.existsByCode(code);
        }

        @Override
        public List<OrganizationUnit> findChildrenOf(OrganizationUnitId parentId) {
            return organizationUnitRepository.findChildrenOf(parentId);
        }

        @Override
        public List<OrganizationUnit> findByType(OrganizationUnitTypeReference type) {
            return organizationUnitRepository.findByType(type);
        }

        @Override
        public List<OrganizationUnit> findByOperationalScope(OperationalScopeReference operationalScopeReference) {
            return organizationUnitRepository.findByOperationalScope(operationalScopeReference);
        }
    }
}
