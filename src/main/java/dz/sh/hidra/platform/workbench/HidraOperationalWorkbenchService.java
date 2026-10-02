/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HidraOperationalWorkbenchService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Class
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.workbench
 *
 * @Description : Provides generic read, detail, list, and search access for Hidra operational workbenches.
 *
 */
package dz.sh.hidra.platform.workbench;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.metamodel.EntityType;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Provides generic read, detail, list, and search access for Hidra operational workbenches.
 */
@Service
public class HidraOperationalWorkbenchService {

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 50;
    private static final int MAX_SIZE = 200;

    private final EntityManager entityManager;
    private final Map<String, List<OperationalResource>> resourcesByModule = new ConcurrentHashMap<>();
    private final Map<String, OperationalResource> resourcesByKey = new ConcurrentHashMap<>();

    public HidraOperationalWorkbenchService(EntityManager entityManager) {
        this.entityManager = Objects.requireNonNull(entityManager, "EntityManager must not be null.");
    }

    @Transactional(readOnly = true)
    public List<String> listModules() {
        ensureIndexed();
        return resourcesByModule.keySet().stream().sorted().toList();
    }

    @Transactional(readOnly = true)
    public List<OperationalResourceDescriptor> listResources(String module) {
        ensureIndexed();
        String normalizedModule = normalizeModule(module);
        return resourcesByModule.getOrDefault(normalizedModule, List.of()).stream()
                .map(this::toDescriptor)
                .sorted(Comparator.comparing(OperationalResourceDescriptor::resource))
                .toList();
    }

    @Transactional(readOnly = true)
    public OperationalPageResponse list(String module, String resource, Integer page, Integer size, String query) {
        OperationalSearchRequest request = new OperationalSearchRequest(query, Map.of(), page, size, null, null);
        return search(module, resource, request);
    }

    @Transactional(readOnly = true)
    public OperationalRecordResponse detail(String module, String resource, String id) {
        OperationalResource resolved = resolve(module, resource);
        Object entity = entityManager.find(resolved.javaType(), id);
        if (entity == null) {
            throw new IllegalArgumentException("No " + resolved.resource() + " record found with id " + id + ".");
        }
        return toRecord(resolved, entity);
    }

    @Transactional(readOnly = true)
    public OperationalPageResponse search(String module, String resource, OperationalSearchRequest request) {
        OperationalResource resolved = resolve(module, resource);
        OperationalSearchRequest normalizedRequest = normalize(request);
        int page = normalizedPage(normalizedRequest.page());
        int size = normalizedSize(normalizedRequest.size());

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object> query = builder.createQuery();
        Root<?> root = query.from(resolved.javaType());
        query.select(root);
        List<Predicate> predicates = predicates(builder, root, resolved, normalizedRequest);
        if (!predicates.isEmpty()) {
            query.where(predicates.toArray(Predicate[]::new));
        }
        order(builder, root, resolved, normalizedRequest).ifPresent(query::orderBy);

        List<Object> entities = entityManager.createQuery(query)
                .setFirstResult(page * size)
                .setMaxResults(size)
                .getResultList();

        CriteriaQuery<Long> countQuery = builder.createQuery(Long.class);
        Root<?> countRoot = countQuery.from(resolved.javaType());
        countQuery.select(builder.count(countRoot));
        List<Predicate> countPredicates = predicates(builder, countRoot, resolved, normalizedRequest);
        if (!countPredicates.isEmpty()) {
            countQuery.where(countPredicates.toArray(Predicate[]::new));
        }
        long total = entityManager.createQuery(countQuery).getSingleResult();
        int totalPages = (int) Math.ceil((double) total / (double) size);

        return new OperationalPageResponse(
                resolved.module(),
                resolved.resource(),
                page,
                size,
                total,
                totalPages,
                entities.stream().map(entity -> toRecord(resolved, entity)).toList()
        );
    }

    private OperationalResource resolve(String module, String resource) {
        ensureIndexed();
        String key = resourceKey(normalizeModule(module), normalizeResource(resource));
        OperationalResource resolved = resourcesByKey.get(key);
        if (resolved == null) {
            throw new IllegalArgumentException("Unsupported operational workbench resource: " + module + "/" + resource + ".");
        }
        return resolved;
    }

    private void ensureIndexed() {
        if (!resourcesByKey.isEmpty()) {
            return;
        }
        synchronized (this) {
            if (!resourcesByKey.isEmpty()) {
                return;
            }
            for (EntityType<?> entityType : entityManager.getMetamodel().getEntities()) {
                Class<?> javaType = entityType.getJavaType();
                Optional<String> module = moduleOf(javaType);
                if (module.isEmpty()) {
                    continue;
                }
                String resource = resourceName(javaType.getSimpleName());
                String idField = idField(javaType).map(Field::getName).orElse("id");
                OperationalResource operationalResource = new OperationalResource(
                        module.get(),
                        resource,
                        entityType.getName(),
                        javaType,
                        tableName(javaType),
                        idField,
                        searchableFields(javaType)
                );
                resourcesByKey.put(resourceKey(module.get(), resource), operationalResource);
                resourcesByModule.computeIfAbsent(module.get(), ignored -> new ArrayList<>()).add(operationalResource);
            }
            resourcesByModule.values().forEach(list -> list.sort(Comparator.comparing(OperationalResource::resource)));
        }
    }

    private OperationalResourceDescriptor toDescriptor(OperationalResource resource) {
        return new OperationalResourceDescriptor(
                resource.module(),
                resource.resource(),
                resource.entityName(),
                resource.javaType().getName(),
                resource.tableName(),
                resource.idField(),
                resource.searchableFields(),
                "/api/v1/workbench/" + resource.module() + "/" + resource.resource(),
                "/api/v1/workbench/" + resource.module() + "/" + resource.resource() + "/{id}",
                "/api/v1/workbench/" + resource.module() + "/" + resource.resource() + "/search"
        );
    }

    private OperationalRecordResponse toRecord(OperationalResource resource, Object entity) {
        Map<String, Object> attributes = new LinkedHashMap<>();
        for (Field field : allFields(entity.getClass())) {
            field.setAccessible(true);
            try {
                attributes.put(field.getName(), normalizeValue(field.get(entity)));
            } catch (IllegalAccessException exception) {
                attributes.put(field.getName(), null);
            }
        }
        return new OperationalRecordResponse(resource.module(), resource.resource(), attributes.get(resource.idField()), attributes);
    }

    private List<Predicate> predicates(CriteriaBuilder builder, Root<?> root, OperationalResource resource, OperationalSearchRequest request) {
        List<Predicate> predicates = new ArrayList<>();
        if (request.query() != null && !request.query().isBlank() && !resource.searchableFields().isEmpty()) {
            String pattern = "%" + request.query().trim().toLowerCase(Locale.ROOT) + "%";
            List<Predicate> orPredicates = new ArrayList<>();
            for (String field : resource.searchableFields()) {
                Expression<String> value = root.get(field).as(String.class);
                orPredicates.add(builder.like(builder.lower(value), pattern));
            }
            predicates.add(builder.or(orPredicates.toArray(Predicate[]::new)));
        }
        Map<String, Object> filters = request.filters() == null ? Map.of() : request.filters();
        Set<String> allowedFields = allFields(resource.javaType()).stream().map(Field::getName).collect(java.util.stream.Collectors.toSet());
        for (Map.Entry<String, Object> entry : filters.entrySet()) {
            if (entry.getKey() == null || !allowedFields.contains(entry.getKey()) || entry.getValue() == null) {
                continue;
            }
            predicates.add(builder.equal(root.get(entry.getKey()).as(String.class), entry.getValue().toString()));
        }
        return predicates;
    }

    private Optional<Order> order(CriteriaBuilder builder, Root<?> root, OperationalResource resource, OperationalSearchRequest request) {
        if (request.sortBy() == null || request.sortBy().isBlank()) {
            return Optional.empty();
        }
        Set<String> allowedFields = allFields(resource.javaType()).stream().map(Field::getName).collect(java.util.stream.Collectors.toSet());
        if (!allowedFields.contains(request.sortBy())) {
            return Optional.empty();
        }
        if ("desc".equalsIgnoreCase(request.sortDirection())) {
            return Optional.of(builder.desc(root.get(request.sortBy())));
        }
        return Optional.of(builder.asc(root.get(request.sortBy())));
    }

    private OperationalSearchRequest normalize(OperationalSearchRequest request) {
        if (request == null) {
            return new OperationalSearchRequest(null, Map.of(), DEFAULT_PAGE, DEFAULT_SIZE, null, null);
        }
        return new OperationalSearchRequest(
                request.query(),
                request.filters() == null ? Map.of() : request.filters(),
                request.page(),
                request.size(),
                request.sortBy(),
                request.sortDirection()
        );
    }

    private static int normalizedPage(Integer page) {
        return page == null || page < 0 ? DEFAULT_PAGE : page;
    }

    private static int normalizedSize(Integer size) {
        if (size == null || size <= 0) {
            return DEFAULT_SIZE;
        }
        return Math.min(size, MAX_SIZE);
    }

    private static Optional<String> moduleOf(Class<?> javaType) {
        String packageName = javaType.getPackageName();
        String marker = ".modules.";
        int index = packageName.indexOf(marker);
        if (index < 0) {
            return Optional.empty();
        }
        String afterMarker = packageName.substring(index + marker.length());
        int dotIndex = afterMarker.indexOf('.');
        if (dotIndex < 0) {
            return Optional.empty();
        }
        return Optional.of(afterMarker.substring(0, dotIndex));
    }

    private static String tableName(Class<?> javaType) {
        Table table = javaType.getAnnotation(Table.class);
        if (table == null || table.name().isBlank()) {
            return javaType.getSimpleName();
        }
        return table.name();
    }

    private static List<String> searchableFields(Class<?> javaType) {
        return allFields(javaType).stream()
                .filter(field -> String.class.equals(field.getType()))
                .map(Field::getName)
                .sorted()
                .toList();
    }

    private static Optional<Field> idField(Class<?> javaType) {
        return allFields(javaType).stream()
                .filter(field -> field.isAnnotationPresent(Id.class) || "id".equals(field.getName()))
                .findFirst();
    }

    private static List<Field> allFields(Class<?> javaType) {
        List<Field> fields = new ArrayList<>();
        Class<?> current = javaType;
        while (current != null && !Object.class.equals(current)) {
            fields.addAll(List.of(current.getDeclaredFields()));
            current = current.getSuperclass();
        }
        return fields;
    }

    private static Object normalizeValue(Object value) {
        if (value == null || value instanceof String || value instanceof Number || value instanceof Boolean || value instanceof BigDecimal || value instanceof Instant) {
            return value;
        }
        if (value instanceof Enum<?> enumValue) {
            return enumValue.name();
        }
        if (value instanceof Collection<?> collection) {
            return collection.stream().map(HidraOperationalWorkbenchService::normalizeValue).toList();
        }
        return value.toString();
    }

    private static String normalizeModule(String module) {
        if (module == null || module.isBlank()) {
            throw new IllegalArgumentException("Module must not be blank.");
        }
        return module.trim().toLowerCase(Locale.ROOT);
    }

    private static String normalizeResource(String resource) {
        if (resource == null || resource.isBlank()) {
            throw new IllegalArgumentException("Resource must not be blank.");
        }
        return resource.trim().toLowerCase(Locale.ROOT);
    }

    private static String resourceKey(String module, String resource) {
        return module + ":" + resource;
    }

    private static String resourceName(String simpleClassName) {
        String base = simpleClassName.endsWith("JpaEntity")
                ? simpleClassName.substring(0, simpleClassName.length() - "JpaEntity".length())
                : simpleClassName;
        String kebab = base.replaceAll("([a-z])([A-Z])", "$1-$2").toLowerCase(Locale.ROOT);
        if (kebab.endsWith("y")) {
            return kebab.substring(0, kebab.length() - 1) + "ies";
        }
        if (kebab.endsWith("s")) {
            return kebab;
        }
        return kebab + "s";
    }

    private record OperationalResource(
            String module,
            String resource,
            String entityName,
            Class<?> javaType,
            String tableName,
            String idField,
            List<String> searchableFields
    ) { }
}
