/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportingLineType
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.value
 *
 * @Description : Stable reference to the Organization-owned reporting-line type catalog.
 *
 */
package dz.sh.hidra.modules.organization.domain.value;

import dz.sh.hidra.modules.organization.domain.exception.InvalidOrganizationValueException;
import java.util.Set;

/**
 * Stable catalog reference for reporting-line business classification.
 *
 * <p>Business role: identifies the governed reporting relationship category while
 * keeping policy behavior separate from localized catalog labels.</p>
 *
 * <p>Architecture role: replaces the former fixed enum with an extensible Organization-owned
 * catalog reference. The named constants are compatibility references for the six
 * authoritative seed rows, not an exhaustive Java taxonomy.</p>
 *
 * <p>Validation: id and code are mandatory. Localized labels are optional until authoritative
 * Arabic/French/English master data is provisioned.</p>
 *
 * <p>Usage: policy code may branch only on stable codes such as {@code LINE}; it must not infer
 * behavior from translated names.</p>
 *
 * @param id stable catalog identifier
 * @param code stable catalog code
 * @param nameAr optional Arabic label
 * @param nameFr optional French label
 * @param nameEn optional English label
 */
public record ReportingLineType(
        String id,
        String code,
        String nameAr,
        String nameFr,
        String nameEn
) {

    private static final Set<String> EXPLICIT_MULTI_LINE_CODES = Set.of(
            "FUNCTIONAL",
            "ADMINISTRATIVE",
            "TECHNICAL",
            "DOTTED_LINE"
    );

    public static final ReportingLineType LINE = seeded("LINE");
    public static final ReportingLineType OPERATIONAL = seeded("OPERATIONAL");
    public static final ReportingLineType FUNCTIONAL = seeded("FUNCTIONAL");
    public static final ReportingLineType ADMINISTRATIVE = seeded("ADMINISTRATIVE");
    public static final ReportingLineType TECHNICAL = seeded("TECHNICAL");
    public static final ReportingLineType DOTTED_LINE = seeded("DOTTED_LINE");

    public ReportingLineType {
        if (id == null || id.isBlank()) {
            throw new InvalidOrganizationValueException(
                    "Reporting line type id must not be blank."
            );
        }
        if (code == null || code.isBlank()) {
            throw new InvalidOrganizationValueException(
                    "Reporting line type code must not be blank."
            );
        }

        id = id.trim();
        code = code.trim();
        nameAr = normalize(nameAr);
        nameFr = normalize(nameFr);
        nameEn = normalize(nameEn);
    }

    /**
     * Returns whether this type represents the primary/hierarchical reporting relation.
     */
    public boolean lineHierarchy() {
        return "LINE".equals(code);
    }

    /**
     * Returns whether the governing roadmap explicitly allows multiple active relations.
     *
     * <p>{@code false} does not imply a single-line rule for unlisted catalog codes. The only
     * single-active rule established by HMR-028 is EMPLOYEE-source {@code LINE}.</p>
     */
    public boolean explicitlyAllowsMultipleActiveLines() {
        return EXPLICIT_MULTI_LINE_CODES.contains(code);
    }

    private static ReportingLineType seeded(String code) {
        return new ReportingLineType(code, code, null, null, null);
    }

    private static String normalize(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }
}
