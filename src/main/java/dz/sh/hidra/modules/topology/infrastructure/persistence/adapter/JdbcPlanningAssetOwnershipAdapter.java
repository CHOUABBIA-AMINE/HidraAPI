/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JdbcPlanningAssetOwnershipAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-10
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.adapter
 *
 * @Description : Locks current asset ownership within the Planning transaction.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.topology.application.contract.planning.PlanningAssetOwnershipContract;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class JdbcPlanningAssetOwnershipAdapter implements PlanningAssetOwnershipContract {

    private final JdbcTemplate jdbc;

    public JdbcPlanningAssetOwnershipAdapter(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Optional<String> activeOwner(String type, String id) {
        String sql = switch (type) {
            case "PIPELINE_SYSTEM" -> """
                    SELECT o.company_party_id FROM public.hidra_topology_pipeline_system s
                    JOIN public.hidra_topology_foundation_system_owner o ON o.pipeline_system_id = s.id
                    WHERE s.id = ? AND s.status = 'ACTIVE'
                    FOR SHARE OF s
                    """;
            case "PIPELINE" -> """
                    SELECT o.company_party_id FROM public.hidra_topology_pipeline p
                    JOIN public.hidra_topology_pipeline_system s ON s.id = p.pipeline_system_id
                    JOIN public.hidra_topology_foundation_system_owner o ON o.pipeline_system_id = s.id
                    WHERE p.id = ? AND p.status = 'ACTIVE' AND s.status = 'ACTIVE'
                    FOR SHARE OF p, s
                    """;
            case "FACILITY" -> """
                    SELECT owner_party_id FROM public.hidra_topology_facility
                    WHERE id = ? AND status = 'ACTIVE' AND owner_party_id IS NOT NULL
                    FOR SHARE
                    """;
            default -> null;
        };
        if (sql == null) {
            return Optional.empty();
        }
        var owners = jdbc.queryForList(sql, String.class, id);
        return owners.size() == 1 ? Optional.of(owners.getFirst()) : Optional.empty();
    }
}
