from pathlib import Path

path = Path("docs/roadmap/authentication.md")
text = path.read_text(encoding="utf-8")

if "| Status | Active — AUTH-005 authentication provider manager composed |" in text:
    raise SystemExit(0)

replacements = {
    "| Status | Active — AUTH-004 authentication request router completed |":
        "| Status | Active — AUTH-005 authentication provider manager composed |",
    "| AUTH-005 | `feat(authentication): compose authentication provider manager` | Register provider-specific strategies behind one AuthenticationManager/ProviderManager with no fallback | Planned |":
        "| AUTH-005 | `feat(authentication): compose authentication provider manager` | Register provider-specific strategies behind one AuthenticationManager/ProviderManager with no fallback | Completed — central fail-closed ProviderManager composed for provider-specific LOCAL and LDAP request types; `mvn -q test` passed in PR CI run 104 |",
    """### AUTH-005 — ProviderManager composition

Commit:

```text
feat(authentication): compose authentication provider manager
```

Register the supported provider strategies behind one `AuthenticationManager`/`ProviderManager`.

Each provider must support only the intended Authentication request type.

Do not use provider ordering as the primary provider discriminator.

Validation:

```bash
mvn -q test
```

---""":
        """### AUTH-005 — ProviderManager composition

Commit:

```text
feat(authentication): compose authentication provider manager
```

Register the supported provider strategies behind one `AuthenticationManager`/`ProviderManager`.

Each provider must support only the intended Authentication request type.

Do not use provider ordering as the primary provider discriminator.

Status:

```text
Completed — HidraAuthenticationManagerConfiguration now exposes one ProviderManager-backed AuthenticationManager for direct provider authentication. It registers only AuthenticationProvider beans that support exactly one of LocalAuthenticationToken or LdapAuthenticationToken, rejects a provider that claims both request types, and uses a fail-closed parent AuthenticationManager when no matching provider is installed. Existing bootstrap Basic authentication and the external OIDC/JWT path are preserved; no LOCAL, LDAP, or OIDC provider implementation was introduced early.
```

Validation:

```bash
mvn -q test
```

Result:

```text
PASS — pull-request CI run 104 completed the repository test check and the acceptance `mvn -q test` step successfully for AUTH-005 implementation commit 00cc471a916fea8882ed25f5d2c6297155899b2b.
```

---""",
    """## 22. Next task

Execute only:

```text
AUTH-005 — feat(authentication): compose authentication provider manager
```

AUTH-004 now converts explicit provider selections deterministically into the intended request path. Do not implement AUTH-006 or later tasks during AUTH-005.""":
        """## 22. Next task

Execute only:

```text
AUTH-006 — feat(identity): normalize authenticated hidra principal
```

AUTH-005 now provides the central fail-closed AuthenticationManager/ProviderManager composition for provider-specific request tokens. Do not implement AUTH-007 or later tasks during AUTH-006."""
}

for old, new in replacements.items():
    if old not in text:
        raise SystemExit(f"Expected roadmap fragment not found: {old[:120]!r}")
    text = text.replace(old, new, 1)

path.write_text(text, encoding="utf-8")
