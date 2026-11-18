/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : SyntheticDocumentDraftMappingV1
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-18
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.domain.service
 * @Description : Deterministic single-field bounded projection and canonical digest.
 */
package dz.sh.hidra.modules.integration.domain.service;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.Normalizer;
import java.util.HexFormat;

public final class SyntheticDocumentDraftMappingV1 {
    public static final String PROFILE = "SYNTHETIC_DOCUMENT_DRAFT_V1";
    private SyntheticDocumentDraftMappingV1() {}

    public static String title(String raw) {
        if (raw == null) throw new IllegalArgumentException("Missing titleFr.");
        String canonical = Normalizer.normalize(raw, Normalizer.Form.NFC);
        if (canonical.codePoints().anyMatch(cp -> Character.isISOControl(cp)
                || Character.getType(cp) == Character.FORMAT))
            throw new IllegalArgumentException("Invalid titleFr.");
        String value = canonical.trim();
        if (value.isEmpty() || value.codePointCount(0,value.length()) > 160)
            throw new IllegalArgumentException("Invalid titleFr.");
        return value;
    }
    public static String projection(String title) {
        String safe = title(title);
        StringBuilder json = new StringBuilder("{\"titleFr\":\"");
        for (int i=0; i<safe.length(); i++) {
            char c=safe.charAt(i);
            if (c == '\\' || c == '"') json.append('\\');
            json.append(c);
        }
        return json.append("\"}").toString();
    }
    public static String sha256(String input) {
        try {
            return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256")
                    .digest(input.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException impossible) {
            throw new IllegalStateException(impossible);
        }
    }
    public static String mappingDigest(String profile, String schema, String checksum,
            String documentType, String classification) {
        return sha256("T26|v1|" + profile + "|" + schema + "|" + checksum + "|" +
                documentType + "|" + classification + "|titleFr:STRING:required");
    }
}
