/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JdbcSimulationEquipmentParameterRevisionRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-10
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.infrastructure.persistence.adapter
 *
 * @Description : Maintains immutable governed equipment source evidence.
 *
 */
package dz.sh.hidra.modules.simulation.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.simulation.application.port.out.SimulationEquipmentParameterRevisionRepositoryPort;
import dz.sh.hidra.modules.simulation.domain.exception.InvalidSimulationValueException;
import dz.sh.hidra.modules.simulation.domain.model.SimulationEquipmentParameterRevision;
import dz.sh.hidra.modules.simulation.domain.model.SimulationEquipmentParameterRevision.*;
import java.util.Arrays;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class JdbcSimulationEquipmentParameterRevisionRepositoryAdapter implements SimulationEquipmentParameterRevisionRepositoryPort {
    private final JdbcTemplate jdbc;
    private final SimulationEquipmentParameterRevisionCodec codec = new SimulationEquipmentParameterRevisionCodec();
    public JdbcSimulationEquipmentParameterRevisionRepositoryAdapter(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }
    @Override @Transactional
    public SimulationEquipmentParameterRevision append(SimulationEquipmentParameterRevision v) {
        if (v == null) throw invalid("Missing gas source revision.");
        for(var c:v.compressorCurves())appendCharacteristic(c);
        for(var c:v.valveCharacteristics())appendCharacteristic(c);
        byte[] bytes = codec.encode(v); String sha = codec.sha256(bytes);
        insert("hidra_simulation_equipment_parameter_revision", "source_id,revision_id", new Object[]{v.sourceId(), v.revisionId()},
                SimulationEquipmentParameterRevisionCodec.FORMAT, bytes, sha);
        var winner = findStored(v.sourceId(), v.revisionId()).orElseThrow(() -> invalid("Missing appended source."));
        identical(bytes, codec.encode(winner.revision())); return winner.revision();
    }
    @Override @Transactional public CompressorCurve appendCharacteristic(CompressorCurve v){
        byte[] bytes=codec.encodeCompressorCurve(v);
        jdbc.update("INSERT INTO hidra_simulation_equipment_characteristic_revision (kind,characteristic_id,revision_id,payload_format,canonical_payload,sha256) VALUES (?,?,?,?,?,?) ON CONFLICT (kind,characteristic_id,revision_id) DO NOTHING",
                "COMPRESSOR",v.id(),v.revisionId(),SimulationEquipmentParameterRevisionCodec.CURVE_FORMAT,bytes,codec.sha256(bytes));
        var winner=findCompressorCurveStored(v.id(),v.revisionId()).orElseThrow(()->invalid("Missing appended characteristic."));identical(bytes,codec.encodeCompressorCurve(winner));return winner;
    }
    @Override @Transactional(readOnly=true) public Optional<CompressorCurve> findCompressorCurveStored(String id,String revision){
        return jdbc.query("SELECT * FROM hidra_simulation_equipment_characteristic_revision WHERE kind=? AND characteristic_id=? AND revision_id=?",(r,n)->{
            var v=codec.decodeCompressorCurve(verified(r.getString("payload_format"),r.getBytes("canonical_payload"),r.getString("sha256"),SimulationEquipmentParameterRevisionCodec.CURVE_FORMAT));
            if(!"COMPRESSOR".equals(r.getString("kind"))||!v.id().equals(r.getString("characteristic_id"))||!v.revisionId().equals(r.getString("revision_id")))throw invalid("Characteristic row key integrity failed.");return v;
        },"COMPRESSOR",identity(id),identity(revision)).stream().findFirst();
    }
    @Override @Transactional public ValveCharacteristic appendCharacteristic(ValveCharacteristic v){
        byte[] bytes=codec.encodeValveCharacteristic(v);
        jdbc.update("INSERT INTO hidra_simulation_equipment_characteristic_revision (kind,characteristic_id,revision_id,payload_format,canonical_payload,sha256) VALUES (?,?,?,?,?,?) ON CONFLICT (kind,characteristic_id,revision_id) DO NOTHING",
                "VALVE",v.id(),v.revisionId(),SimulationEquipmentParameterRevisionCodec.VALVE_FORMAT,bytes,codec.sha256(bytes));
        var winner=findValveCharacteristicStored(v.id(),v.revisionId()).orElseThrow(()->invalid("Missing appended characteristic."));identical(bytes,codec.encodeValveCharacteristic(winner));return winner;
    }
    @Override @Transactional(readOnly=true) public Optional<ValveCharacteristic> findValveCharacteristicStored(String id,String revision){
        return jdbc.query("SELECT * FROM hidra_simulation_equipment_characteristic_revision WHERE kind=? AND characteristic_id=? AND revision_id=?",(r,n)->{
            var v=codec.decodeValveCharacteristic(verified(r.getString("payload_format"),r.getBytes("canonical_payload"),r.getString("sha256"),SimulationEquipmentParameterRevisionCodec.VALVE_FORMAT));
            if(!"VALVE".equals(r.getString("kind"))||!v.id().equals(r.getString("characteristic_id"))||!v.revisionId().equals(r.getString("revision_id")))throw invalid("Characteristic row key integrity failed.");return v;
        },"VALVE",identity(id),identity(revision)).stream().findFirst();
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
        return jdbc.query("SELECT * FROM hidra_simulation_equipment_parameter_revision WHERE " + predicate, (r, n) -> {
            String format = r.getString("payload_format"); String sha = r.getString("sha256");
            var v = codec.decode(verified(format, r.getBytes("canonical_payload"), sha, SimulationEquipmentParameterRevisionCodec.FORMAT));
            if (!v.sourceId().equals(r.getString("source_id")) || !v.revisionId().equals(r.getString("revision_id")))
                throw invalid("Source row identity differs from canonical identity.");
            for(var c:v.compressorCurves())identical(codec.encodeCompressorCurve(c),codec.encodeCompressorCurve(findCompressorCurveStored(c.id(),c.revisionId()).orElseThrow(()->invalid("Missing immutable curve."))));
            for(var c:v.valveCharacteristics())identical(codec.encodeValveCharacteristic(c),codec.encodeValveCharacteristic(findValveCharacteristicStored(c.id(),c.revisionId()).orElseThrow(()->invalid("Missing immutable valve."))));
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
        jdbc.update("INSERT INTO hidra_simulation_equipment_parameter_qualification "
                + "(qualification_id,source_id,revision_id,payload_format,canonical_payload,sha256) VALUES (?,?,?,?,?,?) "
                + "ON CONFLICT (qualification_id) DO NOTHING", q.qualificationId(), q.sourceId(), q.revisionId(),
                SimulationEquipmentParameterRevisionCodec.QUALIFICATION_FORMAT, bytes, codec.sha256(bytes));
        var winner = findQualification(q.qualificationId()).orElseThrow(() -> invalid("Missing appended qualification."));
        identical(bytes, codec.encodeQualification(winner)); return winner;
    }
    @Override @Transactional(readOnly = true)
    public Optional<Qualification> findQualification(String id) {
        return jdbc.query("SELECT * FROM hidra_simulation_equipment_parameter_qualification WHERE qualification_id=?", (r, n) -> {
            var q = codec.decodeQualification(verified(r.getString("payload_format"), r.getBytes("canonical_payload"),
                    r.getString("sha256"), SimulationEquipmentParameterRevisionCodec.QUALIFICATION_FORMAT));
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
    private static InvalidSimulationValueException invalid(String m) { return new InvalidSimulationValueException(m); }
}
