/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : FacilityTypeVersionJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for FacilityTypeVersion.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.entity;

import dz.sh.hidra.modules.topology.domain.value.*;
import jakarta.persistence.*;
import java.time.Instant;
@Entity
@Table(name = "hidra_topology_facility_type_version")
public class FacilityTypeVersionJpaEntity {

    @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;
    @Column(name = "facility_type_id", nullable = false, length = 80)
    private String facilityTypeId;
    @Column(name = "version_number", nullable = false)
    private int versionNumber;
    @Column(name = "definition_payload", nullable = true, columnDefinition = "jsonb")
    private String definitionPayload;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private SnapshotStatus status;
    @Column(name = "effective_from", nullable = true)
    private Instant effectiveFrom;
    @Column(name = "effective_to", nullable = true)
    private Instant effectiveTo;
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;
    protected FacilityTypeVersionJpaEntity() { }
    public FacilityTypeVersionJpaEntity(
            String id,
            String facilityTypeId,
            int versionNumber,
            String definitionPayload,
            SnapshotStatus status,
            Instant effectiveFrom,
            Instant effectiveTo,
            Instant createdAt,
            Instant updatedAt
    ) {
        this.id = id;
        this.facilityTypeId = facilityTypeId;
        this.versionNumber = versionNumber;
        this.definitionPayload = definitionPayload;
        this.status = status;
        this.effectiveFrom = effectiveFrom;
        this.effectiveTo = effectiveTo;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    public String id() { return id; }
    public String facilityTypeId() { return facilityTypeId; }
    public int versionNumber() { return versionNumber; }
    public String definitionPayload() { return definitionPayload; }
    public SnapshotStatus status() { return status; }
    public Instant effectiveFrom() { return effectiveFrom; }
    public Instant effectiveTo() { return effectiveTo; }
    public Instant createdAt() { return createdAt; }
    public Instant updatedAt() { return updatedAt; }
}
