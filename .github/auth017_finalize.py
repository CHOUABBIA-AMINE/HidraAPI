from pathlib import Path

path = Path("docs/roadmap/authentication.md")
text = path.read_text(encoding="utf-8")

replacements = {
    "| Status | Active — AUTH-016 authentication session lifecycle completed |":
        "| Status | Active — AUTH-017 dynamic direct-login endpoint completed |",
    "| AUTH-017 | `feat(authentication): wire dynamic login endpoint` | Introduce and wire the direct LOCAL/LDAP provider-selection login boundary to router, AuthenticationManager, session, and token issuer | Planned |":
        "| AUTH-017 | `feat(authentication): wire dynamic login endpoint` | Introduce and wire the direct LOCAL/LDAP provider-selection login boundary to router, AuthenticationManager, session, and token issuer | Completed — POST /api/v1/identity/authentication/login now accepts explicit LOCAL/LDAP/ACTIVE_DIRECTORY provider selection, routes through application ports to the existing request router and fail-closed AuthenticationManager, creates the AUTH-016 logical session, issues the AUTH-015 Hidra bearer JWT, and returns safe normalized principal/session/token metadata; API-to-infrastructure coupling found by CI run 188 was corrected with hexagonal ports/adapters; `mvn -q test` passed in PR CI run 197 |",
    """It must not:\n\n```text\nquery JPA repositories directly\nperform LDAP binds directly\ncompare password hashes directly\nassign roles\nimplement provider fallback\n```\n\nValidation:\n\n```bash\nmvn -q test\n```\n\n---\n\n### AUTH-018 — OIDC completion to Hidra token""":
        """It must not:\n\n```text\nquery JPA repositories directly\nperform LDAP binds directly\ncompare password hashes directly\nassign roles\nimplement provider fallback\n```\n\nStatus:\n\n```text\nCompleted — the canonical direct-login boundary is POST /api/v1/identity/authentication/login. AuthenticationLoginRequest requires explicit ProviderType, principal, and credentials; validation rejects missing values and the existing router rejects OIDC/unsupported direct providers without fallback. The API depends only on AuthenticateDirectUserUseCase. DirectAuthenticationApplicationService orchestrates DirectAuthenticationPort, AccessTokenIssuerPort, and the existing AuthenticationSessionLifecycleApplicationService; SpringDirectAuthenticationAdapter owns the Spring Security router/AuthenticationManager bridge, while HidraAccessTokenIssuer implements the issuance port. Successful LOCAL/LDAP/AD login returns session id, Hidra bearer token lifecycle metadata, and normalized HidraPrincipal identity/roles/permissions. Submitted credentials are never returned or persisted. The endpoint is explicitly permitAll so unauthenticated callers can authenticate. OIDC browser PKCE completion remains untouched for AUTH-018.\n```\n\nValidation:\n\n```bash\nmvn -q test\n```\n\nResult:\n\n```text\nPASS — initial PR CI run 188 exposed and blocked an API-to-infrastructure architecture violation. The implementation was corrected with application ports/adapters; replacement PR CI run 197 completed repository and acceptance `mvn -q test` checks successfully for AUTH-017 implementation commit 6aab35936511036ffa445954c36d4b661eae107c.\n```\n\n---\n\n### AUTH-018 — OIDC completion to Hidra token""",
    """AUTH-017 — feat(authentication): wire dynamic login endpoint\n```\n\nAUTH-016 now provides provider-neutral LoginSession lifecycle and AuthenticationEvent recording without raw-token persistence. Do not implement AUTH-018 or later tasks during AUTH-017.""":
        """AUTH-018 — feat(authentication): converge oidc completion on hidra token\n```\n\nAUTH-017 now provides the canonical direct LOCAL/LDAP/AD login boundary through application ports to the existing provider router/AuthenticationManager, session lifecycle, and unified Hidra token issuer. Do not implement AUTH-019 or later tasks during AUTH-018."""
}

for old, new in replacements.items():
    if old not in text:
        raise SystemExit(f"Expected roadmap fragment not found: {old[:180]!r}")
    text = text.replace(old, new, 1)

path.write_text(text, encoding="utf-8")
