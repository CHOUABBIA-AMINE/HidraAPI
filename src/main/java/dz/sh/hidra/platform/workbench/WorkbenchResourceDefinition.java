/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkbenchResourceDefinition
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-05
 *
 * @Type        : Record
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.workbench
 *
 * @Description : Defines an explicit reviewed workbench projection and its query field contract.
 *
 */
package dz.sh.hidra.platform.workbench;

import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * No persistence type or schema discovery belongs in this public projection contract.
 * Sensitive-name checks are defense in depth; approval of every field remains mandatory.
 */
public record WorkbenchResourceDefinition(
        String module,
        String resource,
        String idField,
        List<String> outputFields,
        List<String> searchableFields,
        List<String> filterableFields,
        List<String> sortableFields,
        String purpose,
        String owner,
        String approvalReference
) {
    private static final Pattern IDENTIFIER = Pattern.compile("[a-z][a-z0-9]*(?:-[a-z0-9]+)*");
    private static final Pattern FIELD = Pattern.compile("[a-z][A-Za-z0-9]*");
    private static final Pattern SENSITIVE = Pattern.compile(
            ".*(password|credential|secret|token|apikey|privatekey|privatecertificate|privatecert|connectionsettings|connectionoptions).*"
    );

    public WorkbenchResourceDefinition {
        if (!validIdentifier(module) || !validIdentifier(resource) || sensitive(resource)
                || purpose == null || purpose.isBlank() || owner == null || owner.isBlank()
                || approvalReference == null || approvalReference.isBlank()) {
            throw invalid();
        }
        outputFields = fields(outputFields);
        searchableFields = fields(searchableFields);
        filterableFields = fields(filterableFields);
        sortableFields = fields(sortableFields);
        if (!outputFields.contains(idField) || !outputFields.containsAll(searchableFields)
                || !outputFields.containsAll(filterableFields) || !outputFields.containsAll(sortableFields)) {
            throw invalid();
        }
    }

    public String permission(String action) {
        if (!"read".equals(action) && !"search".equals(action)) {
            throw invalid();
        }
        return module + ":" + resource + ":" + action;
    }

    static boolean validIdentifier(String value) {
        return value != null && IDENTIFIER.matcher(value).matches();
    }

    private static List<String> fields(List<String> fields) {
        if (fields == null || fields.stream().anyMatch(field -> field == null
                || !FIELD.matcher(field).matches() || sensitive(field) || field.toLowerCase(Locale.ROOT).endsWith("json"))
                || fields.stream().distinct().count() != fields.size()) {
            throw invalid();
        }
        return List.copyOf(fields);
    }

    private static boolean sensitive(String value) {
        return SENSITIVE.matcher(value.replaceAll("[^A-Za-z0-9]", "").toLowerCase(Locale.ROOT)).matches();
    }

    private static IllegalArgumentException invalid() {
        return new IllegalArgumentException("Invalid workbench exposure contract.");
    }
}
