/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationMappingProfileJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for IntegrationMappingProfile.
 *
 */
package dz.sh.hidra.modules.integration.infrastructure.persistence.entity;

import dz.sh.hidra.modules.integration.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for IntegrationMappingProfile.
     */
    @Entity
    @Table(name = "hidra_integration_mapping_profile")
    public class IntegrationMappingProfileJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "code", nullable = false, length = 120)
    private String code;

    @Column(name = "external_system_id", nullable = false, length = 80)
    private String externalSystemId;

    @Column(name = "data_contract_id", nullable = false, length = 80)
    private String dataContractId;

    @Column(name = "schema_version_id", nullable = true, length = 80)
    private String schemaVersionId;

    @Column(name = "target_module", nullable = false, length = 80)
    private String targetModule;

    @Column(name = "target_type_code", nullable = false, length = 120)
    private String targetTypeCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "direction", nullable = false, length = 30)
    private IntegrationDirection direction;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private MappingProfileStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "validation_mode", nullable = false, length = 40)
    private ValidationMode validationMode;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected IntegrationMappingProfileJpaEntity() {
            // Required by JPA.
        }

        public IntegrationMappingProfileJpaEntity(
                String id,
            String code,
            String externalSystemId,
            String dataContractId,
            String schemaVersionId,
            String targetModule,
            String targetTypeCode,
            IntegrationDirection direction,
            MappingProfileStatus status,
            ValidationMode validationMode,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.code = code;
        this.externalSystemId = externalSystemId;
        this.dataContractId = dataContractId;
        this.schemaVersionId = schemaVersionId;
        this.targetModule = targetModule;
        this.targetTypeCode = targetTypeCode;
        this.direction = direction;
        this.status = status;
        this.validationMode = validationMode;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String code() {
        return code;
    }


    public String externalSystemId() {
        return externalSystemId;
    }


    public String dataContractId() {
        return dataContractId;
    }


    public String schemaVersionId() {
        return schemaVersionId;
    }


    public String targetModule() {
        return targetModule;
    }


    public String targetTypeCode() {
        return targetTypeCode;
    }


    public IntegrationDirection direction() {
        return direction;
    }


    public MappingProfileStatus status() {
        return status;
    }


    public ValidationMode validationMode() {
        return validationMode;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
