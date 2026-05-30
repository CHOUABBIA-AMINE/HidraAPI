/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ValidationErrorDetailTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Kernel Test
 * @Module      : kernel
 * @Package     : dz.sh.hidra.kernel.api.error
 *
 * @Description : Verifies validation error detail shape.
 *
 */
package dz.sh.hidra.kernel.api.error;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ValidationErrorDetailTest {

    @Test
    void shouldNormalizeFields() {
        ValidationErrorDetail detail = new ValidationErrorDetail(" field ", " message ", " rejected ");

        assertThat(detail.field()).isEqualTo("field");
        assertThat(detail.message()).isEqualTo("message");
        assertThat(detail.rejectedValue()).isEqualTo("rejected");
    }

    @Test
    void shouldNormalizeBlankValuesToNull() {
        ValidationErrorDetail detail = new ValidationErrorDetail(" ", " ", " ");

        assertThat(detail.field()).isNull();
        assertThat(detail.message()).isNull();
        assertThat(detail.rejectedValue()).isNull();
    }
}
