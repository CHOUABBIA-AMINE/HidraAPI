/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : FacilityNodeBindingJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for FacilityNodeBinding.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.entity;

import dz.sh.hidra.modules.topology.domain.value.*;
import jakarta.persistence.*;
import java.time.Instant;
@Entity
@Table(name = "hidra_topology_facility_node_binding")
public class FacilityNodeBindingJpaEntity {

    @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;
    @Column(name = "facility_id", nullable = false, length = 80)
    private String facilityId;
    @Column(name = "node_id", nullable = false, length = 80)
    private String nodeId;
    @Column(name = "binding_role_code", nullable = true, length = 120)
    private String bindingRoleCode;
    @Column(name = "primary_binding", nullable = false)
    private boolean primaryBinding;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private TopologyStatus status;
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;
    protected FacilityNodeBindingJpaEntity() { }
    public FacilityNodeBindingJpaEntity(
            String id,
            String facilityId,
            String nodeId,
            String bindingRoleCode,
            boolean primaryBinding,
            TopologyStatus status,
            Instant createdAt,
            Instant updatedAt
    ) {
        this.id = id;
        this.facilityId = facilityId;
        this.nodeId = nodeId;
        this.bindingRoleCode = bindingRoleCode;
        this.primaryBinding = primaryBinding;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    public String id() { return id; }
    public String facilityId() { return facilityId; }
    public String nodeId() { return nodeId; }
    public String bindingRoleCode() { return bindingRoleCode; }
    public boolean primaryBinding() { return primaryBinding; }
    public TopologyStatus status() { return status; }
    public Instant createdAt() { return createdAt; }
    public Instant updatedAt() { return updatedAt; }
}
