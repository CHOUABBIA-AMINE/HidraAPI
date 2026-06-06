/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : Equipment
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.model
 *
 * @Description : Equipment entity representing an optional topology equipment reference.
 *
 */
package dz.sh.hidra.modules.topology.domain.model;

import java.time.Instant;
import java.util.Objects;

import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.kernel.domain.model.Entity;
import dz.sh.hidra.modules.topology.domain.value.EquipmentId;
import dz.sh.hidra.modules.topology.domain.value.EquipmentType;
import dz.sh.hidra.modules.topology.domain.value.EquipmentTypeReference;
import dz.sh.hidra.modules.topology.domain.value.TopologyAssetType;
import dz.sh.hidra.modules.topology.domain.value.TopologyCode;
import dz.sh.hidra.modules.topology.domain.value.TopologyName;
import dz.sh.hidra.modules.topology.domain.value.TopologyStatus;

/**
 * Represents an optional physical equipment/component reference attached to a topology asset.
 *
 * <p>Business role:
 * Equipment captures topology-owned physical component references such as compressors, pumps,
 * meters, actuators, control panels, and instrumentation.
 *
 * <p>Architecture role:
 * Equipment classification is a catalog reference so multilingual labels and configurable equipment
 * taxonomies can be resolved outside this entity.
 *
 * <p>Validation:
 * Parent asset type and identifier are mandatory so equipment can attach to a topology asset.
 */
public final class Equipment implements Entity<EquipmentId> {

    private final EquipmentId id;
    private final TopologyCode code;
    private final TopologyName name;
    private final EquipmentTypeReference equipmentType;
    private final TopologyAssetType parentAssetType;
    private final String parentAssetId;
    private final TopologyStatus status;
    private final Instant createdAt;
    private final Instant updatedAt;

    private Equipment(
            EquipmentId id,
            TopologyCode code,
            TopologyName name,
            EquipmentTypeReference equipmentType,
            TopologyAssetType parentAssetType,
            String parentAssetId,
            TopologyStatus status,
            Instant createdAt,
            Instant updatedAt) {

        this.id = Objects.requireNonNull(id, "Equipment id must not be null.");
        this.code = Objects.requireNonNull(code, "Equipment code must not be null.");
        this.name = Objects.requireNonNull(name, "Equipment name must not be null.");
        this.equipmentType = Objects.requireNonNull(equipmentType, "Equipment type reference must not be null.");
        this.parentAssetType = Objects.requireNonNull(parentAssetType, "Equipment parent asset type must not be null.");
        this.parentAssetId = requireText(parentAssetId, "Equipment parent asset id");
        this.status = Objects.requireNonNull(status, "Equipment status must not be null.");
        this.createdAt = requireInstant(createdAt, "Equipment createdAt");
        this.updatedAt = requireInstant(updatedAt, "Equipment updatedAt");
        ensureUpdatedAtIsValid(this.createdAt, this.updatedAt, "Equipment");
    }

    public static Equipment create(
            TopologyCode code,
            TopologyName name,
            EquipmentTypeReference equipmentType,
            TopologyAssetType parentAssetType,
            String parentAssetId) {

        Instant now = Instant.now();
        return new Equipment(EquipmentId.newId(), code, name, equipmentType, parentAssetType, parentAssetId, TopologyStatus.PLANNED, now, now);
    }

    @Deprecated(forRemoval = true)
    public static Equipment create(
            TopologyCode code,
            TopologyName name,
            EquipmentType equipmentType,
            TopologyAssetType parentAssetType,
            String parentAssetId) {

        return create(code, name, EquipmentTypeReference.from(equipmentType), parentAssetType, parentAssetId);
    }

    public static Equipment restore(
            EquipmentId id,
            TopologyCode code,
            TopologyName name,
            EquipmentTypeReference equipmentType,
            TopologyAssetType parentAssetType,
            String parentAssetId,
            TopologyStatus status,
            Instant createdAt,
            Instant updatedAt) {

        return new Equipment(id, code, name, equipmentType, parentAssetType, parentAssetId, status, createdAt, updatedAt);
    }

    @Deprecated(forRemoval = true)
    public static Equipment restore(
            EquipmentId id,
            TopologyCode code,
            TopologyName name,
            EquipmentType equipmentType,
            TopologyAssetType parentAssetType,
            String parentAssetId,
            TopologyStatus status,
            Instant createdAt,
            Instant updatedAt) {

        return restore(id, code, name, EquipmentTypeReference.from(equipmentType), parentAssetType, parentAssetId, status, createdAt, updatedAt);
    }

    @Override
    public EquipmentId id() { return id; }
    public TopologyCode code() { return code; }
    public TopologyName name() { return name; }
    public EquipmentTypeReference equipmentType() { return equipmentType; }
    public TopologyAssetType parentAssetType() { return parentAssetType; }
    public String parentAssetId() { return parentAssetId; }
    public TopologyStatus status() { return status; }
    public Instant createdAt() { return createdAt; }
    public Instant updatedAt() { return updatedAt; }

    public Equipment activate() { return withStatus(TopologyStatus.ACTIVE); }
    public Equipment deactivate() { return withStatus(TopologyStatus.INACTIVE); }
    public Equipment markUnderMaintenance() { return withStatus(TopologyStatus.UNDER_MAINTENANCE); }
    public Equipment retire() { return withStatus(TopologyStatus.RETIRED); }
    public Equipment decommission() { return withStatus(TopologyStatus.DECOMMISSIONED); }

    private Equipment withStatus(TopologyStatus newStatus) {
        return new Equipment(id, code, name, equipmentType, parentAssetType, parentAssetId, Objects.requireNonNull(newStatus, "Equipment status must not be null."), createdAt, Instant.now());
    }

    private static String requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) { throw new BusinessRuleViolationException(fieldName + " must not be null or blank."); }
        return value.trim();
    }

    private static Instant requireInstant(Instant value, String fieldName) { return Objects.requireNonNull(value, fieldName + " must not be null."); }

    private static void ensureUpdatedAtIsValid(Instant createdAt, Instant updatedAt, String modelName) {
        if (updatedAt.isBefore(createdAt)) { throw new BusinessRuleViolationException(modelName + " updatedAt must not be before createdAt."); }
    }
}
