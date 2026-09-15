from pathlib import Path

path = Path("docs/roadmap/authentication.md")
text = path.read_text(encoding="utf-8")

if "| Status | Active — AUTH-008 LOCAL credential contract completed |" in text:
    raise SystemExit(0)

replacements = {
    "| Status | Active — AUTH-007 OIDC authentication normalized |":
        "| Status | Active — AUTH-008 LOCAL credential contract completed |",
    "| AUTH-008 | `feat(identity): add local credential model and port` | Add the proven-missing LOCAL credential domain/application contract | Planned |":
        "| AUTH-008 | `feat(identity): add local credential model and port` | Add the proven-missing LOCAL credential domain/application contract | Completed — Identity-owned LocalCredential and LocalCredentialRepositoryPort added without Spring PasswordEncoder/domain leakage or persistence implementation; `mvn -q -DskipTests compile` passed in PR CI run 125 |",
    """### AUTH-008 — LOCAL credential model and port

Commit:

```text
feat(identity): add local credential model and port
```

AUTH-002 proved no equivalent persistent credential model exists.

Do not put password hashes on ordinary User DTOs or expose Spring PasswordEncoder in the domain.

Validation:

```bash
mvn -q -DskipTests compile
```

---""":
        """### AUTH-008 — LOCAL credential model and port

Commit:

```text
feat(identity): add local credential model and port
```

AUTH-002 proved no equivalent persistent credential model exists.

Do not put password hashes on ordinary User DTOs or expose Spring PasswordEncoder in the domain.

Status:

```text
Completed — LocalCredential is an Identity-domain record for the one-way password hash and credential metadata (id, stable Hidra user ID, credential status, password-change/create/update timestamps). LocalCredentialRepositoryPort is an application outbound port exposing save, findById, and findByUserId without Spring Data or PasswordEncoder dependencies. No plaintext password, persistence adapter, migration, authentication provider, or login API was introduced.
```

Validation:

```bash
mvn -q -DskipTests compile
```

Result:

```text
PASS — pull-request CI run 125 completed the acceptance `mvn -q -DskipTests compile` step successfully for AUTH-008 implementation commit 26585e8be08f43acd8dc8504421ae0b0fd1ff898.
```

---""",
    """## 22. Next task

Execute only:

```text
AUTH-008 — feat(identity): add local credential model and port
```

AUTH-007 now normalizes the existing OIDC resource-server authentication into HidraPrincipal without changing the browser PKCE flow. Do not implement AUTH-009 or later tasks during AUTH-008.""":
        """## 22. Next task

Execute only:

```text
AUTH-009 — feat(identity): add local credential persistence
```

AUTH-008 now defines the LOCAL credential domain/application contract. Do not implement AUTH-010 or later tasks during AUTH-009."""
}

for old, new in replacements.items():
    if old not in text:
        raise SystemExit(f"Expected roadmap fragment not found: {old[:120]!r}")
    text = text.replace(old, new, 1)

path.write_text(text, encoding="utf-8")
