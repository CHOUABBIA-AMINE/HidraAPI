/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditInputPolicy
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-07
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.application.service
 *
 * @Description : Enforces Audit evidence integrity and explicit owner boundaries.
 *
 */
package dz.sh.hidra.modules.audit.application.service;

import dz.sh.hidra.modules.audit.domain.exception.InvalidAuditValueException;
import dz.sh.hidra.modules.audit.domain.policy.AuditBoundaryPolicy;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;
import tools.jackson.core.StreamReadConstraints;
import tools.jackson.core.StreamReadFeature;
import tools.jackson.core.json.JsonFactory;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.json.JsonMapper;

/** Bounded sanitation; no raw input is included in policy errors. */
@Component
public final class AuditInputPolicy {
    public static final int MAX_JSON_BYTES = 65_536;
    public static final int MAX_JSON_DEPTH = 32;
    private static final String REDACTED = "[REDACTED]";
    private static final Pattern CREDENTIAL = Pattern.compile(
            "(?is)-----BEGIN[^\\r\\n]*PRIVATE KEY-----|\\bbearer\\s+[A-Za-z0-9+/=_\\-.]{7,}"
            + "|(?:password|token|secret|private[ _-]*key|credential|api[ _-]*key|authorization|cookie|session)"
            + "\\s*[:=]\\s*\\S+");
    private static final Pattern BASIC = Pattern.compile("(?i)\\bbasic\\s+([A-Za-z0-9+/]+={0,2})");
    private static final JsonMapper JSON = JsonMapper.builder(JsonFactory.builder()
            .streamReadConstraints(StreamReadConstraints.builder().maxNestingDepth(MAX_JSON_DEPTH).build())
            .enable(StreamReadFeature.STRICT_DUPLICATE_DETECTION).build())
            .enable(DeserializationFeature.FAIL_ON_TRAILING_TOKENS,
                    DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS,
                    DeserializationFeature.USE_BIG_INTEGER_FOR_INTS).build();

    public String json(String input, boolean required) {
        if (input == null || input.isBlank()) {
            if (required) throw new InvalidAuditValueException("Required Audit JSON is missing.");
            return null;
        }
        budget(input);
        try {
            Object value = JSON.readValue(input, Object.class);
            if (!(value instanceof Map<?, ?>) && !(value instanceof List<?>)) {
                throw new InvalidAuditValueException("Audit JSON must be an object or array.");
            }
            String sanitized = JSON.writeValueAsString(sanitize(value));
            budget(sanitized);
            return sanitized;
        } catch (InvalidAuditValueException e) {
            throw e;
        } catch (RuntimeException e) {
            // Jackson errors may echo the source: deliberately omit cause and raw text.
            throw new InvalidAuditValueException("Audit JSON is invalid or exceeds parsing constraints.");
        }
    }
    private Object sanitize(Object value) {
        if (value instanceof Map<?, ?> map) {
            Map<String, Object> result = new LinkedHashMap<>();
            for (var entry : map.entrySet()) {
                String key = (String) entry.getKey();
                text(key, Integer.MAX_VALUE);
                result.put(key, AuditBoundaryPolicy.isSensitiveFieldPath(key)
                        ? REDACTED : sanitize(entry.getValue()));
            }
            return result;
        }
        if (value instanceof List<?> list) {
            List<Object> result = new ArrayList<>(list.size());
            for (Object item : list) result.add(sanitize(item));
            return result;
        }
        if (value instanceof String text) return text(text, Integer.MAX_VALUE);
        return value;
    }
    private void budget(String input) {
        if (input.getBytes(StandardCharsets.UTF_8).length > MAX_JSON_BYTES) {
            throw new InvalidAuditValueException("Audit JSON exceeds the 65536-byte limit.");
        }
    }
    public String text(String input, int maxLength) {
        if (input == null || input.isBlank()) return null;
        if (input.length() > maxLength) throw new InvalidAuditValueException("Audit text exceeds its field limit.");
        String normalized = java.text.Normalizer.normalize(input, java.text.Normalizer.Form.NFKC);
        if (CREDENTIAL.matcher(normalized).find() || basicCredential(normalized)) {
            throw new InvalidAuditValueException("Credential material is forbidden in Audit evidence.");
        }
        return input.trim();
    }
    private boolean basicCredential(String text) {
        var matcher=BASIC.matcher(text);
        while(matcher.find()) {
            try {
                String decoded=new String(Base64.getDecoder().decode(matcher.group(1)),StandardCharsets.UTF_8);
                if(decoded.indexOf(':')>=0)return true;
            } catch(IllegalArgumentException ignored) { /* Not a Basic credential token. */ }
        }
        return false;
    }
    public String hash(String sanitized) {
        try {
            return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256")
                    .digest(sanitized.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException e) { throw new IllegalStateException("SHA-256 is unavailable."); }
    }
    public dz.sh.hidra.modules.audit.domain.model.AuditEvent event(dz.sh.hidra.modules.audit.domain.model.AuditEvent model) {
        return new dz.sh.hidra.modules.audit.domain.model.AuditEvent(
                text(model.id(),80),
                text(model.eventTypeId(),80),
                text(model.eventCategoryId(),80),
                text(model.severityId(),80),
                text(model.sourceModule(),80),
                text(model.sourceComponent(),120),
                text(model.sourceEventId(),120),
                text(model.actionCode(),120),
                text(model.actionLabelSnapshot(),240),
                model.eventStatus(),
                text(model.actorId(),120),
                model.actorType(),
                text(model.actorDisplayNameSnapshot(),160),
                text(model.actorUsernameSnapshot(),120),
                text(model.actorRoleCodeSnapshot(),120),
                text(model.organizationUnitId(),120),
                text(model.organizationUnitCodeSnapshot(),120),
                text(model.organizationUnitNameSnapshot(),160),
                text(model.targetModule(),80),
                text(model.targetType(),120),
                text(model.targetId(),120),
                text(model.targetCodeSnapshot(),120),
                text(model.targetLabelSnapshot(),240),
                model.operation(),
                text(model.decisionCode(),120),
                text(model.reasonId(),80),
                text(model.reasonText(),1000),
                text(model.commentText(),2000),
                text(model.workflowInstanceId(),120),
                text(model.workflowTaskId(),120),
                text(model.workflowActionId(),120),
                text(model.workflowFromState(),80),
                text(model.workflowToState(),80),
                text(model.requestId(),120),
                text(model.correlationId(),120),
                text(model.causationId(),120),
                text(model.ipAddressMasked(),80),
                text(model.userAgentSnapshot(),500),
                text(model.sourceSystemCode(),120),
                model.occurredAt(),
                model.recordedAt(),
                text(model.retentionPolicyId(),80),
                text(model.hashValue(),256),
                text(model.previousHashValue(),256),
                json(model.payloadJson(),false));
    }

}
