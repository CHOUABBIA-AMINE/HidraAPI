from pathlib import Path

path = Path("docs/roadmap/authentication.md")
text = path.read_text(encoding="utf-8")

replacements = {
    "| Status | Active — AUTH-020 ordinary in-memory LOCAL authentication retired |":
        "| Status | Active — AUTH-021 safe persistent LOCAL administrator bootstrap completed |",
    "| AUTH-021 | `feat(authentication): add safe local administrator bootstrap` | Provide controlled persistent LOCAL administrator provisioning without permanent in-memory fallback | Planned |":
        "| AUTH-021 | `feat(authentication): add safe local administrator bootstrap` | Provide controlled persistent LOCAL administrator provisioning without permanent in-memory fallback | Completed — explicitly enabled bootstrap now provisions a persistent ACTIVE HUMAN User, BCrypt-backed ACTIVE LocalCredential, active HIDRA_ADMIN role/global grant, and append-only audit event; fully provisioned reruns are idempotent while pre-existing or incomplete identity state fails closed without credential overwrite; bootstrap password has no repository default and the temporary Basic/InMemoryUserDetailsManager authority is removed; `mvn -q test` passed in PR CI run 229 |",
    """Requirements:\n\n```text\none-time/idempotent provisioning\npersistent normal Identity User + LOCAL credential\nno production default password\nsecret provided externally\ncannot silently overwrite existing credential\nfully auditable\n```\n\nValidation:\n\n```bash\nmvn -q test\n```\n\n---\n\n### AUTH-022 through AUTH-027 — Security proof tasks""":
        """Requirements:\n\n```text\none-time/idempotent provisioning\npersistent normal Identity User + LOCAL credential\nno production default password\nsecret provided externally\ncannot silently overwrite existing credential\nfully auditable\n```\n\nStatus:\n\n```text\nCompleted — LocalAdministratorBootstrapApplicationService provisions a normal ACTIVE HUMAN Identity User, an ACTIVE BCrypt-hashed LocalCredential, and a global ACTIVE HIDRA_ADMIN UserRoleGrant inside a transaction. The HIDRA_ADMIN role is reused when active or created when absent. A rerun is a no-op only when the same username is already an ACTIVE user with an ACTIVE LOCAL credential and active administrator grant; any other pre-existing/incomplete state is rejected and never repaired, reset, or escalated silently. LocalAdministratorBootstrapRunner is disabled by default, requires an externally supplied nonblank password when enabled, and records successful creation through the existing append-only Audit use case in the same startup transaction without recording plaintext credentials. The temporary Basic/InMemoryUserDetailsManager authority retained through AUTH-020 is removed; JWT/direct LOCAL authentication remains the normal runtime path.\n```\n\nValidation:\n\n```bash\nmvn -q test\n```\n\nResult:\n\n```text\nPASS — PR CI run 229 completed repository and acceptance mvn -q test successfully for AUTH-021 corrected implementation commit 91e49d0ac13227d1075278616b1273c21c2d373e. Earlier run 227 correctly blocked final transactional classes that could not be proxied; both transactional classes were made proxy-compatible before validation passed.\n```\n\n---\n\n### AUTH-022 through AUTH-027 — Security proof tasks""",
    """AUTH-021 — feat(authentication): add safe local administrator bootstrap\n```\n\nAUTH-020 now defaults development and test to the unified Hidra JWT path, leaving in-memory Basic only as explicit temporary emergency/bootstrap compatibility. Do not implement AUTH-022 or later tasks during AUTH-021.""":
        """AUTH-022 — test(authentication): cover dynamic provider routing\n```\n\nAUTH-021 now provides explicit, persistent, auditable LOCAL administrator bootstrap with no default password or in-memory fallback. Do not implement AUTH-023 or later tasks during AUTH-022."""
}

for old, new in replacements.items():
    if old not in text:
        raise SystemExit(f"Expected roadmap fragment not found: {old[:180]!r}")
    text = text.replace(old, new, 1)

path.write_text(text, encoding="utf-8")
