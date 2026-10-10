/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyGasFluidRevisionTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-10
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.domain.model
 *
 * @Description : Rejects incomplete gas declarations and preserves exact synthetic source values.
 *
 */
package dz.sh.hidra.modules.custody.domain.model;

import dz.sh.hidra.modules.custody.domain.exception.InvalidCustodyValueException;
import dz.sh.hidra.modules.custody.domain.model.CustodyGasFluidRevision.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CustodyGasFluidRevisionTest {
    public static final Instant AT = Instant.parse("2020-01-01T00:00:00.123456789Z");
    public static CustodyGasFluidRevision fixture(String revision) {
        var method = new Method("synthetic-mole-method", "m1", AT, AT, null, Origin.SYNTHETIC,
                "synthetic test applicability; no operational claim", InputRepresentation.GAS_MOLE_FRACTION,
                List.of("synthetic-A", "synthetic-B"), new BigDecimal("100000.00"), new BigDecimal("900000.00"),
                new BigDecimal("250.00"), new BigDecimal("350.00"), List.of(SupportedUse.STEADY_STATE, SupportedUse.TRANSIENT));
        return new CustodyGasFluidRevision("synthetic-gas", revision, AT, AT, null, Origin.SYNTHETIC,
                "synthetic test source", new ProductSnapshot("product", "PRODUCT", "SYNTHETIC_GAS", true, AT, AT),
                ProductKind.GAS, method, List.of(new Component("synthetic-A", new BigDecimal("0.8000")),
                new Component("synthetic-B", new BigDecimal("0.2000"))), new GovernanceBinding("def", 1, "type", "purpose"));
    }
    public static CustodyGasFluidRevision with(CustodyGasFluidRevision v, Method method, List<Component> composition, Origin origin) {
        return new CustodyGasFluidRevision(v.sourceId(), v.revisionId(), v.recordedAt(), v.effectiveFrom(), v.effectiveUntil(),
                origin, v.evidenceReference(), v.productSnapshot(), v.productKind(), method, composition, v.governanceBinding());
    }
    @Test void preservesSuppliedScaleNanosecondsOrderAndDefensiveLists() {
        var v = fixture("r1"); var mutable = new ArrayList<>(v.components()); var copy = with(v, v.method(), mutable, v.origin());
        mutable.clear(); assertEquals(v, copy); assertEquals(4, v.components().getFirst().moleFraction().scale());
        assertEquals(123456789, v.recordedAt().getNano()); assertThrows(UnsupportedOperationException.class, () -> copy.components().clear());
        assertTrue(v.effectiveAt(AT)); assertFalse(v.effectiveAt(AT.minusNanos(1)));
    }
    @Test void rejectsFractionErrorsDuplicatesUncoveredComponentsAndSyntheticPromotion() {
        var v = fixture("r1");
        for (var bad : List.of(List.of(new Component("synthetic-A", new BigDecimal("0.999999"))),
                List.of(new Component("synthetic-A", new BigDecimal("0.5")), new Component("synthetic-A", new BigDecimal("0.5"))),
                List.of(new Component("uncovered", BigDecimal.ONE))))
            assertThrows(InvalidCustodyValueException.class, () -> with(v, v.method(), bad, v.origin()));
        assertThrows(InvalidCustodyValueException.class, () -> with(v, v.method(), v.components(), Origin.DECLARED_PARAMETER));
        assertThrows(InvalidCustodyValueException.class, () -> new Component("A", BigDecimal.ZERO));
        assertThrows(InvalidCustodyValueException.class, () -> new Component("A", new BigDecimal("1.00001")));
        assertThrows(InvalidCustodyValueException.class, () -> new Component(" ", BigDecimal.ONE));
    }
    @Test void rejectsMissingRangesRepresentationAndUses() {
        var m = fixture("r1").method();
        assertThrows(InvalidCustodyValueException.class, () -> new Method(m.reference(), m.revisionId(), AT, AT, null, m.origin(),
                m.evidenceReference(), null, m.allowedComponentReferences(), m.minimumPressurePascalsAbsolute(),
                m.maximumPressurePascalsAbsolute(), m.minimumTemperatureKelvin(), m.maximumTemperatureKelvin(), m.supportedUses()));
        assertThrows(InvalidCustodyValueException.class, () -> new Method(m.reference(), m.revisionId(), AT, AT, null, m.origin(),
                m.evidenceReference(), m.inputRepresentation(), m.allowedComponentReferences(), BigDecimal.ZERO,
                m.maximumPressurePascalsAbsolute(), m.minimumTemperatureKelvin(), m.maximumTemperatureKelvin(), m.supportedUses()));
        assertThrows(InvalidCustodyValueException.class, () -> new Method(m.reference(), m.revisionId(), AT, AT, AT, m.origin(),
                m.evidenceReference(), m.inputRepresentation(), m.allowedComponentReferences(), m.minimumPressurePascalsAbsolute(),
                m.maximumPressurePascalsAbsolute(), m.minimumTemperatureKelvin(), m.maximumTemperatureKelvin(), List.of()));
    }
}
