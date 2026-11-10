/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JdbcPlanningCompanyEligibilityAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-10
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.infrastructure.persistence.adapter
 *
 * @Description : Checks current Party evidence in the protected Planning transaction.
 *
 */
package dz.sh.hidra.modules.party.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.party.application.contract.planning.PlanningCompanyEligibilityContract;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class JdbcPlanningCompanyEligibilityAdapter implements PlanningCompanyEligibilityContract {

    private final JdbcTemplate jdbc;

    public JdbcPlanningCompanyEligibilityAdapter(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public boolean isActiveCompany(String companyPartyId) {
        return !jdbc.queryForList("""
                SELECT id FROM public.hidra_party_party
                WHERE id = ? AND status = 'ACTIVE'
                FOR SHARE
                """, String.class, companyPartyId).isEmpty();
    }
}
