/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : Pipeline
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-06
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.model
 *
 * @Description : Pipeline aggregate representing a physical pipeline.
 *
 */
package dz.sh.hidra.modules.topology.domain.model;

import java.time.Instant;
import java.util.Objects;

import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.kernel.domain.model.AggregateRoot;
import dz.sh.hidra.modules.topology.domain.value.DiameterInInches;
import dz.sh.hidra.modules.topology.domain.value.LengthInKilometers;
import dz.sh.hidra.modules.topology.domain.value.PipelineId;
import dz.sh.hidra.modules.topology.domain.value.PipelineSystemId;
import dz.sh.hidra.modules.topology.domain.value.ProductTypeReference;
import dz.sh.hidra.modules.topology.domain.value.TopologyCode;
import dz.sh.hidra.modules.topology.domain.value.TopologyMultilingualDescription;
import dz.sh.hidra.modules.topology.domain.value.TopologyMultilingualName;
import dz.sh.hidra.modules.topology.domain.value.TopologyName;
import dz.sh.hidra.modules.topology.domain.value.TopologyStatus;

public final class Pipeline implements AggregateRoot<PipelineId> {

    private final PipelineId id;
    private final PipelineSystemId pipelineSystemId;
    private final TopologyCode code;
    private final TopologyMultilingualName name;
    private final TopologyMultilingualDescription description;
    private final ProductTypeReference productType;
    private final DiameterInInches nominalDiameter;
    private final LengthInKilometers designLength;
    private final TopologyStatus status;
    private final Instant createdAt;
    private final Instant updatedAt;

    private Pipeline(
            PipelineId id,
            PipelineSystemId pipelineSystemId,
            TopologyCode code,
            TopologyMultilingualName name,
            TopologyMultilingualDescription description,
            ProductTypeReference productType,
            DiameterInInches nominalDiameter,
            LengthInKilometers designLength,
            TopologyStatus status,
            Instant createdAt,
            Instant updatedAt) {

        this.id = Objects.requireNonNull(id, "Pipeline id must not be null.");
        this.pipelineSystemId = Objects.requireNonNull(pipelineSystemId, "Pipeline system id must not be null.");
        this.code = Objects.requireNonNull(code, "Pipeline code must not be null.");
        this.name = Objects.requireNonNull(name, "Pipeline trilingual name must not be null.");
        this.description = description;
        this.productType = Objects.requireNonNull(productType, "Pipeline product type reference must not be null.");
        this.nominalDiameter = Objects.requireNonNull(nominalDiameter, "Pipeline nominal diameter must not be null.");
        this.designLength = Objects.requireNonNull(designLength, "Pipeline design length must not be null.");
        this.status = Objects.requireNonNull(status, "Pipeline status must not be null.");
        this.createdAt = requireInstant(createdAt, "Pipeline createdAt");
        this.updatedAt = requireInstant(updatedAt, "Pipeline updatedAt");

        ensureUpdatedAtIsValid(this.createdAt, this.updatedAt, "Pipeline");
    }

    public static Pipeline create(
            PipelineSystemId pipelineSystemId,
            TopologyCode code,
            TopologyMultilingualName name,
            TopologyMultilingualDescription description,
            ProductTypeReference productType,
            DiameterInInches nominalDiameter,
            LengthInKilometers designLength) {

        Instant now = Instant.now();
        return new Pipeline(PipelineId.newId(), pipelineSystemId, code, name, description, productType, nominalDiameter, designLength, TopologyStatus.PLANNED, now, now);
    }

    public static Pipeline create(
            PipelineSystemId pipelineSystemId,
            TopologyCode code,
            TopologyName name,
            String description,
            ProductTypeReference productType,
            DiameterInInches nominalDiameter,
            LengthInKilometers designLength) {

        return create(pipelineSystemId, code, multilingualName(name), multilingualDescription(description), productType, nominalDiameter, designLength);
    }

    public static Pipeline restore(
            PipelineId id,
            PipelineSystemId pipelineSystemId,
            TopologyCode code,
            TopologyMultilingualName name,
            TopologyMultilingualDescription description,
            ProductTypeReference productType,
            DiameterInInches nominalDiameter,
            LengthInKilometers designLength,
            TopologyStatus status,
            Instant createdAt,
            Instant updatedAt) {

        return new Pipeline(id, pipelineSystemId, code, name, description, productType, nominalDiameter, designLength, status, createdAt, updatedAt);
    }

    public static Pipeline restore(
            PipelineId id,
            PipelineSystemId pipelineSystemId,
            TopologyCode code,
            TopologyName name,
            String description,
            ProductTypeReference productType,
            DiameterInInches nominalDiameter,
            LengthInKilometers designLength,
            TopologyStatus status,
            Instant createdAt,
            Instant updatedAt) {

        return restore(id, pipelineSystemId, code, multilingualName(name), multilingualDescription(description), productType, nominalDiameter, designLength, status, createdAt, updatedAt);
    }

    @Override
    public PipelineId id() { return id; }
    public PipelineSystemId pipelineSystemId() { return pipelineSystemId; }
    public TopologyCode code() { return code; }
    public TopologyMultilingualName name() { return name; }
    public TopologyMultilingualDescription description() { return description; }
    public ProductTypeReference productType() { return productType; }
    public DiameterInInches nominalDiameter() { return nominalDiameter; }
    public LengthInKilometers designLength() { return designLength; }
    public TopologyStatus status() { return status; }
    public Instant createdAt() { return createdAt; }
    public Instant updatedAt() { return updatedAt; }

    public Pipeline activate() { return withStatus(TopologyStatus.ACTIVE); }
    public Pipeline deactivate() { return withStatus(TopologyStatus.INACTIVE); }
    public Pipeline markUnderMaintenance() { return withStatus(TopologyStatus.UNDER_MAINTENANCE); }
    public Pipeline retire() { return withStatus(TopologyStatus.RETIRED); }
    public Pipeline decommission() { return withStatus(TopologyStatus.DECOMMISSIONED); }

    private Pipeline withStatus(TopologyStatus newStatus) {
        return new Pipeline(id, pipelineSystemId, code, name, description, productType, nominalDiameter, designLength, Objects.requireNonNull(newStatus, "Pipeline status must not be null."), createdAt, Instant.now());
    }

    private static TopologyMultilingualName multilingualName(TopologyName name) {
        String value = Objects.requireNonNull(name, "Pipeline name must not be null.").value();
        return TopologyMultilingualName.of(value, value, value);
    }

    private static TopologyMultilingualDescription multilingualDescription(String description) {
        return TopologyMultilingualDescription.of(description, description, description);
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
