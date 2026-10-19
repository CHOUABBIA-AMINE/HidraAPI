/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringNetworkDraftTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-19
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.model
 *
 * @Description : Implements the bounded company-scoped engineering draft contract.
 *
 */
package dz.sh.hidra.modules.topology.domain.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.EngineeringNetworkDraftCodec;
class EngineeringNetworkDraftTest {
    @Test void immutableMetadataVersionAndIncompleteDiagnostics(){
        var d=new EngineeringNetworkDraft(UUID.randomUUID(),"company","network",UUID.randomUUID(),1,new EngineeringNetworkDraft.Metadata(null,null,null));
        var next=d.updated(new EngineeringNetworkDraft.Metadata("title",null,null));assertEquals(1,d.version());assertEquals(2,next.version());
        assertEquals(List.of("GRAPH_NOT_AUTHORED","TITLE_NOT_SUPPLIED","EVIDENCE_NOT_SUPPLIED"),d.diagnostics());
        assertTrue(next.diagnostics().contains("GRAPH_NOT_AUTHORED"));assertThrows(UnsupportedOperationException.class,()->d.diagnostics().add("complete"));
    }
    @Test void exactScopeBoundsAndOverflowAreRejected(){
        assertThrows(IllegalArgumentException.class,()->EngineeringNetworkDraft.identity(" company"));
        assertThrows(IllegalArgumentException.class,()->new EngineeringNetworkDraft.Metadata("x".repeat(256),null,null));
        assertThrows(IllegalArgumentException.class,()->new EngineeringNetworkDraft.Metadata("\ud800",null,null));
        var d=new EngineeringNetworkDraft(UUID.randomUUID(),"c","n",UUID.randomUUID(),Long.MAX_VALUE,new EngineeringNetworkDraft.Metadata(null,null,null));
        assertThrows(ArithmeticException.class,()->d.updated(d.metadata()));
    }
    @Test void canonicalCodecRetainsNullAndUnicodeAndRejectsTrailingOrOversizedBytes(){
        var codec=new EngineeringNetworkDraftCodec();var m=new EngineeringNetworkDraft.Metadata("العربية é",null,"ref");
        byte[] bytes=codec.encode(m);assertEquals(m,codec.decode(bytes));assertEquals(64,EngineeringNetworkDraftCodec.digest(bytes).length());
        assertThrows(IllegalArgumentException.class,()->codec.decode(Arrays.copyOf(bytes,bytes.length+1)));
        assertThrows(IllegalArgumentException.class,()->codec.encode(new EngineeringNetworkDraft.Metadata(null,"é".repeat(16384),null)));
    }
}
