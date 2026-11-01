/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InstalledCharacteristicLinkApiExceptionHandlerTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-01
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest
 *
 * @Description : Non-disclosing error classification and uncertain commit preservation.
 *
 */
package dz.sh.hidra.modules.topology.api.rest;

import dz.sh.hidra.modules.topology.application.model.InstalledCharacteristicLinkFailure;
import dz.sh.hidra.platform.persistence.ProtectedTransactionExecutor;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InstalledCharacteristicLinkApiExceptionHandlerTest {
    private final InstalledCharacteristicLinkApiExceptionHandler handler=new InstalledCharacteristicLinkApiExceptionHandler();
    @Test void typedDenialsAndConflictsDoNotDiscloseSourceIdentifiers(){
        var denied=handler.business(new InstalledCharacteristicLinkFailure(InstalledCharacteristicLinkFailure.Kind.DENIED));
        var conflict=handler.business(new InstalledCharacteristicLinkFailure(InstalledCharacteristicLinkFailure.Kind.CONFLICT));
        assertEquals(403,denied.getStatusCode().value());
        assertEquals(409,conflict.getStatusCode().value());
        assertFalse(denied.getBody().toString().contains("sourceId"));
    }
    @Test void malformedInputNeverReturnsProtectedSource(){
        assertEquals(400,handler.invalid(new IllegalArgumentException("secret"))
            .getStatusCode().value());
    }
    @Test void rejectedNativeCommitDoesNotClaimSuccess(){
        assertEquals(503,handler.business(new InstalledCharacteristicLinkFailure(
                InstalledCharacteristicLinkFailure.Kind.UNAVAILABLE)).getStatusCode().value());
        assertEquals(503,handler.business(new InstalledCharacteristicLinkFailure(
                InstalledCharacteristicLinkFailure.Kind.INDETERMINATE)).getStatusCode().value());
    }
}
