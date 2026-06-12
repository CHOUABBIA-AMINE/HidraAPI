/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrityProgramJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for IntegrityProgram.
 *
 */
package dz.sh.hidra.modules.integrity.infrastructure.persistence.entity;

import dz.sh.hidra.modules.integrity.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for IntegrityProgram.
     */
    @Entity
    @Table(name = "hidra_integrity_program")
    public class IntegrityProgramJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "code", nullable = false, length = 80)
    private String code;

    @Column(name = "name_ar", nullable = true, length = 160)
    private String nameAr;

    @Column(name = "name_fr", nullable = false, length = 160)
    private String nameFr;

    @Column(name = "name_en", nullable = true, length = 160)
    private String nameEn;

    @Column(name = "description", nullable = true, columnDefinition = "text")
    private String description;

    @Column(name = "program_type_id", nullable = false, length = 80)
    private String programTypeId;

    @Column(name = "owner_organization_unit_id", nullable = true, length = 80)
    private String ownerOrganizationUnitId;

    @Column(name = "owner_organization_unit_name_snapshot", nullable = true, length = 500)
    private String ownerOrganizationUnitNameSnapshot;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private IntegrityProgramStatus status;

    @Column(name = "planned_start_at", nullable = true)
    private Instant plannedStartAt;

    @Column(name = "planned_end_at", nullable = true)
    private Instant plannedEndAt;

    @Column(name = "actual_start_at", nullable = true)
    private Instant actualStartAt;

    @Column(name = "actual_end_at", nullable = true)
    private Instant actualEndAt;

    @Column(name = "created_by_actor_id", nullable = true, length = 80)
    private String createdByActorId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected IntegrityProgramJpaEntity() {
            // Required by JPA.
        }

        public IntegrityProgramJpaEntity(
                String id,
            String code,
            String nameAr,
            String nameFr,
            String nameEn,
            String description,
            String programTypeId,
            String ownerOrganizationUnitId,
            String ownerOrganizationUnitNameSnapshot,
            IntegrityProgramStatus status,
            Instant plannedStartAt,
            Instant plannedEndAt,
            Instant actualStartAt,
            Instant actualEndAt,
            String createdByActorId,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.code = code;
        this.nameAr = nameAr;
        this.nameFr = nameFr;
        this.nameEn = nameEn;
        this.description = description;
        this.programTypeId = programTypeId;
        this.ownerOrganizationUnitId = ownerOrganizationUnitId;
        this.ownerOrganizationUnitNameSnapshot = ownerOrganizationUnitNameSnapshot;
        this.status = status;
        this.plannedStartAt = plannedStartAt;
        this.plannedEndAt = plannedEndAt;
        this.actualStartAt = actualStartAt;
        this.actualEndAt = actualEndAt;
        this.createdByActorId = createdByActorId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String code() {
        return code;
    }


    public String nameAr() {
        return nameAr;
    }


    public String nameFr() {
        return nameFr;
    }


    public String nameEn() {
        return nameEn;
    }


    public String description() {
        return description;
    }


    public String programTypeId() {
        return programTypeId;
    }


    public String ownerOrganizationUnitId() {
        return ownerOrganizationUnitId;
    }


    public String ownerOrganizationUnitNameSnapshot() {
        return ownerOrganizationUnitNameSnapshot;
    }


    public IntegrityProgramStatus status() {
        return status;
    }


    public Instant plannedStartAt() {
        return plannedStartAt;
    }


    public Instant plannedEndAt() {
        return plannedEndAt;
    }


    public Instant actualStartAt() {
        return actualStartAt;
    }


    public Instant actualEndAt() {
        return actualEndAt;
    }


    public String createdByActorId() {
        return createdByActorId;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
