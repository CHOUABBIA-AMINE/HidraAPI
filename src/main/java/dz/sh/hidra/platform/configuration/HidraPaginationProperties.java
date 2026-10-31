/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HidraPaginationProperties
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-31
 *
 * @Type        : Record
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.configuration
 *
 * @Description : Provides validated startup-bound pagination limits without overriding resource authority.
 *
 */
package dz.sh.hidra.platform.configuration;

import org.springframework.core.env.Environment;

import java.util.Objects;

/**
 * Immutable, server-owned technical pagination bounds. Each resource retains its
 * own stricter limits, query algorithm and authorization requirements.
 *
 * <p>These settings are resolved at application startup. Changing environment
 * properties requires restarting the application; there is no hot reload.</p>
 */
public record HidraPaginationProperties(int defaultSize, int maxSize, int maxCursorLength) {
    public static final String DEFAULT_SIZE_PROPERTY = "hidra.api.pagination.default-size";
    public static final String MAX_SIZE_PROPERTY = "hidra.api.pagination.max-size";
    public static final String MAX_CURSOR_LENGTH_PROPERTY = "hidra.api.pagination.max-cursor-length";

    // Finite configuration ceilings, not permissions or endpoint-specific limits.
    public static final int CONFIGURATION_PAGE_SIZE_CEILING = 10_000;
    public static final int CONFIGURATION_CURSOR_LENGTH_CEILING = 4_096;

    public HidraPaginationProperties {
        if (defaultSize < 1 || maxSize < 1
                || maxSize > CONFIGURATION_PAGE_SIZE_CEILING || defaultSize > maxSize) {
            throw new IllegalArgumentException("Invalid shared pagination default/maximum");
        }
        if (maxCursorLength < 1 || maxCursorLength > CONFIGURATION_CURSOR_LENGTH_CEILING) {
            throw new IllegalArgumentException("Invalid shared pagination cursor-length bound");
        }
    }

    /** Requires all three startup properties; malformed/missing values never fall back. */
    public static HidraPaginationProperties from(Environment environment) {
        Objects.requireNonNull(environment, "environment");
        return new HidraPaginationProperties(
                environment.getRequiredProperty(DEFAULT_SIZE_PROPERTY, Integer.class),
                environment.getRequiredProperty(MAX_SIZE_PROPERTY, Integer.class),
                environment.getRequiredProperty(MAX_CURSOR_LENGTH_PROPERTY, Integer.class));
    }

    /** Checks an explicit resource-owned cap as well as the configured platform cap. */
    public int resolveSize(Integer requestedSize, int resourceMaximum) {
        if (resourceMaximum < 1) {
            throw new IllegalArgumentException("Invalid resource pagination maximum");
        }
        int size = requestedSize == null ? defaultSize : requestedSize;
        if (size < 1 || size > Math.min(resourceMaximum, maxSize)) {
            throw new IllegalArgumentException("Pagination size exceeds effective resource limit");
        }
        return size;
    }

    /**
     * Validates a cursor's input bound only. This does not verify cursor
     * authenticity, authorization, owner scope, expiry, or history continuity.
     */
    public void validateCursor(String cursor, int resourceMaximumLength) {
        if (resourceMaximumLength < 1) {
            throw new IllegalArgumentException("Invalid resource cursor-length maximum");
        }
        if (cursor == null) {
            return;
        }
        if (cursor.isBlank() || cursor.length() > Math.min(resourceMaximumLength, maxCursorLength)
                || cursor.codePoints().anyMatch(Character::isISOControl)) {
            throw new IllegalArgumentException("Invalid pagination cursor");
        }
    }
}
