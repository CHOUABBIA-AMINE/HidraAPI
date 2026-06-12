/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ValidationErrorDetail
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Kernel
 * @Module      : kernel
 * @Package     : dz.sh.hidra.kernel.validation
 *
 * @Description : Represents one validation error detail.
 *
 */
package dz.sh.hidra.kernel.validation;

import java.util.Objects;

/**
 * Describes one validation error.
 *
 * @param field optional field name or logical path
 * @param message human-readable validation message
 * @param rejectedValue sanitized rejected value when safe
 */
public record ValidationErrorDetail(
        String field,
        String message,
        String rejectedValue
) {

    public ValidationErrorDetail {
        field = normalize(field);
        message = Objects.requireNonNull(message, "Validation error message must not be null.").trim();
        if (message.isBlank()) {
            throw new IllegalArgumentException("Validation error message must not be blank.");
        }
        rejectedValue = normalize(rejectedValue);
    }

    private static String normalize(String text) {
        if (text == null || text.isBlank()) {
            return null;
        }
        return text.trim();
    }
}
