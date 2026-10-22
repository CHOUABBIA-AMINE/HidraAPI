/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationAppointmentCommitProofProperties
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-22
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.configuration
 *
 * @Description : Authenticates transaction-bound appointment commit evidence within Identity.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.configuration;

import dz.sh.hidra.modules.identity.infrastructure.security.OrganizationAppointmentCommitProofCodec;
import jakarta.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Base64;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/** Separate purpose-specific key. Binding setters never echo secret input in a validation failure. */
@Component
@ConfigurationProperties(prefix = IdentityModuleConfiguration.ORGANIZATION_COMMIT_PROOF_PREFIX)
public final class OrganizationAppointmentCommitProofProperties {
    private boolean enabled;
    private String keyId;
    private long keyRevision;
    private String signingKeyBase64;
    private OrganizationAppointmentCommitProofCodec codec;

    public void setEnabled(boolean value) { enabled = value; }
    public boolean isEnabled() { return enabled; }
    public void setKeyId(String value) { keyId = value; }
    public String getKeyId() { return keyId; }
    public void setKeyRevision(long value) { keyRevision = value; }
    public long getKeyRevision() { return keyRevision; }
    public void setSigningKeyBase64(String value) { signingKeyBase64 = value; }

    @PostConstruct public void initialize() {
        byte[] decoded = null;
        try {
            if (!enabled) { codec = null; return; }
            if (keyId == null || !keyId.matches("[a-zA-Z0-9][a-zA-Z0-9._-]{0,63}") || keyRevision <= 0)
                throw invalid();
            if (signingKeyBase64 == null) throw invalid();
            decoded = Base64.getDecoder().decode(signingKeyBase64);
            if (decoded.length != 32 || !Base64.getEncoder().encodeToString(decoded).equals(signingKeyBase64))
                throw invalid();
            codec = new OrganizationAppointmentCommitProofCodec(decoded);
        } catch (RuntimeException failure) {
            // Do not retain the decoder exception, binding value or a secret-bearing cause.
            throw invalid();
        } finally {
            signingKeyBase64 = null;
            if (decoded != null) Arrays.fill(decoded, (byte) 0);
        }
    }

    /** This codec exposes no public signing operation. The private native issuer owns its use. */
    public OrganizationAppointmentCommitProofCodec issuerCodec() {
        if (!enabled || codec == null) throw invalid();
        return codec;
    }
    private static IllegalStateException invalid() {
        return new IllegalStateException("Appointment commit proof configuration unavailable");
    }
    @Override public String toString() { return "OrganizationAppointmentCommitProofProperties[redacted]"; }
}

