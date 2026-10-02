/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportingLine
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.model
 *
 * @Description : Effective-dated reporting relation between typed Organization-owned subjects.
 *
 */
package dz.sh.hidra.modules.organization.domain.model;

import dz.sh.hidra.modules.organization.domain.exception.InvalidOrganizationValueException;
import dz.sh.hidra.modules.organization.domain.value.ReportingLineType;
import dz.sh.hidra.modules.organization.domain.value.ReportingSubjectReference;

import java.time.Instant;

/**
 * Represents an effective-dated reporting relation between Organization-owned subjects.
 *
 * <p>Business role: models administrative, operational, functional, or temporary
 * reporting between employees, positions, and organization units.</p>
 *
 * <p>Architecture role: source and target are canonical typed value references.
 * This aggregate does not import identity, topology, or other bounded-context models.</p>
 *
 * <p>Validation: the record requires nonblank identity plus non-null typed source and
 * target references. Referenced-object existence, lifecycle eligibility, reporting
 * cycles, and matrix-reporting cardinality are policy/application concerns.</p>
 *
 * <p>Usage: persistence maps the reference type to the existing string columns with
 * {@code EnumType.STRING}. Deprecated textual bridges exist only for migration
 * compatibility and are not canonical domain state.</p>
 *
 * @param id reporting-line identifier
 * @param reportingLineType reporting relationship category
 * @param source typed source reporting subject
 * @param target typed target reporting subject
 * @param validFrom inclusive effective start
 * @param validTo exclusive effective end, nullable for open-ended relation
 * @param active whether the relation is active
 * @param createdAt creation timestamp
 * @param updatedAt update timestamp
 */
public record ReportingLine(
        String id,
        ReportingLineType reportingLineType,
        ReportingSubjectReference source,
        ReportingSubjectReference target,
        Instant validFrom,
        Instant validTo,
        boolean active,
        Instant createdAt,
        Instant updatedAt
) {

    public ReportingLine {
        id = normalize(id);
        if (id == null) {
            throw new InvalidOrganizationValueException(
                    "Reporting line ID is required."
            );
        }
        if (reportingLineType == null) {
            throw new InvalidOrganizationValueException("Reporting line type is required.");
        }
        if (source == null) {
            throw new InvalidOrganizationValueException(
                    "Reporting line source is required."
            );
        }
        if (target == null) {
            throw new InvalidOrganizationValueException(
                    "Reporting line target is required."
            );
        }
        if (source.equals(target)) {
            throw new InvalidOrganizationValueException("Reporting line source and target must be different.");
        }
        if (validFrom == null) {
            throw new InvalidOrganizationValueException("Reporting line validFrom is required.");
        }
        if (validTo != null && !validTo.isAfter(validFrom)) {
            throw new InvalidOrganizationValueException("Reporting line validTo must be after validFrom.");
        }
    }

    /**
     * Transitional constructor for legacy callers that still provide textual type/id pairs.
     */
    @Deprecated(forRemoval = true)
    public ReportingLine(
            String id,
            ReportingLineType reportingLineType,
            String sourceType,
            String sourceId,
            String targetType,
            String targetId,
            Instant validFrom,
            Instant validTo,
            boolean active,
            Instant createdAt,
            Instant updatedAt
    ) {
        this(
                id,
                reportingLineType,
                ReportingSubjectReference.from(sourceType, sourceId),
                ReportingSubjectReference.from(targetType, targetId),
                validFrom,
                validTo,
                active,
                createdAt,
                updatedAt
        );
    }

    /**
     * Transitional textual source-type accessor.
     *
     * @return canonical source type name
     */
    @Deprecated(forRemoval = true)
    public String sourceType() {
        return source.type().name();
    }

    /**
     * Transitional source identifier accessor.
     *
     * @return canonical source target ID
     */
    @Deprecated(forRemoval = true)
    public String sourceId() {
        return source.targetId();
    }

    /**
     * Transitional textual target-type accessor.
     *
     * @return canonical target type name
     */
    @Deprecated(forRemoval = true)
    public String targetType() {
        return target.type().name();
    }

    /**
     * Transitional target identifier accessor.
     *
     * @return canonical target target ID
     */
    @Deprecated(forRemoval = true)
    public String targetId() {
        return target.targetId();
    }

    private static String normalize(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }
}
