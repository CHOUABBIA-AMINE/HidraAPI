/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JdbcOperationAuthenticationBindingRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-14
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence.adapter
 *
 * @Description : Appends immutable Identity binding tuples through PostgreSQL guards without exposing private producer tags in storage failures.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.identity.application.port.out.OperationAuthenticationBindingRepositoryPort;
import dz.sh.hidra.modules.identity.domain.model.OperationAuthenticationBinding;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

/** Conflict-safe immutable append; exact replay never promotes producer provenance. */
@Component
public class JdbcOperationAuthenticationBindingRepositoryAdapter
        implements OperationAuthenticationBindingRepositoryPort {
    private final JdbcTemplate jdbc;

    public JdbcOperationAuthenticationBindingRepositoryAdapter(JdbcTemplate jdbc) {
        this.jdbc = Objects.requireNonNull(jdbc);
    }

    @Override
    public Optional<OperationAuthenticationBinding> findByNativeIssuerAndJti(
            String issuer, String jti) {
        try {
            var rows =
                    jdbc.query(
                            "SELECT * FROM public.hidra_identity_operation_binding WHERE"
                                + " native_issuer=? AND jti=?",
                            JdbcOperationAuthenticationBindingRepositoryAdapter::map,
                            issuer,
                            jti);
            if (rows.size() > 1)
                throw new IllegalStateException("Corrupt authentication association cardinality.");
            return rows.stream().findFirst();
        } catch (DataAccessException failure) {
            // PostgreSQL error DETAIL can contain private tuple/tag bytes; never propagate it.
            throw new IllegalStateException("Authentication storage lookup failed.");
        }
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public void append(OperationAuthenticationBinding b) {
        if (!TransactionSynchronizationManager.isActualTransactionActive()
                || TransactionSynchronizationManager.isCurrentTransactionReadOnly())
            throw new IllegalStateException(
                    "Authentication append requires an active writable transaction.");
        try {
            if (!"read committed"
                    .equals(jdbc.queryForObject("SHOW transaction_isolation", String.class)))
                throw new IllegalStateException("Authentication append requires read committed.");
            jdbc.update(
                    """
                    INSERT INTO public.hidra_identity_operation_binding
                    (binding_id,format_version,native_issuer,native_audience,jti,session_id,user_id,authentication_type,
                     session_protocol,provider_id,provider_security_version,external_identity_id,external_subject,
                     external_security_version,source_issuer,source_expires_at,credential_id,credential_security_version,
                     issued_at,expires_at,producer_authenticator)
                    VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ON CONFLICT DO NOTHING
                    """,
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
                    time(b.sourceExpiresAt()),
                    b.credentialId(),
                    b.credentialSecurityVersion(),
                    time(b.issuedAt()),
                    time(b.expiresAt()),
                    b.producerAuthenticator());
            exact(
                    findByNativeIssuerAndJti(b.nativeIssuer(), b.jti())
                            .orElseThrow(
                                    () ->
                                            new IllegalStateException(
                                                    "Conflicting binding identity.")),
                    b);
        } catch (DataAccessException failure) {
            throw new IllegalStateException("Authentication storage append failed.");
        }
    }

    private static void exact(
            OperationAuthenticationBinding old, OperationAuthenticationBinding b) {
        if (!old.equals(b))
            throw new IllegalStateException("Conflicting immutable authentication association.");
    }

    private static Timestamp time(Instant value) {
        return value == null ? null : Timestamp.from(value);
    }

    private static Instant instant(ResultSet r, String column) throws SQLException {
        var t = r.getTimestamp(column);
        return t == null ? null : t.toInstant();
    }

    private static Long number(ResultSet r, String column) throws SQLException {
        var n = r.getObject(column);
        return n == null ? null : ((Number) n).longValue();
    }

    public static OperationAuthenticationBinding map(ResultSet r, int row) throws SQLException {
        try {
            return new OperationAuthenticationBinding(
                    r.getString("binding_id"),
                    r.getShort("format_version"),
                    r.getString("native_issuer"),
                    r.getString("native_audience"),
                    r.getString("jti"),
                    r.getString("session_id"),
                    r.getString("user_id"),
                    r.getString("authentication_type"),
                    r.getString("session_protocol"),
                    r.getString("provider_id"),
                    r.getLong("provider_security_version"),
                    r.getString("external_identity_id"),
                    r.getString("external_subject"),
                    number(r, "external_security_version"),
                    r.getString("source_issuer"),
                    instant(r, "source_expires_at"),
                    r.getString("credential_id"),
                    number(r, "credential_security_version"),
                    instant(r, "issued_at"),
                    instant(r, "expires_at"),
                    r.getBytes("producer_authenticator"));
        } catch (IllegalArgumentException invalid) {
            throw new IllegalStateException("Corrupt persisted authentication tuple.");
        }
    }
}
