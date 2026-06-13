# COR2-009 — Domain Name Value Object Audit

## 1. Task

| Field | Value |
|---|---|
| Roadmap | `docs/roadmap/correction_02.md` |
| Task | `COR2-009` |
| Commit message | `refactor(domain): standardize domain name value objects` |
| Scope | Classify and standardize domain value objects representing business labels |
| Status | Completed as scoped standardization plus audit artifact |

---

## 2. Classification Matrix

| Value object | Module | Current meaning | Decision | Implementation status |
|---|---|---|---|---|
| `RoleName` | identity | Role display label shown to administrators | BUSINESS_DISPLAY_LABEL_TRILINGUAL | Converted to `nameAr`, `nameFr`, `nameEn` with compatibility `of(String)` and `value()` |
| `TopologyName` | topology | Generic topology asset display label | BUSINESS_DISPLAY_LABEL_TRILINGUAL | Converted to `nameAr`, `nameFr`, `nameEn` with compatibility `of(String)` and `value()` |
| `OrganizationUnitName` | organization | Organization unit display label | BUSINESS_DISPLAY_LABEL_TRILINGUAL | Converted to `nameAr`, `nameFr`, `nameEn` with compatibility `of(String)` and `value()` |
| `PositionTitle` | organization | Position display title | BUSINESS_DISPLAY_LABEL_TRILINGUAL | Converted to `titleAr`, `titleFr`, `titleEn` with compatibility `of(String)` and `value()` |
| `EmployeeFullName` | organization | Personal legal/professional name of an employee | PERSONAL_NAME_SINGLE_VALUE | Kept single-value; personal names are not catalog labels and should not be translated blindly |
| `TopologyMultilingualName` | topology | Pipeline-specific multilingual name introduced by COR2-008 | BUSINESS_DISPLAY_LABEL_TRILINGUAL | Already trilingual |
| `TopologyMultilingualDescription` | topology | Pipeline-specific multilingual description introduced by COR2-008 | BUSINESS_DESCRIPTION_TRILINGUAL | Already trilingual |

---

## 3. Standardization Rule Applied

Business display labels now expose explicit language components:

```text
nameAr
nameFr
nameEn
```

For position titles, the domain language remains title-specific:

```text
titleAr
titleFr
titleEn
```

Temporary compatibility methods remain only to avoid breaking active callers in the same commit:

```java
of(String value)
value()
```

These methods project the English value and must be removed or replaced once API/application/persistence contracts are fully trilingual in later tasks.

---

## 4. Personal Name Exception

`EmployeeFullName` is intentionally not converted to trilingual fields in this task.

Reason:

```text
An employee full name is a personal identity attribute, not a business label/catalog/display title.
Names may be represented in different scripts in the future, but they must not be automatically treated as translations.
```

Future work may introduce explicit personal-name script fields if needed, for example:

```text
fullNameNative
fullNameLatin
fullNameArabicScript
```

That is separate from the Correction 02 trilingual business label convention.

---

## 5. Follow-Up Work

The following follow-up tasks must migrate callers away from compatibility projections:

```text
COR2-010 — persistence columns must store trilingual fields for role, topology asset, organization unit, and position labels where persisted.
COR2-011 — OpenAPI DTOs should expose trilingual labels instead of single value projections.
COR2-016 — architecture guardrails should reject new single-value business label objects unless explicitly exempt.
```

---

## 6. Validation Status

Required local validation from the roadmap:

```bash
find src/main/java/dz/sh/hidra/modules -path '*/domain/value/*.java' | grep -E 'Name|Title|Designation|Label|Description' || true
grep -R "record .*Name\|class .*Name\|record .*Title\|class .*Title" src/main/java/dz/sh/hidra/modules || true
mvn -q -DskipTests compile
mvn -q test
```

Result in this connector-only session:

```text
NOT_RUN_IN_SANDBOX
Reason: repository was inspected and updated through the GitHub connector; no local checkout or Maven execution environment was available in this session.
```

---

## 7. Acceptance Criteria Result

| Criterion | Result |
|---|---|
| Every label-like value object is classified | Completed for roadmap-known label-like value objects and COR2-008 multilingual value objects |
| Business display labels are trilingual | Completed for `RoleName`, `TopologyName`, `OrganizationUnitName`, and `PositionTitle` |
| Technical values remain single-value | Completed; technical codes and identifiers were not modified |
| Validation requires language values | Completed; converted value objects require all three language components in canonical constructors |
| Mapping tests cover trilingual fields | Deferred to caller migration tasks; compatibility methods keep existing tests compiling until API/persistence follow-up |
