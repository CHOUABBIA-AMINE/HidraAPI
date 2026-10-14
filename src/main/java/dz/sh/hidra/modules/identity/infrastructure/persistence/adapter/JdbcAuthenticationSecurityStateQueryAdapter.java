/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JdbcAuthenticationSecurityStateQueryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-14
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence.adapter
 *
 * @Description : Reads verification-time row images and joined current Identity eligibility independently of cached JPA entity state.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.identity.application.model.AuthenticationVerificationSnapshot;
import dz.sh.hidra.modules.identity.application.model.LocalCredentialVerificationState;
import dz.sh.hidra.modules.identity.application.port.out.AuthenticationSecurityStateQueryPort;
import dz.sh.hidra.modules.identity.domain.model.OperationAuthenticationBinding;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;

/** Verification and B1 reads use actual owner row images, not cached entities. */
@Component
public final class JdbcAuthenticationSecurityStateQueryAdapter
        implements AuthenticationSecurityStateQueryPort {
    private final JdbcTemplate jdbc;

    public JdbcAuthenticationSecurityStateQueryAdapter(JdbcTemplate jdbc) {
        this.jdbc = Objects.requireNonNull(jdbc);
    }

    @Override
    public ProviderState provider(String id, String type) {
        var rows =
                jdbc.query(
                        """
                        SELECT id,provider_type,security_version,issuer_uri,directory_base_dn,user_search_base
                        FROM public.hidra_identity_provider WHERE id=? AND status='ACTIVE' AND provider_type=?
                        """,
                        (r, n) ->
                                new ProviderState(
                                        r.getString(1),
                                        r.getString(2),
                                        r.getLong(3),
                                        r.getString(4),
                                        r.getString(5),
                                        r.getString(6)),
                        id,
                        type);
        if (rows.size() != 1) throw new IllegalStateException("Ineligible verification provider.");
        return rows.getFirst();
    }

    @Override
    public LocalCredentialVerificationState credential(String userId) {
        var rows =
                jdbc.query(
                        """
                        SELECT id,user_id,password_hash,credential_status,security_version
                        FROM public.hidra_identity_local_credential WHERE user_id=?
                        """,
                        (r, n) ->
                                new LocalCredentialVerificationState(
                                        r.getString(1),
                                        r.getString(2),
                                        r.getString(3),
                                        r.getString(4),
                                        r.getLong(5)),
                        userId);
        if (rows.size() != 1)
            throw new IllegalStateException("Ineligible LOCAL verification state.");
        return rows.getFirst();
    }

    @Override
    public AuthenticationVerificationSnapshot external(
            ProviderState p, String subject, String issuer, Instant expiry) {
        var rows =
                jdbc.query(
                        """
                        SELECT e.id,e.user_id,e.security_version FROM public.hidra_identity_external_identity e
                        JOIN public.hidra_identity_user u ON u.id=e.user_id
                        WHERE e.identity_provider_id=? AND e.external_subject=? AND e.status='LINKED' AND u.status='ACTIVE'
                          AND u.user_type='HUMAN' AND (u.locked_until IS NULL OR u.locked_until<=clock_timestamp())
                        """,
                        (r, n) ->
                                new AuthenticationVerificationSnapshot(
                                        r.getString(2),
                                        p.type(),
                                        p.id(),
                                        p.version(),
                                        r.getString(1),
                                        subject,
                                        r.getLong(3),
                                        issuer,
                                        expiry,
                                        null,
                                        null),
                        p.id(),
                        subject);
        if (rows.size() != 1)
            throw new IllegalStateException("Ineligible exact external verification state.");
        return rows.getFirst();
    }

    @Override
    public boolean current(OperationAuthenticationBinding b, Instant now) {
        var rows =
                jdbc.queryForList(
                        """
                        SELECT p.security_version AS pg,p.status AS ps,p.provider_type AS pt,p.issuer_uri AS pi,
                               u.status AS us,u.user_type AS ut,u.locked_until AS ul,
                               s.user_id AS su,s.identity_provider_id AS sp,s.external_identity_id AS se,s.session_type AS st,
                               s.status AS ss,s.ended_at AS ended,s.started_at AS started,s.expires_at AS expires,
                               e.id AS ei,e.user_id AS eu,e.identity_provider_id AS ep,e.external_subject AS es,e.status AS el,e.security_version AS eg,
                               c.id AS ci,c.user_id AS cu,c.credential_status AS cs,c.security_version AS cg
                        FROM public.hidra_identity_user u JOIN public.hidra_identity_provider p ON p.id=?
                        JOIN public.hidra_identity_login_session s ON s.id=?
                        LEFT JOIN public.hidra_identity_external_identity e ON e.id=?
                        LEFT JOIN public.hidra_identity_local_credential c ON c.id=? WHERE u.id=?
                        """,
                        b.providerId(),
                        b.sessionId(),
                        b.externalIdentityId(),
                        b.credentialId(),
                        b.userId());
        if (rows.size() != 1) throw new IllegalStateException("Corrupt bound owner references.");
        var r = rows.getFirst();
        if (!Objects.equals(b.userId(), r.get("su"))
                || !Objects.equals(b.providerId(), r.get("sp"))
                || !Objects.equals(b.externalIdentityId(), r.get("se"))
                || !Objects.equals(b.sessionProtocol(), r.get("st"))
                || !Objects.equals(b.issuedAt(), instant(r.get("started")))
                || !Objects.equals(b.expiresAt(), instant(r.get("expires"))))
            throw new IllegalStateException("Corrupt bound session tuple.");
        if (!number(r.get("pg"), b.providerSecurityVersion())
                || !"ACTIVE".equals(r.get("ps"))
                || !b.authenticationType().equals(r.get("pt"))
                || !"ACTIVE".equals(r.get("us"))
                || !"HUMAN".equals(r.get("ut"))
                || (r.get("ul") != null && now.isBefore(instant(r.get("ul"))))
                || !"ACTIVE".equals(r.get("ss"))
                || r.get("ended") != null
                || now.isBefore(b.issuedAt())
                || !now.isBefore(b.expiresAt())
                || (b.sourceExpiresAt() != null && !now.isBefore(b.sourceExpiresAt())))
            return false;
        if (b.authenticationType().equals("LOCAL")) {
            if (r.get("ci") == null) throw new IllegalStateException("Missing bound credential.");
            if (!number(r.get("cg"), b.credentialSecurityVersion())
                    || !"ACTIVE".equals(r.get("cs"))) return false;
            if (!b.userId().equals(r.get("cu")))
                throw new IllegalStateException("Corrupt bound credential reference.");
        } else {
            if (r.get("ei") == null) throw new IllegalStateException("Missing bound link.");
            if (!number(r.get("eg"), b.externalSecurityVersion()) || !"LINKED".equals(r.get("el")))
                return false;
            if (!b.userId().equals(r.get("eu"))
                    || !b.providerId().equals(r.get("ep"))
                    || !b.externalSubject().equals(r.get("es")))
                throw new IllegalStateException("Corrupt bound external reference.");
            if (b.authenticationType().equals("OIDC") && !b.sourceIssuer().equals(r.get("pi")))
                throw new IllegalStateException("Corrupt bound issuer reference.");
        }
        return true;
    }

    private static boolean number(Object value, long expected) {
        return value instanceof Number n && n.longValue() == expected;
    }

    private static Instant instant(Object value) {
        return value == null ? null : ((Timestamp) value).toInstant();
    }
}
