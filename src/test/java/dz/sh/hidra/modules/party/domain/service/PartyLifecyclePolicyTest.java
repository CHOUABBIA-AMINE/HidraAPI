/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyLifecyclePolicyTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-04
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.domain.service
 *
 * @Description : Verifies the bounded Party foundation contract with synthetic evidence.
 *
 */
package dz.sh.hidra.modules.party.domain.service;

import org.junit.jupiter.api.Test;
import dz.sh.hidra.modules.party.domain.value.PartyStatus;
import java.time.Instant;
import static org.junit.jupiter.api.Assertions.*;
class PartyLifecyclePolicyTest {
    @Test void admitsOnlyDraftActivationAndActiveSuspension() {
        for(var s:PartyStatus.values()) {
            if(s==PartyStatus.DRAFT)PartyLifecyclePolicy.transition(s,true);
            else assertThrows(IllegalStateException.class,()->PartyLifecyclePolicy.transition(s,true));
            if(s==PartyStatus.ACTIVE)PartyLifecyclePolicy.transition(s,false);
            else assertThrows(IllegalStateException.class,()->PartyLifecyclePolicy.transition(s,false));
        }
    }
    @Test void exactVersionRejectsMissingAndMicrosecondStaleValues() {
        Instant version=Instant.parse("2026-11-04T09:00:00.123456Z");
        PartyLifecyclePolicy.expected(version,version);
        assertThrows(IllegalStateException.class,()->PartyLifecyclePolicy.expected(null,version));
        assertThrows(IllegalStateException.class,()->PartyLifecyclePolicy.expected(version.minusNanos(1000),version));
    }
    @Test void metadataPreservesRequiredAndBoundedNames() {
        PartyLifecyclePolicy.metadata("Synthetic legal name",null,null);
        assertThrows(IllegalArgumentException.class,()->PartyLifecyclePolicy.metadata(" ",null,null));
        assertThrows(IllegalArgumentException.class,()->PartyLifecyclePolicy.metadata("valid",null,"x".repeat(161)));
    }
    @Test void reasonsAreRequiredAndBounded() {
        PartyLifecyclePolicy.reason("Authorized synthetic change");
        assertThrows(IllegalArgumentException.class,()->PartyLifecyclePolicy.reason(" "));
        assertThrows(IllegalArgumentException.class,()->PartyLifecyclePolicy.reason("x".repeat(2001)));
    }
    @Test void registrationBoundsMatchExistingOwnerColumns() {
        PartyLifecyclePolicy.registration("CODE","type","DZA",null,null);
        assertThrows(IllegalArgumentException.class,()->PartyLifecyclePolicy.registration("CODE","type","TOO-LONG",null,null));
        assertThrows(IllegalArgumentException.class,()->PartyLifecyclePolicy.registration(" ","type","DZA",null,null));
    }
}
