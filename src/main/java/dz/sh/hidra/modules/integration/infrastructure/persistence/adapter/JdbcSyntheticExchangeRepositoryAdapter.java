/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : JdbcSyntheticExchangeRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-18
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.infrastructure.persistence.adapter
 * @Description : Fenced Integration-owned native run, message, inbound, dead-letter and exchange receipts.
 */

package dz.sh.hidra.modules.integration.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.integration.application.port.out.SyntheticExchangeRepositoryPort;
import dz.sh.hidra.modules.integration.domain.model.SyntheticExchange;
import dz.sh.hidra.modules.integration.domain.model.SyntheticExchange.Attempt;
import dz.sh.hidra.modules.integration.domain.model.SyntheticExchange.Phase;
import dz.sh.hidra.modules.integration.domain.model.SyntheticExchange.Scope;
import dz.sh.hidra.modules.integration.domain.service.SyntheticDocumentDraftMappingV1;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(propagation=Propagation.MANDATORY)
public class JdbcSyntheticExchangeRepositoryAdapter implements SyntheticExchangeRepositoryPort {
    private static final String BINDING="public.hidra_integration_t26_mapping_binding";
    private static final String RECEIPT="public.hidra_integration_t26_exchange_receipt";
    private static final String ATTEMPT="public.hidra_integration_t26_attempt_receipt";
    private final JdbcTemplate jdbc;
    public JdbcSyntheticExchangeRepositoryAdapter(JdbcTemplate jdbc) {
        this.jdbc=Objects.requireNonNull(jdbc);
    }

    @Override public void lockKey(Scope scope,String requestKey) {
        jdbc.queryForObject("SELECT pg_advisory_xact_lock(hashtextextended(?,0))",
                Object.class,"T26:"+scope.companyId()+":"+scope.facilityId()+":"+requestKey);
    }

    @Override public Binding activeBinding(Scope scope,String profileId) {
        var found=jdbc.query("""
                SELECT b.id,p.id,sv.id,sv.checksum,b.document_type_id,b.classification_id,
                       j.id,sys.id,b.message_type_id,b.payload_format_id,
                       sv.schema_definition::text AS schema_source
                FROM public.hidra_integration_t26_mapping_binding b
                JOIN public.hidra_integration_mapping_profile p ON p.id=b.mapping_profile_id
                JOIN public.hidra_integration_schema_version sv ON sv.id=b.schema_version_id
                JOIN public.hidra_integration_data_contract dc ON dc.id=p.data_contract_id
                JOIN public.hidra_integration_job_definition j ON j.id=b.job_definition_id
                JOIN public.hidra_integration_connector_instance ci ON ci.id=j.connector_instance_id
                JOIN public.hidra_integration_external_system sys ON sys.id=b.external_system_id
                JOIN public.hidra_integration_catalog_entry jobType ON jobType.id=j.job_type_id
                JOIN public.hidra_integration_catalog_entry messageType ON messageType.id=b.message_type_id
                JOIN public.hidra_integration_catalog_entry payloadFormat ON payloadFormat.id=b.payload_format_id
                WHERE b.company_id=? AND b.facility_id=? AND p.id=?
                  AND b.status='ACTIVE' AND p.status='ACTIVE' AND p.code='SYNTHETIC_DOCUMENT_DRAFT_V1'
                  AND p.direction='INBOUND' AND p.target_module='documents' AND p.target_type_code='DOCUMENT'
                  AND p.validation_mode='STRICT' AND p.external_system_id=sys.id
                  AND p.schema_version_id=sv.id AND p.data_contract_id=sv.data_contract_id
                  AND dc.id=sv.data_contract_id AND dc.status='ACTIVE'
                  AND dc.owning_target_module='documents' AND dc.payload_format_id=b.payload_format_id
                  AND sv.status='ACTIVE' AND (sv.effective_from IS NULL OR sv.effective_from<=now())
                  AND (sv.effective_to IS NULL OR sv.effective_to>now())
                  AND sys.environment='SANDBOX' AND sys.status='ACTIVE'
                  AND ci.external_system_id=sys.id AND ci.active
                  AND ci.direction='INBOUND' AND j.connector_instance_id=ci.id
                  AND j.mapping_profile_id=p.id AND j.active AND j.manual_run_allowed
                  AND j.direction='INBOUND' AND j.target_module='documents'
                  AND jobType.catalog_name='JOB_TYPE' AND jobType.code='IMPORT' AND jobType.active
                  AND messageType.catalog_name='MESSAGE_TYPE' AND messageType.active
                  AND payloadFormat.catalog_name='PAYLOAD_FORMAT' AND payloadFormat.active
                  AND (SELECT count(*) FROM public.hidra_integration_field_mapping f
                        WHERE f.mapping_profile_id=p.id AND f.active)=1
                  AND EXISTS(SELECT 1 FROM public.hidra_integration_field_mapping f
                        WHERE f.mapping_profile_id=p.id AND f.active
                          AND f.source_path='titleFr' AND f.target_path='titleFr'
                          AND f.data_type='STRING' AND f.required
                          AND f.default_value IS NULL AND f.unit_code IS NULL
                          AND f.transformation_rule_id IS NULL)
                FOR SHARE OF b,p,sv,dc,j,ci,sys,jobType,messageType,payloadFormat
                """,(r,n) -> {
                    String checksum=r.getString(4), schema=r.getString(11);
                    if (checksum==null || !checksum.matches("[a-f0-9]{64}")
                            || !checksum.equals(SyntheticDocumentDraftMappingV1.sha256(schema)))
                        throw conflict();
                    String digest=SyntheticDocumentDraftMappingV1.mappingDigest(r.getString(2),
                            r.getString(3),checksum,r.getString(5),r.getString(6));
                    return new Binding(r.getString(1),r.getString(2),r.getString(3),checksum,
                            r.getString(5),r.getString(6),r.getString(7),r.getString(8),
                            r.getString(9),r.getString(10),digest);
                },scope.companyId(),scope.facilityId(),profileId);
        if (found.size()!=1) throw hidden();
        return found.getFirst();
    }

    @Override public void requireCurrentBinding(SyntheticExchange e) {
        Binding b=activeBinding(e.scope(),e.profileId());
        if (!b.id().equals(e.bindingId()) || !b.schemaVersionId().equals(e.schemaVersionId())
                || !b.schemaChecksum().equals(e.schemaChecksum())
                || !b.mappingDigest().equals(e.mappingDigest())
                || !b.documentTypeId().equals(e.documentTypeId())
                || !b.classificationId().equals(e.classificationId())
                || !b.jobDefinitionId().equals(e.jobDefinitionId())
                || !b.externalSystemId().equals(e.externalSystemId())
                || !b.messageTypeId().equals(e.messageTypeId())
                || !b.payloadFormatId().equals(e.payloadFormatId())) throw conflict();
    }

    @Override public Optional<SyntheticExchange> byKey(Scope scope,String key) {
        return get("request_key",scope,key,false);
    }
    @Override public Optional<SyntheticExchange> byId(Scope scope,String id,boolean lock) {
        return get("id",scope,id,lock);
    }
    private Optional<SyntheticExchange> get(String field,Scope scope,String value,boolean lock) {
        return jdbc.query("SELECT * FROM "+RECEIPT+
                " WHERE company_id=? AND facility_id=? AND "+field+"=?"+(lock?" FOR UPDATE":""),
                (r,n)->exchange(r),scope.companyId(),scope.facilityId(),value).stream().findFirst();
    }
    private static SyntheticExchange exchange(ResultSet r) throws SQLException {
        return new SyntheticExchange(r.getString("id"),
                new Scope(r.getString("company_id"),r.getString("facility_id")),
                r.getString("request_key"),r.getString("submitter_id"),r.getString("binding_id"),
                r.getString("mapping_profile_id"),r.getString("schema_version_id"),
                r.getString("schema_checksum"),r.getString("mapping_digest"),r.getString("payload_digest"),
                r.getString("projection"),r.getString("title_fr"),r.getString("document_type_id"),
                r.getString("classification_id"),r.getString("job_definition_id"),
                r.getString("external_system_id"),r.getString("message_type_id"),
                r.getString("payload_format_id"),Phase.valueOf(r.getString("phase")),
                r.getInt("latest_attempt"),r.getString("document_id"),
                r.getTimestamp("created_at").toInstant());
    }
    @Override public List<Attempt> attempts(String exchangeId) {
        return jdbc.query("SELECT * FROM "+ATTEMPT+" WHERE exchange_id=? ORDER BY attempt_ordinal",
                (r,n)->new Attempt(r.getString("id"),r.getString("exchange_id"),
                    r.getInt("attempt_ordinal"),r.getString("actor_id"),r.getString("request_key"),
                    r.getString("reason"),r.getString("job_run_id"),r.getString("message_id"),
                    r.getString("inbound_id"),r.getString("dead_letter_id"),
                    Phase.valueOf(r.getString("phase")),r.getString("failure_code"),
                    r.getString("document_id"),r.getTimestamp("claimed_at").toInstant(),
                    r.getTimestamp("expires_at").toInstant(),
                    r.getTimestamp("completed_at")==null?null:r.getTimestamp("completed_at").toInstant()),
                exchangeId);
    }
    @Override public void insert(SyntheticExchange e,Attempt a) {
        jdbc.update("""
                INSERT INTO public.hidra_integration_t26_exchange_receipt
                  (id,binding_id,company_id,facility_id,request_key,submitter_id,
                   mapping_profile_id,schema_version_id,schema_checksum,mapping_digest,
                   payload_digest,projection,title_fr,document_type_id,classification_id,
                   job_definition_id,external_system_id,message_type_id,payload_format_id,phase,latest_attempt)
                VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,'CLAIMED',1)
                """,e.id(),e.bindingId(),e.scope().companyId(),e.scope().facilityId(),
                e.requestKey(),e.submitter(),e.profileId(),e.schemaVersionId(),e.schemaChecksum(),
                e.mappingDigest(),e.payloadDigest(),e.projection(),e.titleFr(),e.documentTypeId(),
                e.classificationId(),e.jobDefinitionId(),e.externalSystemId(),
                e.messageTypeId(),e.payloadFormatId());
        nativeClaim(e,a);
        saveAttempt(a);
    }
    @Override public void insertReplay(SyntheticExchange e,Attempt a) {
        if (a.ordinal()!=2 || e.latestAttempt()!=1
                || (e.phase()!=Phase.FAILED && e.phase()!=Phase.ABANDONED))
            throw conflict();
        nativeClaim(e,a);
        saveAttempt(a);
        one(jdbc.update("UPDATE "+RECEIPT+
                " SET phase='CLAIMED',latest_attempt=2,updated_at=now() WHERE id=? AND phase IN ('FAILED','ABANDONED') AND latest_attempt=1",
                e.id()));
    }
    private void nativeClaim(SyntheticExchange e,Attempt a) {
        String external="T26:"+e.id()+":"+a.ordinal();
        jdbc.update("""
                INSERT INTO public.hidra_integration_job_run
                  (id,job_definition_id,run_number,trigger_type,triggered_by_actor_id,status,correlation_id,
                   started_at,received_count,mapped_count,accepted_count,rejected_count,dead_letter_count,retry_count,
                   created_at,updated_at)
                VALUES (?,?,0,'MANUAL',?,'RUNNING',?,now(),1,1,0,0,0,0,now(),now())
                """,a.runId(),e.jobDefinitionId(),a.actor(),external);
        jdbc.update("""
                INSERT INTO public.hidra_integration_exchange_message
                  (id,job_run_id,external_system_id,direction,message_type_id,
                   external_message_id,payload_format_id,payload_storage_mode,payload_sanitized,
                   payload_hash,content_length_bytes,received_or_sent_at,correlation_id,status,created_at)
                VALUES (?,?,?,'INBOUND',?,? ,?,'INLINE_SANITIZED',?::jsonb,?,
                        ?,now(),?,'RECEIVED',now())
                """,a.messageId(),a.runId(),e.externalSystemId(),e.messageTypeId(),
                external,e.payloadFormatId(),e.projection(),e.payloadDigest(),
                (long)e.projection().getBytes(java.nio.charset.StandardCharsets.UTF_8).length,external);
        jdbc.update("""
                INSERT INTO public.hidra_integration_inbound_record
                  (id,exchange_message_id,job_run_id,record_sequence,mapping_profile_id,
                   target_module,target_type_code,mapped_payload,validation_status,
                   submission_status,created_at)
                VALUES (?,?,?,1,?,'documents','DOCUMENT',?::jsonb,'VALID','NOT_SUBMITTED',now())
                """,a.inboundId(),a.messageId(),a.runId(),e.profileId(),e.projection());
    }
    private void saveAttempt(Attempt a) {
        jdbc.update("""
                INSERT INTO public.hidra_integration_t26_attempt_receipt
                  (id,exchange_id,attempt_ordinal,actor_id,request_key,reason,
                   job_run_id,message_id,inbound_id,phase,claimed_at,expires_at)
                VALUES (?,?,?,?,?,?,?,?,?,'CLAIMED',?,?)
                """,a.id(),a.exchangeId(),a.ordinal(),a.actor(),a.requestKey(),a.reason(),
                a.runId(),a.messageId(),a.inboundId(),
                java.sql.Timestamp.from(a.claimedAt()),java.sql.Timestamp.from(a.expiresAt()));
    }
    @Override public void abandon(SyntheticExchange e,Attempt a,String code) {
        one(jdbc.update("UPDATE public.hidra_integration_inbound_record "
                +"SET submission_status='DEAD_LETTERED',error_code=?,completed_at=now() "
                +"WHERE id=? AND submission_status='NOT_SUBMITTED'",code,a.inboundId()));
        one(jdbc.update("UPDATE public.hidra_integration_exchange_message "
                +"SET status='DEAD_LETTERED' WHERE id=? AND status='RECEIVED'",a.messageId()));
        one(jdbc.update("UPDATE public.hidra_integration_job_run "
                +"SET status='FAILED',failure_reason=?,completed_at=now(),updated_at=now() "
                +"WHERE id=? AND status='RUNNING'",code,a.runId()));
        one(jdbc.update("UPDATE "+ATTEMPT+" SET phase='ABANDONED',failure_code=?,completed_at=now() "
                +"WHERE id=? AND phase='CLAIMED'",code,a.id()));
        one(jdbc.update("UPDATE "+RECEIPT+" SET phase='ABANDONED',updated_at=now() "
                +"WHERE id=? AND phase='CLAIMED' AND latest_attempt=?",e.id(),a.ordinal()));
    }
    @Override public void succeeded(SyntheticExchange e,Attempt a,String documentId) {
        one(jdbc.update("UPDATE public.hidra_integration_inbound_record "
                +"SET submission_status='ACCEPTED',target_id=?,target_response_code='DRAFT_CREATED',"
                +"submitted_at=now(),completed_at=now() WHERE id=? AND submission_status='NOT_SUBMITTED'",
                documentId,a.inboundId()));
        one(jdbc.update("UPDATE public.hidra_integration_exchange_message SET status='ACCEPTED' "
                +"WHERE id=? AND status='RECEIVED'",a.messageId()));
        one(jdbc.update("UPDATE public.hidra_integration_job_run "
                +"SET status='COMPLETED',accepted_count=1,completed_at=now(),updated_at=now() "
                +"WHERE id=? AND status='RUNNING'",a.runId()));
        one(jdbc.update("UPDATE "+ATTEMPT+" SET phase='SUCCEEDED',document_id=?,completed_at=now() "
                +"WHERE id=? AND phase='CLAIMED'",documentId,a.id()));
        one(jdbc.update("UPDATE "+RECEIPT+" SET phase='SUCCEEDED',document_id=?,updated_at=now() "
                +"WHERE id=? AND phase='CLAIMED' AND latest_attempt=?",documentId,e.id(),a.ordinal()));
        if (a.ordinal()==2) {
            Attempt old=attempts(e.id()).getFirst();
            if (old.phase()==Phase.ABANDONED) return;
            if (old.phase()!=Phase.FAILED || old.deadLetterId()==null) throw conflict();
            one(jdbc.update("""
                    UPDATE public.hidra_integration_dead_letter_record
                    SET status='REPLAYED',resolved_by_actor_id=?,resolved_at=now(),
                        resolution_comment=?,updated_at=now()
                    WHERE id=? AND status='OPEN'
                    """,a.actor(),a.reason(),old.deadLetterId()));
        }
    }
    @Override public void failed(SyntheticExchange e,Attempt a,String code) {
        String dead=UUID.randomUUID().toString();
        one(jdbc.update("UPDATE public.hidra_integration_inbound_record "
                +"SET submission_status='DEAD_LETTERED',error_code=?,completed_at=now() "
                +"WHERE id=? AND submission_status='NOT_SUBMITTED'",code,a.inboundId()));
        one(jdbc.update("UPDATE public.hidra_integration_exchange_message SET status='DEAD_LETTERED' "
                +"WHERE id=? AND status='RECEIVED'",a.messageId()));
        one(jdbc.update("UPDATE public.hidra_integration_job_run "
                +"SET status='FAILED',dead_letter_count=1,failure_reason=?,completed_at=now(),updated_at=now() "
                +"WHERE id=? AND status='RUNNING'",code,a.runId()));
        jdbc.update("""
                INSERT INTO public.hidra_integration_dead_letter_record
                  (id,external_system_id,job_run_id,exchange_message_id,inbound_record_id,target_module,
                   failure_stage,reason_code,reason_message,payload_hash,sanitized_payload,status,created_at,updated_at)
                VALUES (?,?,?,?,?,'documents','RECEIVER',?,'Synthetic receiver unavailable',?,?::jsonb,'OPEN',now(),now())
                """,dead,e.externalSystemId(),a.runId(),a.messageId(),a.inboundId(),
                code,e.payloadDigest(),e.projection());
        one(jdbc.update("UPDATE "+ATTEMPT+" SET phase='FAILED',failure_code=?,dead_letter_id=?,completed_at=now() "
                +"WHERE id=? AND phase='CLAIMED'",code,dead,a.id()));
        one(jdbc.update("UPDATE "+RECEIPT+" SET phase='FAILED',updated_at=now() "
                +"WHERE id=? AND phase='CLAIMED' AND latest_attempt=?",e.id(),a.ordinal()));
    }
    @Override public boolean isReservedJob(String job,String correlation) {
        return (correlation!=null && correlation.startsWith("T26:"))
          || Boolean.TRUE.equals(jdbc.queryForObject(
              "SELECT EXISTS (SELECT 1 FROM "+BINDING+" WHERE job_definition_id=?)",
              Boolean.class,job));
    }
    @Override public boolean isReservedMessage(String run,String external) {
        return (external!=null && external.startsWith("T26:"))
          || (run!=null && Boolean.TRUE.equals(jdbc.queryForObject(
              "SELECT EXISTS (SELECT 1 FROM "+ATTEMPT+" WHERE job_run_id=?)",
              Boolean.class,run)));
    }
    private static void one(int count) {
        if (count!=1) throw conflict();
    }
    private static NoSuchElementException hidden() {
        return new NoSuchElementException("Protected exchange unavailable.");
    }
    private static IllegalStateException conflict() {
        return new IllegalStateException("Synthetic exchange state conflict.");
    }
}
