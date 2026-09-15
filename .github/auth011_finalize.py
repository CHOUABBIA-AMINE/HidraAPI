from pathlib import Path

path = Path("docs/roadmap/authentication.md")
text = path.read_text(encoding="utf-8")

if "| Status | Active — AUTH-011 LOCAL authentication outcomes completed |" in text:
    raise SystemExit(0)

replacements = {
    "| Status | Active — AUTH-010 database-backed LOCAL provider completed |":
        "| Status | Active — AUTH-011 LOCAL authentication outcomes completed |",
    "| AUTH-011 | `feat(identity): record local authentication outcomes` | Apply existing User lock/login state and AuthenticationEvent to LOCAL success/failure | Planned |":
        "| AUTH-011 | `feat(identity): record local authentication outcomes` | Apply existing User lock/login state and AuthenticationEvent to LOCAL success/failure | Completed — LOCAL success resets failed-login count, updates last-authenticated state, and records LOGIN_SUCCESS; failures for resolved users increment failed-login count while preserving existing lock state and record sanitized LOGIN_FAILED events; submitted passwords are never recorded; `mvn -q test` passed in PR CI run 146 |",
    """Reuse existing User login counters/lock state and AuthenticationEvent.\n\nNever record the submitted password.\n\nValidation:\n\n```bash\nmvn -q test\n```\n\n---\n\n### AUTH-012 — LDAP security infrastructure""":
        """Reuse existing User login counters/lock state and AuthenticationEvent.\n\nNever record the submitted password.\n\nStatus:\n\n```text\nCompleted — LOCAL authentication outcomes are recorded through an Identity application service in independent transactions. Success resets failedLoginCount, updates lastAuthenticatedAt, preserves existing account/lock state, and records LOGIN_SUCCESS with AuthenticationProtocol.LOCAL. Failures against a resolved user increment failedLoginCount without inventing a new lockout threshold or duration, preserve existing lockedUntil/status, and record LOGIN_FAILED with sanitized reason codes. The submitted password is never passed to or persisted by the outcome recorder.\n```\n\nValidation:\n\n```bash\nmvn -q test\n```\n\nResult:\n\n```text\nPASS — pull-request CI run 146 completed repository and acceptance `mvn -q test` checks successfully for AUTH-011 implementation commit 1918492f9d6ba894571b1c1c403c5dbadf39b924.\n```\n\n---\n\n### AUTH-012 — LDAP security infrastructure""",
    """AUTH-011 — feat(identity): record local authentication outcomes\n```\n\nAUTH-010 now provides database-backed LOCAL credential verification and HidraPrincipal normalization. Do not implement AUTH-012 or later tasks during AUTH-011.""":
        """AUTH-012 — feat(authentication): add ldap security infrastructure\n```\n\nAUTH-011 now records LOCAL success/failure state and AuthenticationEvent outcomes without storing submitted passwords. Do not implement AUTH-013 or later tasks during AUTH-012."""
}

for old, new in replacements.items():
    if old not in text:
        raise SystemExit(f"Expected roadmap fragment not found: {old[:160]!r}")
    text = text.replace(old, new, 1)

path.write_text(text, encoding="utf-8")
