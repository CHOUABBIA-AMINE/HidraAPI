/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProtectedStationAdmissionBoundaryGuardrailTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-18
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology
 *
 * @Description : Preserves pure owner exports and pins admission catalog checks to actual migration bodies.
 *
 */
package dz.sh.hidra.modules.topology;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.nio.file.*;
import java.security.MessageDigest;
import java.util.*;
import java.lang.reflect.Modifier;
import dz.sh.hidra.modules.topology.application.service.ProtectedStationAdmissionCoordinator;
class ProtectedStationAdmissionBoundaryGuardrailTest {
    private String source(String relative) throws Exception { return Files.readString(Path.of("src/main/java/dz/sh/hidra/"+relative)); }
    @Test void coordinatorUsesOnlyApprovedOwnerContractsAndTechnicalMechanics() throws Exception {
        String s=source("modules/topology/application/service/ProtectedStationAdmissionCoordinator.java");
        assertFalse(s.contains(".infrastructure."));assertFalse(s.contains("JdbcTemplate"));assertFalse(s.contains("java.util.function"));assertFalse(s.contains("Instant.now("));assertFalse(s.contains("clock_timestamp"));
        assertTrue(s.contains("identity.acquire("));assertTrue(s.contains("party.acquire("));assertTrue(s.contains("facility.acquire("));assertTrue(s.contains("identity.contribute("));
        for(String method:List.of("beginAdmission","sealAdmission","requireAdmission","rejectAdmission")) {
            var found=new HashSet<String>();
            try(var files=Files.walk(Path.of("src/main/java"))) {for(var f:files.filter(x->x.toString().endsWith(".java")).toList())if(Files.readString(f).contains("."+method+"("))found.add(f.getFileName().toString());}
            var expected=method.equals("rejectAdmission")
                ?Set.of("ProtectedStationAdmissionCoordinator.java","JdbcStationGraphProtectedPublicationAdapter.java")
                :Set.of("ProtectedStationAdmissionCoordinator.java");
            assertEquals(expected,found);
        }
    }
    @Test void identityAdmissionAndCollectorHaveOnlyReviewedCallers() throws Exception {
        var expected=Map.of("collectFenced",Set.of("JdbcIdentityAuthorityFence.java"),"acquireNowait",Set.of("ProtectedOperationIdentityParticipant.java"),"admit",Set.of("ProtectedTopologyAuthorizationParticipant.java"));
        for(var item:expected.entrySet()) {
            var found=new HashSet<String>();try(var files=Files.walk(Path.of("src/main/java"))) {for(var f:files.filter(x->x.toString().endsWith(".java")).toList())if(Files.readString(f).contains("."+item.getKey()+"("))found.add(f.getFileName().toString());}assertEquals(item.getValue(),found);
        }
        String s=source("modules/identity/infrastructure/persistence/adapter/JdbcIdentityTemporalEventCollector.java");assertEquals(1,s.split("SELECT clock_timestamp\\(\\),pg_backend_pid",-1).length-1);
    }
    @Test void admissionCredentialsHaveNoPublicConstructorOrScalarFactory() {
        for(var t:List.of(ProtectedStationAdmissionCoordinator.Prepared.class,ProtectedStationAdmissionCoordinator.Admission.class,
            dz.sh.hidra.modules.identity.infrastructure.security.ProtectedOperationIdentityParticipant.Admitted.class,
            dz.sh.hidra.modules.identity.infrastructure.persistence.adapter.JdbcIdentityAuthorityFence.Admission.class)) {
            assertEquals(0,t.getConstructors().length);for(var c:t.getDeclaredConstructors())assertTrue(Modifier.isPrivate(c.getModifiers()));
            for(var f:t.getDeclaredFields())assertTrue(Modifier.isPrivate(f.getModifiers()));
        }
    }
    @Test void platformHasNoBusinessModuleDependencies() throws Exception {
        String approved="\"dz.sh.hidra.modules.topology.application.service.ProtectedStationAdmissionCoordinator\"";
        for(String name:List.of("ProtectedTransactionExecutor","ProtectedOperationDataSource")) {
            String s=source("platform/persistence/"+name+".java");
            // L pins the compiled issuer by name, without importing or linking a business class.
            assertEquals(name.equals("ProtectedTransactionExecutor")?3:0,s.split(java.util.regex.Pattern.quote(approved),-1).length-1);
            assertFalse(s.replace(approved,"\"reviewed-caller\"").contains("dz.sh.hidra.modules."));
        }
    }
    @Test void existingReaderAndJContractsRemainUnchanged() throws Exception {
        assertEquals("262b2b854bff04d64e712357d75ff099c3cb1d04d207a79e646699b2063facb4",HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(Files.readAllBytes(Path.of("src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JdbcProtectedAuthorizationEvidenceReader.java")))));
        assertEquals("02da79360f49461c25ea7b1be34c55ace4074ce56bad3342e0f381370d63ae11",HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(Files.readAllBytes(Path.of("src/main/java/dz/sh/hidra/modules/party/application/contract/topology/TopologyProtectedPartyEligibilityContract.java")))));
        assertEquals("7c323e429f73497623172156f1161e12b00026980441f4fdc11755ff3f75a283",HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(Files.readAllBytes(Path.of("src/main/java/dz/sh/hidra/modules/topology/application/contract/station/StationProtectedFacilityOwnershipContract.java")))));
        assertEquals("d8108e6ebb3d968a4159714080d567f04ddf0ccae08f35cb3e253dcbc93524dc",HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(Files.readAllBytes(Path.of("src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/adapter/JdbcTopologyProtectedPartyEligibilityAdapter.java")))));
        assertEquals("1e60fdd86c70c7816050189d97c3b6cb39406c7c108f954c7d9b0e815b2b2467",HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(Files.readAllBytes(Path.of("src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JdbcStationProtectedFacilityOwnershipAdapter.java")))));
    }
}
