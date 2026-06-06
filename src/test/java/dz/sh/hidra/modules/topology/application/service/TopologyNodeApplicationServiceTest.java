/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyNodeApplicationServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Test
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.service
 *
 * @Description : Unit tests for topology node application service.
 *
 */
package dz.sh.hidra.modules.topology.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import dz.sh.hidra.kernel.application.pagination.PageRequest;
import dz.sh.hidra.kernel.application.pagination.PageResult;
import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.modules.topology.application.command.CreateTopologyNodeCommand;
import dz.sh.hidra.modules.topology.application.dto.TopologyNodeDto;
import dz.sh.hidra.modules.topology.application.query.GetTopologyNodeByIdQuery;
import dz.sh.hidra.modules.topology.application.query.ListTopologyNodesQuery;
import dz.sh.hidra.modules.topology.domain.TopologyDomainTestData;
import dz.sh.hidra.modules.topology.domain.model.Facility;
import dz.sh.hidra.modules.topology.domain.policy.FacilityTopologyPolicy;
import dz.sh.hidra.modules.topology.domain.policy.PipelineAppurtenancePolicy;
import dz.sh.hidra.modules.topology.domain.policy.TopologyAssetStatusPolicy;
import dz.sh.hidra.modules.topology.domain.policy.TopologyConnectivityPolicy;
import dz.sh.hidra.modules.topology.domain.service.TopologyRegistrationDomainService;
import dz.sh.hidra.modules.topology.domain.value.GeoCoordinate;
import dz.sh.hidra.modules.topology.domain.value.NodeType;
import dz.sh.hidra.modules.topology.domain.value.TopologyNodeId;
import dz.sh.hidra.modules.topology.domain.value.TopologyStatus;

/**
 * Unit tests for TopologyNodeApplicationService.
 */
class TopologyNodeApplicationServiceTest {

    @Test
    void shouldCreateFacilityNodeWhenFacilityExists() {
        InMemoryTopologyRepositoryPorts.TopologyNodes nodes = new InMemoryTopologyRepositoryPorts.TopologyNodes();
        InMemoryTopologyRepositoryPorts.Facilities facilities = new InMemoryTopologyRepositoryPorts.Facilities();
        Facility facility = TopologyDomainTestData.facility();
        facilities.save(facility);
        TopologyNodeApplicationService service = new TopologyNodeApplicationService(nodes, facilities, registrationDomainService());

        TopologyNodeDto dto = service.createTopologyNode(new CreateTopologyNodeCommand(
                TopologyDomainTestData.code("APP-NODE"),
                TopologyDomainTestData.name("Application Node"),
                NodeType.FACILITY_INLET,
                facility.id(),
                null,
                GeoCoordinate.of(31.6000, 2.2000),
                new BigDecimal("725.300")));

        assertEquals("APP-NODE", dto.code());
        assertEquals("FACILITY_INLET", dto.nodeType());
        assertEquals(facility.id().value(), dto.facilityId());
        assertEquals("PLANNED", dto.status());
    }

    @Test
    void shouldRejectFacilityNodeWhenFacilityDoesNotExist() {
        TopologyNodeApplicationService service = new TopologyNodeApplicationService(
                new InMemoryTopologyRepositoryPorts.TopologyNodes(),
                new InMemoryTopologyRepositoryPorts.Facilities(),
                registrationDomainService());

        assertThrows(BusinessRuleViolationException.class, () -> service.createTopologyNode(new CreateTopologyNodeCommand(
                TopologyDomainTestData.code("APP-NODE-MISSING-FAC"),
                TopologyDomainTestData.name("Missing Facility Node"),
                NodeType.FACILITY_INLET,
                dz.sh.hidra.modules.topology.domain.value.FacilityId.newId(),
                null,
                null,
                null)));
    }

    @Test
    void shouldRejectDuplicateTopologyNodeCode() {
        InMemoryTopologyRepositoryPorts.TopologyNodes nodes = new InMemoryTopologyRepositoryPorts.TopologyNodes();
        nodes.save(TopologyDomainTestData.freeNode(NodeType.PIPELINE_JUNCTION, "DUP-APP-NODE"));
        TopologyNodeApplicationService service = new TopologyNodeApplicationService(
                nodes,
                new InMemoryTopologyRepositoryPorts.Facilities(),
                registrationDomainService());

        assertThrows(BusinessRuleViolationException.class, () -> service.createTopologyNode(new CreateTopologyNodeCommand(
                TopologyDomainTestData.freeNode(NodeType.PIPELINE_JUNCTION, "DUP-APP-NODE").code(),
                TopologyDomainTestData.name("Duplicate Node"),
                NodeType.PIPELINE_JUNCTION,
                null,
                null,
                null,
                null)));
    }

    @Test
    void shouldGetAndListTopologyNodes() {
        InMemoryTopologyRepositoryPorts.TopologyNodes nodes = new InMemoryTopologyRepositoryPorts.TopologyNodes();
        nodes.save(TopologyDomainTestData.freeNode(NodeType.PIPELINE_JUNCTION, "APP-LIST"));
        TopologyNodeApplicationService service = new TopologyNodeApplicationService(
                nodes,
                new InMemoryTopologyRepositoryPorts.Facilities(),
                registrationDomainService());

        TopologyNodeDto found = service.getTopologyNode(new GetTopologyNodeByIdQuery(
                TopologyDomainTestData.freeNode(NodeType.PIPELINE_JUNCTION, "APP-LIST").id()));
        assertEquals("TOP-NODE-APP-LIST", found.code());

        PageResult<TopologyNodeDto> page = service.listTopologyNodes(new ListTopologyNodesQuery(
                null,
                NodeType.PIPELINE_JUNCTION,
                null,
                null,
                TopologyStatus.PLANNED,
                PageRequest.of(0, 10)));

        assertEquals(1L, page.totalElements());
    }

    @Test
    void shouldRejectUnknownTopologyNodeId() {
        TopologyNodeApplicationService service = new TopologyNodeApplicationService(
                new InMemoryTopologyRepositoryPorts.TopologyNodes(),
                new InMemoryTopologyRepositoryPorts.Facilities(),
                registrationDomainService());

        assertThrows(BusinessRuleViolationException.class, () -> service.getTopologyNode(new GetTopologyNodeByIdQuery(TopologyNodeId.newId())));
    }

    private static TopologyRegistrationDomainService registrationDomainService() {
        return new TopologyRegistrationDomainService(
                new TopologyAssetStatusPolicy(),
                new TopologyConnectivityPolicy(),
                new FacilityTopologyPolicy(),
                new PipelineAppurtenancePolicy());
    }


}
