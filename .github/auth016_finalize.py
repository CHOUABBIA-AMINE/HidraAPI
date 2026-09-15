from pathlib import Path

path = Path("docs/roadmap/authentication.md")
text = path.read_text(encoding="utf-8")

replacements = {
    "| Status | Active — AUTH-015 unified Hidra access-token issuer completed |":
        "| Status | Active — AUTH-016 authentication session lifecycle completed |",
    "| AUTH-016 | `feat(identity): complete authentication session lifecycle` | Reuse LoginSession and AuthenticationEvent for all provider paths | Planned |":
        "| AUTH-016 | `feat(identity): complete authentication session lifecycle` | Reuse LoginSession and AuthenticationEvent for all provider paths | Completed — provider-neutral AuthenticationSessionLifecycleApplicationService now starts, touches, expires, revokes, and logs out LoginSession records without storing raw JWTs; non-LOCAL successful sessions record LOGIN_SUCCESS, LOCAL success remains with AUTH-011 to avoid duplicates, and logout records provider-correct AuthenticationEvent metadata; `mvn -q test` passed in PR CI run 181 |",
    """Reuse `LoginSession` and `AuthenticationEvent` across all provider paths.\n\nDo not store raw JWTs.\n\nValidation:\n\n```bash\nmvn -q test\n```\n\n---\n\n### AUTH-017 — Dynamic login endpoint""":
        """Reuse `LoginSession` and `AuthenticationEvent` across all provider paths.\n\nDo not store raw JWTs.\n\nStatus:\n\n```text\nCompleted — AuthenticationSessionLifecycleApplicationService provides provider-neutral logical session start, touch/automatic expiry, explicit expiry, revocation, and logout using the existing LoginSession repository contract. Session persistence stores only lifecycle/client/correlation/provider metadata and never accepts or stores raw JWT values. LDAP/AD/OIDC normalized session starts record LOGIN_SUCCESS through the existing AuthenticationEvent repository; LOCAL success remains recorded by LocalAuthenticationOutcomeApplicationService from AUTH-011 to avoid duplicate LOGIN_SUCCESS events. Logout records a LOGOUT event with provider-derived AuthenticationProtocol. No dynamic login endpoint, token response orchestration, provider routing, or AUTH-017 behavior was introduced.\n```\n\nValidation:\n\n```bash\nmvn -q test\n```\n\nResult:\n\n```text\nPASS — pull-request CI run 181 completed repository and acceptance `mvn -q test` checks successfully for AUTH-016 implementation commit fb39522e817ddfd9ea2aa94ba1c525ed7f5f7ecc.\n```\n\n---\n\n### AUTH-017 — Dynamic login endpoint""",
    """AUTH-016 — feat(identity): complete authentication session lifecycle\n```\n\nAUTH-015 now provides one Hidra-issued access-token schema from normalized HidraPrincipal with externalized signing material and HMAC resource-server compatibility. Do not implement AUTH-017 or later tasks during AUTH-016.""":
        """AUTH-017 — feat(authentication): wire dynamic login endpoint\n```\n\nAUTH-016 now provides provider-neutral LoginSession lifecycle and AuthenticationEvent recording without raw-token persistence. Do not implement AUTH-018 or later tasks during AUTH-017."""
}

for old, new in replacements.items():
    if old not in text:
        raise SystemExit(f"Expected roadmap fragment not found: {old[:180]!r}")
    text = text.replace(old, new, 1)

path.write_text(text, encoding="utf-8")
