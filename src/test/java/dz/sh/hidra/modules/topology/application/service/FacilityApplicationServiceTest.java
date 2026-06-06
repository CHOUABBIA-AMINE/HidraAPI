/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : FacilityApplicationServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Test
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.service
 *
 * @Description : Unit tests for facility application service.
 *
 */
package dz.sh.hidra.modules.topology.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import dz.sh.hidra.kernel.application.pagination.PageRequest;
import dz.sh.hidra.kernel.application.pagination.PageResult;
import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.modules.topology.application.command.CreateFacilityCommand;
import dz.sh.hidra.modules.topology.application.dto.FacilityDto;
import dz.sh.hidra.modules.topology.application.query.GetFacilityByIdQuery;
import dz.sh.hidra.modules.topology.application.query.ListFacilitiesQuery;
import dz.sh.hidra.modules.topology.domain.TopologyDomainTestData;
import dz.sh.hidra.modules.topology.domain.policy.FacilityTopologyPolicy;
import dz.sh.hidra.modules.topology.domain.policy.PipelineAppurtenancePolicy;
import dz.sh.hidra.modules.topology.domain.policy.TopologyAssetStatusPolicy;
import dz.sh.hidra.modules.topology.domain.policy.TopologyConnectivityPolicy;
import dz.sh.hidra.modules.topology.domain.service.TopologyRegistrationDomainService;
import dz.sh.hidra.modules.topology.domain.value.FacilityId;
import dz.sh.hidra.modules.topology.domain.value.FacilityType;
import dz.sh.hidra.modules.topology.domain.value.GeoCoordinate;
import dz.sh.hidra.modules.topology.domain.value.TopologyStatus;

/**
 * Unit tests for FacilityApplicationService.
 */
class FacilityApplicationServiceTest {

    @Test
    void shouldCreateFacilityAndReturnDto() {
        InMemoryTopologyRepositoryPorts.Facilities facilities = new InMemoryTopologyRepositoryPorts.Facilities();
        FacilityApplicationService service = new FacilityApplicationService(facilities, registrationDomainService());

        FacilityDto dto = service.createFacility(new CreateFacilityCommand(
                TopologyDomainTestData.code("APP-FAC"),
                TopologyDomainTestData.name("Application Facility"),
                TopologyDomainTestData.compressionStationType(),
                TopologyDomainTestData.gasProductType(),
                GeoCoordinate.of(31.6167, 2.2167),
                TopologyDomainTestData.organizationUnitReference()));

        assertEquals("APP-FAC", dto.code());
        assertEquals("COMPRESSION_STATION", dto.facilityType());
        assertEquals("GAS", dto.productType());
        assertEquals("TRC-OPS-EAST-CS-01", dto.organizationUnitReference().referenceCode());
        assertEquals(0, dto.coordinate().latitude().compareTo(java.math.BigDecimal.valueOf(31.6167)));
    }

    @Test
    void shouldRejectDuplicateFacilityCode() {
        InMemoryTopologyRepositoryPorts.Facilities facilities = new InMemoryTopologyRepositoryPorts.Facilities();
        facilities.save(TopologyDomainTestData.facility());
        FacilityApplicationService service = new FacilityApplicationService(facilities, registrationDomainService());

        assertThrows(BusinessRuleViolationException.class, () -> service.createFacility(new CreateFacilityCommand(
                TopologyDomainTestData.facility().code(),
                TopologyDomainTestData.name("Duplicate Facility"),
                TopologyDomainTestData.compressionStationType(),
                TopologyDomainTestData.gasProductType(),
                null,
                null)));
    }

    @Test
    void shouldGetAndListFacilities() {
        InMemoryTopologyRepositoryPorts.Facilities facilities = new InMemoryTopologyRepositoryPorts.Facilities();
        facilities.save(TopologyDomainTestData.facility());
        FacilityApplicationService service = new FacilityApplicationService(facilities, registrationDomainService());

        FacilityDto found = service.getFacility(new GetFacilityByIdQuery(TopologyDomainTestData.facility().id()));
        assertEquals("TOP-FAC", found.code());

        PageResult<FacilityDto> page = service.listFacilities(new ListFacilitiesQuery(
                null,
                TopologyDomainTestData.compressionStationType(),
                TopologyDomainTestData.gasProductType(),
                TopologyStatus.PLANNED,
                PageRequest.of(0, 10)));

        assertEquals(1L, page.totalElements());
        assertEquals("TOP-FAC", page.items().get(0).code());
    }

    @Test
    void shouldRejectUnknownFacilityId() {
        FacilityApplicationService service = new FacilityApplicationService(
                new InMemoryTopologyRepositoryPorts.Facilities(),
                registrationDomainService());

        assertThrows(BusinessRuleViolationException.class, () -> service.getFacility(new GetFacilityByIdQuery(FacilityId.newId())));
    }

    private static TopologyRegistrationDomainService registrationDomainService() {
        return new TopologyRegistrationDomainService(
                new TopologyAssetStatusPolicy(),
                new TopologyConnectivityPolicy(),
                new FacilityTopologyPolicy(),
                new PipelineAppurtenancePolicy());
    }


}
