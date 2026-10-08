/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningUnitReferenceQueryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.infrastructure.integration
 *
 * @Description : Enforces owner-controlled Nomination reference integrity.
 *
 */
package dz.sh.hidra.modules.telemetry.infrastructure.integration;

import dz.sh.hidra.modules.telemetry.application.contract.planning.PlanningUnitReferenceContract;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.repository.TelemetryUnitJpaRepository;
import java.util.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

@Component("planningUnitReferenceQueryAdapter")
public class PlanningUnitReferenceQueryAdapter implements PlanningUnitReferenceContract {
    private final TelemetryUnitJpaRepository repository;
    private final JdbcTemplate jdbc;
    public PlanningUnitReferenceQueryAdapter(TelemetryUnitJpaRepository repository,JdbcTemplate jdbc) {
        this.repository=Objects.requireNonNull(repository);this.jdbc=Objects.requireNonNull(jdbc);
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Optional<Units> resolve(String quantityUnitId,String rateUnitId) {
        if(quantityUnitId==null || quantityUnitId.isBlank() || (rateUnitId!=null && rateUnitId.isBlank())) return Optional.empty();
        var ids=new TreeSet<String>(); ids.add(quantityUnitId); if(rateUnitId!=null) ids.add(rateUnitId);
        var units=new HashMap<String,dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity.TelemetryUnitJpaEntity>();
        for(String id:ids) {
            var unit=repository.findByIdForShare(id).orElse(null);
            if(unit==null || !id.equals(unit.id())) return Optional.empty();
            units.put(id,unit);
        }
        var quantity=qualified(units.get(quantityUnitId),"QUANTITY");
        if(quantity==null) return Optional.empty();
        if(rateUnitId==null) return Optional.of(new Units(quantity,null,true));
        var rate=qualified(units.get(rateUnitId),"RATE");
        if(rate==null) return Optional.empty();
        var pairs=jdbc.query("SELECT active FROM hidra_telemetry_planning_unit_pair WHERE quantity_unit_id=? AND rate_unit_id=? FOR SHARE",
                (rs,n)->rs.getBoolean("active"),quantityUnitId,rateUnitId);
        return pairs.size()==1 ? Optional.of(new Units(quantity,rate,pairs.get(0))) : Optional.empty();
    }
    private Unit qualified(dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity.TelemetryUnitJpaEntity unit,String role) {
        var roles=jdbc.query("SELECT active FROM hidra_telemetry_planning_unit_role WHERE unit_id=? AND usage_role=? FOR SHARE",
                (rs,n)->rs.getBoolean("active"),unit.id(),role);
        if(roles.size()!=1) return null;
        return new Unit(unit.id(),unit.code(),unit.symbol(),unit.dimension(),unit.active() && roles.get(0));
    }
}
