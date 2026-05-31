/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TenantContextTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Platform Test
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.tenancy
 *
 * @Description : Verifies tenant context lifecycle.
 *
 */
package dz.sh.hidra.platform.tenancy;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TenantContextTest {

    @AfterEach
    void clear() {
        TenantContext.clear();
    }

    @Test
    void shouldStoreTrimAndClearTenant() {
        TenantContext.set(" tenant-a ");

        assertThat(TenantContext.current()).contains("tenant-a");
        TenantContext.clear();
        assertThat(TenantContext.current()).isEmpty();
    }
}
