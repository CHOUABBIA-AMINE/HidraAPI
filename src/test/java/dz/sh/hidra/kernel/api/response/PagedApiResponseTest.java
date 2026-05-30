/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PagedApiResponseTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Kernel Test
 * @Module      : kernel
 * @Package     : dz.sh.hidra.kernel.api.response
 *
 * @Description : Verifies paged response shape.
 *
 */
package dz.sh.hidra.kernel.api.response;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PagedApiResponseTest {

    @Test
    void shouldCreatePagedResponse() {
        PagedApiResponse<String> response = PagedApiResponse.of(List.of("a", "b"), 0, 2, 5, 3, " correlation-1 ");

        assertThat(response.data()).containsExactly("a", "b");
        assertThat(response.totalElements()).isEqualTo(5);
        assertThat(response.totalPages()).isEqualTo(3);
        assertThat(response.correlationId()).isEqualTo("correlation-1");
        assertThat(response.empty()).isFalse();
    }

    @Test
    void shouldDefensivelyCopyData() {
        List<String> data = new ArrayList<>();
        data.add("a");

        PagedApiResponse<String> response = PagedApiResponse.of(data, 0, 10, 1, 1, "c1");
        data.add("b");

        assertThat(response.data()).containsExactly("a");
        assertThatThrownBy(() -> response.data().add("c"))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void shouldRejectNegativePage() {
        assertThatThrownBy(() -> PagedApiResponse.empty(-1, 10, "c1"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Page index must not be negative.");
    }
}
