/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditAccessRecordJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for AuditAccessRecord.
 *
 */
package dz.sh.hidra.modules.audit.infrastructure.persistence.entity;

import dz.sh.hidra.modules.audit.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for AuditAccessRecord.
     */
    @Entity
    @Table(name = "hidra_audit_access_record")
    public class AuditAccessRecordJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "actor_id", nullable = false, length = 120)
    private String actorId;

    @Column(name = "actor_display_name_snapshot", nullable = true, length = 160)
    private String actorDisplayNameSnapshot;

    @Enumerated(EnumType.STRING)
    @Column(name = "access_type", nullable = false, length = 40)
    private AuditAccessType accessType;

    @Column(name = "audit_event_id", nullable = true, length = 80)
    private String auditEventId;

    @Column(name = "search_filter_hash", nullable = true, length = 256)
    private String searchFilterHash;

    @Column(name = "export_request_id", nullable = true, length = 80)
    private String exportRequestId;

    @Column(name = "result_count", nullable = true)
    private Integer resultCount;

    @Column(name = "purpose_text", nullable = true, length = 500)
    private String purposeText;

    @Column(name = "accessed_at", nullable = false)
    private Instant accessedAt;

    @Column(name = "correlation_id", nullable = true, length = 120)
    private String correlationId;

        protected AuditAccessRecordJpaEntity() {
            // Required by JPA.
        }

        public AuditAccessRecordJpaEntity(
                String id,
            String actorId,
            String actorDisplayNameSnapshot,
            AuditAccessType accessType,
            String auditEventId,
            String searchFilterHash,
            String exportRequestId,
            Integer resultCount,
            String purposeText,
            Instant accessedAt,
            String correlationId
        ) {
            this.id = id;
        this.actorId = actorId;
        this.actorDisplayNameSnapshot = actorDisplayNameSnapshot;
        this.accessType = accessType;
        this.auditEventId = auditEventId;
        this.searchFilterHash = searchFilterHash;
        this.exportRequestId = exportRequestId;
        this.resultCount = resultCount;
        this.purposeText = purposeText;
        this.accessedAt = accessedAt;
        this.correlationId = correlationId;
        }


    public String id() {
        return id;
    }


    public String actorId() {
        return actorId;
    }


    public String actorDisplayNameSnapshot() {
        return actorDisplayNameSnapshot;
    }


    public AuditAccessType accessType() {
        return accessType;
    }


    public String auditEventId() {
        return auditEventId;
    }


    public String searchFilterHash() {
        return searchFilterHash;
    }


    public String exportRequestId() {
        return exportRequestId;
    }


    public Integer resultCount() {
        return resultCount;
    }


    public String purposeText() {
        return purposeText;
    }


    public Instant accessedAt() {
        return accessedAt;
    }


    public String correlationId() {
        return correlationId;
    }

    }
