from pathlib import Path

path = Path("docs/roadmap/authentication.md")
text = path.read_text(encoding="utf-8")

replacements = {
    "| Status | Active — AUTH-018 OIDC completion to Hidra token completed |":
        "| Status | Active — AUTH-019 protected API bearer authentication standardized |",
    "| AUTH-019 | `refactor(security): standardize protected api bearer authentication` | Make protected APIs consume the unified Hidra JWT while preserving authorization behavior | Planned |":
        "| AUTH-019 | `refactor(security): standardize protected api bearer authentication` | Make protected APIs consume the unified Hidra JWT while preserving authorization behavior | Completed — ordinary JWT-protected APIs now accept only standardized Hidra-issued HS256 access tokens through the Hidra JWT decoder/authorities converter, while a higher-priority path-scoped chain isolates external OIDC bearer validation to the secured completion bridge; existing permission/interceptor semantics are preserved and unconfigured OIDC fails closed on use; `mvn -q clean verify` passed in PR CI run 213 |",
    """Protected APIs should consume the standardized Hidra bearer JWT irrespective of original auth source.\n\nPreserve current permission/interceptor semantics.\n\nValidation:\n\n```bash\nmvn -q clean verify\n```\n\n---\n\n### AUTH-020 — Retire ordinary in-memory LOCAL authentication""":
        """Protected APIs should consume the standardized Hidra bearer JWT irrespective of original auth source.\n\nPreserve current permission/interceptor semantics.\n\nStatus:\n\n```text\nCompleted — security is now split by bearer trust boundary. The higher-priority OIDC completion SecurityFilterChain matches only /api/v1/identity/authentication/oidc/complete and validates external OIDC JWTs with the existing IdentityOidcJwtAuthenticationConverter. The general protected-API chain validates only Hidra-issued HS256 JWTs through hidraJwtDecoder and reconstructs authorities with the existing HidraJwtGrantedAuthoritiesConverter, preserving current role/scope and permission/interceptor semantics. The decoder configuration now exposes separate Hidra and external-OIDC decoders. Missing external OIDC issuer/JWK configuration no longer blocks unrelated application modes from starting; attempted OIDC completion instead fails closed. In-memory LOCAL/bootstrap behavior was not changed because AUTH-020 owns that work.\n```\n\nValidation:\n\n```bash\nmvn -q clean verify\n```\n\nResult:\n\n```text\nPASS — PR CI run 213 completed repository and acceptance clean verification successfully for AUTH-019 implementation commit e33cb9ca93b5ce09f4a471c445a80046becf4795. Earlier runs 211 and 212 correctly blocked a missing import and eager unconfigured OIDC decoder startup respectively; both issues were corrected before validation passed.\n```\n\n---\n\n### AUTH-020 — Retire ordinary in-memory LOCAL authentication""",
    """AUTH-019 — refactor(security): standardize protected api bearer authentication\n```\n\nAUTH-018 now converges externally validated OIDC identities onto the same Hidra session/token completion path as direct authentication while preserving browser PKCE semantics. Do not implement AUTH-020 or later tasks during AUTH-019.""":
        """AUTH-020 — refactor(security): retire ordinary in-memory local authentication\n```\n\nAUTH-019 now isolates external OIDC bearer validation to the completion bridge and requires standardized Hidra-issued JWTs for ordinary protected APIs while preserving existing authorization semantics. Do not implement AUTH-021 or later tasks during AUTH-020."""
}

for old, new in replacements.items():
    if old not in text:
        raise SystemExit(f"Expected roadmap fragment not found: {old[:180]!r}")
    text = text.replace(old, new, 1)

path.write_text(text, encoding="utf-8")
