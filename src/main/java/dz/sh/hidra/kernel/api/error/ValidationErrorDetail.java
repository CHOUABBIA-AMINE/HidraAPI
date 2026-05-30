/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ValidationErrorDetail
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Kernel
 * @Module      : kernel
 * @Package     : dz.sh.hidra.kernel.api.error
 *
 * @Description : Immutable detail describing one validation error.
 *
 */
package dz.sh.hidra.kernel.api.error;

public record ValidationErrorDetail(String field, String message, String rejectedValue) {

    public ValidationErrorDetail {
        field = normalize(field);
        message = normalize(message);
        rejectedValue = normalize(rejectedValue);
    }

    private static String normalize(String value) {
        if (value == null) {
            return null;
        }
        String normalized = value.trim();
        return normalized.isBlank() ? null : normalized;
    }
}
