/**
 * @Project : HidraAPI @Product : Hidra - Hydrocarbon Intelligence for Data, Risk, and
 * Analytics @Author : Abir MEDJERAB @Owner : Sonatrach / TRC : Digitalization Initiative @Name :
 * JdbcBoundSourceClaimIssuanceFence @CreatedOn : 2025-06-26 @UpdatedOn : 2026-10-17 @Type :
 * Class @Layer : Infrastructure @Module : identity @Package :
 * dz.sh.hidra.modules.identity.infrastructure.persistence.adapter @Description : Fences captured
 * claim issuance against current mapping writers in one physical transaction.
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.identity.application.port.out.BoundSourceClaimIssuanceFencePort;
import dz.sh.hidra.modules.identity.application.port.out.ClaimNameCatalogPort;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.List;
import java.util.Objects;

/** Fixed relation order precedes every captured-claim session write. */
@Repository
public class JdbcBoundSourceClaimIssuanceFence implements BoundSourceClaimIssuanceFencePort {
    private static final List<String> RELATIONS =
            List.of(
                    "public.hidra_identity_attribute_definition",
                    "public.hidra_identity_authorization_policy",
                    "public.hidra_identity_authorization_policy_rule",
                    "public.hidra_identity_authorization_policy_version",
                    "public.hidra_identity_external_group_mapping",
                    "public.hidra_identity_external_identity",
                    "public.hidra_identity_external_permission_mapping",
                    "public.hidra_identity_external_role_mapping",
                    "public.hidra_identity_group",
                    "public.hidra_identity_group_role_grant",
                    "public.hidra_identity_local_credential",
                    "public.hidra_identity_login_session",
                    "public.hidra_identity_operation_binding",
                    "public.hidra_identity_permission",
                    "public.hidra_identity_provider",
                    "public.hidra_identity_role",
                    "public.hidra_identity_role_permission_grant",
                    "public.hidra_identity_subject_security_attribute",
                    "public.hidra_identity_user",
                    "public.hidra_identity_user_group_membership",
                    "public.hidra_identity_user_permission_grant",
                    "public.hidra_identity_user_role_grant");

    private static final String HELD =
            "SELECT count(DISTINCT l.relation) FROM pg_catalog.pg_locks l "
                    + "WHERE l.pid=pg_backend_pid() AND l.granted AND l.locktype='relation' "
                    + "AND l.mode='ShareRowExclusiveLock' AND l.relation IN ("
                    + String.join(
                            ",", RELATIONS.stream().map(v -> "'" + v + "'::regclass").toList())
                    + ")";

    private record Prepared(
            String providerId,
            ClaimNameCatalogPort.VersionedSelection expected,
            FenceStamp stamp) {}

    private final JdbcTemplate jdbc;
    private final ClaimNameCatalogPort catalog;

    public JdbcBoundSourceClaimIssuanceFence(JdbcTemplate jdbc, ClaimNameCatalogPort catalog) {
        this.jdbc = Objects.requireNonNull(jdbc);
        this.catalog = Objects.requireNonNull(catalog);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public FenceStamp prepare(String providerId, ClaimNameCatalogPort.VersionedSelection expected) {
        try {
            transaction();
            if (!TransactionSynchronizationManager.isSynchronizationActive()
                    || TransactionSynchronizationManager.hasResource(this)) throw invalid();
            Objects.requireNonNull(expected);
            jdbc.execute(
                    "LOCK TABLE " + String.join(",", RELATIONS) + " IN SHARE ROW EXCLUSIVE MODE");
            jdbc.execute(
                    "LOCK TABLE public.hidra_identity_external_mapping_generation IN ACCESS SHARE"
                        + " MODE");
            if (!expected.equals(catalog.selectVersioned(providerId))) throw invalid();
            FenceStamp stamp =
                    jdbc.queryForObject(
                            "SELECT pg_backend_pid(),txid_current()",
                            (rs, n) -> new FenceStamp(rs.getInt(1), rs.getLong(2)));
            TransactionSynchronizationManager.bindResource(
                    this, new Prepared(providerId, expected, stamp));
            TransactionSynchronizationManager.registerSynchronization(
                    new TransactionSynchronization() {
                        @Override
                        public void afterCompletion(int status) {
                            TransactionSynchronizationManager.unbindResourceIfPossible(
                                    JdbcBoundSourceClaimIssuanceFence.this);
                        }
                    });
            requireCurrent(stamp);
            return stamp;
        } catch (RuntimeException failure) {
            throw invalid();
        }
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void requireCurrent(FenceStamp stamp) {
        try {
            transaction();
            Objects.requireNonNull(stamp);
            if (!(TransactionSynchronizationManager.getResource(this) instanceof Prepared prepared)
                    || !stamp.equals(prepared.stamp())) throw invalid();
            if (!Boolean.TRUE.equals(
                            jdbc.queryForObject(
                                    "SELECT pg_backend_pid()=? AND txid_current()=?",
                                    Boolean.class,
                                    stamp.backendPid(),
                                    stamp.transactionId()))
                    || !Integer.valueOf(RELATIONS.size())
                            .equals(jdbc.queryForObject(HELD, Integer.class))
                    || !Boolean.TRUE.equals(
                            jdbc.queryForObject(
                                    "SELECT EXISTS(SELECT 1 FROM pg_catalog.pg_locks WHERE"
                                        + " pid=pg_backend_pid() AND granted AND"
                                        + " locktype='relation' AND mode='AccessShareLock' AND"
                                        + " relation='public.hidra_identity_external_mapping_generation'::regclass)",
                                    Boolean.class))) throw invalid();
            // Other transactions are fenced; a same-transaction writer must also invalidate
            // capture.
            if (!prepared.expected().equals(catalog.selectVersioned(prepared.providerId())))
                throw invalid();
        } catch (RuntimeException failure) {
            throw invalid();
        }
    }

    private void transaction() {
        if (!TransactionSynchronizationManager.isActualTransactionActive()
                || TransactionSynchronizationManager.isCurrentTransactionReadOnly()
                || !Boolean.TRUE.equals(
                        jdbc.queryForObject(
                                "SELECT current_setting('transaction_isolation')='read committed' "
                                        + "AND current_setting('transaction_read_only')='off'",
                                Boolean.class))) throw invalid();
    }

    private static IllegalStateException invalid() {
        return new IllegalStateException("Bound source-claim issuance fence unavailable.");
    }
}
