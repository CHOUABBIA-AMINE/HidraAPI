/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PipelineSystemApplicationServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Test
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.service
 *
 * @Description : Unit tests for pipeline system application service.
 *
 */
package dz.sh.hidra.modules.topology.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import dz.sh.hidra.kernel.application.pagination.PageRequest;
import dz.sh.hidra.kernel.application.pagination.PageResult;
import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.modules.topology.application.command.CreatePipelineSystemCommand;
import dz.sh.hidra.modules.topology.application.dto.PipelineSystemDto;
import dz.sh.hidra.modules.topology.application.query.GetPipelineSystemByIdQuery;
import dz.sh.hidra.modules.topology.application.query.ListPipelineSystemsQuery;
import dz.sh.hidra.modules.topology.domain.TopologyDomainTestData;
import dz.sh.hidra.modules.topology.domain.value.TopologyStatus;

/**
 * Unit tests for PipelineSystemApplicationService.
 */
class PipelineSystemApplicationServiceTest {

    @Test
    void shouldCreatePipelineSystemAndReturnDto() {
        InMemoryTopologyRepositoryPorts.PipelineSystems repository = new InMemoryTopologyRepositoryPorts.PipelineSystems();
        PipelineSystemApplicationService service = new PipelineSystemApplicationService(repository);

        PipelineSystemDto dto = service.createPipelineSystem(new CreatePipelineSystemCommand(
                TopologyDomainTestData.code("APP-PS"),
                TopologyDomainTestData.name("Application Pipeline System"),
                " Application system ",
                TopologyDomainTestData.gasProductType(),
                TopologyDomainTestData.operationalOwnerReference()));

        assertEquals("APP-PS", dto.code());
        assertEquals("Application Pipeline System", dto.name());
        assertEquals("GAS", dto.productType());
        assertEquals("PLANNED", dto.status());
        assertEquals("TRC-OPS-EAST", dto.operationalOwnerReference().referenceCode());
    }

    @Test
    void shouldRejectDuplicatePipelineSystemCode() {
        InMemoryTopologyRepositoryPorts.PipelineSystems repository = new InMemoryTopologyRepositoryPorts.PipelineSystems();
        repository.save(TopologyDomainTestData.pipelineSystem());
        PipelineSystemApplicationService service = new PipelineSystemApplicationService(repository);

        assertThrows(BusinessRuleViolationException.class, () -> service.createPipelineSystem(new CreatePipelineSystemCommand(
                TopologyDomainTestData.pipelineSystem().code(),
                TopologyDomainTestData.name("Duplicate Pipeline System"),
                null,
                TopologyDomainTestData.gasProductType(),
                null)));
    }

    @Test
    void shouldGetAndListPipelineSystems() {
        InMemoryTopologyRepositoryPorts.PipelineSystems repository = new InMemoryTopologyRepositoryPorts.PipelineSystems();
        PipelineSystemApplicationService service = new PipelineSystemApplicationService(repository);
        repository.save(TopologyDomainTestData.pipelineSystem());

        PipelineSystemDto found = service.getPipelineSystem(new GetPipelineSystemByIdQuery(
                TopologyDomainTestData.pipelineSystem().id()));

        assertEquals("TOP-PS", found.code());

        PageResult<PipelineSystemDto> page = service.listPipelineSystems(new ListPipelineSystemsQuery(
                null,
                TopologyDomainTestData.gasProductType(),
                TopologyStatus.PLANNED,
                PageRequest.of(0, 10)));

        assertEquals(1L, page.totalElements());
        assertEquals("TOP-PS", page.items().get(0).code());
    }

    @Test
    void shouldRejectUnknownPipelineSystemId() {
        PipelineSystemApplicationService service = new PipelineSystemApplicationService(
                new InMemoryTopologyRepositoryPorts.PipelineSystems());

        assertThrows(BusinessRuleViolationException.class, () -> service.getPipelineSystem(
                new GetPipelineSystemByIdQuery(dz.sh.hidra.modules.topology.domain.value.PipelineSystemId.newId())));
    }
}
