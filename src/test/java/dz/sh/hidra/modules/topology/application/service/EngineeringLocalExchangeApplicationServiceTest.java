/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringLocalExchangeApplicationServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-25
 *
 * @Type        : Class
 * @Layer       : Application Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.service
 *
 * @Description : Implements bounded local editor exchange through existing protected owners.
 *
 */
package dz.sh.hidra.modules.topology.application.service;

import dz.sh.hidra.modules.topology.application.port.in.*;
import dz.sh.hidra.modules.topology.application.port.in.EngineeringLocalExchangeUseCase.*;
import dz.sh.hidra.modules.topology.application.port.out.EngineeringLocalExchangeCodecPort.*;
import dz.sh.hidra.modules.topology.application.model.*;
import dz.sh.hidra.modules.topology.domain.model.*;
import dz.sh.hidra.modules.topology.domain.model.EngineeringLocalExchange.Editor;
import dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.*;
import java.util.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

class EngineeringLocalExchangeApplicationServiceTest {
    private final UUID context=UUID.randomUUID(),draft=UUID.randomUUID();
    private EngineeringNetworkDraftUseCase metadata;private EngineeringGraphDraftUseCase graphs;private SurveyRouteProfileUseCase surveys;
    private EngineeringLocalExchangeApplicationService service;
    @BeforeEach void setup(){
        metadata=mock(EngineeringNetworkDraftUseCase.class);graphs=mock(EngineeringGraphDraftUseCase.class);surveys=mock(SurveyRouteProfileUseCase.class);
        var base=new EngineeringNetworkDraft(context,"company","network",draft,3,EngineeringLocalExchangeCodecTest.content(Editor.METADATA).metadata());
        when(metadata.execute(any())).thenAnswer(i->{var c=(EngineeringNetworkDraftUseCase.Command)i.getArgument(0);return new EngineeringNetworkDraftUseCase.Result(c.kind()==EngineeringNetworkDraftUseCase.Kind.UPDATE?base.updated(c.metadata()):base,false,List.of());});
        when(graphs.executeGraph(any())).thenAnswer(i->{var c=(EngineeringGraphDraftUseCase.Command)i.getArgument(0);boolean update=c.kind()==EngineeringGraphDraftUseCase.Kind.UPDATE;
            return new EngineeringGraphDraftUseCase.Result(draft,update?4:3,update?4L:2L,EngineeringLocalExchangeCodecTest.graphHash(),EngineeringLocalExchangeCodecTest.graph(),List.of("PHYSICAL_VALUES_NOT_SUPPLIED"));});
        when(surveys.executeSurvey(any())).thenAnswer(i->{var c=(SurveyRouteProfileUseCase.Command)i.getArgument(0);boolean update=c.kind()==SurveyRouteProfileUseCase.Kind.UPDATE;
            var bytes=new SurveyRouteProfileCodec().encode(EngineeringLocalExchangeCodecTest.survey());
            return new SurveyRouteProfileUseCase.Result(draft,"pipe",update?4:3,update?4L:3L,SurveyRouteProfileCodec.sha256(bytes),2L,EngineeringLocalExchangeCodecTest.graphHash(),EngineeringLocalExchangeCodecTest.survey(),List.of("PHYSICAL_QUALIFICATION_NOT_ESTABLISHED"));});
        service=new EngineeringLocalExchangeApplicationService(metadata,graphs,surveys,new EngineeringLocalExchangeCodec());
    }
    private Command command(Operation op,Editor e,Long expected){return new Command(op,e,"company","network",draft,e==Editor.SURVEY?"pipe":null,null,expected,
        expected==null?null:EngineeringLocalExchangeCodecTest.pack(e,context,draft));}
    @Test void exportRequiresExistingNativeReadBeforeReturningPayload(){
        var r=service.execute(command(Operation.EXPORT,Editor.METADATA,null));assertEquals(context,r.bundle().contextId());
        var order=inOrder(metadata);order.verify(metadata).execute(argThat(c->c.kind()==EngineeringNetworkDraftUseCase.Kind.READ));
        doThrow(new EngineeringNetworkDraftFailure(EngineeringNetworkDraftFailure.Reason.DENIED)).when(metadata).execute(any());
        assertThrows(EngineeringNetworkDraftFailure.class,()->service.execute(command(Operation.EXPORT,Editor.METADATA,null)));
    }
    @Test void dryRunUsesReadOnlyOwnerOperationsAndReturnsNoAdmission(){
        for(var e:Editor.values()){var r=service.execute(command(Operation.DRY_RUN,e,3L));assertNull(r.targetVersion());assertNull(r.bundle());assertTrue(r.diagnostics().contains("DRY_RUN_ADVISORY"));}
        verify(metadata,never()).execute(argThat(c->c.kind()==EngineeringNetworkDraftUseCase.Kind.UPDATE));
        verify(graphs,never()).executeGraph(argThat(c->c.kind()==EngineeringGraphDraftUseCase.Kind.UPDATE));verifyNoInteractions(surveys);
    }
    @Test void importDispatchesOneExistingProtectedUpdateWithExplicitCas(){
        for(var e:Editor.values()){var r=service.execute(command(Operation.IMPORT,e,3L));assertEquals(4L,r.targetVersion().longValue());assertEquals(3,r.observedVersion());assertEquals("IMPORTED",r.state());}
        verify(metadata,times(1)).execute(argThat(c->c.kind()==EngineeringNetworkDraftUseCase.Kind.UPDATE && c.expectedVersion()==3));
        verify(graphs,times(1)).executeGraph(argThat(c->c.kind()==EngineeringGraphDraftUseCase.Kind.UPDATE && c.expectedVersion()==3));
        verify(surveys,times(1)).executeSurvey(argThat(c->c.kind()==SurveyRouteProfileUseCase.Kind.UPDATE && c.expectedVersion()==3));
    }
    @Test void staleTargetVersionNeverInvokesAnUpdate(){
        assertThrows(EngineeringLocalExchangeFailure.class,()->service.execute(command(Operation.IMPORT,Editor.GRAPH,2L)));
        verify(metadata,never()).execute(argThat(c->c.kind()==EngineeringNetworkDraftUseCase.Kind.UPDATE));verifyNoInteractions(graphs,surveys);
    }
    @Test void surveyImportRequiresTheCurrentGraphDigestAndPreservesBindingProvenance(){
        var c=command(Operation.IMPORT,Editor.SURVEY,3L);var p=c.bundle();var bad=new EngineeringLocalExchange(p.format(),p.editor(),p.companyPartyId(),p.pipelineSystemId(),p.contextId(),p.draftId(),p.sourceVersion(),p.componentVersion(),p.componentFormat(),p.sha256(),p.payload(),p.branchId(),p.boundGraphVersion(),"0".repeat(64));
        assertThrows(EngineeringLocalExchangeFailure.class,()->service.execute(new Command(c.operation(),c.editor(),c.companyPartyId(),c.pipelineSystemId(),c.draftId(),c.branchId(),null,3L,bad)));
        verifyNoInteractions(surveys);
        doReturn(new EngineeringGraphDraftUseCase.Result(draft,3,3L,EngineeringLocalExchangeCodecTest.graphHash(),EngineeringLocalExchangeCodecTest.graph(),List.of())).when(graphs).executeGraph(any());
        doReturn(new SurveyRouteProfileUseCase.Result(draft,"pipe",4,4L,p.sha256(),3L,p.boundGraphSha256(),EngineeringLocalExchangeCodecTest.survey(),List.of())).when(surveys).executeSurvey(any());
        var r=service.execute(c);assertEquals(2L,r.bundle().boundGraphVersion().longValue());assertEquals(3L,r.boundGraphVersion().longValue());
    }
    @Test void revocationAndIndeterminateFailureNeverRetryOrReturnSuccess(){
        for(var reason:List.of(EngineeringNetworkDraftFailure.Reason.DENIED,EngineeringNetworkDraftFailure.Reason.INDETERMINATE)){
            clearInvocations(metadata);doAnswer(i->{var c=(EngineeringNetworkDraftUseCase.Command)i.getArgument(0);
                if(c.kind()==EngineeringNetworkDraftUseCase.Kind.UPDATE)throw new EngineeringNetworkDraftFailure(reason);
                return new EngineeringNetworkDraftUseCase.Result(new EngineeringNetworkDraft(context,"company","network",draft,3,EngineeringLocalExchangeCodecTest.content(Editor.METADATA).metadata()),false,List.of());}).when(metadata).execute(any());
            assertThrows(EngineeringNetworkDraftFailure.class,()->service.execute(command(Operation.IMPORT,Editor.METADATA,3L)));
            verify(metadata,times(1)).execute(argThat(c->c.kind()==EngineeringNetworkDraftUseCase.Kind.UPDATE));
        }
    }
    @Test void capabilitiesDescribeSupportWithoutGrantingWriteAuthority(){
        var r=service.execute(command(Operation.CAPABILITIES,Editor.GRAPH,null));assertEquals("AUTHORED",r.state());assertNull(r.targetVersion());assertNull(r.bundle());
        assertEquals("NOT_EVALUATED",dz.sh.hidra.modules.topology.api.rest.mapper.EngineeringLocalExchangeRestMapper.response(r).writeAuthorization());
        verify(metadata,never()).execute(argThat(c->c.kind()==EngineeringNetworkDraftUseCase.Kind.UPDATE));
    }
    @Test void unsupportedEditorsBatchesAndForeignScopesNeverReachAWrite(){
        assertThrows(IllegalArgumentException.class,()->Editor.valueOf("BATCH"));
        assertThrows(IllegalArgumentException.class,()->new Content(EngineeringLocalExchangeCodecTest.content(Editor.METADATA).metadata(),EngineeringLocalExchangeCodecTest.graph(),null));
        var p=EngineeringLocalExchangeCodecTest.pack(Editor.METADATA,UUID.randomUUID(),draft);
        assertThrows(EngineeringLocalExchangeFailure.class,()->service.execute(new Command(Operation.IMPORT,Editor.METADATA,"company","network",draft,null,null,3L,p)));
        verify(metadata,never()).execute(argThat(c->c.kind()==EngineeringNetworkDraftUseCase.Kind.UPDATE));
    }
}
