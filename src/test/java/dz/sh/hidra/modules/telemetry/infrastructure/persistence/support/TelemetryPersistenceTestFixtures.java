/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryPersistenceTestFixtures
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : TestSupport
 * @Layer       : Test
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.infrastructure.persistence.support
 *
 * @Description : Reusable telemetry persistence test fixtures and in-memory Spring Data repository proxies.
 *
 */
package dz.sh.hidra.modules.telemetry.infrastructure.persistence.support;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import dz.sh.hidra.modules.telemetry.domain.support.TelemetryDomainTestFixtures;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity.TelemetryDeviceJpaEntity;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity.TelemetryIngestionBatchJpaEntity;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity.TelemetryPointBindingJpaEntity;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity.TelemetryPointJpaEntity;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity.TelemetryReadingJpaEntity;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity.TelemetrySourceJpaEntity;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity.TelemetryTypeCatalogJpaEntity;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity.TelemetryTypeTranslationJpaEntity;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.repository.TelemetryDeviceJpaRepository;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.repository.TelemetryIngestionBatchJpaRepository;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.repository.TelemetryPointBindingJpaRepository;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.repository.TelemetryPointJpaRepository;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.repository.TelemetryReadingJpaRepository;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.repository.TelemetrySourceJpaRepository;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.repository.TelemetryTypeCatalogJpaRepository;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.repository.TelemetryTypeTranslationJpaRepository;

/**
 * Reusable telemetry persistence test fixtures and in-memory Spring Data repository proxies.
 */
public final class TelemetryPersistenceTestFixtures {

    public static final Instant CREATED_AT = TelemetryDomainTestFixtures.CREATED_AT;
    public static final Instant UPDATED_AT = TelemetryDomainTestFixtures.UPDATED_AT;
    public static final Instant SOURCE_TIMESTAMP = TelemetryDomainTestFixtures.SOURCE_TIMESTAMP;
    public static final Instant RECEIVED_AT = TelemetryDomainTestFixtures.RECEIVED_AT;

    private TelemetryPersistenceTestFixtures() {
        // Test support only.
    }

    public static TelemetryTypeCatalogJpaEntity catalogEntity() {
        return new TelemetryTypeCatalogJpaEntity(
                "catalog-point-type-pressure",
                "POINT_TYPE",
                "PRESSURE",
                true,
                10,
                true,
                CREATED_AT,
                UPDATED_AT);
    }

    public static TelemetryTypeTranslationJpaEntity frenchTranslationEntity() {
        return new TelemetryTypeTranslationJpaEntity(
                "translation-pressure-fr",
                catalogEntity().getId(),
                "fr",
                "Pression",
                "Description Pression",
                CREATED_AT,
                UPDATED_AT);
    }

    public static TelemetryTypeTranslationJpaEntity arabicTranslationEntity() {
        return new TelemetryTypeTranslationJpaEntity(
                "translation-pressure-ar",
                catalogEntity().getId(),
                "ar",
                "ضغط",
                "وصف ضغط",
                CREATED_AT,
                UPDATED_AT);
    }

    public static TelemetryTypeTranslationJpaEntity englishTranslationEntity() {
        return new TelemetryTypeTranslationJpaEntity(
                "translation-pressure-en",
                catalogEntity().getId(),
                "en",
                "Pressure",
                "Pressure description",
                CREATED_AT,
                UPDATED_AT);
    }

    public static TelemetrySourceJpaEntity sourceEntity() {
        return new TelemetrySourceJpaEntity(
                "source-001",
                "SCADA-TRC-01",
                "سكادا",
                "SCADA TRC",
                "TRC SCADA",
                "source-type-scada",
                "protocol-opc-ua",
                "opc.tcp://source.local:4840",
                "EXT-SOURCE-001",
                "ACTIVE",
                CREATED_AT,
                UPDATED_AT);
    }

    public static TelemetryDeviceJpaEntity deviceEntity() {
        return new TelemetryDeviceJpaEntity(
                "device-001",
                sourceEntity().getId(),
                "RTU-001",
                "وحدة RTU",
                "RTU principale",
                "Main RTU",
                "device-type-rtu",
                "EXT-DEVICE-001",
                "ACTIVE",
                CREATED_AT,
                UPDATED_AT);
    }

    public static TelemetryPointJpaEntity pointEntity() {
        return new TelemetryPointJpaEntity(
                "point-001",
                deviceEntity().getId(),
                "PT-001",
                "ضغط",
                "Pression ligne",
                "Line pressure",
                "point-type-pressure",
                "signal-type-numeric",
                "unit-bar",
                "aggregation-average",
                60,
                "SCADA.PT001.PV",
                "ACTIVE",
                CREATED_AT,
                UPDATED_AT);
    }

    public static TelemetryPointBindingJpaEntity bindingEntity() {
        return new TelemetryPointBindingJpaEntity(
                "binding-001",
                pointEntity().getId(),
                "PIPELINE",
                "pipeline-001",
                "GPL-001",
                "Pipeline GPL 001",
                "binding-role-primary",
                true,
                CREATED_AT,
                null,
                CREATED_AT,
                UPDATED_AT);
    }

    public static TelemetryIngestionBatchJpaEntity batchEntity() {
        return new TelemetryIngestionBatchJpaEntity(
                "batch-001",
                sourceEntity().getId(),
                "corr-001",
                "COMPLETED",
                10,
                8,
                1,
                1,
                0,
                CREATED_AT,
                UPDATED_AT,
                null);
    }

    public static TelemetryReadingJpaEntity readingEntity() {
        return new TelemetryReadingJpaEntity(
                "reading-001",
                pointEntity().getId(),
                BigDecimal.valueOf(42.25),
                null,
                null,
                "quality-good",
                SOURCE_TIMESTAMP,
                RECEIVED_AT,
                "RECEIVED",
                batchEntity().getId(),
                "corr-001",
                null);
    }

    public static TelemetryReadingJpaEntity laterReadingEntity() {
        return new TelemetryReadingJpaEntity(
                "reading-002",
                pointEntity().getId(),
                BigDecimal.valueOf(43.25),
                null,
                null,
                "quality-good",
                SOURCE_TIMESTAMP.plusSeconds(60),
                RECEIVED_AT.plusSeconds(60),
                "ACCEPTED",
                batchEntity().getId(),
                "corr-002",
                null);
    }

    public static TelemetryTypeCatalogJpaRepository catalogRepository() {
        return repository(TelemetryTypeCatalogJpaRepository.class);
    }

    public static TelemetryTypeTranslationJpaRepository translationRepository() {
        return repository(TelemetryTypeTranslationJpaRepository.class);
    }

    public static TelemetrySourceJpaRepository sourceRepository() {
        return repository(TelemetrySourceJpaRepository.class);
    }

    public static TelemetryDeviceJpaRepository deviceRepository() {
        return repository(TelemetryDeviceJpaRepository.class);
    }

    public static TelemetryPointJpaRepository pointRepository() {
        return repository(TelemetryPointJpaRepository.class);
    }

    public static TelemetryPointBindingJpaRepository bindingRepository() {
        return repository(TelemetryPointBindingJpaRepository.class);
    }

    public static TelemetryReadingJpaRepository readingRepository() {
        return repository(TelemetryReadingJpaRepository.class);
    }

    public static TelemetryIngestionBatchJpaRepository batchRepository() {
        return repository(TelemetryIngestionBatchJpaRepository.class);
    }

    @SuppressWarnings("unchecked")
    private static <R> R repository(Class<R> repositoryType) {
        InvocationHandler handler = new InMemoryRepositoryInvocationHandler();
        return (R) Proxy.newProxyInstance(
                repositoryType.getClassLoader(),
                new Class<?>[] { repositoryType },
                handler);
    }

    private static final class InMemoryRepositoryInvocationHandler implements InvocationHandler {

        private final Map<String, Object> store = new LinkedHashMap<>();

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            String methodName = method.getName();

            if ("toString".equals(methodName)) {
                return "InMemoryTelemetryRepositoryProxy";
            }
            if ("hashCode".equals(methodName)) {
                return System.identityHashCode(proxy);
            }
            if ("equals".equals(methodName)) {
                return proxy == args[0];
            }
            if ("save".equals(methodName)) {
                Object entity = args[0];
                store.put(idOf(entity), entity);
                return entity;
            }
            if ("findById".equals(methodName)) {
                return Optional.ofNullable(store.get(String.valueOf(args[0])));
            }
            if ("findAll".equals(methodName)) {
                Pageable pageable = args != null && args.length == 1 && args[0] instanceof Pageable ? (Pageable) args[0] : Pageable.unpaged();
                return page(new ArrayList<>(store.values()), pageable);
            }
            if ("findFirstByPointIdOrderBySourceTimestampDesc".equals(methodName)) {
                return store.values()
                        .stream()
                        .filter(entity -> matches(entity, "pointId", args[0]))
                        .max(Comparator.comparing(entity -> (Instant) valueOf(entity, "sourceTimestamp")))
                        .map(Optional::of)
                        .orElse(Optional.empty());
            }
            if ("findByPointIdAndActiveTrue".equals(methodName)) {
                return store.values()
                        .stream()
                        .filter(entity -> matches(entity, "pointId", args[0]))
                        .filter(entity -> Boolean.TRUE.equals(valueOf(entity, "active")))
                        .toList();
            }
            if (methodName.startsWith("existsBy")) {
                return filter(methodName.substring("existsBy".length()), args).stream().findFirst().isPresent();
            }
            if (methodName.startsWith("findBy")) {
                List<Object> filtered = filter(methodName.substring("findBy".length()), args);
                if (Optional.class.equals(method.getReturnType())) {
                    return filtered.stream().findFirst();
                }
                if (List.class.equals(method.getReturnType())) {
                    return filtered;
                }
                Pageable pageable = pageableArgument(args);
                return page(filtered, pageable);
            }

            throw new UnsupportedOperationException("Unsupported repository method in telemetry persistence test proxy: " + methodName);
        }

        private List<Object> filter(String conditionExpression, Object[] args) {
            if ("CodeContainingIgnoreCaseOrNameFrContainingIgnoreCase".equals(conditionExpression)) {
                String codeText = String.valueOf(args[0]).toUpperCase();
                String nameText = String.valueOf(args[1]).toUpperCase();

                return store.values()
                        .stream()
                        .filter(entity -> containsIgnoreCase(valueOf(entity, "code"), codeText)
                                || containsIgnoreCase(valueOf(entity, "nameFr"), nameText))
                        .toList();
            }

            List<String> conditions = List.of(conditionExpression.split("And"));
            return store.values()
                    .stream()
                    .filter(entity -> matchesAll(entity, conditions, args))
                    .toList();
        }

        private boolean matchesAll(Object entity, List<String> conditions, Object[] args) {
            int argumentIndex = 0;

            for (String condition : conditions) {
                if ("ActiveTrue".equals(condition)) {
                    if (!Boolean.TRUE.equals(valueOf(entity, "active"))) {
                        return false;
                    }
                    continue;
                }

                if (condition.endsWith("Between")) {
                    String field = fieldName(condition.substring(0, condition.length() - "Between".length()));
                    Comparable<Object> left = comparable(valueOf(entity, field));
                    Object start = args[argumentIndex++];
                    Object end = args[argumentIndex++];

                    if (left.compareTo(start) < 0 || left.compareTo(end) > 0) {
                        return false;
                    }
                    continue;
                }

                String field = fieldName(condition);
                Object expected = args[argumentIndex++];
                if (expected instanceof Pageable) {
                    break;
                }
                if (!matches(entity, field, expected)) {
                    return false;
                }
            }

            return true;
        }

        @SuppressWarnings("unchecked")
        private Comparable<Object> comparable(Object value) {
            return (Comparable<Object>) value;
        }

        private static boolean containsIgnoreCase(Object value, String expectedUppercase) {
            return value != null && String.valueOf(value).toUpperCase().contains(expectedUppercase);
        }

        private static boolean matches(Object entity, String field, Object expected) {
            Object actual = valueOf(entity, field);
            return actual == null ? expected == null : actual.equals(expected);
        }

        private static Pageable pageableArgument(Object[] args) {
            if (args == null) {
                return Pageable.unpaged();
            }

            for (Object arg : args) {
                if (arg instanceof Pageable pageable) {
                    return pageable;
                }
            }

            return Pageable.unpaged();
        }

        private static Page<Object> page(List<Object> items, Pageable pageable) {
            return new PageImpl<>(items, pageable, items.size());
        }

        private static String fieldName(String methodSuffix) {
            return methodSuffix.substring(0, 1).toLowerCase() + methodSuffix.substring(1);
        }

        private static Object valueOf(Object entity, String field) {
            String getterName = "get" + field.substring(0, 1).toUpperCase() + field.substring(1);
            try {
                return entity.getClass().getMethod(getterName).invoke(entity);
            } catch (ReflectiveOperationException exception) {
                throw new IllegalStateException("Missing getter " + getterName + " on " + entity.getClass().getSimpleName(), exception);
            }
        }

        private static String idOf(Object entity) {
            return String.valueOf(valueOf(entity, "id"));
        }
    }
}
