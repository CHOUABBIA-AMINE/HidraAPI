/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyConfiguration
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.configuration
 *
 * @Description : Spring bean configuration for the topology module.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dz.sh.hidra.modules.topology.application.port.in.CreateFacilityUseCase;
import dz.sh.hidra.modules.topology.application.port.in.CreatePipelineAppurtenanceUseCase;
import dz.sh.hidra.modules.topology.application.port.in.CreatePipelineSegmentUseCase;
import dz.sh.hidra.modules.topology.application.port.in.CreatePipelineSystemUseCase;
import dz.sh.hidra.modules.topology.application.port.in.CreatePipelineUseCase;
import dz.sh.hidra.modules.topology.application.port.in.CreateTopologyConnectionUseCase;
import dz.sh.hidra.modules.topology.application.port.in.CreateTopologyNodeUseCase;
import dz.sh.hidra.modules.topology.application.port.in.GetFacilityUseCase;
import dz.sh.hidra.modules.topology.application.port.in.GetPipelineAppurtenanceUseCase;
import dz.sh.hidra.modules.topology.application.port.in.GetPipelineSystemUseCase;
import dz.sh.hidra.modules.topology.application.port.in.GetPipelineUseCase;
import dz.sh.hidra.modules.topology.application.port.in.GetTopologyCatalogTypeUseCase;
import dz.sh.hidra.modules.topology.application.port.in.GetTopologyNodeUseCase;
import dz.sh.hidra.modules.topology.application.port.in.ListFacilitiesUseCase;
import dz.sh.hidra.modules.topology.application.port.in.ListPipelineAppurtenancesUseCase;
import dz.sh.hidra.modules.topology.application.port.in.ListPipelineSegmentsUseCase;
import dz.sh.hidra.modules.topology.application.port.in.ListPipelineSystemsUseCase;
import dz.sh.hidra.modules.topology.application.port.in.ListPipelinesUseCase;
import dz.sh.hidra.modules.topology.application.port.in.ListTopologyCatalogTypesUseCase;
import dz.sh.hidra.modules.topology.application.port.in.ListTopologyConnectionsUseCase;
import dz.sh.hidra.modules.topology.application.port.in.ListTopologyNodesUseCase;
import dz.sh.hidra.modules.topology.application.port.in.RegisterEquipmentUseCase;
import dz.sh.hidra.modules.topology.application.port.in.ResolveTopologyCatalogTypeUseCase;
import dz.sh.hidra.modules.topology.application.port.out.EquipmentRepositoryPort;
import dz.sh.hidra.modules.topology.application.port.out.FacilityRepositoryPort;
import dz.sh.hidra.modules.topology.application.port.out.PipelineAppurtenanceRepositoryPort;
import dz.sh.hidra.modules.topology.application.port.out.PipelineRepositoryPort;
import dz.sh.hidra.modules.topology.application.port.out.PipelineSegmentRepositoryPort;
import dz.sh.hidra.modules.topology.application.port.out.PipelineSystemRepositoryPort;
import dz.sh.hidra.modules.topology.application.port.out.TopologyCatalogRepositoryPort;
import dz.sh.hidra.modules.topology.application.port.out.TopologyConnectionRepositoryPort;
import dz.sh.hidra.modules.topology.application.port.out.TopologyNodeRepositoryPort;
import dz.sh.hidra.modules.topology.application.service.EquipmentApplicationService;
import dz.sh.hidra.modules.topology.application.service.FacilityApplicationService;
import dz.sh.hidra.modules.topology.application.service.PipelineApplicationService;
import dz.sh.hidra.modules.topology.application.service.PipelineAppurtenanceApplicationService;
import dz.sh.hidra.modules.topology.application.service.PipelineSegmentApplicationService;
import dz.sh.hidra.modules.topology.application.service.PipelineSystemApplicationService;
import dz.sh.hidra.modules.topology.application.service.TopologyCatalogApplicationService;
import dz.sh.hidra.modules.topology.application.service.TopologyConnectionApplicationService;
import dz.sh.hidra.modules.topology.application.service.TopologyNodeApplicationService;
import dz.sh.hidra.modules.topology.domain.policy.FacilityTopologyPolicy;
import dz.sh.hidra.modules.topology.domain.policy.PipelineAppurtenancePolicy;
import dz.sh.hidra.modules.topology.domain.policy.TopologyAssetStatusPolicy;
import dz.sh.hidra.modules.topology.domain.policy.TopologyConnectivityPolicy;
import dz.sh.hidra.modules.topology.domain.service.PipelineAppurtenanceDomainService;
import dz.sh.hidra.modules.topology.domain.service.TopologyConnectivityDomainService;
import dz.sh.hidra.modules.topology.domain.service.TopologyRegistrationDomainService;
import dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.EquipmentRepositoryAdapter;
import dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.FacilityRepositoryAdapter;
import dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.PipelineAppurtenanceRepositoryAdapter;
import dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.PipelineRepositoryAdapter;
import dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.PipelineSegmentRepositoryAdapter;
import dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.PipelineSystemRepositoryAdapter;
import dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.TopologyCatalogRepositoryAdapter;
import dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.TopologyConnectionRepositoryAdapter;
import dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.TopologyNodeRepositoryAdapter;
import dz.sh.hidra.modules.topology.infrastructure.persistence.mapper.TopologyCatalogPersistenceMapper;
import dz.sh.hidra.modules.topology.infrastructure.persistence.mapper.TopologyPersistenceMapper;
import dz.sh.hidra.modules.topology.infrastructure.persistence.repository.EquipmentJpaRepository;
import dz.sh.hidra.modules.topology.infrastructure.persistence.repository.FacilityJpaRepository;
import dz.sh.hidra.modules.topology.infrastructure.persistence.repository.PipelineAppurtenanceJpaRepository;
import dz.sh.hidra.modules.topology.infrastructure.persistence.repository.PipelineJpaRepository;
import dz.sh.hidra.modules.topology.infrastructure.persistence.repository.PipelineSegmentJpaRepository;
import dz.sh.hidra.modules.topology.infrastructure.persistence.repository.PipelineSystemJpaRepository;
import dz.sh.hidra.modules.topology.infrastructure.persistence.repository.TopologyCatalogJpaRepository;
import dz.sh.hidra.modules.topology.infrastructure.persistence.repository.TopologyCatalogTranslationJpaRepository;
import dz.sh.hidra.modules.topology.infrastructure.persistence.repository.TopologyConnectionJpaRepository;
import dz.sh.hidra.modules.topology.infrastructure.persistence.repository.TopologyNodeJpaRepository;
import jakarta.persistence.EntityManager;

/**
 * Wires topology module beans.
 *
 * <p>Business role:
 * Assembles physical topology use cases, domain services, policies, persistence adapters, and
 * multilingual topology catalog services for pipeline systems, pipelines, facilities, topology nodes,
 * pipeline segments, pipeline appurtenances, topology connections, and equipment.
 *
 * <p>Architecture role:
 * Infrastructure configuration only. It connects inbound ports, outbound ports, domain policies,
 * domain services, persistence mappers, and repository adapters. It does not implement business
 * rules.
 *
 * <p>Validation:
 * Domain validation remains in topology value objects, domain models, policies, and domain services.
 *
 * <p>Usage:
 * Keep this class limited to bean wiring. Do not add REST endpoints, security rules, migrations,
 * identity implementation, organization implementation, measurement logic, flow calculations, risk
 * scoring, workflow behavior, or business logic here.
 */
@Configuration
public class TopologyConfiguration {

    @Bean
    TopologyPersistenceMapper topologyPersistenceMapper() {
        return new TopologyPersistenceMapper();
    }

    @Bean
    TopologyCatalogPersistenceMapper topologyCatalogPersistenceMapper() {
        return new TopologyCatalogPersistenceMapper();
    }

    @Bean
    TopologyCatalogJpaRepository topologyCatalogJpaRepository(EntityManager entityManager) {
        return new TopologyCatalogJpaRepository(entityManager);
    }

    @Bean
    TopologyCatalogTranslationJpaRepository topologyCatalogTranslationJpaRepository(EntityManager entityManager) {
        return new TopologyCatalogTranslationJpaRepository(entityManager);
    }

    @Bean
    TopologyCatalogRepositoryPort topologyCatalogRepositoryPort(
            TopologyCatalogJpaRepository catalogRepository,
            TopologyCatalogTranslationJpaRepository translationRepository,
            TopologyCatalogPersistenceMapper mapper) {

        return new TopologyCatalogRepositoryAdapter(catalogRepository, translationRepository, mapper);
    }

    @Bean
    PipelineSystemRepositoryPort pipelineSystemRepositoryPort(
            PipelineSystemJpaRepository repository,
            TopologyPersistenceMapper mapper) {

        return new PipelineSystemRepositoryAdapter(repository, mapper);
    }

    @Bean
    PipelineRepositoryPort pipelineRepositoryPort(
            PipelineJpaRepository repository,
            TopologyPersistenceMapper mapper) {

        return new PipelineRepositoryAdapter(repository, mapper);
    }

    @Bean
    FacilityRepositoryPort facilityRepositoryPort(
            FacilityJpaRepository repository,
            TopologyPersistenceMapper mapper) {

        return new FacilityRepositoryAdapter(repository, mapper);
    }

    @Bean
    TopologyNodeRepositoryPort topologyNodeRepositoryPort(
            TopologyNodeJpaRepository repository,
            TopologyPersistenceMapper mapper) {

        return new TopologyNodeRepositoryAdapter(repository, mapper);
    }

    @Bean
    PipelineSegmentRepositoryPort pipelineSegmentRepositoryPort(
            PipelineSegmentJpaRepository repository,
            TopologyPersistenceMapper mapper) {

        return new PipelineSegmentRepositoryAdapter(repository, mapper);
    }

    @Bean
    PipelineAppurtenanceRepositoryPort pipelineAppurtenanceRepositoryPort(
            PipelineAppurtenanceJpaRepository repository,
            TopologyPersistenceMapper mapper) {

        return new PipelineAppurtenanceRepositoryAdapter(repository, mapper);
    }

    @Bean
    TopologyConnectionRepositoryPort topologyConnectionRepositoryPort(
            TopologyConnectionJpaRepository repository,
            TopologyPersistenceMapper mapper) {

        return new TopologyConnectionRepositoryAdapter(repository, mapper);
    }

    @Bean
    EquipmentRepositoryPort equipmentRepositoryPort(
            EquipmentJpaRepository repository,
            TopologyPersistenceMapper mapper) {

        return new EquipmentRepositoryAdapter(repository, mapper);
    }

    @Bean
    TopologyAssetStatusPolicy topologyAssetStatusPolicy() {
        return new TopologyAssetStatusPolicy();
    }

    @Bean
    TopologyConnectivityPolicy topologyConnectivityPolicy() {
        return new TopologyConnectivityPolicy();
    }

    @Bean
    FacilityTopologyPolicy facilityTopologyPolicy() {
        return new FacilityTopologyPolicy();
    }

    @Bean
    PipelineAppurtenancePolicy pipelineAppurtenancePolicy() {
        return new PipelineAppurtenancePolicy();
    }

    @Bean
    TopologyRegistrationDomainService topologyRegistrationDomainService(
            TopologyAssetStatusPolicy statusPolicy,
            TopologyConnectivityPolicy connectivityPolicy,
            FacilityTopologyPolicy facilityPolicy,
            PipelineAppurtenancePolicy appurtenancePolicy) {

        return new TopologyRegistrationDomainService(
                statusPolicy,
                connectivityPolicy,
                facilityPolicy,
                appurtenancePolicy);
    }

    @Bean
    TopologyConnectivityDomainService topologyConnectivityDomainService(
            TopologyConnectivityPolicy connectivityPolicy) {

        return new TopologyConnectivityDomainService(connectivityPolicy);
    }

    @Bean
    PipelineAppurtenanceDomainService pipelineAppurtenanceDomainService(
            PipelineAppurtenancePolicy appurtenancePolicy,
            TopologyConnectivityPolicy connectivityPolicy,
            TopologyAssetStatusPolicy statusPolicy) {

        return new PipelineAppurtenanceDomainService(appurtenancePolicy, connectivityPolicy, statusPolicy);
    }

    @Bean
    PipelineSystemApplicationService pipelineSystemApplicationService(
            PipelineSystemRepositoryPort pipelineSystemRepository) {

        return new PipelineSystemApplicationService(pipelineSystemRepository);
    }

    @Bean
    CreatePipelineSystemUseCase createPipelineSystemUseCase(
            PipelineSystemApplicationService service) {

        return service;
    }

    @Bean
    GetPipelineSystemUseCase getPipelineSystemUseCase(
            PipelineSystemApplicationService service) {

        return service;
    }

    @Bean
    ListPipelineSystemsUseCase listPipelineSystemsUseCase(
            PipelineSystemApplicationService service) {

        return service;
    }

    @Bean
    PipelineApplicationService pipelineApplicationService(
            PipelineRepositoryPort pipelineRepository,
            PipelineSystemRepositoryPort pipelineSystemRepository,
            TopologyRegistrationDomainService registrationDomainService) {

        return new PipelineApplicationService(
                pipelineRepository,
                pipelineSystemRepository,
                registrationDomainService);
    }

    @Bean
    CreatePipelineUseCase createPipelineUseCase(PipelineApplicationService service) {
        return service;
    }

    @Bean
    GetPipelineUseCase getPipelineUseCase(PipelineApplicationService service) {
        return service;
    }

    @Bean
    ListPipelinesUseCase listPipelinesUseCase(PipelineApplicationService service) {
        return service;
    }

    @Bean
    FacilityApplicationService facilityApplicationService(
            FacilityRepositoryPort facilityRepository,
            TopologyRegistrationDomainService registrationDomainService) {

        return new FacilityApplicationService(facilityRepository, registrationDomainService);
    }

    @Bean
    CreateFacilityUseCase createFacilityUseCase(FacilityApplicationService service) {
        return service;
    }

    @Bean
    GetFacilityUseCase getFacilityUseCase(FacilityApplicationService service) {
        return service;
    }

    @Bean
    ListFacilitiesUseCase listFacilitiesUseCase(FacilityApplicationService service) {
        return service;
    }

    @Bean
    TopologyNodeApplicationService topologyNodeApplicationService(
            TopologyNodeRepositoryPort topologyNodeRepository,
            FacilityRepositoryPort facilityRepository,
            TopologyRegistrationDomainService registrationDomainService) {

        return new TopologyNodeApplicationService(
                topologyNodeRepository,
                facilityRepository,
                registrationDomainService);
    }

    @Bean
    CreateTopologyNodeUseCase createTopologyNodeUseCase(TopologyNodeApplicationService service) {
        return service;
    }

    @Bean
    GetTopologyNodeUseCase getTopologyNodeUseCase(TopologyNodeApplicationService service) {
        return service;
    }

    @Bean
    ListTopologyNodesUseCase listTopologyNodesUseCase(TopologyNodeApplicationService service) {
        return service;
    }

    @Bean
    PipelineSegmentApplicationService pipelineSegmentApplicationService(
            PipelineSegmentRepositoryPort pipelineSegmentRepository,
            PipelineRepositoryPort pipelineRepository,
            TopologyNodeRepositoryPort topologyNodeRepository,
            TopologyRegistrationDomainService registrationDomainService) {

        return new PipelineSegmentApplicationService(
                pipelineSegmentRepository,
                pipelineRepository,
                topologyNodeRepository,
                registrationDomainService);
    }

    @Bean
    CreatePipelineSegmentUseCase createPipelineSegmentUseCase(PipelineSegmentApplicationService service) {
        return service;
    }

    @Bean
    ListPipelineSegmentsUseCase listPipelineSegmentsUseCase(PipelineSegmentApplicationService service) {
        return service;
    }

    @Bean
    PipelineAppurtenanceApplicationService pipelineAppurtenanceApplicationService(
            PipelineAppurtenanceRepositoryPort appurtenanceRepository,
            PipelineRepositoryPort pipelineRepository,
            TopologyNodeRepositoryPort topologyNodeRepository,
            PipelineAppurtenanceDomainService appurtenanceDomainService) {

        return new PipelineAppurtenanceApplicationService(
                appurtenanceRepository,
                pipelineRepository,
                topologyNodeRepository,
                appurtenanceDomainService);
    }

    @Bean
    CreatePipelineAppurtenanceUseCase createPipelineAppurtenanceUseCase(
            PipelineAppurtenanceApplicationService service) {

        return service;
    }

    @Bean
    GetPipelineAppurtenanceUseCase getPipelineAppurtenanceUseCase(
            PipelineAppurtenanceApplicationService service) {

        return service;
    }

    @Bean
    ListPipelineAppurtenancesUseCase listPipelineAppurtenancesUseCase(
            PipelineAppurtenanceApplicationService service) {

        return service;
    }

    @Bean
    TopologyConnectionApplicationService topologyConnectionApplicationService(
            TopologyConnectionRepositoryPort topologyConnectionRepository,
            TopologyNodeRepositoryPort topologyNodeRepository,
            TopologyRegistrationDomainService registrationDomainService) {

        return new TopologyConnectionApplicationService(
                topologyConnectionRepository,
                topologyNodeRepository,
                registrationDomainService);
    }

    @Bean
    CreateTopologyConnectionUseCase createTopologyConnectionUseCase(
            TopologyConnectionApplicationService service) {

        return service;
    }

    @Bean
    ListTopologyConnectionsUseCase listTopologyConnectionsUseCase(
            TopologyConnectionApplicationService service) {

        return service;
    }

    @Bean
    EquipmentApplicationService equipmentApplicationService(
            EquipmentRepositoryPort equipmentRepository,
            TopologyRegistrationDomainService registrationDomainService) {

        return new EquipmentApplicationService(equipmentRepository, registrationDomainService);
    }

    @Bean
    RegisterEquipmentUseCase registerEquipmentUseCase(EquipmentApplicationService service) {
        return service;
    }

    @Bean
    TopologyCatalogApplicationService topologyCatalogApplicationService(
            TopologyCatalogRepositoryPort topologyCatalogRepository) {

        return new TopologyCatalogApplicationService(topologyCatalogRepository);
    }

    @Bean
    GetTopologyCatalogTypeUseCase getTopologyCatalogTypeUseCase(TopologyCatalogApplicationService service) {
        return service;
    }

    @Bean
    ListTopologyCatalogTypesUseCase listTopologyCatalogTypesUseCase(TopologyCatalogApplicationService service) {
        return service;
    }

    @Bean
    ResolveTopologyCatalogTypeUseCase resolveTopologyCatalogTypeUseCase(TopologyCatalogApplicationService service) {
        return service;
    }
}
