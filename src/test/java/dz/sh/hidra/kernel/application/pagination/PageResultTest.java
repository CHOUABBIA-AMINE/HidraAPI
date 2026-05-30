/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PageResultTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Kernel Test
 * @Module      : kernel
 * @Package     : dz.sh.hidra.kernel.application.pagination
 *
 * @Description : Verifies pagination result creation.
 *
 */
package dz.sh.hidra.kernel.application.pagination;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PageResultTest {

    @Test
    void shouldCreatePageResultAndCalculateTotalPages() {
        PageResult<String> result = PageResult.of(List.of("a", "b"), 0, 2, 5);

        assertThat(result.items()).containsExactly("a", "b");
        assertThat(result.totalPages()).isEqualTo(3);
        assertThat(result.empty()).isFalse();
    }

    @Test
    void shouldDefensivelyCopyItems() {
        List<String> items = new ArrayList<>();
        items.add("a");

        PageResult<String> result = PageResult.of(items, 0, 10, 1);
        items.add("b");

        assertThat(result.items()).containsExactly("a");
        assertThatThrownBy(() -> result.items().add("c"))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void shouldCreateEmptyResult() {
        PageResult<String> result = PageResult.empty(0, 10);

        assertThat(result.empty()).isTrue();
        assertThat(result.totalElements()).isZero();
    }
}
