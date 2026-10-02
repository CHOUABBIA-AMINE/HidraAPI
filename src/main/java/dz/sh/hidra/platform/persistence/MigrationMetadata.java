/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MigrationMetadata
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.persistence
 *
 * @Description : Defines technical migration naming conventions.
 *
 */
package dz.sh.hidra.platform.persistence;

/**
 * Technical migration naming conventions.
 */
public final class MigrationMetadata {

    public static final String LOCATION = "classpath:db/migration";
    public static final String VERSION_PREFIX = "V";
    public static final String DESCRIPTION_SEPARATOR = "__";
    public static final String SQL_SUFFIX = ".sql";

    private MigrationMetadata() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static String platformMigrationName(String version, String description) {
        String normalizedVersion = requireText(version, "Migration version must not be null or blank.");
        String normalizedDescription = requireText(description, "Migration description must not be null or blank.")
                .toLowerCase()
                .replaceAll("[^a-z0-9]+", "_")
                .replaceAll("^_+|_+$", "");

        return VERSION_PREFIX + normalizedVersion
                + DESCRIPTION_SEPARATOR
                + normalizedDescription
                + SQL_SUFFIX;
    }

    private static String requireText(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(message);
        }
        return value.trim();
    }
}
