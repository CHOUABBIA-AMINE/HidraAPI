/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PipelineSystemJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for PipelineSystem.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.entity;

import dz.sh.hidra.modules.topology.domain.value.*;
import jakarta.persistence.*;
import java.time.Instant;
@Entity
@Table(name = "hidra_topology_pipeline_system")
public class PipelineSystemJpaEntity {

    @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;
    @Column(name = "code", nullable = false, length = 120)
    private String code;
    @Column(name = "name_ar", nullable = true, length = 255)
    private String nameAr;
    @Column(name = "name_fr", nullable = true, length = 255)
    private String nameFr;
    @Column(name = "name_en", nullable = true, length = 255)
    private String nameEn;
    @Enumerated(EnumType.STRING)
    @Column(name = "system_type", nullable = false, length = 80)
    private PipelineSystemType systemType;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private TopologyStatus status;
    @Column(name = "description", nullable = true, columnDefinition = "text")
    private String description;
    @Column(name = "commissioned_at", nullable = true)
    private Instant commissionedAt;
    @Column(name = "retired_at", nullable = true)
    private Instant retiredAt;
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;
    protected PipelineSystemJpaEntity() { }
    public PipelineSystemJpaEntity(
            String id,
            String code,
            String nameAr,
            String nameFr,
            String nameEn,
            PipelineSystemType systemType,
            TopologyStatus status,
            String description,
            Instant commissionedAt,
            Instant retiredAt,
            Instant createdAt,
            Instant updatedAt
    ) {
        this.id = id;
        this.code = code;
        this.nameAr = nameAr;
        this.nameFr = nameFr;
        this.nameEn = nameEn;
        this.systemType = systemType;
        this.status = status;
        this.description = description;
        this.commissionedAt = commissionedAt;
        this.retiredAt = retiredAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    public String id() { return id; }
    public String code() { return code; }
    public String nameAr() { return nameAr; }
    public String nameFr() { return nameFr; }
    public String nameEn() { return nameEn; }
    public PipelineSystemType systemType() { return systemType; }
    public TopologyStatus status() { return status; }
    public String description() { return description; }
    public Instant commissionedAt() { return commissionedAt; }
    public Instant retiredAt() { return retiredAt; }
    public Instant createdAt() { return createdAt; }
    public Instant updatedAt() { return updatedAt; }
}
