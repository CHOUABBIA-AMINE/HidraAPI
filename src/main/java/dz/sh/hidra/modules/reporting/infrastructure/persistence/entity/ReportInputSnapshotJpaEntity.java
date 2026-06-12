/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportInputSnapshotJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for ReportInputSnapshot.
 *
 */
package dz.sh.hidra.modules.reporting.infrastructure.persistence.entity;

import dz.sh.hidra.modules.reporting.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for ReportInputSnapshot.
     */
    @Entity
    @Table(name = "hidra_reporting_input_snapshot")
    public class ReportInputSnapshotJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "report_run_id", nullable = false, length = 80)
    private String reportRunId;

    @Enumerated(EnumType.STRING)
    @Column(name = "snapshot_type", nullable = false, length = 40)
    private ReportSnapshotType snapshotType;

    @Column(name = "source_module", nullable = false, length = 80)
    private String sourceModule;

    @Column(name = "source_reference_id", nullable = false, length = 120)
    private String sourceReferenceId;

    @Column(name = "source_reference_code", nullable = true, length = 120)
    private String sourceReferenceCode;

    @Column(name = "source_reference_label", nullable = true, length = 240)
    private String sourceReferenceLabel;

    @Column(name = "source_version", nullable = true, length = 80)
    private String sourceVersion;

    @Column(name = "snapshot_hash", nullable = false, length = 160)
    private String snapshotHash;

    @Column(name = "captured_at", nullable = false)
    private Instant capturedAt;

    @Column(name = "metadata_json", nullable = true, columnDefinition = "jsonb")
    private String metadataJson;

        protected ReportInputSnapshotJpaEntity() {
            // Required by JPA.
        }

        public ReportInputSnapshotJpaEntity(
                String id,
            String reportRunId,
            ReportSnapshotType snapshotType,
            String sourceModule,
            String sourceReferenceId,
            String sourceReferenceCode,
            String sourceReferenceLabel,
            String sourceVersion,
            String snapshotHash,
            Instant capturedAt,
            String metadataJson
        ) {
            this.id = id;
        this.reportRunId = reportRunId;
        this.snapshotType = snapshotType;
        this.sourceModule = sourceModule;
        this.sourceReferenceId = sourceReferenceId;
        this.sourceReferenceCode = sourceReferenceCode;
        this.sourceReferenceLabel = sourceReferenceLabel;
        this.sourceVersion = sourceVersion;
        this.snapshotHash = snapshotHash;
        this.capturedAt = capturedAt;
        this.metadataJson = metadataJson;
        }


    public String id() {
        return id;
    }


    public String reportRunId() {
        return reportRunId;
    }


    public ReportSnapshotType snapshotType() {
        return snapshotType;
    }


    public String sourceModule() {
        return sourceModule;
    }


    public String sourceReferenceId() {
        return sourceReferenceId;
    }


    public String sourceReferenceCode() {
        return sourceReferenceCode;
    }


    public String sourceReferenceLabel() {
        return sourceReferenceLabel;
    }


    public String sourceVersion() {
        return sourceVersion;
    }


    public String snapshotHash() {
        return snapshotHash;
    }


    public Instant capturedAt() {
        return capturedAt;
    }


    public String metadataJson() {
        return metadataJson;
    }

    }
