/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentId
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.domain.value
 *
 * @Description : Stable document identifier.
 *
 */
package dz.sh.hidra.modules.documents.domain.value;

import dz.sh.hidra.modules.documents.domain.exception.InvalidDocumentValueException;

import java.util.UUID;

/**
 * Stable document identifier.
 *
 * @param value identifier value
 */
public record DocumentId(String value) {

    public DocumentId {
        value = requireText(value, "Document ID must not be null or blank.");
    }

    public static DocumentId of(String value) {
        return new DocumentId(value);
    }

    public static DocumentId newId() {
        return new DocumentId(UUID.randomUUID().toString());
    }

    private static String requireText(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new InvalidDocumentValueException(message);
        }
        return value.trim();
    }
}
