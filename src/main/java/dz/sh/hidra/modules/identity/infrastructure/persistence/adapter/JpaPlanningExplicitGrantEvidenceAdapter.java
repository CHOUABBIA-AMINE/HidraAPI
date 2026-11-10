/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaPlanningExplicitGrantEvidenceAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-10
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence.adapter
 *
 * @Description : Reads and locks Identity-owned direct grant evidence.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.identity.application.port.out.PlanningExplicitGrantEvidencePort;
import dz.sh.hidra.modules.identity.domain.value.ScopeType;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;
import dz.sh.hidra.platform.persistence.ProtectedRelationFence;
import org.springframework.dao.DataAccessException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class JpaPlanningExplicitGrantEvidenceAdapter implements PlanningExplicitGrantEvidencePort {

    private static final String AUTHORIZATION_RELATIONS = JdbcIdentityAuthorityFence.requiredRelationFences().stream()
            .filter(fence -> fence.mode() != ProtectedRelationFence.Mode.ACCESS_SHARE)
            .map(ProtectedRelationFence::relation)
            .sorted()
            .collect(Collectors.joining(", "));
    private final JdbcTemplate jdbc;

    public JpaPlanningExplicitGrantEvidenceAdapter(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void fenceCurrentAuthorization() {
        // Concurrent Planning writes may share this fence. Identity policy and grant mutations cannot.
        try {
            jdbc.execute("LOCK TABLE " + AUTHORIZATION_RELATIONS + " IN SHARE MODE");
            jdbc.execute("LOCK TABLE public.hidra_identity_external_mapping_generation IN ACCESS SHARE MODE");
        } catch (DataAccessException failure) {
            throw new AccessDeniedException("Planning authority unavailable.");
        }
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public List<GrantScope> candidates(String actor, String permission, Instant at) {
        var timestamp = OffsetDateTime.ofInstant(at, ZoneOffset.UTC);
        return jdbc.query("""
                SELECT g.scope_type, g.scope_reference_id
                FROM public.hidra_identity_user_permission_grant g
                JOIN public.hidra_identity_permission p ON p.id = g.permission_id
                WHERE g.user_id = ? AND p.code = ? AND p.status = 'ACTIVE'
                  AND g.status = 'ACTIVE' AND g.effect = 'GRANT' AND g.revoked_at IS NULL
                  AND g.valid_from <= ? AND (g.valid_to IS NULL OR g.valid_to > ?)
                  AND g.scope_type IN ('CUSTOM', 'PIPELINE_SYSTEM', 'PIPELINE', 'FACILITY')
                  AND g.scope_reference_id IS NOT NULL
                ORDER BY g.id
                FOR SHARE OF g, p
                """, (rs, row) -> new GrantScope(ScopeType.valueOf(rs.getString(1)), rs.getString(2)),
                actor, permission, timestamp, timestamp);
    }
}
