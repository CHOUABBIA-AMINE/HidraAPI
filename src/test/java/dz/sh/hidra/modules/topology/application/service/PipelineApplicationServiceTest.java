/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PipelineApplicationServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Test
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.service
 *
 * @Description : Unit tests for pipeline application service.
 *
 */
package dz.sh.hidra.modules.topology.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import dz.sh.hidra.kernel.application.pagination.PageRequest;
import dz.sh.hidra.kernel.application.pagination.PageResult;
import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.modules.topology.application.command.CreatePipelineCommand;
import dz.sh.hidra.modules.topology.application.dto.PipelineDto;
import dz.sh.hidra.modules.topology.application.query.GetPipelineByIdQuery;
import dz.sh.hidra.modules.topology.application.query.ListPipelinesQuery;
import dz.sh.hidra.modules.topology.domain.TopologyDomainTestData;
import dz.sh.hidra.modules.topology.domain.model.PipelineSystem;
import dz.sh.hidra.modules.topology.domain.policy.FacilityTopologyPolicy;
import dz.sh.hidra.modules.topology.domain.policy.PipelineAppurtenancePolicy;
import dz.sh.hidra.modules.topology.domain.policy.TopologyAssetStatusPolicy;
import dz.sh.hidra.modules.topology.domain.policy.TopologyConnectivityPolicy;
import dz.sh.hidra.modules.topology.domain.service.TopologyRegistrationDomainService;
import dz.sh.hidra.modules.topology.domain.value.DiameterInInches;
import dz.sh.hidra.modules.topology.domain.value.LengthInKilometers;
import dz.sh.hidra.modules.topology.domain.value.PipelineId;
import dz.sh.hidra.modules.topology.domain.value.TopologyStatus;

/**
 * Unit tests for PipelineApplicationService.
 */
class PipelineApplicationServiceTest {

    @Test
    void shouldCreatePipelineWhenParentSystemExists() {
        InMemoryTopologyRepositoryPorts.PipelineSystems pipelineSystems = new InMemoryTopologyRepositoryPorts.PipelineSystems();
        InMemoryTopologyRepositoryPorts.Pipelines pipelines = new InMemoryTopologyRepositoryPorts.Pipelines();
        PipelineSystem pipelineSystem = TopologyDomainTestData.pipelineSystem();
        pipelineSystems.save(pipelineSystem);

        PipelineApplicationService service = new PipelineApplicationService(pipelines, pipelineSystems, registrationDomainService());

        PipelineDto dto = service.createPipeline(new CreatePipelineCommand(
                pipelineSystem.id(),
                TopologyDomainTestData.code("APP-PIPE"),
                TopologyDomainTestData.name("Application Pipeline"),
                " Application pipeline ",
                TopologyDomainTestData.gasProductType(),
                DiameterInInches.of(42.0),
                LengthInKilometers.of(512.3)));

        assertEquals("APP-PIPE", dto.code());
        assertEquals(pipelineSystem.id().value(), dto.pipelineSystemId());
        assertEquals("GAS", dto.productType());
        assertEquals("PLANNED", dto.status());
    }

    @Test
    void shouldRejectPipelineWhenParentSystemDoesNotExist() {
        PipelineApplicationService service = new PipelineApplicationService(
                new InMemoryTopologyRepositoryPorts.Pipelines(),
                new InMemoryTopologyRepositoryPorts.PipelineSystems(),
                registrationDomainService());

        assertThrows(BusinessRuleViolationException.class, () -> service.createPipeline(new CreatePipelineCommand(
                dz.sh.hidra.modules.topology.domain.value.PipelineSystemId.newId(),
                TopologyDomainTestData.code("APP-PIPE-MISSING-PARENT"),
                TopologyDomainTestData.name("Pipeline Missing Parent"),
                null,
                TopologyDomainTestData.gasProductType(),
                DiameterInInches.of(42.0),
                LengthInKilometers.of(512.3))));
    }

    @Test
    void shouldRejectDuplicatePipelineCodeBeforeParentLookup() {
        InMemoryTopologyRepositoryPorts.Pipelines pipelines = new InMemoryTopologyRepositoryPorts.Pipelines();
        PipelineSystem pipelineSystem = TopologyDomainTestData.pipelineSystem();
        pipelines.save(TopologyDomainTestData.pipeline(pipelineSystem));
        PipelineApplicationService service = new PipelineApplicationService(
                pipelines,
                new InMemoryTopologyRepositoryPorts.PipelineSystems(),
                registrationDomainService());

        assertThrows(BusinessRuleViolationException.class, () -> service.createPipeline(new CreatePipelineCommand(
                pipelineSystem.id(),
                TopologyDomainTestData.pipeline(pipelineSystem).code(),
                TopologyDomainTestData.name("Duplicate Pipeline"),
                null,
                TopologyDomainTestData.gasProductType(),
                DiameterInInches.of(42.0),
                LengthInKilometers.of(512.3))));
    }

    @Test
    void shouldGetAndListPipelines() {
        InMemoryTopologyRepositoryPorts.PipelineSystems pipelineSystems = new InMemoryTopologyRepositoryPorts.PipelineSystems();
        InMemoryTopologyRepositoryPorts.Pipelines pipelines = new InMemoryTopologyRepositoryPorts.Pipelines();
        PipelineSystem pipelineSystem = TopologyDomainTestData.pipelineSystem();
        pipelineSystems.save(pipelineSystem);
        pipelines.save(TopologyDomainTestData.pipeline(pipelineSystem));

        PipelineApplicationService service = new PipelineApplicationService(pipelines, pipelineSystems, registrationDomainService());

        PipelineDto found = service.getPipeline(new GetPipelineByIdQuery(TopologyDomainTestData.pipeline(pipelineSystem).id()));
        assertEquals("TOP-PIPE", found.code());

        PageResult<PipelineDto> page = service.listPipelines(new ListPipelinesQuery(
                null,
                pipelineSystem.id(),
                TopologyDomainTestData.gasProductType(),
                TopologyStatus.PLANNED,
                PageRequest.of(0, 10)));

        assertEquals(1L, page.totalElements());
        assertEquals("TOP-PIPE", page.items().get(0).code());
    }

    @Test
    void shouldRejectUnknownPipelineId() {
        PipelineApplicationService service = new PipelineApplicationService(
                new InMemoryTopologyRepositoryPorts.Pipelines(),
                new InMemoryTopologyRepositoryPorts.PipelineSystems(),
                registrationDomainService());

        assertThrows(BusinessRuleViolationException.class, () -> service.getPipeline(new GetPipelineByIdQuery(PipelineId.newId())));
    }

    private static TopologyRegistrationDomainService registrationDomainService() {
        return new TopologyRegistrationDomainService(
                new TopologyAssetStatusPolicy(),
                new TopologyConnectivityPolicy(),
                new FacilityTopologyPolicy(),
                new PipelineAppurtenancePolicy());
    }


}
