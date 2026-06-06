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
    public TopologyPersistenceMapper topologyPersistenceMapper() {
        return new TopologyPersistenceMapper();
    }

    @Bean
    public TopologyCatalogPersistenceMapper topologyCatalogPersistenceMapper() {
        return new TopologyCatalogPersistenceMapper();
    }

    @Bean
    public TopologyCatalogJpaRepository topologyCatalogJpaRepository(EntityManager entityManager) {
        return new TopologyCatalogJpaRepository(entityManager);
    }

    @Bean
    public TopologyCatalogTranslationJpaRepository topologyCatalogTranslationJpaRepository(EntityManager entityManager) {
        return new TopologyCatalogTranslationJpaRepository(entityManager);
    }

    @Bean
    public TopologyCatalogRepositoryPort topologyCatalogRepositoryPort(
            TopologyCatalogJpaRepository catalogRepository,
            TopologyCatalogTranslationJpaRepository translationRepository,
            TopologyCatalogPersistenceMapper mapper) {

        return new TopologyCatalogRepositoryAdapter(catalogRepository, translationRepository, mapper);
    }

    @Bean
    public PipelineSystemRepositoryPort pipelineSystemRepositoryPort(
            PipelineSystemJpaRepository repository,
            TopologyPersistenceMapper mapper) {

        return new PipelineSystemRepositoryAdapter(repository, mapper);
    }

    @Bean
    public PipelineRepositoryPort pipelineRepositoryPort(
            PipelineJpaRepository repository,
            TopologyPersistenceMapper mapper) {

        return new PipelineRepositoryAdapter(repository, mapper);
    }

    @Bean
    public FacilityRepositoryPort facilityRepositoryPort(
            FacilityJpaRepository repository,
            TopologyPersistenceMapper mapper) {

        return new FacilityRepositoryAdapter(repository, mapper);
    }

    @Bean
    public TopologyNodeRepositoryPort topologyNodeRepositoryPort(
            TopologyNodeJpaRepository repository,
            TopologyPersistenceMapper mapper) {

        return new TopologyNodeRepositoryAdapter(repository, mapper);
    }

    @Bean
    public PipelineSegmentRepositoryPort pipelineSegmentRepositoryPort(
            PipelineSegmentJpaRepository repository,
            TopologyPersistenceMapper mapper) {

        return new PipelineSegmentRepositoryAdapter(repository, mapper);
    }

    @Bean
    public PipelineAppurtenanceRepositoryPort pipelineAppurtenanceRepositoryPort(
            PipelineAppurtenanceJpaRepository repository,
            TopologyPersistenceMapper mapper) {

        return new PipelineAppurtenanceRepositoryAdapter(repository, mapper);
    }

    @Bean
    public TopologyConnectionRepositoryPort topologyConnectionRepositoryPort(
            TopologyConnectionJpaRepository repository,
            TopologyPersistenceMapper mapper) {

        return new TopologyConnectionRepositoryAdapter(repository, mapper);
    }

    @Bean
    public EquipmentRepositoryPort equipmentRepositoryPort(
            EquipmentJpaRepository repository,
            TopologyPersistenceMapper mapper) {

        return new EquipmentRepositoryAdapter(repository, mapper);
    }

    @Bean
    public TopologyAssetStatusPolicy topologyAssetStatusPolicy() {
        return new TopologyAssetStatusPolicy();
    }

    @Bean
    public TopologyConnectivityPolicy topologyConnectivityPolicy() {
        return new TopologyConnectivityPolicy();
    }

    @Bean
    public FacilityTopologyPolicy facilityTopologyPolicy() {
        return new FacilityTopologyPolicy();
    }

    @Bean
    public PipelineAppurtenancePolicy pipelineAppurtenancePolicy() {
        return new PipelineAppurtenancePolicy();
    }

    @Bean
    public TopologyRegistrationDomainService topologyRegistrationDomainService(
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
    public TopologyConnectivityDomainService topologyConnectivityDomainService(
            TopologyConnectivityPolicy connectivityPolicy) {

        return new TopologyConnectivityDomainService(connectivityPolicy);
    }

    @Bean
    public PipelineAppurtenanceDomainService pipelineAppurtenanceDomainService(
            PipelineAppurtenancePolicy appurtenancePolicy,
            TopologyConnectivityPolicy connectivityPolicy,
            TopologyAssetStatusPolicy statusPolicy) {

        return new PipelineAppurtenanceDomainService(appurtenancePolicy, connectivityPolicy, statusPolicy);
    }

    @Bean
    public PipelineSystemApplicationService pipelineSystemApplicationService(
            PipelineSystemRepositoryPort pipelineSystemRepository) {

        return new PipelineSystemApplicationService(pipelineSystemRepository);
    }

    @Bean
    public CreatePipelineSystemUseCase createPipelineSystemUseCase(
            PipelineSystemApplicationService service) {

        return service;
    }

    @Bean
    public GetPipelineSystemUseCase getPipelineSystemUseCase(
            PipelineSystemApplicationService service) {

        return service;
    }

    @Bean
    public ListPipelineSystemsUseCase listPipelineSystemsUseCase(
            PipelineSystemApplicationService service) {

        return service;
    }

    @Bean
    public PipelineApplicationService pipelineApplicationService(
            PipelineRepositoryPort pipelineRepository,
            PipelineSystemRepositoryPort pipelineSystemRepository,
            TopologyRegistrationDomainService registrationDomainService) {

        return new PipelineApplicationService(
                pipelineRepository,
                pipelineSystemRepository,
                registrationDomainService);
    }

    @Bean
    public CreatePipelineUseCase createPipelineUseCase(PipelineApplicationService service) {
        return service;
    }

    @Bean
    public GetPipelineUseCase getPipelineUseCase(PipelineApplicationService service) {
        return service;
    }

    @Bean
    public ListPipelinesUseCase listPipelinesUseCase(PipelineApplicationService service) {
        return service;
    }

    @Bean
    public FacilityApplicationService facilityApplicationService(
            FacilityRepositoryPort facilityRepository,
            TopologyRegistrationDomainService registrationDomainService) {

        return new FacilityApplicationService(facilityRepository, registrationDomainService);
    }

    @Bean
    public CreateFacilityUseCase createFacilityUseCase(FacilityApplicationService service) {
        return service;
    }

    @Bean
    public GetFacilityUseCase getFacilityUseCase(FacilityApplicationService service) {
        return service;
    }

    @Bean
    public ListFacilitiesUseCase listFacilitiesUseCase(FacilityApplicationService service) {
        return service;
    }

    @Bean
    public TopologyNodeApplicationService topologyNodeApplicationService(
            TopologyNodeRepositoryPort topologyNodeRepository,
            FacilityRepositoryPort facilityRepository,
            TopologyRegistrationDomainService registrationDomainService) {

        return new TopologyNodeApplicationService(
                topologyNodeRepository,
                facilityRepository,
                registrationDomainService);
    }

    @Bean
    public CreateTopologyNodeUseCase createTopologyNodeUseCase(TopologyNodeApplicationService service) {
        return service;
    }

    @Bean
    public GetTopologyNodeUseCase getTopologyNodeUseCase(TopologyNodeApplicationService service) {
        return service;
    }

    @Bean
    public ListTopologyNodesUseCase listTopologyNodesUseCase(TopologyNodeApplicationService service) {
        return service;
    }

    @Bean
    public PipelineSegmentApplicationService pipelineSegmentApplicationService(
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
    public CreatePipelineSegmentUseCase createPipelineSegmentUseCase(PipelineSegmentApplicationService service) {
        return service;
    }

    @Bean
    public ListPipelineSegmentsUseCase listPipelineSegmentsUseCase(PipelineSegmentApplicationService service) {
        return service;
    }

    @Bean
    public PipelineAppurtenanceApplicationService pipelineAppurtenanceApplicationService(
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
    public CreatePipelineAppurtenanceUseCase createPipelineAppurtenanceUseCase(
            PipelineAppurtenanceApplicationService service) {

        return service;
    }

    @Bean
    public GetPipelineAppurtenanceUseCase getPipelineAppurtenanceUseCase(
            PipelineAppurtenanceApplicationService service) {

        return service;
    }

    @Bean
    public ListPipelineAppurtenancesUseCase listPipelineAppurtenancesUseCase(
            PipelineAppurtenanceApplicationService service) {

        return service;
    }

    @Bean
    public TopologyConnectionApplicationService topologyConnectionApplicationService(
            TopologyConnectionRepositoryPort topologyConnectionRepository,
            TopologyNodeRepositoryPort topologyNodeRepository,
            TopologyRegistrationDomainService registrationDomainService) {

        return new TopologyConnectionApplicationService(
                topologyConnectionRepository,
                topologyNodeRepository,
                registrationDomainService);
    }

    @Bean
    public CreateTopologyConnectionUseCase createTopologyConnectionUseCase(
            TopologyConnectionApplicationService service) {

        return service;
    }

    @Bean
    public ListTopologyConnectionsUseCase listTopologyConnectionsUseCase(
            TopologyConnectionApplicationService service) {

        return service;
    }

    @Bean
    public EquipmentApplicationService equipmentApplicationService(
            EquipmentRepositoryPort equipmentRepository,
            TopologyRegistrationDomainService registrationDomainService) {

        return new EquipmentApplicationService(equipmentRepository, registrationDomainService);
    }

    @Bean
    public RegisterEquipmentUseCase registerEquipmentUseCase(EquipmentApplicationService service) {
        return service;
    }

    @Bean
    public TopologyCatalogApplicationService topologyCatalogApplicationService(
            TopologyCatalogRepositoryPort topologyCatalogRepository) {

        return new TopologyCatalogApplicationService(topologyCatalogRepository);
    }

    @Bean
    public GetTopologyCatalogTypeUseCase getTopologyCatalogTypeUseCase(TopologyCatalogApplicationService service) {
        return service;
    }

    @Bean
    public ListTopologyCatalogTypesUseCase listTopologyCatalogTypesUseCase(TopologyCatalogApplicationService service) {
        return service;
    }

    @Bean
    public ResolveTopologyCatalogTypeUseCase resolveTopologyCatalogTypeUseCase(TopologyCatalogApplicationService service) {
        return service;
    }
}
