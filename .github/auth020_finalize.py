from pathlib import Path

path = Path("docs/roadmap/authentication.md")
text = path.read_text(encoding="utf-8")

replacements = {
    "| Status | Active — AUTH-019 protected API bearer authentication standardized |":
        "| Status | Active — AUTH-020 ordinary in-memory LOCAL authentication retired |",
    "| AUTH-020 | `refactor(security): retire ordinary in-memory local authentication` | Remove InMemoryUserDetailsManager as ordinary LOCAL login only after DB path is proven | Planned |":
        "| AUTH-020 | `refactor(security): retire ordinary in-memory local authentication` | Remove InMemoryUserDetailsManager as ordinary LOCAL login only after DB path is proven | Completed — development and test now default to the unified Hidra JWT path instead of server-wide Basic authentication; InMemoryUserDetailsManager remains only behind explicit `HIDRA_SECURITY_AUTHENTICATION_MODE=basic` selection as temporary emergency/bootstrap compatibility until AUTH-021; `mvn -q clean verify` passed in PR CI run 220 |",
    """Precondition: persistent LOCAL provider, unified login endpoint, and JWT path are proven.\n\nRemove `InMemoryUserDetailsManager` as an ordinary user-login authority. Do not remove emergency/bootstrap capability until AUTH-021 exists.\n\nValidation:\n\n```bash\nmvn -q clean verify\n```\n\n---\n\n### AUTH-021 — Safe LOCAL administrator bootstrap""":
        """Precondition: persistent LOCAL provider, unified login endpoint, and JWT path are proven.\n\nRemove `InMemoryUserDetailsManager` as an ordinary user-login authority. Do not remove emergency/bootstrap capability until AUTH-021 exists.\n\nStatus:\n\n```text\nCompleted — ordinary development and test startup no longer selects the Basic/InMemoryUserDetailsManager path. Both profiles now default to the standardized Hidra JWT resource-server flow, matching the proven protected-API contract from AUTH-019. The existing Basic/in-memory bootstrap mechanism was intentionally retained only when HIDRA_SECURITY_AUTHENTICATION_MODE=basic is explicitly selected, preserving emergency/bootstrap compatibility until AUTH-021 replaces it with persistent administrator provisioning. No persistent bootstrap user/credential provisioning or AUTH-021 behavior was introduced.\n```\n\nValidation:\n\n```bash\nmvn -q clean verify\n```\n\nResult:\n\n```text\nPASS — PR CI run 220 completed repository and acceptance clean verification successfully for AUTH-020 implementation commit cce77bfd8e7dc518258d7606fa6617b68c77be06.\n```\n\n---\n\n### AUTH-021 — Safe LOCAL administrator bootstrap""",
    """AUTH-020 — refactor(security): retire ordinary in-memory local authentication\n```\n\nAUTH-019 now isolates external OIDC bearer validation to the completion bridge and requires standardized Hidra-issued JWTs for ordinary protected APIs while preserving existing authorization semantics. Do not implement AUTH-021 or later tasks during AUTH-020.""":
        """AUTH-021 — feat(authentication): add safe local administrator bootstrap\n```\n\nAUTH-020 now defaults development and test to the unified Hidra JWT path, leaving in-memory Basic only as explicit temporary emergency/bootstrap compatibility. Do not implement AUTH-022 or later tasks during AUTH-021."""
}

for old, new in replacements.items():
    if old not in text:
        raise SystemExit(f"Expected roadmap fragment not found: {old[:180]!r}")
    text = text.replace(old, new, 1)

path.write_text(text, encoding="utf-8")
