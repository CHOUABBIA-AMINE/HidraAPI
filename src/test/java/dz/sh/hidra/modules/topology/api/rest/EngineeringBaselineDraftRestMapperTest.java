/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringBaselineDraftRestMapperTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-03
 *
 * @Type        : Class
 * @Layer       : API Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest
 *
 * @Description : Verifies exact unqualified baseline request and response mapping.
 *
 */
package dz.sh.hidra.modules.topology.api.rest;

import dz.sh.hidra.modules.topology.api.rest.mapper.EngineeringBaselineDraftRestMapper;
import dz.sh.hidra.modules.topology.api.rest.request.EngineeringBaselineDraftRequests;
import dz.sh.hidra.modules.topology.api.rest.response.EngineeringBaselineDraftResponses;
import dz.sh.hidra.modules.topology.application.port.in.EngineeringBaselineDraftUseCase;
import dz.sh.hidra.modules.topology.domain.model.EngineeringBaselineManifest;
import dz.sh.hidra.modules.topology.domain.model.EngineeringBaselineManifest.Diagnostic;
import dz.sh.hidra.modules.topology.domain.model.EngineeringBaselineManifest.Finding;
import dz.sh.hidra.modules.topology.domain.model.EngineeringBaselineManifest.Kind;
import dz.sh.hidra.modules.topology.domain.model.EngineeringBaselineManifest.Pin;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EngineeringBaselineDraftRestMapperTest {
    private static final Instant FROM=Instant.parse("2026-11-03T12:00:00Z");
    private static final UUID MANIFEST=UUID.fromString("00000000-0000-0000-0000-000000000605");
    private static final UUID REQUEST=UUID.fromString("00000000-0000-0000-0000-000000000606");
    private static EngineeringBaselineDraftRequests.SourcePin source(){
        return new EngineeringBaselineDraftRequests.SourcePin(
            "NETWORK_PUBLICATION","context","context","revision","a".repeat(64));
    }
    @Test void createPreservesExactSelectionAndDoesNotClaimQualification(){
        var command=EngineeringBaselineDraftRestMapper.create(new EngineeringBaselineDraftRequests.Create(
            MANIFEST,"company","pipeline",REQUEST,FROM,null,List.of(source())));
        assertEquals(EngineeringBaselineDraftUseCase.Action.CREATE,command.action());
        assertEquals(0,command.expectedVersion());
        assertEquals(REQUEST,command.requestId());
        assertEquals(1,command.candidate().version());
        assertEquals(Kind.NETWORK_PUBLICATION,command.candidate().pins().getFirst().kind());
        assertFalse(command.candidate().engineeringQualified());
        assertFalse(command.candidate().authoritative());
        assertFalse(command.candidate().frozen());
    }
    @Test void updateRequiresExactExpectedRevisionAndRequest(){
        var request=new EngineeringBaselineDraftRequests.Update("company","pipeline",4,REQUEST,FROM,null,List.of(source()));
        var command=EngineeringBaselineDraftRestMapper.update(MANIFEST,request);
        assertEquals(EngineeringBaselineDraftUseCase.Action.UPDATE,command.action());
        assertEquals(5,command.candidate().version());
        assertThrows(IllegalArgumentException.class,()->EngineeringBaselineDraftRestMapper.update(MANIFEST,
            new EngineeringBaselineDraftRequests.Update("company","pipeline",Long.MAX_VALUE,REQUEST,FROM,null,List.of(source()))));
        assertThrows(IllegalArgumentException.class,()->EngineeringBaselineDraftRestMapper.update(MANIFEST,
            new EngineeringBaselineDraftRequests.Update("company","pipeline",-1,REQUEST,FROM,null,List.of(source()))));
        assertThrows(IllegalArgumentException.class,()->EngineeringBaselineDraftRestMapper.update(MANIFEST,
            new EngineeringBaselineDraftRequests.Update("company","pipeline",1,null,FROM,null,List.of(source()))));
    }
    @Test void historicalReadAndCurrentValidationRemainNonMutating(){
        var read=EngineeringBaselineDraftRestMapper.read(MANIFEST,"company","pipeline",3L,false);
        var validate=EngineeringBaselineDraftRestMapper.read(MANIFEST,"company","pipeline",null,true);
        assertEquals(EngineeringBaselineDraftUseCase.Action.READ,read.action());
        assertEquals(3L,read.revision());
        assertEquals(EngineeringBaselineDraftUseCase.Action.VALIDATE,validate.action());
        assertNull(validate.revision());
        assertFalse(read.mutating());assertFalse(validate.mutating());
        assertThrows(IllegalArgumentException.class,
            ()->EngineeringBaselineDraftRestMapper.read(MANIFEST,"company","pipeline",0L,false));
    }
    @Test void responseSeparatesCapturedAndCurrentSourceDiagnostics(){
        var model=new EngineeringBaselineManifest(MANIFEST,"company","pipeline",2,FROM,null,
            List.of(new Pin(Kind.NETWORK_PUBLICATION,"context","context","revision","a".repeat(64))));
        var captured=new EngineeringBaselineManifest.Completeness(List.of(
            new Diagnostic(Finding.ROUTE_PROFILE_NOT_SUPPLIED,Kind.NETWORK_PUBLICATION,"context")));
        var current=new EngineeringBaselineManifest.Completeness(List.of(
            new Diagnostic(Finding.SOURCE_NOT_CURRENTLY_ELIGIBLE,Kind.NETWORK_PUBLICATION,"context")));
        var result=new EngineeringBaselineDraftUseCase.Result(model,"b".repeat(64),captured,current,false);
        EngineeringBaselineDraftResponses.Draft response=EngineeringBaselineDraftRestMapper.response(result);
        assertEquals(model.manifestId(),response.manifestId());
        assertEquals(2,response.version());
        assertEquals(FROM,response.effectiveFrom());
        assertEquals("b".repeat(64),response.canonicalSha256());
        assertEquals("ROUTE_PROFILE_NOT_SUPPLIED",response.capturedCompleteness().findings().getFirst().finding());
        assertEquals("SOURCE_NOT_CURRENTLY_ELIGIBLE",response.currentCompleteness().findings().getFirst().finding());
        assertFalse(response.capturedCompleteness().complete());
        assertFalse(response.currentCompleteness().complete());
        assertFalse(response.engineeringQualified());
        assertFalse(response.authoritative());
        assertFalse(response.frozen());
        assertEquals("context",response.pins().getFirst().sourceId());
    }
    @Test void rejectsUnrecognizedKindsMalformedDigestsAndDuplicatePins(){
        assertThrows(IllegalArgumentException.class,()->EngineeringBaselineDraftRestMapper.create(
            new EngineeringBaselineDraftRequests.Create(MANIFEST,"company","pipeline",REQUEST,FROM,null,
                List.of(new EngineeringBaselineDraftRequests.SourcePin("BOGUS","context","context","revision","a".repeat(64))))));
        assertThrows(IllegalArgumentException.class,()->EngineeringBaselineDraftRestMapper.create(
            new EngineeringBaselineDraftRequests.Create(MANIFEST,"company","pipeline",REQUEST,FROM,null,
                List.of(new EngineeringBaselineDraftRequests.SourcePin("NETWORK_PUBLICATION","context","context","revision","invalid")))));
        assertThrows(IllegalArgumentException.class,()->EngineeringBaselineDraftRestMapper.create(
            new EngineeringBaselineDraftRequests.Create(MANIFEST,"company","pipeline",REQUEST,FROM,null,
                List.of(source(),source()))));
    }
    @Test void rejectsMissingScopeRequestAndInvalidEffectiveTimes(){
        assertThrows(NullPointerException.class,()->EngineeringBaselineDraftRestMapper.create(null));
        assertThrows(NullPointerException.class,()->EngineeringBaselineDraftRestMapper.create(
            new EngineeringBaselineDraftRequests.Create(MANIFEST,"company","pipeline",REQUEST,FROM,null,null)));
        assertThrows(IllegalArgumentException.class,()->EngineeringBaselineDraftRestMapper.create(
            new EngineeringBaselineDraftRequests.Create(MANIFEST," company","pipeline",REQUEST,FROM,null,List.of())));
        assertThrows(IllegalArgumentException.class,()->EngineeringBaselineDraftRestMapper.create(
            new EngineeringBaselineDraftRequests.Create(MANIFEST,"company","pipeline",REQUEST,
                FROM.plusNanos(1),null,List.of())));
        assertThrows(IllegalArgumentException.class,()->EngineeringBaselineDraftRestMapper.create(
            new EngineeringBaselineDraftRequests.Create(MANIFEST,"company","pipeline",REQUEST,FROM,FROM,List.of())));
        assertThrows(IllegalArgumentException.class,()->EngineeringBaselineDraftRestMapper.create(
            new EngineeringBaselineDraftRequests.Create(MANIFEST,"company","pipeline",null,FROM,null,List.of())));
    }
}
