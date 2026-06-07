# TEL-COR-003 — Corrected Telemetry / Topology Boot Baseline

```text
Roadmap file : docs/roadmap/telemetry_topology_boot_baseline.md
Related file : docs/roadmap/telemetry.md
Related file : docs/roadmap/telemetry_validation_checklist.md
Roadmap code : TEL-COR
Task code    : TEL-COR-003
Scope        : Record corrected telemetry and topology boot wiring baseline
Repository   : HidraAPI
Namespace    : dz.sh.hidra
Product      : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
Author       : Abir MEDJERAB
CreatedOn    : 2025-06-26
UpdatedOn    : 2026-06-07
Status       : Documentation-only correction baseline
```

---

## 1. Purpose

This document records the corrected telemetry/topology Spring Boot baseline discovered while validating the telemetry module after TEL-025.

It is a planning and validation artifact only.

It must not contain raw compiler output, unresolved stack traces, or pasted build logs. Runtime failures are summarized as findings and correction records.

---

## 2. Baseline context

Telemetry implementation reached the boot-smoke validation gate after:

```text
TEL-002 through TEL-025
```

The boot-smoke validation exposed production wiring gaps. These gaps were valid baseline findings because the test starts the real `HidraApplication` context and does not hide missing production beans with test-only fallback beans.

The boot baseline involves both modules:

```text
telemetry
topology
```

Reason:

```text
Telemetry owns telemetry acquisition.
Topology owns physical assets.
Telemetry binds points to topology assets through neutral lookup integration.
Therefore telemetry boot validation depends on topology application wiring being stable.
```

---

## 3. Scope

This TEL-COR-003 document records corrections only.

It does not implement code.

It does not change:

```text
production Java files
test Java files
Flyway migrations
REST contracts
domain rules
application service behavior
persistence behavior
business taxonomy modeling
```

---

## 4. Corrected baseline findings

| Finding | Module | Symptom summary | Root cause | Correction |
|---|---|---|---|---|
| `TEL-BOOT-FND-001` | telemetry | `TelemetryCatalogRepositoryAdapter` could not inject `TelemetryPersistenceMapper` | Mapper existed as a plain class but was required as a Spring bean | Register telemetry mappers explicitly in `TelemetryConfiguration` |
| `TEL-BOOT-FND-002` | telemetry | REST controllers would require `TelemetryRestMapper` as a Spring bean | Mapper existed as a plain class while controllers constructor-inject it | Register `TelemetryRestMapper` explicitly in `TelemetryConfiguration` |
| `TOP-BOOT-FND-001` | topology | `TopologyConfiguration#createFacilityUseCase` saw multiple `FacilityApplicationService` candidates | Concrete application service instance was exposed as both concrete bean and inbound use-case beans | Add deterministic topology bean selection |
| `TOP-BOOT-FND-002` | topology | `@Primary` alone did not remove ambiguity for factory method parameter resolution | Use-case beans still expose the same concrete service type | Add explicit `@Qualifier(...)` on topology use-case factory method parameters |

---

## 5. Correction records

### 5.1 TEL-COR-001 — mapper component attempt

Initial correction attempted to register telemetry mappers using `@Component`.

Files involved:

```text
src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/mapper/TelemetryPersistenceMapper.java
src/main/java/dz/sh/hidra/modules/telemetry/api/rest/mapper/TelemetryRestMapper.java
```

Outcome:

```text
Superseded.
```

Reason:

```text
Spring still did not see `TelemetryPersistenceMapper` at runtime in the user's environment.
The stronger correction is explicit bean registration inside `TelemetryConfiguration`.
```

Do not keep both strategies if they create duplicate beans.

---

### 5.2 TEL-COR-002 — explicit telemetry mapper beans

Required production correction:

```text
src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/configuration/TelemetryConfiguration.java
```

Required imports:

```java
import dz.sh.hidra.modules.telemetry.api.rest.mapper.TelemetryRestMapper;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.mapper.TelemetryPersistenceMapper;
```

Required bean methods:

```java
@Bean
public TelemetryPersistenceMapper telemetryPersistenceMapper() {
    return new TelemetryPersistenceMapper();
}

@Bean
public TelemetryRestMapper telemetryRestMapper() {
    return new TelemetryRestMapper();
}
```

Required cleanup if `@Component` was previously added to mapper classes:

```text
Remove @Component from TelemetryPersistenceMapper.
Remove @Component from TelemetryRestMapper.
Remove org.springframework.stereotype.Component imports from both mapper classes.
```

Status:

```text
Required for corrected telemetry boot baseline.
```

---

### 5.3 TOP-COR-001 — topology @Primary attempt

Initial topology correction attempted:

```java
import org.springframework.context.annotation.Primary;
```

and:

```java
@Bean
@Primary
FacilityApplicationService facilityApplicationService(...) {
    return new FacilityApplicationService(...);
}
```

applied to all concrete topology application services.

Outcome:

```text
Insufficient by itself.
```

Reason:

```text
Spring still saw use-case beans as candidates of the same concrete application service type.
```

Status:

```text
Superseded by TOP-COR-002 qualifier correction.
```

---

### 5.4 TOP-COR-002 — topology qualifier correction

Required production correction:

```text
src/main/java/dz/sh/hidra/modules/topology/infrastructure/configuration/TopologyConfiguration.java
```

Required import:

```java
import org.springframework.beans.factory.annotation.Qualifier;
```

Keep:

```java
import org.springframework.context.annotation.Primary;
```

Required pattern:

```java
@Bean
CreateFacilityUseCase createFacilityUseCase(
        @Qualifier("facilityApplicationService") FacilityApplicationService service) {

    return service;
}
```

Apply the same qualifier pattern to every topology use-case factory method that accepts a concrete application service.

Expected concrete service bean names:

| Concrete service | Bean name |
|---|---|
| `PipelineSystemApplicationService` | `pipelineSystemApplicationService` |
| `PipelineApplicationService` | `pipelineApplicationService` |
| `FacilityApplicationService` | `facilityApplicationService` |
| `TopologyNodeApplicationService` | `topologyNodeApplicationService` |
| `PipelineSegmentApplicationService` | `pipelineSegmentApplicationService` |
| `PipelineAppurtenanceApplicationService` | `pipelineAppurtenanceApplicationService` |
| `TopologyConnectionApplicationService` | `topologyConnectionApplicationService` |
| `EquipmentApplicationService` | `equipmentApplicationService` |
| `TopologyCatalogApplicationService` | `topologyCatalogApplicationService` |

Expected qualifier count in corrected `TopologyConfiguration.java`:

```text
23
```

Expected primary count in corrected `TopologyConfiguration.java`:

```text
9
```

Status:

```text
Required for corrected topology boot baseline.
```

---

## 6. Corrected file inventory

Telemetry corrected files:

```text
src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/configuration/TelemetryConfiguration.java
src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/mapper/TelemetryPersistenceMapper.java
src/main/java/dz/sh/hidra/modules/telemetry/api/rest/mapper/TelemetryRestMapper.java
```

Topology corrected file:

```text
src/main/java/dz/sh/hidra/modules/topology/infrastructure/configuration/TopologyConfiguration.java
```

Documentation record:

```text
docs/roadmap/telemetry_topology_boot_baseline.md
```

---

## 7. Final expected boot-wiring baseline

The corrected baseline requires:

```text
TelemetryConfiguration registers TelemetryPersistenceMapper as a bean.
TelemetryConfiguration registers TelemetryRestMapper as a bean.
Telemetry mapper classes are not also registered as duplicate @Component beans.
TopologyConfiguration keeps concrete application-service beans deterministic.
TopologyConfiguration qualifies use-case factory method parameters explicitly.
TelemetryApplicationBootSmokeTest remains a real Spring Boot context test.
No test-only fallback production beans are introduced.
```

---

## 8. Validation commands

Run from repository root.

### 8.1 Compile

```bash
mvn -q clean compile
```

Expected result:

```text
Pass.
```

If this fails:

```text
Do not continue to full test.
Record the compiler issue as a new correction finding.
```

---

### 8.2 Telemetry boot smoke

```bash
mvn -q -DskipITs test -Dtest='TelemetryApplicationBootSmokeTest'
```

Expected result:

```text
Pass.
```

If this fails:

```text
Do not start the next module.
Convert the first missing-bean or schema failure into a focused correction.
```

---

### 8.3 Targeted telemetry tests

```bash
mvn -q -DskipITs test -Dtest='Telemetry*Test'
mvn -q -DskipITs test -Dtest='Telemetry*ApplicationServiceTest'
mvn -q -DskipITs test -Dtest='TelemetryPersistenceMapperTest,TelemetryJpaRepositoryTest,TelemetryPersistenceAdapterTest'
mvn -q -DskipITs test -Dtest='TelemetryRestMapperTest,TelemetryRestControllerTest'
```

Expected result:

```text
Pass.
```

---

### 8.4 Full baseline

```bash
mvn -q test
```

Expected result:

```text
Pass.
```

This is the final gate before selecting or starting the next business module.

---

## 9. Manual verification checklist

### 9.1 Telemetry mapper beans

Check:

```bash
grep -R "TelemetryPersistenceMapper telemetryPersistenceMapper" -n src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/configuration
grep -R "TelemetryRestMapper telemetryRestMapper" -n src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/configuration
```

Reject if mapper classes still contain duplicate component registration:

```bash
grep -R "@Component" -n src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/mapper/TelemetryPersistenceMapper.java
grep -R "@Component" -n src/main/java/dz/sh/hidra/modules/telemetry/api/rest/mapper/TelemetryRestMapper.java
```

Expected:

```text
No @Component output for the two mapper classes if explicit @Bean registration is used.
```

---

### 9.2 Topology qualifiers

Check:

```bash
grep -R "@Qualifier" -n src/main/java/dz/sh/hidra/modules/topology/infrastructure/configuration/TopologyConfiguration.java
grep -R "@Primary" -n src/main/java/dz/sh/hidra/modules/topology/infrastructure/configuration/TopologyConfiguration.java
```

Expected:

```text
23 @Qualifier occurrences.
9 @Primary occurrences.
```

---

### 9.3 No business taxonomy regression

Telemetry business taxonomy must remain catalog-backed.

Reject if these appear:

```bash
grep -R "enum TelemetrySourceType" -n src/main/java/dz/sh/hidra/modules/telemetry && exit 1 || true
grep -R "enum TelemetryDeviceType" -n src/main/java/dz/sh/hidra/modules/telemetry && exit 1 || true
grep -R "enum TelemetryPointType" -n src/main/java/dz/sh/hidra/modules/telemetry && exit 1 || true
grep -R "enum TelemetrySignalType" -n src/main/java/dz/sh/hidra/modules/telemetry && exit 1 || true
grep -R "enum TelemetryQualityCode" -n src/main/java/dz/sh/hidra/modules/telemetry && exit 1 || true
grep -R "enum TelemetryProtocolType" -n src/main/java/dz/sh/hidra/modules/telemetry && exit 1 || true
```

---

## 10. Acceptance criteria

TEL-COR-003 is accepted when:

```text
docs/roadmap/telemetry_topology_boot_baseline.md exists.
The document records TEL-COR-001, TEL-COR-002, TOP-COR-001, and TOP-COR-002.
The document explains why TEL-COR-001 and TOP-COR-001 were superseded.
The document lists corrected files.
The document includes compile, smoke-test, targeted-test, and full-test commands.
The document keeps failures as summarized findings, not raw logs.
The document does not introduce new implementation scope.
```

Corrected baseline is accepted only when:

```text
mvn -q clean compile passes.
mvn -q -DskipITs test -Dtest='TelemetryApplicationBootSmokeTest' passes.
mvn -q test passes.
```

---

## 11. Reject criteria

Reject the corrected baseline if:

```text
TelemetryApplicationBootSmokeTest is weakened to ignore missing production beans.
Test-only fallback beans are added to hide production wiring gaps.
Telemetry mappers are registered twice.
Topology use-case factory methods still rely on ambiguous concrete service injection.
Business taxonomy types are converted to Java enums.
Telemetry imports topology infrastructure directly.
Raw stack traces are pasted into roadmap docs.
The next module starts before compile, smoke, and full tests pass.
```

---

## 12. Remaining risk register

| Risk | Status | Mitigation |
|---|---:|---|
| Another missing Spring bean appears after topology qualifiers | Open until smoke test passes | Fix the first real production missing bean directly. |
| Database schema mismatch appears after bean wiring is corrected | Open until smoke test reaches JPA validation | Convert the first schema mismatch into a focused migration or entity mapping correction. |
| Testcontainers/Docker not available locally | Environment risk | Run compile and targeted non-container tests locally; run boot smoke in an environment with Docker. |
| Roadmap numbering drift between executed TEL-COR tasks and original telemetry plan | Documentation risk | Keep this document as the boot baseline record and update `docs/roadmap/telemetry.md` later if needed. |

---

## 13. Next decision gate

Do not start flow, risk, analytics, workflow, reporting, or notification yet.

Proceed only after:

```text
mvn -q clean compile
mvn -q -DskipITs test -Dtest='TelemetryApplicationBootSmokeTest'
mvn -q test
```

If all pass, the next recommended documentation task is:

```text
TEL-COR-004 — docs(telemetry): mark telemetry corrected baseline validated
```

If any command fails, the next task must be a focused correction task for the first failure.
