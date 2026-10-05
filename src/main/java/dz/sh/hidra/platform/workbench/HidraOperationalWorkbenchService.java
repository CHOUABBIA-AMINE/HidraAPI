/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HidraOperationalWorkbenchService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-05
 *
 * @Type        : Class
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.workbench
 *
 * @Description : Enforces reviewed projections and concrete authorization for scoped workbench reads.
 *
 */
package dz.sh.hidra.platform.workbench;

import dz.sh.hidra.platform.security.HidraEffectivePermissionResolver;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Supplier;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** No entity discovery, reflection, raw nested values or unscoped persistence access. */
@Service
public class HidraOperationalWorkbenchService {
    private static final int DEFAULT_SIZE = 50;
    private static final int MAX_SIZE = 200;
    private static final Set<Class<?>> SCALARS = Set.of(String.class, Boolean.class, Integer.class,
            Long.class, Short.class, Byte.class, Double.class, Float.class, BigDecimal.class,
            BigInteger.class, UUID.class, Instant.class, LocalDate.class, LocalDateTime.class,
            OffsetDateTime.class);
    private final WorkbenchResourceRegistry registry;
    private final HidraEffectivePermissionResolver permissions;

    public HidraOperationalWorkbenchService(WorkbenchResourceRegistry registry,
            HidraEffectivePermissionResolver permissions) {
        this.registry = java.util.Objects.requireNonNull(registry);
        this.permissions = java.util.Objects.requireNonNull(permissions);
    }

    public List<String> listModules() {
        Authentication authentication = authenticated();
        return registry.entries().stream().filter(entry -> visible(entry, authentication))
                .map(entry -> entry.definition().module()).distinct().sorted().toList();
    }

    public List<OperationalResourceDescriptor> listResources(String module) {
        Authentication authentication = authenticated();
        if (!WorkbenchResourceDefinition.validIdentifier(module)) {
            throw WorkbenchResourceRegistry.unavailable();
        }
        return registry.entries().stream().filter(entry -> entry.definition().module().equals(module))
                .filter(entry -> visible(entry, authentication)).map(entry -> descriptor(entry.definition()))
                .sorted(Comparator.comparing(OperationalResourceDescriptor::resource)).toList();
    }

    @Transactional(readOnly = true)
    public OperationalPageResponse list(String module, String resource, Integer page, Integer size, String query) {
        return page(module, resource, new OperationalSearchRequest(query, Map.of(), page, size, null, null), "read");
    }

    @Transactional(readOnly = true)
    public OperationalPageResponse search(String module, String resource, OperationalSearchRequest request) {
        return page(module, resource, request, "search");
    }

    @Transactional(readOnly = true)
    public OperationalRecordResponse detail(String module, String resource, String id) {
        Authentication authentication = authenticated();
        WorkbenchResourceRegistry.Entry entry = authorized(module, resource, "read", authentication);
        if (id == null || id.isBlank()) {
            throw WorkbenchResourceRegistry.unavailable();
        }
        WorkbenchResource.ScopedReader reader = scoped(entry, authentication);
        Map<String, Object> row = safely(() -> reader.detail(id)).orElseThrow(WorkbenchResourceRegistry::unavailable);
        return project(entry.definition(), row);
    }

    private OperationalPageResponse page(String module, String resource, OperationalSearchRequest request, String action) {
        Authentication authentication = authenticated();
        WorkbenchResourceRegistry.Entry entry = authorized(module, resource, action, authentication);
        WorkbenchResourceDefinition definition = entry.definition();
        OperationalSearchRequest validated = validate(definition, request);
        WorkbenchResource.ScopedReader reader = scoped(entry, authentication);
        WorkbenchResource.Page result = safely(() -> reader.search(validated));
        if (result.rows().size() > validated.size()) {
            throw invalidProjection();
        }
        long pages = result.total() / validated.size() + (result.total() % validated.size() == 0 ? 0 : 1);
        return new OperationalPageResponse(module, resource, validated.page(), validated.size(), result.total(),
                (int) Math.min(Integer.MAX_VALUE, pages),
                result.rows().stream().map(row -> project(definition, row)).toList());
    }

    private WorkbenchResourceRegistry.Entry authorized(String module, String resource, String action,
            Authentication authentication) {
        WorkbenchResourceRegistry.Entry entry = registry.resolve(module, resource);
        if (!permissions.hasPermission(authentication, entry.definition().permission(action))) {
            throw WorkbenchResourceRegistry.unavailable();
        }
        return entry;
    }

    private boolean visible(WorkbenchResourceRegistry.Entry entry, Authentication authentication) {
        return permissions.hasPermission(authentication, entry.definition().permission("read"))
                && safely(() -> entry.resource().scope(authentication)).isPresent();
    }

    private WorkbenchResource.ScopedReader scoped(WorkbenchResourceRegistry.Entry entry, Authentication authentication) {
        return safely(() -> entry.resource().scope(authentication)).orElseThrow(WorkbenchResourceRegistry::unavailable);
    }

    private static Authentication authenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()
                || authentication instanceof AnonymousAuthenticationToken) {
            throw new AccessDeniedException("Workbench authentication required.");
        }
        return authentication;
    }

    private static OperationalResourceDescriptor descriptor(WorkbenchResourceDefinition definition) {
        String endpoint = "/api/v1/workbench/" + definition.module() + "/" + definition.resource();
        return new OperationalResourceDescriptor(definition.module(), definition.resource(), definition.idField(),
                definition.outputFields(), definition.searchableFields(), definition.filterableFields(),
                definition.sortableFields(), definition.permission("read"), definition.permission("search"),
                endpoint, endpoint + "/{id}", endpoint + "/search");
    }

    private static OperationalSearchRequest validate(WorkbenchResourceDefinition definition, OperationalSearchRequest request) {
        if (request == null) {
            request = new OperationalSearchRequest(null, Map.of(), null, null, null, null);
        }
        if (request.query() != null && !request.query().isBlank() && definition.searchableFields().isEmpty()) {
            throw invalidQuery();
        }
        Map<String, Object> filters = new LinkedHashMap<>();
        if (request.filters() != null) {
            for (Map.Entry<String, Object> filter : request.filters().entrySet()) {
                if (!definition.filterableFields().contains(filter.getKey()) || filter.getValue() == null) {
                    throw invalidQuery();
                }
                filters.put(filter.getKey(), scalar(filter.getValue(), false));
            }
        }
        String sort = request.sortBy();
        if (sort != null && !definition.sortableFields().contains(sort)) {
            throw invalidQuery();
        }
        String direction = request.sortDirection();
        if (direction != null && (sort == null || !("asc".equalsIgnoreCase(direction) || "desc".equalsIgnoreCase(direction)))) {
            throw invalidQuery();
        }
        int page = request.page() == null || request.page() < 0 ? 0 : request.page();
        int size = request.size() == null || request.size() <= 0 ? DEFAULT_SIZE : Math.min(MAX_SIZE, request.size());
        if ((long) page * size > Integer.MAX_VALUE) {
            throw invalidQuery();
        }
        return new OperationalSearchRequest(request.query(), Collections.unmodifiableMap(filters), page, size,
                sort, sort == null ? null : direction == null ? "asc" : direction.toLowerCase(java.util.Locale.ROOT));
    }

    private static OperationalRecordResponse project(WorkbenchResourceDefinition definition, Map<String, Object> row) {
        if (row == null || !row.containsKey(definition.idField()) || row.get(definition.idField()) == null) {
            throw invalidProjection();
        }
        Map<String, Object> attributes = new LinkedHashMap<>();
        for (String field : definition.outputFields()) {
            attributes.put(field, scalar(row.get(field), true));
        }
        return new OperationalRecordResponse(definition.module(), definition.resource(),
                attributes.get(definition.idField()), Collections.unmodifiableMap(attributes));
    }

    private static Object scalar(Object value, boolean projection) {
        if (value == null || SCALARS.contains(value.getClass())) {
            if ((value instanceof Double d && !Double.isFinite(d)) || (value instanceof Float f && !Float.isFinite(f))) {
                throw projection ? invalidProjection() : invalidQuery();
            }
            return value;
        }
        if (value instanceof Enum<?> enumValue) {
            return enumValue.name();
        }
        // Nested data must be flattened by an approved module projection, never serialized generically.
        throw projection ? invalidProjection() : invalidQuery();
    }

    private static <T> T safely(Supplier<T> operation) {
        try {
            T result = operation.get();
            if (result == null) {
                throw invalidProjection();
            }
            return result;
        } catch (RuntimeException exception) {
            // Do not retain a provider exception/cause that may contain row or query values.
            throw new IllegalStateException("Workbench read failed.");
        }
    }

    private static IllegalArgumentException invalidQuery() {
        return new IllegalArgumentException("Unsupported workbench query.");
    }

    private static IllegalStateException invalidProjection() {
        return new IllegalStateException("Invalid workbench projection.");
    }
}
