/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : Gz2ScopedReportNativeHttpPostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-17
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : qualification
 * @Package     : dz.sh.hidra.qualification
 *
 * @Description : Signed scoped approved Reporting, Documents sealing and JVM-independent historical readback.
 *
 */
package dz.sh.hidra.qualification;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.file.Path;
import java.util.HexFormat;
import java.util.Map;
import java.security.MessageDigest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.testcontainers.containers.PostgreSQLContainer;
import static dz.sh.hidra.qualification.Gz2SignedWorkflowFixture.id;
import static org.junit.jupiter.api.Assertions.*;

class Gz2ScopedReportNativeHttpPostgresIntegrationTest {
    private static final String PROFILE="GZ2_ABSOLUTE_DEVIATION_JSON_V1";
    @TempDir Path objects;
    private static String definition(Gz2SignedWorkflowFixture f) {
        String subject=id(),definition=id(),version=id();
        f.sql.update("""
                INSERT INTO hidra_analytics_subject_area
                    (id,code,name_fr,owner_module,active,created_at,updated_at)
                VALUES (?,?,'Analyse synthétique GZ2','analytics',true,now(),now())
                """,subject,"T23-"+subject);
        // T11 uses an existing Telemetry unit. The separate Analytics catalog
        // must own the same synthetic identifier for its HRA-111 unit FK.
        f.sql.update("""
                INSERT INTO hidra_analytics_catalog_entry
                    (id,catalog_name,code,active,sort_order,system_defined,created_at,updated_at)
                VALUES (?,'UNIT','GZ2_PRESSURE',true,0,false,now(),now())
                """,f.pressureUnit);
        f.sql.update("""
                INSERT INTO hidra_analytics_metric_definition
                  (id,code,name_fr,subject_area_id,metric_type,formula_expression,unit_id,
                   aggregation_method,period_granularity,active,created_at,updated_at)
                VALUES (?,'ABSOLUTE_DEVIATION','Écart absolu synthétique',?,'NUMERIC',
                        'ABSOLUTE_DEVIATION_V1',?,'NONE','POINT',true,now(),now())
                """,definition,subject,f.pressureUnit);
        f.sql.update("""
                INSERT INTO hidra_analytics_metric_definition_version
                  (id,metric_definition_id,version_number,formula_expression,valid_from,created_at)
                VALUES (?,?,1,'ABSOLUTE_DEVIATION_V1',now()-interval '2 hours',now())
                """,version,definition);
        return version;
    }


    @Test void signedApprovedReportRetainsExactAcceptedBytesAcrossRestart() throws Exception {
        try (var postgres=new PostgreSQLContainer<>("postgres:16")
                .withDatabaseName("hidra_gz2_t25")
                .withCommand("postgres","-c","autovacuum=off")) {
            postgres.start();
            var f=new Gz2SignedWorkflowFixture(postgres);
            String metricDefinition=definition(f);
            String reportDefinition=id(), template=id(), version=id();
            configureReport(f,reportDefinition,template,version);
            String provider=f.catalog("documents","DOCUMENT_STORAGE_PROVIDER","T25_LOCAL");
            for (String family : new String[]{"DOCUMENT_TYPE","DOCUMENT_CLASSIFICATION","DOCUMENT_LINK_ROLE"})
                f.catalog("documents",family,"GZ2_REPORT_OUTPUT");
            var review=f.review("reporting","REPORT_REQUEST","REPORT_APPROVAL",true);
            for (String action : new String[]{"submit","render","read"})
                f.assetGrant(f.author,"reporting:scoped-report:"+action,f.company,f.asset);
            f.assetGrant(f.reviewer,"reporting:scoped-report:read",f.company,f.asset);
            f.assetGrant(f.reader,"reporting:scoped-report:seal",f.company,f.asset);
            f.assetGrant(f.reader,"reporting:scoped-report:read",f.company,f.asset);
            for (String action : new String[]{"create","write","read"})
                f.assetGrant(f.author,"documents:controlled:"+action,f.company,f.asset);
            for (String action : new String[]{"read","accept"})
                f.assetGrant(f.reader,"documents:controlled:"+action,f.company,f.asset);
            for (String action : new String[]{"execute","read"})
                f.assetGrant(f.author,"analytics:deviation-metric:"+action,f.company,f.asset);
            f.assetGrant(f.author,"monitoring:rule:read",f.company,f.asset);
            f.global(f.author,"workflow:instances:execute","workflow:tasks:execute");
            f.global(f.reviewer,"workflow:tasks:read","workflow:tasks:execute");
            f.global(f.foreign,"workflow:tasks:read");
            String prefix="/api/v1/reporting/companies/"+f.company+"/facilities/"+f.asset;
            String resultPath, hash;
            byte[] expected;
            long pid;
            try (var r=f.start(objects,provider)) {
                pid=r.pid();
                var source=Gz2SignedWorkflowNativeHttpPostgresIntegrationTest.prepareDeviationMetricEvidence(f,r);
                String author=r.login(f.author), reviewer=r.login(f.reviewer), custodian=r.login(f.reader);
                String foreign=r.login(f.foreign);
                String metric=text(r.json("POST","/api/v1/analytics/companies/"+f.company+"/facilities/"+f.asset+
                        "/deviation-metric-runs",author,Map.of("definitionVersionId",metricDefinition,
                        "evaluationId",source.computedId()),201),"runId");
                String failedMetric=text(r.json("POST","/api/v1/analytics/companies/"+f.company+"/facilities/"+f.asset+
                        "/deviation-metric-runs",author,Map.of("definitionVersionId",metricDefinition,
                        "evaluationId",source.staleId()),201),"runId");
                deny(r.send("POST",prefix+"/report-requests",author,Map.of("requestKey",id(),
                        "definitionId",reportDefinition,"templateVersionId",version,"metricRunId",failedMetric)).statusCode());
                var submit=Map.of("requestKey",id(),"definitionId",reportDefinition,"templateVersionId",version,
                        "metricRunId",metric);
                deny(r.send("POST",prefix+"/report-requests",foreign,submit).statusCode());
                var admitted=r.json("POST",prefix+"/report-requests",author,submit,201);
                String request=text(admitted,"requestId");
                assertEquals(request,text(r.json("POST",prefix+"/report-requests",author,submit,200),"requestId"));
                String renderPath=prefix+"/report-requests/"+request+"/render";
                deny(r.send("POST",renderPath,author,Map.of("requestKey",id(),"workflowActionId",id(),
                        "workflowInstanceId",id())).statusCode());
                var decision=approve(f,r,review,request,author,reviewer,foreign);
                var render=Map.of("requestKey",id(),"workflowInstanceId",decision.instanceId(),
                        "workflowActionId",decision.actionId());
                String run;
                try (var racers=java.util.concurrent.Executors.newFixedThreadPool(2)) {
                    var first=racers.submit(()->r.json("POST",renderPath,author,render,200));
                    var second=racers.submit(()->r.json("POST",renderPath,author,render,200));
                    var one=first.get(45,java.util.concurrent.TimeUnit.SECONDS);
                    var two=second.get(45,java.util.concurrent.TimeUnit.SECONDS);
                    run=text(one,"runId");
                    assertEquals(run,text(two,"runId"));
                    assertEquals("RENDERED",text(one,"status"));
                    assertEquals("RENDERED",text(two,"status"));
                }
                assertEquals(run,text(r.json("POST",renderPath,author,render,200),"runId"));
                resultPath=prefix+"/report-runs/"+run;
                deny(r.send("GET",resultPath+"/content",author,null).statusCode());
                deny(r.send("GET",resultPath,foreign,null).statusCode());
                deny(r.send("POST",resultPath+"/seal",author,Map.of("requestKey",id())).statusCode());
                var seal=Map.of("requestKey",id());
                f.sql.execute("""
                        CREATE FUNCTION public.t25_test_fault() RETURNS trigger LANGUAGE plpgsql AS $$
                        BEGIN RAISE EXCEPTION 'Synthetic seal fault'; END $$;
                        CREATE TRIGGER t25_test_fault AFTER INSERT ON public.hidra_reporting_output_artifact
                        FOR EACH ROW EXECUTE FUNCTION public.t25_test_fault()
                        """);
                try {
                    assertTrue(r.send("POST",resultPath+"/seal",custodian,seal).statusCode()>=400);
                    assertEquals("RENDERED",text(r.get(resultPath,custodian),"status"));
                    assertEquals(0,f.sql.queryForObject(
                            "SELECT count(*) FROM hidra_documents_t25_report_output WHERE run_id=? AND acceptance_id IS NOT NULL",
                            Integer.class,run));
                } finally {
                    f.sql.execute("DROP TRIGGER t25_test_fault ON public.hidra_reporting_output_artifact");
                    f.sql.execute("DROP FUNCTION public.t25_test_fault()");
                }
                var sealed=r.json("POST",resultPath+"/seal",custodian,seal,200);
                assertEquals("COMPLETED",text(sealed,"status"));
                assertEquals(decision.actionId(),text(sealed,"workflowActionId"));
                assertEquals(text(sealed,"acceptanceId"),
                        text(r.json("POST",resultPath+"/seal",custodian,seal,200),"acceptanceId"));
                hash=text(sealed,"checksumSha256");
                var bytes=r.download(resultPath+"/content",custodian);
                assertEquals(200,bytes.statusCode());
                expected=bytes.body();
                assertEquals(hash,HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(expected)));
                assertEquals(expected.length,sealed.path("contentLength").asInt());
                var payload=new ObjectMapper().readTree(expected);
                assertEquals(metric,text(payload,"metricRunId"));
                assertEquals(metricDefinition,text(payload,"definitionVersionId"));
                assertEquals(source.computedId(),text(payload,"evaluationId"));
                assertEquals(5,payload.path("value").decimalValue().intValueExact());
                String docs="/api/v1/documents/companies/"+f.company+"/facilities/"+f.asset+"/controlled-documents";
                assertEquals(0,r.get(docs,custodian).path("total").asInt());
                String previous=text(sealed,"documentVersionId");
                String document=text(sealed,"documentId");
                byte[] newer="Independent controlled successor".getBytes(java.nio.charset.StandardCharsets.UTF_8);
                var upload=r.multipart(docs+"/"+document+"/versions/upload",author,Map.of(
                        "documentId",document,"versionNumber",2,"versionLabel","v2","titleFr","Successeur",
                        "uploadedByActorId",f.author),newer);
                assertEquals(200,upload.statusCode(),new String(upload.body(),java.nio.charset.StandardCharsets.UTF_8));
                String newVersion=text(new ObjectMapper().readTree(upload.body()),"id");
                String linkRole=f.catalog("documents","DOCUMENT_LINK_ROLE","GZ2_REPORT_OUTPUT");
                r.json("POST",docs+"/"+document+"/versions/"+newVersion+"/supersede",custodian,Map.of(
                        "requestId",id(),"reason","Independent controlled successor","linkRoleId",linkRole,
                        "expectedCurrentVersionId",previous),201);
                assertArrayEquals(expected,r.download(resultPath+"/content",custodian).body());
                assertEquals(1,f.sql.queryForObject("SELECT count(*) FROM hidra_reporting_output_artifact WHERE report_run_id=?",
                        Integer.class,run));
                String secondRequest=text(r.json("POST",prefix+"/report-requests",author,Map.of("requestKey",id(),
                        "definitionId",reportDefinition,"templateVersionId",version,"metricRunId",metric),201),"requestId");
                var secondApproval=approve(f,r,review,secondRequest,author,reviewer,foreign);
                String faultPath=prefix+"/report-requests/"+secondRequest+"/render";
                var fault=Map.of("requestKey",id(),"workflowInstanceId",secondApproval.instanceId(),
                        "workflowActionId",secondApproval.actionId());
                Path parked=objects.resolveSibling(objects.getFileName()+"-saved");
                java.nio.file.Files.move(objects,parked);
                try {
                    java.nio.file.Files.writeString(objects,"Synthetic obstruction to binary storage");
                    var failed=r.json("POST",faultPath,author,fault,200);
                    assertEquals("FAILED",text(failed,"status"));
                    assertEquals("DOCUMENT_STORAGE_UNAVAILABLE",text(failed,"failureCode"));
                    String failedRun=text(failed,"runId");
                    assertEquals(failedRun,text(r.json("POST",faultPath,author,fault,200),"runId"));
                    deny(r.send("GET",prefix+"/report-runs/"+failedRun+"/content",author,null).statusCode());
                    assertEquals(0,f.sql.queryForObject(
                            "SELECT count(*) FROM hidra_documents_t25_report_output WHERE run_id=?",
                            Integer.class,failedRun));
                } finally {
                    java.nio.file.Files.deleteIfExists(objects);
                    java.nio.file.Files.move(parked,objects);
                }
            }
            try (var restarted=f.start(objects,provider)) {
                assertNotEquals(pid,restarted.pid());
                String reader=restarted.login(f.reader);
                assertEquals(hash,text(restarted.get(resultPath,reader),"checksumSha256"));
                var download=restarted.download(resultPath+"/content",reader);
                assertEquals(200,download.statusCode());
                assertArrayEquals(expected,download.body());
                f.sql.update("""
                        UPDATE hidra_identity_user_permission_grant SET valid_to=now()-interval '1 second'
                        WHERE user_id=? AND scope_type='FACILITY' AND permission_id=
                          (SELECT id FROM hidra_identity_permission WHERE code='reporting:scoped-report:read')
                        """, f.reader);
                deny(restarted.send("GET",resultPath+"/content",reader,null).statusCode());
            }
        }
    }
    private record Approval(String instanceId,String actionId) {}
    private static Approval approve(Gz2SignedWorkflowFixture f,Gz2SignedWorkflowFixture.Runtime r,
            Gz2SignedWorkflowFixture.Review review,String target,String author,String reviewer,String foreign) throws Exception {
        String base="/api/v1/workflow";
        var start=Map.of("definitionId",review.definition(),"definitionVersion",1,
                "workflowPurposeId",review.purpose(),"targetModule","reporting","targetTypeId",review.type(),
                "targetId",target,"currentStepId",review.step(),"startedByActorId",f.author,"correlationId",id());
        String instance=text(r.json("POST",base+"/instances",author,start,200),"id");
        String task=text(r.json("POST",base+"/tasks",author,Map.of("instanceId",instance,"stepId",review.step(),
                "assignedActorId",f.reviewer,"taskLabelSnapshot","Independent report review"),200),"id");
        String path=base+"/tasks/"+task;
        deny(r.send("GET",path,foreign,null).statusCode());
        var command=Map.of("correlationId",id(),"expectedTaskUpdatedAt",text(r.get(path,reviewer),"updatedAt"));
        var decision=r.json("POST",path+"/transitions/"+review.transition()+"/execute",reviewer,command,200);
        assertEquals("APPROVE",text(decision,"decision"));
        return new Approval(instance,text(decision,"actionId"));
    }
    private static void configureReport(Gz2SignedWorkflowFixture f,String definition,String template,String version) {
        String category=f.catalog("reporting","REPORT_CATEGORY","GZ2_REPORT");
        f.sql.update("""
                INSERT INTO hidra_reporting_report_definition(id,code,name_fr,report_category_id,owner_module,
                    requires_approval,restricted,created_at,updated_at,status)
                VALUES (?,?,'Écart GZ2',?,'reporting',true,false,now(),now(),'ACTIVE')
                """,definition,PROFILE,category);
        f.sql.update("""
                INSERT INTO hidra_reporting_report_template(id,report_definition_id,code,name_fr,template_engine,
                    active,created_at,updated_at) VALUES (?,?,?,'Écart GZ2',?,true,now(),now())
                """,template,definition,PROFILE,PROFILE);
        f.sql.update("""
                INSERT INTO hidra_reporting_report_template_version(id,report_template_id,version_number,status,
                    layout_content_reference,checksum,created_at,activated_at)
                VALUES (?,?,1,'ACTIVE',?,?,now(),now())
                """,version,template,PROFILE,"a".repeat(64));
        f.sql.update("UPDATE hidra_reporting_report_definition SET current_template_version_id=? WHERE id=?",version,definition);
        f.sql.update("""
                INSERT INTO hidra_reporting_parameter_definition(id,report_definition_id,code,label_fr,parameter_type,
                    required,sort_order,active,created_at,updated_at)
                VALUES (?,?,'metricRunId','Exécution de mesure','REFERENCE',true,0,true,now(),now())
                """,id(),definition);
    }
    private static String text(JsonNode node,String name) {
        String value=node.path(name).asText(null);
        assertNotNull(value,"Expected field "+name);
        return value;
    }
    private static void deny(int status) {
        assertTrue(status==403||status==404,"Expected protected unavailable, got "+status);
    }
}
