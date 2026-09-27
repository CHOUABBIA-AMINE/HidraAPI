/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OperationalScopeJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-27
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.persistence.entity
 *
 * @Description : Database entity for the canonical operational-scope registry.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.persistence.entity;

import dz.sh.hidra.modules.organization.domain.value.OperationalScopeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Persistence representation of one canonical typed scope target.
 *
 * <p>Current code/name are deliberately absent because they remain authoritative
 * in the target owning module.</p>
 */
@Entity
@Table(name = "hidra_org_operational_scope")
public class OperationalScopeJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "scope_type", nullable = false, length = 80)
    private OperationalScopeType scopeType;

    @Column(name = "target_id", length = 120)
    private String targetId;

    protected OperationalScopeJpaEntity() {
        // Required by JPA.
    }

    public OperationalScopeJpaEntity(
            Long id,
            OperationalScopeType scopeType,
            String targetId
    ) {
        this.id = id;
        this.scopeType = scopeType;
        this.targetId = targetId;
    }

    public Long id() {
        return id;
    }

    public OperationalScopeType scopeType() {
        return scopeType;
    }

    public String targetId() {
        return targetId;
    }
}
