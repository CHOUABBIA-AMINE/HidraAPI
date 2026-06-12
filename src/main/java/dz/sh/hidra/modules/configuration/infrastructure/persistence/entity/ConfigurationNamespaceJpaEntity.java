/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ConfigurationNamespaceJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for ConfigurationNamespace.
 *
 */
package dz.sh.hidra.modules.configuration.infrastructure.persistence.entity;

import dz.sh.hidra.modules.configuration.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for ConfigurationNamespace.
     */
    @Entity
    @Table(name = "hidra_configuration_namespace")
    public class ConfigurationNamespaceJpaEntity {

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

    @Column(name = "owner_module", nullable = true, length = 80)
    private String ownerModule;

    @Column(name = "owner_team", nullable = true, length = 160)
    private String ownerTeam;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private ConfigurationNamespaceStatus status;

    @Column(name = "description", nullable = true, length = 1000)
    private String description;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected ConfigurationNamespaceJpaEntity() {
            // Required by JPA.
        }

        public ConfigurationNamespaceJpaEntity(
                String id,
            String code,
            String nameFr,
            String nameAr,
            String nameEn,
            String ownerModule,
            String ownerTeam,
            ConfigurationNamespaceStatus status,
            String description,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.code = code;
        this.nameFr = nameFr;
        this.nameAr = nameAr;
        this.nameEn = nameEn;
        this.ownerModule = ownerModule;
        this.ownerTeam = ownerTeam;
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


    public String nameFr() {
        return nameFr;
    }


    public String nameAr() {
        return nameAr;
    }


    public String nameEn() {
        return nameEn;
    }


    public String ownerModule() {
        return ownerModule;
    }


    public String ownerTeam() {
        return ownerTeam;
    }


    public ConfigurationNamespaceStatus status() {
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
