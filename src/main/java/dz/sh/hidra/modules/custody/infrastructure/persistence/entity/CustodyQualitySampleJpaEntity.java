/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyQualitySampleJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for CustodyQualitySample.
 *
 */
package dz.sh.hidra.modules.custody.infrastructure.persistence.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for CustodyQualitySample.
     */
    @Entity
    @Table(name = "hidra_custody_quality_sample")
    public class CustodyQualitySampleJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "sample_number", nullable = false, length = 80)
    private String sampleNumber;

    @Column(name = "measurement_period_id", nullable = false, length = 80)
    private String measurementPeriodId;

    @Column(name = "batch_id", nullable = true, length = 80)
    private String batchId;

    @Column(name = "sample_type_id", nullable = false, length = 80)
    private String sampleTypeId;

    @Column(name = "product_type_id", nullable = false, length = 80)
    private String productTypeId;

    @Column(name = "sampled_at", nullable = false)
    private Instant sampledAt;

    @Column(name = "sampled_by_actor_id", nullable = true, length = 80)
    private String sampledByActorId;

    @Column(name = "laboratory_party_id", nullable = true, length = 80)
    private String laboratoryPartyId;

    @Column(name = "laboratory_name_snapshot", nullable = true, length = 255)
    private String laboratoryNameSnapshot;

    @Column(name = "result_summary", nullable = true, columnDefinition = "text")
    private String resultSummary;

    @Column(name = "certificate_id", nullable = true, length = 80)
    private String certificateId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

        protected CustodyQualitySampleJpaEntity() {
            // Required by JPA.
        }

        public CustodyQualitySampleJpaEntity(
                String id,
            String sampleNumber,
            String measurementPeriodId,
            String batchId,
            String sampleTypeId,
            String productTypeId,
            Instant sampledAt,
            String sampledByActorId,
            String laboratoryPartyId,
            String laboratoryNameSnapshot,
            String resultSummary,
            String certificateId,
            Instant createdAt
        ) {
            this.id = id;
        this.sampleNumber = sampleNumber;
        this.measurementPeriodId = measurementPeriodId;
        this.batchId = batchId;
        this.sampleTypeId = sampleTypeId;
        this.productTypeId = productTypeId;
        this.sampledAt = sampledAt;
        this.sampledByActorId = sampledByActorId;
        this.laboratoryPartyId = laboratoryPartyId;
        this.laboratoryNameSnapshot = laboratoryNameSnapshot;
        this.resultSummary = resultSummary;
        this.certificateId = certificateId;
        this.createdAt = createdAt;
        }


    public String id() {
        return id;
    }


    public String sampleNumber() {
        return sampleNumber;
    }


    public String measurementPeriodId() {
        return measurementPeriodId;
    }


    public String batchId() {
        return batchId;
    }


    public String sampleTypeId() {
        return sampleTypeId;
    }


    public String productTypeId() {
        return productTypeId;
    }


    public Instant sampledAt() {
        return sampledAt;
    }


    public String sampledByActorId() {
        return sampledByActorId;
    }


    public String laboratoryPartyId() {
        return laboratoryPartyId;
    }


    public String laboratoryNameSnapshot() {
        return laboratoryNameSnapshot;
    }


    public String resultSummary() {
        return resultSummary;
    }


    public String certificateId() {
        return certificateId;
    }


    public Instant createdAt() {
        return createdAt;
    }

    }
