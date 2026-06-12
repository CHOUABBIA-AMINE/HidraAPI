/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportParameterValueJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for ReportParameterValue.
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
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

    /**
     * Database-backed JPA entity for ReportParameterValue.
     */
    @Entity
    @Table(name = "hidra_reporting_parameter_value")
    public class ReportParameterValueJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "report_request_id", nullable = false, length = 80)
    private String reportRequestId;

    @Column(name = "parameter_definition_id", nullable = false, length = 80)
    private String parameterDefinitionId;

    @Column(name = "parameter_code", nullable = false, length = 120)
    private String parameterCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "value_type", nullable = false, length = 40)
    private ReportValueType valueType;

    @Column(name = "value_text", nullable = true, length = 2000)
    private String valueText;

    @Column(name = "value_number", nullable = true, precision = 18, scale = 6)
    private BigDecimal valueNumber;

    @Column(name = "value_boolean", nullable = true)
    private Boolean valueBoolean;

    @Column(name = "value_date", nullable = true)
    private LocalDate valueDate;

    @Column(name = "value_date_time", nullable = true)
    private Instant valueDateTime;

    @Column(name = "value_json", nullable = true, columnDefinition = "jsonb")
    private String valueJson;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

        protected ReportParameterValueJpaEntity() {
            // Required by JPA.
        }

        public ReportParameterValueJpaEntity(
                String id,
            String reportRequestId,
            String parameterDefinitionId,
            String parameterCode,
            ReportValueType valueType,
            String valueText,
            BigDecimal valueNumber,
            Boolean valueBoolean,
            LocalDate valueDate,
            Instant valueDateTime,
            String valueJson,
            Instant createdAt
        ) {
            this.id = id;
        this.reportRequestId = reportRequestId;
        this.parameterDefinitionId = parameterDefinitionId;
        this.parameterCode = parameterCode;
        this.valueType = valueType;
        this.valueText = valueText;
        this.valueNumber = valueNumber;
        this.valueBoolean = valueBoolean;
        this.valueDate = valueDate;
        this.valueDateTime = valueDateTime;
        this.valueJson = valueJson;
        this.createdAt = createdAt;
        }


    public String id() {
        return id;
    }


    public String reportRequestId() {
        return reportRequestId;
    }


    public String parameterDefinitionId() {
        return parameterDefinitionId;
    }


    public String parameterCode() {
        return parameterCode;
    }


    public ReportValueType valueType() {
        return valueType;
    }


    public String valueText() {
        return valueText;
    }


    public BigDecimal valueNumber() {
        return valueNumber;
    }


    public Boolean valueBoolean() {
        return valueBoolean;
    }


    public LocalDate valueDate() {
        return valueDate;
    }


    public Instant valueDateTime() {
        return valueDateTime;
    }


    public String valueJson() {
        return valueJson;
    }


    public Instant createdAt() {
        return createdAt;
    }

    }
