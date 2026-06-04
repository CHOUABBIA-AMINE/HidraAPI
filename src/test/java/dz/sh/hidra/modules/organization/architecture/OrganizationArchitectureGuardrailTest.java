/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationArchitectureGuardrailTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.architecture
 *
 * @Description : ArchUnit guardrail tests for organization module boundaries.
 *
 */
package dz.sh.hidra.modules.organization.architecture;

import com.tngtech.archunit.core.importer.ImportOption.DoNotIncludeTests;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

/**
 * Enforces architecture boundaries for the organization module.
 *
 * <p>Business role:
 * This guardrail protects the organization bounded context, which owns employees, organization
 * units, positions, assignments, and reporting lines. It also protects the rule that station as an
 * organization unit belongs to organization, while physical station assets belong to topology.
 *
 * <p>Architecture role:
 * This is an ArchUnit test. It checks package dependencies without loading Spring, JPA, database,
 * REST runtime, identity implementation, topology implementation, or platform runtime.
 *
 * <p>Validation:
 * The test prevents forbidden dependencies between domain, application, API, infrastructure,
 * identityaccess, identity, topology, platform, OpenAPI, Bean Validation, Spring, and JPA packages.
 *
 * <p>Usage:
 * Run this test with the ORG-020 validation command or as part of the full Maven test suite.
 */
@AnalyzeClasses(
        packages = "dz.sh.hidra",
        importOptions = DoNotIncludeTests.class
)
class OrganizationArchitectureGuardrailTest {

    /**
     * Domain must remain pure and must not depend on outer organization layers or technical frameworks.
     */
    @ArchTest
    static final ArchRule organizationDomainMustNotDependOnOuterLayersOrFrameworks = noClasses()
            .that()
            .resideInAPackage("..modules.organization.domain..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage(
                    "..modules.organization.application..",
                    "..modules.organization.api..",
                    "..modules.organization.infrastructure..",
                    "..platform..",
                    "org.springframework..",
                    "jakarta.persistence..",
                    "jakarta.validation..",
                    "io.swagger.v3..");

    /**
     * Application layer may coordinate use cases but must not depend on API, persistence, platform, or frameworks.
     */
    @ArchTest
    static final ArchRule organizationApplicationMustNotDependOnApiInfrastructurePlatformOrFrameworks = noClasses()
            .that()
            .resideInAPackage("..modules.organization.application..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage(
                    "..modules.organization.api..",
                    "..modules.organization.infrastructure..",
                    "..platform..",
                    "org.springframework..",
                    "jakarta.persistence..",
                    "jakarta.validation..",
                    "io.swagger.v3..");

    /**
     * API layer must call application ports and must never access persistence or infrastructure internals.
     */
    @ArchTest
    static final ArchRule organizationApiMustNotDependOnInfrastructureOrPersistence = noClasses()
            .that()
            .resideInAPackage("..modules.organization.api..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage(
                    "..modules.organization.infrastructure..",
                    "jakarta.persistence..",
                    "org.springframework.data..",
                    "org.hibernate..");

    /**
     * Infrastructure may implement ports but must not depend on REST API contracts.
     */
    @ArchTest
    static final ArchRule organizationInfrastructureMustNotDependOnApi = noClasses()
            .that()
            .resideInAPackage("..modules.organization.infrastructure..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..modules.organization.api..");

    /**
     * Organization must not recreate the old identityaccess package or depend on it.
     */
    @ArchTest
    static final ArchRule organizationMustNotDependOnIdentityAccess = noClasses()
            .that()
            .resideInAPackage("..modules.organization..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..modules.identityaccess..");

    /**
     * Organization must not import topology implementation/domain classes; neutral references are used instead.
     */
    @ArchTest
    static final ArchRule organizationMustNotDependOnTopology = noClasses()
            .that()
            .resideInAPackage("..modules.organization..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..modules.topology..");

    /**
     * Organization must not depend on identity implementation classes; employees are separate from users.
     */
    @ArchTest
    static final ArchRule organizationMustNotDependOnIdentityImplementation = noClasses()
            .that()
            .resideInAPackage("..modules.organization..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..modules.identity..");

    /**
     * OpenAPI/Swagger annotations are allowed only in the organization API layer.
     */
    @ArchTest
    static final ArchRule openApiAnnotationsMustStayInOrganizationApiLayer = noClasses()
            .that()
            .resideInAPackage("..modules.organization..")
            .and()
            .resideOutsideOfPackage("..modules.organization.api..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("io.swagger.v3..");

    /**
     * Bean Validation annotations are allowed only in REST API request contracts and controllers.
     */
    @ArchTest
    static final ArchRule beanValidationMustStayOutOfDomainApplicationAndInfrastructure = noClasses()
            .that()
            .resideInAnyPackage(
                    "..modules.organization.domain..",
                    "..modules.organization.application..",
                    "..modules.organization.infrastructure..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("jakarta.validation..");

    /**
     * JPA annotations and Hibernate APIs are allowed only in organization infrastructure persistence.
     */
    @ArchTest
    static final ArchRule jpaMustStayInsideOrganizationPersistenceInfrastructure = noClasses()
            .that()
            .resideInAPackage("..modules.organization..")
            .and()
            .resideOutsideOfPackage("..modules.organization.infrastructure.persistence..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage(
                    "jakarta.persistence..",
                    "org.hibernate..");

    /**
     * Spring Data repositories are infrastructure-only.
     */
    @ArchTest
    static final ArchRule springDataMustStayInsideOrganizationPersistenceInfrastructure = noClasses()
            .that()
            .resideInAPackage("..modules.organization..")
            .and()
            .resideOutsideOfPackage("..modules.organization.infrastructure.persistence..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("org.springframework.data..");

    /**
     * Spring Web controllers and HTTP contracts are API-only.
     */
    @ArchTest
    static final ArchRule springWebMustStayInsideOrganizationApiLayer = noClasses()
            .that()
            .resideInAPackage("..modules.organization..")
            .and()
            .resideOutsideOfPackage("..modules.organization.api..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage(
                    "org.springframework.web..",
                    "org.springframework.http..");

    /**
     * Spring configuration annotations are infrastructure-configuration-only.
     */
    @ArchTest
    static final ArchRule springConfigurationMustStayInsideOrganizationInfrastructureConfiguration = noClasses()
            .that()
            .resideInAPackage("..modules.organization..")
            .and()
            .resideOutsideOfPackage("..modules.organization.infrastructure.configuration..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("org.springframework.context.annotation..");
}
