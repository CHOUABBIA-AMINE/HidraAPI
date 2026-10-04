/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportDefinition
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.domain.model
 *
 * @Description : Reusable formal report type with governed lifecycle and owner-module identity.
 *
 */
package dz.sh.hidra.modules.reporting.domain.model;

import dz.sh.hidra.modules.reporting.domain.exception.InvalidReportingValueException;
import dz.sh.hidra.modules.reporting.domain.value.ReportDefinitionStatus;
import java.time.Instant;
import java.util.Set;

/**
 * Reusable formal report type.
 */
public record ReportDefinition(
        String id,
        String code,
        String nameAr,
        String nameFr,
        String nameEn,
        String reportCategoryId,
        String ownerModule,
        String description,
        ReportDefinitionStatus status,
        String currentTemplateVersionId,
        boolean requiresApproval,
        boolean restricted,
        Instant createdAt,
        Instant updatedAt
) {

    private static final Set<String> KNOWN_HIDRA_MODULES = Set.of(
            "alarm",
            "analytics",
            "assets",
            "audit",
            "configuration",
            "custody",
            "documents",
            "hse",
            "identity",
            "incident",
            "integration",
            "integrity",
            "leakdetection",
            "monitoring",
            "notification",
            "organization",
            "party",
            "planning",
            "reporting",
            "risk",
            "simulation",
            "telemetry",
            "topology",
            "workflow"
    );

    public ReportDefinition {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidReportingValueException("ReportDefinition id must not be blank.");
        }
        // HRA-051 required: code
        if (code == null || code.isBlank()) {
            throw new InvalidReportingValueException("ReportDefinition code must not be blank.");
        }
        if (nameFr == null || nameFr.isBlank()) {
            throw new InvalidReportingValueException("ReportDefinition French name must not be blank.");
        }
        // HRA-051 required: reportCategoryId
        if (reportCategoryId == null || reportCategoryId.isBlank()) {
            throw new InvalidReportingValueException("ReportDefinition report category id must not be blank.");
        }
        if (ownerModule == null || ownerModule.isBlank()) {
            throw new InvalidReportingValueException("ReportDefinition owner module must not be blank.");
        }
        String normalizedOwnerModule = ownerModule.trim();
        if (!KNOWN_HIDRA_MODULES.contains(normalizedOwnerModule)) {
            throw new InvalidReportingValueException(
                    "ReportDefinition owner module must be a known Hidra business module."
            );
        }
        if (status == null) {
            throw new InvalidReportingValueException("ReportDefinition status must not be null.");
        }
        if (createdAt == null || updatedAt == null) {
            throw new InvalidReportingValueException(
                    "ReportDefinition createdAt and updatedAt must not be null."
            );
        }

        id = normalize(id);
        code = normalize(code);
        nameAr = normalize(nameAr);
        nameFr = normalize(nameFr);
        nameEn = normalize(nameEn);
        reportCategoryId = normalize(reportCategoryId);
        ownerModule = normalizedOwnerModule;
        description = normalize(description);
        currentTemplateVersionId = normalize(currentTemplateVersionId);
    }

    /**
     * Compatibility constructor for the pre-HMR-013 creation call shape.
     *
     * <p>The former service passed {@code true} to create a definition directly as active.
     * HMR-013 deliberately maps that legacy creation signal to DRAFT so creation no longer
     * skips the documented DRAFT -> ACTIVE -> RETIRED lifecycle. A false legacy value maps
     * to RETIRED for backward-safe reconstruction only; persistence uses the canonical
     * status constructor.</p>
     */
    public ReportDefinition(
            String id,
            String code,
            String nameAr,
            String nameFr,
            String nameEn,
            String reportCategoryId,
            String ownerModule,
            String description,
            boolean legacyActiveCreationFlag,
            String currentTemplateVersionId,
            boolean requiresApproval,
            boolean restricted,
            Instant createdAt,
            Instant updatedAt
    ) {
        this(
                id,
                code,
                nameAr,
                nameFr,
                nameEn,
                reportCategoryId,
                ownerModule,
                description,
                legacyActiveCreationFlag ? ReportDefinitionStatus.DRAFT : ReportDefinitionStatus.RETIRED,
                currentTemplateVersionId,
                requiresApproval,
                restricted,
                createdAt,
                updatedAt
        );
    }

    public boolean active() {
        return status == ReportDefinitionStatus.ACTIVE;
    }

    public boolean usableForNewRequests() {
        return status.usableForNewRequests();
    }

    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }
}
