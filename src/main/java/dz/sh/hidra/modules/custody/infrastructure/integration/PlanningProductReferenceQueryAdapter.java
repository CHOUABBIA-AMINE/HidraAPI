/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningProductReferenceQueryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.infrastructure.integration
 *
 * @Description : Enforces owner-controlled Nomination reference integrity.
 *
 */
package dz.sh.hidra.modules.custody.infrastructure.integration;

import dz.sh.hidra.modules.custody.application.contract.planning.PlanningProductReferenceContract;
import dz.sh.hidra.modules.custody.infrastructure.persistence.repository.CustodyCatalogEntryJpaRepository;
import java.util.Optional;
import java.util.Objects;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

@Component("planningProductReferenceQueryAdapter")
public class PlanningProductReferenceQueryAdapter implements PlanningProductReferenceContract {
    private final CustodyCatalogEntryJpaRepository repository;
    private final JdbcTemplate jdbc;
    public PlanningProductReferenceQueryAdapter(CustodyCatalogEntryJpaRepository repository, JdbcTemplate jdbc) {
        this.repository=Objects.requireNonNull(repository); this.jdbc=Objects.requireNonNull(jdbc);
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Optional<Product> resolve(String id) {
        if(id==null || id.isBlank()) return Optional.empty();
        var entry=repository.findByIdForShare(id).orElse(null);
        if(entry==null || !id.equals(entry.id())) return Optional.empty();
        var approvals=jdbc.query("SELECT active FROM hidra_custody_planning_product_policy WHERE catalog_entry_id=? FOR SHARE",
                (rs,n)->rs.getBoolean("active"),id);
        if(approvals.size()!=1) return Optional.empty();
        return Optional.of(new Product(entry.id(),entry.code(),entry.active() && approvals.get(0)));
    }
}
