/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportScheduleJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for ReportSchedule.
 *
 */
package dz.sh.hidra.modules.reporting.infrastructure.persistence.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for ReportSchedule.
     */
    @Entity
    @Table(name = "hidra_reporting_schedule")
    public class ReportScheduleJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "report_definition_id", nullable = false, length = 80)
    private String reportDefinitionId;

    @Column(name = "code", nullable = false, length = 120)
    private String code;

    @Column(name = "name_ar", nullable = true, length = 160)
    private String nameAr;

    @Column(name = "name_fr", nullable = false, length = 160)
    private String nameFr;

    @Column(name = "name_en", nullable = true, length = 160)
    private String nameEn;

    @Column(name = "cron_expression", nullable = false, length = 255)
    private String cronExpression;

    @Column(name = "timezone", nullable = false, length = 80)
    private String timezone;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "next_run_at", nullable = true)
    private Instant nextRunAt;

    @Column(name = "last_run_at", nullable = true)
    private Instant lastRunAt;

    @Column(name = "created_by_actor_id", nullable = false, length = 80)
    private String createdByActorId;

    @Column(name = "created_by_display_name_snapshot", nullable = true, length = 160)
    private String createdByDisplayNameSnapshot;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected ReportScheduleJpaEntity() {
            // Required by JPA.
        }

        public ReportScheduleJpaEntity(
                String id,
            String reportDefinitionId,
            String code,
            String nameAr,
            String nameFr,
            String nameEn,
            String cronExpression,
            String timezone,
            boolean active,
            Instant nextRunAt,
            Instant lastRunAt,
            String createdByActorId,
            String createdByDisplayNameSnapshot,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.reportDefinitionId = reportDefinitionId;
        this.code = code;
        this.nameAr = nameAr;
        this.nameFr = nameFr;
        this.nameEn = nameEn;
        this.cronExpression = cronExpression;
        this.timezone = timezone;
        this.active = active;
        this.nextRunAt = nextRunAt;
        this.lastRunAt = lastRunAt;
        this.createdByActorId = createdByActorId;
        this.createdByDisplayNameSnapshot = createdByDisplayNameSnapshot;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String reportDefinitionId() {
        return reportDefinitionId;
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


    public String cronExpression() {
        return cronExpression;
    }


    public String timezone() {
        return timezone;
    }


    public boolean active() {
        return active;
    }


    public Instant nextRunAt() {
        return nextRunAt;
    }


    public Instant lastRunAt() {
        return lastRunAt;
    }


    public String createdByActorId() {
        return createdByActorId;
    }


    public String createdByDisplayNameSnapshot() {
        return createdByDisplayNameSnapshot;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
