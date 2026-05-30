/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationScopeIdTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Kernel Test
 * @Module      : kernel
 * @Package     : dz.sh.hidra.kernel.domain.value
 *
 * @Description : Verifies organization scope ID creation and invalid input rejection.
 *
 */
package dz.sh.hidra.kernel.domain.value;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrganizationScopeIdTest {

    @Test
    void shouldTrimProvidedValue() {
        OrganizationScopeId scopeId = OrganizationScopeId.of("  scope-1  ");

        assertThat(scopeId.value()).isEqualTo("scope-1");
    }

    @Test
    void shouldCreateGeneratedId() {
        OrganizationScopeId scopeId = OrganizationScopeId.newId();

        assertThat(scopeId.value()).isNotBlank();
    }

    @Test
    void shouldRejectNullValue() {
        assertThatThrownBy(() -> OrganizationScopeId.of(null))
                .isInstanceOf(InvalidValueObjectException.class)
                .hasMessage("OrganizationScopeId must not be blank.");
    }
}
