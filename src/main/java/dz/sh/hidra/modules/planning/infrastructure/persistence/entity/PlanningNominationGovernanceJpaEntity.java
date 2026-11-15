/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningNominationGovernanceJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-15
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.infrastructure.persistence.entity
 *
 * @Description : Maps the governance marker used by the scoped nomination queries.
 *
 */
package dz.sh.hidra.modules.planning.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "hidra_planning_nomination_governance")
public class PlanningNominationGovernanceJpaEntity {
    @Id
    @Column(name = "nomination_id", nullable = false, length = 80)
    private String nominationId;

    @Column(name = "company_id", nullable = false, length = 80)
    private String companyId;

    @Column(name = "source_facility_id", nullable = false, length = 80)
    private String sourceFacilityId;

    @Column(name = "destination_facility_id", nullable = false, length = 80)
    private String destinationFacilityId;

    protected PlanningNominationGovernanceJpaEntity() { }
}
