/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyValueObjectsTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Test
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.value
 *
 * @Description : Unit tests for topology domain value objects.
 *
 */
package dz.sh.hidra.modules.topology.domain.value;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;

/**
 * Unit tests for topology value objects.
 */
class TopologyValueObjectsTest {

    @Test
    void shouldNormalizeTopologyCodeAndName() {
        TopologyCode code = TopologyCode.of(" gz1.cs-east-01 ");
        TopologyName name = TopologyName.of("  Compression Station East 01  ");

        assertEquals("GZ1.CS-EAST-01", code.value());
        assertEquals("Compression Station East 01", name.value());
    }

    @Test
    void shouldRejectInvalidTopologyCodeCharacters() {
        assertThrows(InvalidValueObjectException.class, () -> TopologyCode.of("-BAD"));
        assertThrows(InvalidValueObjectException.class, () -> TopologyCode.of("BAD CODE"));
        assertThrows(InvalidValueObjectException.class, () -> TopologyCode.of("A"));
    }

    @Test
    void shouldValidateGeographicalCoordinates() {
        GeoCoordinate coordinate = GeoCoordinate.of(31.6167, 2.2167);

        assertEquals(31.6167, coordinate.latitude());
        assertEquals(2.2167, coordinate.longitude());

        assertThrows(InvalidValueObjectException.class, () -> GeoCoordinate.of(91.0, 2.0));
        assertThrows(InvalidValueObjectException.class, () -> GeoCoordinate.of(31.0, 181.0));
    }

    @Test
    void shouldValidatePositivePhysicalDimensions() {
        assertEquals(
                new BigDecimal("12.5").stripTrailingZeros(),
                LengthInKilometers.of(new BigDecimal("12.500")).value());
        assertEquals(
                new BigDecimal("42").stripTrailingZeros(),
                DiameterInInches.of(new BigDecimal("42.000")).value());

        assertThrows(InvalidValueObjectException.class, () -> LengthInKilometers.of(BigDecimal.ZERO));
        assertThrows(InvalidValueObjectException.class, () -> DiameterInInches.of(new BigDecimal("-1.0")));
    }

    @Test
    void shouldAllowZeroPipelineKilometerPointButRejectNegativeValue() {
        assertEquals(BigDecimal.ZERO, PipelineKilometerPoint.of(BigDecimal.ZERO).value());

        assertThrows(InvalidValueObjectException.class, () -> PipelineKilometerPoint.of(new BigDecimal("-0.001")));
    }

    @Test
    void shouldNormalizeNeutralOrganizationReferences() {
        OrganizationUnitReference organizationUnitReference = OrganizationUnitReference.of(
                " ORGANIZATION_UNIT ",
                " ou-east ",
                " TRC-OPS-EAST ",
                " Operational East Region ");
        OperationalOwnerReference ownerReference = OperationalOwnerReference.of(
                " ORGANIZATION_UNIT ",
                " ou-east ",
                " TRC-OPS-EAST ",
                " Operational East Region ");

        assertEquals("ORGANIZATION_UNIT", organizationUnitReference.referenceType());
        assertEquals("ou-east", organizationUnitReference.referenceId());
        assertEquals("TRC-OPS-EAST", ownerReference.ownerCode());
        assertEquals("Operational East Region", ownerReference.ownerName());
    }
}
