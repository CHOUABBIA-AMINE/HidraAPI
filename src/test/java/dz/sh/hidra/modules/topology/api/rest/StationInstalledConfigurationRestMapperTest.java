/**
 * HidraAPI — strict, explicit station configuration wire/domain mapping tests.
 */
package dz.sh.hidra.modules.topology.api.rest;

import dz.sh.hidra.modules.topology.api.rest.mapper.StationInstalledConfigurationRestMapper;
import dz.sh.hidra.modules.topology.api.rest.request.StationInstalledConfigurationRequests;
import dz.sh.hidra.modules.topology.api.rest.request.StationInstalledConfigurationRequests.*;
import dz.sh.hidra.modules.topology.application.port.in.StationInstalledConfigurationUseCase;
import dz.sh.hidra.modules.topology.domain.model.StationInstalledConfiguration;
import dz.sh.hidra.modules.topology.domain.model.StationInternalGraph;
import dz.sh.hidra.modules.topology.domain.model.StationInternalGraphRevision;
import dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.StationInternalGraphRevisionCodec;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StationInstalledConfigurationRestMapperTest {
    private static final String WHEN="2026-02-01T00:00:00Z";
    private static final Instant NOW=Instant.parse(WHEN);
    private static GraphRevision graph(){
        return new GraphRevision("facility","graph-r1",
            new Graph("facility","graph-r1","COMPLETE",
                List.of(new Header("suction","SUCTION","s"),new Header("discharge","DISCHARGE","d")),
                List.of(new Unit("position","facility","i","o")),
                List.of(new Edge("in","s","i"),new Edge("out","o","d"))),
            "SYNTHETIC",WHEN,WHEN,null,"synthetic:graph",null,"STRUCTURALLY_QUALIFIED");
    }
    private static StationInternalGraphRevision domainGraph(){
        return new StationInternalGraphRevision("facility","graph-r1",
            new StationInternalGraph("facility","graph-r1",StationInternalGraph.Status.COMPLETE,
                List.of(new StationInternalGraph.Header("suction",StationInternalGraph.HeaderKind.SUCTION,"s"),
                    new StationInternalGraph.Header("discharge",StationInternalGraph.HeaderKind.DISCHARGE,"d")),
                List.of(new StationInternalGraph.Unit("position","facility","i","o")),
                List.of(new StationInternalGraph.Edge("in","s","i"),new StationInternalGraph.Edge("out","o","d"))),
            StationInternalGraphRevision.SourceClassification.SYNTHETIC,NOW,NOW,null,
            "synthetic:graph",null,StationInternalGraphRevision.Qualification.STRUCTURALLY_QUALIFIED);
    }
    private static String digest(){
        var codec=new StationInternalGraphRevisionCodec();
        return codec.sha256(codec.encode(domainGraph()));
    }
    private static Save body(){
        return new Save("request","company","config-r1",0L,1L,graph(),
            StationInternalGraphRevisionCodec.FORMAT,digest(),WHEN,null,
            "synthetic:station",List.of(new Position("position","CONNECTED","unit",
                "installation",1L,WHEN,null,"synthetic:installation")),
            List.of("in","out"));
    }
    @Test void saveMapsExplicitWireOnlyAndRecomputesPinnedGraphDigest(){
        var c=StationInstalledConfigurationRestMapper.save("facility",body());
        assertEquals(StationInstalledConfigurationUseCase.Operation.SAVE,c.operation());
        assertEquals("company",c.companyPartyId());
        assertEquals("facility",c.facilityId());
        assertEquals(1,c.declaration().selectedEpoch());
        assertEquals("server-attributed",c.declaration().actorUserId());
        assertEquals("server-attributed",c.declaration().actorBindingId());
        assertEquals(digest(),c.declaration().graphSha256());
        assertEquals(1,c.declaration().positions().size());
        var out=StationInstalledConfigurationRestMapper.from(
            new StationInstalledConfigurationUseCase.Result(c.declaration(),List.of(),false));
        assertEquals(1,out.snapshot().selectedEpoch());
        assertTrue(out.snapshot().structuralDiagnostics().isEmpty());
        assertFalse(out.engineeringQualified());
        assertFalse(out.operatingStateValidated());
        assertEquals("CONNECTED",out.snapshot().positions().getFirst().disposition());
    }
    @Test void malformedHashSyntaxOrGraphScopeIsRejectedBeforeProtectedOwner(){
        var b=body();
        assertThrows(IllegalArgumentException.class,()->StationInstalledConfigurationRestMapper.save("foreign",b));
        var forged=new Save(b.requestId(),b.companyPartyId(),b.revisionId(),b.expectedEpoch(),
            b.graphHeadEpoch(),b.pinnedGraph(),b.graphPayloadFormat(),"noncanonical-digest",
            b.effectiveFrom(),b.effectiveUntil(),b.sourceReference(),b.positions(),b.activeEdgeIds());
        assertThrows(IllegalArgumentException.class,()->StationInstalledConfigurationRestMapper.save("facility",forged));
    }
    @Test void canonicalInstantsAndHistoryEpochCursorsMustBeExact(){
        for(String invalid:List.of("2026-02-01T01:00:00+01:00",
            "2026-02-01T00:00:00.123456789Z","2026-02-01t00:00:00z"))
            assertThrows(IllegalArgumentException.class,()->StationInstalledConfigurationRestMapper.instant(invalid));
        for(String cursor:List.of("0","01","-1","1.0","+1","a"," 1"))
            assertThrows(IllegalArgumentException.class,()->StationInstalledConfigurationRestMapper.history(
                "facility","company",20,cursor));
        assertEquals(2L,StationInstalledConfigurationRestMapper.history(
            "facility","company",20,"2").beforeEpoch());
        assertEquals(100,StationInstalledConfigurationRestMapper.history(
            "facility","company",100,null).limit());
    }
    @Test void exclusiveHistoryCursorUsesOnlyFullyAuthorizedExtraRow(){
        var first=StationInstalledConfigurationRestMapper.save("facility",body()).declaration();
        var second=new StationInstalledConfiguration(first.companyPartyId(),first.facilityId(),
            "config-r2","request-r2",1,2,1,first.pinnedGraph(),
            first.graphPayloadFormat(),first.graphSha256(),first.effectiveFrom(),
            first.effectiveUntil(),first.recordedAt(),first.sourceReference(),
            first.actorBindingId(),first.actorSessionId(),first.actorUserId(),
            first.positions(),first.activeEdgeIds());
        var result=StationInstalledConfigurationRestMapper.from(
            new StationInstalledConfigurationUseCase.Result(null,List.of(second,first),false),1);
        assertEquals(1,result.history().size());
        assertEquals("2",result.nextCursor());
        assertEquals("config-r2",result.history().getFirst().revisionId());
    }
}
