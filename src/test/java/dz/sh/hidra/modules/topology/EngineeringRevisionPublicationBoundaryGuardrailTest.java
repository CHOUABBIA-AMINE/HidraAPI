/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringRevisionPublicationBoundaryGuardrailTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-20
 *
 * @Type        : Class
 * @Layer       : Topology Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology
 *
 * @Description : Pins exact publication authority, source boundaries, SQL hashes and the single final guard.
 *
 */
package dz.sh.hidra.modules.topology;

import dz.sh.hidra.modules.topology.application.service.ProtectedEngineeringPublicationAdmissionCoordinator;
import dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.JdbcEngineeringRevisionPublicationRepositoryAdapter;
import dz.sh.hidra.modules.identity.application.contract.topology.TopologyProtectedEngineeringDraftAuthorizationContract;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.lang.reflect.Modifier;
import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EngineeringRevisionPublicationBoundaryGuardrailTest {
    private String source(String path)throws Exception{return Files.readString(Path.of("src/main/java/dz/sh/hidra/"+path));}
    @Test void onlyPublicationServiceAndCoordinatorReceiveTheirTechnicalPermit()throws Exception {
        String executor=source("platform/persistence/ProtectedTransactionExecutor.java");
        assertTrue(executor.contains("!caller.equals(PUBLICATION_SERVICE)"));
        assertTrue(executor.contains("!caller.equals(PUBLICATION_COORDINATOR)"));
        assertTrue(executor.contains("!caller.equals(permit.owner)"));
        assertTrue(executor.contains("permit.scope!=s"));
        for(var type:List.of(ProtectedEngineeringPublicationAdmissionCoordinator.Prepared.class,ProtectedEngineeringPublicationAdmissionCoordinator.Admission.class)) {
            assertEquals(0,type.getConstructors().length);
            for(var constructor:type.getDeclaredConstructors())assertTrue(Modifier.isPrivate(constructor.getModifiers()));
            for(var field:type.getDeclaredFields())assertTrue(Modifier.isPrivate(field.getModifiers()));
        }
    }
    @Test void publicationDmlTemplatesAndHashesRemainExact()throws Exception {
        Set<String> expected=Set.of("321c4652f15f2da9f76f5ca12395cd899ac3d55c49e8039bdd46b96a1d8fb155","28c7c4023a6d0a597703912a57e0813c04b18fb6caf4d11a4cc91c50be5385b1","76b726f5c9ead80cfebb8cf1b9f109f7f36a5438465f760687f9c8225b62467a","845f50127991e910b122499565576ea758963779dfbcda3df382a92e38dc3ca1");
        Set<String> actual=new HashSet<>();
        for(String field:List.of("SNAPSHOT_INS","EVENT_INS","HEAD_INS","HEAD_UPDATE")) {
            String sql=(String)JdbcEngineeringRevisionPublicationRepositoryAdapter.class.getField(field).get(null);
            assertFalse(sql.contains(";"));assertFalse(sql.contains("--"));
            actual.add(HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(sql.getBytes(StandardCharsets.UTF_8))));
        }
        assertEquals(expected,actual);
        String coordinator=source("modules/topology/application/service/ProtectedEngineeringPublicationAdmissionCoordinator.java");
        String executor=source("platform/persistence/ProtectedTransactionExecutor.java");
        for(String digest:expected){assertTrue(coordinator.contains(digest));assertTrue(executor.contains(digest));}
    }
    @Test void domainAndTransportKeepOwnerAndFrameworkBoundaries()throws Exception {
        String domain=source("modules/topology/domain/model/EngineeringRevisionPublication.java");
        for(String forbidden:List.of("org.springframework","jakarta.persistence","dz.sh.hidra.platform","java.sql"))assertFalse(domain.contains(forbidden));
        String coordinator=source("modules/topology/application/service/ProtectedEngineeringPublicationAdmissionCoordinator.java");
        for(String forbidden:List.of(".infrastructure.","JdbcTemplate","Instant.now(","clock_timestamp(","java.util.function"))assertFalse(coordinator.contains(forbidden));
        String request=source("modules/topology/api/rest/request/EngineeringRevisionPublicationRequests.java");
        for(String forbidden:List.of(".domain.","actorUserId","bindingId","effectiveFrom","engineeringQualified","canonical_payload"))assertFalse(request.contains(forbidden));
        String port=source("modules/topology/application/port/out/EngineeringRevisionPublicationRepositoryPort.java");
        assertFalse(port.contains(".application.service."));
        assertTrue(coordinator.indexOf("identity.contribute(")<coordinator.indexOf("repository.eligible("));
        assertTrue(coordinator.contains("fi.size()!=24"));assertTrue(coordinator.contains("fp.size()!=2"));assertTrue(coordinator.contains("int total=38"));
    }
    @Test void publicationPermissionsCannotAliasDraftOrStationActions() {
        var action=TopologyProtectedEngineeringDraftAuthorizationContract.Action.class;
        for(String verb:List.of("PUBLISH","WITHDRAW","REACTIVATE","READ")) {
            var selected=Enum.valueOf(action,"PUBLICATION_"+verb);
            assertEquals("topology:engineering-publication:"+verb.toLowerCase(Locale.ROOT),TopologyProtectedEngineeringDraftAuthorizationContract.permissionCode(selected));
            assertEquals("ENGINEERING_NETWORK_PUBLICATION",TopologyProtectedEngineeringDraftAuthorizationContract.resourceType(selected));
        }
        for(String verb:List.of("CREATE","READ","UPDATE","VALIDATE")) {
            var selected=Enum.valueOf(action,verb);
            assertEquals("topology:engineering-draft:"+verb.toLowerCase(Locale.ROOT),TopologyProtectedEngineeringDraftAuthorizationContract.permissionCode(selected));
            assertEquals("ENGINEERING_NETWORK_DRAFT",TopologyProtectedEngineeringDraftAuthorizationContract.resourceType(selected));
        }
    }
    @Test void exactRoutesAndFinalGuardRemainClosed()throws Exception {
        String controller=source("modules/topology/api/rest/controller/EngineeringRevisionPublicationController.java");
        assertEquals(8,controller.split("@Operation\\(", -1).length-1);
        String adapter=source("modules/topology/infrastructure/persistence/adapter/JdbcEngineeringRevisionPublicationRepositoryAdapter.java");
        String guard=adapter.substring(adapter.indexOf("public boolean finalGuard("));
        assertEquals(1,guard.split("jdbc.queryForObject", -1).length-1);
        assertTrue(guard.contains("AS MATERIALIZED (SELECT clock_timestamp() AS t)"));
        assertTrue(guard.contains("fences.size()!=38"));assertTrue(guard.contains("count()!=34"));
        assertTrue(guard.contains("l.granted"));assertFalse(guard.contains("verifyCurrent("));
        assertTrue(guard.contains("s.canonical_payload=?"));assertTrue(guard.contains("p.canonical_payload=?"));
        String migration=Files.readString(Path.of("src/main/resources/db/migration/V20261020_002__p25_topology_engineering_publications.sql"));
        assertTrue(migration.contains("CHECK(NOT engineering_qualified)"));assertTrue(migration.contains("FOR UPDATE"));
        for(String function:List.of("snapshot","event","head")) {
            String marker="CREATE FUNCTION public.hidra_p25_publication_"+function+"_guard()";
            int start=migration.indexOf("$guard$",migration.indexOf(marker))+7;
            String body=migration.substring(start,migration.indexOf("$guard$;",start));
            String digest=HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(body.getBytes(StandardCharsets.UTF_8)));
            assertTrue(adapter.contains(digest));
        }
    }
}
