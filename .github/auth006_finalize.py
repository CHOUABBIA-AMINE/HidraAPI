from pathlib import Path

path = Path("docs/roadmap/authentication.md")
text = path.read_text(encoding="utf-8")

if "| Status | Active — AUTH-006 normalized Hidra principal completed |" in text:
    raise SystemExit(0)

replacements = {
    "| Status | Active — AUTH-005 authentication provider manager composed |":
        "| Status | Active — AUTH-006 normalized Hidra principal completed |",
    "| AUTH-006 | `feat(identity): normalize authenticated hidra principal` | Reuse/add one Hidra principal contract shared by LOCAL, LDAP/AD, and OIDC | Planned |":
        "| AUTH-006 | `feat(identity): normalize authenticated hidra principal` | Reuse/add one Hidra principal contract shared by LOCAL, LDAP/AD, and OIDC | Completed — Identity-owned HidraPrincipal added with stable Hidra user identity, provider source, optional provider linkage, and Hidra roles/permissions; `mvn -q test` passed in PR CI run 111 |",
    """### AUTH-006 — Normalized Hidra principal

Commit:

```text
feat(identity): normalize authenticated hidra principal
```

Preserve the existing technical `AuthenticatedPrincipal`/current-security-context adapter, but add the minimum Identity-owned normalized authenticated principal required to carry stable Hidra identity and authorization information across LOCAL, LDAP/AD and OIDC.

No provider-specific business model duplication and no transfer of User/Role/Permission ownership into platform.

Validation:

```bash
mvn -q test
```

---""":
        """### AUTH-006 — Normalized Hidra principal

Commit:

```text
feat(identity): normalize authenticated hidra principal
```

Preserve the existing technical `AuthenticatedPrincipal`/current-security-context adapter, but add the minimum Identity-owned normalized authenticated principal required to carry stable Hidra identity and authorization information across LOCAL, LDAP/AD and OIDC.

No provider-specific business model duplication and no transfer of User/Role/Permission ownership into platform.

Status:

```text
Completed — HidraPrincipal is an Identity-domain record that implements java.security.Principal without Spring dependencies. It carries stable Hidra user ID, Hidra username/display name, ProviderType, optional IdentityProvider linkage, and immutable Hidra-owned role/permission sets. Principal.getName() returns the stable Hidra user ID. The existing platform AuthenticatedPrincipal and current-security-context adapters remain unchanged.
```

Validation:

```bash
mvn -q test
```

Result:

```text
PASS — pull-request CI run 111 completed the repository test check and the acceptance `mvn -q test` step successfully for AUTH-006 implementation commit 17da3f61c338621bdd9fd641442877241abd239a.
```

---""",
    """## 22. Next task

Execute only:

```text
AUTH-006 — feat(identity): normalize authenticated hidra principal
```

AUTH-005 now provides the central fail-closed AuthenticationManager/ProviderManager composition for provider-specific request tokens. Do not implement AUTH-007 or later tasks during AUTH-006.""":
        """## 22. Next task

Execute only:

```text
AUTH-007 — refactor(authentication): normalize existing oidc authentication
```

AUTH-006 now provides the normalized Identity-owned HidraPrincipal contract shared by authentication providers. Do not implement AUTH-008 or later tasks during AUTH-007."""
}

for old, new in replacements.items():
    if old not in text:
        raise SystemExit(f"Expected roadmap fragment not found: {old[:120]!r}")
    text = text.replace(old, new, 1)

path.write_text(text, encoding="utf-8")
