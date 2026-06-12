/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportDataSourceBindingJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for ReportDataSourceBinding.
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
     * Database-backed JPA entity for ReportDataSourceBinding.
     */
    @Entity
    @Table(name = "hidra_reporting_data_source_binding")
    public class ReportDataSourceBindingJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "report_definition_id", nullable = false, length = 80)
    private String reportDefinitionId;

    @Column(name = "source_module", nullable = false, length = 80)
    private String sourceModule;

    @Enumerated(EnumType.STRING)
    @Column(name = "source_type", nullable = false, length = 40)
    private ReportSourceType sourceType;

    @Column(name = "source_name", nullable = false, length = 160)
    private String sourceName;

    @Column(name = "source_contract_version", nullable = true, length = 80)
    private String sourceContractVersion;

    @Column(name = "required", nullable = false)
    private boolean required;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected ReportDataSourceBindingJpaEntity() {
            // Required by JPA.
        }

        public ReportDataSourceBindingJpaEntity(
                String id,
            String reportDefinitionId,
            String sourceModule,
            ReportSourceType sourceType,
            String sourceName,
            String sourceContractVersion,
            boolean required,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.reportDefinitionId = reportDefinitionId;
        this.sourceModule = sourceModule;
        this.sourceType = sourceType;
        this.sourceName = sourceName;
        this.sourceContractVersion = sourceContractVersion;
        this.required = required;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String reportDefinitionId() {
        return reportDefinitionId;
    }


    public String sourceModule() {
        return sourceModule;
    }


    public ReportSourceType sourceType() {
        return sourceType;
    }


    public String sourceName() {
        return sourceName;
    }


    public String sourceContractVersion() {
        return sourceContractVersion;
    }


    public boolean required() {
        return required;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
