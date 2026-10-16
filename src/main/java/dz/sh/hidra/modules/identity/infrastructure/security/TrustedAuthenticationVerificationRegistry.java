/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TrustedAuthenticationVerificationRegistry
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-14
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.security
 *
 * @Description : Owns invocation-bound producer receipts and authenticates immutable binding tuples with a private derived key.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.security;

import dz.sh.hidra.modules.identity.application.model.AccessTokenIssuanceContext;
import dz.sh.hidra.modules.identity.application.model.AccessTokenIssuerSettings;
import dz.sh.hidra.modules.identity.application.model.AuthenticatedPrincipalInput;
import dz.sh.hidra.modules.identity.application.model.AuthenticationVerificationSnapshot;
import dz.sh.hidra.modules.identity.application.port.out.TrustedAuthenticationVerificationPort;
import dz.sh.hidra.modules.identity.domain.model.HidraPrincipal;
import dz.sh.hidra.modules.identity.domain.model.OperationAuthenticationBinding;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.nio.ByteBuffer;
import java.time.Instant;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/** Private producer origin, one-use invocation and persistent format-one witness. */
@Component
public final class TrustedAuthenticationVerificationRegistry
        implements TrustedAuthenticationVerificationPort {
    private static final byte[] SALT =
            "Hidra/identity/operation-binding/format-1/key".getBytes(StandardCharsets.UTF_8);
    private static final byte[] INFO =
            "Hidra/identity/operation-binding/format-1/authenticator"
                    .getBytes(StandardCharsets.UTF_8);
    private static final byte[] DOMAIN =
            "Hidra/identity/operation-binding/format-1/tuple".getBytes(StandardCharsets.UTF_8);
    private final String signingSecret;
    private final String nativeIssuer;
    private final String nativeAudience;
    private volatile byte[] key;
    private final ThreadLocal<Invocation> invocation = new ThreadLocal<>();

    public TrustedAuthenticationVerificationRegistry(
            @Value("${hidra.platform.security.jwt.hmac-secret:}") String signingSecret,
            @Value("${hidra.platform.security.jwt.token-issuer:hidra-api}") String nativeIssuer,
            @Value("${hidra.platform.security.jwt.audience:hidra-api}") String nativeAudience) {
        this.signingSecret = signingSecret;
        this.nativeIssuer = nativeIssuer;
        this.nativeAudience = nativeAudience;
    }

    /** Private bounded source evidence; never a public grant or native token claim. */
    record OidcSourceEvidence(String providerId, String issuer, String subject,
            Instant sourceExpiresAt, OidcClaimCoverageProjector.Projection projection) {
        OidcSourceEvidence {
            Objects.requireNonNull(providerId);
            Objects.requireNonNull(issuer);
            Objects.requireNonNull(subject);
            Objects.requireNonNull(sourceExpiresAt);
            Objects.requireNonNull(projection);
            if (providerId.isBlank() || issuer.isBlank() || subject.isBlank()
                    || !Instant.now().isBefore(sourceExpiresAt)) throw denied();
        }
        @Override public String toString() {
            return "OidcSourceEvidence[claims=<redacted>, provenance=<redacted>]";
        }
    }

    static final class Receipt {
        private final AuthenticationVerificationSnapshot snapshot;
        private final HidraPrincipal principal;
        private final OidcSourceEvidence oidcEvidence;
        private final long thread = Thread.currentThread().threadId();
        private final AtomicBoolean consumed = new AtomicBoolean();
        private volatile OperationAuthenticationBinding completed;

        private Receipt(AuthenticationVerificationSnapshot snapshot, HidraPrincipal principal) {
            this(snapshot, principal, null);
        }

        private Receipt(AuthenticationVerificationSnapshot snapshot, HidraPrincipal principal,
                OidcSourceEvidence oidcEvidence) {
            this.snapshot = Objects.requireNonNull(snapshot);
            this.principal = Objects.requireNonNull(principal);
            this.oidcEvidence = oidcEvidence;
        }

        OidcSourceEvidence oidcEvidence() {
            return oidcEvidence;
        }
    }

    private static final class Invocation {
        final String handle = UUID.randomUUID().toString() + UUID.randomUUID();
        private DirectToken token;
    }

    private static final class DirectToken extends UsernamePasswordAuthenticationToken {
        private final Receipt receipt;

        private DirectToken(Authentication actual, Receipt receipt) {
            super(actual.getPrincipal(), null, actual.getAuthorities());
            this.receipt = receipt;
            setDetails(actual.getDetails());
        }
    }

    String begin() {
        if (invocation.get() != null) throw denied();
        var i = new Invocation();
        invocation.set(i);
        return i.handle;
    }

    void close() {
        invocation.remove();
    }

    String verifiedHandle(Authentication actual) {
        var i = invocation.get();
        if (i == null || i.token != actual || !actual.isAuthenticated()) throw denied();
        return i.handle;
    }

    // Exactly the successful LOCAL and LDAP/AD providers may call this package-private producer
    // method.
    Authentication verifiedDirect(
            Authentication actual, AuthenticationVerificationSnapshot snapshot) {
        if (!actual.isAuthenticated() || !(actual.getPrincipal() instanceof HidraPrincipal p))
            throw denied();
        var token = new DirectToken(actual, new Receipt(snapshot, p));
        var i = invocation.get();
        if (i != null) {
            if (i.token != null) throw denied();
            i.token = token;
        }
        return token;
    }

    // Exactly the successful qualified-decoder OIDC converter may call this producer method.
    Receipt verifiedOidc(AuthenticationVerificationSnapshot snapshot, HidraPrincipal principal) {
        return new Receipt(snapshot, principal);
    }

    Receipt verifiedOidc(AuthenticationVerificationSnapshot snapshot, HidraPrincipal principal,
            OidcSourceEvidence evidence) {
        if (snapshot == null || principal == null || evidence == null
                || !"OIDC".equals(snapshot.authenticationType())
                || !Objects.equals(snapshot.providerId(), evidence.providerId())
                || !Objects.equals(snapshot.sourceIssuer(), evidence.issuer())
                || !Objects.equals(snapshot.externalSubject(), evidence.subject())
                || !Objects.equals(snapshot.sourceExpiresAt(), evidence.sourceExpiresAt())
                || !Objects.equals(principal.identityProviderId(), evidence.providerId())
                || !Objects.equals(principal.userId(), snapshot.userId())
                || !Objects.equals(principal.externalIdentityId(), snapshot.externalIdentityId())
                || !Instant.now().isBefore(evidence.sourceExpiresAt())) throw denied();
        return new Receipt(snapshot, principal, evidence);
    }

    @Override
    public Verification consume(AuthenticatedPrincipalInput input) {
        Objects.requireNonNull(input);
        Receipt r;
        Invocation directOrigin = null;
        Authentication oidcOrigin = null;
        if (input.verificationHandle() != null) {
            var i = invocation.get();
            if (i == null
                    || !i.handle.equals(input.verificationHandle())
                    || i.token == null
                    || !i.token.isAuthenticated()) throw denied();
            r = i.token.receipt;
            directOrigin = i;
        } else {
            var actual = SecurityContextHolder.getContext().getAuthentication();
            if (!(actual instanceof HidraOidcAuthenticationToken o) || !actual.isAuthenticated())
                throw denied();
            r = o.verificationReceipt();
            oidcOrigin = actual;
        }
        if (r == null
                || r.thread != Thread.currentThread().threadId()
                || !r.consumed.compareAndSet(false, true)) throw denied();
        HidraPrincipal supplied =
                new HidraPrincipal(
                        input.userId(),
                        input.username(),
                        input.displayName(),
                        input.authenticationType(),
                        input.identityProviderId(),
                        input.roles(),
                        input.permissions(),
                        input.externalIdentityId());
        var s = r.snapshot;
        if (!r.principal.equals(supplied)
                || !s.userId().equals(input.userId())
                || !s.providerId().equals(input.identityProviderId())
                || !s.authenticationType().equals(input.authenticationType().name())
                || !Objects.equals(s.externalIdentityId(), input.externalIdentityId()))
            throw denied();
        return new Consumed(r, directOrigin, oidcOrigin);
    }

    private final class Consumed implements Verification {
        private final Receipt receipt;
        private final Invocation directOrigin;
        private final Authentication oidcOrigin;
        private final AtomicBoolean attestationAttempt = new AtomicBoolean();
        private TransactionSynchronization attestationTransaction;
        private OperationAuthenticationBinding attested;
        private final AtomicBoolean publication = new AtomicBoolean();

        private Consumed(Receipt receipt, Invocation directOrigin, Authentication oidcOrigin) {
            this.receipt = receipt;
            this.directOrigin = directOrigin;
            this.oidcOrigin = oidcOrigin;
        }

        private void requireOrigin() {
            if (receipt.thread != Thread.currentThread().threadId()) throw denied();
            if (directOrigin != null) {
                if (invocation.get() != directOrigin
                        || directOrigin.token.receipt != receipt
                        || !directOrigin.token.isAuthenticated()) throw denied();
            } else if (oidcOrigin == null
                    || SecurityContextHolder.getContext().getAuthentication() != oidcOrigin
                    || !oidcOrigin.isAuthenticated()) throw denied();
        }

        @Override
        public AuthenticationVerificationSnapshot snapshot() {
            return receipt.snapshot;
        }

        @Override
        public OperationAuthenticationBinding attest(
                String bindingId,
                String sessionId,
                AccessTokenIssuerSettings settings,
                AccessTokenIssuanceContext context) {
            if (!attestationAttempt.compareAndSet(false, true)) throw denied();
            requireOrigin();
            if (!TransactionSynchronizationManager.isActualTransactionActive()
                    || TransactionSynchronizationManager.isCurrentTransactionReadOnly()
                    || !TransactionSynchronizationManager.isSynchronizationActive()
                    || attested != null
                    || receipt.thread != Thread.currentThread().threadId()
                    || !settings.issuer().equals(nativeIssuer)
                    || !settings.audience().equals(nativeAudience)) throw denied();
            if (context.expiresAt().getEpochSecond() - context.issuedAt().getEpochSecond()
                    > settings.ttlSeconds()) throw denied();
            var s = receipt.snapshot;
            String protocol =
                    s.authenticationType().equals("ACTIVE_DIRECTORY")
                            ? "LDAP"
                            : s.authenticationType();
            var unsigned =
                    new OperationAuthenticationBinding(
                            bindingId,
                            (short) 1,
                            settings.issuer(),
                            settings.audience(),
                            context.jti(),
                            sessionId,
                            s.userId(),
                            s.authenticationType(),
                            protocol,
                            s.providerId(),
                            s.providerSecurityVersion(),
                            s.externalIdentityId(),
                            s.externalSubject(),
                            s.externalSecurityVersion(),
                            s.sourceIssuer(),
                            s.sourceExpiresAt(),
                            s.credentialId(),
                            s.credentialSecurityVersion(),
                            context.issuedAt(),
                            context.expiresAt(),
                            new byte[32]);
            attested = copyWithTag(unsigned, tag(unsigned));
            // Marker identity prevents a consumed receipt from moving to another transaction.
            attestationTransaction = new TransactionSynchronization() {};
            TransactionSynchronizationManager.registerSynchronization(attestationTransaction);
            return attested;
        }

        @Override
        public void publishAfterCommit(OperationAuthenticationBinding binding) {
            if (!publication.compareAndSet(false, true)) throw denied();
            requireOrigin();
            if (attested == null
                    || !attested.equals(binding)
                    || !TransactionSynchronizationManager.isActualTransactionActive()
                    || !TransactionSynchronizationManager.isSynchronizationActive()
                    || TransactionSynchronizationManager.isCurrentTransactionReadOnly()
                    || !TransactionSynchronizationManager.getSynchronizations()
                            .contains(attestationTransaction)) throw denied();
            // The registry itself enforces after-commit publication. Caller timing cannot promote
            // an uncommitted receipt.
            TransactionSynchronizationManager.registerSynchronization(
                    new TransactionSynchronization() {
                        @Override
                        public void afterCommit() {
                            receipt.completed = binding;
                        }
                    });
        }
    }

    OperationAuthenticationBinding completed(Authentication actual) {
        Receipt r =
                actual instanceof DirectToken d
                        ? d.receipt
                        : actual instanceof HidraOidcAuthenticationToken o
                                ? o.verificationReceipt()
                                : null;
        return r == null
                        || !actual.isAuthenticated()
                        || r.thread != Thread.currentThread().threadId()
                ? null
                : r.completed;
    }

    /**
     * Verification is public owner infrastructure; attestation remains private to consumed producer
     * objects.
     */
    public void validatePersistentOrigin(OperationAuthenticationBinding binding) {
        if (!MessageDigest.isEqual(tag(binding), binding.producerAuthenticator()))
            throw new IllegalStateException("Corrupt persisted authentication provenance.");
    }

    private byte[] attestationKey() {
        byte[] result = key;
        if (result == null)
            synchronized (this) {
                result = key;
                if (result == null) {
                    // Identical effective signing bytes to the existing HS256 loader; no new
                    // deployment secret.
                    if (signingSecret == null || signingSecret.isBlank())
                        throw new IllegalStateException(
                                "Native authentication key is unavailable.");
                    byte[] effective = signingSecret.trim().getBytes(StandardCharsets.UTF_8);
                    if (effective.length < 32)
                        throw new IllegalStateException("Native authentication key is invalid.");
                    result = hkdf(effective, SALT, INFO, 32);
                    java.util.Arrays.fill(effective, (byte) 0);
                    key = result;
                }
            }
        return result;
    }

    static byte[] hkdf(byte[] ikm, byte[] salt, byte[] info, int length) {
        if (length <= 0 || length > 255 * 32)
            throw new IllegalArgumentException("Invalid derivation length.");
        try {
            Mac h = Mac.getInstance("HmacSHA256");
            h.init(new SecretKeySpec(salt.length == 0 ? new byte[32] : salt, "HmacSHA256"));
            byte[] prk = h.doFinal(ikm);
            h.init(new SecretKeySpec(prk, "HmacSHA256"));
            byte[] output = new byte[length], previous = new byte[0];
            int at = 0;
            for (int block = 1; at < length; block++) {
                h.update(previous);
                h.update(info);
                h.update((byte) block);
                previous = h.doFinal();
                int amount = Math.min(previous.length, length - at);
                System.arraycopy(previous, 0, output, at, amount);
                at += amount;
            }
            java.util.Arrays.fill(prk, (byte) 0);
            java.util.Arrays.fill(previous, (byte) 0);
            return output;
        } catch (GeneralSecurityException failure) {
            throw new IllegalStateException("Private authenticator is unavailable.", failure);
        }
    }

    private byte[] tag(OperationAuthenticationBinding b) {
        try {
            Mac m = Mac.getInstance("HmacSHA256");
            m.init(new SecretKeySpec(attestationKey(), "HmacSHA256"));
            m.update(DOMAIN);
            m.update((byte) 0);
            text(m, b.bindingId());
            small(m, b.formatVersion());
            text(m, b.nativeIssuer());
            text(m, b.nativeAudience());
            text(m, b.jti());
            text(m, b.sessionId());
            text(m, b.userId());
            text(m, b.authenticationType());
            text(m, b.sessionProtocol());
            text(m, b.providerId());
            number(m, b.providerSecurityVersion());
            text(m, b.externalIdentityId());
            text(m, b.externalSubject());
            number(m, b.externalSecurityVersion());
            text(m, b.sourceIssuer());
            time(m, b.sourceExpiresAt());
            text(m, b.credentialId());
            number(m, b.credentialSecurityVersion());
            time(m, b.issuedAt());
            time(m, b.expiresAt());
            return m.doFinal();
        } catch (GeneralSecurityException failure) {
            throw new IllegalStateException("Private authenticator is unavailable.", failure);
        }
    }

    private static void text(Mac m, String value) {
        if (value == null) {
            m.update((byte) 0);
            return;
        }
        m.update((byte) 1);
        long bytes = 0;
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            if (Character.isHighSurrogate(c)) {
                if (++i >= value.length() || !Character.isLowSurrogate(value.charAt(i)))
                    throw invalidFrame();
                bytes += 4;
            } else if (Character.isLowSurrogate(c)) throw invalidFrame();
            else bytes += c < 128 ? 1 : c < 2048 ? 2 : 3;
        }
        if (bytes > Integer.MAX_VALUE) throw invalidFrame();
        m.update(ByteBuffer.allocate(4).putInt((int) bytes).array());
        var encoder =
                StandardCharsets.UTF_8
                        .newEncoder()
                        .onMalformedInput(CodingErrorAction.REPORT)
                        .onUnmappableCharacter(CodingErrorAction.REPORT);
        CharBuffer input = CharBuffer.wrap(value);
        ByteBuffer buffer = ByteBuffer.allocate(4096);
        try {
            while (true) {
                var result = encoder.encode(input, buffer, true);
                if (result.isError()) result.throwException();
                buffer.flip();
                m.update(buffer);
                buffer.clear();
                if (result.isUnderflow()) break;
            }
            while (true) {
                var result = encoder.flush(buffer);
                if (result.isError()) result.throwException();
                buffer.flip();
                m.update(buffer);
                buffer.clear();
                if (result.isUnderflow()) break;
            }
        } catch (CharacterCodingException invalid) {
            throw invalidFrame();
        }
    }

    private static void small(Mac m, short value) {
        m.update((byte) 1);
        m.update(ByteBuffer.allocate(2).putShort(value).array());
    }

    private static void number(Mac m, Long value) {
        if (value == null) {
            m.update((byte) 0);
            return;
        }
        m.update((byte) 1);
        m.update(ByteBuffer.allocate(8).putLong(value).array());
    }

    private static void time(Mac m, java.time.Instant value) {
        if (value == null) {
            m.update((byte) 0);
            return;
        }
        m.update((byte) 1);
        m.update(
                ByteBuffer.allocate(12)
                        .putLong(value.getEpochSecond())
                        .putInt(value.getNano())
                        .array());
    }

    private static OperationAuthenticationBinding copyWithTag(
            OperationAuthenticationBinding b, byte[] tag) {
        return new OperationAuthenticationBinding(
                b.bindingId(),
                b.formatVersion(),
                b.nativeIssuer(),
                b.nativeAudience(),
                b.jti(),
                b.sessionId(),
                b.userId(),
                b.authenticationType(),
                b.sessionProtocol(),
                b.providerId(),
                b.providerSecurityVersion(),
                b.externalIdentityId(),
                b.externalSubject(),
                b.externalSecurityVersion(),
                b.sourceIssuer(),
                b.sourceExpiresAt(),
                b.credentialId(),
                b.credentialSecurityVersion(),
                b.issuedAt(),
                b.expiresAt(),
                tag);
    }

    private static IllegalArgumentException invalidFrame() {
        return new IllegalArgumentException("Unrepresentable authentication frame.");
    }

    private static BadCredentialsException denied() {
        return new BadCredentialsException("Trusted authentication provenance is unavailable.");
    }
}
