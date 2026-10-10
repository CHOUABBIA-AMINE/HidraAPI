/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyGasFluidRevision
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-10
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.domain.model
 *
 * @Description : Defines supplied immutable gas composition and governed method applicability without physical defaults.
 *
 */
package dz.sh.hidra.modules.custody.domain.model;

import dz.sh.hidra.modules.custody.domain.exception.InvalidCustodyValueException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public record CustodyGasFluidRevision(String sourceId, String revisionId, Instant recordedAt,
        Instant effectiveFrom, Instant effectiveUntil, Origin origin, String evidenceReference,
        ProductSnapshot productSnapshot, ProductKind productKind, Method method,
        List<Component> components, GovernanceBinding governanceBinding) {
    public enum Origin { DECLARED_PARAMETER, SYNTHETIC }
    public enum ProductKind { GAS }
    public enum InputRepresentation { GAS_MOLE_FRACTION }
    public enum SupportedUse { STEADY_STATE, TRANSIENT }
    public CustodyGasFluidRevision {
        sourceId = text(sourceId); revisionId = text(revisionId); evidenceReference = text(evidenceReference);
        interval(recordedAt, effectiveFrom, effectiveUntil);
        require(origin != null && productSnapshot != null && productKind == ProductKind.GAS
                && method != null && governanceBinding != null, "All source facts must be supplied.");
        require(productSnapshot.active(), "Product must be active at append.");
        components = copy(components); require(!components.isEmpty(), "Composition must be supplied.");
        var ids = new HashSet<String>(); var total = BigDecimal.ZERO;
        for (var c : components) {
            require(ids.add(c.componentReference()), "Duplicate composition component.");
            require(method.allowedComponentReferences().contains(c.componentReference()), "Method does not cover component.");
            total = total.add(c.moleFraction());
        }
        require(total.compareTo(BigDecimal.ONE) == 0, "Mole fractions must sum exactly to one.");
        require(method.origin() != Origin.SYNTHETIC || origin == Origin.SYNTHETIC, "Synthetic method cannot be promoted.");
        require(!method.recordedAt().isAfter(recordedAt), "Method must be recorded by source recording time.");
    }
    public boolean effectiveAt(Instant at) { return at != null && !at.isBefore(recordedAt)
            && !at.isBefore(effectiveFrom) && (effectiveUntil == null || at.isBefore(effectiveUntil)); }
    public record ProductSnapshot(String id, String catalogName, String code, boolean active,
            Instant createdAt, Instant updatedAt) {
        public ProductSnapshot {
            id = text(id); catalogName = text(catalogName); code = text(code);
            require(createdAt != null && updatedAt != null && !updatedAt.isBefore(createdAt), "Coherent product times required.");
        }
    }
    public record Method(String reference, String revisionId, Instant recordedAt, Instant effectiveFrom,
            Instant effectiveUntil, Origin origin, String evidenceReference, InputRepresentation inputRepresentation,
            List<String> allowedComponentReferences, BigDecimal minimumPressurePascalsAbsolute,
            BigDecimal maximumPressurePascalsAbsolute, BigDecimal minimumTemperatureKelvin,
            BigDecimal maximumTemperatureKelvin, List<SupportedUse> supportedUses) {
        public Method {
            reference = text(reference); revisionId = text(revisionId); evidenceReference = text(evidenceReference);
            interval(recordedAt, effectiveFrom, effectiveUntil);
            require(origin != null && inputRepresentation == InputRepresentation.GAS_MOLE_FRACTION, "Supported method representation required.");
            allowedComponentReferences = copy(allowedComponentReferences).stream().map(CustodyGasFluidRevision::text).toList();
            supportedUses = copy(supportedUses);
            require(!allowedComponentReferences.isEmpty() && new HashSet<>(allowedComponentReferences).size() == allowedComponentReferences.size(), "Unique method components required.");
            require(!supportedUses.isEmpty() && new HashSet<>(supportedUses).size() == supportedUses.size(), "Unique supported uses required.");
            range(minimumPressurePascalsAbsolute, maximumPressurePascalsAbsolute);
            range(minimumTemperatureKelvin, maximumTemperatureKelvin);
        }
        public boolean effectiveAt(Instant at) { return at != null && !at.isBefore(recordedAt)
                && !at.isBefore(effectiveFrom) && (effectiveUntil == null || at.isBefore(effectiveUntil)); }
    }
    public record Component(String componentReference, BigDecimal moleFraction) {
        public Component { componentReference = text(componentReference);
            require(moleFraction != null && moleFraction.signum() > 0 && moleFraction.compareTo(BigDecimal.ONE) <= 0, "Positive mole fraction <= one required."); }
    }
    public record GovernanceBinding(String definitionId, int definitionVersion, String targetTypeId, String purposeId) {
        public GovernanceBinding { definitionId = text(definitionId); targetTypeId = text(targetTypeId); purposeId = text(purposeId);
            require(definitionVersion > 0, "Positive definition version required."); }
    }
    private static void interval(Instant recorded, Instant from, Instant until) {
        require(recorded != null && from != null && (until == null || until.isAfter(from)), "Coherent explicit source validity required.");
    }
    private static void range(BigDecimal min, BigDecimal max) {
        require(min != null && max != null && min.signum() > 0 && min.compareTo(max) <= 0, "Positive ordered applicability range required.");
    }
    private static <T> List<T> copy(List<T> list) {
        require(list != null && list.stream().allMatch(Objects::nonNull), "No missing list children permitted.");
        return List.copyOf(list);
    }
    private static String text(String s) { require(s != null && !s.isBlank(), "Nonblank explicit source fact required."); return s.trim(); }
    private static void require(boolean condition, String message) { if (!condition) throw new InvalidCustodyValueException(message); }
}
