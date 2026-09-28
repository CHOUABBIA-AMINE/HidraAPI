/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationModelValueIntegrityTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Class
 * @Layer       : Organization Test
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization
 *
 * @Description : Final guardrail for canonical Organization domain model/value integrity.
 *
 */
package dz.sh.hidra.modules.organization;

import static org.assertj.core.api.Assertions.assertThat;

import dz.sh.hidra.modules.organization.domain.model.Employee;
import dz.sh.hidra.modules.organization.domain.model.EmployeeAddress;
import dz.sh.hidra.modules.organization.domain.model.EmployeeAssignment;
import dz.sh.hidra.modules.organization.domain.model.OperationalScope;
import dz.sh.hidra.modules.organization.domain.model.OrganizationContactPoint;
import dz.sh.hidra.modules.organization.domain.model.OrganizationUnit;
import dz.sh.hidra.modules.organization.domain.model.ReportingLine;
import dz.sh.hidra.modules.organization.domain.model.ResponsibilityAssignment;
import dz.sh.hidra.modules.organization.domain.value.ContactPointTargetReference;
import dz.sh.hidra.modules.organization.domain.value.OrganizationId;
import dz.sh.hidra.modules.organization.domain.value.ReportingSubjectReference;
import dz.sh.hidra.modules.organization.domain.value.ResponsibilityAssigneeType;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.RecordComponent;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

/**
 * Final ORG-055 model/value integrity guardrail.
 *
 * <p>This test intentionally protects only the canonical Organization domain-model/value
 * contract. Application, persistence, API and migration cutover remain separate roadmap
 * work.</p>
 */
class OrganizationModelValueIntegrityTest {

    private static final Path DOMAIN_MODEL_ROOT =
            Path.of("src/main/java/dz/sh/hidra/modules/organization/domain/model");

    @Test
    void employeePersonalBirthShapeMustRemainCanonical() {
        assertRecordComponentType(Employee.class, "dateOfBirth", LocalDate.class);
        assertRecordComponentType(Employee.class, "birthLocalityId", String.class);
        assertRecordComponentType(Employee.class, "birthPlaceAr", String.class);
        assertRecordComponentType(Employee.class, "birthPlaceFr", String.class);
        assertRecordComponentType(Employee.class, "birthPlaceEn", String.class);

        Set<String> components = componentNames(Employee.class);
        assertThat(components)
                .doesNotContain(
                        "address",
                        "addressLine",
                        "streetLine1",
                        "streetLine2",
                        "postalCode",
                        "postalCodeSnapshot",
                        "localityId"
                );
    }

    @Test
    void employeeContactAndDisplayDuplicatesMustRemainCompatibilityOnly() throws Exception {
        assertDeprecatedForRemoval(Employee.class.getMethod("emailAddress"));
        assertDeprecatedForRemoval(Employee.class.getMethod("mobileNumber"));
        assertDeprecatedForRemoval(Employee.class.getMethod("displayNameAr"));
        assertDeprecatedForRemoval(Employee.class.getMethod("displayNameLt"));

        assertThat(Employee.class.getMethod("arabicDisplayName").getReturnType())
                .isEqualTo(String.class);
        assertThat(Employee.class.getMethod("latinDisplayName").getReturnType())
                .isEqualTo(String.class);

        assertRecordComponentType(
                OrganizationContactPoint.class,
                "target",
                ContactPointTargetReference.class
        );
        assertThat(componentNames(EmployeeAddress.class))
                .contains("employeeId", "localityId", "streetLine1", "streetLine2")
                .doesNotContain("emailAddress", "mobileNumber", "phoneNumber");
    }

    @Test
    void polymorphicReferencesAndScopeIdentityMustRemainTyped() {
        assertRecordComponentType(
                ReportingLine.class,
                "source",
                ReportingSubjectReference.class
        );
        assertRecordComponentType(
                ReportingLine.class,
                "target",
                ReportingSubjectReference.class
        );
        assertRecordComponentType(
                ResponsibilityAssignment.class,
                "assigneeType",
                ResponsibilityAssigneeType.class
        );
        assertRecordComponentType(
                ResponsibilityAssignment.class,
                "scopeId",
                Long.class
        );
        assertRecordComponentType(OperationalScope.class, "id", Long.class);

        assertThat(OrganizationId.class.isRecord()).isTrue();
        assertRecordComponentType(OrganizationId.class, "value", String.class);
    }

    @Test
    void retiredOperationalScopeStateMustNotReturnToCanonicalMembershipRecords() {
        assertThat(componentNames(OrganizationUnit.class))
                .doesNotContain(
                        "operationalScopeType",
                        "operationalScopeId",
                        "operationalScopeCode",
                        "operationalScopeName"
                );
        assertThat(componentNames(EmployeeAssignment.class))
                .doesNotContain(
                        "operationalScopeType",
                        "operationalScopeId",
                        "operationalScopeCode",
                        "operationalScopeName"
                );
        assertThat(componentNames(ResponsibilityAssignment.class))
                .contains("scopeId")
                .doesNotContain(
                        "operationalScopeType",
                        "operationalScopeId",
                        "operationalScopeCode",
                        "operationalScopeName"
                );
    }

    @Test
    void deprecatedOperationalScopeCompatibilityMethodsMustRemainMarkedForRemoval()
            throws Exception {
        for (Class<?> type : List.of(
                OrganizationUnit.class,
                EmployeeAssignment.class,
                ResponsibilityAssignment.class
        )) {
            for (String methodName : List.of(
                    "operationalScopeType",
                    "operationalScopeId",
                    "operationalScopeCode",
                    "operationalScopeName"
            )) {
                assertDeprecatedForRemoval(type.getMethod(methodName));
            }
        }
    }

    @Test
    void effectiveDatedModelsMustRetainHalfOpenIntervalGuards() throws IOException {
        for (String model : List.of(
                "OrganizationUnit",
                "EmployeeAddress",
                "EmployeeAssignment",
                "ReportingLine",
                "ResponsibilityAssignment",
                "ShiftAssignment",
                "OrganizationDelegation"
        )) {
            String source = Files.readString(DOMAIN_MODEL_ROOT.resolve(model + ".java"));
            assertThat(source)
                    .as("%s must require validFrom", model)
                    .contains("validFrom == null");
            assertThat(source)
                    .as("%s must enforce validTo strictly after validFrom", model)
                    .contains("validTo != null", "!validTo.isAfter(validFrom)");
        }
    }

    @Test
    void retiredTranslationModelMustNotReturn() throws IOException {
        Path retiredTranslation =
                DOMAIN_MODEL_ROOT.resolve("OrganizationUnitTypeTranslation.java");

        assertThat(retiredTranslation).doesNotExist();

        try (var files = Files.list(DOMAIN_MODEL_ROOT)) {
            List<String> translationModels = files
                    .filter(Files::isRegularFile)
                    .map(path -> path.getFileName().toString())
                    .filter(name -> name.toLowerCase().contains("translation"))
                    .toList();

            assertThat(translationModels)
                    .as("Organization multilingual content must remain embedded on owning models")
                    .isEmpty();
        }
    }

    private static Set<String> componentNames(Class<?> type) {
        assertThat(type.isRecord()).isTrue();
        return Arrays.stream(type.getRecordComponents())
                .map(RecordComponent::getName)
                .collect(Collectors.toSet());
    }

    private static void assertRecordComponentType(
            Class<?> type,
            String componentName,
            Class<?> expectedType
    ) {
        Class<?> actualType = Arrays.stream(type.getRecordComponents())
                .filter(component -> component.getName().equals(componentName))
                .map(RecordComponent::getType)
                .findFirst()
                .orElseThrow(() -> new AssertionError(
                        type.getName() + " is missing record component " + componentName
                ));

        assertThat(actualType)
                .as("%s.%s type", type.getSimpleName(), componentName)
                .isEqualTo(expectedType);
    }

    private static void assertDeprecatedForRemoval(Method method) {
        Deprecated deprecated = method.getAnnotation(Deprecated.class);
        assertThat(deprecated)
                .as("%s.%s must remain deprecated", method.getDeclaringClass().getSimpleName(), method.getName())
                .isNotNull();
        assertThat(deprecated.forRemoval())
                .as("%s.%s must remain marked for removal", method.getDeclaringClass().getSimpleName(), method.getName())
                .isTrue();
    }
}
