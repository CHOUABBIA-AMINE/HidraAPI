package dz.sh.hidra.modules.identity.infrastructure.security;

import dz.sh.hidra.modules.identity.application.model.AuthenticatedPrincipalInput;
import dz.sh.hidra.modules.identity.application.port.in.IdentityAdministrationQueryUseCase;
import dz.sh.hidra.modules.identity.application.port.out.AuthenticationSecurityStateQueryPort;
import dz.sh.hidra.modules.identity.application.port.out.OperationAuthenticationBindingRepositoryPort;
import dz.sh.hidra.modules.identity.application.port.out.RefreshTokenEvidencePort;
import dz.sh.hidra.modules.identity.application.port.out.RefreshTokenRepositoryPort;
import dz.sh.hidra.modules.identity.domain.model.HidraPrincipal;
import dz.sh.hidra.modules.identity.domain.service.RefreshTokenRotationPolicy;
import dz.sh.hidra.modules.identity.domain.value.ProviderType;
import dz.sh.hidra.modules.identity.domain.value.UserStatus;
import dz.sh.hidra.modules.identity.domain.value.UserType;
import dz.sh.hidra.modules.identity.infrastructure.persistence.repository.UserJpaRepository;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

/**
 * Only this Identity-owned adapter may promote proof of a consumed, keyed LOCAL refresh token
 * into the registry's genuine invocation-bound producer receipt. A refresh is never an IdP proof.
 */
@Component
public final class TrustedRefreshTokenEvidenceAdapter implements RefreshTokenEvidencePort {
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final byte[] DERIVE_CONTEXT =
            "Hidra/identity/refresh-key/v1".getBytes(StandardCharsets.UTF_8);
    private static final byte[] TOKEN_CONTEXT =
            "Hidra/identity/refresh-token/v1".getBytes(StandardCharsets.UTF_8);
    private static final byte[] RIGHTS_CONTEXT =
            "Hidra/identity/refresh-rights/v1".getBytes(StandardCharsets.UTF_8);
    private final boolean enabled;
    private final long ttl;
    private final byte[] derivedKey;
    private final TrustedAuthenticationVerificationRegistry registry;
    private final OperationAuthenticationBindingRepositoryPort bindings;
    private final AuthenticationSecurityStateQueryPort states;
    private final UserJpaRepository users;
    private final IdentityAdministrationQueryUseCase queries;
    private final IdentityAdministratorGrantService admin;

    public TrustedRefreshTokenEvidenceAdapter(
            @Value("${hidra.identity.refresh.enabled:false}") boolean enabled,
            @Value("${hidra.identity.refresh.family-ttl-seconds:604800}") long ttl,
            @Value("${hidra.platform.security.jwt.hmac-secret:}") String configuredSecret,
            TrustedAuthenticationVerificationRegistry registry,
            OperationAuthenticationBindingRepositoryPort bindings,
            AuthenticationSecurityStateQueryPort states,
            UserJpaRepository users, IdentityAdministrationQueryUseCase queries,
            IdentityAdministratorGrantService admin) {
        this.enabled=enabled;
        RefreshTokenRotationPolicy.deadline(Instant.parse("2026-01-01T00:00:00Z"),ttl);
        this.ttl=ttl;
        this.registry=Objects.requireNonNull(registry);
        this.bindings=Objects.requireNonNull(bindings);
        this.states=Objects.requireNonNull(states);
        this.users=Objects.requireNonNull(users);
        this.queries=Objects.requireNonNull(queries);
        this.admin=Objects.requireNonNull(admin);
        if (enabled && (configuredSecret==null
                || configuredSecret.strip().getBytes(StandardCharsets.UTF_8).length<32))
            throw new IllegalStateException("Refresh feature requires valid native signing key.");
        this.derivedKey=enabled?derive(configuredSecret.strip()):new byte[0];
    }

    @Override public boolean enabled(){return enabled;}
    @Override public long familyTtlSeconds(){return ttl;}
    @Override public Opaque fresh(String familyId) {
        requireEnabled();
        String id=canonicalId(familyId);
        byte[] random=new byte[32];RANDOM.nextBytes(random);
        try {
            String raw=id+"."+Base64.getUrlEncoder().withoutPadding().encodeToString(random);
            return new Opaque(raw,id,mac(TOKEN_CONTEXT,raw.getBytes(StandardCharsets.US_ASCII)));
        } finally {java.util.Arrays.fill(random,(byte)0);}
    }
    @Override public Proof proof(String token) {
        requireEnabled();
        if(token==null||token.length()!=80||token.charAt(36)!='.')throw invalid();
        String id;
        try {
            id=canonicalId(token.substring(0,36));
            String suffix=token.substring(37);
            byte[] random=Base64.getUrlDecoder().decode(suffix);
            try {
                if(random.length!=32
                        || !Base64.getUrlEncoder().withoutPadding().encodeToString(random).equals(suffix))
                    throw invalid();
            } finally {java.util.Arrays.fill(random,(byte)0);}
        } catch(IllegalArgumentException e){throw invalid();}
        return new Proof(id,mac(TOKEN_CONTEXT,token.getBytes(StandardCharsets.US_ASCII)));
    }

    @Override public byte[] rightsDigest(HidraPrincipal principal) {
        requireEnabled();
        Objects.requireNonNull(principal);
        try {
            Mac m=Mac.getInstance("HmacSHA256");
            m.init(new SecretKeySpec(derivedKey,"HmacSHA256"));
            m.update(RIGHTS_CONTEXT);m.update((byte)0);
            for(String role:new TreeSet<>(principal.roles()))add(m,"role",role);
            for(String permission:new TreeSet<>(principal.permissions()))add(m,"permission",permission);
            return m.doFinal();
        }catch(GeneralSecurityException e){throw new IllegalStateException("Refresh digest unavailable.");}
    }
    @Override
    public AuthenticatedPrincipalInput verifyAndBegin(RefreshTokenRepositoryPort.Consumed consumed,
                                                      Instant observedAt) {
        requireEnabled();
        var f=consumed.family();
        var binding=bindings.findByNativeIssuerAndJti(consumed.issuer(),consumed.jti())
                .orElseThrow(TrustedRefreshTokenEvidenceAdapter::invalid);
        registry.validatePersistentOrigin(binding);
        if(!binding.bindingId().equals(consumed.bindingId())
                || !binding.sessionId().equals(consumed.sessionId())
                || !"LOCAL".equals(binding.authenticationType())
                || !binding.userId().equals(f.userId())
                || !binding.providerId().equals(f.providerId())
                || !binding.credentialId().equals(f.credentialId())
                || binding.providerSecurityVersion()!=f.providerGeneration()
                || binding.credentialSecurityVersion()==null
                || binding.credentialSecurityVersion()!=f.credentialGeneration()
                || !binding.nativeIssuer().equals(consumed.issuer()))
            throw invalid();
        var user=users.findById(f.userId()).orElseThrow(TrustedRefreshTokenEvidenceAdapter::invalid);
        if(user.userType()!=UserType.HUMAN||user.status()!=UserStatus.ACTIVE
                || (user.lockedUntil()!=null&&observedAt.isBefore(user.lockedUntil())))
            throw invalid();
        AuthenticationSecurityStateQueryPort.ProviderState p;
        dz.sh.hidra.modules.identity.application.model.LocalCredentialVerificationState c;
        try {
            p=states.provider(f.providerId(),"LOCAL");
            c=states.credential(f.userId());
        } catch (IllegalStateException missingOrDisabled) {
            // Failure to locate an eligible LOCAL source is a denied refresh, not a path
            // that resurrects the family when the provider is later reenabled.
            throw invalid();
        }
        if(p.version()!=f.providerGeneration()
                || !c.credentialId().equals(f.credentialId())
                || c.securityVersion()!=f.credentialGeneration()
                || !"ACTIVE".equals(c.status()))
            throw invalid();
        boolean administrator=admin.hasActiveGlobalAdministratorGrant(user.id());
        var rights=queries.principal(user.id(),List.of());
        var principal=new HidraPrincipal(user.id(),user.username(),user.displayName(),
                ProviderType.LOCAL,f.providerId(),
                administrator?Set.of("HIDRA_ADMIN"):Set.of(),
                Set.copyOf(rights.effectivePermissions()));
        if(!MessageDigest.isEqual(f.rightsDigest(),rightsDigest(principal)))
            throw invalid();
        var state=new dz.sh.hidra.modules.identity.application.model.AuthenticationVerificationSnapshot(
                f.userId(),"LOCAL",f.providerId(),p.version(),null,null,null,null,null,
                c.credentialId(),c.securityVersion());
        registry.begin();
        try {
            var actual=UsernamePasswordAuthenticationToken.authenticated(principal,null,List.of());
            var verified=registry.verifiedLocalRefresh(actual,state);
            return new AuthenticatedPrincipalInput(principal.userId(),principal.username(),
                    principal.displayName(),ProviderType.LOCAL,f.providerId(),
                    principal.roles(),principal.permissions(),null,registry.verifiedHandle(verified));
        }catch(RuntimeException|Error failure){registry.close();throw failure;}
    }
    @Override public void finish(){registry.close();}
    private void requireEnabled(){if(!enabled)throw invalid();}
    private static String canonicalId(String text) {
        if(text==null||text.length()!=36)throw invalid();
        try {
            if(!UUID.fromString(text).toString().equals(text))throw invalid();
            return text;
        }catch(IllegalArgumentException exception){throw invalid();}
    }
    private static void add(Mac m,String kind,String data){
        byte[] a=kind.getBytes(StandardCharsets.UTF_8),b=data.getBytes(StandardCharsets.UTF_8);
        m.update(ByteBuffer.allocate(4).putInt(a.length).array());m.update(a);
        m.update(ByteBuffer.allocate(4).putInt(b.length).array());m.update(b);
    }
    private byte[] mac(byte[] prefix,byte[] bytes){
        try {
            var m=Mac.getInstance("HmacSHA256");
            m.init(new SecretKeySpec(derivedKey,"HmacSHA256"));
            m.update(prefix);m.update((byte)0);return m.doFinal(bytes);
        }catch(GeneralSecurityException error){throw new IllegalStateException("Refresh cryptography unavailable.");}
    }
    private static byte[] derive(String secret){
        byte[] raw=secret.getBytes(StandardCharsets.UTF_8);
        try {
            var m=Mac.getInstance("HmacSHA256");
            m.init(new SecretKeySpec(raw,"HmacSHA256"));
            return m.doFinal(DERIVE_CONTEXT);
        }catch(GeneralSecurityException error){throw new IllegalStateException("Refresh key unavailable.");}
        finally {java.util.Arrays.fill(raw,(byte)0);}
    }
    private static BadCredentialsException invalid() {
        return new BadCredentialsException("Invalid refresh credentials.");
    }
}
