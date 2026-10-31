/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProtectedStationAdmissionBoundaryGuardrailTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-30
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
                ?Set.of("ProtectedInstalledUnitAdmissionCoordinator.java","ProtectedProductPublicationAdmissionCoordinator.java","JdbcProductRevisionPublicationRepositoryAdapter.java","ProtectedSuppliedFluidPropertyAdmissionCoordinator.java","JdbcSuppliedFluidPropertyRevisionRepositoryAdapter.java","ProtectedProductDraftAdmissionCoordinator.java","JdbcProductEngineeringDraftRepositoryAdapter.java","ProtectedStationAdmissionCoordinator.java","ProtectedEmployeeAppointmentAdmissionCoordinator.java","JdbcStationGraphProtectedPublicationAdapter.java","ProtectedEngineeringDraftAdmissionCoordinator.java","JdbcEngineeringNetworkDraftRepositoryAdapter.java","ProtectedEngineeringPublicationAdmissionCoordinator.java","JdbcEngineeringRevisionPublicationRepositoryAdapter.java","JdbcStationInstalledConfigurationRepositoryAdapter.java","ProtectedStationConfigurationAdmissionCoordinator.java")
                :method.equals("requireAdmission")
                    ?Set.of("ProtectedInstalledUnitAdmissionCoordinator.java","ProtectedProductPublicationAdmissionCoordinator.java","ProtectedSuppliedFluidPropertyAdmissionCoordinator.java","ProtectedProductDraftAdmissionCoordinator.java","ProtectedStationAdmissionCoordinator.java","ProtectedEngineeringDraftAdmissionCoordinator.java",
                        "JdbcEngineeringGraphDraftRepositoryAdapter.java","JdbcEngineeringGraphStationWitnessAdapter.java",
                        "JdbcSurveyRouteProfileRepositoryAdapter.java","ProtectedEngineeringPublicationAdmissionCoordinator.java","JdbcEngineeringRevisionPublicationRepositoryAdapter.java","ProtectedStationConfigurationAdmissionCoordinator.java")
                    :method.equals("beginAdmission")
                        ?Set.of("ProtectedInstalledUnitAdmissionCoordinator.java","ProtectedProductPublicationAdmissionCoordinator.java","ProtectedSuppliedFluidPropertyAdmissionCoordinator.java","ProtectedProductDraftAdmissionCoordinator.java","ProtectedStationAdmissionCoordinator.java","ProtectedEmployeeAppointmentAdmissionCoordinator.java","ProtectedEngineeringDraftAdmissionCoordinator.java","ProtectedEngineeringPublicationAdmissionCoordinator.java","ProtectedStationConfigurationAdmissionCoordinator.java")
                        :Set.of("ProtectedInstalledUnitAdmissionCoordinator.java","ProtectedProductPublicationAdmissionCoordinator.java","ProtectedSuppliedFluidPropertyAdmissionCoordinator.java","ProtectedProductDraftAdmissionCoordinator.java","ProtectedStationAdmissionCoordinator.java","ProtectedEmployeeAppointmentAdmissionCoordinator.java","ProtectedEngineeringDraftAdmissionCoordinator.java","ProtectedEngineeringPublicationAdmissionCoordinator.java","ProtectedStationConfigurationAdmissionCoordinator.java");
            if (Set.of("beginAdmission", "sealAdmission", "rejectAdmission").contains(method)) {
                var permitted = new HashSet<>(expected);
                permitted.add("ProtectedManufacturerCharacteristicAdmissionCoordinator.java");
                expected = Set.copyOf(permitted);
            }
            assertEquals(expected,found);
        }
    }
    @Test void identityAdmissionAndCollectorHaveOnlyReviewedCallers() throws Exception {
        var expected=Map.of("collectFenced",Set.of("JdbcIdentityAuthorityFence.java"),"acquireNowait",Set.of("ProtectedOperationIdentityParticipant.java"),"admit",Set.of("ProtectedTopologyStationConfigurationAuthorizationParticipant.java","ProtectedTopologyInstalledUnitAuthorizationParticipant.java","ProtectedProductPublicationAuthorizationParticipant.java","ProtectedFluidPropertyAuthorizationParticipant.java","ProtectedProductDraftAuthorizationParticipant.java","ProtectedTopologyAuthorizationParticipant.java","ProtectedEngineeringDraftAuthorizationParticipant.java","ProtectedOrganizationAppointmentAuthorizationParticipant.java","ProtectedSimulationManufacturerCharacteristicAuthorizationParticipant.java"));
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
        String configService="\"dz.sh.hidra.modules.topology.application.service.StationInstalledConfigurationApplicationService\"";
        String configCoordinator="\"dz.sh.hidra.modules.topology.application.service.ProtectedStationConfigurationAdmissionCoordinator\"";
        String configRepository="\"dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.JdbcStationInstalledConfigurationRepositoryAdapter\"";
        String installedService="\"dz.sh.hidra.modules.topology.application.service.InstalledUnitLifecycleApplicationService\"";
        String installedCoordinator="\"dz.sh.hidra.modules.topology.application.service.ProtectedInstalledUnitAdmissionCoordinator\"";
        String manufacturerService="\"dz.sh.hidra.modules.simulation.application.service.ManufacturerCharacteristicRevisionApplicationService\"";
        String manufacturerCoordinator="\"dz.sh.hidra.modules.simulation.application.service.ProtectedManufacturerCharacteristicAdmissionCoordinator\"";
        String approved="\"dz.sh.hidra.modules.topology.application.service.ProtectedStationAdmissionCoordinator\"";
        String publication="\"dz.sh.hidra.modules.topology.application.service.ProtectedStationGraphOperationService\"";
        String draft="\"dz.sh.hidra.modules.topology.application.service.EngineeringNetworkDraftApplicationService\"";
        String draftCoordinator="\"dz.sh.hidra.modules.topology.application.service.ProtectedEngineeringDraftAdmissionCoordinator\"";
        String appointment="\"dz.sh.hidra.modules.organization.application.service.ProtectedEmployeeAppointmentAdmissionCoordinator\"";
        String appointmentService="\"dz.sh.hidra.modules.organization.application.service.EmployeeAppointmentLifecycleApplicationService\"";
        for(String name:List.of("ProtectedTransactionExecutor","ProtectedOperationDataSource")) {
            String s=source("platform/persistence/"+name+".java");
            String productService="\"dz.sh.hidra.modules.custody.application.service.ProductEngineeringDraftApplicationService\"";
            String productCoordinator="\"dz.sh.hidra.modules.custody.application.service.ProtectedProductDraftAdmissionCoordinator\"";
            assertEquals(name.equals("ProtectedTransactionExecutor")?1:0,s.split(java.util.regex.Pattern.quote(productService),-1).length-1);
            assertEquals(name.equals("ProtectedTransactionExecutor")?1:0,s.split(java.util.regex.Pattern.quote(productCoordinator),-1).length-1);
            String newPublicationService="\"dz.sh.hidra.modules.custody.application.service.ProductRevisionPublicationApplicationService\"";
            String newPublicationCoordinator="\"dz.sh.hidra.modules.custody.application.service.ProtectedProductPublicationAdmissionCoordinator\"";
            assertEquals(name.equals("ProtectedTransactionExecutor")?1:0,s.split(java.util.regex.Pattern.quote(newPublicationService),-1).length-1);
            assertEquals(name.equals("ProtectedTransactionExecutor")?1:0,s.split(java.util.regex.Pattern.quote(newPublicationCoordinator),-1).length-1);
            String fluidService="\"dz.sh.hidra.modules.custody.application.service.SuppliedFluidPropertyRevisionApplicationService\"";
            String fluidCoordinator="\"dz.sh.hidra.modules.custody.application.service.ProtectedSuppliedFluidPropertyAdmissionCoordinator\"";
            assertEquals(name.equals("ProtectedTransactionExecutor")?1:0,s.split(java.util.regex.Pattern.quote(fluidService),-1).length-1);
            assertEquals(name.equals("ProtectedTransactionExecutor")?1:0,s.split(java.util.regex.Pattern.quote(fluidCoordinator),-1).length-1);
            // L pins the compiled issuer by name, without importing or linking a business class.
            assertEquals(name.equals("ProtectedTransactionExecutor")?3:0,s.split(java.util.regex.Pattern.quote(approved),-1).length-1);
            assertEquals(name.equals("ProtectedTransactionExecutor")?1:0,s.split(java.util.regex.Pattern.quote(publication),-1).length-1);
            assertEquals(name.equals("ProtectedTransactionExecutor")?1:0,s.split(java.util.regex.Pattern.quote(appointment),-1).length-1);
            assertEquals(name.equals("ProtectedTransactionExecutor")?1:0,s.split(java.util.regex.Pattern.quote(appointmentService),-1).length-1);
            for(var reviewed:List.of(configService,configCoordinator,configRepository))
                assertEquals(name.equals("ProtectedTransactionExecutor")?1:0,
                    s.split(java.util.regex.Pattern.quote(reviewed),-1).length-1);
            assertEquals(name.equals("ProtectedTransactionExecutor")?1:0,s.split(java.util.regex.Pattern.quote(installedService),-1).length-1);
            assertEquals(name.equals("ProtectedTransactionExecutor")?1:0,s.split(java.util.regex.Pattern.quote(installedCoordinator),-1).length-1);
            assertEquals(name.equals("ProtectedTransactionExecutor")?1:0,s.split(java.util.regex.Pattern.quote(manufacturerService),-1).length-1);
            assertEquals(name.equals("ProtectedTransactionExecutor")?1:0,s.split(java.util.regex.Pattern.quote(manufacturerCoordinator),-1).length-1);
            assertFalse(s.replace(configService,"\"reviewed-config-service\"")
                .replace(configCoordinator,"\"reviewed-config-coordinator\"")
                .replace(configRepository,"\"reviewed-config-repository\"")
                .replace(installedService,"\"reviewed-installed-service\"").replace(installedCoordinator,"\"reviewed-installed-coordinator\"").replace(newPublicationService,"\"reviewed-product-publication-service\"").replace(newPublicationCoordinator,"\"reviewed-product-publication-coordinator\"").replace(fluidService,"\"reviewed-fluid-service\"").replace(fluidCoordinator,"\"reviewed-fluid-coordinator\"").replace(productService,"\"reviewed-product-service\"").replace(productCoordinator,"\"reviewed-product-coordinator\"").replace(appointmentService,"\"reviewed-appointment-service\"").replace(appointment,"\"reviewed-appointment-caller\"").replace(approved,"\"reviewed-caller\"").replace(publication,"\"reviewed-publication-caller\"").replace(draft,"\"reviewed-draft\"").replace(draftCoordinator,"\"reviewed-draft-coordinator\"").replace(manufacturerService,"\"reviewed-manufacturer-service\"").replace(manufacturerCoordinator,"\"reviewed-manufacturer-coordinator\"").replace("\"dz.sh.hidra.modules.topology.application.service.EngineeringRevisionPublicationApplicationService\"","\"reviewed-publication-service\"").replace("\"dz.sh.hidra.modules.topology.application.service.ProtectedEngineeringPublicationAdmissionCoordinator\"","\"reviewed-publication-coordinator\"").contains("dz.sh.hidra.modules."));
        }
    }
    // HAPI-599B admits the reviewed per-resource replacement binding; pin actual reader bytes and unchanged J contracts.
    @Test void existingReaderAndJContractsRemainUnchanged() throws Exception {
        // B2 reviewed typed configuration-family extension; every other source pin remains unchanged.
        assertEquals("3da973eda0d22ee60337c390cd06be361db2c84fbf1bfa2ae49f7a9c5bacc5c8",HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(Files.readAllBytes(Path.of("src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JdbcProtectedAuthorizationEvidenceReader.java")))));
        assertEquals("ae4b5c36fbe2531ab5fc82988a05206ad850dfb5377792f7201d2394eb5c41cc",HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(Files.readAllBytes(Path.of("src/main/java/dz/sh/hidra/modules/party/application/contract/topology/TopologyProtectedPartyEligibilityContract.java")))));
        assertEquals("11a7e8b269fd96e4132437831b6efd4184c1cd17765438d52bb022decaf3e886",HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(Files.readAllBytes(Path.of("src/main/java/dz/sh/hidra/modules/topology/application/contract/station/StationProtectedFacilityOwnershipContract.java")))));
        assertEquals("b4ed2114a0af4d715d3da994ed096b2d0b184f5106ddf68cd16dd8cedbdc666c",HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(Files.readAllBytes(Path.of("src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/adapter/JdbcTopologyProtectedPartyEligibilityAdapter.java")))));
        assertEquals("d886bb5797eb64c144733609fa648e051ae9015320e41d1a674a04258a44ff83",HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(Files.readAllBytes(Path.of("src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JdbcStationProtectedFacilityOwnershipAdapter.java")))));
    }

    @Test void publicationAdmissionKeepsTheSingleClockAndPrivateSourceBoundaries() throws Exception {
        String coordinator=source("modules/topology/application/service/ProtectedEngineeringPublicationAdmissionCoordinator.java");
        assertFalse(coordinator.contains(".infrastructure."));assertFalse(coordinator.contains("java.sql"));
        assertTrue(coordinator.contains("int topologyCount=12"));assertTrue(coordinator.contains("int total=38"));
        assertTrue(coordinator.contains("repository.observe(a.repository,a.result)"));
        assertTrue(coordinator.contains("identity.requireCurrent(a.identity)"));
    }
    @Test void publicationOwnerTicketsRemainOpaqueAndPolicyUsesRetainedOwnerFences() throws Exception {
        for(var t:List.of(dz.sh.hidra.modules.identity.infrastructure.security.ProtectedOperationIdentityParticipant.ProductPublicationClaimPair.class)) {
            assertEquals(0,t.getConstructors().length);for(var c:t.getDeclaredConstructors())assertTrue(Modifier.isPrivate(c.getModifiers()));
        }
        String s=source("modules/workflow/infrastructure/persistence/adapter/JdbcCustodyProtectedProductPublicationApprovalAdapter.java");
        assertTrue(s.contains("IN SHARE MODE NOWAIT"));assertTrue(s.contains("TRIGGERS.equals(actual)"));
        assertFalse(s.contains(".modules.identity.infrastructure"));assertFalse(s.contains(".modules.custody.domain"));
        assertFalse(s.contains("Instant.now("));assertFalse(s.contains("INSERT INTO"));
    }
    @Test void productPublicationUsesActionSpecificExactFenceUnion() throws Exception {
        String s=source("modules/custody/application/service/ProtectedProductPublicationAdmissionCoordinator.java");
        assertTrue(s.contains("fences.size()!=37"));assertTrue(s.contains("a.fences.size()!=50"));assertTrue(s.contains("identity.reviewer(a.identity,e.actorId())"));
        assertFalse(s.contains(".infrastructure."));assertFalse(s.contains("JdbcTemplate"));assertFalse(s.contains("Instant.now("));
        String repository=source("modules/custody/infrastructure/persistence/adapter/JdbcProductRevisionPublicationRepositoryAdapter.java");
        assertTrue(repository.contains("imageSql(fences)"));assertTrue(repository.contains("NEW_FUNCTIONS"));assertTrue(repository.contains("sampled_clock AS MATERIALIZED"));
    }

    @Test void productPublicationTransportUsesOnlyItsProtectedUseCaseAndScalarExport()throws Exception{
        String controller=source("modules/custody/api/rest/controller/ProductRevisionPublicationController.java");
        assertTrue(controller.contains("ProductRevisionPublicationUseCase"));assertFalse(controller.contains(".infrastructure."));assertFalse(controller.contains("JdbcTemplate"));
        String adapter=source("modules/custody/infrastructure/integration/ProductPublicationSourceQueryAdapter.java");
        assertTrue(adapter.contains("Kind.READ_EXACT"));assertTrue(adapter.contains("Kind.ELIGIBLE_CURRENT"));assertTrue(adapter.contains("Kind.ELIGIBLE_EFFECTIVE"));assertFalse(adapter.contains("JdbcTemplate"));assertFalse(adapter.contains("actorId,"));
    }
}

