/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProtectedStationAdmissionBoundaryGuardrailTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-20
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
                ?Set.of("ProtectedStationAdmissionCoordinator.java","JdbcStationGraphProtectedPublicationAdapter.java","ProtectedEngineeringDraftAdmissionCoordinator.java","JdbcEngineeringNetworkDraftRepositoryAdapter.java")
                :method.equals("requireAdmission")
                    ?Set.of("ProtectedStationAdmissionCoordinator.java","ProtectedEngineeringDraftAdmissionCoordinator.java",
                        "JdbcEngineeringGraphDraftRepositoryAdapter.java","JdbcEngineeringGraphStationWitnessAdapter.java",
                        "JdbcSurveyRouteProfileRepositoryAdapter.java")
                    :Set.of("ProtectedStationAdmissionCoordinator.java","ProtectedEngineeringDraftAdmissionCoordinator.java");
            assertEquals(expected,found);
        }
    }
    @Test void identityAdmissionAndCollectorHaveOnlyReviewedCallers() throws Exception {
        var expected=Map.of("collectFenced",Set.of("JdbcIdentityAuthorityFence.java"),"acquireNowait",Set.of("ProtectedOperationIdentityParticipant.java"),"admit",Set.of("ProtectedTopologyAuthorizationParticipant.java","ProtectedEngineeringDraftAuthorizationParticipant.java"));
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
        String publication="\"dz.sh.hidra.modules.topology.application.service.ProtectedStationGraphOperationService\"";
        String draft="\"dz.sh.hidra.modules.topology.application.service.EngineeringNetworkDraftApplicationService\"";
        String draftCoordinator="\"dz.sh.hidra.modules.topology.application.service.ProtectedEngineeringDraftAdmissionCoordinator\"";
        for(String name:List.of("ProtectedTransactionExecutor","ProtectedOperationDataSource")) {
            String s=source("platform/persistence/"+name+".java");
            // L pins the compiled issuer by name, without importing or linking a business class.
            assertEquals(name.equals("ProtectedTransactionExecutor")?3:0,s.split(java.util.regex.Pattern.quote(approved),-1).length-1);
            assertEquals(name.equals("ProtectedTransactionExecutor")?1:0,s.split(java.util.regex.Pattern.quote(publication),-1).length-1);
            assertFalse(s.replace(approved,"\"reviewed-caller\"").replace(publication,"\"reviewed-publication-caller\"").replace(draft,"\"reviewed-draft\"").replace(draftCoordinator,"\"reviewed-draft-coordinator\"").contains("dz.sh.hidra.modules."));
        }
    }
    // Reader stays byte-pinned; J fingerprints admit only the registered technical fence exports.
    @Test void existingReaderAndJContractsRemainUnchanged() throws Exception {
        assertEquals("46aec66a9ae0f0fdfa374319de1a5dc803511c363f784c65b39d801de0d3032f",HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(Files.readAllBytes(Path.of("src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JdbcProtectedAuthorizationEvidenceReader.java")))));
        assertEquals("ae4b5c36fbe2531ab5fc82988a05206ad850dfb5377792f7201d2394eb5c41cc",HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(Files.readAllBytes(Path.of("src/main/java/dz/sh/hidra/modules/party/application/contract/topology/TopologyProtectedPartyEligibilityContract.java")))));
        assertEquals("11a7e8b269fd96e4132437831b6efd4184c1cd17765438d52bb022decaf3e886",HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(Files.readAllBytes(Path.of("src/main/java/dz/sh/hidra/modules/topology/application/contract/station/StationProtectedFacilityOwnershipContract.java")))));
        assertEquals("b4ed2114a0af4d715d3da994ed096b2d0b184f5106ddf68cd16dd8cedbdc666c",HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(Files.readAllBytes(Path.of("src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/adapter/JdbcTopologyProtectedPartyEligibilityAdapter.java")))));
        assertEquals("d886bb5797eb64c144733609fa648e051ae9015320e41d1a674a04258a44ff83",HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(Files.readAllBytes(Path.of("src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JdbcStationProtectedFacilityOwnershipAdapter.java")))));
    }
}
