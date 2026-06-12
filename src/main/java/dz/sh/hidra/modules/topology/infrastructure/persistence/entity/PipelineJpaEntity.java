/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PipelineJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for Pipeline.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.entity;

import dz.sh.hidra.modules.topology.domain.value.*;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
@Entity
@Table(name = "hidra_topology_pipeline")
public class PipelineJpaEntity {

    @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;
    @Column(name = "pipeline_system_id", nullable = false, length = 80)
    private String pipelineSystemId;
    @Column(name = "code", nullable = false, length = 120)
    private String code;
    @Column(name = "name_ar", nullable = true, length = 255)
    private String nameAr;
    @Column(name = "name_fr", nullable = true, length = 255)
    private String nameFr;
    @Column(name = "name_en", nullable = true, length = 255)
    private String nameEn;
    @Enumerated(EnumType.STRING)
    @Column(name = "pipeline_type", nullable = false, length = 80)
    private PipelineType pipelineType;
    @Column(name = "nominal_diameter", nullable = true, precision = 12, scale = 4)
    private BigDecimal nominalDiameter;
    @Column(name = "diameter_unit_code", nullable = true, length = 40)
    private String diameterUnitCode;
    @Column(name = "design_pressure", nullable = true, precision = 12, scale = 4)
    private BigDecimal designPressure;
    @Column(name = "pressure_unit_code", nullable = true, length = 40)
    private String pressureUnitCode;
    @Column(name = "total_length_km", nullable = true, precision = 12, scale = 4)
    private BigDecimal totalLengthKm;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private TopologyStatus status;
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;
    protected PipelineJpaEntity() { }
    public PipelineJpaEntity(
            String id,
            String pipelineSystemId,
            String code,
            String nameAr,
            String nameFr,
            String nameEn,
            PipelineType pipelineType,
            BigDecimal nominalDiameter,
            String diameterUnitCode,
            BigDecimal designPressure,
            String pressureUnitCode,
            BigDecimal totalLengthKm,
            TopologyStatus status,
            Instant createdAt,
            Instant updatedAt
    ) {
        this.id = id;
        this.pipelineSystemId = pipelineSystemId;
        this.code = code;
        this.nameAr = nameAr;
        this.nameFr = nameFr;
        this.nameEn = nameEn;
        this.pipelineType = pipelineType;
        this.nominalDiameter = nominalDiameter;
        this.diameterUnitCode = diameterUnitCode;
        this.designPressure = designPressure;
        this.pressureUnitCode = pressureUnitCode;
        this.totalLengthKm = totalLengthKm;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    public String id() { return id; }
    public String pipelineSystemId() { return pipelineSystemId; }
    public String code() { return code; }
    public String nameAr() { return nameAr; }
    public String nameFr() { return nameFr; }
    public String nameEn() { return nameEn; }
    public PipelineType pipelineType() { return pipelineType; }
    public BigDecimal nominalDiameter() { return nominalDiameter; }
    public String diameterUnitCode() { return diameterUnitCode; }
    public BigDecimal designPressure() { return designPressure; }
    public String pressureUnitCode() { return pressureUnitCode; }
    public BigDecimal totalLengthKm() { return totalLengthKm; }
    public TopologyStatus status() { return status; }
    public Instant createdAt() { return createdAt; }
    public Instant updatedAt() { return updatedAt; }
}
