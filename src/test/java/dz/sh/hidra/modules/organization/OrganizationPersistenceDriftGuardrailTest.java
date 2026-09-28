/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationPersistenceDriftGuardrailTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Class
 * @Layer       : Organization Test
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization
 *
 * @Description : Detects silent Employee domain/JPA/mapper field drift before persistence.
 *
 */
package dz.sh.hidra.modules.organization;

import static org.assertj.core.api.Assertions.assertThat;

import dz.sh.hidra.modules.organization.domain.model.Employee;
import dz.sh.hidra.modules.organization.infrastructure.persistence.entity.EmployeeJpaEntity;
import jakarta.persistence.Transient;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.RecordComponent;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

/**
 * HRA-003 persistence-drift guardrail for the Organization Employee vertical slice.
 *
 * <p>The canonical comparison surface is the Employee record components. Derived accessors
 * such as {@code arabicDisplayName()} and {@code latinDisplayName()} are therefore excluded
 * by construction because they are behavior, not persisted record state.</p>
 *
 * <p>Historical display/contact components remain part of the comparison because they are
 * still persisted compatibility state until the later HRA-020/HRA-023 cutover. They must not
 * be silently dropped before that migration is explicitly completed.</p>
 *
 * <p>The five birth fields below are the exact mapper drift confirmed by the forensic audit and
 * the user-supplied source reference. HRA-010 makes them explicit transient JPA-model state
 * because the live Flyway schema does not yet contain their columns; HRA-011 must map them and
 * HRA-013 must replace @Transient with real column mappings after the schema migration.</p>
 */
class OrganizationPersistenceDriftGuardrailTest {

    private static final Path ORGANIZATION_MAPPER_SOURCE = Path.of(
            "src/main/java/dz/sh/hidra/modules/organization/infrastructure/persistence/mapper/"
                    + "OrganizationPersistenceMapper.java"
    );

    private static final Set<String> TRANSITIONAL_EMPLOYEE_MAPPER_DRIFT = Set.of(
            "dateOfBirth",
            "birthLocalityId",
            "birthPlaceAr",
            "birthPlaceFr",
            "birthPlaceEn"
    );

    @Test
    void employeeJpaShapeMustNotGainUnrecordedDomainDrift() {
        Set<String> domainComponents = recordComponents(Employee.class);
        Set<String> entityFields = instanceFields(EmployeeJpaEntity.class);

        Set<String> missingEntityFields = difference(domainComponents, entityFields);

        assertThat(missingEntityFields)
                .as("HRA-010 requires EmployeeJpaEntity to represent every Employee component")
                .isEmpty();
    }

    @Test
    void employeeMapperMustNotSilentlyDropAdditionalDomainComponents() throws IOException {
        String mapperSource = Files.readString(ORGANIZATION_MAPPER_SOURCE);
        String toEntity = methodBody(
                mapperSource,
                "public static EmployeeJpaEntity toEntity(Employee model)"
        );
        String toDomain = methodBody(
                mapperSource,
                "public static Employee toDomain(EmployeeJpaEntity entity)"
        );

        Set<String> domainComponents = recordComponents(Employee.class);

        assertThat(missingAccessorCalls(domainComponents, toEntity, "model"))
                .as("Employee -> JPA mapper drift must remain limited to the HRA-011 birth gap")
                .isEqualTo(TRANSITIONAL_EMPLOYEE_MAPPER_DRIFT);

        assertThat(missingAccessorCalls(domainComponents, toDomain, "entity"))
                .as("JPA -> Employee mapper drift must remain limited to the HRA-011 birth gap")
                .isEqualTo(TRANSITIONAL_EMPLOYEE_MAPPER_DRIFT);
    }

    @Test
    void transitionalBirthDriftMustRemainNarrowAndCurrent() throws IOException {
        assertThat(TRANSITIONAL_EMPLOYEE_MAPPER_DRIFT)
                .containsExactlyInAnyOrder(
                        "dateOfBirth",
                        "birthLocalityId",
                        "birthPlaceAr",
                        "birthPlaceFr",
                        "birthPlaceEn"
                );

        assertThat(difference(recordComponents(Employee.class), instanceFields(EmployeeJpaEntity.class)))
                .as("HRA-010 removes domain/entity shape drift before mapper repair")
                .isEmpty();

        for (String fieldName : TRANSITIONAL_EMPLOYEE_MAPPER_DRIFT) {
            try {
                Field field = EmployeeJpaEntity.class.getDeclaredField(fieldName);
                assertThat(field.isAnnotationPresent(Transient.class))
                        .as("%s must remain @Transient until HRA-013 adds its database column", fieldName)
                        .isTrue();
            } catch (NoSuchFieldException exception) {
                throw new AssertionError("EmployeeJpaEntity is missing " + fieldName, exception);
            }
        }

        String mapperSource = Files.readString(ORGANIZATION_MAPPER_SOURCE);
        assertThat(missingAccessorCalls(
                recordComponents(Employee.class),
                methodBody(mapperSource, "public static EmployeeJpaEntity toEntity(Employee model)"),
                "model"
        )).containsExactlyInAnyOrderElementsOf(TRANSITIONAL_EMPLOYEE_MAPPER_DRIFT);
    }

    @Test
    void detectorRejectsRepresentativeUnmappedCanonicalComponent() {
        Set<String> canonical = new LinkedHashSet<>(Set.of("id", "employeeNumber", "dateOfBirth"));
        Set<String> persisted = new LinkedHashSet<>(Set.of("id", "employeeNumber"));

        assertThat(difference(canonical, persisted))
                .containsExactly("dateOfBirth");
    }

    @Test
    void compatibilityComponentsRemainMappedUntilTheirApprovedCutover() throws IOException {
        Set<String> compatibilityComponents = Set.of(
                "displayNameAr",
                "displayNameLt",
                "emailAddress",
                "mobileNumber"
        );

        assertThat(instanceFields(EmployeeJpaEntity.class))
                .containsAll(compatibilityComponents);

        String mapperSource = Files.readString(ORGANIZATION_MAPPER_SOURCE);
        String toEntity = methodBody(
                mapperSource,
                "public static EmployeeJpaEntity toEntity(Employee model)"
        );
        String toDomain = methodBody(
                mapperSource,
                "public static Employee toDomain(EmployeeJpaEntity entity)"
        );

        assertThat(missingAccessorCalls(compatibilityComponents, toEntity, "model")).isEmpty();
        assertThat(missingAccessorCalls(compatibilityComponents, toDomain, "entity")).isEmpty();
    }

    private static Set<String> recordComponents(Class<?> recordType) {
        assertThat(recordType.isRecord()).isTrue();
        LinkedHashSet<String> components = new LinkedHashSet<>();
        Arrays.stream(recordType.getRecordComponents())
                .map(RecordComponent::getName)
                .forEach(components::add);
        return components;
    }

    private static Set<String> instanceFields(Class<?> type) {
        LinkedHashSet<String> fields = new LinkedHashSet<>();
        Arrays.stream(type.getDeclaredFields())
                .filter(field -> !Modifier.isStatic(field.getModifiers()))
                .map(Field::getName)
                .forEach(fields::add);
        return fields;
    }

    private static Set<String> difference(Set<String> expected, Set<String> actual) {
        LinkedHashSet<String> difference = new LinkedHashSet<>(expected);
        difference.removeAll(actual);
        return difference;
    }

    private static Set<String> missingAccessorCalls(
            Set<String> componentNames,
            String source,
            String receiver
    ) {
        LinkedHashSet<String> missing = new LinkedHashSet<>();
        for (String componentName : componentNames) {
            String accessor = receiver + "." + componentName + "()";
            if (!source.contains(accessor)) {
                missing.add(componentName);
            }
        }
        return missing;
    }

    private static String methodBody(String source, String signature) {
        int signatureStart = source.indexOf(signature);
        if (signatureStart < 0) {
            throw new AssertionError("Missing mapper method: " + signature);
        }

        int bodyStart = source.indexOf('{', signatureStart);
        if (bodyStart < 0) {
            throw new AssertionError("Missing mapper method body: " + signature);
        }

        int depth = 0;
        for (int index = bodyStart; index < source.length(); index++) {
            char current = source.charAt(index);
            if (current == '{') {
                depth++;
            } else if (current == '}') {
                depth--;
                if (depth == 0) {
                    return source.substring(bodyStart + 1, index);
                }
            }
        }

        throw new AssertionError("Unterminated mapper method body: " + signature);
    }
}
