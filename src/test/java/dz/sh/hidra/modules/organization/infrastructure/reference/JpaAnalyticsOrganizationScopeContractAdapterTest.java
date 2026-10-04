/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaAnalyticsOrganizationScopeContractAdapterTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Class
 * @Layer       : Organization Test
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.reference
 *
 * @Description : Verifies the Organization-owned Analytics scope contract.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.reference;

import dz.sh.hidra.modules.organization.infrastructure.persistence.repository.OrganizationUnitJpaRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class JpaAnalyticsOrganizationScopeContractAdapterTest {

    @Test
    void supportsOrganizationUnitAndFailsClosedForOtherScopeTypes() {
        var repository = mock(OrganizationUnitJpaRepository.class);
        var adapter = new JpaAnalyticsOrganizationScopeContractAdapter(repository);

        when(repository.existsById("unit-1")).thenReturn(true);

        var resolved = adapter.resolve("organization_unit", "unit-1");
        assertThat(resolved.supported()).isTrue();
        assertThat(resolved.exists()).isTrue();

        assertThat(adapter.resolve("PRODUCT", "product-1").supported()).isFalse();
    }
}
