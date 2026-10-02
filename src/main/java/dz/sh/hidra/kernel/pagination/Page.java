/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : Page
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Kernel
 * @Module      : kernel
 * @Package     : dz.sh.hidra.kernel.pagination
 *
 * @Description : Represents a framework-neutral paged result.
 *
 */
package dz.sh.hidra.kernel.pagination;

import java.util.List;
import java.util.Objects;

/**
 * Framework-independent page result.
 *
 * @param content page items
 * @param page current page index
 * @param size requested or actual page size
 * @param totalElements total matching items
 * @param totalPages total number of pages
 * @param <T> content item type
 */
public record Page<T>(
        List<T> content,
        int page,
        int size,
        long totalElements,
        int totalPages
) {

    public Page {
        content = List.copyOf(Objects.requireNonNull(content, "Page content must not be null."));
        if (page < 0) {
            throw new IllegalArgumentException("Page index must be greater than or equal to zero.");
        }
        if (size < 0) {
            throw new IllegalArgumentException("Page size must be greater than or equal to zero.");
        }
        if (totalElements < 0) {
            throw new IllegalArgumentException("Total elements must be greater than or equal to zero.");
        }
        if (totalPages < 0) {
            throw new IllegalArgumentException("Total pages must be greater than or equal to zero.");
        }
    }

    /**
     * Creates a page and calculates total pages.
     *
     * @param content page items
     * @param page current page index
     * @param size page size
     * @param totalElements total matching items
     * @param <T> content item type
     * @return page result
     */
    public static <T> Page<T> of(List<T> content, int page, int size, long totalElements) {
        int totalPages = calculateTotalPages(size, totalElements);
        return new Page<>(content, page, size, totalElements, totalPages);
    }

    private static int calculateTotalPages(int size, long totalElements) {
        if (size <= 0 || totalElements == 0) {
            return 0;
        }
        return (int) Math.ceil((double) totalElements / (double) size);
    }
}
