/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PageResult
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Kernel
 * @Module      : kernel
 * @Package     : dz.sh.hidra.kernel.application.pagination
 *
 * @Description : Immutable framework-independent pagination result.
 *
 */
package dz.sh.hidra.kernel.application.pagination;

import java.util.List;

public record PageResult<T>(List<T> items, int page, int size, long totalElements, int totalPages) {

    public PageResult {
        if (items == null) {
            items = List.of();
        } else {
            items = List.copyOf(items);
        }
        if (page < 0) {
            throw new IllegalArgumentException("Page index must not be negative.");
        }
        if (size < 1) {
            throw new IllegalArgumentException("Page size must be at least 1.");
        }
        if (totalElements < 0) {
            throw new IllegalArgumentException("Total elements must not be negative.");
        }
        if (totalPages < 0) {
            throw new IllegalArgumentException("Total pages must not be negative.");
        }
    }

    public static <T> PageResult<T> of(List<T> items, int page, int size, long totalElements) {
        return new PageResult<>(items, page, size, totalElements, calculateTotalPages(totalElements, size));
    }

    public static <T> PageResult<T> empty(int page, int size) {
        return new PageResult<>(List.of(), page, size, 0, 0);
    }

    public boolean empty() {
        return items.isEmpty();
    }

    private static int calculateTotalPages(long totalElements, int size) {
        if (size < 1) {
            throw new IllegalArgumentException("Page size must be at least 1.");
        }
        if (totalElements == 0) {
            return 0;
        }
        return (int) Math.ceil((double) totalElements / size);
    }
}
