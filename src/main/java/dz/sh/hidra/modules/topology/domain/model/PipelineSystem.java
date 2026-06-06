/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PipelineSystem
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.model
 *
 * @Description : Pipeline system aggregate representing a hydrocarbon transportation system.
 *
 */
package dz.sh.hidra.modules.topology.domain.model;

import java.time.Instant;
import java.util.Objects;

import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.kernel.domain.model.AggregateRoot;
import dz.sh.hidra.modules.topology.domain.value.OperationalOwnerReference;
import dz.sh.hidra.modules.topology.domain.value.PipelineSystemId;
import dz.sh.hidra.modules.topology.domain.value.ProductType;
import dz.sh.hidra.modules.topology.domain.value.ProductTypeReference;
import dz.sh.hidra.modules.topology.domain.value.TopologyCode;
import dz.sh.hidra.modules.topology.domain.value.TopologyName;
import dz.sh.hidra.modules.topology.domain.value.TopologyStatus;

/**
 * Represents a hydrocarbon transportation system grouping physical pipelines.
 *
 * <p>Business role:
 * A pipeline system groups pipelines by transportation system, product stream, or operational
 * network. It is physical topology master data and not an organization unit.
 *
 * <p>Architecture role:
 * This is a pure topology domain aggregate. Product classification is now carried as a catalog
 * reference so multilingual labels and configurable taxonomies can be resolved outside the asset.
 *
 * <p>Validation:
 * Identifier, code, name, product type reference, status, creation instant, and update instant are
 * mandatory.
 */
public final class PipelineSystem implements AggregateRoot<PipelineSystemId> {

    private final PipelineSystemId id;
    private final TopologyCode code;
    private final TopologyName name;
    private final String description;
    private final ProductTypeReference productType;
    private final TopologyStatus status;
    private final OperationalOwnerReference operationalOwnerReference;
    private final Instant createdAt;
    private final Instant updatedAt;

    private PipelineSystem(
            PipelineSystemId id,
            TopologyCode code,
            TopologyName name,
            String description,
            ProductTypeReference productType,
            TopologyStatus status,
            OperationalOwnerReference operationalOwnerReference,
            Instant createdAt,
            Instant updatedAt) {

        this.id = Objects.requireNonNull(id, "Pipeline system id must not be null.");
        this.code = Objects.requireNonNull(code, "Pipeline system code must not be null.");
        this.name = Objects.requireNonNull(name, "Pipeline system name must not be null.");
        this.description = normalizeOptionalText(description, 500, "Pipeline system description");
        this.productType = Objects.requireNonNull(productType, "Pipeline system product type reference must not be null.");
        this.status = Objects.requireNonNull(status, "Pipeline system status must not be null.");
        this.operationalOwnerReference = operationalOwnerReference;
        this.createdAt = requireInstant(createdAt, "Pipeline system createdAt");
        this.updatedAt = requireInstant(updatedAt, "Pipeline system updatedAt");

        ensureUpdatedAtIsValid(this.createdAt, this.updatedAt, "PipelineSystem");
    }

    public static PipelineSystem create(
            TopologyCode code,
            TopologyName name,
            String description,
            ProductTypeReference productType,
            OperationalOwnerReference operationalOwnerReference) {

        Instant now = Instant.now();
        return new PipelineSystem(
                PipelineSystemId.newId(),
                code,
                name,
                description,
                productType,
                TopologyStatus.PLANNED,
                operationalOwnerReference,
                now,
                now);
    }

    /**
     * Transitional legacy factory retained until REST/persistence callers are migrated.
     */
    @Deprecated(forRemoval = true)
    public static PipelineSystem create(
            TopologyCode code,
            TopologyName name,
            String description,
            ProductType productType,
            OperationalOwnerReference operationalOwnerReference) {

        return create(code, name, description, ProductTypeReference.from(productType), operationalOwnerReference);
    }

    public static PipelineSystem restore(
            PipelineSystemId id,
            TopologyCode code,
            TopologyName name,
            String description,
            ProductTypeReference productType,
            TopologyStatus status,
            OperationalOwnerReference operationalOwnerReference,
            Instant createdAt,
            Instant updatedAt) {

        return new PipelineSystem(
                id,
                code,
                name,
                description,
                productType,
                status,
                operationalOwnerReference,
                createdAt,
                updatedAt);
    }

    /**
     * Transitional legacy restore retained until persistence mapping is migrated to catalog FKs.
     */
    @Deprecated(forRemoval = true)
    public static PipelineSystem restore(
            PipelineSystemId id,
            TopologyCode code,
            TopologyName name,
            String description,
            ProductType productType,
            TopologyStatus status,
            OperationalOwnerReference operationalOwnerReference,
            Instant createdAt,
            Instant updatedAt) {

        return restore(id, code, name, description, ProductTypeReference.from(productType), status, operationalOwnerReference, createdAt, updatedAt);
    }

    @Override
    public PipelineSystemId id() {
        return id;
    }

    public TopologyCode code() {
        return code;
    }

    public TopologyName name() {
        return name;
    }

    public String description() {
        return description;
    }

    public ProductTypeReference productType() {
        return productType;
    }

    public TopologyStatus status() {
        return status;
    }

    public OperationalOwnerReference operationalOwnerReference() {
        return operationalOwnerReference;
    }

    public Instant createdAt() {
        return createdAt;
    }

    public Instant updatedAt() {
        return updatedAt;
    }

    public PipelineSystem activate() {
        return withStatus(TopologyStatus.ACTIVE);
    }

    public PipelineSystem deactivate() {
        return withStatus(TopologyStatus.INACTIVE);
    }

    public PipelineSystem markUnderMaintenance() {
        return withStatus(TopologyStatus.UNDER_MAINTENANCE);
    }

    public PipelineSystem retire() {
        return withStatus(TopologyStatus.RETIRED);
    }

    public PipelineSystem decommission() {
        return withStatus(TopologyStatus.DECOMMISSIONED);
    }

    private PipelineSystem withStatus(TopologyStatus newStatus) {
        return new PipelineSystem(
                id,
                code,
                name,
                description,
                productType,
                Objects.requireNonNull(newStatus, "Pipeline system status must not be null."),
                operationalOwnerReference,
                createdAt,
                Instant.now());
    }

    private static String normalizeOptionalText(String value, int maxLength, String fieldName) {
        if (value == null || value.isBlank()) {
            return null;
        }

        String normalized = value.trim();

        if (normalized.length() > maxLength) {
            throw new BusinessRuleViolationException(fieldName + " length must not exceed " + maxLength + " characters.");
        }

        return normalized;
    }

    private static Instant requireInstant(Instant value, String fieldName) {
        return Objects.requireNonNull(value, fieldName + " must not be null.");
    }

    private static void ensureUpdatedAtIsValid(Instant createdAt, Instant updatedAt, String modelName) {
        if (updatedAt.isBefore(createdAt)) {
            throw new BusinessRuleViolationException(modelName + " updatedAt must not be before createdAt.");
        }
    }
}
