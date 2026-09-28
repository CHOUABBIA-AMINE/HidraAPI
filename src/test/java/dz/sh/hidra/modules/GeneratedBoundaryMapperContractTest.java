/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : GeneratedBoundaryMapperContractTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Test
 * @Layer       : API Test
 * @Module      : modules
 * @Package     : dz.sh.hidra.modules
 *
 * @Description : Verifies every remaining HRA-07x generated boundary mapper preserves exact record components.
 *
 */
package dz.sh.hidra.modules;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.RecordComponent;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class GeneratedBoundaryMapperContractTest {

    private static final Map<String, Integer> MAPPER_METHOD_COUNTS = Map.ofEntries(
            Map.entry("dz.sh.hidra.modules.assets.api.rest.mapper.AssetsGeneratedRestMapper", 6),
            Map.entry("dz.sh.hidra.modules.audit.api.rest.mapper.AuditGeneratedRestMapper", 6),
            Map.entry("dz.sh.hidra.modules.configuration.api.rest.mapper.ConfigurationGeneratedRestMapper", 6),
            Map.entry("dz.sh.hidra.modules.custody.api.rest.mapper.CustodyGeneratedRestMapper", 6),
            Map.entry("dz.sh.hidra.modules.documents.api.rest.mapper.DocumentsGeneratedRestMapper", 6),
            Map.entry("dz.sh.hidra.modules.hse.api.rest.mapper.HseGeneratedRestMapper", 5),
            Map.entry("dz.sh.hidra.modules.identity.api.rest.mapper.IdentityGeneratedRestMapper", 2),
            Map.entry("dz.sh.hidra.modules.incident.api.rest.mapper.IncidentGeneratedRestMapper", 4),
            Map.entry("dz.sh.hidra.modules.integration.api.rest.mapper.IntegrationGeneratedRestMapper", 6),
            Map.entry("dz.sh.hidra.modules.integrity.api.rest.mapper.IntegrityGeneratedRestMapper", 6),
            Map.entry("dz.sh.hidra.modules.leakdetection.api.rest.mapper.LeakdetectionGeneratedRestMapper", 5),
            Map.entry("dz.sh.hidra.modules.monitoring.api.rest.mapper.MonitoringGeneratedRestMapper", 4),
            Map.entry("dz.sh.hidra.modules.notification.api.rest.mapper.NotificationGeneratedRestMapper", 6),
            Map.entry("dz.sh.hidra.modules.organization.api.rest.mapper.OrganizationGeneratedRestMapper", 4),
            Map.entry("dz.sh.hidra.modules.party.api.rest.mapper.PartyGeneratedRestMapper", 3),
            Map.entry("dz.sh.hidra.modules.planning.api.rest.mapper.PlanningGeneratedRestMapper", 4),
            Map.entry("dz.sh.hidra.modules.reporting.api.rest.mapper.ReportingGeneratedRestMapper", 8),
            Map.entry("dz.sh.hidra.modules.risk.api.rest.mapper.RiskGeneratedRestMapper", 5),
            Map.entry("dz.sh.hidra.modules.simulation.api.rest.mapper.SimulationGeneratedRestMapper", 8),
            Map.entry("dz.sh.hidra.modules.telemetry.api.rest.mapper.TelemetryGeneratedRestMapper", 4),
            Map.entry("dz.sh.hidra.modules.topology.api.rest.mapper.TopologyGeneratedRestMapper", 4),
            Map.entry("dz.sh.hidra.modules.workflow.api.rest.mapper.WorkflowGeneratedRestMapper", 6)
    );

    @Test
    void generatedMappersPreserveAllRemainingExactBoundaryContracts() throws ReflectiveOperationException {
        int totalMappings = 0;

        for (Map.Entry<String, Integer> entry : MAPPER_METHOD_COUNTS.entrySet()) {
            Class<?> mapperType = Class.forName(entry.getKey());
            Object mapper = mapperType.getField("INSTANCE").get(null);
            assertNotNull(mapper, mapperType.getName() + " generated instance must exist.");

            Method[] mappingMethods = Arrays.stream(mapperType.getDeclaredMethods())
                    .filter(method -> Modifier.isPublic(method.getModifiers()))
                    .filter(method -> !Modifier.isStatic(method.getModifiers()))
                    .filter(method -> !method.isSynthetic())
                    .toArray(Method[]::new);

            assertEquals(entry.getValue(), mappingMethods.length, mapperType.getName());
            totalMappings += mappingMethods.length;

            for (Method method : mappingMethods) {
                assertEquals(1, method.getParameterCount(), method.toString());
                Object source = instantiateRecord(method.getParameterTypes()[0]);
                Object target = method.invoke(mapper, source);
                assertNotNull(target, method + " must return a mapped record.");
                assertSameNamedRecordComponents(source, target, method);
            }
        }

        assertEquals(114, totalMappings, "All remaining HRA-070 exact pairs must be generated.");
    }

    private static Object instantiateRecord(Class<?> type) throws ReflectiveOperationException {
        assertTrue(type.isRecord(), type.getName() + " must remain an immutable record contract.");
        RecordComponent[] components = type.getRecordComponents();
        Class<?>[] parameterTypes = Arrays.stream(components)
                .map(RecordComponent::getType)
                .toArray(Class<?>[]::new);
        Object[] values = Arrays.stream(components)
                .map(component -> syntheticValue(component.getType(), component.getName()))
                .toArray();

        Constructor<?> constructor = type.getDeclaredConstructor(parameterTypes);
        constructor.setAccessible(true);
        return constructor.newInstance(values);
    }

    private static void assertSameNamedRecordComponents(
            Object source,
            Object target,
            Method mappingMethod
    ) throws ReflectiveOperationException {
        assertTrue(target.getClass().isRecord(), target.getClass().getName() + " must remain an immutable record contract.");

        Map<String, Object> sourceValues = new HashMap<>();
        for (RecordComponent component : source.getClass().getRecordComponents()) {
            sourceValues.put(component.getName(), component.getAccessor().invoke(source));
        }

        RecordComponent[] targetComponents = target.getClass().getRecordComponents();
        assertEquals(sourceValues.size(), targetComponents.length, mappingMethod.toString());

        for (RecordComponent component : targetComponents) {
            assertTrue(
                    sourceValues.containsKey(component.getName()),
                    mappingMethod + " missing source component " + component.getName()
            );
            assertEquals(
                    sourceValues.get(component.getName()),
                    component.getAccessor().invoke(target),
                    mappingMethod + " changed component " + component.getName()
            );
        }
    }

    private static Object syntheticValue(Class<?> type, String componentName) {
        if (type == String.class) {
            return componentName + "-value";
        }
        if (type == int.class || type == Integer.class) {
            return 7;
        }
        if (type == long.class || type == Long.class) {
            return 11L;
        }
        if (type == boolean.class || type == Boolean.class) {
            return true;
        }
        if (type == double.class || type == Double.class) {
            return 13.5d;
        }
        if (type == float.class || type == Float.class) {
            return 17.5f;
        }
        if (type == short.class || type == Short.class) {
            return (short) 19;
        }
        if (type == byte.class || type == Byte.class) {
            return (byte) 23;
        }
        if (type == char.class || type == Character.class) {
            return 'H';
        }
        if (type == BigDecimal.class) {
            return new BigDecimal("29.75");
        }
        if (type == Instant.class) {
            return Instant.parse("2026-09-28T12:34:56Z");
        }
        if (type == LocalDate.class) {
            return LocalDate.of(2026, 9, 28);
        }
        if (type == LocalDateTime.class) {
            return LocalDateTime.of(2026, 9, 28, 12, 34, 56);
        }
        if (type == OffsetDateTime.class) {
            return OffsetDateTime.parse("2026-09-28T12:34:56+01:00");
        }
        if (type == UUID.class) {
            return UUID.fromString("12345678-1234-5678-1234-567812345678");
        }
        if (type.isEnum()) {
            Object[] constants = type.getEnumConstants();
            assertTrue(constants.length > 0, type.getName() + " must define at least one value.");
            return constants[0];
        }

        return fail("Unsupported exact-boundary test component type: " + type.getName());
    }
}
