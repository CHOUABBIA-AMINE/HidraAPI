# Identity Roadmap — ID-021 Final Checklist Update

> Merge note: this file contains the ID-021 final checklist section for
> `docs/roadmap/identity.md`.
>
> If the repository already contains a full `docs/roadmap/identity.md`, append or merge
> the section below into that file rather than replacing unrelated roadmap content.

---

## ID-021 — Finalize Identity Checklist

### Commit message

```text
docs(identity): finalize identity checklist
```

### Execution status

```text
Status: Pending repository validation
```

### Scope

This step records the final execution status for the identity module after applying
ID-002 through ID-020.

The checklist below must be checked only after the identity files are present in the
repository and the validation commands have been executed on the target branch.

### Final checklist

```text
[ ] Identity package structure exists
[ ] No identityaccess package exists
[ ] Identity domain has no Spring dependency
[ ] Identity domain has no JPA dependency
[ ] Identity application has no API dependency
[ ] Identity application has no infrastructure dependency
[ ] Identity API has no repository dependency
[ ] User aggregate exists
[ ] Role aggregate exists
[ ] Permission model exists
[ ] Permission code policy exists
[ ] Permission evaluation works
[ ] Role assignment policy works
[ ] REST API compiles
[ ] Persistence migration exists
[ ] Domain tests pass
[ ] Application tests pass
[ ] API tests pass
[ ] Persistence tests pass or are blocked with exact reason
[ ] Architecture guardrail passes or is blocked with exact reason
[ ] mvn -q clean verify passes or unrelated blocker is recorded
```

### Validation commands

```bash
mvn -q -DskipTests compile
mvn -q test
mvn -q clean verify
```

### Validation result

```text
Not executed in this generated ZIP.
Run the validation commands after applying ID-002 through ID-020 to the repository.
```

### Persistence test status

```text
Pending.
If database/Testcontainers is not configured, record the exact failure message from:
mvn -q test -Dtest='*RepositoryAdapterTest'
```

### Architecture guardrail status

```text
Pending.
ArchUnit is available in the project POM, so IdentityArchitectureTest should be runnable
after ID-020 is applied. Record the exact failure if:
mvn -q test -Dtest=IdentityArchitectureTest
does not pass.
```

### Final verification notes

Before checking the boxes, verify:

```bash
find src/main/java/dz/sh/hidra/modules/identity -type f | sort
find src/test/java/dz/sh/hidra/modules/identity -type f | sort
find src/main/java -path '*identityaccess*' -o -path '*identityAccess*'
grep -R "dz.sh.hidra.modules.organization.domain" src/main/java/dz/sh/hidra/modules/identity src/test/java/dz/sh/hidra/modules/identity || true
grep -R "SecurityFilterChain" src/main/java/dz/sh/hidra/modules/identity || true
grep -R "JpaRepository" src/main/java/dz/sh/hidra/modules/identity/api src/main/java/dz/sh/hidra/modules/identity/application src/main/java/dz/sh/hidra/modules/identity/domain || true
```

### Completion rule

Mark ID-021 as completed only when:

```text
- ID-002 through ID-020 have been applied.
- The final checklist has been reviewed.
- Validation commands have passed, or exact unrelated blockers are recorded.
- The roadmap status table is updated.
```
