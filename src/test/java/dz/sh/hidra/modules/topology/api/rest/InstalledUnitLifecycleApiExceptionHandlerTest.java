/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InstalledUnitLifecycleApiExceptionHandlerTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-29
 *
 * @Type        : Class
 * @Layer       : Topology Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest
 *
 * @Description : Pins non-disclosing HTTP mappings for every typed installed-unit failure.
 *
 */
package dz.sh.hidra.modules.topology.api.rest;

import dz.sh.hidra.modules.topology.application.model.InstalledUnitLifecycleFailure;
import dz.sh.hidra.modules.topology.application.model.InstalledUnitLifecycleFailure.Kind;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InstalledUnitLifecycleApiExceptionHandlerTest {
    private final InstalledUnitLifecycleApiExceptionHandler handler=new InstalledUnitLifecycleApiExceptionHandler();
    @Test void mapsOnlyOwnerTypedReasonsToFixedStatuses() {
        var statuses=Map.of(Kind.INVALID,400,Kind.UNAUTHENTICATED,401,Kind.DENIED,403,
                Kind.NOT_FOUND,404,Kind.CONFLICT,409,Kind.UNAVAILABLE,503,Kind.INDETERMINATE,503);
        statuses.forEach((kind,code)->{
            var entity=handler.failure(new InstalledUnitLifecycleFailure(kind,new IllegalStateException("private sql native token")));
            assertEquals(code,entity.getStatusCode().value());assertEquals(kind.name(),entity.getBody().getProperties().get("code"));
            assertEquals(java.net.URI.create("urn:hidra:installed-unit"),entity.getBody().getInstance());
            assertFalse(entity.getBody().getDetail().contains("private"));
        });
    }
    @Test void parserAndPathErrorsCannotExposePrivateRequestContent() {
        var response=handler.invalid(new IllegalArgumentException("sensitive arbitrary input"));
        assertEquals(400,response.getStatusCode().value());
        assertEquals("INVALID",response.getBody().getProperties().get("code"));
        assertFalse(response.getBody().getDetail().contains("sensitive"));
    }
    @Test void indeterminateCompletionMustNotBeMappedToConflictOrSuccess() {
        var response=handler.failure(new InstalledUnitLifecycleFailure(Kind.INDETERMINATE,null));
        assertEquals(503,response.getStatusCode().value());
    }
}
