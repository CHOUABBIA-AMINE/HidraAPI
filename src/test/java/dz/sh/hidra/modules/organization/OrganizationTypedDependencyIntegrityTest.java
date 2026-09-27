/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationTypedDependencyIntegrityTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-27
 *
 * @Type        : Class
 * @Layer       : Organization Test
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization
 *
 * @Description : Enforces final Organization typed-reference, multilingual-retirement, and database-ownership guardrails.
 *
 */
package dz.sh.hidra.modules.organization;

import static org.assertj.core.api.Assertions.assertThat;

import dz.sh.hidra.modules.organization.domain.model.OrganizationContactPoint;
import dz.sh.hidra.modules.organization.domain.model.ReportingLine;
import dz.sh.hidra.modules.organization.domain.model.ResponsibilityAssignment;
import dz.sh.hidra.modules.organization.domain.value.ContactPointTargetReference;
import dz.sh.hidra.modules.organization.domain.value.ContactPointTargetType;
import dz.sh.hidra.modules.organization.domain.value.ReportingSubjectReference;
import dz.sh.hidra.modules.organization.domain.value.ReportingSubjectType;
import dz.sh.hidra.modules.organization.domain.value.ResponsibilityAssigneeType;
import dz.sh.hidra.modules.organization.infrastructure.persistence.entity.OrganizationContactPointJpaEntity;
import dz.sh.hidra.modules.organization.infrastructure.persistence.entity.OrganizationUnitTypeJpaEntity;
import dz.sh.hidra.modules.organization.infrastructure.persistence.entity.PositionJpaEntity;
import dz.sh.hidra.modules.organization.infrastructure.persistence.entity.ReportingLineJpaEntity;
import dz.sh.hidra.modules.organization.infrastructure.persistence.entity.ResponsibilityAssignmentJpaEntity;
import dz.sh.hidra.modules.organization.infrastructure.persistence.entity.ShiftJpaEntity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.RecordComponent;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;

/**
 * Final ORG-048 architecture/integrity guardrails.
 *
 * <p>Canonical Organization polymorphic state must remain typed. Narrow textual
 * compatibility bridges may survive temporarily, but they must remain explicitly
 * deprecated for removal and may not become canonical record/JPA state again.</p>
 *
 * <p>Database integrity remains owned inside the Organization bounded context:
 * same-module references retain the ORG-046 FK/check contract, while Organization
 * migrations are forbidden from adding foreign keys to another module's tables.</p>
 *
 * <p>Retired multilingual translation/compatibility structures from ORG-039 and
 * ORG-047 must not reappear in Java mappings or later migrations.</p>
 */
class OrganizationTypedDependencyIntegrityTest {

    private static final Path MIGRATION_ROOT = Path.of("src/main/resources/db/migration");
    private static final String INTERNAL_INTEGRITY_MIGRATION =
            "V20260927_004__enforce_organization_internal_reference_integrity.sql";
    private static final String MULTILINGUAL_RETIREMENT_MIGRATION =
            "V20260927_005__retire_organization_multilingual_compatibility_columns.sql";

    @Test
    void canonicalPolymorphicDomainStateMustRemainTyped() {
        assertRecordComponentType(
                OrganizationContactPoint.class,
                "target",
                ContactPointTargetReference.class
        );
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
    }

    @Test
    void canonicalJpaDiscriminatorsMustRemainEnumsStoredAsStrings() {
        assertEnumStringField(
                OrganizationContactPointJpaEntity.class,
                "targetType",
                ContactPointTargetType.class
        );
        assertEnumStringField(
                ReportingLineJpaEntity.class,
                "sourceType",
                ReportingSubjectType.class
        );
        assertEnumStringField(
                ReportingLineJpaEntity.class,
                "targetType",
                ReportingSubjectType.class
        );
        assertEnumStringField(
                ResponsibilityAssignmentJpaEntity.class,
                "assigneeType",
                ResponsibilityAssigneeType.class
        );
    }

    @Test
    void textualCompatibilityBridgesMustRemainDeprecatedForRemoval() {
        assertWideStringConstructorsDeprecated(OrganizationContactPoint.class, 5);
        assertWideStringConstructorsDeprecated(ReportingLine.class, 5);
        assertWideStringConstructorsDeprecated(ResponsibilityAssignment.class, 4);
        assertWideStringConstructorsDeprecated(OrganizationContactPointJpaEntity.class, 5);
        assertWideStringConstructorsDeprecated(ReportingLineJpaEntity.class, 5);
        assertWideStringConstructorsDeprecated(ResponsibilityAssignmentJpaEntity.class, 4);

        assertDeprecatedForRemovalIfPresent(OrganizationContactPoint.class, "targetType");
        assertDeprecatedForRemovalIfPresent(ReportingLine.class, "sourceType");
        assertDeprecatedForRemovalIfPresent(ReportingLine.class, "targetType");
        assertDeprecatedForRemovalIfPresent(ResponsibilityAssignment.class, "operationalScopeType");
        assertDeprecatedForRemovalIfPresent(ResponsibilityAssignment.class, "operationalScopeId");
        assertDeprecatedForRemovalIfPresent(ResponsibilityAssignment.class, "operationalScopeCode");
        assertDeprecatedForRemovalIfPresent(ResponsibilityAssignment.class, "operationalScopeName");
        assertDeprecatedForRemovalIfPresent(OrganizationContactPointJpaEntity.class, "targetType");
        assertDeprecatedForRemovalIfPresent(ReportingLineJpaEntity.class, "sourceType");
        assertDeprecatedForRemovalIfPresent(ReportingLineJpaEntity.class, "targetType");
        assertDeprecatedForRemovalIfPresent(
                ResponsibilityAssignmentJpaEntity.class,
                "operationalScopeType"
        );
        assertDeprecatedForRemovalIfPresent(
                ResponsibilityAssignmentJpaEntity.class,
                "operationalScopeId"
        );
        assertDeprecatedForRemovalIfPresent(
                ResponsibilityAssignmentJpaEntity.class,
                "operationalScopeCode"
        );
        assertDeprecatedForRemovalIfPresent(
                ResponsibilityAssignmentJpaEntity.class,
                "operationalScopeName"
        );
    }

    @Test
    void org046InternalReferenceIntegrityContractMustRemainPresent() throws IOException {
        String sql = readMigration(INTERNAL_INTEGRITY_MIGRATION).toLowerCase(Locale.ROOT);

        assertThat(sql).contains(
                "fk_org_district_state",
                "fk_org_locality_district",
                "fk_org_employee_address_employee",
                "fk_org_employee_address_locality",
                "fk_org_unit_type",
                "fk_org_unit_parent",
                "fk_org_employee_assignment_employee",
                "fk_org_employee_assignment_unit",
                "fk_org_employee_assignment_position",
                "fk_org_shift_assignment_employee",
                "fk_org_shift_assignment_shift",
                "fk_org_shift_assignment_unit",
                "fk_org_delegation_delegator",
                "fk_org_delegation_delegate",
                "fk_org_delegation_responsibility",
                "fk_org_hierarchy_snapshot_employee",
                "ck_org_contact_target_type",
                "ck_org_reporting_source_type",
                "ck_org_reporting_target_type",
                "ck_org_responsibility_assignee_type"
        );
        assertThat(sql)
                .as("ORG-046 must remain fail-closed before constraint installation")
                .contains("org-046 preflight failed");
    }

    @Test
    void retiredMultilingualStructuresMustRemainRetired() throws IOException {
        assertNoDeclaredField(OrganizationUnitTypeJpaEntity.class, "legacyDescription");
        assertNoDeclaredField(PositionJpaEntity.class, "legacyDescription");
        assertNoDeclaredField(ShiftJpaEntity.class, "legacyName");

        String retirement = readMigration(MULTILINGUAL_RETIREMENT_MIGRATION)
                .toLowerCase(Locale.ROOT);
        assertThat(retirement).contains(
                "alter table hidra_org_unit_type drop column description",
                "alter table hidra_org_position drop column description",
                "alter table hidra_org_shift drop column name"
        );

        List<Path> reintroductions = new ArrayList<>();
        try (var files = Files.list(MIGRATION_ROOT)) {
            for (Path path : files.filter(Files::isRegularFile).sorted().toList()) {
                String filename = path.getFileName().toString();
                if (!filename.startsWith("V")
                        || filename.compareTo(MULTILINGUAL_RETIREMENT_MIGRATION) <= 0) {
                    continue;
                }
                String sql = Files.readString(path).toLowerCase(Locale.ROOT);
                if (reintroducesRetiredMultilingualStructure(sql)) {
                    reintroductions.add(path);
                }
            }
        }

        assertThat(reintroductions)
                .as("Later migrations must not restore retired Organization multilingual structures")
                .isEmpty();
    }

    @Test
    void organizationMigrationsMustNotOwnOtherModulesThroughForeignKeys() throws IOException {
        Pattern externalReference = Pattern.compile(
                "(?is)references\\s+(hidra_(?!org_)[a-z0-9_]+)"
        );
        List<String> violations = new ArrayList<>();

        try (var files = Files.list(MIGRATION_ROOT)) {
            for (Path path : files.filter(Files::isRegularFile).sorted().toList()) {
                String sql = Files.readString(path);
                if (!sql.toLowerCase(Locale.ROOT).contains("module: organization")) {
                    continue;
                }

                Matcher matcher = externalReference.matcher(sql);
                while (matcher.find()) {
                    violations.add(path.getFileName() + " -> " + matcher.group(1));
                }
            }
        }

        assertThat(violations)
                .as("Organization migrations must not create database ownership of external modules")
                .isEmpty();
    }

    private static void assertRecordComponentType(
            Class<?> recordType,
            String componentName,
            Class<?> expectedType
    ) {
        assertThat(recordType.isRecord()).isTrue();

        Class<?> actualType = Arrays.stream(recordType.getRecordComponents())
                .filter(component -> component.getName().equals(componentName))
                .map(RecordComponent::getType)
                .findFirst()
                .orElseThrow(() -> new AssertionError(
                        recordType.getName() + " is missing record component " + componentName
                ));

        assertThat(actualType)
                .as("%s.%s must remain typed", recordType.getSimpleName(), componentName)
                .isEqualTo(expectedType);
    }

    private static void assertEnumStringField(
            Class<?> entityType,
            String fieldName,
            Class<?> expectedType
    ) {
        Field field = declaredField(entityType, fieldName);
        assertThat(field.getType())
                .as("%s.%s must remain an enum discriminator", entityType.getSimpleName(), fieldName)
                .isEqualTo(expectedType);

        Enumerated enumerated = field.getAnnotation(Enumerated.class);
        assertThat(enumerated)
                .as("%s.%s must declare @Enumerated", entityType.getSimpleName(), fieldName)
                .isNotNull();
        assertThat(enumerated.value())
                .as("%s.%s must persist stable enum names", entityType.getSimpleName(), fieldName)
                .isEqualTo(EnumType.STRING);
    }

    private static void assertWideStringConstructorsDeprecated(
            Class<?> type,
            int minimumStringParameterCount
    ) {
        List<Constructor<?>> bridges = Arrays.stream(type.getDeclaredConstructors())
                .filter(constructor -> countStringParameters(constructor) >= minimumStringParameterCount)
                .toList();

        assertThat(bridges)
                .as("%s must retain expected compatibility constructor coverage", type.getName())
                .isNotEmpty();

        bridges.forEach(constructor -> assertDeprecatedForRemoval(constructor, type.getName()));
    }

    private static long countStringParameters(Constructor<?> constructor) {
        return Arrays.stream(constructor.getParameterTypes())
                .filter(String.class::equals)
                .count();
    }

    private static void assertDeprecatedForRemovalIfPresent(Class<?> type, String methodName) {
        List<Method> methods = Arrays.stream(type.getDeclaredMethods())
                .filter(method -> method.getName().equals(methodName))
                .toList();

        methods.forEach(method -> assertDeprecatedForRemoval(method, type.getName()));
    }

    private static void assertDeprecatedForRemoval(
            java.lang.reflect.AnnotatedElement element,
            String owner
    ) {
        Deprecated deprecated = element.getAnnotation(Deprecated.class);
        assertThat(deprecated)
                .as("%s textual compatibility bridge must remain @Deprecated", owner)
                .isNotNull();
        assertThat(deprecated.forRemoval())
                .as("%s textual compatibility bridge must remain marked for removal", owner)
                .isTrue();
    }

    private static Field declaredField(Class<?> type, String name) {
        try {
            return type.getDeclaredField(name);
        } catch (NoSuchFieldException exception) {
            throw new AssertionError(type.getName() + " must declare " + name, exception);
        }
    }

    private static void assertNoDeclaredField(Class<?> type, String name) {
        assertThat(Arrays.stream(type.getDeclaredFields()).map(Field::getName))
                .as("%s must not restore retired field %s", type.getName(), name)
                .doesNotContain(name);
    }

    private static String readMigration(String filename) throws IOException {
        Path path = MIGRATION_ROOT.resolve(filename);
        assertThat(path).exists();
        return Files.readString(path);
    }

    private static boolean reintroducesRetiredMultilingualStructure(String sql) {
        if (sql.contains("hidra_org_unit_type_translation")) {
            return true;
        }
        return Pattern.compile(
                "(?is)alter\\s+table\\s+hidra_org_unit_type\\b.*?add\\s+column\\s+description\\b"
        ).matcher(sql).find()
                || Pattern.compile(
                "(?is)alter\\s+table\\s+hidra_org_position\\b.*?add\\s+column\\s+description\\b"
        ).matcher(sql).find()
                || Pattern.compile(
                "(?is)alter\\s+table\\s+hidra_org_shift\\b.*?add\\s+column\\s+name\\b"
        ).matcher(sql).find();
    }
}
