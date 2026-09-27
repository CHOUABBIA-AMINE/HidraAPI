/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuthoritativeOperationalScopeTargetResolverAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-27
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.adapter
 *
 * @Description : Resolves operational-scope targets through authoritative owner read contracts.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.adapter;

import dz.sh.hidra.modules.organization.application.port.out.OperationalScopeTargetResolverPort;
import dz.sh.hidra.modules.organization.application.port.out.OrganizationUnitRepositoryPort;
import dz.sh.hidra.modules.organization.domain.model.OrganizationUnit;
import dz.sh.hidra.modules.organization.domain.value.OperationalScopeType;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitStatus;
import dz.sh.hidra.modules.topology.application.port.in.ResolveTopologyOperationalScopeTargetUseCase;
import dz.sh.hidra.modules.topology.application.port.in.ResolveTopologyOperationalScopeTargetUseCase.TargetView;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Authoritative operational-scope resolver for currently implemented owner types.
 *
 * <p>Organization units are owned locally. Topology-owned targets are resolved only
 * through topology's public application input port. No topology domain model,
 * repository, persistence entity or infrastructure type crosses this boundary.</p>
 */
@Component
public final class AuthoritativeOperationalScopeTargetResolverAdapter
        implements OperationalScopeTargetResolverPort {

    private final OrganizationUnitRepositoryPort organizationUnitRepositoryPort;
    private final ResolveTopologyOperationalScopeTargetUseCase topologyTargetUseCase;

    public AuthoritativeOperationalScopeTargetResolverAdapter(
            OrganizationUnitRepositoryPort organizationUnitRepositoryPort,
            ResolveTopologyOperationalScopeTargetUseCase topologyTargetUseCase
    ) {
        this.organizationUnitRepositoryPort = Objects.requireNonNull(
                organizationUnitRepositoryPort,
                "Organization unit repository port must not be null."
        );
        this.topologyTargetUseCase = Objects.requireNonNull(
                topologyTargetUseCase,
                "Topology operational-scope target use case must not be null."
        );
    }

    @Override
    public boolean supports(OperationalScopeType type) {
        return type == OperationalScopeType.ORGANIZATION_UNIT
                || type == OperationalScopeType.PIPELINE_SYSTEM
                || type == OperationalScopeType.PIPELINE
                || type == OperationalScopeType.FACILITY
                || type == OperationalScopeType.EQUIPMENT;
    }

    @Override
    public Optional<ResolvedTarget> resolve(
            OperationalScopeType type,
            String targetId
    ) {
        if (!supports(type) || targetId == null || targetId.isBlank()) {
            return Optional.empty();
        }

        String normalizedTargetId = targetId.trim();

        return switch (type) {
            case ORGANIZATION_UNIT ->
                    organizationUnitRepositoryPort.findById(normalizedTargetId)
                            .map(this::fromOrganizationUnit);
            case PIPELINE_SYSTEM ->
                    topologyTargetUseCase.resolvePipelineSystem(normalizedTargetId)
                            .map(target -> fromTopology(type, target));
            case PIPELINE ->
                    topologyTargetUseCase.resolvePipeline(normalizedTargetId)
                            .map(target -> fromTopology(type, target));
            case FACILITY ->
                    topologyTargetUseCase.resolveFacility(normalizedTargetId)
                            .map(target -> fromTopology(type, target));
            case EQUIPMENT ->
                    topologyTargetUseCase.resolveEquipment(normalizedTargetId)
                            .map(target -> fromTopology(type, target));
            case GLOBAL, CUSTOM -> Optional.empty();
        };
    }

    private ResolvedTarget fromOrganizationUnit(OrganizationUnit unit) {
        return new ResolvedTarget(
                OperationalScopeType.ORGANIZATION_UNIT,
                unit.id(),
                unit.code(),
                currentName(unit.nameEn(), unit.nameFr(), unit.nameAr()),
                unit.status() == OrganizationUnitStatus.ACTIVE
        );
    }

    private static ResolvedTarget fromTopology(
            OperationalScopeType type,
            TargetView target
    ) {
        return new ResolvedTarget(
                type,
                target.id(),
                target.code(),
                target.name(),
                target.assignable()
        );
    }

    private static String currentName(
            String nameEn,
            String nameFr,
            String nameAr
    ) {
        if (nameEn != null && !nameEn.isBlank()) {
            return nameEn.trim();
        }
        if (nameFr != null && !nameFr.isBlank()) {
            return nameFr.trim();
        }
        if (nameAr != null && !nameAr.isBlank()) {
            return nameAr.trim();
        }
        return null;
    }
}
