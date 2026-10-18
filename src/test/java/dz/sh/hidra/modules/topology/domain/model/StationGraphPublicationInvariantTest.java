/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : StationGraphPublicationInvariantTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-18
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.model
 *
 * @Description : Verifies immutable Topology publication head and event invariants.
 *
 */
package dz.sh.hidra.modules.topology.domain.model;

import java.time.Instant;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StationGraphPublicationInvariantTest {
    private static final UUID ID=UUID.fromString("00000000-0000-4000-8000-000000000001");
    @Test void rejectsMissingOrOverflowedEpochAndInvalidPublicationState(){
        assertThrows(IllegalArgumentException.class,()->new StationGraphPublicationHead("f","r",0,StationGraphPublicationHead.State.ACTIVE,ID));
        assertThrows(IllegalArgumentException.class,()->new StationGraphPublicationHead("f","r",1,null,ID));
        assertThrows(IllegalArgumentException.class,()->new StationGraphPublicationHead("f","r",1,StationGraphPublicationHead.State.ACTIVE,null));
        assertThrows(IllegalArgumentException.class,()->new StationGraphPublicationHead("f","r",Long.MAX_VALUE,StationGraphPublicationHead.State.ACTIVE,ID).transitioned("r2",StationGraphPublicationHead.State.ACTIVE,UUID.randomUUID()));
    }
    @Test void rejectsSelfOrStalePredecessorAndForgedProvenance(){
        var h=new StationGraphPublicationHead("f","r",1,StationGraphPublicationHead.State.ACTIVE,ID);
        assertThrows(IllegalArgumentException.class,()->h.transitioned("r",StationGraphPublicationHead.State.ACTIVE,UUID.randomUUID()));
        var s=Instant.parse("2026-10-18T12:00:00Z");
        assertThrows(IllegalArgumentException.class,()->new StationGraphPublicationEvent(ID,"f",
            StationGraphPublicationEvent.Kind.PUBLISH,2L,"r","r2","binding","session","user","company",1,1,s,s,1,1));
        assertThrows(IllegalArgumentException.class,()->new StationGraphPublicationEvent(ID,"f",
            StationGraphPublicationEvent.Kind.PUBLISH,2L,"r","r2","","session","user","company",1,1,s,s.plusSeconds(1),1,1));
    }
    @Test void preservesWithdrawnHistoryAndMonotonicEpoch(){
        var h=new StationGraphPublicationHead("f","r1",1,StationGraphPublicationHead.State.ACTIVE,ID);
        var withdrawn=h.transitioned("r1",StationGraphPublicationHead.State.WITHDRAWN,UUID.randomUUID());
        assertEquals(2,withdrawn.epoch());assertEquals("r1",withdrawn.revisionId());
        assertEquals(StationGraphPublicationHead.State.ACTIVE,h.state());
        assertThrows(IllegalArgumentException.class,()->withdrawn.transitioned("r2",StationGraphPublicationHead.State.ACTIVE,UUID.randomUUID()));
        var activated=withdrawn.transitioned("r1",StationGraphPublicationHead.State.ACTIVE,UUID.randomUUID());
        assertEquals(3,activated.epoch());assertEquals(StationGraphPublicationHead.State.ACTIVE,activated.state());
    }
}
