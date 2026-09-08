# HidraAPI Stabilization Roadmap 02 — Repository Recovery and Verification

```text
Roadmap file : docs/roadmap/fixs/stabilization_02.md
Roadmap code : STB2
Repository   : CHOUABBIA-AMINE/HidraAPI
Baseline     : c8947feab934fe95c7eaff3c863afaaba73ddb8e
Product      : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
Author       : Abir MEDJERAB
CreatedOn    : 2025-06-26
UpdatedOn    : 2026-09-08
Status       : Prepared for execution
```

---

## 1. Purpose

This roadmap stabilizes the live HidraAPI `main` branch before additional bounded-context
implementation work.

The scope is deliberately repository-level. It corrects verified repository drift and
restores missing engineering guardrails without redesigning business domains.

Verified baseline findings:

```text
1. Spring Boot parent is 4.0.7 while README still states Spring Boot 3.x.
2. Tracked bin/ output duplicates project content and contains compiled .class files.
3. .gitignore ignores bin/, but tracked files remain until explicitly removed from Git.
4. docs/roadmap/identity.md was replaced by an ID-021-only checklist instead of merged.
5. README references docs/ARCHITECTURE.md, which is absent.
6. README references docs/architecture/persistence-schema.md, which is absent.
7. README correction/checklist links do not match the current docs/roadmap/fixs/ location.
8. The live repository has 24 business-module roots, but README documents only the
   foundational subset and can be read as if the others are still purely future work.
9. The live main branch has no src/test tree.
10. No .github workflow exists and the current head has no CI status checks.
11. The POM security comment says OAuth2 Resource Server is not included even though
    spring-boot-starter-oauth2-resource-server is declared.
12. Historical stabilization documentation claims tests existed; current main no longer
    contains them, so historical evidence must not be treated as current validation.
```

---

## 2. Non-goals

This roadmap MUST NOT:

```text
mass-refactor the 24 business domains
rename bounded contexts
convert the project to microservices
perform a Maven multi-module split
rewrite domain invariants without module roadmaps
restore obsolete Spring Boot 3 test-slice imports
add identityaccess
replace current date-based Flyway migrations with legacy V010/V020 names
silently mark Maven verification as passed
```

---

## 3. Execution plan

| Code | Commit message | Purpose | Files/actions |
|---|---|---|---|
| STB2-001 | `docs(stabilization): add repository recovery roadmap` | Establish verified recovery plan | `docs/roadmap/fixs/stabilization_02.md` |
| STB2-002 | `chore(repository): remove tracked build artifacts` | Remove tracked `bin/` and harden ignore rules | `.gitignore`, delete tracked `bin/**` |
| STB2-003 | `docs(identity): restore identity roadmap execution memory` | Recover Identity roadmap history and merge current-state record | `docs/roadmap/identity.md` |
| STB2-004 | `docs(architecture): restore architecture indexes and inventories` | Repair missing architecture docs and document module/persistence inventories | `docs/ARCHITECTURE.md`, `docs/architecture/module-catalog.md`, `docs/architecture/persistence-schema.md` |
| STB2-005 | `docs(project): align Spring Boot 4 and repository links` | Align README and POM comments with live build | `README.md`, `pom.xml` |
| STB2-006 | `test(stabilization): restore baseline verification tests` | Restore Boot smoke test and architecture guardrails without Boot test slices | `src/test/java/dz/sh/hidra/HidraApplicationTests.java`, `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java` |
| STB2-007 | `ci: add Maven verification workflow` | Add repeatable Java 21 compile/test/verify CI | `.github/workflows/ci.yml` |
| STB2-008 | `docs(stabilization): finalize repository recovery checklist` | Record prepared state and required final validation | `docs/roadmap/fixs/stabilization_02.md` |

---

## 4. Mandatory validation

After applying all steps to a real clone:

```bash
git status --short
git ls-files bin
find . -name '*.class' -not -path './target/*'
mvn -q -DskipTests compile
mvn -q test
mvn -q clean verify
```

Expected repository hygiene:

```text
git ls-files bin -> no output
no tracked .class files
README build version matches pom.xml
all README local links resolve
src/test exists
architecture guardrail executes
CI workflow exists
```

The Spring context smoke test uses PostgreSQL Testcontainers. A Docker-compatible runtime
is required to execute it; it is skipped when Docker is unavailable.

---

## 5. Execution status

| Code | Status | Validation |
|---|---|---|
| STB2-001 | Prepared artifact | Roadmap generated from live repository audit |
| STB2-002 | Prepared artifact | `.gitignore` corrected; tracked `bin/**` deletion still requires Git apply |
| STB2-003 | Prepared artifact | Identity execution memory recovered into a current-state roadmap |
| STB2-004 | Prepared artifact | Missing architecture index, module catalog, and persistence inventory added |
| STB2-005 | Prepared artifact | README aligned to Spring Boot 4.0.7 and live paths; POM comment corrected |
| STB2-006 | Prepared artifact | Boot/Testcontainers smoke test and ArchUnit layer guardrail added |
| STB2-007 | Prepared artifact | Java 21 Maven CI workflow added |
| STB2-008 | Prepared artifact | Final checklist recorded; full Maven validation still pending on a complete clone |

---

## 6. Stop conditions

Stop and record the exact blocker if:

```text
production compilation fails for reasons outside these files
Flyway/JPA validation reveals schema/model mismatch
the architecture guardrail exposes existing layer violations
Docker is unavailable for the Testcontainers smoke test
the baseline branch changes materially before the artifacts are applied
```

Do not weaken a guardrail simply to obtain a green build. Fix the violating production
code under the appropriate module roadmap.


---

## 7. Final application checklist

```text
[x] Corrective roadmap prepared
[x] Tracked build-output removal action documented
[x] .gitignore hardened against bin/ and .class files
[x] Identity roadmap recovery prepared
[x] Missing architecture index restored
[x] Missing persistence inventory restored
[x] Current module inventory documented
[x] README aligned to Spring Boot 4.0.7
[x] README broken roadmap links corrected
[x] Contradictory OAuth2 POM comment corrected
[x] Baseline src/test files restored
[x] Tests avoid removed/unstable Spring Boot test-slice annotations
[x] ArchUnit guardrail restored
[x] GitHub Actions Maven workflow prepared
[ ] `git rm -r bin` executed in the real repository clone
[ ] `mvn -q -DskipTests compile` passes
[ ] `mvn -q test` passes
[ ] `mvn -q clean verify` passes
[ ] GitHub Actions is green
```

### Validation limitation

A literal clone could not be created in the artifact runtime because that runtime could
not resolve `github.com`. The files were prepared from the connected live GitHub
repository at the baseline SHA shown above. Therefore compile/test/verify results are
intentionally **not** marked as passed.

If the target `main` branch advances before application, rebase/review these artifacts
against the new head before committing.
