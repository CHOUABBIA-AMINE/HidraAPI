/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringLocalExchangeTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-25
 *
 * @Type        : Class
 * @Layer       : Domain Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.model
 *
 * @Description : Implements bounded local editor exchange through existing protected owners.
 *
 */
package dz.sh.hidra.modules.topology.domain.model;

import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class EngineeringLocalExchangeTest {
    private EngineeringLocalExchange envelope(String format,EngineeringLocalExchange.Editor editor,String company,long source,long component,String componentFormat,String sha,String payload,String branch){
        return new EngineeringLocalExchange(format,editor,company,"network",UUID.randomUUID(),UUID.randomUUID(),source,component,componentFormat,sha,payload,branch,null,null);
    }
    private EngineeringLocalExchange good(){byte[] b={1};return envelope(EngineeringLocalExchange.FORMAT,EngineeringLocalExchange.Editor.METADATA,"company",1,1,
        EngineeringLocalExchange.Editor.METADATA.componentFormat(),EngineeringLocalExchange.digest(b),Base64.getEncoder().encodeToString(b),null);}
    @Test void requiresOneKnownEditorAndExactOriginSelectors(){
        var e=good();assertThrows(IllegalArgumentException.class,()->envelope("ZIP",e.editor(),"company",1,1,e.componentFormat(),e.sha256(),e.payload(),null));
        assertThrows(IllegalArgumentException.class,()->envelope(e.format(),null,"company",1,1,e.componentFormat(),e.sha256(),e.payload(),null));
        for(String s:List.of(""," company","company "))assertThrows(IllegalArgumentException.class,()->envelope(e.format(),e.editor(),s,1,1,e.componentFormat(),e.sha256(),e.payload(),null));
    }
    @Test void rejectsInvalidVersionsFormatsDigestsAndBounds(){
        var e=good();assertThrows(IllegalArgumentException.class,()->envelope(e.format(),e.editor(),"company",0,1,e.componentFormat(),e.sha256(),e.payload(),null));
        assertThrows(IllegalArgumentException.class,()->envelope(e.format(),e.editor(),"company",2,1,e.componentFormat(),e.sha256(),e.payload(),null));
        assertThrows(IllegalArgumentException.class,()->envelope(e.format(),e.editor(),"company",1,1,e.componentFormat(),"0".repeat(64),e.payload(),null));
        assertThrows(IllegalArgumentException.class,()->envelope(e.format(),e.editor(),"company",1,1,e.componentFormat(),e.sha256(),"A".repeat(50000),null));
        assertThrows(IllegalArgumentException.class,()->envelope(e.format(),e.editor(),"company",1,1,e.componentFormat(),e.sha256(),"AQ",null));
    }
    @Test void copiesPayloadAndDiagnosticsDefensively(){
        var e=good();byte[] bytes=e.bytes();bytes[0]=99;assertArrayEquals(new byte[]{1},e.bytes());
        var graph=new EngineeringGraphDraft(List.of(),List.of(),List.of());assertThrows(UnsupportedOperationException.class,()->graph.diagnostics().add("forged"));
    }
    @Test void preservesAbsentValuesWithoutInferringQualification(){
        var m=new EngineeringNetworkDraft.Metadata(null,null,null);assertNull(m.title());assertNull(m.evidenceReference());
        assertTrue(Arrays.stream(EngineeringLocalExchange.class.getRecordComponents()).noneMatch(c->c.getName().equals("engineeringQualified")));
    }
    @Test void rejectsMixedEditorsAndRemapping(){
        var e=good();assertThrows(IllegalArgumentException.class,()->envelope(e.format(),EngineeringLocalExchange.Editor.GRAPH,"company",1,1,e.componentFormat(),e.sha256(),e.payload(),null));
        assertThrows(IllegalArgumentException.class,()->envelope(e.format(),e.editor(),"company",1,1,e.componentFormat(),e.sha256(),e.payload(),"other-branch"));
        // A portable origin is not silently normalized into a different scope.
        assertThrows(IllegalArgumentException.class,()->envelope(e.format(),e.editor()," company",1,1,e.componentFormat(),e.sha256(),e.payload(),null));
    }
    @Test void keepsOriginClaimsSeparateFromTargetAuthority(){
        for(var c:EngineeringLocalExchange.class.getRecordComponents())
            assertFalse(c.getName().toLowerCase(Locale.ROOT).matches(".*(actor|permission|credential|ticket|grant|token|role).*"));
        assertEquals("company",good().companyPartyId());
    }
}
