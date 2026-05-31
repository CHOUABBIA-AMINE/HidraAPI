/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationScopeContextTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Platform Test
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.tenancy
 *
 * @Description : Verifies organization scope context lifecycle.
 *
 */
package dz.sh.hidra.platform.tenancy;

import dz.sh.hidra.kernel.domain.value.OrganizationScopeId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OrganizationScopeContextTest {

    @AfterEach
    void clear() {
        OrganizationScopeContext.clear();
    }

    @Test
    void shouldStoreKernelScopeAndClearIt() {
        OrganizationScopeContext.set(" scope-a ");

        assertThat(OrganizationScopeContext.current()).contains(OrganizationScopeId.of("scope-a"));
        OrganizationScopeContext.clear();
        assertThat(OrganizationScopeContext.current()).isEmpty();
    }
}
