/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : Gz2DeviationMetricNativeHttpPostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-17
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : qualification
 * @Package     : dz.sh.hidra.qualification
 *
 * @Description : Signed scoped deterministic Analytics execution and JVM-independent historical readback.
 *
 */
package dz.sh.hidra.qualification;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.Map;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import com.fasterxml.jackson.databind.ObjectMapper;
import dz.sh.hidra.modules.analytics.domain.service.AbsoluteDeviationMetricV1;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataAccessException;
import org.testcontainers.containers.PostgreSQLContainer;
import static dz.sh.hidra.qualification.Gz2SignedWorkflowFixture.id;
import static org.junit.jupiter.api.Assertions.*;

class Gz2DeviationMetricNativeHttpPostgresIntegrationTest {
    private static final String ROOT="/api/v1/analytics/companies/";
    private static String base(Gz2SignedWorkflowFixture f) {
        return ROOT+f.company+"/facilities/"+f.asset+"/deviation-metric-runs";
    }
    private static String text(JsonNode node,String name) {
        String value=node.path(name).asText(null);
        assertNotNull(value,"Expected field "+name);
        return value;
    }
    private static void deny(int status) {
        assertTrue(status==403||status==404,"Expected protected unavailable, got "+status);
    }
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

    @Test
    void executesAndReadsScopedDeviationMetricAcrossJvmRestart() throws Exception {
        try (var postgres=new PostgreSQLContainer<>("postgres:16")
                .withDatabaseName("hidra_gz2_t23")
                .withCommand("postgres","-c","autovacuum=off")) {
            postgres.start();
            var f=new Gz2SignedWorkflowFixture(postgres);
            String version=definition(f);
            f.assetGrant(f.operator,"analytics:deviation-metric:execute",f.company,f.asset);
            f.assetGrant(f.operator,"analytics:deviation-metric:read",f.company,f.asset);
            f.assetGrant(f.reader,"analytics:deviation-metric:read",f.company,f.asset);
            f.assetGrant(f.revoked,"analytics:deviation-metric:read",f.company,f.asset);
            f.assetGrant(f.foreign,"analytics:deviation-metric:read",f.foreignCompany,f.asset);
            f.assetGrant(f.wrongAsset,"analytics:deviation-metric:read",f.company,f.secondAsset);
            String runId,failedId,computed,stale;
            long firstPid;
            try(var r=f.start()) {
                firstPid=r.pid();
                var source=Gz2SignedWorkflowNativeHttpPostgresIntegrationTest
                        .prepareDeviationMetricEvidence(f,r);
                computed=source.computedId();
                stale=source.staleId();
                String operator=r.login(f.operator);
                var request=Map.of("definitionVersionId",version,"evaluationId",computed);
                // Both requests race for the first immutable replay key: exactly one creates.
                JsonNode created;
                try(var executor=Executors.newFixedThreadPool(2)) {
                    var gate=new CountDownLatch(1);
                    var left=executor.submit(()->{
                        gate.await();
                        return r.send("POST",base(f),operator,request);
                    });
                    var right=executor.submit(()->{
                        gate.await();
                        return r.send("POST",base(f),operator,request);
                    });
                    gate.countDown();
                    var first=left.get(35,TimeUnit.SECONDS);
                    var second=right.get(35,TimeUnit.SECONDS);
                    int firstStatus = first.statusCode();
                    int secondStatus = second.statusCode();
                    assertEquals(List.of(200, 201),
                            List.of(Math.min(firstStatus, secondStatus), Math.max(firstStatus, secondStatus)),
                            () -> "Expected one create and one replay; received " + firstStatus + " and " + secondStatus);
                    var readerJson=new ObjectMapper();
                    var createdResponse=first.statusCode()==201?first:second;
                    var replayResponse=first.statusCode()==200?first:second;
                    created=readerJson.readTree(createdResponse.body());
                    assertEquals(text(created,"runId"),
                            text(readerJson.readTree(replayResponse.body()),"runId"));
                }
                runId=text(created,"runId");
                assertEquals("COMPLETED",text(created,"status"));
                assertEquals("ABSOLUTE_DEVIATION",text(created,"metricCode"));
                assertEquals("1",text(created,"methodVersion"));
                assertEquals(5,created.path("value").decimalValue().intValueExact());
                assertEquals(computed,text(created.path("source"),"evaluationId"));
                assertEquals("COMPUTED",text(created.path("source"),"outcome"));
                assertEquals(f.pressureUnit,text(created,"unitId"));
                assertEquals(runId,text(r.json("POST",base(f),operator,request,200),"runId"));
                assertEquals(runId,text(r.get(base(f)+"/"+runId,operator),"runId"));
                JsonNode failed=r.json("POST",base(f),operator,
                        Map.of("definitionVersionId",version,"evaluationId",stale),201);
                failedId=text(failed,"runId");
                assertEquals("FAILED",text(failed,"status"));
                assertEquals("SOURCE_UNAVAILABLE",text(failed,"errorCode"));
                assertFalse(failed.hasNonNull("value"), "A failed run must not expose a metric value");
                assertEquals("STALE",text(failed.path("source"),"outcome"));
                assertEquals(422,r.send("POST",base(f),operator,Map.of(
                        "definitionVersionId",id(),"evaluationId",computed)).statusCode());
                String reader=r.login(f.reader), foreign=r.login(f.foreign);
                String wrong=r.login(f.wrongAsset), revoked=r.login(f.revoked);
                // A fully authorized reader is allowed; revoking only Monitoring READ fails closed.
                assertEquals(200,r.send("GET",base(f)+"/"+runId,reader,null).statusCode());
                f.sql.update("""
                        UPDATE hidra_identity_user_permission_grant SET valid_to=now()-interval '1 second'
                        WHERE user_id=? AND permission_id IN
                          (SELECT id FROM hidra_identity_permission WHERE code='monitoring:rule:read')
                        """,f.reader);
                deny(r.send("GET",base(f)+"/"+runId,reader,null).statusCode());
                deny(r.send("GET",base(f)+"/"+runId,foreign,null).statusCode());
                deny(r.send("GET",base(f)+"/"+runId,wrong,null).statusCode());
                f.sql.update("""
                        UPDATE hidra_identity_user_permission_grant SET valid_to=now()-interval '1 second'
                        WHERE user_id=? AND permission_id IN
                          (SELECT id FROM hidra_identity_permission WHERE code='analytics:deviation-metric:read')
                        """,f.revoked);
                deny(r.send("GET",base(f)+"/"+runId,revoked,null).statusCode());
            }
            assertEquals(2L,f.sql.queryForObject(
                    "select count(*) from hidra_analytics_deviation_metric_receipt",Long.class));
            assertEquals(1L,f.sql.queryForObject(
                    "select count(*) from hidra_analytics_metric_value where metric_evaluation_run_id=?",Long.class,runId));
            assertEquals(0L,f.sql.queryForObject(
                    "select count(*) from hidra_analytics_metric_value where metric_evaluation_run_id=?",Long.class,failedId));
            assertThrows(DataAccessException.class,()->f.sql.update(
                    "update hidra_analytics_metric_value set value_numeric=7 where metric_evaluation_run_id=?",runId));
            assertThrows(DataAccessException.class,()->f.sql.update(
                    "delete from hidra_analytics_deviation_metric_receipt where run_id=?",runId));
            // A generic/legacy metric API cannot insert a reserved T23 run without its sealed receipt.
            assertThrows(DataAccessException.class,()->f.sql.update("""
                    INSERT INTO hidra_analytics_metric_evaluation_run
                        (id,metric_definition_version_id,run_status,period_start,period_end,
                         scope_type,scope_id,started_at,created_at)
                    VALUES (? ,?,'RUNNING',now(),now(),'FACILITY',?,now(),now())
                    """,id(),version,f.asset));
            assertEquals(2L,f.sql.queryForObject(
                    "select count(*) from hidra_analytics_deviation_metric_receipt",Long.class));

            try(var next=f.start()) {
                assertNotEquals(firstPid,next.pid(),"Must start independent application JVM");
                String operator=next.login(f.operator);
                JsonNode read=next.get(base(f)+"/"+runId,operator);
                assertEquals(5,read.path("value").decimalValue().intValueExact());
                assertEquals(computed,text(read.path("source"),"evaluationId"));
                assertEquals("COMPLETED",text(read,"status"));
                JsonNode unavailable=next.get(base(f)+"/"+failedId,operator);
                assertEquals("FAILED",text(unavailable,"status"));
                assertEquals("SOURCE_UNAVAILABLE",text(unavailable,"errorCode"));
                assertFalse(unavailable.hasNonNull("value"), "A failed run must not expose a metric value after restart");
                assertEquals("VALUE_OUT_OF_RANGE",AbsoluteDeviationMetricV1.evaluate(
                        "COMPUTED",new BigDecimal("9999999999999.000001"),BigDecimal.ZERO).errorCode());
                assertEquals(runId,text(next.json("POST",base(f),operator,
                        Map.of("definitionVersionId",version,"evaluationId",computed),200),"runId"));
            }
        }
    }
}
