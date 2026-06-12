/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditActionReferenceJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for AuditActionReference.
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
     * Database-backed JPA entity for AuditActionReference.
     */
    @Entity
    @Table(name = "hidra_audit_action_reference")
    public class AuditActionReferenceJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "audit_event_id", nullable = false, length = 80)
    private String auditEventId;

    @Column(name = "action_code", nullable = false, length = 120)
    private String actionCode;

    @Column(name = "action_type_id", nullable = false, length = 80)
    private String actionTypeId;

    @Enumerated(EnumType.STRING)
    @Column(name = "operation", nullable = false, length = 60)
    private AuditOperation operation;

    @Column(name = "command_name", nullable = true, length = 160)
    private String commandName;

    @Column(name = "result_status", nullable = false, length = 40)
    private String resultStatus;

    @Column(name = "failure_reason_code", nullable = true, length = 120)
    private String failureReasonCode;

    @Column(name = "captured_at", nullable = false)
    private Instant capturedAt;

        protected AuditActionReferenceJpaEntity() {
            // Required by JPA.
        }

        public AuditActionReferenceJpaEntity(
                String id,
            String auditEventId,
            String actionCode,
            String actionTypeId,
            AuditOperation operation,
            String commandName,
            String resultStatus,
            String failureReasonCode,
            Instant capturedAt
        ) {
            this.id = id;
        this.auditEventId = auditEventId;
        this.actionCode = actionCode;
        this.actionTypeId = actionTypeId;
        this.operation = operation;
        this.commandName = commandName;
        this.resultStatus = resultStatus;
        this.failureReasonCode = failureReasonCode;
        this.capturedAt = capturedAt;
        }


    public String id() {
        return id;
    }


    public String auditEventId() {
        return auditEventId;
    }


    public String actionCode() {
        return actionCode;
    }


    public String actionTypeId() {
        return actionTypeId;
    }


    public AuditOperation operation() {
        return operation;
    }


    public String commandName() {
        return commandName;
    }


    public String resultStatus() {
        return resultStatus;
    }


    public String failureReasonCode() {
        return failureReasonCode;
    }


    public Instant capturedAt() {
        return capturedAt;
    }

    }
