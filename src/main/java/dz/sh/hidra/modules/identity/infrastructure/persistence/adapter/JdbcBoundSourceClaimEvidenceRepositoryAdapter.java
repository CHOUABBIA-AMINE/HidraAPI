/**
 * @Project : HidraAPI @Product : Hidra - Hydrocarbon Intelligence for Data, Risk, and
 * Analytics @Author : Abir MEDJERAB @Owner : Sonatrach / TRC : Digitalization Initiative @Name :
 * JdbcBoundSourceClaimEvidenceRepositoryAdapter @CreatedOn : 2025-06-26 @UpdatedOn :
 * 2026-10-17 @Type : Class @Layer : Infrastructure @Module : identity @Package :
 * dz.sh.hidra.modules.identity.infrastructure.persistence.adapter @Description : Persists immutable
 * bounded claim companions with redacted structural readback.
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.identity.application.port.out.BoundSourceClaimEvidenceRepositoryPort;
import dz.sh.hidra.modules.identity.domain.value.BoundSourceClaimEvidence;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/** Parameterized structural storage. Trusted owner verification is independently required. */
@Repository
public class JdbcBoundSourceClaimEvidenceRepositoryAdapter
        implements BoundSourceClaimEvidenceRepositoryPort {
    private static final String TABLE = "public.hidra_identity_bound_source_claim_evidence";
    private final JdbcTemplate jdbc;

    public JdbcBoundSourceClaimEvidenceRepositoryAdapter(JdbcTemplate jdbc) {
        this.jdbc = Objects.requireNonNull(jdbc);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void append(BoundSourceClaimEvidence evidence) {
        try {
            if (!TransactionSynchronizationManager.isActualTransactionActive()
                    || TransactionSynchronizationManager.isCurrentTransactionReadOnly()
                    || !Boolean.TRUE.equals(
                            jdbc.queryForObject(
                                    "SELECT current_setting('transaction_isolation')='read"
                                        + " committed' AND"
                                        + " current_setting('transaction_read_only')='off'",
                                    Boolean.class))) throw invalid();
            Objects.requireNonNull(evidence);
            jdbc.update(
                    "INSERT INTO "
                            + TABLE
                            + " (binding_id,evidence_format,mapping_generation,issuance_transaction_id,coverage_payload,producer_authenticator)"
                            + " VALUES (?,?,?,?,?,?) ON CONFLICT DO NOTHING",
                    evidence.bindingId(),
                    evidence.evidenceFormat(),
                    evidence.mappingGeneration(),
                    evidence.issuanceTransactionId(),
                    BoundSourceClaimEvidenceCodec.encode(evidence.coverage()),
                    evidence.producerAuthenticator());
            if (!Optional.of(evidence).equals(findByBindingId(evidence.bindingId())))
                throw invalid();
        } catch (RuntimeException failure) {
            throw invalid();
        }
    }

    @Override
    public Optional<BoundSourceClaimEvidence> findByBindingId(String bindingId) {
        try {
            if (bindingId == null || !UUID.fromString(bindingId).toString().equals(bindingId))
                throw invalid();
            var rows =
                    jdbc.query(
                            "SELECT"
                                + " binding_id,evidence_format,mapping_generation,issuance_transaction_id,coverage_payload,producer_authenticator"
                                + " FROM "
                                    + TABLE
                                    + " WHERE binding_id=?",
                            (rs, n) ->
                                    new BoundSourceClaimEvidence(
                                            rs.getString(1),
                                            rs.getShort(2),
                                            rs.getLong(3),
                                            rs.getLong(4),
                                            BoundSourceClaimEvidenceCodec.decode(rs.getBytes(5)),
                                            rs.getBytes(6)),
                            bindingId);
            if (rows.size() > 1) throw invalid();
            return rows.stream().findFirst();
        } catch (RuntimeException failure) {
            throw invalid();
        }
    }

    private static IllegalStateException invalid() {
        return new IllegalStateException("Bound source-claim evidence unavailable.");
    }
}
