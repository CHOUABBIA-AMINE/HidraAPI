/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JdbcCustodyGasFluidRevisionRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-10
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.infrastructure.persistence.adapter
 *
 * @Description : Atomically appends immutable gas sources and verifies canonical row and nested-method integrity.
 *
 */
package dz.sh.hidra.modules.custody.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.custody.application.contract.simulation.SimulationProductCandidateContract;
import dz.sh.hidra.modules.custody.application.port.out.CustodyGasFluidRevisionRepositoryPort;
import dz.sh.hidra.modules.custody.domain.exception.InvalidCustodyValueException;
import dz.sh.hidra.modules.custody.domain.model.CustodyGasFluidRevision;
import dz.sh.hidra.modules.custody.domain.model.CustodyGasFluidRevision.Method;
import java.util.Arrays;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class JdbcCustodyGasFluidRevisionRepositoryAdapter implements CustodyGasFluidRevisionRepositoryPort {
    private final JdbcTemplate jdbc;
    private final SimulationProductCandidateContract products;
    private final CustodyGasFluidRevisionCodec codec = new CustodyGasFluidRevisionCodec();
    public JdbcCustodyGasFluidRevisionRepositoryAdapter(JdbcTemplate jdbc, SimulationProductCandidateContract products) {
        this.jdbc = jdbc; this.products = products;
    }
    @Override @Transactional
    public CustodyGasFluidRevision append(CustodyGasFluidRevision v) {
        if (v == null) throw invalid("Missing gas source revision.");
        var snapshot = v.productSnapshot(); var p = products.resolve(snapshot.id()).orElse(null);
        if (p == null || !p.active() || !snapshot.id().equals(p.id()) || !snapshot.catalogName().equals(p.catalogName())
                || !snapshot.code().equals(p.code()) || snapshot.active() != p.active()
                || !snapshot.createdAt().equals(p.createdAt()) || !snapshot.updatedAt().equals(p.updatedAt()))
            throw invalid("Source product snapshot differs from the current Custody candidate.");
        appendMethod(v.method());
        byte[] bytes = codec.encode(v); String sha = codec.sha256(bytes);
        insert("hidra_custody_gas_fluid_revision", "source_id,revision_id", new Object[]{v.sourceId(), v.revisionId()},
                CustodyGasFluidRevisionCodec.FORMAT, bytes, sha);
        var winner = findStored(v.sourceId(), v.revisionId()).orElseThrow(() -> invalid("Missing appended source."));
        identical(bytes, codec.encode(winner.revision())); return winner.revision();
    }
    @Override @Transactional
    public Method appendMethod(Method v) {
        byte[] bytes = codec.encodeMethod(v);
        insert("hidra_custody_gas_method_revision", "method_reference,revision_id", new Object[]{v.reference(), v.revisionId()},
                CustodyGasFluidRevisionCodec.METHOD_FORMAT, bytes, codec.sha256(bytes));
        var winner = findMethodStored(v.reference(), v.revisionId()).orElseThrow(() -> invalid("Missing appended method."));
        identical(bytes, codec.encodeMethod(winner)); return winner;
    }
    @Override @Transactional(readOnly = true)
    public Optional<Method> findMethodStored(String reference, String revisionId) {
        String key = identity(reference); String rev = identity(revisionId);
        return jdbc.query("SELECT * FROM hidra_custody_gas_method_revision WHERE method_reference=? AND revision_id=?",
                (r, n) -> { byte[] bytes = verified(r.getString("payload_format"), r.getBytes("canonical_payload"),
                    r.getString("sha256"), CustodyGasFluidRevisionCodec.METHOD_FORMAT);
                    var v = codec.decodeMethod(bytes);
                    if (!v.reference().equals(r.getString("method_reference")) || !v.revisionId().equals(r.getString("revision_id")))
                        throw invalid("Method row identity differs from canonical identity."); return v;
                }, key, rev).stream().findFirst();
    }
    @Override @Transactional(readOnly = true)
    public Optional<StoredRevision> findStored(String source, String revision) {
        return read("source_id=? AND revision_id=?", identity(source), identity(revision));
    }
    @Override @Transactional(readOnly = true)
    public Optional<StoredRevision> findByApprovalTargetId(String digest) {
        if (digest == null || !digest.matches("[0-9a-f]{64}")) return Optional.empty();
        return read("sha256=?", digest);
    }
    private Optional<StoredRevision> read(String predicate, Object... args) {
        return jdbc.query("SELECT * FROM hidra_custody_gas_fluid_revision WHERE " + predicate, (r, n) -> {
            String format = r.getString("payload_format"); String sha = r.getString("sha256");
            var v = codec.decode(verified(format, r.getBytes("canonical_payload"), sha, CustodyGasFluidRevisionCodec.FORMAT));
            if (!v.sourceId().equals(r.getString("source_id")) || !v.revisionId().equals(r.getString("revision_id")))
                throw invalid("Source row identity differs from canonical identity.");
            var method = findMethodStored(v.method().reference(), v.method().revisionId()).orElseThrow(() -> invalid("Missing immutable method."));
            identical(codec.encodeMethod(v.method()), codec.encodeMethod(method));
            return new StoredRevision(v, format, sha);
        }, args).stream().findFirst();
    }
    @Override @Transactional
    public Qualification appendQualification(String source, String revision, Qualification q) {
        if (q == null || !identity(source).equals(q.sourceId()) || !identity(revision).equals(q.revisionId()))
            throw invalid("Qualification must name its exact source revision.");
        var stored = findStored(source, revision).orElseThrow(() -> invalid("Missing qualification source."));
        if (!stored.sha256().equals(q.payloadSha256())) throw invalid("Qualification digest differs from source.");
        byte[] bytes = codec.encodeQualification(q);
        jdbc.update("INSERT INTO hidra_custody_gas_fluid_qualification "
                + "(qualification_id,source_id,revision_id,payload_format,canonical_payload,sha256) VALUES (?,?,?,?,?,?) "
                + "ON CONFLICT (qualification_id) DO NOTHING", q.qualificationId(), q.sourceId(), q.revisionId(),
                CustodyGasFluidRevisionCodec.QUALIFICATION_FORMAT, bytes, codec.sha256(bytes));
        var winner = findQualification(q.qualificationId()).orElseThrow(() -> invalid("Missing appended qualification."));
        identical(bytes, codec.encodeQualification(winner)); return winner;
    }
    @Override @Transactional(readOnly = true)
    public Optional<Qualification> findQualification(String id) {
        return jdbc.query("SELECT * FROM hidra_custody_gas_fluid_qualification WHERE qualification_id=?", (r, n) -> {
            var q = codec.decodeQualification(verified(r.getString("payload_format"), r.getBytes("canonical_payload"),
                    r.getString("sha256"), CustodyGasFluidRevisionCodec.QUALIFICATION_FORMAT));
            if (!q.qualificationId().equals(r.getString("qualification_id")) || !q.sourceId().equals(r.getString("source_id"))
                    || !q.revisionId().equals(r.getString("revision_id"))) throw invalid("Qualification key integrity failed.");
            var s = findStored(q.sourceId(), q.revisionId()).orElseThrow(() -> invalid("Missing qualification source."));
            if (!s.sha256().equals(q.payloadSha256())) throw invalid("Qualification/source integrity failed."); return q;
        }, identity(id)).stream().findFirst();
    }
    private void insert(String table, String keys, Object[] ids, String format, byte[] bytes, String sha) {
        jdbc.update("INSERT INTO " + table + " (" + keys + ",payload_format,canonical_payload,sha256) VALUES (?,?,?,?,?) "
                + "ON CONFLICT (" + keys + ") DO NOTHING", ids[0], ids[1], format, bytes, sha);
    }
    private byte[] verified(String format, byte[] bytes, String digest, String expected) {
        if (!expected.equals(format) || bytes == null || !codec.sha256(bytes).equals(digest)) throw invalid("Stored gas evidence integrity failed.");
        return bytes;
    }
    private static void identical(byte[] expected, byte[] actual) {
        if (!Arrays.equals(expected, actual)) throw invalid("Conflicting immutable identity content.");
    }
    private static String identity(String s) { if (s == null || s.isBlank()) throw invalid("Nonblank exact identity required."); return s.trim(); }
    private static InvalidCustodyValueException invalid(String m) { return new InvalidCustodyValueException(m); }
}
