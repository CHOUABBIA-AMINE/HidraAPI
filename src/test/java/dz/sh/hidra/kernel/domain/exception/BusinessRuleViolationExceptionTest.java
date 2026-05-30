/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : BusinessRuleViolationExceptionTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Kernel Test
 * @Module      : kernel
 * @Package     : dz.sh.hidra.kernel.domain.exception
 *
 * @Description : Verifies business rule exception constructors.
 *
 */
package dz.sh.hidra.kernel.domain.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BusinessRuleViolationExceptionTest {

    @Test
    void shouldCreateExceptionWithMessage() {
        BusinessRuleViolationException exception = new BusinessRuleViolationException("Broken rule.");

        assertThat(exception).isInstanceOf(DomainException.class);
        assertThat(exception).hasMessage("Broken rule.");
    }

    @Test
    void shouldCreateExceptionWithMessageAndCause() {
        IllegalStateException cause = new IllegalStateException("cause");

        BusinessRuleViolationException exception = new BusinessRuleViolationException("Broken rule.", cause);

        assertThat(exception).hasMessage("Broken rule.");
        assertThat(exception).hasCause(cause);
    }
}
