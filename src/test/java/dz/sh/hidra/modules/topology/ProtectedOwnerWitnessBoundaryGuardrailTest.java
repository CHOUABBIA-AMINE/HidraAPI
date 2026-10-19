/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProtectedOwnerWitnessBoundaryGuardrailTest
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

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.HexFormat;
import java.util.List;
import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProtectedOwnerWitnessBoundaryGuardrailTest {
    private record Owner(String module,String contractPackage,String contract,String adapter,String migration) {}
    private static final List<Owner> OWNERS=List.of(
            new Owner("party","topology","TopologyProtectedPartyEligibilityContract","JdbcTopologyProtectedPartyEligibilityAdapter",
                    "V20261018_001__p25_party_eligibility_generations.sql"),
            new Owner("topology","station","StationProtectedFacilityOwnershipContract","JdbcStationProtectedFacilityOwnershipAdapter",
                    "V20261018_002__p25_topology_facility_owner_generations.sql"));
    private Path module(Owner o) {return Path.of("src/main/java/dz/sh/hidra/modules",o.module());}

    @Test void exportedContractsContainOnlyJdkScalarsAndOpaqueAdmission() throws Exception {
        for(var o:OWNERS) {
            String source=Files.readString(module(o).resolve("application/contract/"+o.contractPackage()+"/"+o.contract()+".java"));
            var imports=Pattern.compile("^import ([^;]+);",Pattern.MULTILINE).matcher(source);
            while(imports.find())assertTrue(List.of("java.util.List","dz.sh.hidra.platform.persistence.ProtectedRelationFence").contains(imports.group(1)));
            assertFalse(source.contains("org.springframework"));
            assertTrue(source.contains("finalFences(Admission admission)"));
            String technical=Files.readString(Path.of("src/main/java/dz/sh/hidra/platform/persistence/ProtectedRelationFence.java"));
            assertFalse(technical.contains("org.springframework"));assertFalse(technical.contains("jakarta.persistence"));
            assertTrue(source.contains("Admission acquire(String exactId)"));assertTrue(source.contains("Contribution read(Admission admission)"));
            Class<?> type=Class.forName("dz.sh.hidra.modules."+o.module()+".application.contract."+o.contractPackage()+"."+o.contract());
            Class<?> marker=Class.forName(type.getName()+"$Admission");
            assertTrue(marker.isInterface());assertEquals(0,marker.getDeclaredMethods().length);
            Class<?> scalar=Class.forName(type.getName()+"$Contribution");
            assertTrue(scalar.isRecord());
            for(var c:scalar.getRecordComponents()) assertTrue(c.getType().isPrimitive() || c.getType()==String.class);
        }
    }

    @Test void eachAdapterUsesOnlyItsOwnerAndParticipatesWithoutNewTransactionOrClock() throws Exception {
        for(var o:OWNERS) {
            String source=Files.readString(module(o).resolve("infrastructure/persistence/adapter/"+o.adapter()+".java"));
            var imports=Pattern.compile("^import dz\\.sh\\.hidra\\.modules\\.([^;]+);",Pattern.MULTILINE).matcher(source);
            while(imports.find()) assertTrue(imports.group(1).startsWith(o.module()+"."));
            for(String forbidden:List.of("REQUIRES_NEW","Instant.now(","EntityManager","@RestController","getConnection() {","unwrap("))
                assertFalse(source.contains(forbidden),forbidden);
            assertTrue(source.contains("Propagation.MANDATORY"));assertTrue(source.contains("MODE NOWAIT"));
            assertTrue(source.contains("private final class Ticket"));assertTrue(source.contains("afterCompletion"));
        }
    }

    @Test void catalogFunctionFingerprintsMatchBothActualMigrationBodies() throws Exception {
        for(var o:OWNERS) {
            String sql=Files.readString(Path.of("src/main/resources/db/migration",o.migration()));
            String adapter=Files.readString(module(o).resolve("infrastructure/persistence/adapter/"+o.adapter()+".java"));
            var bodies=Pattern.compile("AS \\$owner\\$(.*?)\\$owner\\$;",Pattern.DOTALL).matcher(sql);
            int count=0;
            while(bodies.find()) {
                String digest=HexFormat.of().formatHex(MessageDigest.getInstance("MD5").digest(bodies.group(1).getBytes(StandardCharsets.UTF_8)));
                assertTrue(adapter.contains("md5(prosrc)='"+digest+"'"));count++;
            }
            assertEquals(2,count);assertTrue(sql.contains("SECURITY DEFINER"));
            assertTrue(sql.contains("REVOKE ALL ON FUNCTION"));assertTrue(sql.contains("BEFORE TRUNCATE"));
        }
    }
}
