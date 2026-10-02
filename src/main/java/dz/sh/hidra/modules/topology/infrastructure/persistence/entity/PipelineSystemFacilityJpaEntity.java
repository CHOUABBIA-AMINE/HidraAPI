/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PipelineSystemFacilityJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for PipelineSystemFacility.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.entity;

import dz.sh.hidra.modules.topology.domain.value.*;
import jakarta.persistence.*;
import java.time.Instant;
@Entity
@Table(name = "hidra_topology_pipeline_system_facility")
public class PipelineSystemFacilityJpaEntity {

    @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;
    @Column(name = "pipeline_system_id", nullable = false, length = 80)
    private String pipelineSystemId;
    @Column(name = "facility_id", nullable = false, length = 80)
    private String facilityId;
    @Column(name = "relationship_code", nullable = true, length = 120)
    private String relationshipCode;
    @Column(name = "valid_from", nullable = true)
    private Instant validFrom;
    @Column(name = "valid_to", nullable = true)
    private Instant validTo;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private TopologyStatus status;
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;
    protected PipelineSystemFacilityJpaEntity() { }
    public PipelineSystemFacilityJpaEntity(
            String id,
            String pipelineSystemId,
            String facilityId,
            String relationshipCode,
            Instant validFrom,
            Instant validTo,
            TopologyStatus status,
            Instant createdAt,
            Instant updatedAt
    ) {
        this.id = id;
        this.pipelineSystemId = pipelineSystemId;
        this.facilityId = facilityId;
        this.relationshipCode = relationshipCode;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    public String id() { return id; }
    public String pipelineSystemId() { return pipelineSystemId; }
    public String facilityId() { return facilityId; }
    public String relationshipCode() { return relationshipCode; }
    public Instant validFrom() { return validFrom; }
    public Instant validTo() { return validTo; }
    public TopologyStatus status() { return status; }
    public Instant createdAt() { return createdAt; }
    public Instant updatedAt() { return updatedAt; }
}
