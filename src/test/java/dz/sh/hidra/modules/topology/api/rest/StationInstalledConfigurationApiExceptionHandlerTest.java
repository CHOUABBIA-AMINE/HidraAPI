/**
 * HidraAPI — exact, non-disclosing protected station configuration HTTP outcomes.
 */
package dz.sh.hidra.modules.topology.api.rest;

import dz.sh.hidra.modules.topology.application.model.StationInstalledConfigurationFailure;
import dz.sh.hidra.modules.topology.application.model.StationInstalledConfigurationFailure.Kind;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StationInstalledConfigurationApiExceptionHandlerTest {
    private final StationInstalledConfigurationApiExceptionHandler advice=
        new StationInstalledConfigurationApiExceptionHandler();
    @Test void fixedOwnerKindsNeverExposeNativeStackOrProtectedDetails(){
        Map<Kind,Integer> outcomes=Map.of(Kind.INVALID,400,Kind.UNAUTHENTICATED,401,
            Kind.DENIED,403,Kind.NOT_FOUND,404,Kind.CONFLICT,409,Kind.UNAVAILABLE,503,
            Kind.INDETERMINATE,503);
        outcomes.forEach((kind,status)->{
            var response=advice.failure(new StationInstalledConfigurationFailure(
                kind,new IllegalStateException("private SQL row and secret bearer")));
            assertEquals(status,response.getStatusCode().value());
            var body=response.getBody();
            assertNotNull(body);
            assertEquals(kind.name(),body.getProperties().get("code"));
            assertEquals(java.net.URI.create("urn:hidra:station-configuration"),body.getInstance());
            assertFalse(body.getDetail().contains("private"));
            assertFalse(body.getDetail().contains("secret"));
            assertFalse(body.getDetail().contains("SQL"));
        });
    }
    @Test void malformedRequestCannotDiscloseAttemptedPayload(){
        var p=advice.invalid(new IllegalArgumentException("private bearer token"));
        assertEquals(400,p.getStatusCode().value());
        assertEquals("INVALID",p.getBody().getProperties().get("code"));
        assertFalse(p.getBody().getDetail().contains("bearer"));
    }
    @Test void uncertainCommitIsNeverReportedAsConflictOrSuccessfulWrite(){
        assertEquals(503,advice.failure(new StationInstalledConfigurationFailure(
            Kind.INDETERMINATE,null)).getStatusCode().value());
    }
}
