/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningTopologyScopeQueryServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-07
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.service
 *
 * @Description : Enforces Planning-owned semantic integrity through explicit owner boundaries.
 *
 */
package dz.sh.hidra.modules.topology.application.service;
import dz.sh.hidra.modules.topology.application.port.out.*;
import dz.sh.hidra.modules.topology.domain.model.*;
import dz.sh.hidra.modules.topology.domain.value.*;
import java.lang.reflect.Proxy;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PlanningTopologyScopeQueryServiceTest {
    @SuppressWarnings("unchecked") <T>T empty(Class<T> type) {
        return (T)Proxy.newProxyInstance(getClass().getClassLoader(),new Class<?>[]{type},(p,m,a)->{assertEquals("findById",m.getName());return Optional.empty();});
    }
    @Test void missingAndUnsupportedTypesDenyWithoutIdentityAliasing() {
        var service=new PlanningTopologyScopeQueryService(empty(PipelineSystemRepositoryPort.class),empty(PipelineRepositoryPort.class),empty(FacilityRepositoryPort.class));
        for(String type:new String[]{"PIPELINE_SYSTEM","PIPELINE","FACILITY","REGION","NETWORK","EQUIPMENT","UNKNOWN"})
            assertTrue(service.resolve(type,"missing").isEmpty());
        assertTrue(service.resolve(null,"id").isEmpty());assertTrue(service.resolve("PIPELINE"," ").isEmpty());
    }

    @SuppressWarnings("unchecked") <T>T repository(Class<T> type,Object value) {
        return (T)Proxy.newProxyInstance(getClass().getClassLoader(),new Class<?>[]{type},(p,m,a)->Optional.of(value));
    }
    @Test void supportedOwnersReturnCanonicalSnapshotsWithoutInventingActiveOnlyPolicy() {
        var system=new PipelineSystem("system","SYS",null,"Système",null,PipelineSystemType.TRANSPORT,TopologyStatus.RETIRED,null,null,null,null,null);
        var pipeline=new Pipeline("pipe","system","PIPE",null,"Pipeline",null,PipelineType.NATURAL_GAS,null,null,null,null,null,TopologyStatus.DRAFT,null,null);
        var facility=new Facility("facility","FAC",null,"Installation",null,"facility-type",FacilityKind.STATION,null,null,null,null,null,null,FacilityStatus.PLANNED,null,null,null,null);
        var service=new PlanningTopologyScopeQueryService(repository(PipelineSystemRepositoryPort.class,system),repository(PipelineRepositoryPort.class,pipeline),repository(FacilityRepositoryPort.class,facility));
        assertEquals("system",service.resolve("PIPELINE_SYSTEM","system").orElseThrow().id());
        assertEquals("PIPE",service.resolve(" pipeline ","pipe").orElseThrow().code());
        assertEquals("Installation",service.resolve("FACILITY","facility").orElseThrow().name());
        assertTrue(service.resolve("REGION","system").isEmpty());assertTrue(service.resolve("NETWORK","pipe").isEmpty());
    }
}
