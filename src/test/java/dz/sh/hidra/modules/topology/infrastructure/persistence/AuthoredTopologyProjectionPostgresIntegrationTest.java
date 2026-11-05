/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuthoredTopologyProjectionPostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-04
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence
 *
 * @Description : Verifies synthetic fresh-schema Pipeline association mechanics; configured authority is qualified separately.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence;

import java.util.*;
import org.junit.jupiter.api.*;
import org.flywaydb.core.Flyway;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.*;
import org.springframework.transaction.support.TransactionTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.*;
import static org.junit.jupiter.api.Assertions.*;

import dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraft;
import dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.EngineeringGraphDraftCodec;
@Testcontainers(disabledWithoutDocker=true)
class AuthoredTopologyProjectionPostgresIntegrationTest {
    @Container static final PostgreSQLContainer<?> PG=new PostgreSQLContainer<>("postgres:16");
    static JdbcTemplate jdbc;
    @BeforeAll static void migrate(){jdbc=PipelineAuthoringPostgresIntegrationTest.database(PG);}
    private EngineeringGraphDraft graph(){return new EngineeringGraphDraft(List.of(new EngineeringGraphDraft.Node("from",EngineeringGraphDraft.NodeKind.JUNCTION,null),new EngineeringGraphDraft.Node("to",EngineeringGraphDraft.NodeKind.JUNCTION,null)),List.of(new EngineeringGraphDraft.Port("out","from",EngineeringGraphDraft.PortDirection.OUTLET),new EngineeringGraphDraft.Port("in","to",EngineeringGraphDraft.PortDirection.INLET)),List.of(new EngineeringGraphDraft.Branch("pipe",EngineeringGraphDraft.BranchKind.PIPE,"out","in")));}
    private UUID draft(PipelineAuthoringPostgresIntegrationTest.Fixture f){var context=UUID.randomUUID();var draft=UUID.randomUUID();jdbc.update("INSERT INTO public.hidra_topology_engineering_context(id,company_party_id,pipeline_system_id) VALUES (?,?,?)",context,f.company(),f.system());jdbc.update("INSERT INTO public.hidra_topology_engineering_draft(id,context_id,creation_request_id,request_sha256) VALUES (?,?,?,?)",draft,context,UUID.randomUUID(),"0".repeat(64));return draft;}
    private void metadata(UUID draft,int version){byte[] bytes="synthetic-schema-metadata-without-auth-claim".getBytes(java.nio.charset.StandardCharsets.UTF_8);String hash=new EngineeringGraphDraftCodec().sha256(bytes);jdbc.update("INSERT INTO public.hidra_topology_engineering_draft_version(draft_id,version,payload_format,canonical_payload,sha256,predecessor_sha256,actor_binding_id,actor_session_id,actor_user_id,party_generation,sampled_at,valid_until,backend_pid,transaction_id) VALUES (?,?,'HIDRA_ENGINEERING_METADATA_V1',?,?,?,?::uuid,?::uuid,'synthetic-schema-only',1,clock_timestamp(),clock_timestamp()+interval '1 hour',pg_backend_pid(),txid_current())",draft,version,bytes,hash,version==1?null:hash,UUID.randomUUID(),UUID.randomUUID());}
    private void snapshot(PipelineAuthoringPostgresIntegrationTest.Fixture f,UUID draft,int version,long pipelineRevision){var codec=new EngineeringGraphDraftCodec();byte[] bytes=codec.encode(graph());String hash=codec.sha256(bytes);metadata(draft,version);jdbc.update("INSERT INTO public.hidra_topology_engineering_graph_snapshot(draft_id,version,payload_format,canonical_payload,sha256,transaction_id) VALUES (?,?,'HIDRA_ENGINEERING_GRAPH_DRAFT_V1',?,?,txid_current())",draft,version,bytes,hash);jdbc.update("INSERT INTO public.hidra_topology_pipeline_graph_snapshot(draft_id,version,request_id,company_party_id,pipeline_system_id,graph_sha256,actor_user_id,transaction_id) VALUES (?,?,?,?,?,?,'synthetic-schema-only',txid_current())",draft,version,UUID.randomUUID(),f.company(),f.system(),hash);jdbc.update("INSERT INTO public.hidra_topology_pipeline_graph_binding(draft_id,version,branch_id,pipeline_id,pipeline_revision) VALUES (?,?,'pipe',?,?)",draft,version,f.pipeline(),pipelineRevision);}
    @Test void pinnedCanonicalLabelsAndLegacyGraphBytesSurviveHistoricalProjection(){var f=PipelineAuthoringPostgresIntegrationTest.seed(jdbc);PipelineAuthoringPostgresIntegrationTest.revision(jdbc,f,1,"Original");var draft=draft(f);var tx=new TransactionTemplate(new DataSourceTransactionManager(jdbc.getDataSource()));
        tx.executeWithoutResult(s->{metadata(draft,1);snapshot(f,draft,2,1);});
        tx.executeWithoutResult(s->{jdbc.update("UPDATE public.hidra_topology_pipeline SET name_en='Revised' WHERE id=?",f.pipeline());PipelineAuthoringPostgresIntegrationTest.revision(jdbc,f,2,"Revised");snapshot(f,draft,3,2);});
        for(int version:new int[]{2,3}){
            String label=jdbc.queryForObject("SELECT r.name_en FROM public.hidra_topology_pipeline_graph_binding b JOIN public.hidra_topology_pipeline_authoring_revision r ON r.pipeline_id=b.pipeline_id AND r.revision=b.pipeline_revision WHERE b.draft_id=? AND b.version=?",String.class,draft,version);assertEquals(version==2?"Original":"Revised",label);
            byte[] stored=jdbc.queryForObject("SELECT canonical_payload FROM public.hidra_topology_engineering_graph_snapshot WHERE draft_id=? AND version=?",byte[].class,draft,version);assertEquals(graph(),new EngineeringGraphDraftCodec().decode(stored));
        }
        assertThrows(org.springframework.dao.DataAccessException.class,()->jdbc.update("DELETE FROM public.hidra_topology_pipeline_graph_binding WHERE draft_id=?",draft));
    }
    @Test void fakeGraphDigestOrCrossCompanyAssociationCannotBePersisted(){var f=PipelineAuthoringPostgresIntegrationTest.seed(jdbc);PipelineAuthoringPostgresIntegrationTest.revision(jdbc,f,1,"Original");var draft=draft(f);var tx=new TransactionTemplate(new DataSourceTransactionManager(jdbc.getDataSource()));
        tx.executeWithoutResult(s->{metadata(draft,1);snapshot(f,draft,2,1);});
        assertThrows(org.springframework.dao.DataAccessException.class,()->jdbc.update("INSERT INTO public.hidra_topology_pipeline_graph_snapshot(draft_id,version,request_id,company_party_id,pipeline_system_id,graph_sha256,actor_user_id,transaction_id) VALUES (?,3,?,?,?,?,'synthetic',txid_current())",draft,UUID.randomUUID(),f.company(),f.system(),"0".repeat(64)));
        var foreign=PipelineAuthoringPostgresIntegrationTest.seed(jdbc);PipelineAuthoringPostgresIntegrationTest.revision(jdbc,foreign,1,"Original");
        assertThrows(org.springframework.dao.DataAccessException.class,()->jdbc.update("INSERT INTO public.hidra_topology_pipeline_graph_binding(draft_id,version,branch_id,pipeline_id,pipeline_revision) VALUES (?,2,'foreign',?,1)",draft,foreign.pipeline()));
    }
    @Test void canonicalReferenceCoverageRejectsMissingDuplicateAndDanglingBindings(){
        var g=graph();var reference=new EngineeringGraphDraft.PipelineReference("pipe","pipeline",null);
        assertEquals(List.of(reference),g.pipelineReferences(List.of(reference)));
        assertThrows(IllegalArgumentException.class,()->g.pipelineReferences(List.of()));
        assertThrows(IllegalArgumentException.class,()->g.pipelineReferences(List.of(reference,reference)));
        assertThrows(IllegalArgumentException.class,()->g.pipelineReferences(List.of(new EngineeringGraphDraft.PipelineReference("dangling","pipeline",null))));
    }

    private dz.sh.hidra.modules.topology.application.port.in.AuthoredTopologyViewUseCase.Discovery candidateChoices(List<String> ids) {
        return new dz.sh.hidra.modules.topology.application.port.in.AuthoredTopologyViewUseCase.Discovery("company","system",List.of(),0,0,50,List.of(),List.of(),List.of(),
            List.of(new dz.sh.hidra.modules.topology.application.port.in.AuthoredTopologyViewUseCase.Choice("kind","K","Kind","EQUIPMENT_KIND")),List.of(),ids);
    }
    @Test void discoveryRequiresIndividualUnitAuthorityAndNeverSerializesCandidateIdentities() throws Exception {
        var graphs=org.mockito.Mockito.mock(dz.sh.hidra.modules.topology.application.port.in.EngineeringGraphDraftUseCase.class);
        var auth=org.mockito.Mockito.mock(dz.sh.hidra.modules.topology.application.port.out.AuthoredTopologyAuthorizationPort.class);
        var units=org.mockito.Mockito.mock(dz.sh.hidra.modules.topology.application.port.in.InstalledUnitLifecycleUseCase.class);
        var scope=new dz.sh.hidra.modules.topology.application.port.in.PipelineAuthoringUseCase.Scope("company","system",UUID.randomUUID());
        org.mockito.Mockito.when(graphs.executeAuthoredDiscovery(scope,0,50)).thenReturn(candidateChoices(List.of("denied-private-unit","permitted")));
        org.mockito.Mockito.when(units.execute(org.mockito.ArgumentMatchers.any())).thenAnswer(call->{
            dz.sh.hidra.modules.topology.application.port.in.InstalledUnitLifecycleUseCase.Command command=call.getArgument(0);
            assertEquals(dz.sh.hidra.modules.topology.application.port.in.InstalledUnitLifecycleUseCase.Operation.READ_UNIT,command.operation());
            assertEquals("company",command.companyPartyId());
            if(command.unitId().equals("denied-private-unit"))throw new dz.sh.hidra.modules.topology.application.model.InstalledUnitLifecycleFailure(dz.sh.hidra.modules.topology.application.model.InstalledUnitLifecycleFailure.Kind.DENIED,null);
            var unit=new dz.sh.hidra.modules.topology.domain.model.InstalledUnit("permitted","company","kind",dz.sh.hidra.modules.topology.domain.model.InstalledUnit.Evidence.unknown(),dz.sh.hidra.modules.topology.domain.model.InstalledUnit.Evidence.unknown(),dz.sh.hidra.modules.topology.domain.model.InstalledUnit.Evidence.unknown(),null);
            return new dz.sh.hidra.modules.topology.application.port.in.InstalledUnitLifecycleUseCase.Result(unit,null,null,List.of(),false);
        });
        var result=new dz.sh.hidra.modules.topology.application.service.PipelineAuthoringApplicationService(graphs,auth,units).discover(scope,0,50);
        org.mockito.Mockito.verify(auth).requireNativeAuthentication();org.mockito.Mockito.verify(units,org.mockito.Mockito.times(2)).execute(org.mockito.ArgumentMatchers.any());
        assertEquals(List.of("permitted"),result.installedUnits().stream().map(dz.sh.hidra.modules.topology.application.port.in.AuthoredTopologyViewUseCase.Choice::id).toList());
        assertTrue(result.candidateInstalledUnitIds().isEmpty());
        String json=tools.jackson.databind.json.JsonMapper.builder().build().writeValueAsString(dz.sh.hidra.modules.topology.api.rest.response.AuthoredTopologyResponses.Discovery.from(result));
        assertFalse(json.contains("denied-private-unit"));assertFalse(json.contains("candidateInstalledUnitIds"));assertFalse(json.contains("manufacturer"));assertTrue(json.contains("equipmentKinds"));
    }
    @Test void discoveryDoesNotReturnPartialChoicesOnIndeterminateUnitAuthority() {
        var graphs=org.mockito.Mockito.mock(dz.sh.hidra.modules.topology.application.port.in.EngineeringGraphDraftUseCase.class);
        var auth=org.mockito.Mockito.mock(dz.sh.hidra.modules.topology.application.port.out.AuthoredTopologyAuthorizationPort.class);
        var units=org.mockito.Mockito.mock(dz.sh.hidra.modules.topology.application.port.in.InstalledUnitLifecycleUseCase.class);
        var scope=new dz.sh.hidra.modules.topology.application.port.in.PipelineAuthoringUseCase.Scope("company","system",UUID.randomUUID());
        org.mockito.Mockito.when(graphs.executeAuthoredDiscovery(scope,0,50)).thenReturn(candidateChoices(List.of("private")));
        org.mockito.Mockito.when(units.execute(org.mockito.ArgumentMatchers.any())).thenThrow(new dz.sh.hidra.modules.topology.application.model.InstalledUnitLifecycleFailure(dz.sh.hidra.modules.topology.application.model.InstalledUnitLifecycleFailure.Kind.INDETERMINATE,null));
        var service=new dz.sh.hidra.modules.topology.application.service.PipelineAuthoringApplicationService(graphs,auth,units);
        assertEquals(dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure.Reason.UNAVAILABLE,assertThrows(dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure.class,()->service.discover(scope,0,50)).reason());
        assertThrows(IllegalArgumentException.class,()->service.discover(scope,0,201));
        org.mockito.Mockito.clearInvocations(graphs,units);
        org.mockito.Mockito.doThrow(new org.springframework.security.access.AccessDeniedException("Denied")).when(auth).requireNativeAuthentication();
        assertThrows(org.springframework.security.access.AccessDeniedException.class,()->service.discover(scope,0,50));
        org.mockito.Mockito.verifyNoInteractions(graphs,units);
    }
    private void discoveryUnit(String id,String company,String kind) {
        jdbc.update("INSERT INTO public.hidra_topology_installed_unit(id,company_party_id,equipment_kind_id,manufacturer_party_verification,manufacturer_model_verification,serial_verification) VALUES (?,?,?,'UNKNOWN','UNKNOWN','UNKNOWN')",id,company,kind);
    }
    @Test void discoverySqlRestrictsCompanyActiveKindsAndDeterministicBoundedPages() {
        String company="discovery-"+UUID.randomUUID(),active="kind-"+UUID.randomUUID(),inactive="kind-"+UUID.randomUUID();
        for(String kind:List.of(active,inactive))jdbc.update("INSERT INTO public.hidra_topology_equipment_type(id,code,name,status,created_at,updated_at) VALUES (?,?,?, ?,now(),now())",kind,kind,"Synthetic kind",kind.equals(active)?"ACTIVE":"SUSPENDED");
        discoveryUnit(company+"-a",company,active);discoveryUnit(company+"-b",company,active);discoveryUnit(company+"-inactive",company,inactive);discoveryUnit(company+"-foreign","foreign-"+company,active);
        String sql=dz.sh.hidra.modules.topology.infrastructure.query.JpaAuthoredTopologyViewAdapter.UNIT_CANDIDATES;
        assertEquals(List.of(company+"-a"),jdbc.queryForList(sql,String.class,company,0,1));
        assertEquals(List.of(company+"-b"),jdbc.queryForList(sql,String.class,company,1,1));
        assertTrue(jdbc.queryForList(sql,String.class,company,2,1).isEmpty());
        assertTrue(jdbc.queryForList(sql,String.class,"absent-"+company,0,50).isEmpty());
        var kinds=jdbc.queryForList(dz.sh.hidra.modules.topology.infrastructure.query.JpaAuthoredTopologyViewAdapter.EQUIPMENT_CHOICES);
        assertFalse(kinds.stream().anyMatch(row->inactive.equals(row.get("id"))));
    }
}
