/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsAccessPolicyJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for AnalyticsAccessPolicy.
 *
 */
package dz.sh.hidra.modules.analytics.infrastructure.persistence.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for AnalyticsAccessPolicy.
     */
    @Entity
    @Table(name = "hidra_analytics_access_policy")
    public class AnalyticsAccessPolicyJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "analytics_object_type", nullable = false, length = 120)
    private String analyticsObjectType;

    @Column(name = "analytics_object_id", nullable = false, length = 120)
    private String analyticsObjectId;

    @Column(name = "access_scope_type", nullable = false, length = 80)
    private String accessScopeType;

    @Column(name = "access_scope_id", nullable = false, length = 120)
    private String accessScopeId;

    @Column(name = "permission_code", nullable = false, length = 120)
    private String permissionCode;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected AnalyticsAccessPolicyJpaEntity() {
            // Required by JPA.
        }

        public AnalyticsAccessPolicyJpaEntity(
                String id,
            String analyticsObjectType,
            String analyticsObjectId,
            String accessScopeType,
            String accessScopeId,
            String permissionCode,
            boolean active,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.analyticsObjectType = analyticsObjectType;
        this.analyticsObjectId = analyticsObjectId;
        this.accessScopeType = accessScopeType;
        this.accessScopeId = accessScopeId;
        this.permissionCode = permissionCode;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String analyticsObjectType() {
        return analyticsObjectType;
    }


    public String analyticsObjectId() {
        return analyticsObjectId;
    }


    public String accessScopeType() {
        return accessScopeType;
    }


    public String accessScopeId() {
        return accessScopeId;
    }


    public String permissionCode() {
        return permissionCode;
    }


    public boolean active() {
        return active;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
