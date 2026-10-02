/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditBeforeAfterValueJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for AuditBeforeAfterValue.
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
     * Database-backed JPA entity for AuditBeforeAfterValue.
     */
    @Entity
    @Table(name = "hidra_audit_before_after_value")
    public class AuditBeforeAfterValueJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "audit_event_id", nullable = false, length = 80)
    private String auditEventId;

    @Column(name = "field_path", nullable = false, length = 240)
    private String fieldPath;

    @Column(name = "field_label_snapshot", nullable = true, length = 240)
    private String fieldLabelSnapshot;

    @Enumerated(EnumType.STRING)
    @Column(name = "value_type", nullable = false, length = 40)
    private AuditValueType valueType;

    @Column(name = "before_value_text", nullable = true, length = 2000)
    private String beforeValueText;

    @Column(name = "after_value_text", nullable = true, length = 2000)
    private String afterValueText;

    @Column(name = "before_value_hash", nullable = true, length = 256)
    private String beforeValueHash;

    @Column(name = "after_value_hash", nullable = true, length = 256)
    private String afterValueHash;

    @Column(name = "masked", nullable = false)
    private boolean masked;

    @Column(name = "mask_reason_id", nullable = true, length = 80)
    private String maskReasonId;

    @Column(name = "changed", nullable = false)
    private boolean changed;

    @Column(name = "recorded_at", nullable = false)
    private Instant recordedAt;

        protected AuditBeforeAfterValueJpaEntity() {
            // Required by JPA.
        }

        public AuditBeforeAfterValueJpaEntity(
                String id,
            String auditEventId,
            String fieldPath,
            String fieldLabelSnapshot,
            AuditValueType valueType,
            String beforeValueText,
            String afterValueText,
            String beforeValueHash,
            String afterValueHash,
            boolean masked,
            String maskReasonId,
            boolean changed,
            Instant recordedAt
        ) {
            this.id = id;
        this.auditEventId = auditEventId;
        this.fieldPath = fieldPath;
        this.fieldLabelSnapshot = fieldLabelSnapshot;
        this.valueType = valueType;
        this.beforeValueText = beforeValueText;
        this.afterValueText = afterValueText;
        this.beforeValueHash = beforeValueHash;
        this.afterValueHash = afterValueHash;
        this.masked = masked;
        this.maskReasonId = maskReasonId;
        this.changed = changed;
        this.recordedAt = recordedAt;
        }


    public String id() {
        return id;
    }


    public String auditEventId() {
        return auditEventId;
    }


    public String fieldPath() {
        return fieldPath;
    }


    public String fieldLabelSnapshot() {
        return fieldLabelSnapshot;
    }


    public AuditValueType valueType() {
        return valueType;
    }


    public String beforeValueText() {
        return beforeValueText;
    }


    public String afterValueText() {
        return afterValueText;
    }


    public String beforeValueHash() {
        return beforeValueHash;
    }


    public String afterValueHash() {
        return afterValueHash;
    }


    public boolean masked() {
        return masked;
    }


    public String maskReasonId() {
        return maskReasonId;
    }


    public boolean changed() {
        return changed;
    }


    public Instant recordedAt() {
        return recordedAt;
    }

    }
