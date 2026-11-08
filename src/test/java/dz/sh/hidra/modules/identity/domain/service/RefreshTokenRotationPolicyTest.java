package dz.sh.hidra.modules.identity.domain.service;

import static org.assertj.core.api.Assertions.*;
import dz.sh.hidra.modules.identity.domain.model.RefreshTokenFamily;
import java.time.Instant;
import org.junit.jupiter.api.Test;

class RefreshTokenRotationPolicyTest {
    private static final Instant T=Instant.parse("2026-11-08T10:00:00Z");
    @Test void defaultsToSevenDayAbsoluteLifetimeAndCapsThirtyDays() {
        assertThat(RefreshTokenRotationPolicy.DEFAULT_SECONDS).isEqualTo(604800L);
        assertThat(RefreshTokenRotationPolicy.deadline(T,604800L)).isEqualTo(T.plusSeconds(604800));
        assertThat(RefreshTokenRotationPolicy.deadline(T,2592000L)).isEqualTo(T.plusSeconds(2592000));
        for(long n:new long[]{-1,0,1,299,2592001,Long.MAX_VALUE})
            assertThatThrownBy(()->RefreshTokenRotationPolicy.deadline(T,n))
                    .isInstanceOf(IllegalArgumentException.class);
    }
    @Test void onlyLocalAuthenticationMayRefresh() {
        RefreshTokenRotationPolicy.requireLocal("LOCAL");
        for(String type:new String[]{"LDAP","ACTIVE_DIRECTORY","OIDC","KEYCLOAK","AZURE_AD","OKTA","OAUTH2","SAML2",null})
            assertThatThrownBy(()->RefreshTokenRotationPolicy.requireLocal(type))
                    .isInstanceOf(IllegalArgumentException.class);
    }
    @Test void familyIsImmutableAndNeverContainsRawRefreshToken() {
        var bytes=new byte[32];
        var family=new RefreshTokenFamily("f","u","p",1,"c",1,bytes,T,T.plusSeconds(300));
        bytes[0]=99;
        assertThat(family.rightsDigest()[0]).isEqualTo((byte)0);
        family.rightsDigest()[0]=99;
        assertThat(family.rightsDigest()[0]).isEqualTo((byte)0);
        assertThat(family.toString()).doesNotContain("token","credential");
        assertThatThrownBy(()->new RefreshTokenFamily("f","u","p",0,"c",1,
                new byte[32],T,T.plusSeconds(300))).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(()->new RefreshTokenFamily("f","u","p",1,"c",1,
                new byte[31],T,T.plusSeconds(300))).isInstanceOf(IllegalArgumentException.class);
    }
}
