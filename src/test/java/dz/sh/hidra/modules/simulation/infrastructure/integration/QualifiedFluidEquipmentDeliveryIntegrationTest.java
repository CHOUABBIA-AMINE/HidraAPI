/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : QualifiedFluidEquipmentDeliveryIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-10
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.infrastructure.integration
 *
 * @Description : Maintains immutable governed equipment source evidence.
 *
 */
package dz.sh.hidra.modules.simulation.infrastructure.integration;

import dz.sh.hidra.HidraApplication;
import dz.sh.hidra.modules.simulation.application.port.out.*;
import dz.sh.hidra.modules.simulation.application.port.out.SimulationEquipmentParameterRevisionRepositoryPort.*;
import dz.sh.hidra.modules.simulation.application.service.*;
import dz.sh.hidra.modules.simulation.domain.model.SimulationEquipmentParameterRevision;
import dz.sh.hidra.modules.simulation.domain.model.SimulationEquipmentParameterRevision.*;
import dz.sh.hidra.modules.simulation.domain.model.SimulationEquipmentParameterRevisionTest;
import dz.sh.hidra.modules.simulation.infrastructure.persistence.adapter.SimulationEquipmentParameterRevisionCodec;
import dz.sh.hidra.modules.simulation.domain.exception.InvalidSimulationValueException;
import dz.sh.hidra.modules.custody.application.port.out.CustodyGasFluidRevisionRepositoryPort;
import dz.sh.hidra.modules.custody.application.contract.simulation.SimulationProductCandidateContract;
import dz.sh.hidra.modules.custody.application.service.CustodyGasFluidQualificationService;
import dz.sh.hidra.modules.topology.application.port.out.TopologyPhysicalNetworkRevisionRepositoryPort;
import dz.sh.hidra.modules.topology.domain.model.TopologyPhysicalNetworkRevision;
import dz.sh.hidra.modules.workflow.application.service.WorkflowApplicationService;
import dz.sh.hidra.modules.workflow.application.port.in.ExecuteWorkflowTransitionUseCase;
import dz.sh.hidra.modules.workflow.application.port.out.WorkflowTaskRepositoryPort;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.RevisionApprovalEvidencePostgresIntegrationTest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.flywaydb.core.Flyway;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.*;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.DriverManager;
import java.sql.Connection;
import java.util.zip.GZIPOutputStream;
import static dz.sh.hidra.modules.simulation.domain.model.SimulationEquipmentParameterRevisionTest.*;
import static org.junit.jupiter.api.Assertions.*;

@Testcontainers(disabledWithoutDocker=true)
@SpringBootTest(classes=HidraApplication.class,webEnvironment=SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
class QualifiedFluidEquipmentDeliveryIntegrationTest {

    @Container static final PostgreSQLContainer<?> POSTGRES=new PostgreSQLContainer<>("postgres:16").withDatabaseName("hidra_test");
    @DynamicPropertySource static void database(DynamicPropertyRegistry r){
        r.add("spring.datasource.url",POSTGRES::getJdbcUrl);r.add("spring.datasource.username",POSTGRES::getUsername);r.add("spring.datasource.password",POSTGRES::getPassword);
        r.add("spring.flyway.enabled",()->"true");r.add("spring.jpa.hibernate.ddl-auto",()->"validate");}
    @Autowired JdbcTemplate jdbc;
    @Autowired PlatformTransactionManager transactions;
    @Autowired SimulationEquipmentParameterRevisionRepositoryPort revisions;
    @Autowired SimulationEquipmentParameterQualificationService qualifier;
    @Autowired SimulationEquipmentParameterRevisionQueryService query;
    @Autowired CustodyGasFluidRevisionRepositoryPort fluids;
    @Autowired CustodyGasFluidQualificationService fluidQualifier;
    @Autowired SimulationProductCandidateContract products;
    @Autowired TopologyPhysicalNetworkRevisionRepositoryPort networks;
    @Autowired WorkflowApplicationService workflow;
    @Autowired ExecuteWorkflowTransitionUseCase transitions;
    @Autowired WorkflowTaskRepositoryPort tasks;
    final SimulationEquipmentParameterRevisionCodec codec=new SimulationEquipmentParameterRevisionCodec();
    @AfterEach void clearSecurity(){SecurityContextHolder.clearContext();}
    SimulationEquipmentParameterRevision prepared(){
        String id=UUID.randomUUID().toString();String fd="fluid-def-"+id,ft="fluid-type-"+id,fp="fluid-purpose-"+id;
        var f=RevisionApprovalEvidencePostgresIntegrationTest.fluid(jdbc,products,"f1",fd,ft,fp);fluids.append(f);
        var fs=fluids.findStored(f.sourceId(),f.revisionId()).orElseThrow();
        var a=RevisionApprovalEvidencePostgresIntegrationTest.approve(jdbc,workflow,transitions,tasks,"custody",fd,ft,fp,fs.sha256());
        var fq=fluidQualifier.qualify("fluid-q-"+id,f.sourceId(),f.revisionId(),a.instanceId(),a.taskId(),a.actionId());
        var n=new TopologyPhysicalNetworkRevision("network-"+id,"n1",TopologyPhysicalNetworkRevision.ScopeType.PIPELINE_SYSTEM,"synthetic-test",AT,AT,null,
            TopologyPhysicalNetworkRevision.Origin.SYNTHETIC,"synthetic fixture, no operational claim",
            List.of(new TopologyPhysicalNetworkRevision.Node("a",d("0")),new TopologyPhysicalNetworkRevision.Node("b",d("0"))),
            List.of(new TopologyPhysicalNetworkRevision.PipeSegment("pipe","a","b",d("100"),d("0.5"),d("0.001"))),
            List.of(new TopologyPhysicalNetworkRevision.EquipmentLink("compressor","a","b",TopologyPhysicalNetworkRevision.EquipmentKind.COMPRESSOR),
                new TopologyPhysicalNetworkRevision.EquipmentLink("valve","b","a",TopologyPhysicalNetworkRevision.EquipmentKind.VALVE)));
        networks.append(n);var ns=networks.findStored(n.sourceId(),n.revisionId()).orElseThrow();
        return rebind(fixture("r1"),"equipment-"+id,n.sourceId(),n.revisionId(),ns.sha256(),f.sourceId(),f.revisionId(),fs.sha256(),fq.qualificationId(),new GovernanceBinding("equipment-def-"+id,1,"equipment-type-"+id,"equipment-purpose-"+id));
    }
    Qualification approved(SimulationEquipmentParameterRevision v){revisions.append(v);var s=revisions.findStored(v.sourceId(),v.revisionId()).orElseThrow();var b=v.governanceBinding();
        var a=RevisionApprovalEvidencePostgresIntegrationTest.approve(jdbc,workflow,transitions,tasks,"simulation",b.definitionId(),b.targetTypeId(),b.purposeId(),s.sha256());
        return qualifier.qualify("eq-q-"+UUID.randomUUID(),v.sourceId(),v.revisionId(),a.instanceId(),a.taskId(),a.actionId());}
    SimulationEquipmentParameterRevision replacement(SimulationEquipmentParameterRevision v,String rev){var e=v.equipment().getFirst();
        var items=List.of(new Equipment(e.id(),e.fromNodeId(),e.toNodeId(),e.kind(),e.curveId(),e.curveRevisionId(),d("1600.00"),null,null,null),v.equipment().getLast());
        return new SimulationEquipmentParameterRevision(v.sourceId(),rev,v.recordedAt(),v.effectiveFrom(),v.effectiveUntil(),v.origin(),v.evidenceReference(),v.networkSourceId(),v.networkRevisionId(),v.networkSha256(),
            v.fluidSourceId(),v.fluidRevisionId(),v.fluidSha256(),v.fluidQualificationId(),items,v.compressorCurves(),v.valveCharacteristics(),v.governedLimits(),v.governanceBinding());}

    @Test void realOwnerAdaptersWorkflowAndStoresReturnExactAdvisoryBundle(){var v=prepared();var q=approved(v);var result=query.resolve(v.sourceId(),v.revisionId(),q.qualificationId(),Instant.now(),SimulationQualifiedFluidRevisionPort.SupportedUse.TRANSIENT).orElseThrow();
        assertEquals(v.fluidSha256(),result.fluid().stored().sha256());assertEquals(v.networkSha256(),result.network().sha256());assertEquals(v.fluidQualificationId(),result.fluid().qualification().qualificationId());
        assertEquals(8,result.stored().revision().governedLimits().size());assertEquals(v.compressorCurves(),result.stored().revision().compressorCurves());assertEquals(v.valveCharacteristics(),result.stored().revision().valveCharacteristics());
        assertEquals("Synthetic Reviewer",result.qualification().approverDisplayName());assertEquals(SimulationQualifiedFluidRevisionPort.Origin.SYNTHETIC,result.fluid().stored().revision().origin());
        jdbc.update("UPDATE hidra_workflow_definition_target_binding SET active=false WHERE id=?",result.fluid().qualification().definitionId()+"-binding");
        assertTrue(query.resolve(v.sourceId(),v.revisionId(),q.qualificationId(),Instant.now(),SimulationQualifiedFluidRevisionPort.SupportedUse.TRANSIENT).isEmpty());
    }
}
