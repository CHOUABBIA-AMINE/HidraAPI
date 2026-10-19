/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringNetworkDraftBoundaryGuardrailTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-19
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology
 *
 * @Description : Implements the bounded company-scoped engineering draft contract.
 *
 */
package dz.sh.hidra.modules.topology;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.nio.file.*;
import java.util.*;
import java.security.*;
import java.lang.reflect.Modifier;
import dz.sh.hidra.modules.identity.infrastructure.security.ProtectedOperationIdentityParticipant;
class EngineeringNetworkDraftBoundaryGuardrailTest {
    @Test void networkPairCannotBeConstructedOrRetaggedByCallers(){
        for(var c:ProtectedOperationIdentityParticipant.DraftClaimPair.class.getDeclaredConstructors())assertTrue(Modifier.isPrivate(c.getModifiers()));
        assertEquals(0,ProtectedOperationIdentityParticipant.DraftClaimPair.class.getConstructors().length);
        for(var f:ProtectedOperationIdentityParticipant.QualifiedClaimRead.class.getDeclaredFields()){
            assertTrue(Modifier.isPrivate(f.getModifiers()));assertTrue(Modifier.isFinal(f.getModifiers()));
        }

    }
    @Test void finiteDraftSqlHashesAndPlatformIssuerNamesStayBound() throws Exception {
        String base="src/main/java/dz/sh/hidra/";
        String a=Files.readString(Path.of(base+"modules/topology/infrastructure/persistence/adapter/JdbcEngineeringNetworkDraftRepositoryAdapter.java"));
        String c=Files.readString(Path.of(base+"modules/topology/application/service/ProtectedEngineeringDraftAdmissionCoordinator.java"));
        var m=java.util.regex.Pattern.compile("private static final String (?:CONTEXT_INS|DRAFT_INS|VERSION_INS)=\"(.*?)\";").matcher(a);int n=0;
        while(m.find()){n++;String hash=HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(m.group(1).getBytes(java.nio.charset.StandardCharsets.UTF_8)));assertTrue(c.contains(hash));}
        assertEquals(3,n);String platform=Files.readString(Path.of(base+"platform/persistence/ProtectedTransactionExecutor.java"));
        assertTrue(platform.contains("caller.equals(permit.owner)"));assertFalse(platform.contains("import dz.sh.hidra.modules."));
    }
    @Test void domainAndContractsRemainFreeOfForeignPersistenceAndFrameworks() throws Exception {
        String s=Files.readString(Path.of("src/main/java/dz/sh/hidra/modules/topology/domain/model/EngineeringNetworkDraft.java"));
        for(String forbidden:List.of("org.springframework","jakarta.persistence","com.fasterxml",".infrastructure."))assertFalse(s.contains(forbidden));
        String c=Files.readString(Path.of("src/main/java/dz/sh/hidra/modules/topology/application/service/ProtectedEngineeringDraftAdmissionCoordinator.java"));
        assertFalse(c.contains(".infrastructure."));assertFalse(c.contains("JdbcTemplate"));
    }
}
