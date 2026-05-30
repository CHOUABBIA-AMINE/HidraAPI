/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InvalidValueObjectExceptionTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Kernel Test
 * @Module      : kernel
 * @Package     : dz.sh.hidra.kernel.domain.exception
 *
 * @Description : Verifies invalid value object exception constructors.
 *
 */
package dz.sh.hidra.kernel.domain.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class InvalidValueObjectExceptionTest {

    @Test
    void shouldCreateExceptionWithMessage() {
        InvalidValueObjectException exception = new InvalidValueObjectException("Invalid value.");

        assertThat(exception).isInstanceOf(DomainException.class);
        assertThat(exception).hasMessage("Invalid value.");
    }

    @Test
    void shouldCreateExceptionWithMessageAndCause() {
        IllegalArgumentException cause = new IllegalArgumentException("cause");

        InvalidValueObjectException exception = new InvalidValueObjectException("Invalid value.", cause);

        assertThat(exception).hasMessage("Invalid value.");
        assertThat(exception).hasCause(cause);
    }
}
