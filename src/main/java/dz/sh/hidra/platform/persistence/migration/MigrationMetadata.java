/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MigrationMetadata
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.persistence.migration
 *
 * @Description : Documents platform migration naming and location conventions.
 *
 */
package dz.sh.hidra.platform.persistence.migration;

public final class MigrationMetadata {

    public static final String LOCATION = "classpath:db/migration";
    public static final String VERSION_PREFIX = "V";
    public static final String DESCRIPTION_SEPARATOR = "__";
    public static final String SQL_SUFFIX = ".sql";

    private MigrationMetadata() {
    }

    public static String platformMigrationName(int version, String description) {
        if (version < 1) {
            throw new IllegalArgumentException("Migration version must be positive.");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Migration description must not be blank.");
        }
        return VERSION_PREFIX + version + DESCRIPTION_SEPARATOR + normalizeDescription(description) + SQL_SUFFIX;
    }

    private static String normalizeDescription(String description) {
        return description.trim().toLowerCase().replaceAll("[^a-z0-9]+", "_").replaceAll("(^_+|_+$)", "");
    }
}
