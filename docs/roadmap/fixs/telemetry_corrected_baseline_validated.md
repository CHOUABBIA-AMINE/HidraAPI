# TEL-COR-004 — Telemetry Corrected Baseline Validated

```text
Roadmap file : docs/roadmap/telemetry_corrected_baseline_validated.md
Related file : docs/roadmap/telemetry.md
Related file : docs/roadmap/telemetry_validation_checklist.md
Related file : docs/roadmap/telemetry_topology_boot_baseline.md
Roadmap code : TEL-COR
Task code    : TEL-COR-004
Scope        : Mark corrected telemetry/topology baseline validated
Repository   : HidraAPI
Namespace    : dz.sh.hidra
Product      : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
Author       : Abir MEDJERAB
CreatedOn    : 2025-06-26
UpdatedOn    : 2026-06-07
Status       : Validated corrected baseline record
```

---

## 1. Purpose

This document marks the corrected telemetry/topology baseline as validated after the correction path recorded in:

```text
docs/roadmap/telemetry_topology_boot_baseline.md
```

This is a documentation-only validation record.

It does not contain raw build logs, unresolved stack traces, compiler output dumps, or implementation code.

---

## 2. Validated correction chain

The corrected baseline includes the following correction history:

| Correction | Module | Final status | Reason |
|---|---|---:|---|
| `TEL-COR-001` | telemetry | Superseded | `@Component` mapper registration was not deterministic enough for the runtime baseline. |
| `TEL-COR-002` | telemetry | Accepted | Explicit mapper beans in `TelemetryConfiguration` provide deterministic Spring wiring. |
| `TOP-COR-001` | topology | Superseded | `@Primary` alone did not remove concrete application-service ambiguity. |
| `TOP-COR-002` | topology | Accepted | Explicit `@Qualifier(...)` annotations on topology use-case factory parameters remove ambiguity. |
| `TEL-COR-003` | telemetry/topology docs | Accepted | Correction findings and validation path were recorded without raw logs. |
| `TEL-COR-004` | telemetry/topology docs | Current | Corrected baseline is marked validated. |

---

## 3. Validated production files

Telemetry production wiring baseline:

```text
src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/configuration/TelemetryConfiguration.java
src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/mapper/TelemetryPersistenceMapper.java
src/main/java/dz/sh/hidra/modules/telemetry/api/rest/mapper/TelemetryRestMapper.java
```

Topology production wiring baseline:

```text
src/main/java/dz/sh/hidra/modules/topology/infrastructure/configuration/TopologyConfiguration.java
```

Telemetry roadmap and validation docs:

```text
docs/roadmap/telemetry.md
docs/roadmap/telemetry_validation_checklist.md
docs/roadmap/telemetry_topology_boot_baseline.md
docs/roadmap/telemetry_corrected_baseline_validated.md
```

---

## 4. Validated wiring expectations

Telemetry:

```text
TelemetryConfiguration registers TelemetryPersistenceMapper as a production bean.
TelemetryConfiguration registers TelemetryRestMapper as a production bean.
Telemetry mapper classes are not also registered as duplicate @Component beans.
Telemetry persistence adapters can inject TelemetryPersistenceMapper.
Telemetry REST controllers can inject TelemetryRestMapper.
```

Topology:

```text
TopologyConfiguration imports org.springframework.beans.factory.annotation.Qualifier.
TopologyConfiguration keeps concrete application service beans deterministic with @Primary.
TopologyConfiguration qualifies use-case factory method parameters explicitly.
TopologyConfiguration no longer relies on ambiguous concrete service parameter resolution.
```

Expected topology counts:

```text
@Primary count   : 9
@Qualifier count : 23
```

---

## 5. Validated command gate

The corrected baseline is marked validated after the following command gate:

```bash
mvn -q clean compile
mvn -q -DskipITs test -Dtest='TelemetryApplicationBootSmokeTest'
mvn -q test
```

Expected result:

```text
All commands pass before the next module starts.
```

If this document is copied before the local command gate has actually passed, update the status back to:

```text
Pending local validation
```

and do not start the next module.

---

## 6. Acceptance criteria

The corrected telemetry/topology baseline is accepted when:

```text
mvn -q clean compile passes.
TelemetryApplicationBootSmokeTest passes.
mvn -q test passes.
Telemetry mapper bean wiring remains explicit and deterministic.
Topology use-case factory method parameters remain qualified.
No test-only fallback beans are introduced.
Telemetry business taxonomy remains catalog-backed.
Telemetry multilingual policy remains enforced.
No raw logs are pasted into roadmap documents.
```

---

## 7. Reject criteria

Reject this validated status if any of the following become true:

```text
TelemetryApplicationBootSmokeTest fails.
Spring reports missing telemetry mapper beans again.
Spring reports ambiguous topology application-service beans again.
Telemetry mappers are registered twice.
Topology @Qualifier annotations are removed from use-case factory method parameters.
Telemetry business taxonomy is converted to Java enums.
Telemetry imports topology infrastructure directly.
Flow, risk, analytics, workflow, reporting, or notification work starts before the full baseline passes.
```

---

## 8. Manual verification checklist

### 8.1 Telemetry mapper wiring

```bash
grep -R "TelemetryPersistenceMapper telemetryPersistenceMapper" -n src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/configuration
grep -R "TelemetryRestMapper telemetryRestMapper" -n src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/configuration
```

Expected:

```text
Both bean methods exist in TelemetryConfiguration.
```

Reject duplicate mapper component registration:

```bash
grep -R "@Component" -n src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/mapper/TelemetryPersistenceMapper.java
grep -R "@Component" -n src/main/java/dz/sh/hidra/modules/telemetry/api/rest/mapper/TelemetryRestMapper.java
```

Expected:

```text
No output if explicit @Bean registration is the chosen strategy.
```

---

### 8.2 Topology qualifier wiring

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

### 8.3 Telemetry taxonomy guard

```bash
grep -R "enum TelemetrySourceType" -n src/main/java/dz/sh/hidra/modules/telemetry && exit 1 || true
grep -R "enum TelemetryDeviceType" -n src/main/java/dz/sh/hidra/modules/telemetry && exit 1 || true
grep -R "enum TelemetryPointType" -n src/main/java/dz/sh/hidra/modules/telemetry && exit 1 || true
grep -R "enum TelemetrySignalType" -n src/main/java/dz/sh/hidra/modules/telemetry && exit 1 || true
grep -R "enum TelemetryQualityCode" -n src/main/java/dz/sh/hidra/modules/telemetry && exit 1 || true
grep -R "enum TelemetryProtocolType" -n src/main/java/dz/sh/hidra/modules/telemetry && exit 1 || true
```

Expected:

```text
No forbidden business taxonomy enums.
```

Allowed technical enums remain:

```text
TelemetrySourceStatus
TelemetryDeviceStatus
TelemetryPointStatus
TelemetryReadingState
TelemetryIngestionBatchStatus
```

---

## 9. Validated module readiness

After this baseline is validated, telemetry is ready for downstream module integration as a stable upstream dependency.

Telemetry can now provide:

```text
source/device/point metadata
point-to-topology binding lookup
reading ingestion records
reading quality and lifecycle state
ingestion batch traceability
catalog-backed telemetry type references
localized labels and catalog translations
```

Topology remains the upstream source for physical asset identity and structure.

Telemetry remains the upstream source for industrial signal acquisition and trust.

---

## 10. Next module gate

Do not start a new module unless the command gate remains green.

If green, the next module should be selected explicitly from the product roadmap.

Recommended next planning action:

```text
NEXT-MOD-001 — docs(roadmap): select and scope the next module after telemetry
```

If the next module is already decided, create a module-specific roadmap first and do not start implementation directly.

---

## 11. Final status

```text
Telemetry corrected baseline : VALIDATED
Topology boot wiring baseline: VALIDATED
Telemetry as next dependency : READY
Next module implementation   : ALLOWED AFTER FULL TEST BASELINE REMAINS GREEN
```
