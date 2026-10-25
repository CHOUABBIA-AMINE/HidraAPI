/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringLocalExchangeCodecTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-25
 *
 * @Type        : Class
 * @Layer       : Infrastructure Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.adapter
 *
 * @Description : Implements bounded local editor exchange through existing protected owners.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.topology.domain.model.*;
import dz.sh.hidra.modules.topology.domain.model.EngineeringLocalExchange.Editor;
import dz.sh.hidra.modules.topology.application.port.out.EngineeringLocalExchangeCodecPort.*;
import java.util.*;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EngineeringLocalExchangeCodecTest {
    public static EngineeringGraphDraft graph(){return new EngineeringGraphDraft(
        List.of(new EngineeringGraphDraft.Node("a",EngineeringGraphDraft.NodeKind.JUNCTION,null),new EngineeringGraphDraft.Node("b",EngineeringGraphDraft.NodeKind.JUNCTION,null)),
        List.of(new EngineeringGraphDraft.Port("a-port","a",EngineeringGraphDraft.PortDirection.BIDIRECTIONAL),new EngineeringGraphDraft.Port("b-port","b",EngineeringGraphDraft.PortDirection.BIDIRECTIONAL)),
        List.of(new EngineeringGraphDraft.Branch("pipe",EngineeringGraphDraft.BranchKind.PIPE,"a-port","b-port")));}
    public static SurveyRouteProfile survey(){return new SurveyRouteProfile("pipe",new SurveyRouteProfile.Crs("LOCAL","declared",
        List.of(new SurveyRouteProfile.Axis("north","metre"),new SurveyRouteProfile.Axis("east","metre"))),null," exact origin ",SurveyRouteProfile.Direction.FROM_TO," evidence ",
        List.of(new SurveyRouteProfile.Point("p1",new BigDecimal("1.00"),new BigDecimal("2.000"),new BigDecimal("0.00"),null,BigDecimal.ONE,BigDecimal.ONE,null),
                new SurveyRouteProfile.Point("p2",new BigDecimal("3.000"),new BigDecimal("4.00"),new BigDecimal("10.000"),null,BigDecimal.ONE,BigDecimal.ONE,null)),List.of());}
    public static Content content(Editor e){return switch(e){case METADATA->new Content(new EngineeringNetworkDraft.Metadata(null," text é ",null),null,null);
        case GRAPH->new Content(null,graph(),null);case SURVEY->new Content(null,null,survey());};}
    public static String graphHash(){return EngineeringLocalExchange.digest(new EngineeringGraphDraftCodec().encode(graph()));}
    public static Origin origin(Editor e,UUID context,UUID draft){return new Origin(e,"company","network",context,draft,3,e==Editor.GRAPH?2:3,
        e==Editor.SURVEY?"pipe":null,e==Editor.SURVEY?2L:null,e==Editor.SURVEY?graphHash():null);}
    public static EngineeringLocalExchange pack(Editor e,UUID context,UUID draft){return new EngineeringLocalExchangeCodec().encode(origin(e,context,draft),content(e));}
    @Test void roundTripsAllThreeOriginalCanonicalFormats(){
        var codec=new EngineeringLocalExchangeCodec();for(var e:Editor.values()){
            var origin=origin(e,UUID.randomUUID(),UUID.randomUUID());var content=content(e);var pack=codec.encode(origin,content);
            assertEquals(content,codec.decode(pack));assertEquals(pack,codec.encode(origin,codec.decode(pack)));
        }
    }
    @Test void preservesSurveyDecimalScaleUnitsAndMissingElevation(){
        var actual=new EngineeringLocalExchangeCodec().decode(pack(Editor.SURVEY,UUID.randomUUID(),UUID.randomUUID())).survey();
        assertEquals(new BigDecimal("1.00"),actual.points().get(0).firstCoordinate());assertEquals(2,actual.points().get(0).firstCoordinate().scale());
        assertEquals("metre",actual.horizontalCrs().axes().get(0).unitIdentifier());assertNull(actual.points().get(0).elevation());assertEquals(" evidence ",actual.evidenceReference());
    }
    @Test void rejectsDigestBase64FormatAndTrailingByteTampering(){
        var e=pack(Editor.GRAPH,UUID.randomUUID(),UUID.randomUUID());byte[] b=Arrays.copyOf(e.bytes(),e.bytes().length+1);
        var altered=new EngineeringLocalExchange(e.format(),e.editor(),e.companyPartyId(),e.pipelineSystemId(),e.contextId(),e.draftId(),e.sourceVersion(),e.componentVersion(),e.componentFormat(),EngineeringLocalExchange.digest(b),Base64.getEncoder().encodeToString(b),null,null,null);
        assertThrows(IllegalArgumentException.class,()->new EngineeringLocalExchangeCodec().decode(altered));
        assertThrows(IllegalArgumentException.class,()->new EngineeringLocalExchange("unsupported",e.editor(),e.companyPartyId(),e.pipelineSystemId(),e.contextId(),e.draftId(),3,2,e.componentFormat(),e.sha256(),e.payload(),null,null,null));
    }
    @Test void rejectsMalformedUtf8NoncanonicalAndOversizedPayloads(){
        var codec=new EngineeringLocalExchangeCodec();byte[] b={0,0,0,1,0,0,0,1,(byte)0xff,-1,-1,-1,-1,-1,-1,-1,-1};
        var e=new EngineeringLocalExchange(EngineeringLocalExchange.FORMAT,Editor.METADATA,"company","network",UUID.randomUUID(),UUID.randomUUID(),1,1,Editor.METADATA.componentFormat(),EngineeringLocalExchange.digest(b),Base64.getEncoder().encodeToString(b),null,null,null);
        assertThrows(IllegalArgumentException.class,()->codec.decode(e));
        byte[] tooLarge=new byte[32769];assertThrows(IllegalArgumentException.class,()->new EngineeringLocalExchange(e.format(),e.editor(),"company","network",e.contextId(),e.draftId(),1,1,e.componentFormat(),EngineeringLocalExchange.digest(tooLarge),Base64.getEncoder().encodeToString(tooLarge),null,null,null));
    }
    @Test void retainsMetadataNullsAndGraphIdentityNamespaces(){
        var codec=new EngineeringLocalExchangeCodec();assertNull(codec.decode(pack(Editor.METADATA,UUID.randomUUID(),UUID.randomUUID())).metadata().title());
        assertEquals(graph(),codec.decode(pack(Editor.GRAPH,UUID.randomUUID(),UUID.randomUUID())).graph());
    }
    @Test void keepsOriginAndComponentVersionProvenanceExplicit(){
        var e=pack(Editor.SURVEY,UUID.randomUUID(),UUID.randomUUID());assertEquals(3,e.sourceVersion());assertEquals(3,e.componentVersion());assertEquals(2L,e.boundGraphVersion().longValue());assertEquals(graphHash(),e.boundGraphSha256());
    }
    @Test void neverConvertsCoordinatesUnitsOrPhysicalValues(){
        var expected=content(Editor.SURVEY);var codec=new EngineeringLocalExchangeCodec();var actual=codec.decode(pack(Editor.SURVEY,UUID.randomUUID(),UUID.randomUUID()));
        assertArrayEquals(new SurveyRouteProfileCodec().encode(expected.survey()),new SurveyRouteProfileCodec().encode(actual.survey()));assertEquals(expected,actual);
    }
}
