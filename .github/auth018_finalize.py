from pathlib import Path

path = Path("docs/roadmap/authentication.md")
text = path.read_text(encoding="utf-8")

replacements = {
    "| Status | Active — AUTH-017 dynamic direct-login endpoint completed |":
        "| Status | Active — AUTH-018 OIDC completion to Hidra token completed |",
    "| AUTH-018 | `feat(authentication): converge oidc completion on hidra token` | Ensure OIDC completion produces same Hidra principal/session/JWT result as LOCAL/LDAP while preserving current PKCE behavior | Planned |":
        "| AUTH-018 | `feat(authentication): converge oidc completion on hidra token` | Ensure OIDC completion produces same Hidra principal/session/JWT result as LOCAL/LDAP while preserving current PKCE behavior | Completed — externally validated OIDC HidraPrincipal now completes through the same provider-neutral session/token application path used by direct authentication; secured POST /api/v1/identity/authentication/oidc/complete issues the AUTH-015 Hidra bearer JWT and AUTH-016 LoginSession while preserving browser authorization-code + PKCE semantics and adding no OAuth callback/token exchange; `mvn -q test` passed in PR CI run 204 |",
    """After external OIDC validation, route the mapped HidraPrincipal through the same session/token issuance path as LOCAL and LDAP while preserving the current authorization-code + PKCE browser security semantics.\n\nValidation:\n\n```bash\nmvn -q test\n```\n\n---\n\n### AUTH-019 — Standard protected-API bearer authentication""":
        """After external OIDC validation, route the mapped HidraPrincipal through the same session/token issuance path as LOCAL and LDAP while preserving the current authorization-code + PKCE browser security semantics.\n\nStatus:\n\n```text\nCompleted — AuthenticationCompletionApplicationService is now the provider-neutral post-authentication seam for HidraPrincipal -> unified access token -> logical LoginSession. DirectAuthenticationApplicationService delegates to this shared seam after LOCAL/LDAP/AD credential authentication. POST /api/v1/identity/authentication/oidc/complete is intentionally protected rather than permitAll: the external OIDC bearer JWT must first be validated by the existing resource-server decoder and normalized by IdentityOidcJwtAuthenticationConverter. Only an OIDC HidraPrincipal can complete the endpoint. The completion response reuses AuthenticationLoginResponse and therefore returns the same session/token/principal shape as direct login. Browser authorization-code + PKCE remains owned by HidraWEB/external IdP; HidraAPI still does not implement an OAuth2 client callback or authorization-code/token exchange. AUTH-019 protected-bearer standardization was not implemented.\n```\n\nValidation:\n\n```bash\nmvn -q test\n```\n\nResult:\n\n```text\nPASS — pull-request CI run 204 completed repository and acceptance `mvn -q test` checks successfully for AUTH-018 implementation commit c87808ce41d77e89330776681d4583740ad035b6.\n```\n\n---\n\n### AUTH-019 — Standard protected-API bearer authentication""",
    """AUTH-018 — feat(authentication): converge oidc completion on hidra token\n```\n\nAUTH-017 now provides the canonical direct LOCAL/LDAP/AD login boundary through application ports to the existing provider router/AuthenticationManager, session lifecycle, and unified Hidra token issuer. Do not implement AUTH-019 or later tasks during AUTH-018.""":
        """AUTH-019 — refactor(security): standardize protected api bearer authentication\n```\n\nAUTH-018 now converges externally validated OIDC identities onto the same Hidra session/token completion path as direct authentication while preserving browser PKCE semantics. Do not implement AUTH-020 or later tasks during AUTH-019."""
}

for old, new in replacements.items():
    if old not in text:
        raise SystemExit(f"Expected roadmap fragment not found: {old[:180]!r}")
    text = text.replace(old, new, 1)

path.write_text(text, encoding="utf-8")
