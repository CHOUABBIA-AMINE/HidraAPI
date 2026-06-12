/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanScenarioJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for PlanScenario.
 *
 */
package dz.sh.hidra.modules.planning.infrastructure.persistence.entity;

import dz.sh.hidra.modules.planning.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for PlanScenario.
     */
    @Entity
    @Table(name = "hidra_planning_plan_scenario")
    public class PlanScenarioJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "revision_id", nullable = false, length = 80)
    private String revisionId;

    @Column(name = "code", nullable = false, length = 80)
    private String code;

    @Column(name = "name_ar", nullable = true, length = 160)
    private String nameAr;

    @Column(name = "name_fr", nullable = false, length = 160)
    private String nameFr;

    @Column(name = "name_en", nullable = true, length = 160)
    private String nameEn;

    @Column(name = "scenario_type_id", nullable = false, length = 80)
    private String scenarioTypeId;

    @Column(name = "primary_scenario", nullable = false)
    private boolean primaryScenario;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private PlanScenarioStatus status;

    @Column(name = "description", nullable = true, length = 500)
    private String description;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected PlanScenarioJpaEntity() {
            // Required by JPA.
        }

        public PlanScenarioJpaEntity(
                String id,
            String revisionId,
            String code,
            String nameAr,
            String nameFr,
            String nameEn,
            String scenarioTypeId,
            boolean primaryScenario,
            PlanScenarioStatus status,
            String description,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.revisionId = revisionId;
        this.code = code;
        this.nameAr = nameAr;
        this.nameFr = nameFr;
        this.nameEn = nameEn;
        this.scenarioTypeId = scenarioTypeId;
        this.primaryScenario = primaryScenario;
        this.status = status;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String revisionId() {
        return revisionId;
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


    public String scenarioTypeId() {
        return scenarioTypeId;
    }


    public boolean primaryScenario() {
        return primaryScenario;
    }


    public PlanScenarioStatus status() {
        return status;
    }


    public String description() {
        return description;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
