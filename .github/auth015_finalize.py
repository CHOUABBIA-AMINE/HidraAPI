from pathlib import Path

path = Path("docs/roadmap/authentication.md")
text = path.read_text(encoding="utf-8")

replacements = {
    "| Status | Active — AUTH-014 LDAP authentication provider completed |":
        "| Status | Active — AUTH-015 unified Hidra access-token issuer completed |",
    "| AUTH-015 | `feat(authentication): add unified hidra access token issuer` | Add JwtEncoder and issue one standardized Hidra JWT for all providers | Planned |":
        "| AUTH-015 | `feat(authentication): add unified hidra access token issuer` | Add JwtEncoder and issue one standardized Hidra JWT for all providers | Completed — provider-neutral HidraAccessTokenIssuer now emits one JWT schema from HidraPrincipal with stable Hidra subject, issuer/audience/expiry/JTI, Hidra-owned roles/permissions, externalized HS256 signing material, and HMAC resource-server issuer compatibility; `mvn -q test` passed in PR CI run 174 |",
    """one token schema for LOCAL/LDAP/OIDC\nstable Hidra user subject\nissuer/audience/expiry/JTI\nexternalized signing material\ncompatibility with existing resource server\nno authorization bypass through provider claims\n```\n\nValidation:\n\n```bash\nmvn -q test\n```\n\n---\n\n### AUTH-016 — Authentication session lifecycle""":
        """one token schema for LOCAL/LDAP/OIDC\nstable Hidra user subject\nissuer/audience/expiry/JTI\nexternalized signing material\ncompatibility with existing resource server\nno authorization bypass through provider claims\n```\n\nStatus:\n\n```text\nCompleted — HidraAccessTokenIssuer accepts the normalized HidraPrincipal shared by LOCAL, LDAP/Active Directory, and OIDC and produces one Bearer JWT schema. The standard subject is the stable Hidra user ID; issuer, audience, expiry, issued-at, and JTI are present; configured roles and scope claims are populated only from HidraPrincipal roles/permissions; authentication type and provider linkage are metadata only. HidraJwtEncoderConfiguration signs HS256 tokens with the existing externalized HIDRA_JWT_HMAC_SECRET and enforces a minimum 32-byte secret. The HMAC resource-server decoder validates the dedicated Hidra token issuer while external JWK/issuer OIDC validation remains unchanged. No external IdP roles/groups are promoted, and no session/login endpoint behavior from AUTH-016 or later was implemented.\n```\n\nValidation:\n\n```bash\nmvn -q test\n```\n\nResult:\n\n```text\nPASS — pull-request CI run 174 completed repository and acceptance `mvn -q test` checks successfully for AUTH-015 implementation commit 5a28df47c5107a5c93d1c35c127429e5427a4cc4.\n```\n\n---\n\n### AUTH-016 — Authentication session lifecycle""",
    """AUTH-015 — feat(authentication): add unified hidra access token issuer\n```\n\nAUTH-014 now normalizes successful LDAP/Active Directory authentication to HidraPrincipal using linked Hidra identity and authorization. Do not implement AUTH-016 or later tasks during AUTH-015.""":
        """AUTH-016 — feat(identity): complete authentication session lifecycle\n```\n\nAUTH-015 now provides one Hidra-issued access-token schema from normalized HidraPrincipal with externalized signing material and HMAC resource-server compatibility. Do not implement AUTH-017 or later tasks during AUTH-016."""
}

for old, new in replacements.items():
    if old not in text:
        raise SystemExit(f"Expected roadmap fragment not found: {old[:180]!r}")
    text = text.replace(old, new, 1)

path.write_text(text, encoding="utf-8")
