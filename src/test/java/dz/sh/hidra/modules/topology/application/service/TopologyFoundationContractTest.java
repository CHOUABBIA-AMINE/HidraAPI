package dz.sh.hidra.modules.topology.application.service;
import dz.sh.hidra.modules.topology.application.port.in.TopologyFoundationUseCase.*;
import dz.sh.hidra.modules.identity.application.contract.topology.TopologyScopedAccessContract;
import dz.sh.hidra.modules.identity.application.contract.topology.TopologyProtectedAuthorizationContract;
import dz.sh.hidra.modules.topology.api.rest.mapper.TopologyDiscoveryCursor;
import dz.sh.hidra.modules.topology.domain.value.FacilityKind;
import java.time.Instant;
import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class TopologyFoundationContractTest {
    @Test void setupAndStationPermissionsAreDistinctEvenForSameActorAndTarget(){
        var legacy=new TopologyScopedAccessContract.Request(TopologyScopedAccessContract.Operation.AUTHOR,"company","facility");
        var setup=new TopologyScopedAccessContract.Request(TopologyScopedAccessContract.Operation.AUTHOR,"company","facility",TopologyScopedAccessContract.Target.FOUNDATION_FACILITY);
        assertEquals("topology:station-revision:author",TopologyProtectedAuthorizationContract.permissionCode(legacy));
        assertEquals("topology:foundation:write",TopologyProtectedAuthorizationContract.permissionCode(setup));
        assertEquals("TOPOLOGY_FOUNDATION",setup.resourceType());assertNotEquals(legacy,setup);
        assertThrows(IllegalArgumentException.class,()->new TopologyScopedAccessContract.Request(TopologyScopedAccessContract.Operation.PUBLISH,"company","facility",TopologyScopedAccessContract.Target.FOUNDATION_FACILITY));
        assertThrows(IllegalArgumentException.class,()->new TopologyScopedAccessContract.Request(TopologyScopedAccessContract.Operation.AUTHOR,"company","other",TopologyScopedAccessContract.Target.FOUNDATION_COMPANY));
    }
    @Test void setupNeedsExactScopeReasonRequestAndGeneration(){
        assertThrows(IllegalArgumentException.class,()->new Command(Kind.REGISTER_POSITION,"company","facility",UUID.randomUUID(),"position","P","Name","kind",null,null," ",null,0,1));
        assertThrows(NullPointerException.class,()->new Command(Kind.ACTIVATE_FACILITY,"company","facility",UUID.randomUUID(),"facility",null,null,null,null,null,"reason",null,0,1));
        assertThrows(IllegalArgumentException.class,()->new Command(Kind.ACTIVATE_FACILITY,"company","facility",UUID.randomUUID(),"other",null,null,null,null,Instant.now(),"reason",null,0,1));
        assertThrows(IllegalArgumentException.class,()->new Command(Kind.DISCOVER,"company",null,null,null,null,null,null,null,null,null,Catalog.SYSTEM_TYPES,0,201));
    }
    @Test void stationSpecializationsShareOneEligibilityPolicy(){
        for(var kind:FacilityKind.values())assertEquals(Set.of(FacilityKind.STATION,FacilityKind.COMPRESSOR_STATION,FacilityKind.PUMP_STATION,FacilityKind.METERING_STATION).contains(kind),kind.isStation());
    }
    @Test void scanCursorIsBoundedCanonicalAndIndependentOfAuthority(){
        assertNull(TopologyDiscoveryCursor.encode(null));assertEquals(0,TopologyDiscoveryCursor.decode(null));
        for(int n:new int[]{0,1,50,200,100000})assertEquals(n,TopologyDiscoveryCursor.decode(TopologyDiscoveryCursor.encode(n)));
        for(String bad:List.of("","50","AAAA","AQAAAMg=","%%%%"))assertThrows(IllegalArgumentException.class,()->TopologyDiscoveryCursor.decode(bad));
        assertThrows(IllegalArgumentException.class,()->TopologyDiscoveryCursor.decode(TopologyDiscoveryCursor.encode(100001)));
    }
    @Test void legacyCreateConstraintsRemainOnControllerInterface(){
        try(var validator=jakarta.validation.Validation.buildDefaultValidatorFactory()) {
            var violations=validator.getValidator().forExecutables().validateParameters(
                new dz.sh.hidra.modules.topology.api.rest.controller.SpringTopologyController(c->null,c->null),
                dz.sh.hidra.modules.topology.api.rest.controller.SpringTopologyController.class.getMethod("createPipelineSystem",dz.sh.hidra.modules.topology.api.rest.request.CreatePipelineSystemRequest.class),new Object[]{null});
            assertNotNull(violations); // HV000151 used to occur before either creation handler could execute.
        }catch(ReflectiveOperationException e){throw new AssertionError(e);}
    }
}
