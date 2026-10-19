/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProtectedStationGraphBoundaryGuardrailTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-18
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence
 *
 * @Description : Pins private publication boundaries and reviewed provisional statement entry points.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProtectedStationGraphBoundaryGuardrailTest {
    static String source(String path) throws IOException {
        return Files.readString(Path.of("src/main/java/dz/sh/hidra/"+path));
    }
    @Test void topologyOwnsHeadAndNoForeignBusinessAdapterImports() throws IOException{
        String port=source("modules/topology/application/port/out/StationGraphProtectedPublicationPort.java");
        assertFalse(port.contains("modules.identity.infrastructure"));
        assertFalse(port.contains("modules.party.infrastructure"));
        assertFalse(port.contains("modules.topology.application.service"));
        String service=source("modules/topology/application/service/ProtectedStationGraphOperationService.java");
        String adapter=source("modules/topology/infrastructure/persistence/adapter/JdbcStationGraphProtectedPublicationAdapter.java");
        assertFalse(service.contains("jakarta.persistence"));
        assertTrue(service.contains("publication.flush()"));
        assertTrue(service.contains("executor.executePublication("));
        assertTrue(adapter.contains("ownerFences.size()!=28"));
        assertTrue(adapter.contains("l.mode=required.mode"));
        assertTrue(adapter.contains("session_user=?"));
        assertTrue(adapter.contains("void flush(){physical();em.flush();}"));
        assertTrue(adapter.contains("if(!compiledCaller())"));
        assertTrue(adapter.contains("t.issuer!=this"));
        assertTrue(adapter.contains("jdbc.queryForObject(CATALOG,Boolean.class)"));
        assertTrue(adapter.contains("count(*)=7"));
        assertTrue(adapter.contains("count(*)=21"));
        assertTrue(adapter.contains("RETAINED"));
        assertFalse(service.contains("final class ProtectedStationGraphOperationService"));
        assertFalse(adapter.contains("final class JdbcStationGraphProtectedPublicationAdapter"));
    }
    @Test void onlyReviewedCoordinatorCanOpenBoundedProvisionalPhase() throws IOException{
        String coordinator=source("modules/topology/application/service/ProtectedStationAdmissionCoordinator.java");
        String service=source("modules/topology/application/service/ProtectedStationGraphOperationService.java");
        assertTrue(coordinator.contains("executor.beginProvisional(admission.provisional,exactStatements)"));
        assertTrue(service.contains("coordinator.beginProvisional(admission,publication.mutationSql())"));
        assertFalse(service.contains("executor.beginProvisional("));
    }
    @Test void allExactJdbcStatementsAndEventsAreParameterBound() throws IOException{
        String adapter=source("modules/topology/infrastructure/persistence/adapter/JdbcStationGraphProtectedPublicationAdapter.java");
        assertTrue(adapter.contains("VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"));
        assertTrue(adapter.contains("head_epoch=? AND revision_id=?"));
        assertFalse(adapter.contains("Statement.execute("));
    }
    @Test void noPublicCallerSuppliesAuthorizationScalarsOrClock() throws IOException{
        String svc=source("modules/topology/application/service/ProtectedStationGraphOperationService.java");
        assertTrue(svc.contains("coordinator.collect(admission)"));
        assertTrue(svc.contains("var prepared=coordinator.prepare()"));
        assertFalse(svc.contains("Instant.now("));
        assertFalse(svc.contains("Clock.system"));
    }
    @Test void noPublicRestSurfaceOrChangedLegacyRevisionContract() throws IOException{
        String svc=source("modules/topology/application/service/ProtectedStationGraphOperationService.java");
        String legacy=source("modules/topology/application/port/out/StationInternalGraphRevisionRepositoryPort.java");
        assertFalse(svc.contains("@RestController"));
        assertTrue(legacy.contains("does not enact head selection"));
        assertFalse(legacy.contains("compareAndSet("));
    }
}
