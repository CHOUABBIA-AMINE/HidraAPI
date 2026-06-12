/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ExternalSystemJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for ExternalSystem.
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
     * Database-backed JPA entity for ExternalSystem.
     */
    @Entity
    @Table(name = "hidra_integration_external_system")
    public class ExternalSystemJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "code", nullable = false, length = 120)
    private String code;

    @Column(name = "name_ar", nullable = true, length = 160)
    private String nameAr;

    @Column(name = "name_fr", nullable = false, length = 160)
    private String nameFr;

    @Column(name = "name_en", nullable = true, length = 160)
    private String nameEn;

    @Column(name = "system_type_id", nullable = false, length = 80)
    private String systemTypeId;

    @Column(name = "owner_organization_unit_id", nullable = true, length = 80)
    private String ownerOrganizationUnitId;

    @Enumerated(EnumType.STRING)
    @Column(name = "environment", nullable = false, length = 40)
    private IntegrationEnvironment environment;

    @Enumerated(EnumType.STRING)
    @Column(name = "criticality", nullable = false, length = 40)
    private IntegrationCriticality criticality;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private ExternalSystemStatus status;

    @Column(name = "description", nullable = true, length = 1000)
    private String description;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected ExternalSystemJpaEntity() {
            // Required by JPA.
        }

        public ExternalSystemJpaEntity(
                String id,
            String code,
            String nameAr,
            String nameFr,
            String nameEn,
            String systemTypeId,
            String ownerOrganizationUnitId,
            IntegrationEnvironment environment,
            IntegrationCriticality criticality,
            ExternalSystemStatus status,
            String description,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.code = code;
        this.nameAr = nameAr;
        this.nameFr = nameFr;
        this.nameEn = nameEn;
        this.systemTypeId = systemTypeId;
        this.ownerOrganizationUnitId = ownerOrganizationUnitId;
        this.environment = environment;
        this.criticality = criticality;
        this.status = status;
        this.description = description;
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


    public String systemTypeId() {
        return systemTypeId;
    }


    public String ownerOrganizationUnitId() {
        return ownerOrganizationUnitId;
    }


    public IntegrationEnvironment environment() {
        return environment;
    }


    public IntegrationCriticality criticality() {
        return criticality;
    }


    public ExternalSystemStatus status() {
        return status;
    }


    public String description() {
        return description;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
