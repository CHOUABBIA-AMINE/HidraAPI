/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PageRequestTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Kernel Test
 * @Module      : kernel
 * @Package     : dz.sh.hidra.kernel.application.pagination
 *
 * @Description : Verifies pagination request validation.
 *
 */
package dz.sh.hidra.kernel.application.pagination;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PageRequestTest {

    @Test
    void shouldCreateUnsortedRequest() {
        PageRequest request = PageRequest.of(0, 25);

        assertThat(request.page()).isZero();
        assertThat(request.size()).isEqualTo(25);
        assertThat(request.sorted()).isFalse();
        assertThat(request.optionalSortField()).isEmpty();
    }

    @Test
    void shouldCreateSortedRequestAndDefaultDirection() {
        PageRequest request = PageRequest.sorted(1, 50, " name ", null);

        assertThat(request.sorted()).isTrue();
        assertThat(request.sortField()).isEqualTo("name");
        assertThat(request.sortDirection()).isEqualTo(SortDirection.ASC);
    }

    @Test
    void shouldRejectInvalidPageSize() {
        assertThatThrownBy(() -> PageRequest.of(0, PageRequest.MAX_PAGE_SIZE + 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Page size must not exceed 200.");
    }
}
