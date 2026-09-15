from pathlib import Path

path = Path("docs/roadmap/authentication.md")
text = path.read_text(encoding="utf-8")

if "| Status | Active — AUTH-010 database-backed LOCAL provider completed |" in text:
    raise SystemExit(0)

replacements = {
    "| Status | Active — AUTH-009 LOCAL credential persistence completed |":
        "| Status | Active — AUTH-010 database-backed LOCAL provider completed |",
    "| AUTH-010 | `feat(authentication): add database local authentication provider` | Replace ordinary in-memory LOCAL verification with persistent password verification behind LocalAuthenticationProvider | Planned |":
        "| AUTH-010 | `feat(authentication): add database local authentication provider` | Replace ordinary in-memory LOCAL verification with persistent password verification behind LocalAuthenticationProvider | Completed — LOCAL requests now resolve the active LOCAL IdentityProvider and persisted Hidra User/LocalCredential, enforce account/credential state, verify via PasswordEncoder, and return HidraPrincipal without in-memory fallback or token issuance; `mvn -q test` passed in PR CI run 138 |",
    """Requirements:\n\n```text\nresolve existing Hidra User/provider binding\nverify account state\nload LOCAL credential\nPasswordEncoder.matches(...)\nreturn normalized HidraPrincipal\nnever query in-memory bootstrap on failed ordinary LOCAL credentials\nno token issuance inside provider\n```\n\nValidation:\n\n```bash\nmvn -q test\n```\n\n---\n\n### AUTH-011 — LOCAL authentication outcomes""":
        """Requirements:\n\n```text\nresolve existing Hidra User/provider binding\nverify account state\nload LOCAL credential\nPasswordEncoder.matches(...)\nreturn normalized HidraPrincipal\nnever query in-memory bootstrap on failed ordinary LOCAL credentials\nno token issuance inside provider\n```\n\nStatus:\n\n```text\nCompleted — LocalAuthenticationProvider handles only LocalAuthenticationToken, resolves one ACTIVE LOCAL IdentityProvider plus the persisted Hidra User and LOCAL credential, rejects locked/non-active users and non-active credentials, verifies the submitted password using the existing PasswordEncoder, resolves Hidra-owned effective permissions, and returns an authenticated HidraPrincipal. Failed ordinary LOCAL credentials never consult InMemoryUserDetailsManager, and the provider does not issue tokens or record AUTH-011 login outcomes.\n```\n\nValidation:\n\n```bash\nmvn -q test\n```\n\nResult:\n\n```text\nPASS — pull-request CI run 138 completed repository and acceptance `mvn -q test` checks successfully for AUTH-010 implementation commit b659f11ff30fbb3ddf3971b51ea31b4ba834b73f.\n```\n\n---\n\n### AUTH-011 — LOCAL authentication outcomes""",
    """AUTH-010 — feat(authentication): add database local authentication provider\n```\n\nAUTH-009 now provides persistent LOCAL credential storage behind the Identity application port. Do not implement AUTH-011 or later tasks during AUTH-010.""":
        """AUTH-011 — feat(identity): record local authentication outcomes\n```\n\nAUTH-010 now provides database-backed LOCAL credential verification and HidraPrincipal normalization. Do not implement AUTH-012 or later tasks during AUTH-011."""
}

for old, new in replacements.items():
    if old not in text:
        raise SystemExit(f"Expected roadmap fragment not found: {old[:160]!r}")
    text = text.replace(old, new, 1)

path.write_text(text, encoding="utf-8")
