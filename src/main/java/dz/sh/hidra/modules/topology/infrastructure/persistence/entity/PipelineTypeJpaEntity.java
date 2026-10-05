/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PipelineTypeJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-05
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.entity
 *
 * @Description : Database-backed catalog entry for Pipeline classification.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "hidra_topology_pipeline_type")
public class PipelineTypeJpaEntity {

    @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "code", nullable = false, length = 120, unique = true)
    private String code;

    @Column(name = "name_ar", length = 255)
    private String nameAr;

    @Column(name = "name_fr", length = 255)
    private String nameFr;

    @Column(name = "name_en", length = 255)
    private String nameEn;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected PipelineTypeJpaEntity() { }

    public PipelineTypeJpaEntity(
            String id,
            String code,
            String nameAr,
            String nameFr,
            String nameEn,
            boolean active,
            Instant createdAt,
            Instant updatedAt
    ) {
        this.id = id;
        this.code = code;
        this.nameAr = nameAr;
        this.nameFr = nameFr;
        this.nameEn = nameEn;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String id() { return id; }
    public String code() { return code; }
    public String nameAr() { return nameAr; }
    public String nameFr() { return nameFr; }
    public String nameEn() { return nameEn; }
    public boolean active() { return active; }
    public Instant createdAt() { return createdAt; }
    public Instant updatedAt() { return updatedAt; }
}
