/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : AbsoluteDeviationReportJsonV1
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-18
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.domain.service
 * @Description : Scoped, versioned T25 report delivery.
 */
package dz.sh.hidra.modules.reporting.domain.service;
import dz.sh.hidra.modules.reporting.domain.model.ScopedReportRun.Projection;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

public final class AbsoluteDeviationReportJsonV1 {
    public static final String PROFILE = "GZ2_ABSOLUTE_DEVIATION_JSON_V1";
    public static final int MAX_BYTES = 65_536;
    private AbsoluteDeviationReportJsonV1() {}

    public static byte[] render(Projection p) {
        String data = "{"
                + field("profile", PROFILE) + "," + field("metricRunId", p.metricRunId()) + ","
                + field("definitionVersionId", p.definitionVersionId()) + ","
                + field("methodVersion", p.methodVersion()) + ",\"value\":"
                + p.value().stripTrailingZeros().toPlainString() + "," + field("unitId", p.unitId()) + ","
                + field("evaluationId", p.evaluationId()) + "," + field("ruleId", p.ruleId())
                + ",\"ruleRevision\":" + p.ruleRevision() + "," + field("targetId", p.targetId()) + ","
                + field("targetRevisionId", p.targetRevisionId()) + "," + field("approvalId", p.approvalId()) + ","
                + field("trustedReadingId", p.trustedReadingId()) + "," + field("rawReadingId", p.rawReadingId()) + ","
                + field("assessmentId", p.assessmentId()) + "," + field("bindingId", p.bindingId()) + ","
                + field("evaluatedAt", p.evaluatedAt().toString()) + ","
                + field("recordedAt", p.recordedAt().toString()) + "}\n";
        byte[] bytes = data.getBytes(StandardCharsets.UTF_8);
        if (bytes.length > MAX_BYTES) throw new IllegalArgumentException("Report exceeds profile size.");
        return bytes;
    }
    private static String field(String key, String value) {
        var result = new StringBuilder().append('"').append(key).append("\":\"");
        value.codePoints().forEach(cp -> {
            switch (cp) {
                case '"' -> result.append("\\\"");
                case '\\' -> result.append("\\\\");
                default -> {
                    if (cp < 32) result.append(String.format("\\u%04x",cp));
                    else if (cp >= 0xd800 && cp <= 0xdfff)
                        throw new IllegalArgumentException("Invalid Unicode in report projection.");
                    else result.appendCodePoint(cp);
                }
            }
        });
        return result.append('"').toString();
    }
    public static String sha256(byte[] bytes) {
        try {
            return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(bytes));
        } catch (NoSuchAlgorithmException impossible) {
            throw new IllegalStateException(impossible);
        }
    }
}
