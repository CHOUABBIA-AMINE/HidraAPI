/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PublicationResult
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.messaging
 *
 * @Description : Represents the result of technical event publication.
 *
 */
package dz.sh.hidra.platform.messaging;

/**
 * Technical publication result.
 *
 * @param successful whether publication succeeded
 * @param errorMessage optional technical error message
 */
public record PublicationResult(
        boolean successful,
        String errorMessage
) {

    public PublicationResult {
        errorMessage = normalize(errorMessage);
    }

    public static PublicationResult success() {
        return new PublicationResult(true, null);
    }

    public static PublicationResult failed(String errorMessage) {
        return new PublicationResult(false, errorMessage);
    }

    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }
}
