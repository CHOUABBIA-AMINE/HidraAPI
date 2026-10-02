/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PermissionJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for Permission.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence.entity;

import dz.sh.hidra.modules.identity.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for Permission.
     */
    @Entity
    @Table(name = "hidra_identity_permission")
    public class PermissionJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "code", nullable = false, length = 160)
    private String code;

    @Column(name = "name_ar", nullable = true, length = 255)
    private String nameAr;

    @Column(name = "name_fr", nullable = true, length = 255)
    private String nameFr;

    @Column(name = "name_en", nullable = true, length = 255)
    private String nameEn;

    @Column(name = "description", nullable = true, columnDefinition = "text")
    private String description;

    @Column(name = "permission_domain", nullable = false, length = 120)
    private String permissionDomain;

    @Column(name = "resource_type", nullable = true, length = 120)
    private String resourceType;

    @Column(name = "action", nullable = false, length = 80)
    private String action;

    @Column(name = "sensitive", nullable = false)
    private boolean sensitive;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private PermissionStatus status;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected PermissionJpaEntity() {
            // Required by JPA.
        }

        public PermissionJpaEntity(
                String id,
            String code,
            String nameAr,
            String nameFr,
            String nameEn,
            String description,
            String permissionDomain,
            String resourceType,
            String action,
            boolean sensitive,
            PermissionStatus status,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.code = code;
        this.nameAr = nameAr;
        this.nameFr = nameFr;
        this.nameEn = nameEn;
        this.description = description;
        this.permissionDomain = permissionDomain;
        this.resourceType = resourceType;
        this.action = action;
        this.sensitive = sensitive;
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


    public String description() {
        return description;
    }


    public String permissionDomain() {
        return permissionDomain;
    }


    public String resourceType() {
        return resourceType;
    }


    public String action() {
        return action;
    }


    public boolean sensitive() {
        return sensitive;
    }


    public PermissionStatus status() {
        return status;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
