/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationResultSeriesReferenceJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for SimulationResultSeriesReference.
 *
 */
package dz.sh.hidra.modules.simulation.infrastructure.persistence.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for SimulationResultSeriesReference.
     */
    @Entity
    @Table(name = "hidra_simulation_result_series_reference")
    public class SimulationResultSeriesReferenceJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "run_id", nullable = false, length = 80)
    private String runId;

    @Column(name = "series_type_id", nullable = false, length = 80)
    private String seriesTypeId;

    @Column(name = "target_type", nullable = true, length = 80)
    private String targetType;

    @Column(name = "target_id", nullable = true, length = 120)
    private String targetId;

    @Column(name = "storage_location", nullable = false, length = 1000)
    private String storageLocation;

    @Column(name = "checksum", nullable = true, length = 160)
    private String checksum;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

        protected SimulationResultSeriesReferenceJpaEntity() {
            // Required by JPA.
        }

        public SimulationResultSeriesReferenceJpaEntity(
                String id,
            String runId,
            String seriesTypeId,
            String targetType,
            String targetId,
            String storageLocation,
            String checksum,
            Instant createdAt
        ) {
            this.id = id;
        this.runId = runId;
        this.seriesTypeId = seriesTypeId;
        this.targetType = targetType;
        this.targetId = targetId;
        this.storageLocation = storageLocation;
        this.checksum = checksum;
        this.createdAt = createdAt;
        }


    public String id() {
        return id;
    }


    public String runId() {
        return runId;
    }


    public String seriesTypeId() {
        return seriesTypeId;
    }


    public String targetType() {
        return targetType;
    }


    public String targetId() {
        return targetId;
    }


    public String storageLocation() {
        return storageLocation;
    }


    public String checksum() {
        return checksum;
    }


    public Instant createdAt() {
        return createdAt;
    }

    }
