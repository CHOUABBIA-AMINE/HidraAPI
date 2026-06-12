/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentsBoundaryPolicy
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.domain.policy
 *
 * @Description : Validates documents ownership boundaries.
 *
 */
package dz.sh.hidra.modules.documents.domain.policy;

import java.util.Locale;

/**
 * Validates documents ownership boundaries.
 */
public final class DocumentsBoundaryPolicy {

    private DocumentsBoundaryPolicy() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static boolean ownsBinaryContent() {
        return false;
    }

    public static boolean ownsAuditLedger() {
        return false;
    }

    public static boolean isForbiddenMetadataValue(String value) {
        if (value == null) {
            return false;
        }
        String normalized = value.toLowerCase(Locale.ROOT);
        return normalized.contains("password")
                || normalized.contains("secret")
                || normalized.contains("token")
                || normalized.contains("private_key")
                || normalized.contains("apikey")
                || normalized.contains("signedurl")
                || normalized.contains("signed_url");
    }
}
