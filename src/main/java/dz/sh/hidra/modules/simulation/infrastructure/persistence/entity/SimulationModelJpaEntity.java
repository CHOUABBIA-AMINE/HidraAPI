/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationModelJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for SimulationModel.
 *
 */
package dz.sh.hidra.modules.simulation.infrastructure.persistence.entity;

import dz.sh.hidra.modules.simulation.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for SimulationModel.
     */
    @Entity
    @Table(name = "hidra_simulation_model")
    public class SimulationModelJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "code", nullable = false, length = 120)
    private String code;

    @Column(name = "name_ar", nullable = true, length = 160)
    private String nameAr;

    @Column(name = "name_fr", nullable = false, length = 160)
    private String nameFr;

    @Column(name = "name_en", nullable = true, length = 160)
    private String nameEn;

    @Column(name = "model_type_id", nullable = false, length = 80)
    private String modelTypeId;

    @Column(name = "topology_scope_type", nullable = false, length = 80)
    private String topologyScopeType;

    @Column(name = "topology_scope_id", nullable = true, length = 120)
    private String topologyScopeId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private SimulationModelStatus status;

    @Column(name = "description", nullable = true, length = 1000)
    private String description;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected SimulationModelJpaEntity() {
            // Required by JPA.
        }

        public SimulationModelJpaEntity(
                String id,
            String code,
            String nameAr,
            String nameFr,
            String nameEn,
            String modelTypeId,
            String topologyScopeType,
            String topologyScopeId,
            SimulationModelStatus status,
            String description,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.code = code;
        this.nameAr = nameAr;
        this.nameFr = nameFr;
        this.nameEn = nameEn;
        this.modelTypeId = modelTypeId;
        this.topologyScopeType = topologyScopeType;
        this.topologyScopeId = topologyScopeId;
        this.status = status;
        this.description = description;
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


    public String modelTypeId() {
        return modelTypeId;
    }


    public String topologyScopeType() {
        return topologyScopeType;
    }


    public String topologyScopeId() {
        return topologyScopeId;
    }


    public SimulationModelStatus status() {
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
