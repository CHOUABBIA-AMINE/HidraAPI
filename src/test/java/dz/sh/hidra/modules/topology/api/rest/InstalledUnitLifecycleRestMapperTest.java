/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InstalledUnitLifecycleRestMapperTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-29
 *
 * @Type        : Class
 * @Layer       : Topology Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest
 *
 * @Description : Checks exact command formation, immutable responses and provenance classifications.
 *
 */
package dz.sh.hidra.modules.topology.api.rest;

import dz.sh.hidra.modules.topology.api.rest.mapper.InstalledUnitLifecycleRestMapper;
import dz.sh.hidra.modules.topology.api.rest.request.InstalledUnitLifecycleRequests.*;
import dz.sh.hidra.modules.topology.application.port.in.InstalledUnitLifecycleUseCase;
import dz.sh.hidra.modules.topology.application.port.in.InstalledUnitLifecycleUseCase.*;
import dz.sh.hidra.modules.topology.domain.model.*;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InstalledUnitLifecycleRestMapperTest {
    private static final Instant AT=Instant.parse("2026-10-29T10:00:00Z");
    private static final InstalledUnit.Evidence UNKNOWN=InstalledUnit.Evidence.unknown();
    private static final dz.sh.hidra.modules.topology.api.rest.request.InstalledUnitLifecycleRequests.Evidence UNKNOWN_WIRE =
            new dz.sh.hidra.modules.topology.api.rest.request.InstalledUnitLifecycleRequests.Evidence(null,"UNKNOWN",null);
    private static InstalledUnit unit(String id) {
        return new InstalledUnit(id,"company","kind",UNKNOWN,UNKNOWN,UNKNOWN,null);
    }
    @Test void eachMutationBindsPathCompanyAndOneOwnerOperation() {
        var registered=InstalledUnitLifecycleRestMapper.register("unit",new Register("req","company","kind",UNKNOWN_WIRE,UNKNOWN_WIRE,UNKNOWN_WIRE,null));
        assertEquals(Operation.REGISTER_UNIT,registered.operation());assertEquals("unit",registered.registration().id());
        var recorded=InstalledUnitLifecycleRestMapper.record("unit",new RecordInstallation("req","company","inst","position","facility",AT,null,"source"));
        assertEquals(new Position("position","facility"),recorded.destination());
        var closed=InstalledUnitLifecycleRestMapper.close("unit","inst",new Close("req","company","position","facility",AT.plusSeconds(1),1));
        assertEquals(1,closed.expectedRevision());assertNull(closed.destination());
        var moved=InstalledUnitLifecycleRestMapper.relocate("unit","inst",
                new Relocate("req","company","new","position","facility","destination","facility2",AT,"source",1));
        assertEquals(Operation.RELOCATE,moved.operation());assertNotEquals(moved.source(),moved.destination());
        var replaced=InstalledUnitLifecycleRestMapper.replace("unit","inst",
                new Replace("req","company","other","new","position","facility",AT,"source",1));
        assertEquals(Operation.REPLACE,replaced.operation());assertEquals(replaced.source(),replaced.destination());
        assertEquals("other",replaced.replacementUnitId());assertEquals(AT,replaced.effectiveUntil());
    }
    @Test void readsNeverRequireRequestIdAndUseExactCompanyPositionScope() {
        var unit=InstalledUnitLifecycleRestMapper.readUnit("unit","company");
        var installed=InstalledUnitLifecycleRestMapper.readInstallation("unit","inst","company","position","facility");
        var history=InstalledUnitLifecycleRestMapper.history("unit","inst","company","position","facility");
        assertNull(unit.requestId());assertNull(installed.requestId());assertNull(history.requestId());
        assertNull(unit.source());assertEquals(new Position("position","facility"),installed.source());
        assertEquals(Operation.READ_HISTORY,history.operation());
    }
    @Test void distinctPhysicalUnitsAndSourceRevisionCannotBeReinterpreted() {
        assertThrows(IllegalArgumentException.class,()->InstalledUnitLifecycleRestMapper.replace("unit","inst",
                new Replace("req","company","unit","new","position","facility",AT,"source",1)));
        assertThrows(IllegalArgumentException.class,()->InstalledUnitLifecycleRestMapper.close("unit","inst",
                new Close("req","company","position","facility",AT,0)));
        assertThrows(IllegalArgumentException.class,()->InstalledUnitLifecycleRestMapper.readUnit(" unit","company"));
    }
    @Test void responsePreservesUnknownEvidenceAndHistoryWithoutNativeCoordinates() {
        var source=unit("unit");
        var installed=new InstalledUnitInstallation("inst","unit","position","company",AT,null,AT,"proof",1);
        var result=new Result(source,installed,null,List.of(installed),true,null);
        var dto=InstalledUnitLifecycleRestMapper.from(result);
        assertTrue(dto.replayed());assertEquals("UNKNOWN",dto.unit().serialNumber().verification());
        assertNull(dto.unit().serialNumber().value());assertEquals(1,dto.history().size());
        assertEquals(AT,dto.installation().recordedAt());assertNull(dto.replacementUnit());
        assertThrows(UnsupportedOperationException.class,()->dto.history().clear());
    }
    @Test void replacementResultKeepsBothPhysicalIdentities() {
        var source=unit("unit");var replacement=unit("other");
        var dto=InstalledUnitLifecycleRestMapper.from(new Result(source,null,null,List.of(),false,replacement));
        assertEquals("unit",dto.unit().id());assertEquals("other",dto.replacementUnit().id());
    }
}
