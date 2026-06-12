/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationDataContractJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for IntegrationDataContract.
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
     * Database-backed JPA entity for IntegrationDataContract.
     */
    @Entity
    @Table(name = "hidra_integration_data_contract")
    public class IntegrationDataContractJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "code", nullable = false, length = 120)
    private String code;

    @Column(name = "name_fr", nullable = false, length = 160)
    private String nameFr;

    @Column(name = "name_ar", nullable = true, length = 160)
    private String nameAr;

    @Column(name = "name_en", nullable = true, length = 160)
    private String nameEn;

    @Column(name = "contract_type_id", nullable = false, length = 80)
    private String contractTypeId;

    @Column(name = "payload_format_id", nullable = false, length = 80)
    private String payloadFormatId;

    @Column(name = "owning_target_module", nullable = true, length = 80)
    private String owningTargetModule;

    @Column(name = "description", nullable = true, length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private ContractStatus status;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected IntegrationDataContractJpaEntity() {
            // Required by JPA.
        }

        public IntegrationDataContractJpaEntity(
                String id,
            String code,
            String nameFr,
            String nameAr,
            String nameEn,
            String contractTypeId,
            String payloadFormatId,
            String owningTargetModule,
            String description,
            ContractStatus status,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.code = code;
        this.nameFr = nameFr;
        this.nameAr = nameAr;
        this.nameEn = nameEn;
        this.contractTypeId = contractTypeId;
        this.payloadFormatId = payloadFormatId;
        this.owningTargetModule = owningTargetModule;
        this.description = description;
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


    public String nameFr() {
        return nameFr;
    }


    public String nameAr() {
        return nameAr;
    }


    public String nameEn() {
        return nameEn;
    }


    public String contractTypeId() {
        return contractTypeId;
    }


    public String payloadFormatId() {
        return payloadFormatId;
    }


    public String owningTargetModule() {
        return owningTargetModule;
    }


    public String description() {
        return description;
    }


    public ContractStatus status() {
        return status;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
