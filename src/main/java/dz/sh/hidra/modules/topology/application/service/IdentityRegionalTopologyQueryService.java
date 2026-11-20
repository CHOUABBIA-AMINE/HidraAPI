/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityRegionalTopologyQueryService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-20
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.service
 *
 * @Description : Provides T33 scoped regional resource access evidence.
 *
 */
package dz.sh.hidra.modules.topology.application.service;

import dz.sh.hidra.modules.topology.application.contract.identity.IdentityRegionalTopologyContract;
import dz.sh.hidra.modules.topology.application.port.out.RegionalTopologyRelationshipPort;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Service;

/** Owner-verified identity/lineage, not a permission or cached graph entitlement. */
@Service
public final class IdentityRegionalTopologyQueryService implements IdentityRegionalTopologyContract {
    private final RegionalTopologyRelationshipPort relationships;
    public IdentityRegionalTopologyQueryService(RegionalTopologyRelationshipPort relationships) {
        this.relationships = Objects.requireNonNull(relationships);
    }
    @Override
    public Optional<Resolution> resolve(Kind kind, String id, String company, Instant at) {
        if (kind == null || id == null || id.isBlank() || company == null || company.isBlank()
                || at == null || id.length() > 80 || company.length() > 80) return Optional.empty();
        try {
            return relationships.resolve(kind, id, company, at).filter(r ->
                    r.kind() == kind && id.equals(r.resourceId()) && company.equals(r.companyPartyId())
                            && r.ancestry().stream().allMatch(s -> valid(s, kind, id)));
        } catch (RuntimeException unavailable) {
            return Optional.empty();
        }
    }
    private static boolean valid(Scope scope, Kind kind, String id) {
        if (scope == null) return false;
        return switch (scope.type()) {
            case "PIPELINE_SYSTEM", "PIPELINE", "FACILITY", "EQUIPMENT" -> true;
            default -> false;
        };
    }
}
