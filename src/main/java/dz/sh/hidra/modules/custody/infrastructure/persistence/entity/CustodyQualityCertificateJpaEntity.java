/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyQualityCertificateJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for CustodyQualityCertificate.
 *
 */
package dz.sh.hidra.modules.custody.infrastructure.persistence.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for CustodyQualityCertificate.
     */
    @Entity
    @Table(name = "hidra_custody_quality_certificate")
    public class CustodyQualityCertificateJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "certificate_number", nullable = false, length = 80)
    private String certificateNumber;

    @Column(name = "quality_sample_id", nullable = true, length = 80)
    private String qualitySampleId;

    @Column(name = "document_reference_id", nullable = true, length = 80)
    private String documentReferenceId;

    @Column(name = "issued_by_party_id", nullable = true, length = 80)
    private String issuedByPartyId;

    @Column(name = "issued_by_name_snapshot", nullable = true, length = 255)
    private String issuedByNameSnapshot;

    @Column(name = "issued_at", nullable = false)
    private Instant issuedAt;

    @Column(name = "certificate_summary_json", nullable = true, columnDefinition = "jsonb")
    private String certificateSummaryJson;

    @Column(name = "status", nullable = false, length = 40)
    private String status;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

        protected CustodyQualityCertificateJpaEntity() {
            // Required by JPA.
        }

        public CustodyQualityCertificateJpaEntity(
                String id,
            String certificateNumber,
            String qualitySampleId,
            String documentReferenceId,
            String issuedByPartyId,
            String issuedByNameSnapshot,
            Instant issuedAt,
            String certificateSummaryJson,
            String status,
            Instant createdAt
        ) {
            this.id = id;
        this.certificateNumber = certificateNumber;
        this.qualitySampleId = qualitySampleId;
        this.documentReferenceId = documentReferenceId;
        this.issuedByPartyId = issuedByPartyId;
        this.issuedByNameSnapshot = issuedByNameSnapshot;
        this.issuedAt = issuedAt;
        this.certificateSummaryJson = certificateSummaryJson;
        this.status = status;
        this.createdAt = createdAt;
        }


    public String id() {
        return id;
    }


    public String certificateNumber() {
        return certificateNumber;
    }


    public String qualitySampleId() {
        return qualitySampleId;
    }


    public String documentReferenceId() {
        return documentReferenceId;
    }


    public String issuedByPartyId() {
        return issuedByPartyId;
    }


    public String issuedByNameSnapshot() {
        return issuedByNameSnapshot;
    }


    public Instant issuedAt() {
        return issuedAt;
    }


    public String certificateSummaryJson() {
        return certificateSummaryJson;
    }


    public String status() {
        return status;
    }


    public Instant createdAt() {
        return createdAt;
    }

    }
