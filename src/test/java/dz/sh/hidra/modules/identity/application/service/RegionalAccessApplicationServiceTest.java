/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RegionalAccessApplicationServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-20
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.service
 *
 * @Description : Verifies T33 regional read scope, deny precedence and owner evidence.
 *
 */
package dz.sh.hidra.modules.identity.application.service;

import dz.sh.hidra.modules.identity.application.contract.topology.TopologyCurrentActorContract;
import dz.sh.hidra.modules.identity.application.dto.PermissionDecisionDto;
import dz.sh.hidra.modules.identity.application.port.in.EvaluatePermissionUseCase;
import dz.sh.hidra.modules.identity.application.port.in.IdentityAdministrationQueryUseCase;
import dz.sh.hidra.modules.identity.application.port.in.ResolveRegionalAccessUseCase;
import dz.sh.hidra.modules.identity.application.query.EvaluatePermissionQuery;
import dz.sh.hidra.modules.identity.domain.value.AuthorizationDecisionValue;
import dz.sh.hidra.modules.organization.application.contract.identity.IdentityRegionalResponsibilityContract;
import dz.sh.hidra.modules.topology.application.contract.identity.IdentityRegionalTopologyContract;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class RegionalAccessApplicationServiceTest {
    private static final Instant NOW = Instant.parse("2026-11-20T09:00:00Z");
    private final TopologyCurrentActorContract actor = mock(TopologyCurrentActorContract.class);
    private final IdentityAdministrationQueryUseCase principals = mock(IdentityAdministrationQueryUseCase.class);
    private final EvaluatePermissionUseCase grant = mock(EvaluatePermissionUseCase.class);
    private final IdentityRegionalResponsibilityContract org = mock(IdentityRegionalResponsibilityContract.class);
    private final IdentityRegionalTopologyContract owner = mock(IdentityRegionalTopologyContract.class);
    private final RegionalAccessApplicationService service =
            new RegionalAccessApplicationService(actor, principals, grant, org, owner);
    private final ResolveRegionalAccessUseCase.Query query =
            new ResolveRegionalAccessUseCase.Query(ResolveRegionalAccessUseCase.ResourceKind.PIPELINE, "p", "co");

    @BeforeEach void givenLiveActorAndTwoQualifiedAncestors() {
        when(actor.currentActor()).thenReturn(Optional.of(new TopologyCurrentActorContract.Actor("u")));
        when(principals.principal("u", List.of())).thenReturn(
                new IdentityAdministrationQueryUseCase.PrincipalView("u", "LOCAL", "u", "u",
                        "Synthetic", "employee", List.of(), List.of()));
        when(owner.resolve(eq(IdentityRegionalTopologyContract.Kind.PIPELINE),
                eq("p"), eq("co"), any(Instant.class))).thenReturn(Optional.of(
                new IdentityRegionalTopologyContract.Resolution(
                        IdentityRegionalTopologyContract.Kind.PIPELINE, "p", "co",
                        List.of(new IdentityRegionalTopologyContract.Scope("PIPELINE_SYSTEM", "sys"),
                                new IdentityRegionalTopologyContract.Scope("PIPELINE", "p")))));
        when(org.eligibleScopes(eq("u"), eq("employee"), anyList(), any(Instant.class))).thenReturn(
                List.of(new IdentityRegionalResponsibilityContract.Scope("PIPELINE_SYSTEM", "sys")));
        when(grant.evaluate(any())).thenAnswer(invocation -> {
            EvaluatePermissionQuery request = invocation.getArgument(0);
            return "sys".equals(request.scope().scopeReferenceId())
                    ? decision(AuthorizationDecisionValue.PERMIT, "AUTHORITY_MATCHED")
                    : decision(AuthorizationDecisionValue.DENY, "NO_GRANT_MATCHED");
        });
    }

    private static PermissionDecisionDto decision(AuthorizationDecisionValue value, String reason) {
        return new PermissionDecisionDto(value == AuthorizationDecisionValue.PERMIT, value, reason, reason, NOW);
    }

    @Test void systemGrantAndOrganizationResponsibilityCoverActualChild() {
        assertTrue(service.permitted(query));
        verify(actor).currentActor();
        verify(owner).resolve(eq(IdentityRegionalTopologyContract.Kind.PIPELINE), eq("p"), eq("co"), any());
    }

    @Test void explicitChildDenyOverridesInheritedSystemPermit() {
        when(grant.evaluate(any())).thenAnswer(invocation -> {
            EvaluatePermissionQuery request = invocation.getArgument(0);
            return "p".equals(request.scope().scopeReferenceId())
                    ? decision(AuthorizationDecisionValue.DENY, "EXPLICIT_DENY")
                    : decision(AuthorizationDecisionValue.PERMIT, "AUTHORITY_MATCHED");
        });
        assertFalse(service.permitted(query));
    }

    @Test void revokedGrantCannotRemainCached() {
        assertTrue(service.permitted(query));
        when(grant.evaluate(any())).thenReturn(decision(AuthorizationDecisionValue.DENY, "NO_GRANT_MATCHED"));
        assertFalse(service.permitted(query));
    }

    @Test void noCurrentEmployeeResponsibilityIsNotEnough() {
        when(org.eligibleScopes(eq("u"), eq("employee"), anyList(), any())).thenReturn(List.of());
        assertFalse(service.permitted(query));
    }

    @Test void unlinkedCurrentUserIsRejectedBeforeOwnerLookup() {
        when(principals.principal("u", List.of())).thenReturn(
                new IdentityAdministrationQueryUseCase.PrincipalView("u", "LOCAL", "u", "u",
                        "Synthetic", null, List.of(), List.of()));
        assertFalse(service.permitted(query));
        verifyNoInteractions(owner);
    }

    @Test void conflictingCompanyOwnerAndProviderFailureAreNonDisclosing() {
        when(owner.resolve(eq(IdentityRegionalTopologyContract.Kind.PIPELINE),
                eq("p"), eq("co"), any())).thenReturn(Optional.of(
                new IdentityRegionalTopologyContract.Resolution(
                        IdentityRegionalTopologyContract.Kind.PIPELINE, "p", "other",
                        List.of(new IdentityRegionalTopologyContract.Scope("PIPELINE", "p")))));
        assertFalse(service.permitted(query));
        when(owner.resolve(eq(IdentityRegionalTopologyContract.Kind.PIPELINE),
                eq("p"), eq("co"), any())).thenThrow(new IllegalStateException("synthetic private owner detail"));
        assertFalse(service.permitted(query));
    }

    @Test void noCurrentSignedActorNeverReachesProtectedOwner() {
        when(actor.currentActor()).thenReturn(Optional.empty());
        assertFalse(service.permitted(query));
        verifyNoInteractions(owner);
    }
}
