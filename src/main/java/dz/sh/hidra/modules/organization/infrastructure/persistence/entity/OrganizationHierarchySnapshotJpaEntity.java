/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationHierarchySnapshotJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for OrganizationHierarchySnapshot.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.persistence.entity;

import dz.sh.hidra.modules.organization.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for OrganizationHierarchySnapshot.
     */
    @Entity
    @Table(name = "hidra_org_hierarchy_snapshot")
    public class OrganizationHierarchySnapshotJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "snapshot_code", nullable = false, length = 120)
    private String snapshotCode;

    @Column(name = "captured_at", nullable = false)
    private Instant capturedAt;

    @Column(name = "captured_by_employee_id", nullable = true, length = 80)
    private String capturedByEmployeeId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private HierarchySnapshotStatus status;

    @Column(name = "snapshot_payload", nullable = false, columnDefinition = "jsonb")
    private String snapshotPayload;

    @Column(name = "description", nullable = true, columnDefinition = "text")
    private String description;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

        protected OrganizationHierarchySnapshotJpaEntity() {
            // Required by JPA.
        }

        public OrganizationHierarchySnapshotJpaEntity(
                String id,
            String snapshotCode,
            Instant capturedAt,
            String capturedByEmployeeId,
            HierarchySnapshotStatus status,
            String snapshotPayload,
            String description,
            Instant createdAt
        ) {
            this.id = id;
        this.snapshotCode = snapshotCode;
        this.capturedAt = capturedAt;
        this.capturedByEmployeeId = capturedByEmployeeId;
        this.status = status;
        this.snapshotPayload = snapshotPayload;
        this.description = description;
        this.createdAt = createdAt;
        }


    public String id() {
        return id;
    }


    public String snapshotCode() {
        return snapshotCode;
    }


    public Instant capturedAt() {
        return capturedAt;
    }


    public String capturedByEmployeeId() {
        return capturedByEmployeeId;
    }


    public HierarchySnapshotStatus status() {
        return status;
    }


    public String snapshotPayload() {
        return snapshotPayload;
    }


    public String description() {
        return description;
    }


    public Instant createdAt() {
        return createdAt;
    }

    }
