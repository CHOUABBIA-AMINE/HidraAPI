/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OperationResultTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Kernel Test
 * @Module      : kernel
 * @Package     : dz.sh.hidra.kernel.application.result
 *
 * @Description : Verifies operation result factories.
 *
 */
package dz.sh.hidra.kernel.application.result;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OperationResultTest {

    @Test
    void shouldCreateSuccessWithValue() {
        OperationResult<String> result = OperationResult.success("created");

        assertThat(result.status()).isEqualTo(ResultStatus.SUCCESS);
        assertThat(result.successful()).isTrue();
        assertThat(result.optionalValue()).contains("created");
    }

    @Test
    void shouldCreateFailureWithStatusAndErrorCode() {
        OperationResult<String> result = OperationResult.failure(ResultStatus.CONFLICT, "Already exists.", "DUPLICATE");

        assertThat(result.failed()).isTrue();
        assertThat(result.status()).isEqualTo(ResultStatus.CONFLICT);
        assertThat(result.optionalMessage()).contains("Already exists.");
        assertThat(result.optionalErrorCode()).contains("DUPLICATE");
    }

    @Test
    void shouldRejectFailureWithSuccessStatus() {
        assertThatThrownBy(() -> OperationResult.failure(ResultStatus.SUCCESS, "Invalid."))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Failure status must not be SUCCESS.");
    }
}
