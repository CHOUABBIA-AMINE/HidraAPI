/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowCode
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain.value
 *
 * @Description : Validated workflow business code value object.
 *
 */
package dz.sh.hidra.modules.workflow.domain.value;

import java.util.Locale;
import java.util.regex.Pattern;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

/**
 * Stable business code for workflow definitions, steps, transitions, catalog entries, targets, and reasons.
 *
 * <p>Validation:
 * Codes are trimmed, upper-cased, must be 2 to 120 characters long, may contain uppercase letters,
 * digits, underscores, hyphens, colons, slashes, or dots, and must start with a letter or digit.
 *
 * @param value validated workflow business code
 */
public record WorkflowCode(String value) implements ValueObject {

    private static final Pattern ALLOWED_PATTERN = Pattern.compile("^[A-Z0-9][A-Z0-9_.:/-]*$");

    public WorkflowCode {
        value = normalize(value);
    }

    public static WorkflowCode of(String value) {
        return new WorkflowCode(value);
    }

    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            throw new InvalidValueObjectException("WorkflowCode must not be null or blank.");
        }

        String normalized = value.trim().toUpperCase(Locale.ROOT);

        if (normalized.length() < 2 || normalized.length() > 120) {
            throw new InvalidValueObjectException("WorkflowCode length must be between 2 and 120 characters.");
        }

        if (!ALLOWED_PATTERN.matcher(normalized).matches()) {
            throw new InvalidValueObjectException("WorkflowCode contains unsupported characters.");
        }

        return normalized;
    }
}
