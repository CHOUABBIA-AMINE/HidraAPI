/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HidraOperationalWorkbenchExposurePolicy
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-05
 *
 * @Type        : Class
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.workbench
 *
 * @Description : Enforces fail-closed resource and field exposure for the generic operational workbench.
 *
 */
package dz.sh.hidra.platform.workbench;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Enforces explicit resource and field approval for generic workbench reads.
 *
 * <p>Configuration format:
 * {@code module/resource=id,fieldA,fieldB;other-module/other-resource=id,name}.
 * An absent or blank configuration exposes no resources.</p>
 */
@Component
public final class HidraOperationalWorkbenchExposurePolicy {

    private static final String LOCAL_CREDENTIAL_RESOURCE = "identity:local-credentials";

    private final Map<String, Set<String>> approvedFieldsByResource;

    public HidraOperationalWorkbenchExposurePolicy(
            @Value("${hidra.platform.workbench.exposure:}") String configuredExposure
    ) {
        this.approvedFieldsByResource = parse(configuredExposure);
    }

    public boolean hasNoApprovedResources() {
        return approvedFieldsByResource.isEmpty();
    }

    public Optional<Set<String>> approvedFields(String module, String resource) {
        String key = resourceKey(module, resource);
        if (isProhibitedResource(key)) {
            return Optional.empty();
        }
        return Optional.ofNullable(approvedFieldsByResource.get(key));
    }

    private static Map<String, Set<String>> parse(String configuredExposure) {
        if (configuredExposure == null || configuredExposure.isBlank()) {
            return Map.of();
        }

        Map<String, Set<String>> approvals = new LinkedHashMap<>();
        for (String rawEntry : configuredExposure.split(";")) {
            String entry = rawEntry.trim();
            if (entry.isEmpty()) {
                continue;
            }

            String[] assignment = entry.split("=", 2);
            if (assignment.length != 2) {
                throw invalidConfiguration(entry, "expected module/resource=field1,field2");
            }

            String[] resourceParts = assignment[0].trim().split("/", 2);
            if (resourceParts.length != 2) {
                throw invalidConfiguration(entry, "expected module/resource key");
            }

            String key = resourceKey(resourceParts[0], resourceParts[1]);
            if (isProhibitedResource(key)) {
                throw invalidConfiguration(entry, "credential/secret resources cannot be exposed");
            }
            if (approvals.containsKey(key)) {
                throw invalidConfiguration(entry, "duplicate resource approval");
            }

            Set<String> fields = new LinkedHashSet<>();
            for (String rawField : assignment[1].split(",")) {
                String field = rawField.trim();
                if (field.isEmpty()) {
                    continue;
                }
                if (!field.matches("[A-Za-z_][A-Za-z0-9_]*")) {
                    throw invalidConfiguration(entry, "invalid Java field name " + field);
                }
                if (isProhibitedField(field)) {
                    throw invalidConfiguration(entry, "password/secret fields cannot be exposed");
                }
                fields.add(field);
            }
            if (fields.isEmpty()) {
                throw invalidConfiguration(entry, "at least one approved field is required");
            }

            approvals.put(key, Set.copyOf(fields));
        }
        return Map.copyOf(approvals);
    }

    private static boolean isProhibitedResource(String key) {
        if (LOCAL_CREDENTIAL_RESOURCE.equals(key)) {
            return true;
        }
        String normalized = key.toLowerCase(Locale.ROOT);
        return normalized.contains("credential") || normalized.contains("secret");
    }

    private static boolean isProhibitedField(String fieldName) {
        String normalized = fieldName.toLowerCase(Locale.ROOT);
        return normalized.contains("password") || normalized.contains("secret");
    }

    private static String resourceKey(String module, String resource) {
        if (module == null || module.isBlank() || resource == null || resource.isBlank()) {
            throw new IllegalArgumentException("Workbench module and resource must not be blank.");
        }
        return module.trim().toLowerCase(Locale.ROOT) + ":" + resource.trim().toLowerCase(Locale.ROOT);
    }

    private static IllegalArgumentException invalidConfiguration(String entry, String reason) {
        return new IllegalArgumentException(
                "Invalid hidra.platform.workbench.exposure entry '" + entry + "': " + reason + "."
        );
    }
}
