/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakDetectionMethodCatalogJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for LeakDetectionMethodCatalog.
 *
 */
package dz.sh.hidra.modules.leakdetection.infrastructure.persistence.entity;

import dz.sh.hidra.modules.leakdetection.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for LeakDetectionMethodCatalog.
     */
    @Entity
    @Table(name = "hidra_leak_detection_method")
    public class LeakDetectionMethodCatalogJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "code", nullable = false, length = 80)
    private String code;

    @Column(name = "method_family", nullable = false, length = 120)
    private String methodFamily;

    @Column(name = "description", nullable = true, columnDefinition = "text")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private LeakDetectionMethodStatus status;

    @Column(name = "system_defined", nullable = false)
    private boolean systemDefined;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected LeakDetectionMethodCatalogJpaEntity() {
            // Required by JPA.
        }

        public LeakDetectionMethodCatalogJpaEntity(
                String id,
            String code,
            String methodFamily,
            String description,
            LeakDetectionMethodStatus status,
            boolean systemDefined,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.code = code;
        this.methodFamily = methodFamily;
        this.description = description;
        this.status = status;
        this.systemDefined = systemDefined;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String code() {
        return code;
    }


    public String methodFamily() {
        return methodFamily;
    }


    public String description() {
        return description;
    }


    public LeakDetectionMethodStatus status() {
        return status;
    }


    public boolean systemDefined() {
        return systemDefined;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
