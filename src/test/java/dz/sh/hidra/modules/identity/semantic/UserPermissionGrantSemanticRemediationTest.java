/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : UserPermissionGrantSemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-06
 *
 * @Type        : Class
 * @Layer       : Identity Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.semantic
 *
 * @Description : Enforces the admitted Batch 5 UserPermissionGrantSemanticRemediationTest contract.
 *
 */
package dz.sh.hidra.modules.identity.semantic;

import dz.sh.hidra.modules.identity.domain.model.*;
import dz.sh.hidra.modules.identity.domain.value.*;
import dz.sh.hidra.modules.identity.domain.exception.InvalidIdentityValueException;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class UserPermissionGrantSemanticRemediationTest {
    private final Instant from = Instant.parse("2026-10-06T12:00:00Z");
    private UserPermissionGrant grant(String reason, Instant to, GrantStatus status, boolean emergency) {
        return new UserPermissionGrant("g","u","p",GrantEffect.GRANT,null,reason,null,emergency,
                from,to,status,from,null);
    }
    @Test void boundsEveryDirectGrantIncludingEmergencyAccess() {
        for (boolean emergency : new boolean[]{false,true}) {
            assertThatThrownBy(() -> grant("reason",null,GrantStatus.ACTIVE,emergency)).isInstanceOf(InvalidIdentityValueException.class);
        }
        assertThatThrownBy(() -> grant(" ",from,GrantStatus.ACTIVE,false)).isInstanceOf(InvalidIdentityValueException.class);
        assertThatThrownBy(() -> grant("reason",from.minusSeconds(1),GrantStatus.ACTIVE,false)).isInstanceOf(InvalidIdentityValueException.class);
        assertThatThrownBy(() -> grant("reason",from,GrantStatus.SUSPENDED,false)).isInstanceOf(InvalidIdentityValueException.class);
        assertThat(grant(" reason ",from,GrantStatus.REVOKED,true).grantReason()).isEqualTo("reason");
    }
    @Test void preservesDifferentRoleGrantContract() {
        var role = new UserRoleGrant("r","u","role",null,null,null,from,null,GrantStatus.SUSPENDED,from,null,null);
        assertThat(role.validTo()).isNull();
        assertThat(role.grantReason()).isNull();
        assertThat(role.status()).isEqualTo(GrantStatus.SUSPENDED);
    }
}
