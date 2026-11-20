/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RegionalAccessPostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-20
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.qualification
 *
 * @Description : Verifies T33 regional read scope, deny precedence and owner evidence.
 *
 */
// T33 is physically registered under Identity's exact file ledger. This suite uses the
// package-private shared GZ2 signed-client fixture rather than duplicating its launch,
// Flyway, native-bearer and limited runtime-role setup.
package dz.sh.hidra.qualification;

import com.fasterxml.jackson.databind.JsonNode;
import java.time.Instant;
import java.sql.Timestamp;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import static org.junit.jupiter.api.Assertions.*;

class RegionalAccessPostgresIntegrationTest {
    private static final String ROUTE = "identity:me:read";
    private static final String READ = "topology:regional-resource:read";

    @Test
    void regionalSignedHttpIsolatedByCurrentEmployeeGrantResponsibilityAndLiveTopology() throws Exception {
        try (var db = new PostgreSQLContainer<>("postgres:16")
                .withDatabaseName("hidra_gz2_t33")) {
            db.start();
            var f = new Gz2SignedWorkflowFixture(db);
            String foreignSystem = Gz2SignedWorkflowFixture.id();
            String pipeline = Gz2SignedWorkflowFixture.id();
            String section = Gz2SignedWorkflowFixture.id();
            String equipment = Gz2SignedWorkflowFixture.id();

            seedTopology(f, foreignSystem, pipeline, section, equipment);
            for (String actor : List.of(f.reader, f.foreign, f.revoked,
                    f.operator, f.author, f.wrongAsset)) {
                f.permit(actor, ROUTE, "IDENTITY_REGIONAL_ACCESS", "GLOBAL", null);
            }

            bindEmployee(f, f.reader, f.pipeline, false);
            bindEmployee(f, f.foreign, foreignSystem, false);
            bindEmployee(f, f.revoked, f.pipeline, false);
            bindEmployee(f, f.operator, f.pipeline, true);
            bindEmployee(f, f.author, f.pipeline, false);

            for (String actor : List.of(f.reader, f.revoked, f.operator, f.author)) {
                f.permit(actor, READ, "TOPOLOGY_REGIONAL_RESOURCE", "PIPELINE_SYSTEM", f.pipeline);
            }
            f.permit(f.foreign, READ, "TOPOLOGY_REGIONAL_RESOURCE",
                    "PIPELINE_SYSTEM", foreignSystem);
            explicitlyDeny(f, f.author, f.pipeline);

            try (var client = f.start()) {
                String reader = client.login(f.reader);
                String foreign = client.login(f.foreign);
                String revoked = client.login(f.revoked);
                String expired = client.login(f.operator);
                String denied = client.login(f.author);
                String unlinked = client.login(f.wrongAsset);
                String noRoute = client.login(f.producer);

                String system = path("PIPELINE_SYSTEM", f.pipeline, f.company);
                String foreignPath = path("PIPELINE_SYSTEM", foreignSystem, f.foreignCompany);
                JsonNode permitted = client.get(system, reader);
                assertTrue(permitted.path("permitted").asBoolean());
                assertEquals(f.company, permitted.path("companyPartyId").asText());
                assertTrue(client.get(foreignPath, foreign).path("permitted").asBoolean());

                // A parent responsibility and matching current Identity grant admit
                // only the owner-proven pipeline, segment, station and equipment.
                assertTrue(client.get(path("PIPELINE", pipeline, f.company), reader)
                        .path("permitted").asBoolean());
                assertTrue(client.get(path("PIPELINE_SEGMENT", section, f.company), reader)
                        .path("permitted").asBoolean());
                assertTrue(client.get(path("FACILITY", f.asset, f.company), reader)
                        .path("permitted").asBoolean());
                assertTrue(client.get(path("EQUIPMENT", equipment, f.company), reader)
                        .path("permitted").asBoolean());

                assertEquals(404, status(client, system, foreign));
                assertEquals(404, status(client, foreignPath, reader));
                assertEquals(404, status(client, system, unlinked));
                assertEquals(404, status(client, system, expired));
                assertEquals(404, status(client, system, denied));
                assertEquals(404, status(client,
                        path("PIPELINE_SYSTEM", f.pipeline, f.foreignCompany), reader));
                assertEquals(404, status(client,
                        path("PIPELINE", "unknown-pipeline", f.company), reader));
                assertEquals(401, status(client, system, null));
                assertEquals(403, status(client, system, noRoute));

                assertTrue(client.get(system, revoked).path("permitted").asBoolean());
                f.sql.update("""
                        UPDATE hidra_identity_user_permission_grant SET status='REVOKED',revoked_at=now()
                        WHERE user_id=? AND scope_type='PIPELINE_SYSTEM' AND scope_reference_id=?
                          AND permission_id=(SELECT id FROM hidra_identity_permission WHERE code=?)
                        """, f.revoked, f.pipeline, READ);
                assertEquals(404, status(client, system, revoked),
                        "A signed bearer issued before revocation never preserves G1 access");

                // An active physical association to another system makes the facility
                // and dual-linked equipment ambiguous; the system read is unaffected.
                f.sql.update("""
                        INSERT INTO hidra_topology_pipeline_system_facility(
                          id,pipeline_system_id,facility_id,status,created_at,updated_at)
                        VALUES (?,?,?,'ACTIVE',now(),now())
                        """, Gz2SignedWorkflowFixture.id(), foreignSystem, f.asset);
                assertEquals(404, status(client, path("FACILITY", f.asset, f.company), reader));
                assertEquals(404, status(client, path("EQUIPMENT", equipment, f.company), reader));
                assertTrue(client.get(system, reader).path("permitted").asBoolean());
            }
        }
    }

    private static int status(Gz2SignedWorkflowFixture.Runtime client, String route, String token)
            throws Exception {
        var response = client.send("GET", route, token, null);
        // No response can serialize another region's label, coordinates or counts.
        assertFalse(response.body().contains("latitude"));
        assertFalse(response.body().contains("longitude"));
        assertFalse(response.body().contains("totalElements"));
        return response.statusCode();
    }

    private static String path(String kind, String id, String company) {
        return "/api/v1/identity/me/regional-access/" + kind + "/" + id
                + "?companyPartyId=" + company;
    }

    private static void seedTopology(Gz2SignedWorkflowFixture f, String foreignSystem,
            String pipeline, String section, String equipment) {
        f.sql.update("UPDATE hidra_topology_pipeline_system SET status='ACTIVE' WHERE id=?", f.pipeline);
        f.sql.update("""
                INSERT INTO hidra_topology_foundation_system_owner(pipeline_system_id,company_party_id)
                VALUES (?,?)
                """, f.pipeline, f.company);
        f.sql.update("""
                INSERT INTO hidra_topology_pipeline_system(
                  id,code,system_type_id,status,created_at,updated_at)
                SELECT ?,?,system_type_id,'ACTIVE',now(),now()
                FROM hidra_topology_pipeline_system WHERE id=?
                """, foreignSystem, "T33-GAS-FOREIGN-" + foreignSystem, f.pipeline);
        f.sql.update("""
                INSERT INTO hidra_topology_foundation_system_owner(pipeline_system_id,company_party_id)
                VALUES (?,?)
                """, foreignSystem, f.foreignCompany);

        String type = Gz2SignedWorkflowFixture.id();
        f.sql.update("""
                INSERT INTO hidra_topology_pipeline_type(id,code,active,created_at,updated_at)
                VALUES (?,?,true,now(),now())
                """, type, "T33-SYNTHETIC-" + type);
        f.sql.update("""
                INSERT INTO hidra_topology_pipeline(
                  id,pipeline_system_id,code,pipeline_type_id,status,created_at,updated_at)
                VALUES (?,?,?,?, 'ACTIVE',now(),now())
                """, pipeline, f.pipeline, "T33-GZ2-PIPELINE", type);
        f.sql.update("""
                INSERT INTO hidra_topology_pipeline_authoring_revision(
                  pipeline_id,revision,company_party_id,pipeline_system_id,
                  code,pipeline_type_id,actor_user_id,recorded_at)
                VALUES (?,1,?,?,?,?,?,now())
                """, pipeline, f.company, f.pipeline, "T33-GZ2-PIPELINE", type, f.reader);

        String start = Gz2SignedWorkflowFixture.id();
        String end = Gz2SignedWorkflowFixture.id();
        for (String node : List.of(start, end)) {
            f.sql.update("""
                    INSERT INTO hidra_topology_node(
                      id,code,node_type,status,created_at,updated_at)
                    VALUES (?,?,'JUNCTION','ACTIVE',now(),now())
                    """, node, "T33-NODE-" + node);
        }
        f.sql.update("""
                INSERT INTO hidra_topology_pipeline_segment(
                  id,pipeline_id,code,segment_type,from_node_id,to_node_id,
                  flow_direction,status,created_at,updated_at)
                VALUES (?,?,?,'MAINLINE',?,?,'DIRECTED','ACTIVE',now(),now())
                """, section, pipeline, "T33-SECTION", start, end);

        String equipmentType = Gz2SignedWorkflowFixture.id();
        f.sql.update("""
                INSERT INTO hidra_topology_equipment_type(
                  id,code,name,status,created_at,updated_at)
                VALUES (?,?,'Synthetic GZ2 equipment','ACTIVE',now(),now())
                """, equipmentType, equipmentType);
        f.sql.update("""
                INSERT INTO hidra_topology_equipment(
                  id,code,facility_id,pipeline_segment_id,equipment_type_id,
                  status,created_at,updated_at)
                VALUES (?,?,?,?,?,'ACTIVE',now(),now())
                """, equipment, "T33-EQUIPMENT", f.asset, section, equipmentType);
    }

    private static void bindEmployee(Gz2SignedWorkflowFixture f, String actor,
            String system, boolean expired) {
        String employee = Gz2SignedWorkflowFixture.id();
        f.sql.update("""
                INSERT INTO hidra_org_employee(
                  id,employee_number,employee_type,status,identity_user_reference,created_at,updated_at)
                VALUES (?,?,'PERMANENT','ACTIVE',?,now(),now())
                """, employee, "T33-" + employee, actor);
        f.sql.update("UPDATE hidra_identity_user SET employee_reference_id=? WHERE id=?", employee, actor);
        List<Long> registered = f.sql.queryForList("""
                SELECT id FROM hidra_org_operational_scope
                WHERE scope_type='PIPELINE_SYSTEM' AND target_id=?
                """, Long.class, system);
        Long scope = registered.isEmpty()
                ? f.sql.queryForObject("""
                    INSERT INTO hidra_org_operational_scope(scope_type,target_id)
                    VALUES ('PIPELINE_SYSTEM',?) RETURNING id
                    """, Long.class, system)
                : registered.getFirst();
        f.sql.update("""
                INSERT INTO hidra_org_responsibility_assignment(
                  id,responsibility_type,assignee_type,assignee_id,scope_id,valid_from,valid_to,
                  status,created_at,updated_at)
                VALUES (?,'RESPONSIBLE','EMPLOYEE',?,?,
                  now()-interval '2 hours',?,'ACTIVE',now(),now())
                """, Gz2SignedWorkflowFixture.id(), employee, scope,
                Timestamp.from(expired ? Instant.now().minusSeconds(15) : Instant.now().plusSeconds(7200)));
    }

    private static void explicitlyDeny(Gz2SignedWorkflowFixture f, String actor, String scope) {
        f.sql.update("""
                INSERT INTO hidra_identity_user_permission_grant(
                  id,user_id,permission_id,effect,scope_type,scope_reference_id,
                  grant_reason,emergency_access,valid_from,valid_to,status,created_at)
                SELECT ?,?,id,'DENY','PIPELINE_SYSTEM',?,
                  'Synthetic T33 precedence',false,now()-interval '1 hour',
                  now()+interval '2 hours','ACTIVE',now()
                FROM hidra_identity_permission WHERE code=?
                """, Gz2SignedWorkflowFixture.id(), actor, scope, READ);
    }
}
