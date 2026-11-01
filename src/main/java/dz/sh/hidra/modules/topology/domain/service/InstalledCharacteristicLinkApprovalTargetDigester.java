/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InstalledCharacteristicLinkApprovalTargetDigester
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-01
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.service
 *
 * @Description : Computes the versioned canonical approval target digest over an immutable proposed characteristic link.
 *
 */
package dz.sh.hidra.modules.topology.domain.service;

import dz.sh.hidra.modules.topology.domain.exception.InvalidTopologyValueException;
import dz.sh.hidra.modules.topology.domain.model.InstalledCharacteristicLink;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.HexFormat;
import java.util.Locale;

/**
 * Pure target identity: this digest is NOT a Workflow approval, a grant, or
 * evidence that manufacturer facts remain available to a current requester.
 *
 * V1 encoding: first the version tag, then exactly thirteen fields in the
 * stated order. Every UTF-8 field is ASCII decimal byte-length, ':' followed
 * by the raw UTF-8 bytes and LF. The null marker is "-1:\n", distinct from
 * an empty string ("0:\n"). Times are UTC with precisely six fractional
 * digits. No platform charset, locale, zone or Java record toString is used.
 */
public final class InstalledCharacteristicLinkApprovalTargetDigester {
    public static final String VERSION = "HIDRA_INSTALLED_CHARACTERISTIC_LINK_APPROVAL_TARGET_V1";
    private static final DateTimeFormatter MICROS =
            new DateTimeFormatterBuilder().appendInstant(6).toFormatter(Locale.ROOT);

    private InstalledCharacteristicLinkApprovalTargetDigester() {}

    public static String digest(InstalledCharacteristicLink link) {
        if (link == null) throw new InvalidTopologyValueException("Exact immutable approval target required.");
        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            field(sha, VERSION);
            field(sha, link.companyPartyId());
            field(sha, link.installedUnitId());
            field(sha, link.linkId());
            field(sha, Long.toString(link.linkRevision()));
            field(sha, link.sourceId());
            field(sha, link.sourceRevisionId());
            field(sha, link.sourceSha256());
            field(sha, link.kind().name());
            field(sha, time(link.effectiveFrom()));
            field(sha, time(link.effectiveUntil()));
            field(sha, time(link.recordedAt()));
            field(sha, link.evidenceReference());
            field(sha, link.installationId());
            return HexFormat.of().formatHex(sha.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("JVM SHA-256 algorithm unavailable.", e);
        }
    }

    private static String time(Instant time) {
        if (time == null) return null;
        if (time.getNano() % 1_000 != 0)
            throw new InvalidTopologyValueException("Approval target timestamps must have microsecond precision.");
        return MICROS.format(time);
    }

    private static void field(MessageDigest sha, String value) {
        if (value == null) {
            sha.update("-1:\n".getBytes(StandardCharsets.US_ASCII));
            return;
        }
        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
        sha.update(Integer.toString(bytes.length).getBytes(StandardCharsets.US_ASCII));
        sha.update((byte) ':');
        sha.update(bytes);
        sha.update((byte) '\n');
    }
}
