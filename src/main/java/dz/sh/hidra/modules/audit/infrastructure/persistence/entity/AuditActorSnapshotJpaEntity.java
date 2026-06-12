/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditActorSnapshotJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for AuditActorSnapshot.
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
     * Database-backed JPA entity for AuditActorSnapshot.
     */
    @Entity
    @Table(name = "hidra_audit_actor_snapshot")
    public class AuditActorSnapshotJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "audit_event_id", nullable = false, length = 80)
    private String auditEventId;

    @Column(name = "actor_id", nullable = true, length = 120)
    private String actorId;

    @Enumerated(EnumType.STRING)
    @Column(name = "actor_type", nullable = false, length = 40)
    private AuditActorType actorType;

    @Column(name = "username_snapshot", nullable = true, length = 120)
    private String usernameSnapshot;

    @Column(name = "display_name_snapshot", nullable = true, length = 160)
    private String displayNameSnapshot;

    @Column(name = "email_masked", nullable = true, length = 160)
    private String emailMasked;

    @Column(name = "role_code_snapshot", nullable = true, length = 120)
    private String roleCodeSnapshot;

    @Column(name = "employee_id", nullable = true, length = 120)
    private String employeeId;

    @Column(name = "employee_number_snapshot", nullable = true, length = 80)
    private String employeeNumberSnapshot;

    @Column(name = "organization_unit_id", nullable = true, length = 120)
    private String organizationUnitId;

    @Column(name = "organization_unit_code_snapshot", nullable = true, length = 120)
    private String organizationUnitCodeSnapshot;

    @Column(name = "organization_unit_name_snapshot", nullable = true, length = 160)
    private String organizationUnitNameSnapshot;

    @Column(name = "position_code_snapshot", nullable = true, length = 120)
    private String positionCodeSnapshot;

    @Column(name = "captured_at", nullable = false)
    private Instant capturedAt;

        protected AuditActorSnapshotJpaEntity() {
            // Required by JPA.
        }

        public AuditActorSnapshotJpaEntity(
                String id,
            String auditEventId,
            String actorId,
            AuditActorType actorType,
            String usernameSnapshot,
            String displayNameSnapshot,
            String emailMasked,
            String roleCodeSnapshot,
            String employeeId,
            String employeeNumberSnapshot,
            String organizationUnitId,
            String organizationUnitCodeSnapshot,
            String organizationUnitNameSnapshot,
            String positionCodeSnapshot,
            Instant capturedAt
        ) {
            this.id = id;
        this.auditEventId = auditEventId;
        this.actorId = actorId;
        this.actorType = actorType;
        this.usernameSnapshot = usernameSnapshot;
        this.displayNameSnapshot = displayNameSnapshot;
        this.emailMasked = emailMasked;
        this.roleCodeSnapshot = roleCodeSnapshot;
        this.employeeId = employeeId;
        this.employeeNumberSnapshot = employeeNumberSnapshot;
        this.organizationUnitId = organizationUnitId;
        this.organizationUnitCodeSnapshot = organizationUnitCodeSnapshot;
        this.organizationUnitNameSnapshot = organizationUnitNameSnapshot;
        this.positionCodeSnapshot = positionCodeSnapshot;
        this.capturedAt = capturedAt;
        }


    public String id() {
        return id;
    }


    public String auditEventId() {
        return auditEventId;
    }


    public String actorId() {
        return actorId;
    }


    public AuditActorType actorType() {
        return actorType;
    }


    public String usernameSnapshot() {
        return usernameSnapshot;
    }


    public String displayNameSnapshot() {
        return displayNameSnapshot;
    }


    public String emailMasked() {
        return emailMasked;
    }


    public String roleCodeSnapshot() {
        return roleCodeSnapshot;
    }


    public String employeeId() {
        return employeeId;
    }


    public String employeeNumberSnapshot() {
        return employeeNumberSnapshot;
    }


    public String organizationUnitId() {
        return organizationUnitId;
    }


    public String organizationUnitCodeSnapshot() {
        return organizationUnitCodeSnapshot;
    }


    public String organizationUnitNameSnapshot() {
        return organizationUnitNameSnapshot;
    }


    public String positionCodeSnapshot() {
        return positionCodeSnapshot;
    }


    public Instant capturedAt() {
        return capturedAt;
    }

    }
