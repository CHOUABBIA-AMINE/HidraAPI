from pathlib import Path

path = Path("docs/roadmap/authentication.md")
text = path.read_text(encoding="utf-8")

if "| Status | Active — AUTH-007 OIDC authentication normalized |" in text:
    raise SystemExit(0)

replacements = {
    "| Status | Active — AUTH-006 normalized Hidra principal completed |":
        "| Status | Active — AUTH-007 OIDC authentication normalized |",
    "| AUTH-007 | `refactor(authentication): normalize existing oidc authentication` | Preserve working OIDC flow while mapping successful identities to Hidra User/HidraPrincipal and Hidra authorization | Planned |":
        "| AUTH-007 | `refactor(authentication): normalize existing oidc authentication` | Preserve working OIDC flow while mapping successful identities to Hidra User/HidraPrincipal and Hidra authorization | Completed — validated JWT issuer+subject now resolves through active OIDC provider and linked ExternalIdentity to active Hidra User/HidraPrincipal with Hidra-owned effective permissions; `mvn -q test` passed in PR CI run 118 |",
    """### AUTH-007 — Normalize existing OIDC authentication

Commit:

```text
refactor(authentication): normalize existing oidc authentication
```

Preserve current browser OIDC authorization-code + PKCE behavior and HidraAPI resource-server validation.

Add only missing mapping from validated external OIDC identity to:

```text
IdentityProvider / ExternalIdentity
Hidra User
Hidra account state
Hidra authorization
HidraPrincipal
```

Do not collect external provider passwords and do not replace working OIDC configuration unnecessarily.

Validation:

```bash
mvn -q test
```

---""":
        """### AUTH-007 — Normalize existing OIDC authentication

Commit:

```text
refactor(authentication): normalize existing oidc authentication
```

Preserve current browser OIDC authorization-code + PKCE behavior and HidraAPI resource-server validation.

Add only missing mapping from validated external OIDC identity to:

```text
IdentityProvider / ExternalIdentity
Hidra User
Hidra account state
Hidra authorization
HidraPrincipal
```

Do not collect external provider passwords and do not replace working OIDC configuration unnecessarily.

Status:

```text
Completed — the existing resource-server JWT validation remains intact, but its post-validation converter is now Identity-owned. A validated JWT is normalized by issuer + subject through an ACTIVE OIDC IdentityProvider and LINKED ExternalIdentity to an ACTIVE, unlocked Hidra User. The resulting Spring Authentication carries HidraPrincipal with stable Hidra user ID and Hidra-owned effective permissions. External IdP roles/claims are not promoted into Hidra business authorization. Existing browser authorization-code + PKCE behavior and GET /api/v1/security/oidc are unchanged.
```

Validation:

```bash
mvn -q test
```

Result:

```text
PASS — pull-request CI run 118 completed the repository test check and the acceptance `mvn -q test` step successfully for AUTH-007 implementation commit 93cd247d83e9ed519554df69b6835ab8d1dfe622.
```

---""",
    """## 22. Next task

Execute only:

```text
AUTH-007 — refactor(authentication): normalize existing oidc authentication
```

AUTH-006 now provides the normalized Identity-owned HidraPrincipal contract shared by authentication providers. Do not implement AUTH-008 or later tasks during AUTH-007.""":
        """## 22. Next task

Execute only:

```text
AUTH-008 — feat(identity): add local credential model and port
```

AUTH-007 now normalizes the existing OIDC resource-server authentication into HidraPrincipal without changing the browser PKCE flow. Do not implement AUTH-009 or later tasks during AUTH-008."""
}

for old, new in replacements.items():
    if old not in text:
        raise SystemExit(f"Expected roadmap fragment not found: {old[:120]!r}")
    text = text.replace(old, new, 1)

path.write_text(text, encoding="utf-8")
