/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsDataSourceReferenceJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for AnalyticsDataSourceReference.
 *
 */
package dz.sh.hidra.modules.analytics.infrastructure.persistence.entity;

import dz.sh.hidra.modules.analytics.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for AnalyticsDataSourceReference.
     */
    @Entity
    @Table(name = "hidra_analytics_data_source_reference")
    public class AnalyticsDataSourceReferenceJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "source_module", nullable = false, length = 80)
    private String sourceModule;

    @Column(name = "source_type", nullable = false, length = 120)
    private String sourceType;

    @Column(name = "source_name", nullable = false, length = 160)
    private String sourceName;

    @Column(name = "source_version", nullable = true, length = 80)
    private String sourceVersion;

    @Enumerated(EnumType.STRING)
    @Column(name = "access_mode", nullable = false, length = 40)
    private AnalyticsAccessMode accessMode;

    @Enumerated(EnumType.STRING)
    @Column(name = "refresh_mode", nullable = false, length = 40)
    private AnalyticsRefreshMode refreshMode;

    @Column(name = "trusted", nullable = false)
    private boolean trusted;

    @Column(name = "last_available_at", nullable = true)
    private Instant lastAvailableAt;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected AnalyticsDataSourceReferenceJpaEntity() {
            // Required by JPA.
        }

        public AnalyticsDataSourceReferenceJpaEntity(
                String id,
            String sourceModule,
            String sourceType,
            String sourceName,
            String sourceVersion,
            AnalyticsAccessMode accessMode,
            AnalyticsRefreshMode refreshMode,
            boolean trusted,
            Instant lastAvailableAt,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.sourceModule = sourceModule;
        this.sourceType = sourceType;
        this.sourceName = sourceName;
        this.sourceVersion = sourceVersion;
        this.accessMode = accessMode;
        this.refreshMode = refreshMode;
        this.trusted = trusted;
        this.lastAvailableAt = lastAvailableAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String sourceModule() {
        return sourceModule;
    }


    public String sourceType() {
        return sourceType;
    }


    public String sourceName() {
        return sourceName;
    }


    public String sourceVersion() {
        return sourceVersion;
    }


    public AnalyticsAccessMode accessMode() {
        return accessMode;
    }


    public AnalyticsRefreshMode refreshMode() {
        return refreshMode;
    }


    public boolean trusted() {
        return trusted;
    }


    public Instant lastAvailableAt() {
        return lastAvailableAt;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
