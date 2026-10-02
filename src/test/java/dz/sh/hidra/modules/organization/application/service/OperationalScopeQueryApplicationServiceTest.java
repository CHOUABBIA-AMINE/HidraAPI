/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OperationalScopeQueryApplicationServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-29
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.service
 *
 * @Description : Verifies operational-scope reads resolve current owner display data.
 *
 */
package dz.sh.hidra.modules.organization.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import dz.sh.hidra.modules.organization.application.port.out.OperationalScopeRegistryRepositoryPort;
import dz.sh.hidra.modules.organization.application.port.out.OperationalScopeTargetResolverPort;
import dz.sh.hidra.modules.organization.domain.model.OperationalScope;
import dz.sh.hidra.modules.organization.domain.value.OperationalScopeType;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class OperationalScopeQueryApplicationServiceTest {

    @Test
    void entityBackedScopeUsesCurrentAuthoritativeOwnerDisplay() {
        OperationalScopeRegistryRepositoryPort scopes = mock(OperationalScopeRegistryRepositoryPort.class);
        OperationalScopeTargetResolverPort targets = mock(OperationalScopeTargetResolverPort.class);
        when(scopes.findById(42L))
                .thenReturn(Optional.of(new OperationalScope(42L, OperationalScopeType.PIPELINE, "pipeline-1")));
        when(targets.supports(OperationalScopeType.PIPELINE)).thenReturn(true);
        when(targets.resolve(OperationalScopeType.PIPELINE, "pipeline-1"))
                .thenReturn(Optional.of(new OperationalScopeTargetResolverPort.ResolvedTarget(
                        OperationalScopeType.PIPELINE,
                        "pipeline-1",
                        "PL-NEW",
                        "Current Name",
                        true
                )));

        var view = new OperationalScopeQueryApplicationService(scopes, targets).scope(42L);

        assertThat(view.id()).isEqualTo(42L);
        assertThat(view.code()).isEqualTo("PL-NEW");
        assertThat(view.name()).isEqualTo("Current Name");
        assertThat(view.assignable()).isTrue();
    }

    @Test
    void globalScopeHasNoInventedOwnerDisplay() {
        OperationalScopeRegistryRepositoryPort scopes = mock(OperationalScopeRegistryRepositoryPort.class);
        OperationalScopeTargetResolverPort targets = mock(OperationalScopeTargetResolverPort.class);
        when(scopes.findById(1L))
                .thenReturn(Optional.of(new OperationalScope(1L, OperationalScopeType.GLOBAL, null)));

        var view = new OperationalScopeQueryApplicationService(scopes, targets).scope(1L);

        assertThat(view.type()).isEqualTo(OperationalScopeType.GLOBAL);
        assertThat(view.targetId()).isNull();
        assertThat(view.code()).isNull();
        assertThat(view.name()).isNull();
        assertThat(view.assignable()).isTrue();
        verifyNoInteractions(targets);
    }
}
