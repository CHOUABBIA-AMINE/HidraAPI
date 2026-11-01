/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InstalledCharacteristicLinkTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-01
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.model
 *
 * @Description : Synthetic pure-domain verification of immutable manufacturer source references and candidate selection invariants.
 *
 */
package dz.sh.hidra.modules.topology.domain.model;

import dz.sh.hidra.modules.topology.domain.exception.InvalidTopologyValueException;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InstalledCharacteristicLinkTest {
    private static final Instant BASE = Instant.parse("2026-01-01T00:00:00Z");
    private static final String DIGEST_A = "a".repeat(64);
    private static final String DIGEST_B = "b".repeat(64);

    private static InstalledCharacteristicLink link(String id, String company, String unit,
            InstalledCharacteristicLink.Kind kind, int start, Integer end) {
        return new InstalledCharacteristicLink(id, 1, company, unit, "source-one", "source-r1",
                DIGEST_A, kind, BASE.plusSeconds(start),
                end == null ? null : BASE.plusSeconds(end), BASE,
                "synthetic:manufacturer-data", null);
    }

    @Test void acceptsFourKindsWithoutInferringPhysicalQualification() {
        for (var kind : InstalledCharacteristicLink.Kind.values()) {
            var candidate = link("link-" + kind, "company", "unit", kind, 0, null);
            assertEquals(kind, candidate.kind());
            assertNull(candidate.installationId());
            assertEquals("source-r1", candidate.sourceRevisionId());
        }
        assertEquals(4, InstalledCharacteristicLink.Kind.values().length);
    }

    @Test void preservesExactSourceAndDigestInImmutableRecordValue() {
        var original = link("link-a", "company", "unit-a", InstalledCharacteristicLink.Kind.COMPRESSOR_MAP, 0, null);
        var copy = new InstalledCharacteristicLink(original.linkId(), original.linkRevision(),
                original.companyPartyId(), original.installedUnitId(), original.sourceId(),
                original.sourceRevisionId(), original.sourceSha256(), original.kind(),
                original.effectiveFrom(), original.effectiveUntil(), original.recordedAt(),
                original.evidenceReference(), original.installationId());
        assertEquals(original, copy);
        assertEquals(original.hashCode(), copy.hashCode());
        var revised = new InstalledCharacteristicLink("link-b", 2, "company", "unit-a",
                "source-one", "source-r2", DIGEST_B, original.kind(), BASE, null, BASE,
                "synthetic:new-document", null);
        assertNotEquals(original, revised);
        assertEquals(DIGEST_A, original.sourceSha256());
        assertEquals("source-r1", original.sourceRevisionId());
        assertEquals(DIGEST_B, revised.sourceSha256());
    }

    @Test void rejectsMalformedSourceDigestsAndMissingKind() {
        var original = link("link", "company", "unit", InstalledCharacteristicLink.Kind.COMPRESSOR_MAP, 0, null);
        for (String digest : Arrays.asList("A".repeat(64), "a".repeat(63), "g".repeat(64), " a".repeat(64))) {
            assertThrows(InvalidTopologyValueException.class, () -> new InstalledCharacteristicLink(
                    original.linkId(), 1, "company", "unit", "source-one", "source-r1",
                    digest, original.kind(), BASE, null, BASE, "synthetic", null));
        }
        assertThrows(InvalidTopologyValueException.class, () -> new InstalledCharacteristicLink(
                "link", 1, "company", "unit", "source-one", "source-r1",
                null, original.kind(), BASE, null, BASE, "synthetic", null));
        assertThrows(InvalidTopologyValueException.class, () -> new InstalledCharacteristicLink(
                "link", 1, "company", "unit", "source-one", "source-r1",
                DIGEST_A, null, BASE, null, BASE, "synthetic", null));
    }

    @Test void rejectsMalformedIdentityRevisionAndSourceReferences() {
        assertThrows(InvalidTopologyValueException.class, () -> new InstalledCharacteristicLink(
                "link", 0, "company", "unit", "source-one", "source-r1", DIGEST_A,
                InstalledCharacteristicLink.Kind.VALVE_MAP, BASE, null, BASE, "synthetic", null));
        for (String invalid : Arrays.asList("", " ", " source", "source ", "bad\nsource", "x".repeat(81))) {
            assertThrows(InvalidTopologyValueException.class, () -> new InstalledCharacteristicLink(
                    "link", 1, "company", "unit", invalid, "source-r1", DIGEST_A,
                    InstalledCharacteristicLink.Kind.VALVE_MAP, BASE, null, BASE, "synthetic", null));
        }
        assertThrows(InvalidTopologyValueException.class, () -> new InstalledCharacteristicLink(
                null, 1, "company", "unit", "source-one", "source-r1", DIGEST_A,
                InstalledCharacteristicLink.Kind.VALVE_MAP, BASE, null, BASE, "synthetic", null));
        assertThrows(InvalidTopologyValueException.class, () -> new InstalledCharacteristicLink(
                "link", 1, "company", "unit", "source-one", " ", DIGEST_A,
                InstalledCharacteristicLink.Kind.VALVE_MAP, BASE, null, BASE, "synthetic", null));
        assertThrows(InvalidTopologyValueException.class, () -> new InstalledCharacteristicLink(
                "link", 1, "company", "unit", "source-one", "source-r1", DIGEST_A,
                InstalledCharacteristicLink.Kind.VALVE_MAP, BASE, null, BASE, " ", null));
    }

    @Test void requiresExplicitMicrosecondTimesAndNonemptyHalfOpenPeriod() {
        for (var from : Arrays.asList(BASE.minusNanos(1), BASE.plusNanos(1))) {
            assertThrows(InvalidTopologyValueException.class, () -> new InstalledCharacteristicLink(
                    "link", 1, "company", "unit", "source", "r1", DIGEST_A,
                    InstalledCharacteristicLink.Kind.COMPRESSOR_MAP, from, null, BASE, "synthetic", null));
        }
        for (var until : Arrays.asList(BASE, BASE.minusSeconds(1), BASE.plusNanos(1))) {
            assertThrows(InvalidTopologyValueException.class, () -> new InstalledCharacteristicLink(
                    "link", 1, "company", "unit", "source", "r1", DIGEST_A,
                    InstalledCharacteristicLink.Kind.COMPRESSOR_MAP, BASE, until, BASE, "synthetic", null));
        }
        for (var badRecorded : Arrays.asList(BASE.plusNanos(1), BASE.plusNanos(12))) {
            assertThrows(InvalidTopologyValueException.class, () -> new InstalledCharacteristicLink(
                    "link", 1, "company", "unit", "source", "r1", DIGEST_A,
                    InstalledCharacteristicLink.Kind.COMPRESSOR_MAP, BASE, null, badRecorded, "synthetic", null));
        }
        assertThrows(InvalidTopologyValueException.class, () -> new InstalledCharacteristicLink(
                "link", 1, "company", "unit", "source", "r1", DIGEST_A,
                InstalledCharacteristicLink.Kind.COMPRESSOR_MAP, null, null, BASE, "synthetic", null));
        assertThrows(InvalidTopologyValueException.class, () -> new InstalledCharacteristicLink(
                "link", 1, "company", "unit", "source", "r1", DIGEST_A,
                InstalledCharacteristicLink.Kind.COMPRESSOR_MAP, BASE, null, null, "synthetic", null));
    }

    @Test void rejectsConflictingSameUnitKindIntervalsAcrossLinkIds() {
        var first = link("link-one", "company", "unit", InstalledCharacteristicLink.Kind.COMPRESSOR_MAP, 0, 20);
        var overlapping = link("link-two", "company", "unit", InstalledCharacteristicLink.Kind.COMPRESSOR_MAP, 10, 30);
        assertThrows(InvalidTopologyValueException.class,
                () -> InstalledCharacteristicLink.requireNonOverlappingSelections(List.of(first, overlapping)));
        assertThrows(InvalidTopologyValueException.class,
                () -> InstalledCharacteristicLink.requireNonOverlappingSelections(List.of(first,
                        link("link-three", "company", "unit", InstalledCharacteristicLink.Kind.COMPRESSOR_MAP, 10, null))));
    }

    @Test void acceptsAdjacentPeriodsAndMultipleKindsForSameUnit() {
        var first = link("link-one", "company", "unit", InstalledCharacteristicLink.Kind.COMPRESSOR_MAP, 0, 20);
        var next = link("link-two", "company", "unit", InstalledCharacteristicLink.Kind.COMPRESSOR_MAP, 20, null);
        var otherKind = link("link-three", "company", "unit", InstalledCharacteristicLink.Kind.OPERATING_LIMITS, 0, null);
        InstalledCharacteristicLink.requireNonOverlappingSelections(List.of(first, next, otherKind));
        assertEquals(first.effectiveUntil(), next.effectiveFrom());
    }

    @Test void preservesPhysicalUnitAndCompanySeparation() {
        var first = link("a", "company-a", "physical-unit-a", InstalledCharacteristicLink.Kind.VALVE_MAP, 0, null);
        var otherUnit = link("b", "company-a", "physical-unit-b", InstalledCharacteristicLink.Kind.VALVE_MAP, 0, null);
        var otherCompany = link("c", "company-b", "physical-unit-a", InstalledCharacteristicLink.Kind.VALVE_MAP, 0, null);
        InstalledCharacteristicLink.requireNonOverlappingSelections(List.of(first, otherUnit, otherCompany));
        assertNotEquals(first.installedUnitId(), otherUnit.installedUnitId());
        assertNotEquals(first.companyPartyId(), otherCompany.companyPartyId());
    }

    @Test void rejectsDuplicateLinkRevisionAndNullSnapshots() {
        var row = link("same", "company", "unit", InstalledCharacteristicLink.Kind.PARAMETER_TABLE, 0, 1);
        assertThrows(InvalidTopologyValueException.class,
                () -> InstalledCharacteristicLink.requireNonOverlappingSelections(List.of(row, row)));
        assertThrows(InvalidTopologyValueException.class,
                () -> InstalledCharacteristicLink.requireNonOverlappingSelections(null));
        assertThrows(InvalidTopologyValueException.class,
                () -> InstalledCharacteristicLink.requireNonOverlappingSelections(Arrays.asList(row, null)));
    }

    @Test void installationReferenceIsOptionalAndDoesNotProvePlacement() {
        var noInstallation = link("link", "company", "unit", InstalledCharacteristicLink.Kind.PARAMETER_TABLE, 0, null);
        assertNull(noInstallation.installationId());
        var supplied = new InstalledCharacteristicLink("another", 1, "company", "unit",
                "source", "revision", DIGEST_A, InstalledCharacteristicLink.Kind.PARAMETER_TABLE,
                BASE, null, BASE, "synthetic:document", "unverified-installation-reference");
        assertEquals("unverified-installation-reference", supplied.installationId());
        assertThrows(InvalidTopologyValueException.class, () -> new InstalledCharacteristicLink(
                "another", 1, "company", "unit", "source", "revision", DIGEST_A,
                InstalledCharacteristicLink.Kind.PARAMETER_TABLE, BASE, null, BASE,
                "synthetic:document", " "));
    }
}
