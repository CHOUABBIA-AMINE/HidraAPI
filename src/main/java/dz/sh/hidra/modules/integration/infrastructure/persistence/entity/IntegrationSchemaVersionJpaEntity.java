/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationSchemaVersionJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for IntegrationSchemaVersion.
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
     * Database-backed JPA entity for IntegrationSchemaVersion.
     */
    @Entity
    @Table(name = "hidra_integration_schema_version")
    public class IntegrationSchemaVersionJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "data_contract_id", nullable = false, length = 80)
    private String dataContractId;

    @Column(name = "version_number", nullable = false)
    private int versionNumber;

    @Column(name = "schema_definition", nullable = false, columnDefinition = "jsonb")
    private String schemaDefinition;

    @Column(name = "checksum", nullable = false, length = 128)
    private String checksum;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private SchemaVersionStatus status;

    @Column(name = "effective_from", nullable = true)
    private Instant effectiveFrom;

    @Column(name = "effective_to", nullable = true)
    private Instant effectiveTo;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected IntegrationSchemaVersionJpaEntity() {
            // Required by JPA.
        }

        public IntegrationSchemaVersionJpaEntity(
                String id,
            String dataContractId,
            int versionNumber,
            String schemaDefinition,
            String checksum,
            SchemaVersionStatus status,
            Instant effectiveFrom,
            Instant effectiveTo,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.dataContractId = dataContractId;
        this.versionNumber = versionNumber;
        this.schemaDefinition = schemaDefinition;
        this.checksum = checksum;
        this.status = status;
        this.effectiveFrom = effectiveFrom;
        this.effectiveTo = effectiveTo;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String dataContractId() {
        return dataContractId;
    }


    public int versionNumber() {
        return versionNumber;
    }


    public String schemaDefinition() {
        return schemaDefinition;
    }


    public String checksum() {
        return checksum;
    }


    public SchemaVersionStatus status() {
        return status;
    }


    public Instant effectiveFrom() {
        return effectiveFrom;
    }


    public Instant effectiveTo() {
        return effectiveTo;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
