/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationAppointmentCommitProofPropertiesTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-22
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.configuration
 *
 * @Description : Authenticates transaction-bound appointment commit evidence within Identity.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.configuration;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Base64;

class OrganizationAppointmentCommitProofPropertiesTest {
    private OrganizationAppointmentCommitProofProperties configured() {
        var p = new OrganizationAppointmentCommitProofProperties();
        p.setEnabled(true); p.setKeyId("test-key"); p.setKeyRevision(1);
        p.setSigningKeyBase64(Base64.getEncoder().encodeToString(new byte[32]));
        return p;
    }
    @Test void disabledLoadsNoKeyAndCannotIssue() {
        var p = new OrganizationAppointmentCommitProofProperties();
        p.setSigningKeyBase64("invalid-secret-input"); p.initialize();
        assertFalse(p.isEnabled()); assertThrows(IllegalStateException.class, p::issuerCodec);
    }
    @Test void enabledRequiresExplicitExactThirtyTwoByteKey() {
        for (String value : new String[]{"", "bad-value", Base64.getEncoder().encodeToString(new byte[31]),
                Base64.getEncoder().encodeToString(new byte[33]),
                Base64.getEncoder().withoutPadding().encodeToString(new byte[32])}) {
            var p = configured(); p.setSigningKeyBase64(value);
            var e = assertThrows(IllegalStateException.class, p::initialize);
            assertNull(e.getCause()); assertFalse(e.getMessage().contains(value.isEmpty() ? "bad-value" : value));
        }
        var p = configured(); p.setSigningKeyBase64(null);
        assertThrows(IllegalStateException.class, p::initialize);
    }
    @Test void requiresExactIdentityAndPositiveRevision() {
        for (String id : new String[]{"", " key", "key ", "../key", "x".repeat(65)}) {
            var p = configured(); p.setKeyId(id); assertThrows(IllegalStateException.class,p::initialize);
        }
        var p = configured(); p.setKeyRevision(0); assertThrows(IllegalStateException.class,p::initialize);
    }
    @Test void independentPurposeCodecIsRedactedAndNoSecretGetterExists() {
        var p = configured(); p.initialize();
        assertEquals("OrganizationAppointmentCommitProofProperties[redacted]",p.toString());
        assertEquals("OrganizationAppointmentCommitProofCodec[redacted]",p.issuerCodec().toString());
        assertTrue(java.util.Arrays.stream(p.getClass().getMethods()).noneMatch(m -> m.getName().equals("getSigningKeyBase64")));
        assertTrue(java.util.Arrays.stream(p.issuerCodec().getClass().getMethods()).noneMatch(m ->
                m.getReturnType().getSimpleName().equals("CommitProof")));
    }
}

