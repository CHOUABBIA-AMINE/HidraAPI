/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : JdbcCustodyMeasurementAssetOwnershipAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-15
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.adapter
 * @Description : Resolves one active Topology facility ownership under the current transaction.
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.topology.application.contract.custody.CustodyMeasurementAssetOwnershipContract;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Repository
public class JdbcCustodyMeasurementAssetOwnershipAdapter implements CustodyMeasurementAssetOwnershipContract {
    private final JdbcTemplate jdbc;
    public JdbcCustodyMeasurementAssetOwnershipAdapter(JdbcTemplate jdbc){this.jdbc=jdbc;}
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Optional<String> activeOwner(String type,String id){
        if(!"FACILITY".equals(type) || id==null || id.isBlank())return Optional.empty();
        var owners=jdbc.queryForList("""
            SELECT owner_party_id FROM public.hidra_topology_facility
             WHERE id=? AND status='ACTIVE' AND owner_party_id IS NOT NULL FOR SHARE
            """,String.class,id);
        return owners.size()==1?Optional.of(owners.getFirst()):Optional.empty();
    }
}
