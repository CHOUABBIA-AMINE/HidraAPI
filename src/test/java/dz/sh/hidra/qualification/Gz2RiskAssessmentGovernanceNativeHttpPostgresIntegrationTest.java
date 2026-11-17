/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : Gz2RiskAssessmentGovernanceNativeHttpPostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-17
 * @Type        : Class
 * @Layer       : Test
 * @Module      : qualification
 * @Package     : dz.sh.hidra.qualification
 * @Description : Qualifies signed scoped Risk provenance, independent decisions and immutable reads on PostgreSQL.
 */
package dz.sh.hidra.qualification;

import com.fasterxml.jackson.databind.JsonNode;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import static org.junit.jupiter.api.Assertions.*;

class Gz2RiskAssessmentGovernanceNativeHttpPostgresIntegrationTest {
    private static String id(){return Gz2SignedWorkflowFixture.id();}
    private static String field(JsonNode json,String name) {
        assertTrue(json.hasNonNull(name),"Missing "+name+": "+json);
        return json.get(name).asText();
    }
    private static void denied(Gz2SignedWorkflowFixture.Runtime runtime,String method,
            String path,String bearer,Object body) throws Exception {
        int status=runtime.send(method,path,bearer,body).statusCode();
        assertTrue(status==400||status==403||status==404||status==409||status==422,
                ()->"Protected request unexpectedly accepted: "+method+" "+path+" status "+status);
    }
    @Test void approvesScopedRiskAndReadsImmutableDecisionHistory() throws Exception {
        try(var postgres=new PostgreSQLContainer<>("postgres:16")
                .withDatabaseName("hidra_gz2_t22")) {
            postgres.start();
            var f=new Gz2SignedWorkflowFixture(postgres);
            var review=f.riskReview();
            String registerType=f.catalog("risk","RISK_REGISTER_TYPE","T22_GAS_REGISTER");
            String assessmentType=f.catalog("risk","RISK_ASSESSMENT_TYPE","T22_GAS_RISK");
            String method=f.catalog("risk","RISK_METHODLOGY","T22_MATRIX");
            String matrixType=f.catalog("risk","RISK_MATRIX_TYPE","T22_MATRIX");
            String likelihood=f.catalog("risk","RISK_LIKELIHOOD_LEVEL","T22_POSSIBLE");
            String consequence=f.catalog("risk","RISK_CONSEQUENCE_LEVEL","T22_CONSEQUENCE");
            String rating=f.catalog("risk","RISK_RATING","T22_RATING");
            String scenarioType=f.catalog("risk","RISK_SCENARIO_TYPE","T22_GAS_SCENARIO");
            String register=id(), matrix=id(),cell=id(),alarm=id(),scenario=id();
            // Assessment persistence references the concrete Risk scenario table.
            // Both the Risk-owned scenario and its cataloged type must be seeded.
            f.sql.update("""
                INSERT INTO hidra_risk_scenario(id,code,name_fr,scenario_type_id,
                    active,created_at,updated_at)
                VALUES (?, ?, 'Synthetic GZ2 Risk scenario', ?, true, now(), now())
                """,scenario,"GZ2-"+scenario,scenarioType);
            f.sql.update("""
                INSERT INTO hidra_risk_register(id,code,name_fr,register_type_id,
                    scope_type,scope_id,status,created_at,updated_at)
                VALUES (?,?,'Synthetic GZ2 facility risk',?,'FACILITY',?,'DRAFT',now(),now())
                """,register,"GZ2-"+register,registerType,f.asset);
            f.sql.update("""
                INSERT INTO hidra_risk_matrix(id,code,name_fr,matrix_type_id,version,status,
                    created_at,updated_at)
                VALUES (?,?,'Synthetic inherent Risk matrix',?,'t22-v1','ACTIVE',now(),now())
                """,matrix,"GZ2-"+matrix,matrixType);
            // The matrix cell uses a concrete Risk rating entity, not merely its
            // independently validated catalog entry. Seed both with one ID so
            // PostgreSQL's same-owner FK remains enforced in the synthetic journey.
            f.sql.update("""
                INSERT INTO hidra_risk_rating(
                    id,code,name_fr,severity_order,requires_treatment,
                    requires_approval,active,created_at,updated_at)
                VALUES (?,'T22_RATING','Synthetic GZ2 Risk rating',1,false,true,true,now(),now())
                """,rating);
            f.sql.update("""
                INSERT INTO hidra_risk_matrix_cell(id,risk_matrix_id,likelihood_level_id,
                    consequence_level_id,score_value,rating_id,requires_treatment,
                    requires_approval,requires_executive_acceptance,created_at,updated_at)
                VALUES (?,?,?,?,17.25,?,false,true,false,now(),now())
                """,cell,matrix,likelihood,consequence,rating);
            String alarmType=f.catalog("alarm","ALARM_TYPE","T22_TEST_ALARM");
            String severity=f.catalog("alarm","ALARM_SEVERITY","T22_TEST_SEVERITY");
            f.sql.update("""
                INSERT INTO hidra_alarm(id,alarm_number,alarm_type_id,severity_id,title_fr,
                    source_type,topology_asset_type_code,topology_asset_id,topology_asset_code,
                    current_state,raised_at,last_updated_at,created_at,updated_at)
                VALUES (?,?,?,?,?,'MANUAL','FACILITY',?,?,'ACTIVE',now(),now(),now(),now())
                """,alarm,"T22-"+alarm,alarmType,severity,"GZ2 synthetic observation",f.asset,f.asset);
            for(String principal:List.of(f.author,f.reviewer,f.operator,f.reader)) {
                f.assetGrant(principal,"risk:assessments:read",f.company,f.asset);
            }
            f.assetGrant(f.author,"risk:assessments:author",f.company,f.asset);
            f.assetGrant(f.reviewer,"risk:assessments:review",f.company,f.asset);
            f.assetGrant(f.operator,"risk:assessments:review",f.company,f.asset);
            f.assetGrant(f.author,"alarm:alarms:read",f.company,f.asset);
            f.assetGrant(f.foreign,"risk:assessments:read",f.foreignCompany,f.asset);
            f.assetGrant(f.wrongAsset,"risk:assessments:read",f.company,f.secondAsset);
            f.assetGrant(f.revoked,"risk:assessments:read",f.company,f.asset);
            f.global(f.author,"risk:assessments:execute","risk:evidence:execute",
                    "workflow:instances:execute","workflow:tasks:execute");
            f.global(f.reviewer,"workflow:tasks:execute","workflow:tasks:read");
            f.global(f.operator,"workflow:tasks:execute","workflow:tasks:read");
            try(var runtime=f.start()) {
                String author=runtime.login(f.author),reviewer=runtime.login(f.reviewer);
                String approver=runtime.login(f.operator),reader=runtime.login(f.reader);
                String foreign=runtime.login(f.foreign),wrong=runtime.login(f.wrongAsset);
                String revoked=runtime.login(f.revoked);
                String create="/api/v1/risk/assessments";
                Map<String,Object> request=Map.ofEntries(
                    Map.entry("riskRegisterId",register),Map.entry("assessmentNumber","T22-"+id()),
                    Map.entry("title","GZ2 synthetic scoped assessment"),
                    Map.entry("assessmentTypeId",assessmentType),
                    Map.entry("methodologyId",method),Map.entry("riskScenarioId",scenario),
                    Map.entry("assessedByActorId",f.author),
                    Map.entry("scopes",List.of(Map.of("scopeType","FACILITY","scopeId",f.asset,
                            "included",true))));
                var created=runtime.json("POST",create,author,request,200);
                String assessment=field(created,"id"),base=create+"/"+assessment;
                denied(runtime,"GET",base,foreign,null);
                denied(runtime,"GET",base,wrong,null);
                denied(runtime,"POST",create,foreign,request);
                denied(runtime,"POST","/api/v1/risk/evidence",author,Map.of(
                        "riskAssessmentId",assessment,"evidenceModule","alarm",
                        "evidenceType","Alarm","evidenceId",f.secondAsset));
                String evidence="/api/v1/risk/evidence";
                assertEquals(200,runtime.send("POST",evidence,author,Map.of(
                        "riskAssessmentId",assessment,"evidenceModule","alarm",
                        "evidenceType","Alarm","evidenceId",alarm)).statusCode());
                var scored=runtime.json("POST",base+"/score",author,
                        Map.of("assessmentId",assessment,"inherentCellId",cell),200);
                assertEquals("17.25",scored.path("inherentScore").decimalValue().stripTrailingZeros().toPlainString());
                var submitted=runtime.json("POST",base+"/submit",author,null,200);
                assertEquals("UNDER_REVIEW",field(submitted,"status"));
                JsonNode before=runtime.get(base,reader);
                String digest=field(before,"submissionDigest");
                assertEquals(64,digest.length());
                assertEquals(cell,field(before,"inherentCellId"));
                assertEquals("t22-v1",field(before,"inherentMatrixVersion"));
                var rejectedScore=runtime.json("POST",base+"/score",author,
                        Map.of("assessmentId",assessment,"inherentCellId",cell),422);
                assertEquals("RISK_INVALID_OPERATION",field(rejectedScore,"code"));
                denied(runtime,"POST",evidence,author,Map.of("riskAssessmentId",assessment,
                        "evidenceModule","alarm","evidenceType","Alarm","evidenceId",alarm));
                String instance=field(runtime.json("POST","/api/v1/workflow/instances",author,Map.of(
                        "definitionId",review.definition(),"definitionVersion",1,
                        "workflowPurposeId",review.purpose(),"targetModule","risk",
                        "targetTypeId",review.type(),"targetId",assessment,
                        "targetCodeSnapshot",field(created,"assessmentNumber"),
                        "currentStepId",review.firstStep(),"startedByActorId",f.author),200),"id");
                String initialTask=field(runtime.json("POST","/api/v1/workflow/tasks",author,
                        Map.of("instanceId",instance,"stepId",review.firstStep(),
                                "assignedActorId",f.reviewer,"taskLabelSnapshot","Synthetic T22 reviewer"),200),"id");
                String firstTaskPath="/api/v1/workflow/tasks/"+initialTask;
                JsonNode loaded=runtime.get(firstTaskPath,reviewer);
                JsonNode first=runtime.json("POST",firstTaskPath+"/transitions/"
                        +review.firstTransition()+"/execute",reviewer,
                        Map.of("expectedTaskUpdatedAt",field(loaded,"updatedAt")),200);
                String reviewAction=field(first,"actionId");
                String next=field(first,"nextTaskId");
                String approvalTaskPath="/api/v1/workflow/tasks/"+next;
                JsonNode approvalTask=runtime.get(approvalTaskPath,approver);
                Map<String,Object> approval=Map.ofEntries(
                        Map.entry("assessmentId",assessment),Map.entry("workflowInstanceId",instance),
                        Map.entry("taskId",next),Map.entry("transitionId",review.finalTransition()),
                        Map.entry("reviewActionId",reviewAction),
                        Map.entry("expectedTaskUpdatedAt",field(approvalTask,"updatedAt")));
                // Neither a generic terminal decision nor a stale approval may publish Risk.
                denied(runtime,"POST",approvalTaskPath+"/transitions/"
                        +review.finalTransition()+"/execute",approver,
                        Map.of("expectedTaskUpdatedAt",field(approvalTask,"updatedAt")));
                denied(runtime,"POST",base+"/approve",reviewer,approval);
                assertEquals(0L,f.sql.queryForObject(
                        "SELECT count(*) FROM hidra_risk_governed_approval WHERE assessment_id=?",
                        Long.class,assessment));
                var accepted=runtime.json("POST",base+"/approve",approver,approval,200);
                assertEquals("APPROVED",field(accepted,"status"));
                JsonNode finalRead=runtime.get(base,reader);
                assertEquals("APPROVED",field(finalRead,"status"));
                assertEquals(digest,field(finalRead,"submissionDigest"));
                assertEquals(alarm,field(finalRead,"alarmId"));
                JsonNode history=runtime.get(base+"/decision-history",reader);
                assertTrue(history.isArray());assertEquals(1,history.size());
                assertEquals(reviewAction,field(history.get(0),"reviewActionId"));
                assertEquals(f.reviewer,field(history.get(0),"reviewerId"));
                assertEquals(f.operator,field(history.get(0),"approverId"));
                assertEquals(digest,field(history.get(0),"submissionDigest"));
                denied(runtime,"GET",base+"/decision-history",foreign,null);
                denied(runtime,"GET",base+"/decision-history",wrong,null);
                f.sql.update("""
                    UPDATE hidra_identity_user_permission_grant SET valid_to=now()-interval '1 second'
                    WHERE user_id=? AND permission_id IN (
                        SELECT id FROM hidra_identity_permission WHERE code='risk:assessments:read')
                    """,f.revoked);
                denied(runtime,"GET",base,revoked,null);
                assertThrows(org.springframework.dao.DataAccessException.class,()->
                    f.sql.update("UPDATE hidra_risk_governed_submission SET digest=? WHERE assessment_id=?",
                            "0".repeat(64),assessment));
                assertThrows(org.springframework.dao.DataAccessException.class,()->
                    f.sql.update("DELETE FROM hidra_risk_governed_approval WHERE assessment_id=?",assessment));
                assertEquals(digest,field(runtime.get(base,reader),"submissionDigest"));
            }
        }
    }
}
