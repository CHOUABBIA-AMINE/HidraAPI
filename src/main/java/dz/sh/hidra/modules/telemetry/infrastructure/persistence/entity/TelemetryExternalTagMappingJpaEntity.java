/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryExternalTagMappingJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for TelemetryExternalTagMapping.
 *
 */
package dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity;

import dz.sh.hidra.modules.telemetry.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for TelemetryExternalTagMapping.
     */
    @Entity
    @Table(name = "hidra_telemetry_external_tag_mapping")
    public class TelemetryExternalTagMappingJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "source_id", nullable = false, length = 80)
    private String sourceId;

    @Column(name = "device_id", nullable = true, length = 80)
    private String deviceId;

    @Column(name = "point_id", nullable = false, length = 80)
    private String pointId;

    @Column(name = "external_tag_name", nullable = false, length = 500)
    private String externalTagName;

    @Column(name = "external_tag_id", nullable = true, length = 500)
    private String externalTagId;

    @Column(name = "external_namespace", nullable = true, length = 500)
    private String externalNamespace;

    @Column(name = "external_data_type", nullable = true, length = 80)
    private String externalDataType;

    @Enumerated(EnumType.STRING)
    @Column(name = "mapping_mode", nullable = false, length = 40)
    private ExternalTagMappingMode mappingMode;

    @Column(name = "transformation_expression", nullable = true, columnDefinition = "text")
    private String transformationExpression;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "valid_from", nullable = false)
    private Instant validFrom;

    @Column(name = "valid_to", nullable = true)
    private Instant validTo;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected TelemetryExternalTagMappingJpaEntity() {
            // Required by JPA.
        }

        public TelemetryExternalTagMappingJpaEntity(
                String id,
            String sourceId,
            String deviceId,
            String pointId,
            String externalTagName,
            String externalTagId,
            String externalNamespace,
            String externalDataType,
            ExternalTagMappingMode mappingMode,
            String transformationExpression,
            boolean active,
            Instant validFrom,
            Instant validTo,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.sourceId = sourceId;
        this.deviceId = deviceId;
        this.pointId = pointId;
        this.externalTagName = externalTagName;
        this.externalTagId = externalTagId;
        this.externalNamespace = externalNamespace;
        this.externalDataType = externalDataType;
        this.mappingMode = mappingMode;
        this.transformationExpression = transformationExpression;
        this.active = active;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String sourceId() {
        return sourceId;
    }


    public String deviceId() {
        return deviceId;
    }


    public String pointId() {
        return pointId;
    }


    public String externalTagName() {
        return externalTagName;
    }


    public String externalTagId() {
        return externalTagId;
    }


    public String externalNamespace() {
        return externalNamespace;
    }


    public String externalDataType() {
        return externalDataType;
    }


    public ExternalTagMappingMode mappingMode() {
        return mappingMode;
    }


    public String transformationExpression() {
        return transformationExpression;
    }


    public boolean active() {
        return active;
    }


    public Instant validFrom() {
        return validFrom;
    }


    public Instant validTo() {
        return validTo;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
