/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PageRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Kernel
 * @Module      : kernel
 * @Package     : dz.sh.hidra.kernel.application.pagination
 *
 * @Description : Immutable framework-independent pagination request.
 *
 */
package dz.sh.hidra.kernel.application.pagination;

import java.util.Optional;

public record PageRequest(int page, int size, String sortField, SortDirection sortDirection) {

    public static final int MAX_PAGE_SIZE = 200;

    public PageRequest {
        if (page < 0) {
            throw new IllegalArgumentException("Page index must not be negative.");
        }
        if (size < 1) {
            throw new IllegalArgumentException("Page size must be at least 1.");
        }
        if (size > MAX_PAGE_SIZE) {
            throw new IllegalArgumentException("Page size must not exceed " + MAX_PAGE_SIZE + ".");
        }
        if (sortField != null) {
            sortField = sortField.trim();
            if (sortField.isBlank()) {
                sortField = null;
            }
        }
        if (sortField == null) {
            sortDirection = null;
        } else if (sortDirection == null) {
            sortDirection = SortDirection.ASC;
        }
    }

    public static PageRequest of(int page, int size) {
        return new PageRequest(page, size, null, null);
    }

    public static PageRequest sorted(int page, int size, String sortField, SortDirection sortDirection) {
        return new PageRequest(page, size, sortField, sortDirection);
    }

    public boolean sorted() {
        return sortField != null;
    }

    public Optional<String> optionalSortField() {
        return Optional.ofNullable(sortField);
    }

    public Optional<SortDirection> optionalSortDirection() {
        return Optional.ofNullable(sortDirection);
    }
}
