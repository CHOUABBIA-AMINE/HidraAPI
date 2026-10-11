/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationRegulatorCharacteristicRevision
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.domain.model
 *
 * @Description : Defines supplied synthetic regulator capacity metadata with exact fluid provenance.
 *
 */
package dz.sh.hidra.modules.simulation.domain.model;
import dz.sh.hidra.modules.simulation.domain.exception.InvalidSimulationValueException;
import dz.sh.hidra.modules.simulation.domain.model.SimulationEquipmentParameterRevision.Origin;
import java.math.BigDecimal;
import java.time.Instant;
/** Immutable supplied synthetic capacity parameters; no mathematical evaluation. */
public record SimulationRegulatorCharacteristicRevision(String id, String revisionId,
        Instant recordedAt, Instant effectiveFrom, Instant effectiveUntil, Origin origin,
        String evidenceReference, String fluidSourceId, String fluidRevisionId, String fluidSha256,
        BigDecimal referenceTemperatureKelvin, String capacityMethod,
        BigDecimal coefficientKilogramsPerSecondPerSquareRootPascal,
        BigDecimal minimumUpstreamPressurePascalsAbsolute, BigDecimal maximumUpstreamPressurePascalsAbsolute,
        BigDecimal minimumDownstreamPressurePascalsAbsolute, BigDecimal maximumDownstreamPressurePascalsAbsolute,
        BigDecimal minimumDifferentialPressurePascals, BigDecimal maximumDifferentialPressurePascals) {
    public static final String METHOD = "SYNTHETIC_REGULATOR_SQRT_DP_CAPACITY_V1";
    public SimulationRegulatorCharacteristicRevision {
        id=text(id); revisionId=text(revisionId); evidenceReference=text(evidenceReference);
        fluidSourceId=text(fluidSourceId); fluidRevisionId=text(fluidRevisionId);
        require(fluidSha256!=null && fluidSha256.matches("[0-9a-f]{64}"),"Exact fluid digest required.");
        require(recordedAt!=null && effectiveFrom!=null && (effectiveUntil==null || effectiveUntil.isAfter(effectiveFrom)),"Ordered source times required.");
        require(origin==Origin.SYNTHETIC && METHOD.equals(capacityMethod),"Only named synthetic regulator source supported.");
        positive(referenceTemperatureKelvin); positive(coefficientKilogramsPerSecondPerSquareRootPascal);
        range(minimumUpstreamPressurePascalsAbsolute,maximumUpstreamPressurePascalsAbsolute);
        range(minimumDownstreamPressurePascalsAbsolute,maximumDownstreamPressurePascalsAbsolute);
        range(minimumDifferentialPressurePascals,maximumDifferentialPressurePascals);
    }
    public boolean effectiveAt(Instant at) { return at!=null && !recordedAt.isAfter(at) && !effectiveFrom.isAfter(at) && (effectiveUntil==null || at.isBefore(effectiveUntil)); }
    private static String text(String s) { require(s!=null && !s.isBlank(),"Source identity/evidence required."); return s.trim(); }
    private static void positive(BigDecimal v) { require(v!=null && v.signum()>0,"Positive supplied parameter required."); }
    private static void range(BigDecimal lo,BigDecimal hi) { positive(lo); positive(hi); require(lo.compareTo(hi)<=0,"Ordered source domain required."); }
    private static void require(boolean b,String s) { if(!b) throw new InvalidSimulationValueException(s); }
}
