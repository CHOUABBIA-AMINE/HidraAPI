/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetrySourceJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for TelemetrySource.
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
     * Database-backed JPA entity for TelemetrySource.
     */
    @Entity
    @Table(name = "hidra_telemetry_source")
    public class TelemetrySourceJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "code", nullable = false, length = 80)
    private String code;

    @Column(name = "name_ar", nullable = true, length = 160)
    private String nameAr;

    @Column(name = "name_fr", nullable = false, length = 160)
    private String nameFr;

    @Column(name = "name_en", nullable = true, length = 160)
    private String nameEn;

    @Column(name = "source_type_id", nullable = false, length = 80)
    private String sourceTypeId;

    @Column(name = "protocol_id", nullable = false, length = 80)
    private String protocolId;

    @Column(name = "endpoint_uri", nullable = true, columnDefinition = "text")
    private String endpointUri;

    @Column(name = "external_reference", nullable = true, length = 500)
    private String externalReference;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private TelemetryLifecycleStatus status;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected TelemetrySourceJpaEntity() {
            // Required by JPA.
        }

        public TelemetrySourceJpaEntity(
                String id,
            String code,
            String nameAr,
            String nameFr,
            String nameEn,
            String sourceTypeId,
            String protocolId,
            String endpointUri,
            String externalReference,
            TelemetryLifecycleStatus status,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.code = code;
        this.nameAr = nameAr;
        this.nameFr = nameFr;
        this.nameEn = nameEn;
        this.sourceTypeId = sourceTypeId;
        this.protocolId = protocolId;
        this.endpointUri = endpointUri;
        this.externalReference = externalReference;
        this.status = status;
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


    public String sourceTypeId() {
        return sourceTypeId;
    }


    public String protocolId() {
        return protocolId;
    }


    public String endpointUri() {
        return endpointUri;
    }


    public String externalReference() {
        return externalReference;
    }


    public TelemetryLifecycleStatus status() {
        return status;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
