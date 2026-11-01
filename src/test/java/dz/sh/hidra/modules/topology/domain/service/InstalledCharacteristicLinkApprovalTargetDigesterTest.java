/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InstalledCharacteristicLinkApprovalTargetDigesterTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-01
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.service
 *
 * @Description : Checks version-tagged canonical SHA-256 link approval target vectors and field sensitivity.
 *
 */
package dz.sh.hidra.modules.topology.domain.service;

import dz.sh.hidra.modules.topology.domain.exception.InvalidTopologyValueException;
import dz.sh.hidra.modules.topology.domain.model.InstalledCharacteristicLink;
import java.time.Instant;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InstalledCharacteristicLinkApprovalTargetDigesterTest {
    private static final Instant FROM = Instant.parse("2026-11-01T12:34:56.123456Z");
    private static final Instant RECORDED = Instant.parse("2026-10-31T10:15:00Z");
    private static final String A = "a".repeat(64);

    private static InstalledCharacteristicLink candidate() {
        return new InstalledCharacteristicLink("link-1", 7, "party-1", "unit-1", "source-1",
                "source-r3", A, InstalledCharacteristicLink.Kind.COMPRESSOR_MAP, FROM,
                null, RECORDED, "synthetic:approval-source", null);
    }

    @Test void goldenVectorPinsNullSentinelFixedUtcMicrosAndVersionTag() {
        assertEquals("4894f10aae741238eb3903a94ac3af976b9bb290ca4eff4728e28491f81d6d2d",
                InstalledCharacteristicLinkApprovalTargetDigester.digest(candidate()));
        assertEquals("HIDRA_INSTALLED_CHARACTERISTIC_LINK_APPROVAL_TARGET_V1",
                InstalledCharacteristicLinkApprovalTargetDigester.VERSION);
    }

    @Test void goldenVectorPinsUtf8ByteLengthsAndPresentOptionalFields() {
        var c = candidate();
        var withOptional = new InstalledCharacteristicLink(c.linkId(), c.linkRevision(),
                c.companyPartyId(), c.installedUnitId(), c.sourceId(), c.sourceRevisionId(),
                c.sourceSha256(), c.kind(), c.effectiveFrom(),
                Instant.parse("2026-11-02T00:00:00Z"), c.recordedAt(), c.evidenceReference(),
                "installation-ä");
        assertEquals("9b214013fa4e7113dc6d223bae33133eaaf38bd03110959f5acceeeb64d2f3b0",
                InstalledCharacteristicLinkApprovalTargetDigester.digest(withOptional));
    }

    @Test void everyOneOfThirteenTargetFieldsChangesDigest() {
        var c = candidate();
        String original = InstalledCharacteristicLinkApprovalTargetDigester.digest(c);
        for (int field = 0; field < 13; field++) {
            var changed = new InstalledCharacteristicLink(
                    field == 2 ? "link-2" : c.linkId(),
                    field == 3 ? 8 : c.linkRevision(),
                    field == 0 ? "party-2" : c.companyPartyId(),
                    field == 1 ? "unit-2" : c.installedUnitId(),
                    field == 4 ? "source-2" : c.sourceId(),
                    field == 5 ? "source-r4" : c.sourceRevisionId(),
                    field == 6 ? "b".repeat(64) : c.sourceSha256(),
                    field == 7 ? InstalledCharacteristicLink.Kind.VALVE_MAP : c.kind(),
                    field == 8 ? c.effectiveFrom().plusSeconds(1) : c.effectiveFrom(),
                    field == 9 ? c.effectiveFrom().plusSeconds(3_600) : c.effectiveUntil(),
                    field == 10 ? c.recordedAt().plusSeconds(1) : c.recordedAt(),
                    field == 11 ? "synthetic:other" : c.evidenceReference(),
                    field == 12 ? "installation-2" : c.installationId());
            assertNotEquals(original, InstalledCharacteristicLinkApprovalTargetDigester.digest(changed),
                    "Canonical field " + field + " must be part of exact target identity.");
        }
    }

    @Test void absentAndPresentInstallationOrEffectiveEndCannotAlias() {
        var c = candidate();
        var present = new InstalledCharacteristicLink(c.linkId(), c.linkRevision(), c.companyPartyId(),
                c.installedUnitId(), c.sourceId(), c.sourceRevisionId(), c.sourceSha256(), c.kind(),
                c.effectiveFrom(), c.effectiveFrom().plusSeconds(10), c.recordedAt(),
                c.evidenceReference(), "installation-1");
        assertNotEquals(InstalledCharacteristicLinkApprovalTargetDigester.digest(c),
                InstalledCharacteristicLinkApprovalTargetDigester.digest(present));
    }

    @Test void offsetEquivalentInstantSerializesAsSameUtcSixDigitMicroseconds() {
        var c = candidate();
        var withOffset = new InstalledCharacteristicLink(c.linkId(), c.linkRevision(),
                c.companyPartyId(), c.installedUnitId(), c.sourceId(), c.sourceRevisionId(),
                c.sourceSha256(), c.kind(),
                OffsetDateTime.parse("2026-11-01T13:34:56.123456+01:00").toInstant(),
                null, c.recordedAt(), c.evidenceReference(), null);
        assertEquals(InstalledCharacteristicLinkApprovalTargetDigester.digest(c),
                InstalledCharacteristicLinkApprovalTargetDigester.digest(withOffset));
    }

    @Test void separateFieldLengthsAvoidAmbiguousConcatenation() {
        var c = candidate();
        var one = new InstalledCharacteristicLink(c.linkId(), c.linkRevision(), "ab", "c",
                c.sourceId(), c.sourceRevisionId(), c.sourceSha256(), c.kind(),
                c.effectiveFrom(), null, c.recordedAt(), c.evidenceReference(), null);
        var two = new InstalledCharacteristicLink(c.linkId(), c.linkRevision(), "a", "bc",
                c.sourceId(), c.sourceRevisionId(), c.sourceSha256(), c.kind(),
                c.effectiveFrom(), null, c.recordedAt(), c.evidenceReference(), null);
        assertNotEquals(InstalledCharacteristicLinkApprovalTargetDigester.digest(one),
                InstalledCharacteristicLinkApprovalTargetDigester.digest(two));
    }

    @Test void rejectsNullInsteadOfReturningAReusableDefaultTarget() {
        assertThrows(InvalidTopologyValueException.class,
                () -> InstalledCharacteristicLinkApprovalTargetDigester.digest(null));
    }

    @Test void constructorRejectsNonMicrosecondTimestampsBeforeHashing() {
        var c = candidate();
        assertThrows(InvalidTopologyValueException.class,
                () -> new InstalledCharacteristicLink(c.linkId(), c.linkRevision(), c.companyPartyId(),
                        c.installedUnitId(), c.sourceId(), c.sourceRevisionId(), c.sourceSha256(),
                        c.kind(), c.effectiveFrom().plusNanos(1), null, c.recordedAt(),
                        c.evidenceReference(), null));
    }

    @Test void historicalRevisionRemainsStableAfterNewRevisionIsCreated() {
        var old = candidate();
        String oldDigest = InstalledCharacteristicLinkApprovalTargetDigester.digest(old);
        var next = new InstalledCharacteristicLink(old.linkId(), 8, old.companyPartyId(),
                old.installedUnitId(), old.sourceId(), "source-r4", "b".repeat(64), old.kind(),
                old.effectiveFrom(), null, old.recordedAt(), old.evidenceReference(), null);
        assertNotEquals(oldDigest, InstalledCharacteristicLinkApprovalTargetDigester.digest(next));
        assertEquals(oldDigest, InstalledCharacteristicLinkApprovalTargetDigester.digest(old));
    }
}
