/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportScheduleParameterJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for ReportScheduleParameter.
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
     * Database-backed JPA entity for ReportScheduleParameter.
     */
    @Entity
    @Table(name = "hidra_reporting_schedule_parameter")
    public class ReportScheduleParameterJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "report_schedule_id", nullable = false, length = 80)
    private String reportScheduleId;

    @Column(name = "parameter_definition_id", nullable = false, length = 80)
    private String parameterDefinitionId;

    @Column(name = "parameter_code", nullable = false, length = 120)
    private String parameterCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "value_type", nullable = false, length = 40)
    private ReportValueType valueType;

    @Column(name = "value_json", nullable = true, columnDefinition = "jsonb")
    private String valueJson;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected ReportScheduleParameterJpaEntity() {
            // Required by JPA.
        }

        public ReportScheduleParameterJpaEntity(
                String id,
            String reportScheduleId,
            String parameterDefinitionId,
            String parameterCode,
            ReportValueType valueType,
            String valueJson,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.reportScheduleId = reportScheduleId;
        this.parameterDefinitionId = parameterDefinitionId;
        this.parameterCode = parameterCode;
        this.valueType = valueType;
        this.valueJson = valueJson;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String reportScheduleId() {
        return reportScheduleId;
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


    public String valueJson() {
        return valueJson;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
