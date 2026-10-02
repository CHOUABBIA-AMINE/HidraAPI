/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationInputDatasetJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for SimulationInputDataset.
 *
 */
package dz.sh.hidra.modules.simulation.infrastructure.persistence.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for SimulationInputDataset.
     */
    @Entity
    @Table(name = "hidra_simulation_input_dataset")
    public class SimulationInputDatasetJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "input_snapshot_id", nullable = false, length = 80)
    private String inputSnapshotId;

    @Column(name = "dataset_type_id", nullable = false, length = 80)
    private String datasetTypeId;

    @Column(name = "source_module", nullable = false, length = 80)
    private String sourceModule;

    @Column(name = "source_reference", nullable = false, length = 255)
    private String sourceReference;

    @Column(name = "record_count", nullable = true)
    private Long recordCount;

    @Column(name = "checksum", nullable = true, length = 160)
    private String checksum;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

        protected SimulationInputDatasetJpaEntity() {
            // Required by JPA.
        }

        public SimulationInputDatasetJpaEntity(
                String id,
            String inputSnapshotId,
            String datasetTypeId,
            String sourceModule,
            String sourceReference,
            Long recordCount,
            String checksum,
            Instant createdAt
        ) {
            this.id = id;
        this.inputSnapshotId = inputSnapshotId;
        this.datasetTypeId = datasetTypeId;
        this.sourceModule = sourceModule;
        this.sourceReference = sourceReference;
        this.recordCount = recordCount;
        this.checksum = checksum;
        this.createdAt = createdAt;
        }


    public String id() {
        return id;
    }


    public String inputSnapshotId() {
        return inputSnapshotId;
    }


    public String datasetTypeId() {
        return datasetTypeId;
    }


    public String sourceModule() {
        return sourceModule;
    }


    public String sourceReference() {
        return sourceReference;
    }


    public Long recordCount() {
        return recordCount;
    }


    public String checksum() {
        return checksum;
    }


    public Instant createdAt() {
        return createdAt;
    }

    }
