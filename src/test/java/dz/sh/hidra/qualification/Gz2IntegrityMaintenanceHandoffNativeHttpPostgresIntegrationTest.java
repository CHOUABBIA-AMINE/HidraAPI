/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : Gz2IntegrityMaintenanceHandoffNativeHttpPostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-15
 * @Type        : Class
 * @Layer       : Test
 * @Module      : qualification
 * @Package     : dz.sh.hidra.qualification
 * @Description : Signed synthetic GZ2 Integrity approval and independent Assets maintenance verification.
 */
package dz.sh.hidra.qualification;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class Gz2IntegrityMaintenanceHandoffNativeHttpPostgresIntegrationTest {
    @Test
    void approvedFindingProducesVerifiedMaintenanceOutcome() throws Exception {
        try (var postgres=new PostgreSQLContainer<>("postgres:16")
                .withDatabaseName("hidra_gz2_t18")
                .withCommand("postgres","-c","track_commit_timestamp=on","-c","autovacuum=off")) {
            postgres.start();
            var f=new Gz2SignedWorkflowFixture(postgres);
            String equipment=Gz2SignedWorkflowFixture.id();
            String asset=Gz2SignedWorkflowFixture.id();
            String run=Gz2SignedWorkflowFixture.id();
            String finding=Gz2SignedWorkflowFixture.id();
            String defect=Gz2SignedWorkflowFixture.id();
            String icase=Gz2SignedWorkflowFixture.id();
            String recommendation=Gz2SignedWorkflowFixture.id();
            String workOrderType=f.catalog("asset","WORK_ORDER_TYPE","T18_SYNTHETIC_REPAIR");
            seed(f,equipment,asset,run,finding,defect,icase,recommendation);
            grant(f,f.author,"integrity:maintenance:approve",equipment);
            grant(f,f.author,"integrity:maintenance:read",equipment);
            grant(f,f.author,"assets:integrity-work-orders:handoff",equipment);
            grant(f,f.author,"assets:integrity-work-orders:assign",equipment);
            grant(f,f.author,"assets:integrity-work-orders:read",equipment);
            grant(f,f.operator,"assets:integrity-work-orders:execute",equipment);
            grant(f,f.operator,"assets:integrity-work-orders:verify",equipment);
            grant(f,f.reviewer,"assets:integrity-work-orders:verify",equipment);
            grant(f,f.reviewer,"assets:integrity-work-orders:read",equipment);
            grant(f,f.reviewer,"integrity:maintenance:read",equipment);
            grant(f,f.wrongAsset,"assets:integrity-work-orders:read",f.secondAsset);
            grant(f,f.revoked,"assets:integrity-work-orders:read",equipment);
            try (var rt=f.start()) {
                String author=rt.login(f.author);
                String operator=rt.login(f.operator);
                String reviewer=rt.login(f.reviewer);
                String forbidden=rt.login(f.foreign);
                String wrongAssetToken=rt.login(f.wrongAsset);
                String revokedToken=rt.login(f.revoked);
                String integrityPath="/api/v1/integrity/findings/"+finding+"/maintenance-approvals";
                String assetsPath="/api/v1/assets/maintenance-work-orders";

                Map<String,Object> approvalRequest=Map.of(
                    "commandId",UUID.randomUUID().toString(),
                    "recommendationId",recommendation,"caseId",icase,
                    "reason","Qualified synthetic finding requires controlled repair");
                assertEquals(404,rt.send("POST",integrityPath,forbidden,approvalRequest).statusCode());
                assertEquals(404,rt.send("POST",integrityPath,author,
                    Map.of("commandId",UUID.randomUUID().toString(),
                        "recommendationId",recommendation,"caseId",f.secondAsset,
                        "reason","Unbound synthetic case")).statusCode());
                assertEquals("PROPOSED",f.sql.queryForObject(
                    "select status from public.hidra_integrity_recommendation where id=?",String.class,recommendation));
                JsonNode approval=rt.json("POST",integrityPath,author,approvalRequest,200);
                String approvalId=approval.path("approvalId").asText();
                assertFalse(approvalId.isBlank());
                assertEquals("APPROVED",f.sql.queryForObject(
                    "select status from public.hidra_integrity_recommendation where id=?",String.class,recommendation));
                assertTrue(rt.json("POST",integrityPath,author,approvalRequest,200).path("replay").asBoolean());
                assertEquals(409,rt.send("POST",integrityPath,author,
                    Map.of("commandId",approvalRequest.get("commandId"),"recommendationId",recommendation,
                            "caseId",icase,"reason","Different meaning")).statusCode());

                Map<String,Object> handoff=Map.of("commandId",UUID.randomUUID().toString(),
                    "approvalId",approvalId,"maintainableAssetId",asset,
                    "workOrderNumber","GZ2-T18-"+UUID.randomUUID(),
                    "workOrderTypeId",workOrderType,"title","Synthetic controlled defect remediation");
                assertEquals(404,rt.send("POST",assetsPath+"/from-integrity",forbidden,handoff).statusCode());
                assertEquals(404,rt.send("POST",assetsPath+"/from-integrity",author,
                    Map.of("commandId",UUID.randomUUID().toString(),"approvalId",approvalId,
                        "maintainableAssetId",f.secondAsset,"workOrderNumber","WRONG-"+UUID.randomUUID(),
                        "workOrderTypeId",workOrderType,"title","Mismatched physical asset")).statusCode());
                assertEquals(0,f.sql.queryForObject(
                    "select count(*) from public.hidra_asset_integrity_work_order",Integer.class));
                JsonNode created=rt.json("POST",assetsPath+"/from-integrity",author,handoff,200);
                String orderId=created.path("workOrderId").asText();
                assertFalse(orderId.isBlank());
                assertEquals("DRAFT",created.path("status").asText());
                assertTrue(rt.json("POST",assetsPath+"/from-integrity",author,handoff,200)
                    .path("replay").asBoolean());
                assertEquals(409,rt.send("POST",assetsPath+"/from-integrity",author,
                    Map.of("commandId",UUID.randomUUID().toString(),"approvalId",approvalId,
                        "maintainableAssetId",asset,"workOrderNumber","DUP-"+UUID.randomUUID(),
                        "workOrderTypeId",workOrderType,"title","Competing handoff")).statusCode());
                Integer count=f.sql.queryForObject("""
                    select count(*) from public.hidra_asset_maintenance_work_order
                    where source_recommendation_id=?""",Integer.class,recommendation);
                assertEquals(1,count);

                String base=assetsPath+"/"+orderId;
                String assignKey=UUID.randomUUID().toString();
                JsonNode assigned=rt.json("POST",base+"/assignment",author,
                    Map.of("commandId",assignKey,"expectedVersion",0,
                        "assigneeId",f.operator,"reason","Licensed synthetic field assignee"),200);
                assertEquals("SCHEDULED",assigned.path("status").asText());
                assertEquals(409,rt.send("POST",base+"/start",operator,
                    Map.of("commandId",UUID.randomUUID().toString(),"expectedVersion",0,
                        "reason","stale")).statusCode());
                JsonNode started=rt.json("POST",base+"/start",operator,
                    Map.of("commandId",UUID.randomUUID().toString(),"expectedVersion",1,
                        "reason","Authorised start"),200);
                assertEquals("IN_PROGRESS",started.path("status").asText());
                JsonNode completed=rt.json("POST",base+"/execution",operator,
                    Map.of("commandId",UUID.randomUUID().toString(),"expectedVersion",2,
                        "result","SUCCESS","summary","Synthetic repair execution evidence",
                        "occurredAt",Instant.now().toString()),200);
                assertEquals("COMPLETED",completed.path("status").asText());
                String completionId=completed.path("eventId").asText();
                Map<String,Object> rejected=Map.of(
                    "commandId",UUID.randomUUID().toString(),"expectedVersion",3,
                    "completionId",completionId,"verdict","REJECT",
                    "evidenceSummary","Independent synthetic rejection requiring recheck");
                assertEquals("COMPLETED",rt.json("POST",base+"/verification",reviewer,rejected,200)
                    .path("status").asText());
                assertEquals("REJECT",rt.get("/api/v1/integrity/findings/"+finding+
                    "/maintenance-handoffs/"+approvalId,reviewer).path("verificationStatus").asText());
                Map<String,Object> verification=Map.of(
                    "commandId",UUID.randomUUID().toString(),"expectedVersion",4,
                    "completionId",completionId,"verdict","ACCEPT",
                    "evidenceSummary","Independent synthetic verification; no plant claim");
                assertEquals(409,rt.send("POST",base+"/verification",operator,verification).statusCode());
                JsonNode verified=rt.json("POST",base+"/verification",reviewer,verification,200);
                assertEquals("VERIFIED",verified.path("status").asText());
                assertTrue(rt.json("POST",base+"/verification",reviewer,verification,200)
                        .path("replay").asBoolean());
                assertEquals(409,rt.send("POST",base+"/verification",reviewer,
                    Map.of("commandId",UUID.randomUUID().toString(),"expectedVersion",5,
                    "completionId",completionId,"verdict","ACCEPT",
                    "evidenceSummary","Second positive verification")).statusCode());

                JsonNode assetsHistory=rt.get(base+"/integrity-history",reviewer);
                assertEquals("VERIFIED",assetsHistory.path("status").asText());
                assertEquals(6,assetsHistory.path("events").size());
                JsonNode integrityHistory=rt.get("/api/v1/integrity/findings/"+finding+
                        "/maintenance-handoffs/"+approvalId,reviewer);
                assertEquals(orderId,integrityHistory.path("workOrderId").asText());
                assertEquals("ACCEPT",integrityHistory.path("verificationStatus").asText());
                assertEquals(404,rt.send("GET",base+"/integrity-history",forbidden,null).statusCode());
                assertEquals(404,rt.send("GET",base+"/integrity-history",wrongAssetToken,null).statusCode());
                f.sql.update("""
                    update public.hidra_identity_user_permission_grant
                       set status='REVOKED',revoked_at=now()
                     where user_id=? and scope_type IN ('CUSTOM','EQUIPMENT')
                    """,f.revoked);
                assertEquals(404,rt.send("GET",base+"/integrity-history",revokedToken,null).statusCode());
                assertEquals(1,f.sql.queryForObject(
                    "select count(*) from public.hidra_integrity_maintenance_verification where verdict='ACCEPT'",
                    Integer.class));
                System.out.println("GZ2 T18 signed native HTTP/PostgreSQL approval, handoff and independent verification accepted");
            }
        }
    }

    private static void grant(Gz2SignedWorkflowFixture f,String actor,String permission,String equipment) {
        String module=permission.substring(0,permission.indexOf(':'));
        f.permit(actor,permission,"T18_MAINTENANCE","CUSTOM",module+"-company:"+f.company);
        f.permit(actor,permission,"T18_MAINTENANCE","EQUIPMENT",equipment);
    }

    private static void seed(Gz2SignedWorkflowFixture f,String equipment,String asset,String run,
            String finding,String defect,String icase,String recommendation){
        String type=Gz2SignedWorkflowFixture.id();
        f.sql.update("""
            insert into public.hidra_topology_equipment_type(id,code,name,status,created_at,updated_at)
            values (?,?,'Synthetic GZ2 compressor','ACTIVE',now(),now())
            """,type,type);
        f.sql.update("""
            insert into public.hidra_topology_equipment
              (id,code,facility_id,equipment_type_id,status,created_at,updated_at)
            values (?,?,?,?,'ACTIVE',now(),now())
            """,equipment,equipment,f.asset,type);
        // HRA-111 requires the dedicated Assets type owner, not the generic catalog.
        String assetType=Gz2SignedWorkflowFixture.id();
        f.sql.update("""
            insert into public.hidra_asset_type
              (id,code,active,sort_order,system_defined,created_at,updated_at)
            values (?,'T18_EQUIPMENT',true,0,false,now(),now())
            """,assetType);
        f.sql.update("""
            insert into public.hidra_asset_maintainable_asset
              (id,asset_number,asset_code,asset_name,asset_type_id,
               topology_asset_type_code,topology_asset_id,status,
               registered_at,created_at,updated_at)
            values (?,?,?,?,?,'EQUIPMENT',?,'ACTIVE',now(),now(),now())
            """,asset,asset,asset,"Synthetic GZ2 compressor",assetType,equipment);
        String inspection=f.catalog("integrity","INSPECTION_TYPE","T18_INSPECTION");
        String findingType=f.catalog("integrity","FINDING_TYPE","T18_FINDING");
        String defectType=f.catalog("integrity","DEFECT_TYPE","T18_DEFECT");
        String caseType=f.catalog("integrity","CASE_TYPE","T18_CASE");
        String recType=f.catalog("integrity","RECOMMENDATION_TYPE","T18_REPAIR");
        String campaign=Gz2SignedWorkflowFixture.id();
        f.sql.update("""
            insert into public.hidra_integrity_inspection_campaign
              (id,campaign_number,name,inspection_type_id,status,created_at,updated_at)
            values (?,?,'Synthetic GZ2 campaign',?,'COMPLETED',now(),now())
            """,campaign,campaign,inspection);
        f.sql.update("""
            insert into public.hidra_integrity_inspection_run
              (id,campaign_id,run_number,topology_asset_type_code,
               topology_asset_id,status,created_at,updated_at)
            values (?,?,?,'EQUIPMENT',?,'COMPLETED',now(),now())
            """,run,campaign,run,equipment);
        f.sql.update("""
            insert into public.hidra_integrity_inspection_finding
              (id,inspection_run_id,finding_number,finding_type_id,severity,
               topology_asset_type_code,topology_asset_id,
               observed_at,created_at,updated_at)
            values (?,?,?,?,'HIGH','EQUIPMENT',?,now(),now(),now())
            """,finding,run,finding,findingType,equipment);
        f.sql.update("""
            insert into public.hidra_integrity_pipeline_defect
              (id,defect_number,defect_type_id,status,
               topology_asset_type_code,topology_asset_id,
               source_finding_id,detected_at,created_at,updated_at)
            values (?,?,?,'RECOMMENDED_FOR_REPAIR','EQUIPMENT',?,?,now(),now(),now())
            """,defect,defect,defectType,equipment,finding);
        f.sql.update("""
            update public.hidra_integrity_inspection_finding
            set linked_defect_id=? where id=?
            """,defect,finding);
        f.sql.update("""
            insert into public.hidra_integrity_case
              (id,case_number,title,case_type_id,status,topology_asset_type_code,
               topology_asset_id,primary_defect_id,opened_at,created_at,updated_at)
            values (?,?,'Synthetic GZ2 Integrity case',?,'ACTION_REQUIRED','EQUIPMENT',?,?,now(),now(),now())
            """,icase,icase,caseType,equipment,defect);
        f.sql.update("""
            insert into public.hidra_integrity_recommendation
              (id,recommendation_number,source_defect_id,recommendation_type_id,title,status,created_at)
            values (?,?,?,?,'Approved synthetic corrective work','PROPOSED',now())
            """,recommendation,recommendation,defect,recType);
    }
}
