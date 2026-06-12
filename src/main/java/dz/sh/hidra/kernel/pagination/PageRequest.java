/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PageRequest
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Kernel
 * @Module      : kernel
 * @Package     : dz.sh.hidra.kernel.pagination
 *
 * @Description : Represents a framework-neutral pagination request.
 *
 */
package dz.sh.hidra.kernel.pagination;

/**
 * Immutable, framework-independent pagination request.
 *
 * @param page zero-based page index
 * @param size page size
 * @param sortField optional logical sort field
 * @param sortDirection optional sort direction
 */
public record PageRequest(
        int page,
        int size,
        String sortField,
        SortDirection sortDirection
) {

    public static final int MAX_PAGE_SIZE = 200;

    public PageRequest {
        if (page < 0) {
            throw new IllegalArgumentException("Page index must be greater than or equal to zero.");
        }
        if (size < 1 || size > MAX_PAGE_SIZE) {
            throw new IllegalArgumentException("Page size must be between 1 and " + MAX_PAGE_SIZE + ".");
        }
        sortField = normalize(sortField);
        if (sortField == null) {
            sortDirection = null;
        } else if (sortDirection == null) {
            sortDirection = SortDirection.ASC;
        }
    }

    /**
     * Creates an unsorted page request.
     *
     * @param page zero-based page index
     * @param size page size
     * @return page request
     */
    public static PageRequest of(int page, int size) {
        return new PageRequest(page, size, null, null);
    }

    /**
     * Creates a sorted page request.
     *
     * @param page zero-based page index
     * @param size page size
     * @param sortField logical sort field
     * @param sortDirection sort direction
     * @return page request
     */
    public static PageRequest sorted(int page, int size, String sortField, SortDirection sortDirection) {
        return new PageRequest(page, size, sortField, sortDirection);
    }

    private static String normalize(String text) {
        if (text == null || text.isBlank()) {
            return null;
        }
        return text.trim();
    }
}
