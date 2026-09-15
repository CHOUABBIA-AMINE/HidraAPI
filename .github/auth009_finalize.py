from pathlib import Path

path = Path("docs/roadmap/authentication.md")
text = path.read_text(encoding="utf-8")

if "| Status | Active — AUTH-009 LOCAL credential persistence completed |" in text:
    raise SystemExit(0)

replacements = {
    "| Status | Active — AUTH-008 LOCAL credential contract completed |":
        "| Status | Active — AUTH-009 LOCAL credential persistence completed |",
    "| AUTH-009 | `feat(identity): add local credential persistence` | Add forward Flyway migration plus JPA repository/adapter for LOCAL password hashes | Planned |":
        "| AUTH-009 | `feat(identity): add local credential persistence` | Add forward Flyway migration plus JPA repository/adapter for LOCAL password hashes | Completed — forward-only PostgreSQL migration plus LocalCredential JPA entity, Spring Data repository, and application-port adapter added; `mvn -q test` passed in PR CI run 131 |",
    """### AUTH-009 — LOCAL credential persistence

Commit:

```text
feat(identity): add local credential persistence
```

Requirements:

```text
forward-only Flyway migration
PostgreSQL-compatible schema
JPA entity/repository/adapter aligned with Identity infrastructure style
no modification of released migrations
no hash exposure/logging
```

Validation:

```bash
mvn -q test
```

---""":
        """### AUTH-009 — LOCAL credential persistence

Commit:

```text
feat(identity): add local credential persistence
```

Requirements:

```text
forward-only Flyway migration
PostgreSQL-compatible schema
JPA entity/repository/adapter aligned with Identity infrastructure style
no modification of released migrations
no hash exposure/logging
```

Status:

```text
Completed — added forward-only V20260915_001 migration for hidra_identity_local_credential with one credential row per Hidra user, password hash storage, credential lifecycle metadata, user foreign key, and supporting indexes. Added LocalCredentialJpaEntity, LocalCredentialJpaRepository, and JpaLocalCredentialRepositoryAdapter implementing the existing LocalCredentialRepositoryPort. No released migration was modified, no plaintext password handling was added, and AUTH-010 authentication-provider behavior was not implemented.
```

Validation:

```bash
mvn -q test
```

Result:

```text
PASS — pull-request CI run 131 completed both repository and acceptance test checks successfully for AUTH-009 implementation commit 3e9bfa1a539fc545c8f75133978f023d8c01f743.
```

---""",
    """AUTH-009 — feat(identity): add local credential persistence
```

AUTH-008 now defines the LOCAL credential domain/application contract. Do not implement AUTH-010 or later tasks during AUTH-009.""":
        """AUTH-010 — feat(authentication): add database local authentication provider
```

AUTH-009 now provides persistent LOCAL credential storage behind the Identity application port. Do not implement AUTH-011 or later tasks during AUTH-010."""
}

for old, new in replacements.items():
    if old not in text:
        raise SystemExit(f"Expected roadmap fragment not found: {old[:160]!r}")
    text = text.replace(old, new, 1)

path.write_text(text, encoding="utf-8")
