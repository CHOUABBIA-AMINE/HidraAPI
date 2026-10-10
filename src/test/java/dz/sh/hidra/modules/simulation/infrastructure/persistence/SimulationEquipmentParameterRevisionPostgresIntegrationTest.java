/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationEquipmentParameterRevisionPostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-10
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.infrastructure.persistence
 *
 * @Description : Maintains immutable governed equipment source evidence.
 *
 */
package dz.sh.hidra.modules.simulation.infrastructure.persistence;

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
public class SimulationEquipmentParameterRevisionPostgresIntegrationTest {

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

    @Test void exactReplayIndependentRevisionsAndActualApproval(){var v=prepared();var q=approved(v);assertEquals(v,revisions.append(v));
        var r2=replacement(v,"r2");revisions.append(r2);assertNotEquals(revisions.findStored(v.sourceId(),"r1").orElseThrow().sha256(),revisions.findStored(v.sourceId(),"r2").orElseThrow().sha256());
        var result=query.resolve(v.sourceId(),"r1",q.qualificationId(),Instant.now(),SimulationQualifiedFluidRevisionPort.SupportedUse.STEADY_STATE).orElseThrow();assertEquals(Origin.SYNTHETIC,result.stored().revision().origin());
        assertEquals(2,result.stored().revision().valveCharacteristics().getFirst().openingLines().size());
        assertTrue(query.resolve(v.sourceId(),"r2",q.qualificationId(),Instant.now(),SimulationQualifiedFluidRevisionPort.SupportedUse.STEADY_STATE).isEmpty());
        jdbc.update("UPDATE hidra_workflow_definition_target_binding SET active=false WHERE id=?",v.governanceBinding().definitionId()+"-binding");
        assertTrue(query.resolve(v.sourceId(),"r1",q.qualificationId(),Instant.now(),SimulationQualifiedFluidRevisionPort.SupportedUse.STEADY_STATE).isEmpty());}
    @Test void independentCharacteristicsRejectChangedOriginOrTimeAndRollback(){var v=prepared();revisions.append(v);var c=v.compressorCurves().getFirst();var z=v.valveCharacteristics().getFirst();
        var altered=new CompressorCurve(c.id(),c.revisionId(),c.recordedAt(),c.effectiveFrom().plusNanos(1),c.effectiveUntil(),c.origin(),c.evidenceReference(),c.fluidSourceId(),c.fluidRevisionId(),c.fluidSha256(),c.headDefinitionReference(),c.efficiencyDefinitionReference(),c.interpolationMethodReference(),c.referenceInletPressurePascalsAbsolute(),c.referenceInletTemperatureKelvin(),c.speedLines());
        assertThrows(InvalidSimulationValueException.class,()->revisions.appendCharacteristic(altered));
        var changed=new ValveCharacteristic(z.id(),z.revisionId(),z.recordedAt(),z.effectiveFrom(),z.effectiveUntil(),Origin.DECLARED_PARAMETER,z.evidenceReference(),z.fluidSourceId(),z.fluidRevisionId(),z.fluidSha256(),z.referenceTemperatureKelvin(),z.openingLines());
        assertThrows(InvalidSimulationValueException.class,()->revisions.appendCharacteristic(changed));
        var fresh=replacement(v,"rollback");new TransactionTemplate(transactions).executeWithoutResult(s->{revisions.append(fresh);s.setRollbackOnly();});assertTrue(revisions.findStored(v.sourceId(),"rollback").isEmpty());
        assertEquals(c,revisions.findCompressorCurveStored(c.id(),c.revisionId()).orElseThrow());assertEquals(z,revisions.findValveCharacteristicStored(z.id(),z.revisionId()).orElseThrow());}
    @Test void concurrentIdenticalWritersHaveOneVerifiedWinner() throws Exception {var v=prepared();var gate=new CountDownLatch(1);var pool=Executors.newFixedThreadPool(2);
        try {var a=pool.submit(()->{assertTrue(gate.await(10,TimeUnit.SECONDS));return revisions.append(v);});var b=pool.submit(()->{assertTrue(gate.await(10,TimeUnit.SECONDS));return revisions.append(v);});gate.countDown();assertEquals(v,a.get(30,TimeUnit.SECONDS));assertEquals(v,b.get(30,TimeUnit.SECONDS));
            assertEquals(1L,jdbc.queryForObject("SELECT count(*) FROM hidra_simulation_equipment_parameter_revision WHERE source_id=? AND revision_id=?",Long.class,v.sourceId(),v.revisionId()));}finally{pool.shutdownNow();}}
    @Test void appendOnlySqlAndForgedQualificationCorruptionCannotQualify(){var v=prepared();revisions.append(v);String sha=revisions.findStored(v.sourceId(),v.revisionId()).orElseThrow().sha256();
        var fake=new Qualification("forged-"+UUID.randomUUID(),v.sourceId(),v.revisionId(),sha,Instant.now(),"missing-instance","missing-task","missing-action","fake-actor","Synthetic",AT,v.governanceBinding().definitionId(),1,v.governanceBinding().targetTypeId(),v.governanceBinding().purposeId());revisions.appendQualification(v.sourceId(),v.revisionId(),fake);
        assertTrue(query.resolve(v.sourceId(),v.revisionId(),fake.qualificationId(),Instant.now(),SimulationQualifiedFluidRevisionPort.SupportedUse.STEADY_STATE).isEmpty());
        for(String t:List.of("hidra_simulation_equipment_parameter_revision","hidra_simulation_equipment_parameter_qualification","hidra_simulation_equipment_characteristic_revision")){
            assertThrows(org.springframework.dao.DataAccessException.class,()->jdbc.update("UPDATE "+t+" SET payload_format=payload_format"));assertThrows(org.springframework.dao.DataAccessException.class,()->jdbc.update("DELETE FROM "+t));assertThrows(org.springframework.dao.DataAccessException.class,()->jdbc.execute("TRUNCATE "+t+" CASCADE"));}
        assertTrue(products.resolve(fluids.findStored(v.fluidSourceId(),v.fluidRevisionId()).orElseThrow().revision().productSnapshot().id()).isPresent());
        byte[] bad={1,2,3};String corrupt="corrupt-"+UUID.randomUUID();jdbc.update("INSERT INTO hidra_simulation_equipment_parameter_revision(source_id,revision_id,payload_format,canonical_payload,sha256) VALUES (?,?,?,?,?)",corrupt,"bad",SimulationEquipmentParameterRevisionCodec.FORMAT,bad,codec.sha256(bad));
        assertThrows(InvalidSimulationValueException.class,()->revisions.findStored(corrupt,"bad"));}
    @Test void concurrentConflictingCharacteristicWritersRollbackLosingAggregate() throws Exception {
        var v=prepared();var c=v.compressorCurves().getFirst();
        var changed=new CompressorCurve(c.id(),c.revisionId(),c.recordedAt(),c.effectiveFrom().plusNanos(1),c.effectiveUntil(),c.origin(),c.evidenceReference(),c.fluidSourceId(),c.fluidRevisionId(),c.fluidSha256(),c.headDefinitionReference(),c.efficiencyDefinitionReference(),c.interpolationMethodReference(),c.referenceInletPressurePascalsAbsolute(),c.referenceInletTemperatureKelvin(),c.speedLines());
        var other=new SimulationEquipmentParameterRevision(v.sourceId(),"conflict-r2",v.recordedAt(),v.effectiveFrom(),v.effectiveUntil(),v.origin(),v.evidenceReference(),v.networkSourceId(),v.networkRevisionId(),v.networkSha256(),v.fluidSourceId(),v.fluidRevisionId(),v.fluidSha256(),v.fluidQualificationId(),v.equipment(),List.of(changed),v.valveCharacteristics(),v.governedLimits(),v.governanceBinding());
        var gate=new CountDownLatch(1);var pool=Executors.newFixedThreadPool(2);
        try {var jobs=new ArrayList<Future<Boolean>>();for(var item:List.of(v,other))jobs.add(pool.submit(()->{assertTrue(gate.await(10,TimeUnit.SECONDS));try{revisions.append(item);return true;}catch(InvalidSimulationValueException rejected){return false;}}));
            gate.countDown();int wins=0;for(var j:jobs)if(j.get(30,TimeUnit.SECONDS))wins++;assertEquals(1,wins);
            assertEquals(1L,jdbc.queryForObject("SELECT count(*) FROM hidra_simulation_equipment_parameter_revision WHERE source_id=?",Long.class,v.sourceId()));
        }finally{pool.shutdownNow();}
    }
    @Test void laterNestedConflictRollsBackEarlierCharacteristicInsert(){var v=prepared();revisions.append(v);var c=v.compressorCurves().getFirst();var z=v.valveCharacteristics().getFirst();String fresh=c.id()+"-new";
        var first=new CompressorCurve(fresh,c.revisionId(),c.recordedAt(),c.effectiveFrom(),c.effectiveUntil(),c.origin(),c.evidenceReference(),c.fluidSourceId(),c.fluidRevisionId(),c.fluidSha256(),c.headDefinitionReference(),c.efficiencyDefinitionReference(),c.interpolationMethodReference(),c.referenceInletPressurePascalsAbsolute(),c.referenceInletTemperatureKelvin(),c.speedLines());
        var conflict=new ValveCharacteristic(z.id(),z.revisionId(),z.recordedAt(),z.effectiveFrom(),z.effectiveUntil(),Origin.DECLARED_PARAMETER,z.evidenceReference(),z.fluidSourceId(),z.fluidRevisionId(),z.fluidSha256(),z.referenceTemperatureKelvin(),z.openingLines());
        var e=v.equipment().getFirst();var items=List.of(new Equipment(e.id(),e.fromNodeId(),e.toNodeId(),e.kind(),fresh,c.revisionId(),e.configuredSpeedRevolutionsPerMinute(),null,null,null),v.equipment().getLast());
        var other=new SimulationEquipmentParameterRevision(v.sourceId(),"nested-conflict",v.recordedAt(),v.effectiveFrom(),v.effectiveUntil(),v.origin(),v.evidenceReference(),v.networkSourceId(),v.networkRevisionId(),v.networkSha256(),v.fluidSourceId(),v.fluidRevisionId(),v.fluidSha256(),v.fluidQualificationId(),items,List.of(first),List.of(conflict),v.governedLimits(),v.governanceBinding());
        assertThrows(InvalidSimulationValueException.class,()->revisions.append(other));assertTrue(revisions.findCompressorCurveStored(fresh,c.revisionId()).isEmpty());assertTrue(revisions.findStored(v.sourceId(),"nested-conflict").isEmpty());
    }
    @Test void fullFlywayChainProvidesActualCatalog() throws Exception {Flyway.configure().dataSource(POSTGRES.getJdbcUrl(),POSTGRES.getUsername(),POSTGRES.getPassword()).locations("filesystem:src/main/resources/db/migration").load().validate();captureExactCatalog();}
    private void captureExactCatalog() throws Exception {
        // Reuse the existing generator's schema-only query and source-inventory validator.
        // This equivalent capture reads the actual fully migrated disposable PostgreSQL 16
        // catalog; it never exports business rows or substitutes generated schema facts.
        String sql = python("""
                import importlib.util
                spec=importlib.util.spec_from_file_location('dictionary','.github/scripts/generate_data_dictionary.py')
                g=importlib.util.module_from_spec(spec);spec.loader.exec_module(g)
                print(g.CATALOG_SQL.split(';',1)[1].rsplit('COMMIT;',1)[0])
                """);
        Path directory = Files.createTempDirectory("hidra-p25-catalog-");
        Path catalog = directory.resolve("catalog.json");
        try (var connection = DriverManager.getConnection(POSTGRES.getJdbcUrl(), POSTGRES.getUsername(), POSTGRES.getPassword())) {
            connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            connection.setReadOnly(true);
            connection.setAutoCommit(false);
            try (var statement = connection.createStatement()) {
                statement.setQueryTimeout(60);
                try (var result = statement.executeQuery(sql)) {
                    assertTrue(result.next());
                    Files.writeString(catalog, result.getString(1), StandardCharsets.UTF_8);
                    assertFalse(result.next());
                }
            }
            connection.commit();
        }
        String result = python("""
                import importlib.util,json,subprocess,sys
                from pathlib import Path
                spec=importlib.util.spec_from_file_location('dictionary','.github/scripts/generate_data_dictionary.py')
                g=importlib.util.module_from_spec(spec);spec.loader.exec_module(g)
                root=Path('.').resolve();directory=Path(sys.argv[1])
                source=g.inventory(root);catalog=g.load_json(directory/'catalog.json')
                sha=subprocess.check_output(['git','rev-parse','HEAD'],text=True).strip()
                document={'format_version':g.VERSION,'source_sha':sha,'source':source,'catalog':catalog}
                g.validate_document(document,root)
                overrides=g.load_json('.github/database-dictionary-ownership.json')
                markdown=g.render(document,root,overrides,final=True)
                data=g.canonical(document)
                (directory/'schema.json').write_bytes(data)
                (directory/'DATA_DICTIONARY.md').write_text(markdown,encoding='utf-8')
                print(json.dumps({'source_sha':sha,'catalog_sha256':g.digest(data),'source_bundle_sha256':source['bundle_sha256'],'migrations':len(source['migrations']),'relations':len(catalog['relations']),'dictionary_sha256':g.digest(markdown.encode())},sort_keys=True))
                """, directory.toString());
        assertTrue(result.contains("\"catalog_sha256\""));
        System.out.println("HIDRA_P25_C3D_CATALOG_VERIFICATION " + result.strip());
        if ("true".equals(System.getenv("GITHUB_ACTIONS"))) {
            // Transport compressed schema-only evidence in existing job logs; no workflow changes.
            var compressed = new ByteArrayOutputStream();
            try (var gzip = new GZIPOutputStream(compressed)) {
                gzip.write(Files.readAllBytes(directory.resolve("schema.json")));
            }
            String encoded = Base64.getEncoder().encodeToString(compressed.toByteArray());
            for (int start = 0, part = 0; start < encoded.length(); start += 4096, part++) {
                System.out.println("HIDRA_P25_C3D_CATALOG_PART " + part + " " + encoded.substring(start, Math.min(start+4096, encoded.length())));
            }
            System.out.println("HIDRA_P25_C3D_CATALOG_END");
        }
    }

    private static String python(String script, String... arguments) throws Exception {
        var command = new java.util.ArrayList<>(List.of("python3", "-c", script));
        command.addAll(List.of(arguments));
        var process = new ProcessBuilder(command).redirectErrorStream(true).start();
        var output = new ByteArrayOutputStream();
        var reader = Executors.newSingleThreadExecutor();
        try {
            var read = reader.submit(() -> { process.getInputStream().transferTo(output); return 0; });
            assertTrue(process.waitFor(60, TimeUnit.SECONDS), "Catalog helper timeout");
            read.get(10, TimeUnit.SECONDS);
            String text = output.toString(StandardCharsets.UTF_8);
            assertEquals(0, process.exitValue(), text);
            return text;
        } finally {
            process.destroyForcibly();
            reader.shutdownNow();
        }
    }

}
