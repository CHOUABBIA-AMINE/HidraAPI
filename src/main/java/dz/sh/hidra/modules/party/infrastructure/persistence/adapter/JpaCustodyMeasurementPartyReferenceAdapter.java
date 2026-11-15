/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : JpaCustodyMeasurementPartyReferenceAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-15
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.infrastructure.persistence.adapter
 * @Description : Checks exact active Party identity from Party-owned persistence.
 */
package dz.sh.hidra.modules.party.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.party.application.contract.custody.CustodyMeasurementPartyReferenceContract;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Repository
public class JpaCustodyMeasurementPartyReferenceAdapter implements CustodyMeasurementPartyReferenceContract {
    private final JdbcTemplate jdbc;
    public JpaCustodyMeasurementPartyReferenceAdapter(JdbcTemplate jdbc){this.jdbc=jdbc;}
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public boolean active(String id,String role) {
        if(id==null || id.isBlank() || role==null || role.isBlank())return false;
        return Boolean.TRUE.equals(jdbc.queryForObject("""
            SELECT EXISTS(SELECT 1 FROM public.hidra_party_party p JOIN public.hidra_party_role r ON r.id=? WHERE p.id=? AND p.status='ACTIVE' AND r.status='ACTIVE' AND r.code='SHIPPER')
            """,Boolean.class,role,id));
    }
}
