# HidraAPI / HyFlo Coding Policy

**Status:** Enforced policy  
**Scope:** All Java, Spring Boot, API, domain, application, infrastructure, workflow, topology, analytics, and kernel-related code generated or modified in this repository.  
**Default project:** `HidraAPI`  
**Default product:** `Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics`  
**Default owner:** `Sonatrach / TRC : Digitalization Initiative`  
**Default author:** `Abir MEDJERAB`

---

## 1. Purpose

This policy defines the mandatory coding conditions for the HidraAPI / HyFlo project.

It is not a generic Java style guide. It is an enforceable architecture and implementation policy for a modular monolith that manages hydrocarbon transportation data, topology, workflows, analytics, and operational intelligence.

Any generated code, manual code, refactoring, package creation, or test addition must comply with this document.

---

## 2. Policy language

The following terms are mandatory:

- **MUST** means the rule is required.
- **MUST NOT** means the rule is forbidden.
- **SHOULD** means the rule is expected unless there is a strong project-specific reason.
- **MAY** means the rule is allowed but not required.

When a requested implementation conflicts with this policy, the implementation must stop at the safe boundary and report the conflict instead of silently violating the architecture.

---

## 3. Non-negotiable coding conditions

Before writing or modifying code, every task MUST satisfy these conditions:

1. **Stay inside the requested task.**  
   Do not implement future roadmap tasks, nearby TODOs, or opportunistic improvements.

2. **Respect module boundaries.**  
   A module must not directly reach into another module's internal packages.

3. **Use the modular monolith model.**  
   Prefer strict internal boundaries over premature microservices.

4. **Never create generic dumping-ground packages.**  
   The following packages are forbidden unless an existing approved policy explicitly allows them:
   - `shared`
   - `sharedkernel`
   - `common`
   - `core`
   - `utils`
   - `helper`
   - `helpers`
   - `misc`

5. **Do not mix layers.**  
   Domain, application, API, and infrastructure responsibilities must remain separate.

6. **Do not leak frameworks into the domain.**  
   Domain classes must not depend on Spring, JPA, Hibernate, Jackson, OpenAPI, or web concerns.

7. **Use explicit package ownership.**  
   Every class must live in a package that clearly identifies its module and layer.

8. **Use ports for dependencies.**  
   Application services depend on input/output ports, not infrastructure implementations.

9. **Use value objects for business concepts.**  
   Identifiers, codes, names, statuses, and coordinates must not be represented as raw primitives inside domain aggregates unless explicitly justified.

10. **Do not invent behavior while creating skeletons.**  
    If a task asks only for package skeletons or `package-info.java`, create only those files.

---

## 4. Repository-level architecture

The repository follows a modular monolith with strict internal boundaries.

Recommended top-level package model:

```text
dz.sh.hidra
├── kernel
├── platform
└── modules
    ├── topology
    ├── identity
    ├── organization
    ├── workflow
    ├── analytics
    ├── simulation
    └── <business-module>
```

### 4.1 Kernel

`kernel` owns cross-cutting technical and domain primitives that are intentionally reusable.

Examples:

```text
dz.sh.hidra.kernel.domain
dz.sh.hidra.kernel.exception
dz.sh.hidra.kernel.pagination
dz.sh.hidra.kernel.audit
```

Kernel MAY contain stable abstractions such as:

- `AggregateRoot`
- `ValueObject`
- `BusinessRuleViolationException`
- `PageResult`
- base audit abstractions

Kernel MUST NOT contain business-module-specific logic.

### 4.2 Platform

`platform` owns application-wide infrastructure and configuration.

Examples:

```text
dz.sh.hidra.platform.config
dz.sh.hidra.platform.security
dz.sh.hidra.platform.web
dz.sh.hidra.platform.persistence
dz.sh.hidra.platform.observability
```

Platform MAY contain Spring configuration, security filters, global exception handlers, OpenAPI configuration, and technical adapters.

Platform MUST NOT contain business rules.

### 4.3 Business modules

Each business module owns its domain, application use cases, API boundary, and infrastructure adapters.

Standard module structure:

```text
dz.sh.hidra.modules.<module>
├── domain
│   ├── model
│   ├── value
│   ├── service
│   └── event
├── application
│   ├── command
│   ├── query
│   ├── dto
│   ├── port
│   │   ├── in
│   │   └── out
│   └── service
├── api
│   └── rest
│       ├── controller
│       ├── request
│       ├── response
│       └── mapper
└── infrastructure
    ├── persistence
    │   ├── entity
    │   ├── repository
    │   ├── adapter
    │   └── mapper
    ├── client
    └── messaging
```

---

## 5. Layer dependency policy

### 5.1 Allowed dependency direction

Dependencies MUST flow inward:

```text
API → Application → Domain → Kernel
Infrastructure → Application ports + Domain
Platform → Kernel / technical configuration
```

### 5.2 Forbidden dependency direction

The following are forbidden:

```text
Domain → Application
Domain → API
Domain → Infrastructure
Domain → Spring / JPA / Web / Jackson / OpenAPI
Application → API
Application → Infrastructure implementation
Application → Spring MVC / JPA entities
API → Infrastructure repository implementation
Infrastructure → API request/response classes
```

### 5.3 Allowed import examples

A controller MAY import:

```java
import dz.sh.hidra.modules.topology.application.port.in.CreateFacilityUseCase;
import dz.sh.hidra.modules.topology.api.rest.mapper.FacilityRestMapper;
import dz.sh.hidra.modules.topology.api.rest.request.CreateFacilityRequest;
import dz.sh.hidra.modules.topology.api.rest.response.FacilityResponse;
```

An application service MAY import:

```java
import dz.sh.hidra.modules.topology.domain.model.Facility;
import dz.sh.hidra.modules.topology.domain.value.FacilityId;
import dz.sh.hidra.modules.topology.application.port.out.FacilityRepositoryPort;
import dz.sh.hidra.kernel.exception.BusinessRuleViolationException;
```

A domain aggregate MAY import:

```java
import dz.sh.hidra.kernel.domain.AggregateRoot;
import dz.sh.hidra.modules.topology.domain.value.TopologyCode;
import java.util.Objects;
```

An infrastructure adapter MAY import:

```java
import dz.sh.hidra.modules.topology.application.port.out.FacilityRepositoryPort;
import dz.sh.hidra.modules.topology.domain.model.Facility;
import dz.sh.hidra.modules.topology.infrastructure.persistence.repository.SpringDataFacilityRepository;
```

### 5.4 Forbidden import examples

A domain aggregate MUST NOT import:

```java
import jakarta.persistence.Entity;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.annotation.JsonProperty;
```

An application service MUST NOT import:

```java
import org.springframework.web.bind.annotation.RestController;
import jakarta.persistence.EntityManager;
import dz.sh.hidra.modules.topology.infrastructure.persistence.repository.SpringDataFacilityRepository;
```

A controller MUST NOT import:

```java
import dz.sh.hidra.modules.topology.infrastructure.persistence.repository.SpringDataFacilityRepository;
import dz.sh.hidra.modules.topology.infrastructure.persistence.entity.FacilityJpaEntity;
```

---

## 6. Mandatory Java file header

Every production Java file MUST start with the Hidra header block.

The header MUST appear before the `package` declaration.

Template:

```java
/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : <ClassName>
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : <YYYY-MM-DD>
 *
 * @Type        : <Class|Interface|Record|Enum|PackageInfo>
 * @Layer       : <Domain|Application|API|Infrastructure|Kernel|Platform>
 * @Module      : <module-name>
 * @Package     : <full.package.name>
 *
 * @Description : <one-sentence description of this type>.
 *
 */
package <full.package.name>;
```

### 6.1 Header rules

- `@Project` MUST be `HidraAPI` unless the repository name is officially changed.
- `@Product` MUST use the full product name.
- `@Owner` MUST be `Sonatrach / TRC : Digitalization Initiative`.
- `@Author` MUST be `Abir MEDJERAB` unless the task explicitly provides another approved author.
- `@Name` MUST match the class, interface, enum, record, or `package-info` name.
- `@Layer` MUST match the package and responsibility.
- `@Module` MUST match the bounded module, such as `topology`, `identity`, `workflow`, or `analytics`.
- `@Package` MUST exactly match the declared package.
- `@Description` MUST describe responsibility, not implementation details.
- `@CreatedOn` MUST be `2025-06-26`.

---

## 7. Package-info policy

Every meaningful package MAY have a `package-info.java` when the package needs architectural documentation.

When a task asks only for package skeletons:

- Create only production `package-info.java` files.
- Do not create tests unless explicitly requested.
- Do not create classes, records, enums, controllers, DTOs, services, repositories, entities, mappers, migrations, or behavior.
- Do not modify unrelated modules.
- Do not introduce imports unless required for package annotations.

Package-info template:

```java
/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : package-info
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : <YYYY-MM-DD>
 *
 * @Type        : PackageInfo
 * @Layer       : <Domain|Application|API|Infrastructure|Kernel|Platform>
 * @Module      : <module-name>
 * @Package     : <full.package.name>
 *
 * @Description : Declares the architectural role of the <package-name> package.
 *
 */
package <full.package.name>;
```

---

## 8. Domain layer policy

### 8.1 Domain package names

```text
dz.sh.hidra.modules.<module>.domain.model
dz.sh.hidra.modules.<module>.domain.value
dz.sh.hidra.modules.<module>.domain.service
dz.sh.hidra.modules.<module>.domain.event
```

### 8.2 Aggregates

Aggregates MUST:

- live in `.domain.model`;
- be `final` unless inheritance is explicitly justified;
- implement `AggregateRoot` when they are aggregate roots;
- use private fields and controlled construction;
- enforce mandatory invariants in constructors or static factories;
- expose behavior methods instead of public setters;
- use value objects for identifiers, codes, names, statuses, and coordinates;
- throw business exceptions for violated business rules.

Aggregates MUST NOT:

- use JPA annotations;
- use Spring annotations;
- expose mutable collections directly;
- accept invalid intermediate states;
- perform persistence, HTTP calls, messaging, logging orchestration, or transaction management.

Recommended aggregate pattern:

```java
public final class Facility implements AggregateRoot {

    private final FacilityId id;
    private final TopologyCode code;
    private TopologyName name;
    private TopologyStatus status;

    private Facility(
            FacilityId id,
            TopologyCode code,
            TopologyName name,
            TopologyStatus status
    ) {
        this.id = Objects.requireNonNull(id, "Facility id is required.");
        this.code = Objects.requireNonNull(code, "Facility code is required.");
        this.name = Objects.requireNonNull(name, "Facility name is required.");
        this.status = Objects.requireNonNull(status, "Facility status is required.");
    }

    public static Facility create(
            FacilityId id,
            TopologyCode code,
            TopologyName name
    ) {
        return new Facility(id, code, name, TopologyStatus.draft());
    }

    public void activate() {
        if (!status.canBeActivated()) {
            throw new BusinessRuleViolationException("Facility cannot be activated from its current status.");
        }
        this.status = TopologyStatus.active();
    }
}
```

### 8.3 Value objects

Value objects MUST:

- live in `.domain.value`;
- be immutable;
- validate their own structural constraints;
- avoid exposing invalid raw values;
- use business naming.

Value objects SHOULD be Java records when appropriate.

Example:

```java
public record TopologyCode(String value) {

    public TopologyCode {
        if (value == null || value.isBlank()) {
            throw new BusinessRuleViolationException("Topology code is required.");
        }
    }
}
```

### 8.4 Domain services

Domain services MUST:

- live in `.domain.service`;
- contain business rules that do not naturally belong to one aggregate;
- be framework-independent;
- depend only on domain objects, value objects, and approved kernel abstractions.

Domain services MUST NOT:

- inject repositories directly unless a domain-specific policy explicitly allows a domain port;
- call REST APIs;
- send messages;
- manage transactions.

---

## 9. Application layer policy

### 9.1 Application package names

```text
dz.sh.hidra.modules.<module>.application.command
dz.sh.hidra.modules.<module>.application.query
dz.sh.hidra.modules.<module>.application.dto
dz.sh.hidra.modules.<module>.application.port.in
dz.sh.hidra.modules.<module>.application.port.out
dz.sh.hidra.modules.<module>.application.service
```

### 9.2 Application services

Application services MUST:

- live in `.application.service`;
- be `final` unless proxying or framework constraints require otherwise;
- implement one or more input use-case ports from `.application.port.in`;
- use constructor injection;
- guard dependencies with `Objects.requireNonNull`;
- accept command/query objects instead of long primitive parameter lists;
- orchestrate domain objects, domain services, and output ports;
- return application DTOs or result types;
- enforce application-level rules such as uniqueness checks through output ports.

Application services MUST NOT:

- depend on REST request/response classes;
- depend on JPA entities;
- depend on Spring MVC;
- access repositories through infrastructure implementations;
- contain HTTP status logic;
- expose domain internals directly to the API layer.

Recommended pattern:

```java
public final class FacilityApplicationService
        implements CreateFacilityUseCase, GetFacilityByIdUseCase, ListFacilitiesUseCase {

    private final FacilityRepositoryPort facilityRepository;
    private final TopologyRegistrationDomainService registrationDomainService;

    public FacilityApplicationService(
            FacilityRepositoryPort facilityRepository,
            TopologyRegistrationDomainService registrationDomainService
    ) {
        this.facilityRepository = Objects.requireNonNull(facilityRepository, "Facility repository is required.");
        this.registrationDomainService = Objects.requireNonNull(registrationDomainService, "Registration domain service is required.");
    }

    @Override
    public FacilityDto create(CreateFacilityCommand command) {
        Objects.requireNonNull(command, "Create facility command is required.");

        if (facilityRepository.existsByCode(command.code())) {
            throw new BusinessRuleViolationException("Facility code already exists.");
        }

        Facility facility = Facility.create(command.id(), command.code(), command.name());
        registrationDomainService.validateFacilityRegistration(facility);

        return toDto(facilityRepository.save(facility));
    }

    private static FacilityDto toDto(Facility facility) {
        return new FacilityDto(
                facility.id().value(),
                facility.code().value(),
                facility.name().value(),
                facility.status().value()
        );
    }
}
```

### 9.3 Input ports

Input ports MUST:

- live in `.application.port.in`;
- represent use cases;
- use business language;
- accept commands or queries;
- return DTOs, result types, or void when appropriate.

Examples:

```java
public interface CreateFacilityUseCase {
    FacilityDto create(CreateFacilityCommand command);
}

public interface GetFacilityByIdUseCase {
    FacilityDto getById(GetFacilityByIdQuery query);
}
```

### 9.4 Output ports

Output ports MUST:

- live in `.application.port.out`;
- be pure interfaces;
- hide persistence and external system details;
- use domain objects and value objects, not JPA entities;
- return `Optional<T>` where absence is expected;
- use `PageResult<T>` for paginated results.

Example:

```java
public interface FacilityRepositoryPort {

    Facility save(Facility facility);

    Optional<Facility> findById(FacilityId id);

    boolean existsByCode(TopologyCode code);

    PageResult<Facility> list(ListFacilitiesQuery query);
}
```

### 9.5 Commands and queries

Commands and queries MUST:

- live in `.application.command` or `.application.query`;
- be immutable records unless a strong reason exists;
- represent one use case input;
- use domain value objects where appropriate;
- not contain REST annotations;
- not contain JPA annotations.

Examples:

```java
public record CreateFacilityCommand(
        FacilityId id,
        TopologyCode code,
        TopologyName name
) { }

public record ListFacilitiesQuery(
        int page,
        int size,
        String status
) { }
```

### 9.6 DTOs

Application DTOs MUST:

- live in `.application.dto`;
- be immutable records;
- be framework-independent;
- represent application output, not REST transport details;
- not contain business validation logic.

Example:

```java
public record FacilityDto(
        String id,
        String code,
        String name,
        String status
) { }
```

---

## 10. API layer policy

### 10.1 API package names

```text
dz.sh.hidra.modules.<module>.api.rest.controller
dz.sh.hidra.modules.<module>.api.rest.request
dz.sh.hidra.modules.<module>.api.rest.response
dz.sh.hidra.modules.<module>.api.rest.mapper
```

### 10.2 Controllers

Controllers MUST:

- live in `.api.rest.controller`;
- be annotated with `@RestController`;
- use a stable `/api/v1/<module>/<aggregate-plural>` base path;
- depend on input ports from `.application.port.in`;
- use API mappers to convert request/response objects;
- use `@Valid` for request bodies;
- use Bean Validation for query and path constraints;
- document endpoints with OpenAPI annotations.

Controllers MUST NOT:

- inject JPA repositories;
- inject infrastructure adapters directly;
- expose JPA entities;
- perform business rule validation that belongs to the domain;
- contain persistence logic;
- return domain aggregates directly.

Controller base pattern:

```java
@RestController
@RequestMapping("/api/v1/topology/facilities")
@Tag(name = "Topology Facilities", description = "Topology facility endpoints.")
public final class FacilityController {

    private final CreateFacilityUseCase createFacilityUseCase;
    private final GetFacilityByIdUseCase getFacilityByIdUseCase;
    private final ListFacilitiesUseCase listFacilitiesUseCase;
    private final FacilityRestMapper mapper;

    public FacilityController(
            CreateFacilityUseCase createFacilityUseCase,
            GetFacilityByIdUseCase getFacilityByIdUseCase,
            ListFacilitiesUseCase listFacilitiesUseCase,
            FacilityRestMapper mapper
    ) {
        this.createFacilityUseCase = Objects.requireNonNull(createFacilityUseCase, "Create facility use case is required.");
        this.getFacilityByIdUseCase = Objects.requireNonNull(getFacilityByIdUseCase, "Get facility use case is required.");
        this.listFacilitiesUseCase = Objects.requireNonNull(listFacilitiesUseCase, "List facilities use case is required.");
        this.mapper = Objects.requireNonNull(mapper, "Facility mapper is required.");
    }
}
```

### 10.3 REST endpoint policy

Every endpoint MUST include:

- `@Operation(summary = ..., description = ...)`
- `@ApiResponses`
- `@Parameter` on every path, query, and header parameter
- clear success and error response codes

Standard response codes:

```text
200 OK        - successful read or list
201 Created   - successful creation
204 No Content - successful delete or command with no response body
400 Bad Request - structural validation or invalid request
404 Not Found - requested single resource does not exist
409 Conflict - uniqueness conflict or state conflict
500 Internal Server Error - unexpected server-side failure
```

### 10.4 Pagination policy

Pagination parameters MUST use the following defaults and limits:

```java
@RequestParam(defaultValue = "0") @Min(0) int page,
@RequestParam(defaultValue = "20") @Min(1) @Max(200) int size
```

List endpoints SHOULD return `PageResult<T>` or an API response mapped from `PageResult<T>`.

### 10.5 API request objects

Request objects MUST:

- live in `.api.rest.request`;
- use Bean Validation annotations;
- represent external input shape;
- not be reused as application commands;
- not contain domain behavior.

Example:

```java
public record CreateFacilityRequest(
        @NotBlank String code,
        @NotBlank String name,
        String description
) { }
```

### 10.6 API response objects

Response objects MUST:

- live in `.api.rest.response`;
- represent external API response shape;
- be immutable records where possible;
- not expose domain aggregates or JPA entities.

Example:

```java
public record FacilityResponse(
        String id,
        String code,
        String name,
        String status
) { }
```

### 10.7 API mappers

API mappers MUST:

- live in `.api.rest.mapper`;
- convert request objects to commands/queries;
- convert application DTOs to response objects;
- not call repositories;
- not apply business decisions.

Example:

```java
public final class FacilityRestMapper {

    public CreateFacilityCommand toCommand(CreateFacilityRequest request) {
        Objects.requireNonNull(request, "Create facility request is required.");
        return new CreateFacilityCommand(
                FacilityId.newId(),
                new TopologyCode(request.code()),
                new TopologyName(request.name())
        );
    }

    public FacilityResponse toResponse(FacilityDto dto) {
        Objects.requireNonNull(dto, "Facility DTO is required.");
        return new FacilityResponse(dto.id(), dto.code(), dto.name(), dto.status());
    }
}
```

---

## 11. Infrastructure layer policy

### 11.1 Infrastructure package names

```text
dz.sh.hidra.modules.<module>.infrastructure.persistence.entity
dz.sh.hidra.modules.<module>.infrastructure.persistence.repository
dz.sh.hidra.modules.<module>.infrastructure.persistence.adapter
dz.sh.hidra.modules.<module>.infrastructure.persistence.mapper
dz.sh.hidra.modules.<module>.infrastructure.client
dz.sh.hidra.modules.<module>.infrastructure.messaging
```

### 11.2 Persistence adapters

Persistence adapters MUST:

- implement output ports from `.application.port.out`;
- translate between domain aggregates and JPA entities;
- keep JPA entities inside infrastructure;
- not expose Spring Data repositories to application services;
- preserve domain invariants when reconstructing domain objects.

Example naming:

```text
FacilityRepositoryPort              // application output port
JpaFacilityRepositoryAdapter         // infrastructure adapter implementing the port
SpringDataFacilityRepository         // Spring Data interface
FacilityJpaEntity                    // infrastructure persistence entity
FacilityPersistenceMapper            // infrastructure mapper
```

### 11.3 JPA entities

JPA entities MUST:

- live in `.infrastructure.persistence.entity`;
- represent persistence schema, not domain behavior;
- not be returned by application services or controllers;
- not replace domain aggregates.

### 11.4 Persistence mappers

Persistence mappers MUST:

- live in `.infrastructure.persistence.mapper`;
- convert domain objects to persistence entities;
- convert persistence entities to domain objects;
- not perform business workflows;
- not call external services.

---

## 12. Validation policy

Validation is split by responsibility.

### 12.1 API validation

API validation handles external input shape:

- required fields;
- string length;
- numeric bounds;
- basic format;
- pagination limits.

Use Bean Validation annotations in API request objects and controller parameters.

### 12.2 Application validation

Application validation handles use-case orchestration rules:

- command/query null checks;
- uniqueness checks through output ports;
- authorization checks when applicable;
- existence checks when loading required aggregates;
- coordination across aggregates.

### 12.3 Domain validation

Domain validation handles business invariants:

- aggregate consistency;
- status transitions;
- mandatory business fields;
- value object constraints;
- lifecycle rules;
- domain-specific conflict rules.

### 12.4 Error mapping

The global exception handling policy SHOULD map exceptions consistently:

```text
Bean Validation error                 → 400 Bad Request
Malformed request                     → 400 Bad Request
BusinessRuleViolationException         → 400 Bad Request or 409 Conflict
ResourceNotFoundException              → 404 Not Found
Duplicate code / unique constraint     → 409 Conflict
Unexpected exception                   → 500 Internal Server Error
```

Business errors MUST use business-oriented messages.

Good:

```text
Facility code already exists.
Facility cannot be activated from its current status.
```

Bad:

```text
Constraint violation on table FACILITY_UK_01.
Null pointer in service method.
```

---

## 13. Documentation policy

### 13.1 Class-level Javadoc

Every public production type SHOULD include class-level Javadoc after the mandatory header.

The Javadoc MUST contain these four labeled paragraphs when the type has behavior or architectural significance:

```java
/**
 * Business role:
 * Describes what this type represents in the hydrocarbon transportation business.
 *
 * Architecture role:
 * Describes the layer, dependency direction, and architectural responsibility.
 *
 * Validation:
 * Describes what this type validates or explicitly does not validate.
 *
 * Usage:
 * Describes how other components should use this type.
 */
```

### 13.2 OpenAPI documentation

Every REST controller MUST include:

```java
@Tag(name = "<Module> <AggregatePlural>", description = "<short description>.")
```

Every REST endpoint MUST include:

```java
@Operation(summary = "<short action>", description = "<business operation description>.")
@ApiResponses({ ... })
```

Every path, query, and header parameter SHOULD include:

```java
@Parameter(description = "...", example = "...", required = true)
```

---

## 14. Naming policy

### 14.1 Domain naming

```text
<Aggregate>                         // Facility, Pipeline, Segment
<Aggregate>Id                       // FacilityId, PipelineId
<Concept>Name                       // TopologyName
<Concept>Code                       // TopologyCode
<Concept>Status                     // TopologyStatus
<BusinessRule>DomainService         // TopologyRegistrationDomainService
```

### 14.2 Application naming

```text
Create<Aggregate>Command
Update<Aggregate>Command
Get<Aggregate>ByIdQuery
List<AggregatePlural>Query
<UseCase>UseCase
<Aggregate>ApplicationService
<Aggregate>RepositoryPort
<Aggregate>Dto
```

### 14.3 API naming

```text
<Aggregate>Controller
Create<Aggregate>Request
Update<Aggregate>Request
<Aggregate>Response
<Aggregate>RestMapper
```

### 14.4 Infrastructure naming

```text
<Aggregate>JpaEntity
SpringData<Aggregate>Repository
Jpa<Aggregate>RepositoryAdapter
<Aggregate>PersistenceMapper
```

---

## 15. Module-specific policy

### 15.1 Topology module

The topology module owns the physical and logical transportation network representation.

It MAY contain aggregates such as:

- `Facility`
- `Pipeline`
- `PipelineSegment`
- `Node`
- `Connection`
- `NetworkTopology`

Topology owns:

- network structure;
- facility and pipeline registration;
- topology lifecycle;
- topology visualization data needed to represent the network;
- graph-like connectivity rules.

Topology MUST NOT own:

- workflow execution;
- analytics calculations;
- user identity;
- infrastructure monitoring agents.

### 15.2 Workflow module

The workflow module owns business process execution.

It MAY contain aggregates such as:

- `WorkflowDefinition`
- `WorkflowInstance`
- `WorkflowTask`
- `ApprovalStep`

Workflow owns:

- process state transitions;
- approvals;
- task assignment;
- workflow lifecycle;
- orchestration policies.

Workflow MUST NOT own topology structure or analytics computation.

### 15.3 Analytics module

The analytics module owns derived insight, metrics, reporting, and decision-support projections.

It MAY contain:

- KPI projections;
- operational metrics;
- risk scores;
- dashboard read models;
- simulation result summaries.

Analytics MUST NOT become the transactional owner of topology or workflow entities.

### 15.4 Identity module

The identity module owns user, role, permission, and access concepts.

Identity MUST NOT be mixed with organization or business modules unless a documented boundary requires integration through ports.

### 15.5 Organization module

The organization module owns organizational units, ownership structures, teams, and business hierarchy.

Organization MUST NOT own authentication credentials or workflow execution.

---

## 16. Cross-module communication policy

Modules MUST communicate through stable boundaries.

Allowed patterns:

1. Application input ports exposed as use cases.
2. Application output ports implemented by adapters.
3. Domain events published through approved mechanisms.
4. Read-only projections designed for analytics.
5. Anti-corruption mappers between modules when necessary.

Forbidden patterns:

1. Direct import of another module's `.domain.model` from API or infrastructure without a documented use-case boundary.
2. Direct import of another module's `.infrastructure` package.
3. Direct database table access across modules.
4. Sharing mutable entities across modules.
5. Creating `common` or `shared` packages to bypass ownership.

---

## 17. Security and authorization policy

Security rules MUST be enforced at the correct layer:

- Authentication and token processing belong to platform/security.
- Authorization checks MAY be performed in API, application service, or policy service depending on granularity.
- Domain rules MUST remain business rules, not HTTP/security-framework rules.

Controllers MUST NOT manually parse tokens unless no platform abstraction exists.

Application services SHOULD receive the current actor through an explicit command field or approved security context abstraction when business behavior depends on the actor.

---

## 18. Transaction policy

Transactions SHOULD be declared at application service boundaries or infrastructure adapter boundaries according to project configuration.

Domain objects MUST NOT manage transactions.

Controllers MUST NOT manage transactions.

Infrastructure repositories MAY participate in transactions but MUST NOT decide business workflows.

---

## 19. Logging policy

Logging SHOULD be used for operational observability, not for business validation.

Sensitive operational data MUST NOT be logged.

Domain objects SHOULD NOT log.

Application services MAY log use-case boundaries when useful.

Infrastructure adapters MAY log external system or persistence failures.

---

## 20. Testing policy

When tests are requested or required by the task, they MUST respect the same architecture.

### 20.1 Domain tests

Domain tests MUST:

- test aggregates, value objects, and domain services;
- avoid Spring context;
- avoid database dependencies;
- focus on invariants and business rules.

### 20.2 Application tests

Application tests MUST:

- test use-case orchestration;
- mock output ports;
- verify interactions with repositories and domain services;
- avoid controllers and HTTP concerns.

### 20.3 API tests

API tests SHOULD:

- test request validation;
- test response mapping;
- test status codes;
- mock input ports.

### 20.4 Infrastructure tests

Infrastructure tests MAY:

- use database test containers or approved integration test configuration;
- verify persistence mappings;
- verify repository adapter behavior.

---

## 21. Migration policy

Database migration files MUST:

- belong to the infrastructure/persistence concern;
- be created only when the task explicitly requires persistence schema changes;
- use stable naming conventions already used in the repository;
- not be created for pure domain/application/API skeleton tasks.

---

## 22. Code generation execution policy

When an AI assistant or developer receives a coding task, the following execution sequence is mandatory:

1. Read `AGENTS.md` first when present.
2. Read the relevant roadmap or module policy document before coding.
3. Identify the exact task ID or requested scope.
4. Identify allowed modules and forbidden modules.
5. Identify allowed file types.
6. Make only the requested changes.
7. Do not create extra behavior.
8. Do not refactor unrelated files.
9. Do not change kernel/platform files unless explicitly requested.
10. Report any skipped change caused by policy constraints.

### 22.1 Stop conditions

Stop or restrict implementation when:

- the task asks to modify an unrelated module;
- the task requires a forbidden package;
- the task mixes domain with infrastructure;
- the task asks for behavior while the roadmap task only allows skeletons;
- the task would require guessing missing business rules;
- the task would bypass ports or module boundaries.

---

## 23. Acceptance checklist

A code change is acceptable only if all applicable answers are `YES`.

### Scope

- [ ] Does the change implement only the requested task?
- [ ] Are unrelated modules untouched?
- [ ] Are future roadmap tasks untouched?

### Header

- [ ] Does every Java file have the mandatory Hidra header?
- [ ] Is `@Package` exactly equal to the declared package?
- [ ] Is `@Layer` correct?
- [ ] Is `@Module` correct?

### Architecture

- [ ] Are packages aligned with domain/application/API/infrastructure responsibilities?
- [ ] Are dependency directions respected?
- [ ] Are module boundaries respected?
- [ ] Are forbidden generic packages avoided?

### Domain

- [ ] Are aggregates framework-free?
- [ ] Are invariants enforced inside domain objects or domain services?
- [ ] Are value objects used for business concepts?

### Application

- [ ] Do services implement input ports?
- [ ] Do services depend on output ports rather than infrastructure implementations?
- [ ] Are commands/queries used for use-case input?
- [ ] Are DTOs framework-independent?

### API

- [ ] Are controllers mapped under `/api/v1/<module>/<aggregate-plural>`?
- [ ] Are request objects validated with Bean Validation?
- [ ] Are OpenAPI annotations present?
- [ ] Are domain and JPA objects hidden from REST responses?

### Infrastructure

- [ ] Do adapters implement output ports?
- [ ] Are JPA entities contained inside infrastructure?
- [ ] Are domain objects reconstructed safely?

### Validation and errors

- [ ] Are structural validations in API request objects?
- [ ] Are business rules in domain/application?
- [ ] Are error messages business-oriented?

---

## 24. Prompt-ready coding condition block

Use this block in future implementation prompts:

```text
Apply the HidraAPI / HyFlo Coding Policy.

Rules:
- Implement only the requested task.
- Do not implement future roadmap tasks.
- Respect modular monolith boundaries.
- Do not create shared, sharedkernel, common, core, utils, helper, helpers, or misc packages.
- Keep domain free from Spring, JPA, Hibernate, Jackson, OpenAPI, HTTP, and persistence concerns.
- Use domain/application/API/infrastructure package boundaries exactly.
- Application services must depend on ports, not infrastructure implementations.
- Controllers must depend on application input ports and API mappers only.
- Infrastructure adapters must implement application output ports.
- Use value objects for identifiers, codes, names, statuses, coordinates, and other business concepts.
- Use commands and queries for use-case input.
- Use immutable records for DTOs, requests, responses, commands, and queries when appropriate.
- Add the canonical Hidra Java header to every Java file.
- Do not modify unrelated modules, kernel, platform, organization, identity, workflow, analytics, or topology unless explicitly allowed by the task.
- If the request conflicts with the policy, stop at the safe boundary and explain the conflict.
```

---

## 25. Final rule

Architecture is part of the product.

A change that compiles but violates module ownership, layer boundaries, or domain purity is not acceptable in HidraAPI / HyFlo.
