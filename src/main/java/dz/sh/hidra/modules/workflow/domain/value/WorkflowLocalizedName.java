/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowLocalizedName
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain.value
 *
 * @Description : Multilingual workflow name value object.
 *
 */
package dz.sh.hidra.modules.workflow.domain.value;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

/**
 * Multilingual name for user-facing workflow definitions, steps, tasks, and catalog entries.
 *
 * <p>Business rule:
 * French is mandatory in the first implementation because it is the default business language.
 * Arabic and English are optional but must be supported by the contract.
 *
 * @param nameAr Arabic label, optional
 * @param nameFr French label, mandatory
 * @param nameEn English label, optional
 */
public record WorkflowLocalizedName(String nameAr, String nameFr, String nameEn) implements ValueObject {

    public WorkflowLocalizedName {
        nameAr = normalizeOptional(nameAr);
        nameFr = normalizeRequired(nameFr, "nameFr");
        nameEn = normalizeOptional(nameEn);
    }

    public static WorkflowLocalizedName of(String nameAr, String nameFr, String nameEn) {
        return new WorkflowLocalizedName(nameAr, nameFr, nameEn);
    }

    public WorkflowName frenchName() {
        return WorkflowName.of(nameFr);
    }

    private static String normalizeRequired(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new InvalidValueObjectException("WorkflowLocalizedName " + fieldName + " must not be null or blank.");
        }

        String normalized = value.trim();

        if (normalized.length() > 160) {
            throw new InvalidValueObjectException("WorkflowLocalizedName " + fieldName + " length must not exceed 160 characters.");
        }

        return normalized;
    }

    private static String normalizeOptional(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        String normalized = value.trim();

        if (normalized.length() > 160) {
            throw new InvalidValueObjectException("WorkflowLocalizedName optional value length must not exceed 160 characters.");
        }

        return normalized;
    }
}
