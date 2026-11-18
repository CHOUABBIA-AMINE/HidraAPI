/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : JdbcControlledDocumentRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-18
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.infrastructure.persistence.adapter
 * @Description : Serializable scoped document identity and append-only custodian receipt persistence.
 */
package dz.sh.hidra.modules.documents.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.documents.application.port.out.ControlledDocumentRepositoryPort;
import dz.sh.hidra.modules.documents.application.port.in.ControlledDocumentsUseCase.*;
import dz.sh.hidra.modules.documents.domain.model.ControlledDocumentDecision;
import jakarta.persistence.EntityManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Objects;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class JdbcControlledDocumentRepositoryAdapter implements ControlledDocumentRepositoryPort {
    private static final String SCOPE="hidra_documents_controlled_scope";
    private static final String DECISION="hidra_documents_controlled_decision";
    private static final String DOCUMENT="hidra_documents_document";
    private static final String VERSION="hidra_documents_document_version";
    private static final String LINK="hidra_documents_target_link";
    private static final String STORAGE="hidra_documents_storage_object";
    private final JdbcTemplate jdbc;
    private final EntityManager em;

    public JdbcControlledDocumentRepositoryAdapter(JdbcTemplate jdbc,EntityManager em) {
        this.jdbc=Objects.requireNonNull(jdbc);
        this.em=Objects.requireNonNull(em);
    }
    private static Instant instant(ResultSet rs,String key)throws SQLException {
        var timestamp=rs.getTimestamp(key);
        return timestamp==null?null:timestamp.toInstant();
    }
    private static Scope scope(ResultSet rs,int row)throws SQLException {
        return new Scope(rs.getString("document_id"),rs.getString("company_id"),
                rs.getString("facility_id"),rs.getString("create_request_id"),
                rs.getString("payload_digest"),rs.getString("created_by_actor_id"),
                instant(rs,"created_at"));
    }
    private static DocumentView document(ResultSet rs,int row)throws SQLException {
        return new DocumentView(rs.getString("id"),rs.getString("code"),
                rs.getString("title_fr"),rs.getString("company_id"),
                rs.getString("facility_id"),rs.getString("status"),
                rs.getString("current_version_id"),rs.getString("current_checksum"),
                instant(rs,"created_at"));
    }
    private static VersionView version(ResultSet rs,int row)throws SQLException {
        return new VersionView(rs.getString("id"),rs.getInt("version_number"),
                rs.getString("version_status"),rs.getString("checksum_value"),
                rs.getLong("file_size_bytes"),rs.getString("uploaded_by_actor_id"),
                instant(rs,"uploaded_at"),rs.getString("superseded_by_version_id"));
    }
    private static LinkView link(ResultSet rs,int row)throws SQLException {
        return new LinkView(rs.getString("id"),rs.getString("document_version_id"),
                rs.getString("target_module"),rs.getString("target_type_code"),
                rs.getString("target_id"),rs.getString("link_role_id"),
                rs.getBoolean("primary_link"),instant(rs,"linked_at"));
    }
    private static ControlledDocumentDecision decision(ResultSet rs,int row)throws SQLException {
        return new ControlledDocumentDecision(rs.getString("id"),rs.getString("document_id"),
                rs.getString("version_id"),rs.getString("previous_version_id"),
                rs.getString("request_id"),rs.getString("payload_digest"),
                rs.getString("actor_id"),rs.getString("reason"),rs.getString("action"),
                rs.getString("checksum_sha256"),rs.getLong("content_length"),
                rs.getString("company_id"),rs.getString("facility_id"),
                rs.getString("link_role_id"),rs.getString("link_id"),instant(rs,"decided_at"));
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public void serializeCreate(String company,String facility,String requestId){
        jdbc.queryForObject("SELECT pg_advisory_xact_lock(hashtextextended(?::text,0))",
                Object.class,company+"|"+facility+"|"+requestId);
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Optional<Scope> findCreated(String company,String facility,String requestId){
        var all=jdbc.query("SELECT * FROM "+SCOPE+" WHERE company_id=? AND facility_id=? AND create_request_id=?",
                JdbcControlledDocumentRepositoryAdapter::scope,company,facility,requestId);
        return all.stream().findFirst();
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public void bind(Scope scope){
        em.flush();
        jdbc.update("INSERT INTO "+SCOPE+" (document_id,company_id,facility_id,create_request_id,payload_digest,created_by_actor_id,created_at) VALUES (?,?,?,?,?,?,?)",
                scope.documentId(),scope.companyId(),scope.facilityId(),scope.requestId(),
                scope.payloadDigest(),scope.createdByActorId(),java.sql.Timestamp.from(scope.createdAt()));
        em.clear();
    }
    @Override public Optional<Scope> byDocument(String id){
        if(id==null || id.isBlank())return Optional.empty();
        return jdbc.query("SELECT * FROM "+SCOPE+" WHERE document_id=?",
                JdbcControlledDocumentRepositoryAdapter::scope,id).stream().findFirst();
    }
    @Override public Optional<Scope> byVersion(String id){
        if(id==null || id.isBlank())return Optional.empty();
        return jdbc.query("SELECT s.* FROM "+SCOPE+" s JOIN "+VERSION+
                " v ON v.document_id=s.document_id WHERE v.id=?",
                JdbcControlledDocumentRepositoryAdapter::scope,id).stream().findFirst();
    }
    @Override public Optional<Scope> byStorage(String id){
        if(id==null||id.isBlank())return Optional.empty();
        var all=jdbc.query("SELECT DISTINCT s.* FROM "+SCOPE+" s JOIN "+VERSION+
                " v ON v.document_id=s.document_id WHERE v.storage_object_id=? LIMIT 2",
                JdbcControlledDocumentRepositoryAdapter::scope,id);
        if(all.size()>1)throw new NoSuchElementException("Protected Documents storage scope unavailable.");
        return all.stream().findFirst();
    }
    private static String projection(){
        return "SELECT d.id,d.code,d.title_fr,d.status,d.current_version_id,d.created_at,"+
                "s.company_id,s.facility_id,v.checksum_value AS current_checksum FROM "+
                DOCUMENT+" d JOIN "+SCOPE+" s ON s.document_id=d.id "+
                "LEFT JOIN "+VERSION+" v ON v.id=d.current_version_id ";
    }
    @Override public Page<DocumentView> discover(String company,String facility,int page,int size,String code){
        // Keep both owner predicates mandatory. PostgreSQL cannot type a bare null
        // JDBC parameter in "? IS NULL"; bind the optional prefix only when present.
        String fragment=" WHERE s.company_id=? AND s.facility_id=?";
        var args=new java.util.ArrayList<Object>(List.of(company,facility));
        if(code!=null&&!code.isBlank()){
            fragment+=" AND d.code LIKE ?";
            args.add(code.trim()+"%");
        }
        long total=jdbc.queryForObject("SELECT count(*) FROM "+DOCUMENT+
                " d JOIN "+SCOPE+" s ON s.document_id=d.id"+fragment,
                Long.class,args.toArray());
        args.add(size);
        args.add(page*size);
        var items=jdbc.query(projection()+fragment+" ORDER BY d.code,d.id LIMIT ? OFFSET ?",
                JdbcControlledDocumentRepositoryAdapter::document,args.toArray());
        return new Page<>(items,total,page,size);
    }
    @Override public Optional<DocumentView> detail(String documentId){
        return jdbc.query(projection()+" WHERE d.id=?",
                JdbcControlledDocumentRepositoryAdapter::document,documentId).stream().findFirst();
    }
    @Override public Page<VersionView> versions(String id,int page,int size){
        long total=jdbc.queryForObject("SELECT count(*) FROM "+VERSION+" WHERE document_id=?",
                Long.class,id);
        var items=jdbc.query("SELECT id,version_number,version_status,checksum_value,file_size_bytes,"+
                "uploaded_by_actor_id,uploaded_at,superseded_by_version_id FROM "+VERSION+
                " WHERE document_id=? ORDER BY version_number,id LIMIT ? OFFSET ?",
                JdbcControlledDocumentRepositoryAdapter::version,id,size,page*size);
        return new Page<>(items,total,page,size);
    }
    @Override public Page<LinkView> links(String id,int page,int size){
        long total=jdbc.queryForObject("SELECT count(*) FROM "+LINK+" WHERE document_id=?"+
                " AND document_version_id IS NOT NULL",Long.class,id);
        var items=jdbc.query("SELECT id,document_version_id,target_module,target_type_code,target_id,"+
                "link_role_id,primary_link,linked_at FROM "+LINK+
                " WHERE document_id=? AND document_version_id IS NOT NULL ORDER BY linked_at,id LIMIT ? OFFSET ?",
                JdbcControlledDocumentRepositoryAdapter::link,id,size,page*size);
        return new Page<>(items,total,page,size);
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public void lockDocument(String documentId) {
        var locked=jdbc.query("SELECT id FROM "+DOCUMENT+" WHERE id=? FOR UPDATE",
                (rs,row)->rs.getString(1),documentId);
        if(locked.size()!=1)throw new NoSuchElementException("Protected Documents resource unavailable.");
        em.clear();
    }
    @Override public Optional<ControlledDocumentDecision> decision(String documentId,String requestId){
        return jdbc.query("SELECT * FROM "+DECISION+" WHERE document_id=? AND request_id=?",
                JdbcControlledDocumentRepositoryAdapter::decision,documentId,requestId)
                .stream().findFirst();
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public void accept(ControlledDocumentDecision receipt,String code,String label){
        em.flush();
        String documentId=receipt.documentId(),newVersion=receipt.versionId();
        String before=receipt.previousVersionId();
        if(before!=null){
            if(jdbc.update("UPDATE "+VERSION+" SET version_status='SUPERSEDED',superseded_by_version_id=?"+
                    " WHERE id=? AND document_id=? AND version_status='CURRENT'",
                    newVersion,before,documentId)!=1)throw conflict();
        }
        if(jdbc.update("UPDATE "+VERSION+" SET version_status='CURRENT'"+
                " WHERE id=? AND document_id=? AND version_status='DRAFT'",
                newVersion,documentId)!=1)throw conflict();
        int update= before==null
            ?jdbc.update("UPDATE "+DOCUMENT+" SET current_version_id=?,status='ACTIVE',updated_at=?"+
                " WHERE id=? AND current_version_id IS NULL AND status='DRAFT'",
                newVersion,java.sql.Timestamp.from(receipt.decidedAt()),documentId)
            :jdbc.update("UPDATE "+DOCUMENT+" SET current_version_id=?,status='ACTIVE',updated_at=?"+
                " WHERE id=? AND current_version_id=? AND status='ACTIVE'",
                newVersion,java.sql.Timestamp.from(receipt.decidedAt()),documentId,before);
        if(update!=1)throw conflict();
        jdbc.update("INSERT INTO "+LINK+" (id,document_id,document_version_id,target_module,"+
                "target_type_code,target_id,target_code_snapshot,target_label_snapshot,link_role_id,"+
                "primary_link,linked_by_actor_id,linked_at,unlinked_at,active)"+
                " VALUES (?,?,?,'topology','FACILITY',?,?,?,?,true,?,?,NULL,true)",
                receipt.linkId(),documentId,newVersion,receipt.facilityId(),code,label,
                receipt.linkRoleId(),receipt.actorId(),java.sql.Timestamp.from(receipt.decidedAt()));
        jdbc.update("INSERT INTO "+DECISION+" (id,document_id,version_id,previous_version_id,"+
                "request_id,payload_digest,actor_id,reason,action,checksum_sha256,content_length,"+
                "company_id,facility_id,link_role_id,link_id,decided_at)"+
                " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                receipt.id(),documentId,newVersion,before,receipt.requestId(),receipt.payloadDigest(),
                receipt.actorId(),receipt.reason(),receipt.action(),receipt.checksumSha256(),
                receipt.contentLength(),receipt.companyId(),receipt.facilityId(),receipt.linkRoleId(),
                receipt.linkId(),java.sql.Timestamp.from(receipt.decidedAt()));
        em.clear();
    }
    private static IllegalStateException conflict(){
        return new IllegalStateException("Controlled document version transition conflict.");
    }
}
